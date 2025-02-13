(ns chromex.ext.autofill-private
  "Use the chrome.autofillPrivate API to add, remove, or update
   autofill data from the settings UI.

     * available since Chrome 51"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro save-address
  "Saves the given address. If |address| has an empty string as its ID, it will be assigned a new one and added as a new
   entry.

     |address| - The address entry to save."
  ([address] (gen-call :function ::save-address &form address)))

(defmacro get-address-components
  "Gets the address components for a given country code.

     |country-code| - A two-character string representing the address' country     whose components should be returned. See
                      autofill_country.cc for a     list of valid codes.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [components] where:

     |components| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([country-code] (gen-call :function ::get-address-components &form country-code)))

(defmacro save-credit-card
  "Saves the given credit card. If |card| has an empty string as its ID, it will be assigned a new one and added as a new
   entry.

     |card| - The card entry to save."
  ([card] (gen-call :function ::save-credit-card &form card)))

(defmacro remove-entry
  "Removes the entry (address or credit card) with the given ID.

     |guid| - ID of the entry to remove."
  ([guid] (gen-call :function ::remove-entry &form guid)))

(defmacro validate-phone-numbers
  "Validates a newly-added phone number and invokes the callback with a list of validated numbers. Note that if the
   newly-added number was invalid, it will not be returned in the list of valid numbers.

     |params| - The parameters to this function.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [validated-phone-numbers] where:

     |validated-phone-numbers| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([params] (gen-call :function ::validate-phone-numbers &form params)))

(defmacro mask-credit-card
  "Clears the data associated with a wallet card which was saved locally so that the saved copy is masked (e.g., 'Card ending
   in 1234').

     |guid| - GUID of the credit card to mask."
  ([guid] (gen-call :function ::mask-credit-card &form guid)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-address-list-changed-events
  "Fired when the address list has changed, meaning that an entry has been added, removed, or changed.  |entries| The updated
   list of entries.

   Events will be put on the |channel| with signature [::on-address-list-changed [entries]] where:

     |entries| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-address-list-changed &form channel args)))

(defmacro tap-on-credit-card-list-changed-events
  "Fired when the credit card list has changed, meaning that an entry has been added, removed, or changed.  |entries| The
   updated list of entries.

   Events will be put on the |channel| with signature [::on-credit-card-list-changed [entries]] where:

     |entries| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-credit-card-list-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.autofill-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.autofillPrivate",
   :since "51",
   :functions
   [{:id ::save-address, :name "saveAddress", :params [{:name "address", :type "autofillPrivate.AddressEntry"}]}
    {:id ::get-address-components,
     :name "getAddressComponents",
     :callback? true,
     :params
     [{:name "country-code", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "components", :type "object"}]}}]}
    {:id ::save-credit-card, :name "saveCreditCard", :params [{:name "card", :type "autofillPrivate.CreditCardEntry"}]}
    {:id ::remove-entry, :name "removeEntry", :params [{:name "guid", :type "string"}]}
    {:id ::validate-phone-numbers,
     :name "validatePhoneNumbers",
     :callback? true,
     :params
     [{:name "params", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "validated-phone-numbers", :type "[array-of-strings]"}]}}]}
    {:id ::mask-credit-card, :name "maskCreditCard", :params [{:name "guid", :type "string"}]}],
   :events
   [{:id ::on-address-list-changed,
     :name "onAddressListChanged",
     :params [{:name "entries", :type "[array-of-autofillPrivate.AddressEntrys]"}]}
    {:id ::on-credit-card-list-changed,
     :name "onCreditCardListChanged",
     :params [{:name "entries", :type "[array-of-autofillPrivate.CreditCardEntrys]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))
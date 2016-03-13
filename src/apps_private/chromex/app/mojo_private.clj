(ns chromex.app.mojo-private
  "The chrome.mojoPrivate API provides access to the mojo modules.
   
     * available since Chrome 42
     * https://developer.chrome.com/extensions/mojoPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro define
  "Defines a AMD module.
   
     |moduleName| - See https://developer.chrome.com/extensions/mojoPrivate#property-define-moduleName.
     |dependencies| - See https://developer.chrome.com/extensions/mojoPrivate#property-define-dependencies.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [modules] where:
   
     |modules| - See https://developer.chrome.com/extensions/mojoPrivate#property-factory-modules.
   
   See https://developer.chrome.com/extensions/mojoPrivate#method-define."
  ([module-name dependencies #_factory] (gen-call :function ::define &form module-name dependencies))
  ([module-name] `(define ~module-name :omit)))

(defmacro require-async
  "Returns a promise that will resolve to an asynchronously loaded module.
   
     |name| - See https://developer.chrome.com/extensions/mojoPrivate#property-requireAsync-name.
   
   See https://developer.chrome.com/extensions/mojoPrivate#method-requireAsync."
  ([name] (gen-call :function ::require-async &form name)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.mojoPrivate",
   :since "42",
   :functions
   [{:id ::define,
     :name "define",
     :callback? true,
     :params
     [{:name "module-name", :type "string"}
      {:name "dependencies", :optional? true, :type "[array-of-strings]"}
      {:name "factory", :type :callback, :callback {:params [{:name "modules", :type "[array-of-anys]"}]}}]}
    {:id ::require-async, :name "requireAsync", :return-type "any", :params [{:name "name", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))
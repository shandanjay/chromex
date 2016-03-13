(ns chromex.ext.sessions
  "Use the chrome.sessions API to query and restore tabs and windows from a browsing session.
   
     * available since Chrome 37
     * https://developer.chrome.com/extensions/sessions"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-session-results
  "The maximum number of 'sessions.Session' that will be included in a requested list.
   
   See https://developer.chrome.com/extensions/sessions#property-MAX_SESSION_RESULTS."
  ([] (gen-call :property ::max-session-results &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-recently-closed
  "Gets the list of recently closed tabs and/or windows.
   
     |filter| - See https://developer.chrome.com/extensions/sessions#property-getRecentlyClosed-filter.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [sessions] where:
   
     |sessions| - The list of closed entries in reverse order that they were closed (the most recently closed tab or window
                  will be at index 0). The entries may contain either tabs or windows.
   
   See https://developer.chrome.com/extensions/sessions#method-getRecentlyClosed."
  ([filter #_callback] (gen-call :function ::get-recently-closed &form filter))
  ([] `(get-recently-closed :omit)))

(defmacro get-devices
  "Retrieves all devices with synced sessions.
   
     |filter| - See https://developer.chrome.com/extensions/sessions#property-getDevices-filter.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [devices] where:
   
     |devices| - The list of 'sessions.Device' objects for each synced session, sorted in order from device with most recently
                 modified session to device with least recently modified session. 'tabs.Tab' objects are sorted by recency in
                 the 'windows.Window' of the 'sessions.Session' objects.
   
   See https://developer.chrome.com/extensions/sessions#method-getDevices."
  ([filter #_callback] (gen-call :function ::get-devices &form filter))
  ([] `(get-devices :omit)))

(defmacro restore
  "Reopens a 'windows.Window' or 'tabs.Tab', with an optional callback to run when the entry has been restored.
   
     |sessionId| - The 'windows.Window.sessionId', or 'tabs.Tab.sessionId' to restore. If this parameter is not specified,
                   the most recently closed session is restored.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [restoredSession] where:
   
     |restoredSession| - A 'sessions.Session' containing the restored 'windows.Window' or 'tabs.Tab' object.
   
   See https://developer.chrome.com/extensions/sessions#method-restore."
  ([session-id #_callback] (gen-call :function ::restore &form session-id))
  ([] `(restore :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when recently closed tabs and/or windows are changed. This event does not monitor synced sessions changes.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/sessions#event-onChanged."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

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
  {:namespace "chrome.sessions",
   :since "37",
   :properties [{:id ::max-session-results, :name "MAX_SESSION_RESULTS", :return-type "unknown-type"}],
   :functions
   [{:id ::get-recently-closed,
     :name "getRecentlyClosed",
     :callback? true,
     :params
     [{:name "filter", :optional? true, :type "sessions.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "sessions", :type "[array-of-sessions.Sessions]"}]}}]}
    {:id ::get-devices,
     :name "getDevices",
     :callback? true,
     :params
     [{:name "filter", :optional? true, :type "sessions.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "devices", :type "[array-of-sessions.Devices]"}]}}]}
    {:id ::restore,
     :name "restore",
     :callback? true,
     :params
     [{:name "session-id", :optional? true, :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "restored-session", :type "sessions.Session"}]}}]}],
   :events [{:id ::on-changed, :name "onChanged"}]})

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
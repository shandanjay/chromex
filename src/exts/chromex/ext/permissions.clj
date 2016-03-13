(ns chromex.ext.permissions
  "Use the chrome.permissions API to request declared optional permissions at run time rather than install time, so users
   understand why the permissions are needed and grant only those that are necessary.
   
     * available since Chrome 16
     * https://developer.chrome.com/extensions/permissions"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-all
  "Gets the extension's current set of permissions.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [permissions] where:
   
     |permissions| - The extension's active permissions.
   
   See https://developer.chrome.com/extensions/permissions#method-getAll."
  ([#_callback] (gen-call :function ::get-all &form)))

(defmacro contains
  "Checks if the extension has the specified permissions.
   
     |permissions| - See https://developer.chrome.com/extensions/permissions#property-contains-permissions.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - True if the extension has the specified permissions.
   
   See https://developer.chrome.com/extensions/permissions#method-contains."
  ([permissions #_callback] (gen-call :function ::contains &form permissions)))

(defmacro request
  "Requests access to the specified permissions. These permissions must be defined in the optional_permissions field of the
   manifest. If there are any problems requesting the permissions, 'runtime.lastError' will be set.
   
     |permissions| - See https://developer.chrome.com/extensions/permissions#property-request-permissions.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [granted] where:
   
     |granted| - True if the user granted the specified permissions.
   
   See https://developer.chrome.com/extensions/permissions#method-request."
  ([permissions #_callback] (gen-call :function ::request &form permissions)))

(defmacro remove
  "Removes access to the specified permissions. If there are any problems removing the permissions, 'runtime.lastError' will
   be set.
   
     |permissions| - See https://developer.chrome.com/extensions/permissions#property-remove-permissions.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [removed] where:
   
     |removed| - True if the permissions were removed.
   
   See https://developer.chrome.com/extensions/permissions#method-remove."
  ([permissions #_callback] (gen-call :function ::remove &form permissions)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-added-events
  "Fired when the extension acquires new permissions.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/permissions#event-onAdded."
  ([channel & args] (apply gen-call :event ::on-added &form channel args)))

(defmacro tap-on-removed-events
  "Fired when access to permissions has been removed from the extension.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/permissions#event-onRemoved."
  ([channel & args] (apply gen-call :event ::on-removed &form channel args)))

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
  {:namespace "chrome.permissions",
   :since "16",
   :functions
   [{:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "permissions", :type "permissions.Permissions"}]}}]}
    {:id ::contains,
     :name "contains",
     :callback? true,
     :params
     [{:name "permissions", :type "permissions.Permissions"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::request,
     :name "request",
     :callback? true,
     :params
     [{:name "permissions", :type "permissions.Permissions"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "granted", :type "boolean"}]}}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params
     [{:name "permissions", :type "permissions.Permissions"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "removed", :type "boolean"}]}}]}],
   :events
   [{:id ::on-added, :name "onAdded", :params [{:name "permissions", :type "permissions.Permissions"}]}
    {:id ::on-removed, :name "onRemoved", :params [{:name "permissions", :type "permissions.Permissions"}]}]})

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
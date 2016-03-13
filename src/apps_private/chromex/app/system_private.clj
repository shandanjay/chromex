(ns chromex.app.system-private
  "  * available since Chrome 18
     * https://developer.chrome.com/extensions/systemPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-incognito-mode-availability
  "Returns whether the incognito mode is enabled, disabled or forced
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [value] where:
   
     |value| - See https://developer.chrome.com/extensions/systemPrivate#property-callback-value.
   
   See https://developer.chrome.com/extensions/systemPrivate#method-getIncognitoModeAvailability."
  ([#_callback] (gen-call :function ::get-incognito-mode-availability &form)))

(defmacro get-update-status
  "Gets information about the system update.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:
   
     |status| - Details of the system update
   
   See https://developer.chrome.com/extensions/systemPrivate#method-getUpdateStatus."
  ([#_callback] (gen-call :function ::get-update-status &form)))

(defmacro get-api-key
  "Gets Chrome's API key to use for requests to Google services.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [key] where:
   
     |key| - The API key.
   
   See https://developer.chrome.com/extensions/systemPrivate#method-getApiKey."
  ([#_callback] (gen-call :function ::get-api-key &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-volume-changed-events
  "Fired when the volume is changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/systemPrivate#event-onVolumeChanged."
  ([channel & args] (apply gen-call :event ::on-volume-changed &form channel args)))

(defmacro tap-on-brightness-changed-events
  "Fired when the screen brightness is changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/systemPrivate#event-onBrightnessChanged."
  ([channel & args] (apply gen-call :event ::on-brightness-changed &form channel args)))

(defmacro tap-on-screen-unlocked-events
  "Fired when the screen is unlocked.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/systemPrivate#event-onScreenUnlocked."
  ([channel & args] (apply gen-call :event ::on-screen-unlocked &form channel args)))

(defmacro tap-on-woke-up-events
  "Fired when the device wakes up from sleep.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/systemPrivate#event-onWokeUp."
  ([channel & args] (apply gen-call :event ::on-woke-up &form channel args)))

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
  {:namespace "chrome.systemPrivate",
   :since "18",
   :functions
   [{:id ::get-incognito-mode-availability,
     :name "getIncognitoModeAvailability",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "value", :type "systemPrivate.GetIncognitoModeAvailabilityValue"}]}}]}
    {:id ::get-update-status,
     :name "getUpdateStatus",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "status", :type "systemPrivate.UpdateStatus"}]}}]}
    {:id ::get-api-key,
     :name "getApiKey",
     :since "33",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "key", :type "string"}]}}]}],
   :events
   [{:id ::on-volume-changed, :name "onVolumeChanged", :params [{:name "volume", :type "systemPrivate.VolumeInfo"}]}
    {:id ::on-brightness-changed,
     :name "onBrightnessChanged",
     :params [{:name "brightness", :type "systemPrivate.BrightnessChangeInfo"}]}
    {:id ::on-screen-unlocked, :name "onScreenUnlocked"}
    {:id ::on-woke-up, :name "onWokeUp"}]})

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
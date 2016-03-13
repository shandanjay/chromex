(ns chromex.ext.webcam-private
  "Webcam Private API.
   
     * available since Chrome 40
     * https://developer.chrome.com/extensions/webcamPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-serial-webcam
  "Open a serial port that controls a webcam.
   
     |path| - See https://developer.chrome.com/extensions/webcamPrivate#property-openSerialWebcam-path.
     |protocol| - See https://developer.chrome.com/extensions/webcamPrivate#property-openSerialWebcam-protocol.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [webcamId] where:
   
     |webcamId| - See https://developer.chrome.com/extensions/webcamPrivate#property-callback-webcamId.
   
   See https://developer.chrome.com/extensions/webcamPrivate#method-openSerialWebcam."
  ([path protocol #_callback] (gen-call :function ::open-serial-webcam &form path protocol)))

(defmacro close-webcam
  "Close a serial port connection to a webcam.
   
     |webcamId| - See https://developer.chrome.com/extensions/webcamPrivate#property-closeWebcam-webcamId.
   
   See https://developer.chrome.com/extensions/webcamPrivate#method-closeWebcam."
  ([webcam-id] (gen-call :function ::close-webcam &form webcam-id)))

(defmacro get
  "  |webcamId| - See https://developer.chrome.com/extensions/webcamPrivate#property-get-webcamId.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [configuration] where:
   
     |configuration| - See https://developer.chrome.com/extensions/webcamPrivate#property-callback-configuration.
   
   See https://developer.chrome.com/extensions/webcamPrivate#method-get."
  ([webcam-id #_callback] (gen-call :function ::get &form webcam-id)))

(defmacro set
  "  |webcamId| - See https://developer.chrome.com/extensions/webcamPrivate#property-set-webcamId.
     |config| - See https://developer.chrome.com/extensions/webcamPrivate#property-set-config.
   
   See https://developer.chrome.com/extensions/webcamPrivate#method-set."
  ([webcam-id config] (gen-call :function ::set &form webcam-id config)))

(defmacro reset
  "Reset a webcam. Note: the value of the parameter have no effect, it's the presence of the parameter that matters. E.g.:
   reset(webcamId, {pan: 0,  tilt: 1}); will reset pan & tilt, but not zoom.
   
     |webcamId| - See https://developer.chrome.com/extensions/webcamPrivate#property-reset-webcamId.
     |config| - See https://developer.chrome.com/extensions/webcamPrivate#property-reset-config.
   
   See https://developer.chrome.com/extensions/webcamPrivate#method-reset."
  ([webcam-id config] (gen-call :function ::reset &form webcam-id config)))

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
  {:namespace "chrome.webcamPrivate",
   :since "40",
   :functions
   [{:id ::open-serial-webcam,
     :name "openSerialWebcam",
     :since "45",
     :callback? true,
     :params
     [{:name "path", :type "string"}
      {:name "protocol", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "webcam-id", :type "string"}]}}]}
    {:id ::close-webcam, :name "closeWebcam", :since "45", :params [{:name "webcam-id", :type "string"}]}
    {:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamConfiguration"}]}}]}
    {:id ::set,
     :name "set",
     :params [{:name "webcam-id", :type "string"} {:name "config", :type "webcamPrivate.WebcamConfiguration"}]}
    {:id ::reset,
     :name "reset",
     :params [{:name "webcam-id", :type "string"} {:name "config", :type "webcamPrivate.WebcamConfiguration"}]}]})

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
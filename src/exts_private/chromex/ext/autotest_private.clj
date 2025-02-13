(ns chromex.ext.autotest-private
  "API for integration testing. To be used on test images with a test component
   extension.

     * available since Chrome 25"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro logout
  "Logout of a user session."
  ([] (gen-call :function ::logout &form)))

(defmacro restart
  "Restart the browser."
  ([] (gen-call :function ::restart &form)))

(defmacro shutdown
  "Shutdown the browser.

     |force| - if set, ignore ongoing downloads and onunbeforeunload handlers."
  ([force] (gen-call :function ::shutdown &form force)))

(defmacro login-status
  "Get login status.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::login-status &form)))

(defmacro lock-screen
  "Locks the screen."
  ([] (gen-call :function ::lock-screen &form)))

(defmacro get-extensions-info
  "Get info about installed extensions.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-extensions-info &form)))

(defmacro simulate-asan-memory-bug
  "Simulates a memory access bug for asan testing."
  ([] (gen-call :function ::simulate-asan-memory-bug &form)))

(defmacro set-touchpad-sensitivity
  "Set the touchpad pointer sensitivity setting.

     |value| - the pointer sensitivity setting index."
  ([value] (gen-call :function ::set-touchpad-sensitivity &form value)))

(defmacro set-tap-to-click
  "Turn on/off tap-to-click for the touchpad.

     |enabled| - if set, enable tap-to-click."
  ([enabled] (gen-call :function ::set-tap-to-click &form enabled)))

(defmacro set-three-finger-click
  "Turn on/off three finger click for the touchpad.

     |enabled| - if set, enable three finger click."
  ([enabled] (gen-call :function ::set-three-finger-click &form enabled)))

(defmacro set-tap-dragging
  "Turn on/off tap dragging for the touchpad.

     |enabled| - if set, enable tap dragging."
  ([enabled] (gen-call :function ::set-tap-dragging &form enabled)))

(defmacro set-natural-scroll
  "Turn on/off Australian scrolling for devices other than wheel mouse.

     |enabled| - if set, enable Australian scrolling."
  ([enabled] (gen-call :function ::set-natural-scroll &form enabled)))

(defmacro set-mouse-sensitivity
  "Set the mouse pointer sensitivity setting.

     |value| - the pointer sensitivity setting index."
  ([value] (gen-call :function ::set-mouse-sensitivity &form value)))

(defmacro set-primary-button-right
  "Swap the primary mouse button for left click.

     |right| - if set, swap the primary mouse button."
  ([right] (gen-call :function ::set-primary-button-right &form right)))

(defmacro get-visible-notifications
  "Get visible notifications on the system.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [notifications] where:

     |notifications| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-visible-notifications &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.autotest-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.autotestPrivate",
   :since "25",
   :functions
   [{:id ::logout, :name "logout"}
    {:id ::restart, :name "restart"}
    {:id ::shutdown, :name "shutdown", :params [{:name "force", :type "boolean"}]}
    {:id ::login-status,
     :name "loginStatus",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "status", :type "object"}]}}]}
    {:id ::lock-screen, :name "lockScreen"}
    {:id ::get-extensions-info,
     :name "getExtensionsInfo",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::simulate-asan-memory-bug, :name "simulateAsanMemoryBug"}
    {:id ::set-touchpad-sensitivity, :name "setTouchpadSensitivity", :params [{:name "value", :type "integer"}]}
    {:id ::set-tap-to-click, :name "setTapToClick", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-three-finger-click, :name "setThreeFingerClick", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-tap-dragging, :name "setTapDragging", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-natural-scroll, :name "setNaturalScroll", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-mouse-sensitivity, :name "setMouseSensitivity", :params [{:name "value", :type "integer"}]}
    {:id ::set-primary-button-right, :name "setPrimaryButtonRight", :params [{:name "right", :type "boolean"}]}
    {:id ::get-visible-notifications,
     :name "getVisibleNotifications",
     :since "master",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "notifications", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))
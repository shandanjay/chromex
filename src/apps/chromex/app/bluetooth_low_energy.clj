(ns chromex.app.bluetooth-low-energy
  "The chrome.bluetoothLowEnergy API is used to communicate with
   Bluetooth Smart (Low Energy) devices using the

   Generic Attribute Profile (GATT).

     * available since Chrome 37
     * https://developer.chrome.com/apps/bluetoothLowEnergy"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro connect
  "Establishes a connection between the application and the device with the given address. A device may be already connected
   and its GATT services available without calling connect, however, an app that wants to access GATT services of a device
   should call this function to make sure that a connection to the device is maintained. If the device is not connected, all
   GATT services of the device will be discovered after a successful call to connect.

     |device-address| - The Bluetooth address of the remote device to which a GATT connection should be opened.
     |properties| - Connection properties (optional).

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-connect."
  ([device-address properties] (gen-call :function ::connect &form device-address properties))
  ([device-address] `(connect ~device-address :omit)))

(defmacro disconnect
  "Closes the app's connection to the device with the given address. Note that this will not always destroy the physical link
   itself, since there may be other apps with open connections.

     |device-address| - The Bluetooth address of the remote device.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-disconnect."
  ([device-address] (gen-call :function ::disconnect &form device-address)))

(defmacro get-service
  "Get the GATT service with the given instance ID.

     |service-id| - The instance ID of the requested GATT service.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getService."
  ([service-id] (gen-call :function ::get-service &form service-id)))

(defmacro get-services
  "Get all the GATT services that were discovered on the remote device with the given device address.

     |device-address| - The Bluetooth address of the remote device whose GATT services should be returned.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getServices."
  ([device-address] (gen-call :function ::get-services &form device-address)))

(defmacro get-characteristic
  "Get the GATT characteristic with the given instance ID that belongs to the given GATT service, if the characteristic
   exists.

     |characteristic-id| - The instance ID of the requested GATT characteristic.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getCharacteristic."
  ([characteristic-id] (gen-call :function ::get-characteristic &form characteristic-id)))

(defmacro get-characteristics
  "Get a list of all discovered GATT characteristics that belong to the given service.

     |service-id| - The instance ID of the GATT service whose characteristics should be returned.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getCharacteristics."
  ([service-id] (gen-call :function ::get-characteristics &form service-id)))

(defmacro get-included-services
  "Get a list of GATT services that are included by the given service.

     |service-id| - The instance ID of the GATT service whose included services should be returned.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getIncludedServices."
  ([service-id] (gen-call :function ::get-included-services &form service-id)))

(defmacro get-descriptor
  "Get the GATT characteristic descriptor with the given instance ID.

     |descriptor-id| - The instance ID of the requested GATT characteristic descriptor.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getDescriptor."
  ([descriptor-id] (gen-call :function ::get-descriptor &form descriptor-id)))

(defmacro get-descriptors
  "Get a list of GATT characteristic descriptors that belong to the given characteristic.

     |characteristic-id| - The instance ID of the GATT characteristic whose descriptors should be returned.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-getDescriptors."
  ([characteristic-id] (gen-call :function ::get-descriptors &form characteristic-id)))

(defmacro read-characteristic-value
  "Retrieve the value of a specified characteristic from a remote peripheral.

     |characteristic-id| - The instance ID of the GATT characteristic whose value should be read from the remote device.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-readCharacteristicValue."
  ([characteristic-id] (gen-call :function ::read-characteristic-value &form characteristic-id)))

(defmacro write-characteristic-value
  "Write the value of a specified characteristic from a remote peripheral.

     |characteristic-id| - The instance ID of the GATT characteristic whose value should be written to.
     |value| - The value that should be sent to the remote characteristic as part of the write request.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-writeCharacteristicValue."
  ([characteristic-id value] (gen-call :function ::write-characteristic-value &form characteristic-id value)))

(defmacro start-characteristic-notifications
  "Enable value notifications/indications from the specified characteristic. Once enabled, an application can listen to
   notifications using the 'onCharacteristicValueChanged' event.

     |characteristic-id| - The instance ID of the GATT characteristic that notifications should be enabled on.
     |properties| - Notification session properties (optional).

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-startCharacteristicNotifications."
  ([characteristic-id properties] (gen-call :function ::start-characteristic-notifications &form characteristic-id properties))
  ([characteristic-id] `(start-characteristic-notifications ~characteristic-id :omit)))

(defmacro stop-characteristic-notifications
  "Disable value notifications/indications from the specified characteristic. After a successful call, the application will
   stop receiving notifications/indications from this characteristic.

     |characteristic-id| - The instance ID of the GATT characteristic on which this app's notification session should be
                           stopped.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-stopCharacteristicNotifications."
  ([characteristic-id] (gen-call :function ::stop-characteristic-notifications &form characteristic-id)))

(defmacro read-descriptor-value
  "Retrieve the value of a specified characteristic descriptor from a remote peripheral.

     |descriptor-id| - The instance ID of the GATT characteristic descriptor whose value should be read from the remote
                       device.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-readDescriptorValue."
  ([descriptor-id] (gen-call :function ::read-descriptor-value &form descriptor-id)))

(defmacro write-descriptor-value
  "Write the value of a specified characteristic descriptor from a remote peripheral.

     |descriptor-id| - The instance ID of the GATT characteristic descriptor whose value should be written to.
     |value| - The value that should be sent to the remote descriptor as part of the write request.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-writeDescriptorValue."
  ([descriptor-id value] (gen-call :function ::write-descriptor-value &form descriptor-id value)))

(defmacro register-advertisement
  "Create an advertisement and register it for advertising. To call this function, the app must have the bluetooth:low_energy
   and bluetooth:peripheral permissions set to true. Additionally this API is only available to auto launched apps in Kiosk
   Mode of by setting the 'enable-ble-advertising-in-apps' flag. See https://developer.chrome.com/apps/manifest/bluetooth
   Note: On some hardware, central and peripheral modes at the same time is supported but on hardware that doesn't support
   this, making this call will switch the device to peripheral mode. In the case of hardware which does not support both
   central and peripheral mode, attempting to use the device in both modes will lead to undefined behavior or prevent other
   central-role applications from behaving correctly (including the discovery of Bluetooth Low Energy devices).

     |advertisement| - The advertisement to advertise.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [advertisement-id] where:

     |advertisement-id| - https://developer.chrome.com/apps/bluetoothLowEnergy#property-callback-advertisementId.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-registerAdvertisement."
  ([advertisement] (gen-call :function ::register-advertisement &form advertisement)))

(defmacro unregister-advertisement
  "Unregisters an advertisement and stops its advertising. If the advertisement fails to unregister the only way to stop
   advertising might be to restart the device.

     |advertisement-id| - Id of the advertisement to unregister.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothLowEnergy#method-unregisterAdvertisement."
  ([advertisement-id] (gen-call :function ::unregister-advertisement &form advertisement-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-service-added-events
  "Fired whan a new GATT service has been discovered on a remote device.

   Events will be put on the |channel| with signature [::on-service-added [service]] where:

     |service| - The GATT service that was added.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothLowEnergy#event-onServiceAdded."
  ([channel & args] (apply gen-call :event ::on-service-added &form channel args)))

(defmacro tap-on-service-changed-events
  "Fired when the state of a remote GATT service changes. This involves any characteristics and/or descriptors that get added
   or removed from the service, as well as 'ServiceChanged' notifications from the remote device.

   Events will be put on the |channel| with signature [::on-service-changed [service]] where:

     |service| - The GATT service whose state has changed.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothLowEnergy#event-onServiceChanged."
  ([channel & args] (apply gen-call :event ::on-service-changed &form channel args)))

(defmacro tap-on-service-removed-events
  "Fired when a GATT service that was previously discovered on a remote device has been removed.

   Events will be put on the |channel| with signature [::on-service-removed [service]] where:

     |service| - The GATT service that was removed.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothLowEnergy#event-onServiceRemoved."
  ([channel & args] (apply gen-call :event ::on-service-removed &form channel args)))

(defmacro tap-on-characteristic-value-changed-events
  "Fired when the value of a remote GATT characteristic changes, either as a result of a read request, or a value change
   notification/indication This event will only be sent if the app has enabled notifications by calling
   'startCharacteristicNotifications'.

   Events will be put on the |channel| with signature [::on-characteristic-value-changed [characteristic]] where:

     |characteristic| - The GATT characteristic whose value has changed.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothLowEnergy#event-onCharacteristicValueChanged."
  ([channel & args] (apply gen-call :event ::on-characteristic-value-changed &form channel args)))

(defmacro tap-on-descriptor-value-changed-events
  "Fired when the value of a remote GATT characteristic descriptor changes, usually as a result of a read request. This event
   exists mostly for convenience and will always be sent after a successful call to 'readDescriptorValue'.

   Events will be put on the |channel| with signature [::on-descriptor-value-changed [descriptor]] where:

     |descriptor| - The GATT characteristic descriptor whose value has changed.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothLowEnergy#event-onDescriptorValueChanged."
  ([channel & args] (apply gen-call :event ::on-descriptor-value-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.bluetooth-low-energy namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bluetoothLowEnergy",
   :since "37",
   :functions
   [{:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "properties", :optional? true, :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::disconnect,
     :name "disconnect",
     :callback? true,
     :params [{:name "device-address", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-service,
     :name "getService",
     :callback? true,
     :params
     [{:name "service-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "bluetoothLowEnergy.Service"}]}}]}
    {:id ::get-services,
     :name "getServices",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Services]"}]}}]}
    {:id ::get-characteristic,
     :name "getCharacteristic",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Characteristic"}]}}]}
    {:id ::get-characteristics,
     :name "getCharacteristics",
     :callback? true,
     :params
     [{:name "service-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Characteristics]"}]}}]}
    {:id ::get-included-services,
     :name "getIncludedServices",
     :callback? true,
     :params
     [{:name "service-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Services]"}]}}]}
    {:id ::get-descriptor,
     :name "getDescriptor",
     :callback? true,
     :params
     [{:name "descriptor-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Descriptor"}]}}]}
    {:id ::get-descriptors,
     :name "getDescriptors",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Descriptors]"}]}}]}
    {:id ::read-characteristic-value,
     :name "readCharacteristicValue",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Characteristic"}]}}]}
    {:id ::write-characteristic-value,
     :name "writeCharacteristicValue",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "value", :type "ArrayBuffer"}
      {:name "callback", :type :callback}]}
    {:id ::start-characteristic-notifications,
     :name "startCharacteristicNotifications",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "properties", :optional? true, :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::stop-characteristic-notifications,
     :name "stopCharacteristicNotifications",
     :callback? true,
     :params [{:name "characteristic-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::read-descriptor-value,
     :name "readDescriptorValue",
     :callback? true,
     :params
     [{:name "descriptor-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Descriptor"}]}}]}
    {:id ::write-descriptor-value,
     :name "writeDescriptorValue",
     :callback? true,
     :params
     [{:name "descriptor-id", :type "string"}
      {:name "value", :type "ArrayBuffer"}
      {:name "callback", :type :callback}]}
    {:id ::register-advertisement,
     :name "registerAdvertisement",
     :since "47",
     :callback? true,
     :params
     [{:name "advertisement", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "advertisement-id", :type "integer"}]}}]}
    {:id ::unregister-advertisement,
     :name "unregisterAdvertisement",
     :since "47",
     :callback? true,
     :params [{:name "advertisement-id", :type "integer"} {:name "callback", :type :callback}]}],
   :events
   [{:id ::on-service-added, :name "onServiceAdded", :params [{:name "service", :type "bluetoothLowEnergy.Service"}]}
    {:id ::on-service-changed,
     :name "onServiceChanged",
     :params [{:name "service", :type "bluetoothLowEnergy.Service"}]}
    {:id ::on-service-removed,
     :name "onServiceRemoved",
     :params [{:name "service", :type "bluetoothLowEnergy.Service"}]}
    {:id ::on-characteristic-value-changed,
     :name "onCharacteristicValueChanged",
     :params [{:name "characteristic", :type "bluetoothLowEnergy.Characteristic"}]}
    {:id ::on-descriptor-value-changed,
     :name "onDescriptorValueChanged",
     :params [{:name "descriptor", :type "bluetoothLowEnergy.Descriptor"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))
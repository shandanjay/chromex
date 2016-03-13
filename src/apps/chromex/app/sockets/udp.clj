(ns chromex.app.sockets.udp
  "Use the chrome.sockets.udp API to send and receive data over the
   network using UDP connections. This API supersedes the UDP functionality
   previously found in the 'socket' API.
   
     * available since Chrome 33
     * https://developer.chrome.com/extensions/sockets.udp"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a UDP socket with the given properties.
   
     |properties| - The socket properties (optional).
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [createInfo] where:
   
     |createInfo| - The result of the socket creation.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-create."
  ([properties #_callback] (gen-call :function ::create &form properties))
  ([] `(create :omit)))

(defmacro update
  "Updates the socket properties.
   
     |socketId| - The socket ID.
     |properties| - The properties to update.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.udp#method-update."
  ([socket-id properties #_callback] (gen-call :function ::update &form socket-id properties)))

(defmacro set-paused
  "Pauses or unpauses a socket. A paused socket is blocked from firing onReceive events.
   
     |socketId| - See https://developer.chrome.com/extensions/sockets.udp#property-setPaused-socketId.
     |paused| - Flag to indicate whether to pause or unpause.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.udp#method-setPaused."
  ([socket-id paused #_callback] (gen-call :function ::set-paused &form socket-id paused)))

(defmacro bind
  "Binds the local address and port for the socket. For a client socket, it is recommended to use port 0 to let the platform
   pick a free port.Once the bind operation completes successfully, onReceive events are raised when UDP packets arrive on the
   address/port specified -- unless the socket is paused.
   
     |socketId| - The socket ID.
     |address| - The address of the local machine. DNS name, IPv4 and IPv6 formats are supported. Use '0.0.0.0' to accept
                 packets from all local available network interfaces.
     |port| - The port of the local machine. Use '0' to bind to a free port.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call. A negative value indicates an error.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-bind."
  ([socket-id address port #_callback] (gen-call :function ::bind &form socket-id address port)))

(defmacro send
  "Sends data on the given socket to the given address and port. The socket must be bound to a local port before calling this
   method.
   
     |socketId| - The socket ID.
     |data| - The data to send.
     |address| - The address of the remote machine.
     |port| - The port of the remote machine.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [sendInfo] where:
   
     |sendInfo| - Result of the send method.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-send."
  ([socket-id data address port #_callback] (gen-call :function ::send &form socket-id data address port)))

(defmacro close
  "Closes the socket and releases the address/port the socket is bound to. Each socket created should be closed after use. The
   socket id is no longer valid as soon at the function is called. However, the socket is guaranteed to be closed only when
   the callback is invoked.
   
     |socketId| - The socket ID.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.udp#method-close."
  ([socket-id #_callback] (gen-call :function ::close &form socket-id)))

(defmacro get-info
  "Retrieves the state of the given socket.
   
     |socketId| - The socket ID.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [socketInfo] where:
   
     |socketInfo| - Object containing the socket information.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-getInfo."
  ([socket-id #_callback] (gen-call :function ::get-info &form socket-id)))

(defmacro get-sockets
  "Retrieves the list of currently opened sockets owned by the application.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [socketInfos] where:
   
     |socketInfos| - Array of object containing socket information.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-getSockets."
  ([#_callback] (gen-call :function ::get-sockets &form)))

(defmacro join-group
  "Joins the multicast group and starts to receive packets from that group. The socket must be bound to a local port before
   calling this method.
   
     |socketId| - The socket ID.
     |address| - The group address to join. Domain names are not supported.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call. A negative value indicates an error.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-joinGroup."
  ([socket-id address #_callback] (gen-call :function ::join-group &form socket-id address)))

(defmacro leave-group
  "Leaves the multicast group previously joined using joinGroup. This is only necessary to call if you plan to keep using the
   socketafterwards, since it will be done automatically by the OS when the socket is closed.Leaving the group will prevent
   the router from sending multicast datagrams to the local host, presuming no other process on the host is still joined to
   the group.
   
     |socketId| - The socket ID.
     |address| - The group address to leave. Domain names are not supported.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call. A negative value indicates an error.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-leaveGroup."
  ([socket-id address #_callback] (gen-call :function ::leave-group &form socket-id address)))

(defmacro set-multicast-time-to-live
  "Sets the time-to-live of multicast packets sent to the multicast group.Calling this method does not require multicast
   permissions.
   
     |socketId| - The socket ID.
     |ttl| - The time-to-live value.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call. A negative value indicates an error.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-setMulticastTimeToLive."
  ([socket-id ttl #_callback] (gen-call :function ::set-multicast-time-to-live &form socket-id ttl)))

(defmacro set-multicast-loopback-mode
  "Sets whether multicast packets sent from the host to the multicast group will be looped back to the host.Note: the behavior
   of setMulticastLoopbackMode is slightly different between Windows and Unix-like systems. The inconsistency happens only
   when there is more than one application on the same host joined to the same multicast group while having different settings
   on multicast loopback mode. On Windows, the applications with loopback off will not RECEIVE the loopback packets; while on
   Unix-like systems, the applications with loopback off will not SEND the loopback packets to other applications on the same
   host. See MSDN: http://goo.gl/6vqbjCalling this method does not require multicast permissions.
   
     |socketId| - The socket ID.
     |enabled| - Indicate whether to enable loopback mode.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call. A negative value indicates an error.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-setMulticastLoopbackMode."
  ([socket-id enabled #_callback] (gen-call :function ::set-multicast-loopback-mode &form socket-id enabled)))

(defmacro get-joined-groups
  "Gets the multicast group addresses the socket is currently joined to.
   
     |socketId| - The socket ID.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [groups] where:
   
     |groups| - Array of groups the socket joined.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-getJoinedGroups."
  ([socket-id #_callback] (gen-call :function ::get-joined-groups &form socket-id)))

(defmacro set-broadcast
  "Enables or disables broadcast packets on this socket.
   
     |socketId| - The socket ID.
     |enabled| - true to enable broadcast packets, false to disable them.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call.
   
   See https://developer.chrome.com/extensions/sockets.udp#method-setBroadcast."
  ([socket-id enabled #_callback] (gen-call :function ::set-broadcast &form socket-id enabled)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-receive-events
  "Event raised when a UDP packet has been received for the given socket.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/sockets.udp#event-onReceive."
  ([channel & args] (apply gen-call :event ::on-receive &form channel args)))

(defmacro tap-on-receive-error-events
  "Event raised when a network error occured while the runtime was waiting for data on the socket address and port. Once this
   event is raised, the socket is paused and no more onReceive events will be raised for this socket until the socket is
   resumed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/sockets.udp#event-onReceiveError."
  ([channel & args] (apply gen-call :event ::on-receive-error &form channel args)))

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
  {:namespace "chrome.sockets.udp",
   :since "33",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "properties", :optional? true, :type "sockets.udp.SocketProperties"}
      {:name "callback", :type :callback, :callback {:params [{:name "create-info", :type "object"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "properties", :type "sockets.udp.SocketProperties"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-paused,
     :name "setPaused",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "paused", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::bind,
     :name "bind",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "port", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::send,
     :name "send",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "address", :type "string"}
      {:name "port", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "send-info", :type "object"}]}}]}
    {:id ::close,
     :name "close",
     :callback? true,
     :params [{:name "socket-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "socket-info", :type "sockets.udp.SocketInfo"}]}}]}
    {:id ::get-sockets,
     :name "getSockets",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "socket-infos", :type "[array-of-sockets.udp.SocketInfos]"}]}}]}
    {:id ::join-group,
     :name "joinGroup",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::leave-group,
     :name "leaveGroup",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-multicast-time-to-live,
     :name "setMulticastTimeToLive",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "ttl", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-multicast-loopback-mode,
     :name "setMulticastLoopbackMode",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "enabled", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::get-joined-groups,
     :name "getJoinedGroups",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "groups", :type "[array-of-strings]"}]}}]}
    {:id ::set-broadcast,
     :name "setBroadcast",
     :since "44",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "enabled", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}],
   :events
   [{:id ::on-receive, :name "onReceive", :params [{:name "info", :type "object"}]}
    {:id ::on-receive-error, :name "onReceiveError", :params [{:name "info", :type "object"}]}]})

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
(ns chromex.ext.system.display (:require-macros [chromex.ext.system.display :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

(defn set-display-properties* [config id info]
  (gen-wrap :function ::set-display-properties config id info))

(defn enable-unified-desktop* [config enabled]
  (gen-wrap :function ::enable-unified-desktop config enabled))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-display-changed* [config channel & args]
  (gen-wrap :event ::on-display-changed config channel args))


(ns hls-viewer.core
    (:require [rum.core :as rum]))

(enable-console-print!)

(rum/defc headerContainer []
  [:div
   [:input]
   [:button "load"]])

(rum/defc wrapper []
  (headerContainer))

(defn init [] (rum/mount (wrapper)
              (.getElementById js/document "app")))
(defn on-js-reload [] init)(init)

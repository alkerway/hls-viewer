(ns hls-viewer.core
  (:require
   [hls-viewer.reqs :as reqs]
   [rum.core :as rum]))

(enable-console-print!)

(defn requestSetLevel [url]
  (println "aylmao"))
(rum/defc headerContainer [url]
  [:div
   [:input {:placeholder "manifest"
            :on-change #(reset! url (.. % -target -value))
            }]
   [:button {
             :on-click #(requestSetLevel url)
             } "load"]])

(rum/defc wrapper []
  (let [url (atom "")]
  (headerContainer url)))

(defn init [] (rum/mount (wrapper)
              (.getElementById js/document "app")))
(defn on-js-reload [] init)(init)

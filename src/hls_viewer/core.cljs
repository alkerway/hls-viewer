(ns hls-viewer.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [hls-viewer.reqs :as reqs]
   [cljs.core.async :refer [<!]]
   [rum.core :as rum]))

(enable-console-print!)

(defn func [] "aylmao")

(defn setManifestText [url textAtom]
  (go (let [manifest (<! (reqs/getManifest url))]
        (reset! textAtom manifest))))


(rum/defc displayContainer < rum/reactive
  [displayText]
  [:div (rum/react displayText)])

(rum/defc headerContainer [url displayText]
  [:div
   [:input {:placeholder "manifest"
            :on-change #(reset! url (.. % -target -value))
            }]
   [:button {
             :on-click #(setManifestText @url displayText)
             } "load"]])

(rum/defc wrapper []
  (let [url (atom "")
        displayText (atom [])]
    [(headerContainer url displayText)
    (displayContainer displayText)]))

(defn init [] (rum/mount (wrapper)
              (.getElementById js/document "app")))
(defn on-js-reload [] init)(init)

(ns hls-viewer.core
  (:require
   [hls-viewer.reqs :as reqs]
   [rum.core :as rum]))

(enable-console-print!)

(defn func [] "aylmao")

(defn setManifestText [urlAtom textAtom]
  (reset! textAtom (reqs/getManifest urlAtom)))


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

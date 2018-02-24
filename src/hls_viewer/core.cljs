(ns hls-viewer.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [hls-viewer.reqs :as reqs]
   [hls-viewer.urls :as urlUtil]
   [cljs.core.async :refer [<!]]
   [rum.core :as rum]))

(enable-console-print!)
(def currentUrl (atom ""))
(def setManifestUrl (atom ""))

(defn setManifestText [url textAtom]
  (go (let [manifest (<! (reqs/getManifest url))]
        (if (some #(re-find #".m3u8" %) manifest)
          (reset! setManifestUrl url))
        (reset! textAtom manifest)
        (reset! currentUrl @setManifestUrl))))

(defn onLineClick [line textAtom]
  (let [destUrl  (urlUtil/constructUrl @currentUrl line)]
    (println @currentUrl)
    (if (re-matches #".+\.m3u8" destUrl)
      (setManifestText destUrl textAtom)
      (reqs/downloadUrl destUrl))))

(rum/defc displayContainer < rum/reactive
  [displayText]
  [:div {:style {:font-size "12px" :padding "20px"}}
   (for [line (rum/react displayText)]
     [:div [:span
            (if (re-matches #".+\.(m3u8|ts|vtt)" line)
              {:on-click #(onLineClick line displayText)
               :style {
                       :color "#BADA55"
                       :cursor "pointer"
                       }}) line]])])
(rum/defc options [textAtom]
  [:div {:style {:font-size "10px"
                 :text-decoration "underline"
                 :user-select "none"}}
   [:span {:style {:cursor "pointer"}
           :on-click #(do (reset! currentUrl @setManifestUrl)
                          (setManifestText @currentUrl textAtom))}
    "Back to Set Level"]])

(rum/defc headerContainer < rum/reactive [displayText]
  [:div {:style {:text-align "center" :padding "10px"}}
   [:input {:style {:border 0
                    :border-bottom "1px solid #C0DEC5"
                    :outline "none"
                    :width "400px"
                    :background-color "inherit"
                    :color "inherit"
                    }
            :value (rum/react currentUrl)
            :placeholder "Enter Manifest"
            :on-change #(reset! currentUrl (.. % -target -value))
            :on-key-up #(if (= "Enter" (.. % -key))
                           (setManifestText @currentUrl displayText))
            }] (options displayText)])

(rum/defc wrapper []
  (let [displayText (atom [])]
    [(headerContainer displayText)
    (displayContainer displayText)]))

(defn init [] (rum/mount (wrapper)
              (.getElementById js/document "app")))
(defn on-js-reload [] init)(init)

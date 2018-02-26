
(ns hls-viewer.reqs
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as cljstr]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defn getManifest [url]
 (go (if (re-matches #"http.*" url)
    (let [response (<! (http/get url {:with-credentials? false}))
          code (:status response)]
          (if (and (>= code 200) (< code 300))
            (cljstr/split-lines  (:body response))
              ["Failed" (str "Status: " code)]))
        ["Invalid Url"])))

(defn downloadUrl [url]
  (.open js/window url))

(defn copyText [text]
  (let [el (js/document.createElement "textarea")]
    (set! (.-value el) text)
    (set! (.-style el) #js {:position "absolute"
                            :left "-9999px"
                            :top "-10px"
                            :padding "0"
                            :margin "0"})
    (.appendChild js/document.body el)
    (.select el)
    (.execCommand js/document "copy")
    (.removeChild js/document.body el)))

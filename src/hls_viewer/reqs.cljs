
(ns hls-viewer.reqs
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as cljstr]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defn getManifest [url]
  (if (re-matches #"http.*" url)
    (go (let [response (<! (http/get url {:with-credentials? false}))
              code (:status response)]
          (if (and (>= code 200) (< code 300))
            (cljstr/split-lines  (:body response))
              ["Failed" (str "Status: " code)])))
        (go ["Invalid Url"])))

(defn downloadUrl [url] "")

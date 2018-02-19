
(ns hls-viewer.reqs
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as cljstr]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defn getManifest [url]
  (if (re-matches #"http.*" url)
    (go (cljstr/split-lines (:body (<!
       (http/get url {:with-credentials? false})))))))

(defn downloadUrl [url] "")

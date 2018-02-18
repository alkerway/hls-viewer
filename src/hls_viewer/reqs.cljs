
(ns hls-viewer.reqs
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defn getManifest [url]
  (if (re-matches #"http.*" url)
    (go (:body (<! (http/get url {:with-credentials? false}))))))

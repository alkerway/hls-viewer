(ns hls-viewer.urls
  (:require [clojure.string :as cljstr]))

(defn constructUrl [manifestUrl text]
  (cond (re-matches #"^http.*" text) text
        (= (first text) "/") "aylmao"
        :else
          (str (cljstr/join "/" (pop (cljstr/split @manifestUrl "/"))) "/" text)))

(ns bk-prices.core
  (:use clojure.pprint)
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as client]))

(defn fetch-url [url]
  (-> url
      java.net.URL.
      html/html-resource))

(defn url-of [symbol]
  (str "https://www.google.co.uk/finance/historical?q=" symbol))

(defn extract-value
  [x]
  (-> x
      :content
      first
      clojure.string/trim
      ))

(defn header-of [html']
  (->> html'
       ((fn [h] (html/select h [:table :tr :th])))
       (map extract-value)
       (map keyword)))

(defn extract-row
  [tr']
  (let [xs (html/select tr' [:td])]
    (map extract-value xs)))

(defn table-rows [html']
  (->>
   (html/select html' [:table.historical_price :tr])))

(defn number-of
  [s]
  (bigdec
   (clojure.string/replace s #"," "")))

(defn parse-datom
  [d]
  (->> d
       (into [])
       (map (fn [[k v]] (if (= k :Date) [k v] [k (number-of v)])))
       (into {})))

(comment
  (parse-datom {:Volume "34543,4545,444"})
  (number-of )
  (clojure.string/replace "345,345,235" #"," "")

  (->> "AAPL"
              history-of
              )
  )

(defn history-of [symbol]
  (let [html' (-> symbol
                  url-of
                  fetch-url)
        header (header-of html')
        rows (->> html'
                  table-rows
                 (map extract-row)
                 rest)
        data (map (fn [row] (zipmap header row)) rows)]
    (map parse-datom data)))

(comment

  (if (= 1 2) "2" 2)

  {:a 1 :b 2}
[[:a 1] [:b 2]]

  (zipmap [:a :b :c] ["a" "b" "c"])

  (def y )



  ;; what er're aiming for -> [{"Jul 21, 2014" 256.8} {"some date" 56}]
  (print (history-of "AAPL")))

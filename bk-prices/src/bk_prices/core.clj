(ns bk-prices.core
  (:gen-class)
  (:require [net.cgrand.enlive-html :as html])
  (:require [clj-http.client :as client])

  (defn fetch-url [url]
    (html/html-resource (java.net.URL. url)))

  (defn url-of [symbol]
    (fetch-url (str "https://www.google.co.uk/finance/historical?p=" symbol)))

  (defn history-of [symbol]
    (map html/text (html/select (fetch-url (str "https://www.google.co.uk/finance/historical?q=" symbol)) [:td]))))

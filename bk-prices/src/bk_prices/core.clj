(ns bk-prices.core
  (:gen-class)
  (:require [net.cgrand.enlive-html :as enlive])
  (:require [clj-http.client :as html])

  (html/get "http://www.google.co.uk/finance/historical?q=NYSE%3ABKW&ei=JPC6U4CsG8TGwAPR6YHQDA")

  (enlive/wrap )


)

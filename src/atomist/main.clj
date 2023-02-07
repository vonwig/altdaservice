(ns atomist.main
  (:require [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn handler [_]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (-> {:version 237}
             (str))})

(defn -main [& args]
  (run-jetty handler {:port 3000}))

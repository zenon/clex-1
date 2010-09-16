(in-ns 'clex.core)
(clojure.core/defonce dev? true)

(ns clex.dev-srv (:use
    [ring.adapter.jetty :only (run-jetty)]
	[clex.core :only (service)]))

(defn start-server [] (run-jetty #'service          
                           {:join? false
                            :port 8080}))

(defonce server (start-server))

(in-ns 'user) 

(defn r [] (require :reload-all 'clex.core))
(defn start [] (.start clex.dev-srv/server))
(defn stop  [] (.stop  clex.dev-srv/server))

(println "UNFO: CLEX initialised.")

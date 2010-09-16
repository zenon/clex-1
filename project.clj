(defproject clex "0.0.1-SNAPSHOT"
  :description "CLEX - The CLojure EXplorer"
  :namespaces [clex.core]
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
				 [ring/ring "0.2.5"]
                 [ring/ring-servlet "0.2.5"]
				 [compojure "0.4.0"]
				 [hiccup "0.2.6"]
				 [org.clojars.choas/clj-gae-datastore "0.1"]
;				 [joda-time "1.6"]
                 ]
  :dev-dependencies [
                 [leiningen/lein-swank "1.2.0-SNAPSHOT"]
; I dont understand why I have to reinclude the following two here. Didn't work without.
				 [compojure "0.4.0"]
				 [hiccup "0.2.6"]
				 [ring/ring-jetty-adapter "0.2.5"]]
  :compile-path "war/WEB-INF/classes"
  :library-path "war/WEB-INF/lib")

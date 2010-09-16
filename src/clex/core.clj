(ns clex.core
	(:gen-class :extends javax.servlet.http.HttpServlet)
	(:use 
		[compojure.core :only (defroutes ANY GET)]
		[hiccup.core :only (html)]
		[hiccup.form-helpers :only ()]
		[hiccup.page-helpers :only (link-to unordered-list doctype xhtml-tag)]
		[ring.util.servlet :only (defservice)]
		[ring.middleware.file :only (wrap-file)]
		[ring.middleware.file-info :only (wrap-file-info)]
		[compojure.route :only (not-found)]
		[ring.middleware.session :only (wrap-session)]))

(defonce dev? false)

(defn shrinkwrap [title & content]
    (html
		(doctype :xhtml-strict)
		(xhtml-tag "en"
		  [:head
		   [:meta {:http-equiv "Content-type"
					:content "text/html; charset=utf-8"}]
		   [:title title]
		   [:link {:href "/clex.css" :rel "stylesheet" :type "text/css"}]]
		   [:body content])))

; we handle exactly one param, still
(defn process-params [session params]
;		(println)
;		(println (str "Session: " session))
;		(flush)
		(if (first params) 
		    (do
;				(println (str "Parameter: " params))
;		        (flush)
                (let [param (first params)]
				   (cond 
		              (params "addpackage") (assoc session :package (params "addpackage")) 
		              (params "rmpackage") (dissoc session :package)
		              (params "addname") (assoc session :name (params "addname")) 
		              (params "rmname") (dissoc session :name)
			          :else (do (println (str "strange parameter: " (first params))) (flush) session))))
			session))

(defn show-method [method]
   (let [info-map (meta method)]
   (str ", " (if (info-map :arglists) (str (info-map :arglists) ", ") "") (info-map :doc) ", from " (info-map :file) ", line " (info-map :line))))

(defn format-mapping [name method session]
    (if (= (session :name) (str name))
	(list (link-to (str "/?rmname=" name) name) (show-method method))
	(link-to (str "/?addname=" name) name)))

(defn format-package [package session]
    (let [package-name (ns-name package)]
	(if (= (session :package) (str package-name))
	(list (link-to (str "/?rmpackage=" package-name) [:span.package-selected package-name]) (unordered-list (map (fn [x] (format-mapping (first x) (second x) session)) (sort (ns-publics package)))))
    (link-to (str "/?addpackage=" package-name) [:span.package package-name]))))
			
(defn explore [session params]
;	(println (str "Session: " session))
;	(println (str "Parameters: " params))
	(let [new-session (if params (process-params session params) session)]
		{:body (shrinkwrap "CLEX"
		  (list [:h1 "CLEX"] [:h2 "The CLojure Explorer"]
			(unordered-list 
		      (doall (map 
				(fn [x] (format-package x new-session)) 
				(sort (fn [x y] (compare (ns-name x) (ns-name y))) (all-ns)))))))
		:session new-session}))

(defn err404 []
	(shrinkwrap "404 - Not found"
		(list [:h1 "You misled me!"][:p "There is no such page! Why?"])))
		
(defroutes routes
;  (GET "/" {session :session} (explore session nil))
  (ANY "/" {session :session params :params} 
           (explore session params))
   (ANY "*" [] (err404)))

(def service
  (let [static-path (if dev? "war/static" "static")]
  (-> #'routes
       (wrap-file static-path)
;      (wrap-file "static")      ; public is the directory. How does this fit into GAE??
;      wrap-file-info          ; when is this called and why? (should be only if wrap-file succedes??)
      wrap-session)))            ; (wrap! clex-routes :session)

  
(defservice service)

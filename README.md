CLEX - The CLojure EXplorer
===========================

This is the first version, as running here: http://a0002.latest.clex-1.appspot.com/

What is it?
-----------

Just a small example Clojure project sutable to run in the Google clouds (aka GAE = Google Application Engine).

The current state is rather bare bone. There is no AJAX, so a click makes the page reload, but I want to keep a simple example. There is no access to the Google Datastore. (I intend to play with Datastore capabilities later, but please don't develop any expectations on my time table.)

Q: Does it have any practical use?
A: Well, um, ah, yes, it lets you explore the Clojure packages it comes with. (Clojure, Conrib, Compojure, Hiccup, and Ring). But back to the technology.

The parts to be used in the REPL are seperated into clex.dev_srv.clj. (see Usage, below).

This project aims at being well documented, so if you dont understand stuff, just open an issue and ask. (I repeat "it aims at" - I dont think I'm done here.)

Prerequisits:

* You should know (and have) Leiningen.
* This is a web application written in Clojure. So HTML and Clojure will come handy.
* More?

Thank You
----------

These blogs helped me do this. Thank you a lot!

http://compojureongae.posterous.com/

http://www.hackers-with-attitude.com/



Usage
------

Locally with Leiningen:

lein deps
lein repl

then, in the REPL

(require 'clex.dev-srv)

This will start a jetty at port 8080.

You now have - in the user namespace - (r) for reload, and (stop) and (stop) as server commands (but the two latter arent usually needed.)

Some Remarks
------------

If you look into the project.clj file, and in the directory structure, you might spot some unusual items.

* :compile-path "war/WEB-INF/classes" in project.clj - tells Leiningen to put the class files into the place that is usual for an web application, and expected by GAE

* :library-path "war/WEB-INF/lib" in project.clj - tells Leiningen to put the jar files into the place that is usual for an web application, and expected by GAE (except the jar files needed for development only)

* the directory "static" is a hack, see section Issues, below.

* two batch files for communication with the Google tools, see At GAE, below.

* Some content of the war/WEB-INF directory is discussed in At GAE, below.

At GAE
------

To put an application into the Google cloud, you 

* need an account there ( https://appengine.google.com/ ), and you

* must have created an application there, and you

* have to download the Google App Engine SDK *for Java*. (If you are logged in at the appengine site, you should see a download link. Last time it had been at the lower left.

* change the application field in war/WEB-INF/appengine-web.xml from clex-1 to the name of the application you created.

You may look into the two batch files about how I tested this small application with GAE development server, and how I put it online. Both are for windows, so your might have to change something.

test.cmd - starts a local application server, listening at port 8080 (you shouldn't run the server from the REPL at the same time)

deploy.cmd - starts the upload tool to put the application into the cloud. Yes, the real thing. Easy. You need your password, of course.

*Attention*: You need to change the version field in war/WEB-INF/appengine-web.xml for *every* upload! As far as I understand, the versions are required to be different for every upload, thats enough (I nevertheless suggest counting up, or similar). 

TODO: Describe web.xml, appengine-web.xml

Issues
------

* Leiningen - I had to enter some libraries into the development dependencies that are in the runtime dependencies already. I dont think this should be needed.

* Ring / static - In clex.core, the wrap-file function checks whether the directory exists. It does this at compile time too, what is obviousely not useful. I had to provide a fake directory "static" in the project root to calm it down.

* Some open questions have been (or should have been) put in the code.

License
-------

Copyright (C) 2010

Distributed under the Eclipse Public License, the same as Clojure.

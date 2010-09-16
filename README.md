CLEX - The CLojure EXplorer
===========================

This is the first version, as running here http://a0002.latest.clex-1.appspot.com/


Usage
------

Locally with Leiningen:

lein deps
lein repl

then, in the REPL

(require 'clex.dev-srv)

This will start a jetty at 8080.


At GAE
------

You may look into the two batch files about how I tested this small application with GAE development server, and how I put it online.


License
-------

Copyright (C) 2010

Distributed under the Eclipse Public License, the same as Clojure.

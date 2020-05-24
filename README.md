[![Clojars Project](https://img.shields.io/clojars/v/wavejumper/clj-polyglot.svg)](https://clojars.org/wavejumper/clj-polyglot)
[![CircleCI](https://circleci.com/gh/wavejumper/clj-polyglot.svg?style=svg)](https://circleci.com/gh/wavejumper/clj-polyglot)

# clj-polygot

Clojure interop into graalvm's polyglot functionality. Requires JDK11+ or a graalvm runtime.

* `clj-polyglot.core` - eval fn + seralization/deserialization fns
* `clj-polyglot.js` - JavaScript context

This library was used to build [tonal-clj](https://github.com/wavejumper/tonal-clj)

**TODO**: extend this library to support other graalvm languages 

# Usage

Consider a JavaScript API like [tonal](https://github.com/tonaljs/tonal#example): 

```javascript
import { Note, Interval, Scale } from "@tonaljs/tonal";
Note.freq("a4").freq; // => 440
```

We can interface with this library like so:

```clojure
(require '[clj-polyglot.core :as poly])
(require '[clj-polyglot.js :as poly.js])

(def tonal-src 
 (slurp "https://cdn.jsdelivr.net/npm/@tonaljs/tonal/browser/tonal.min.js"))

(def ctx
  (poly.js/js-ctx tonal-src))

(def Tonal
  (poly.js/require ctx "Tonal"))

(def api
  (poly.js/import Tonal :Note [:midi :freq]))

(defn freq [note]
  (poly/eval api :freq note))

(freq "A4") ;; => 440
``` 

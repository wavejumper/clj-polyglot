[![Clojars Project](https://img.shields.io/clojars/v/wavejumper/clj-polyglot.svg)](https://clojars.org/wavejumper/clj-polyglot)
[![CircleCI](https://circleci.com/gh/wavejumper/clj-polyglot.svg?style=svg)](https://circleci.com/gh/wavejumper/clj-polyglot)

# clj-polygot

Clojure library for graalvm's polyglot functionality. Requires JDK11+ or a graalvm runtime.

* `clj-polyglot.core` - eval fn + seralization/deserialization fns
* `clj-polyglot.js` - JavaScript context

This library was used to build [tonal-clj](https://github.com/wavejumper/tonal-clj)

**TODO**: extend this library to support other graalvm languages 

# Examples

## tonal

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

(def tonal
  (poly.js/from ctx "Tonal"))

(def api
  (poly.js/import tonal :Note [:midi :freq]))

(defn freq [note]
  (poly/eval api :freq note))

(freq "A4") ;; => 440
``` 

## asciichart 

Consider a JavaScript API like [asciichart](https://github.com/kroitor/asciichart)

```javascript
var asciichart = require ('asciichart');
asciichart.plot([1,2,3]);
```

```clojure
(def asciichart-src 
  (slurp "https://cdn.jsdelivr.net/npm/asciichart@1.5.21/asciichart.js"))

(def ctx 
  (poly.js/js-ctx asciichart-src))

(def asciichart
  (poly.js/from ctx "asciichart"))

(def api 
  (poly.js/import asciichart [:plot]))

(def plot [values]
  (poly/eval api :plot values))

(plot [1 2 3])
```

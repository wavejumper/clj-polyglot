# clj-polygot

Clojure interop into graalvm's polyglot functionality. Requires JDK11+ or a graalvm runtime.

* `clj-polyglot.core` - standard eval + seralization/deserialization fns
* `clj-polyglot.js` - JavaScript context

This library was used to build [tonal-clj](https://github.com/wavejumper/tonal-clj)

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

(def ctx 
  (poly.js/jx-ctx "https://cdn.jsdelivr.net/npm/@tonaljs/tonal/browser/tonal.min.js"))

(def Tonal
  (poly.js/require "Tonal"))

(def api
  (poly.js/import Tonal :Note [:midi :freq])) 

(defn freq [note]
  (poly/execute api :freq note))

(freq "A4") ;; => 60
``` 

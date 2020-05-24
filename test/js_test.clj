(ns js_test
  (:require [clojure.test :refer :all]
            [clj-polyglot.core :as poly]
            [clj-polyglot.js :as poly.js]))

(deftest readme-example
  (let [tonal-src (slurp "https://cdn.jsdelivr.net/npm/@tonaljs/tonal/browser/tonal.min.js")
        ctx       (poly.js/js-ctx tonal-src)
        tonal     (poly.js/require ctx "Tonal")
        api       (poly.js/import tonal :Note [:midi :freq])
        f         #(poly/eval api :freq %)]
    (is (= 440 (f "A4")))))

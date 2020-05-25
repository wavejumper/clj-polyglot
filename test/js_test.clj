(ns js_test
  (:require [clojure.test :refer :all]
            [clj-polyglot.core :as poly]
            [clj-polyglot.js :as poly.js]))

(deftest readme-example
  (let [tonal-src (slurp "https://cdn.jsdelivr.net/npm/@tonaljs/tonal/browser/tonal.min.js")
        ctx       (poly.js/js-ctx tonal-src)
        Tonal     (poly.js/require ctx "Tonal")
        api       (poly.js/import Tonal :Note [:midi :freq :names])
        freq      #(poly/eval api :freq %)
        names     #(poly/eval api :names %)]
    (is (= 440 (freq "A4")))
    ;; test recusion
    (is (= ["F##"] (names ["fx" [""] {"a" {"b" :c}}])))))
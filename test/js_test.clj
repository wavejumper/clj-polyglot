(ns js_test
  (:require [clojure.test :refer :all]
            [clj-polyglot.core :as poly]
            [clj-polyglot.js :as poly.js]
            [clojure.java.io :as io]))

(deftest readme-example
  (let [tonal-src (slurp "https://cdn.jsdelivr.net/npm/@tonaljs/tonal/browser/tonal.min.js")
        ctx       (poly.js/js-ctx tonal-src)
        tonal     (poly.js/from ctx "Tonal")
        api       (poly.js/import tonal :Note [:midi :freq :names])
        freq      #(poly/eval api :freq %)
        names     #(poly/eval api :names %)]
    (is (= 440 (freq "A4")))
    ;; test recusion
    (is (= ["F##"] (names ["fx" [""] {"a" {"b" :c}}])))))

(deftest two-artiy-import
  (let [asciichart-src (slurp "https://cdn.jsdelivr.net/npm/asciichart@1.5.21/asciichart.js")
        ctx            (poly.js/js-ctx asciichart-src)
        asciichart     (poly.js/from ctx "asciichart")
        api            (poly.js/import asciichart [:plot])
        plot           #(poly/eval api :plot %)]

    (is (= "       3.00 ┤ ╭ \n       2.00 ┤╭╯ \n       1.00 ┼╯  "
           (plot [1 2 3])))))
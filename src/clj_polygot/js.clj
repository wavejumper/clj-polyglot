(ns clj-polygot.js
  (:refer-clojure :exclude [import require])
  (:import (org.graalvm.polyglot Context Source Value)))

(defn js-ctx
  [^String src-file]
  (let [ctx (-> (Context/newBuilder (into-array String ["js"]))
                (.allowExperimentalOptions true)
                (.option "js.experimental-foreign-object-prototype" "true")
                (.build))
        src (.build (Source/newBuilder "js" src-file "src.js"))]
    (.eval ctx src)
    ctx))

(defn ^Value require
  [^Context ctx ^String module-name]
  (let [bindings (.getBindings ctx "js")]
    (.getMember bindings module-name)))

(defn import
  [^Value tonal api-name members]
  (let [member (.getMember tonal (name api-name))]
    (into {}
          (map (fn [f]
                 [f (.getMember member (name f))]))
          members)))


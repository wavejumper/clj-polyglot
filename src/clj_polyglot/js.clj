(ns clj-polyglot.js
  (:refer-clojure :exclude [import])
  (:import (org.graalvm.polyglot Context Context$Builder Source Value)))

(defn ^Context$Builder default-builder []
  (-> (Context/newBuilder (into-array String ["js"]))
      (.allowExperimentalOptions true)
      (.option "js.experimental-foreign-object-prototype" "true")))

(defn js-ctx
  ([src-file]
   (js-ctx (default-builder) src-file))

  ([^Context$Builder builder ^String src-file]
   (let [ctx (.build builder)
         src (.build (Source/newBuilder "js" src-file "src.js"))]
     (.eval ctx src)
     ctx)))

(defn ^Value from
  [^Context ctx ^String module-name]
  (let [bindings (.getBindings ctx "js")]
    (.getMember bindings module-name)))

(defn import
  ([^Value member members]
   (into {}
         (map (fn [f]
                [f (.getMember member (name f))]))
         members))
  ([^Value value api-name members]
   (let [member (.getMember value (name api-name))]
     (into {}
           (map (fn [f]
                  [f (.getMember member (name f))]))
           members))))


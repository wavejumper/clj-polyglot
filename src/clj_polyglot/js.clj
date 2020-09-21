(ns clj-polyglot.js
  (:refer-clojure :exclude [import load-file])
  (:import (org.graalvm.polyglot Context Context$Builder Source Value)))

(defn ^Context$Builder default-builder []
  (-> (Context/newBuilder (into-array String ["js"]))
      (.allowExperimentalOptions true)
      (.option "js.experimental-foreign-object-prototype" "true")))

(defn load-string [^Context ctx ^String src-str]
  (let [src (.build (Source/newBuilder "js" src-str "src.js"))]
    (.eval ctx src)
    ctx))

(defn load-file [ctx file]
  (load-string ctx (slurp file)))

(defn ^Context js-ctx
  ([]
   (.build (default-builder)))

  ([src]
   (js-ctx (default-builder) src))

  ([^Context$Builder builder src-str]
   (let [ctx (.build builder)]
     (load-string ctx src-str))))

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


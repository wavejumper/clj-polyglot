(ns clj-polyglot.core
  (:refer-clojure :exclude [eval])
  (:import (org.graalvm.polyglot Value)
           (org.graalvm.polyglot.proxy ProxyArray ProxyObject)))

(defn deserialize-number
  [^Value result]
  (cond
    (.fitsInShort result)
    (.asShort result)

    (.fitsInLong result)
    (.asLong result)

    (.fitsInInt result)
    (.asInt result)

    (.fitsInDouble result)
    (.asDouble result)

    (.fitsInFloat result)
    (.asFloat result)))

(defn deserialize
  [^Value result]
  (cond
    (.isNumber result)
    (deserialize-number result)

    (.isString result)
    (.asString result)

    (.hasArrayElements result)
    (let [n (.getArraySize result)]
      (into [] (map (fn [idx]
                      (deserialize (.getArrayElement result idx)))
                    (range 0 n))))

    (.isNull result)
    nil

    (.isBoolean result)
    (.asBoolean result)

    :else
    result))

(defn serialize-arg [arg]
  (cond
    (keyword? arg)
    (name arg)

    (symbol? arg)
    (name arg)

    (map? arg)
    (ProxyObject/fromMap (into {} (map (fn [[k v]]
                                         [(serialize-arg k) (serialize-arg v)])
                                       arg)))

    (coll? arg)
    (ProxyArray/fromArray (into-array Object (map serialize-arg arg)))

    :else
    arg))

(defn eval
  [api member & args]
  (let [value  (get api member)
        result (.execute ^Value value (into-array Object (map serialize-arg args)))]
    (deserialize result)))
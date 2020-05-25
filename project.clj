(defproject wavejumper/clj-polyglot "1.0.1"
  :description "Clojure API into graal polygamy"
  :url "https://github.com/wavejumper/tonal-clj"

  :dependencies
  [[org.clojure/clojure "1.10.1"]
   [org.graalvm.js/js "20.0.0"]
   [org.graalvm.js/js-scriptengine "20.0.0"]]

  :uberjar {:prep-tasks  ["clean" "compile"]}

  :compile-path
  "target"

  :test-paths
  ["test"]

  :jvm-opts
  ["-XX:+UnlockExperimentalVMOptions" "-XX:+EnableJVMCI"]

  :source-paths
  ["src"])

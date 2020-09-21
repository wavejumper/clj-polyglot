(defproject wavejumper/clj-polyglot "1.0.6"
  :description "Clojure library for graal's polyglot functionality"
  :url "https://github.com/wavejumper/clj-polyglot"

  :dependencies
  [[org.clojure/clojure "1.10.1"]
   [org.graalvm.js/js "20.2.0"]
   [org.graalvm.js/js-scriptengine "20.2.0"]]

  :uberjar {:prep-tasks  ["clean" "compile"]}

  :compile-path
  "target"

  :test-paths
  ["test"]

  :jvm-opts
  ["-XX:+UnlockExperimentalVMOptions" "-XX:+EnableJVMCI"]

  :source-paths
  ["src"])

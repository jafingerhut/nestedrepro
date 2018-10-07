(ns nestedrepro.core
  (:gen-class))

(def depth
  (Integer/parseInt (System/getenv "NESTING_DEPTH")))

(def expr
  (case (System/getenv "NESTED_EXPR_TYPE")
    "try" '(try (catch RuntimeException e e))
    "do" 'do))

(defmacro nested-expr
  []
  (concat `(-> nil) (repeat depth expr)))

(println (format "nesting expr type %s %s times, producing:" expr depth))
(println (macroexpand-1 `(nested-expr)))

(defn -main
  [& args]
  (nested-expr))

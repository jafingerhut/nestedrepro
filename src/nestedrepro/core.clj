(ns nestedrepro.core
  (:gen-class))

(def expr-type (System/getenv "NESTED_EXPR_TYPE"))

(def depth
  (Integer/parseInt (System/getenv "NESTING_DEPTH")))

(def expr
  (case expr-type
    "trynestcatch" 'trynestcatch
    "try" '(try (println "foo") (catch RuntimeException e {:caught-exception e}))
    "do" 'do))

(defn nested-catch-in-try [depth]
  (if (= depth 0)
    `(throw ~'e)
    `(try nil (catch RuntimeException ~'e ~(nested-catch-in-try (dec depth))))))

(defmacro nested-expr
  []
  (case expr-type
    "trynestcatch" (nested-catch-in-try depth)
    (concat `(-> nil) (repeat depth expr))))

(println (format "nesting expr type %s %s times, producing:" expr depth))
(println (macroexpand-1 `(nested-expr)))

(defn -main
  [& args]
  (nested-expr))

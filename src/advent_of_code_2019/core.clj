(ns advent-of-code-2019.core)

(defn abs [n] (max n (- n)))

(defn sgn [n]
  (if (< n 0) -1 (if (> n 0) 1 0)))

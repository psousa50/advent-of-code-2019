(ns advent-of-code-2019.day1
  (:require [advent-of-code-2019.core :as core]))

(def day-input (core/read-one-number-per-line "day1.txt"))

(defn- calculate-fuel [mass] (- (Math/floor (/ mass 3)) 2))

(defn part
  []
  (reduce + (map calculate-fuel day-input)))

(defn- calculate-fuel-recursively
  [fuel]
  (let [next (calculate-fuel fuel)]
    (if (<= next 0)
      0
      (+ next (calculate-fuel-recursively next)))))

(defn part2
  []
  (reduce + (map calculate-fuel-recursively day-input)))

(ns advent-of-code-2019.day1
  (:require [advent-of-code-2019.read-input :as read-input]))

(def day-input (read-input/one-number-per-line "day1.txt"))

(defn- calculate-fuel [mass] (- (Math/floor (/ mass 3)) 2))

(defn part1
  [masses]
  (reduce + (map calculate-fuel masses)))

(defn- calculate-fuel-recursively
  [fuel]
  (let [next (calculate-fuel fuel)]
    (if (<= next 0)
      0
      (+ next (calculate-fuel-recursively next)))))

(defn part2
  [masses]
  (reduce + (map calculate-fuel-recursively masses)))

(part1 day-input)
(part2 day-input)

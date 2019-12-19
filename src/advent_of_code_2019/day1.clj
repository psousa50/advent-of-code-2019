(ns advent-of-code-2019.day1
  (:require [advent-of-code-2019.core :as core]))

(defn calculate-fuel [mass] (- (Math/floor (/ mass 3)) 2))

(defn calculate-total-fuel
  []
  (reduce + (map calculate-fuel (core/read-input "day1.txt"))))

(defn calculate-fuel-recursively
  [fuel]
  (let [next (calculate-fuel fuel)]
    (if (<= next 0)
      0
      (+ next (calculate-fuel-recursively next)))))

(defn calculate-mass [mass]
  (+ mass (calculate-fuel-recursively mass)))

(defn calculate-total-mass
  []
  (reduce + (map calculate-fuel-recursively (core/read-input "day1.txt"))))

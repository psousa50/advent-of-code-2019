(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.core :as core]
            [advent-of-code-2019.intcode-computer :as computer]))

(def day-input (core/read-int-codes "day2.txt"))

(defn- get-program-output
  [v1 v2]
  (let [state (computer/load-program (assoc (assoc day-input 1 v1) 2 v2))]
    (nth (:program (computer/run state)) 0)))

(defn part1
  []
  (get-program-output 12 2))

(defn part2
  []
  (let [[noun verb]
        (first (vec
                (for [noun (range 100) verb (range 100) :when (= (get-program-output noun verb) 19690720)]
                  [noun verb])))]
    (+ (* noun 100) verb)))

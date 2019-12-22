(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.read-input :as read-input]
            [advent-of-code-2019.intcode-computer :as computer]))

(def day-input (read-input/int-codes "day2.txt"))

(defn- get-program-output
  [program v1 v2]
  (let [state (computer/load-program (assoc (assoc program 1 v1) 2 v2))]
    (nth (:program (computer/run state)) 0)))

(defn part1
  [program]
  (get-program-output program 12 2))

(defn part2
  [program]
  (let [[noun verb]
        (first (vec
                (for [noun (range 100) verb (range 100) :when (= (get-program-output program noun verb) 19690720)]
                  [noun verb])))]
    (+ (* noun 100) verb)))

(part1 day-input)
(part2 day-input)

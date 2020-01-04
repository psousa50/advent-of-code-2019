(ns advent-of-code-2019.day5
  (:require [advent-of-code-2019.read-input :as read-input]
            [advent-of-code-2019.intcode-computer :as computer]))

(def day-input (read-input/int-codes "day5.txt"))

(computer/run (computer/load-program day-input) [1])
`(ns advent-of-code-2019.day2
   (:require [advent-of-code-2019.core :as core]))

(defn- get-input-indirect
  [{program :program} position]
  (nth program (nth program position)))

(defn- get-input
  [{program :program} position]
  (nth program position))

(defn- get-op-code
  [{pc :pc program :program}]
  (nth program pc))

(defn- pop2
  [stack]
  (pop (pop stack)))

(defn- fetch-indirect
  [{pc :pc stack :stack :as state}]
  (-> state
      (update :pc inc')
      (assoc :stack (conj stack (get-input-indirect state pc)))))

(defn- fetch-direct
  [{pc :pc stack :stack :as state}]
  (-> state
      (update :pc inc')
      (assoc :stack (conj stack (get-input state pc)))))

(defn- sum
  [{stack :stack :as state}]
  (assoc state :stack (conj (pop2 stack) (+ (first stack) (second stack)))))

(defn- multiply
  [{stack :stack :as state}]
  (assoc state :stack (conj (pop2 stack) (* (first stack) (second stack)))))

(defn- store
  [{stack :stack program :program :as state}]
  (-> state
      (assoc :stack (conj (pop2 stack)))
      (assoc :program (assoc program (first stack) (second stack)))))

(defn- micro
  [state & micro-op]
  (loop [state# state micro-op# micro-op]
    (if micro-op#
      (recur ((first micro-op#) state#) (next micro-op#))
      state#)))


(defn- run-op
  [op-code state]
  (case op-code
    1 (micro state fetch-indirect fetch-indirect sum fetch-direct store)
    2 (micro state fetch-indirect fetch-indirect multiply fetch-direct store)))

(defn intcode-computer
  [program]
  (loop [state {:program program :pc 0 :stack '()}]
    (let [op-code (get-op-code state)]
      (if (= op-code 99)
        state
        (recur (run-op op-code (update state :pc inc)))))))


(def program [1 0 0 3 1 1 2 3 1 3 4 3 1 5 0 3 2 1 6 19 1 9 19 23 2 23 10 27 1 27 5 31 1 31 6 35 1 6 35 39 2 39 13 43 1 9 43 47 2 9 47 51 1 51 6 55 2 55 10 59 1 59 5 63 2 10 63 67 2 9 67 71 1 71 5 75 2 10 75 79 1 79 6 83 2 10 83 87 1 5 87 91 2 9 91 95 1 95 5 99 1 99 2 103 1 103 13 0 99 2 14 0 0])
; (def program [1 0 0 3 99])

(defn get-program-output
  [v1 v2]
  (nth (:program (intcode-computer
                  (assoc (assoc program 1 v1) 2 v2))) 0))

(defn part1
  []
  (get-program-output 12 2))

(defn part2
  []
  (for [noun (range 100) verb (range 100) :when (= (get-program-output noun verb) 19690720)]
    (list noun verb)))

(comment
  (intcode-computer [1 0 0 3 99]))


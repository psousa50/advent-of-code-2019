(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.core :as core]))

(defn- get-indirect-input
  [{program :program} position]
  (nth program (nth program position)))

(defn- get-input
  [{program :program} position]
  (nth program position))

(defn- add
  [{pc :pc :as state}]
  (+ (get-indirect-input state (+ pc 1)) (get-indirect-input state (+ pc 2))))

(defn- multiply
  [{pc :pc :as state}]
  (* (get-indirect-input state (+ pc 1)) (get-indirect-input state (+ pc 2))))

(defn- inc-pc
  [{pc :pc} inc]
  (+ pc inc))

(defn- get-op-code
  [{pc :pc program :program}]
  (nth program pc))

(defn- new-state
  [{program :program} pc position new-value]
  {:program (assoc program position new-value) :pc pc})

(defn- micro
  [state & ops]
(loop [stack '() ops#: ops]
  (case (first (keys ops))
  :fetch-indirect (recur (conj stack (get-indirect-input state (pop stack))) next ops#))))
  :+ (+ )

(defn- run-op
  [op-code state]
  (case op-code
    1 (micro :fetch-indirect  :fetch-indirect :sum :fetch-direct :store)
    1 (new-state state (inc-pc state 4) (get-input state (+ (:pc state) 3)) (add state))
    2 (new-state state (inc-pc state 4) (get-input state (+ (:pc state) 3)) (multiply state))))

(defn intcode-computer
  [program]
  (loop [state {:program program :pc 0}]
    (let [op-code (get-op-code state)]
      (if (= op-code 99)
        state
        (recur (run-op op-code state))))))


(def program [1 0 0 3 1 1 2 3 1 3 4 3 1 5 0 3 2 1 6 19 1 9 19 23 2 23 10 27 1 27 5 31 1 31 6 35 1 6 35 39 2 39 13 43 1 9 43 47 2 9 47 51 1 51 6 55 2 55 10 59 1 59 5 63 2 10 63 67 2 9 67 71 1 71 5 75 2 10 75 79 1 79 6 83 2 10 83 87 1 5 87 91 2 9 91 95 1 95 5 99 1 99 2 103 1 103 13 0 99 2 14 0 0])
; (def program [1 0 0 3 99])

(defn main
  []
  prn
  (nth (:program (intcode-computer
                  (assoc (assoc program 1 12) 2 2))) 0))


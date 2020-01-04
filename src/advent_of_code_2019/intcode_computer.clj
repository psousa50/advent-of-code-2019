(ns advent-of-code-2019.intcode-computer)

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

(defn- push
  [stack value]
  (conj stack value))

(defn- fetch-indirect
  [{pc :pc stack :stack :as state}]
  (-> state
      (update :pc inc)
      (assoc :stack (push stack (get-input-indirect state pc)))))

(defn- fetch-direct
  [{pc :pc stack :stack :as state}]
  (-> state
      (update :pc inc)
      (assoc :stack (push stack (get-input state pc)))))

(defn- sum
  [{stack :stack :as state}]
  (assoc state :stack (conj (pop2 stack) (+ (first stack) (second stack)))))

(defn- multiply
  [{stack :stack :as state}]
  (assoc state :stack (conj (pop2 stack) (* (first stack) (second stack)))))

(defn- store
  [{stack :stack program :program :as state}]
  (-> state
      (assoc :stack (pop2 stack))
      (assoc :program (assoc program (first stack) (second stack)))))

(defn- fetch-input
  [{stack :stack input :input :as state}]
  (-> state
      (assoc :input (next input))
      (assoc :stack (push stack (first input)))))

(defn- store-output
  [{stack :stack output :output :as state}]
  (-> state
      (assoc :stack (pop stack))
      (assoc :output (conj output (first stack)))))

(defn- micro
  [state & micro-op]
  (loop [state# state micro-op# micro-op]
    (if micro-op#
      (recur ((first micro-op#) state#) (next micro-op#))
      state#)))

(defn- fetch-mode
  [mode]
  (case mode
    0 fetch-indirect
    1 fetch-direct))

(defn- decode-op-code
  [op-code]
  {:op-code (mod op-code 100)
   :fetch1 (fetch-mode (mod (int (/ op-code 100)) 10))
   :fetch2 (fetch-mode (mod (int (/ op-code 1000)) 10))
   :fetch3 (fetch-mode (mod (int (/ op-code 10000)) 10))})

(defn- run-op
  [op-code state]
  (let [decoded-op-code (decode-op-code op-code)]
    (case (:op-code decoded-op-code)
      1 (micro state (:fetch1 decoded-op-code) (:fetch2 decoded-op-code) sum fetch-direct store)
      2 (micro state (:fetch1 decoded-op-code) (:fetch2 decoded-op-code) multiply fetch-direct store)
      3 (micro state fetch-input fetch-direct store)
      4 (micro state (:fetch1 decoded-op-code) store-output))))

(defn load-program
  ([program]
   {:program program :pc 0 :stack '()}))

(defn run
  ([state]
   (run state []))
  ([state input]
   (loop [state (assoc state :input input)]
     (let [op-code (get-op-code state)]
       (if (= op-code 99)
         state
         (recur (run-op op-code (update state :pc inc))))))))

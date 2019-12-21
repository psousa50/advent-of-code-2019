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

(defn load-program
  [program]
  {:program program :pc 0 :stack '()})

(defn run
  [state]
  (loop [state state]
    (let [op-code (get-op-code state)]
      (if (= op-code 99)
        state
        (recur (run-op op-code (update state :pc inc)))))))

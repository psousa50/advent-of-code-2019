(ns advent-of-code-2019.day4
  (:require
   [clojure.string]
   [advent-of-code-2019.core :as core]))

(defn- to-digit
  [char]
  (Character/digit char 10))

(defn- split-password
  [password]
  (vec (map to-digit (str password))))

(defn- join-password
  [digits]
  (Integer/parseInt (clojure.string/join digits)))

(defn- always-increase?
  [digits]
  (loop [prev nil d digits]
    (if d
      (if (and prev (< (core/sgn (- (first d) prev)) 0))
        false
        (recur (first d) (next d)))
      true)))

(defn- digit-count
  [digits]
  (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} digits))

(defn- password-is-valid-part1
  [digits]
  (and (always-increase? digits) (-> digits set count (< 6))))

(defn- password-is-valid-part2
  [digits]
  (and (always-increase? digits) (some #(= %1 2) (vals (digit-count digits)))))

(defn- fill-to-end
  [digits pos next-digit]
  (vec (concat (subvec digits 0 pos) (for [_ (range 0 (- 6 pos))] next-digit))))

(defn- next-password
  [digits]
  (loop [digits digits pos 5]
    (let [next-digit (inc (nth digits pos))]
      (if (not= next-digit 10)
        (fill-to-end digits pos next-digit)
        (recur digits (dec pos))))))

(defn part1
  [lower-limit upper-limit]
  (loop [digits (split-password lower-limit) valid-count 0]
    (if (> (join-password digits) upper-limit)
      valid-count
      (recur (next-password digits)
             (if (password-is-valid-part1 digits)
               (inc valid-count) valid-count)))))

(defn part2
  [lower-limit upper-limit]
  (loop [digits (split-password lower-limit) valid-count 0]
    (if (> (join-password digits) upper-limit)
      valid-count
      (recur (next-password digits)
             (if (password-is-valid-part2 digits)
               (inc valid-count) valid-count)))))

(part1 284639 748759)
(part2 284639 748759)

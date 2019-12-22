(ns advent-of-code-2019.day2
  (:require [advent-of-code-2019.core :as core]))

(def day-input (core/read-string-lines "day3.txt"))

(defn- move-to
  [[x y] instruction]
  (let [direction (nth instruction 0) count (Integer/parseInt (str (subs instruction 1)))]
    (case direction
      \R [(+ x count) y]
      \L [(- x count) y]
      \U [x (+ y count)]
      \D [x (- y count)])))

(defn- calc-next-point
  [prev-point path]
  (let [instruction (first path)]
    (when instruction (move-to prev-point instruction))))

(defn- cx
  [segment]
  (first segment))

(defn- cy
  [segment]
  (second segment))

(defn- horizontal?
  [{p1 :p1 p2 :p2}]
  (= (cy p1) (cy p2)))

(defn- vertical?
  [{p1 :p1 p2 :p2}]
  (= (cx p1) (cx p2)))

(defn- between
  [p1 p2 p]
  (and (< (min p1 p2) p) (< p (max p1 p2))))

(defn- calc-intersection
  [s1 s2]
  (let [[horizontal vertical] (if (horizontal? s1) [s1 s2] [s2 s1])]
    (when (and (horizontal? horizontal) (vertical? vertical))
      (let [[ix iy] [(cx (:p1 vertical)) (cy (:p1 horizontal))]]
        (when (and
               (between (cx (:p1 horizontal)) (cx (:p2 horizontal)) ix)
               (between (cy (:p1 vertical)) (cy (:p2 vertical)) iy)) [ix iy])))))


(defn- calc-intersections
  [first-segments second-segments]
  (remove nil? (for [s1 first-segments s2 second-segments]
                 (calc-intersection s1 s2))))


(defn- calc-segments
  [path]
  (loop [prev-point [0 0] path path segments []]
    (let [next-point (calc-next-point prev-point path)]
      (if next-point
        (recur next-point (next path) (conj segments {:p1 prev-point :p2 next-point}))
        segments))))

(defn- distance-to-zero
  [[x y]]
  (+ (core/abs x) (core/abs y)))

(defn- find-closest
  [intersections]
  (apply min (map distance-to-zero intersections)))
  ; (filter #(= (distance-to-zero %) (apply min (map distance-to-zero intersections))) intersections))

(defn part1
  [paths]
  (let [first-segments (calc-segments (first paths)) second-segments (calc-segments (second paths))]
    (->> (calc-intersections first-segments second-segments) (remove #(= [0 0] %)) find-closest)))

(part1 day-input)


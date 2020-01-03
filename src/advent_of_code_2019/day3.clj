(ns advent-of-code-2019.day3
  (:require [advent-of-code-2019.core :as core]
            [advent-of-code-2019.read-input :as read-input]))

(def day-input (read-input/string-lines "day3.txt"))

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


(defn- on-segment
  [{s1 :p1 s2 :p2 :as segment} p]
  (if (horizontal? segment)
    (and (<= (min (cx s1) (cx s2)) (cx p)) (<= (cx p) (max (cx s1) (cx s2))) (= (cy s1) (cy p)))
    (and (<= (min (cy s1) (cy s2)) (cy p)) (<= (cy p) (max (cy s1) (cy s2))) (= (cx s1) (cx p)))))

(defn- length
  [{p1 :p1 p2 :p2 :as segment}]
  (if (horizontal? segment)
    (core/abs (- (cx p1) (cx p2)))
    (core/abs (- (cy p1) (cy p2)))))

(defn- calc-intersection
  [s1 s2]
  (let [[horizontal vertical] (if (horizontal? s1) [s1 s2] [s2 s1])]
    (when (and (horizontal? horizontal) (vertical? vertical))
      (let [intersection [(cx (:p1 vertical)) (cy (:p1 horizontal))]]
        (when (and
               (on-segment horizontal intersection)
               (on-segment vertical intersection)) intersection)))))

(defn- manhattan-distance-to-zero
  [[x y]]
  (+ (core/abs x) (core/abs y)))

(defn- find-closest
  [intersections]
  (apply min (map manhattan-distance-to-zero intersections)))

(defn- calc-segments
  [path]
  (loop [prev-point [0 0] path path segments []]
    (let [next-point (calc-next-point prev-point path)]
      (if next-point
        (recur next-point (next path) (conj segments {:p1 prev-point :p2 next-point}))
        segments))))

(defn- calc-intersections
  [first-segments second-segments]
  (for [s1 first-segments
        s2 second-segments
        :let [intersection (calc-intersection s1 s2)]
        :when intersection]
    intersection))


(defn part1
  [paths]
  (->> (calc-intersections
        (calc-segments (first paths))
        (calc-segments (second paths)))
       (remove #(= [0 0] %))
       find-closest))

(defn- calc-path-length-to-intersection
  [intersection segments]
  (loop [segments segments total-length 0]
    (let [segment (first segments)]
      (if (or (not segment) (on-segment segment intersection))
        (if segment (+ total-length (length {:p1 (:p1 segment) :p2 intersection})) 0)
        (recur (next segments) (+ total-length (length segment)))))))

(defn- calc-path-lengths-to-intersection
  [intersection first-segments second-segments]
  (+
   (calc-path-length-to-intersection intersection first-segments)
   (calc-path-length-to-intersection intersection second-segments)))

(defn part2
  [paths]
  (apply min
         (let [first-segments (calc-segments (first paths))
               second-segments (calc-segments (second paths))
               intersections (remove #(= [0 0] %) (calc-intersections first-segments second-segments))]
           (for [intersection intersections]
             (calc-path-lengths-to-intersection intersection first-segments second-segments)))))

(part1 day-input)
(part2 day-input)

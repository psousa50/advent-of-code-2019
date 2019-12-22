(ns advent-of-code-2019.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn abs [n] (max n (- n)))

(defn read-one-number-per-line
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (doall (map #(Integer/parseInt %) (line-seq rdr)))))

(defn read-int-codes
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (vec (map #(Integer/parseInt %) (str/split (first (vec (line-seq rdr))) #",")))))

(defn read-string-lines
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (vec (map #(str/split %  #",") (vec (line-seq rdr))))))

(prn (first (read-string-lines "day3.txt")))
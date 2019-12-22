(ns advent-of-code-2019.read-input
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn one-number-per-line
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (doall (map #(Integer/parseInt %) (line-seq rdr)))))

(defn int-codes
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (vec (map #(Integer/parseInt %) (str/split (first (vec (line-seq rdr))) #",")))))

(defn string-lines
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (vec (map #(str/split %  #",") (vec (line-seq rdr))))))

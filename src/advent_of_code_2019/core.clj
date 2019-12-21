(ns advent-of-code-2019.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-one-number-per-line
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (doall (map #(Integer/parseInt %) (line-seq rdr)))))

(defn read-int-codes
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (vec (map #(Integer/parseInt %) (str/split (first (vec (line-seq rdr))) #",")))))

(ns advent-of-code-2019.core
  (:require [clojure.java.io :as io]))


(defn read-input
  [filename]
  (with-open [rdr (io/reader (str "resources/inputs/" filename))]
    (doall (map #(Integer/parseInt %) (line-seq rdr)))))

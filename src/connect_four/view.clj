(ns connect-four.view
  (:require [clojure.string :as s]
            [connect-four.util :as u]))

(defn format-row [row]
  (s/join "|" (replace {nil \_ 1 \X 2 \O} row)))

(defn display [board]
  (println (s/join "\n" (map format-row (u/transpose board)))))

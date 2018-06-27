(ns connect-four.util)

(defn transpose [board]
  "Turns the board by 90 degrees. Expects all columns to have the same length."
  (mapv (fn [i] (mapv #(get % i) board))
        (->> (first board) (count) (range)))
  )

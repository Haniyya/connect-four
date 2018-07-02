(ns connect-four.win
  (:require [connect-four.util :as u])
  (:require [connect-four.board :as b]))

(defn verticals [board f]
  "Return the vertial lines of a board.
  f should be either `+` or `-` which will determine the direction
  of the verticals."
  (let [length (count board)
        grouping (fn [ind] (f (mod ind length) (quot ind length)))]
    (->> board
         (u/transpose)
         (flatten)
         (map-indexed vector)
         (group-by (comp grouping first))
         (vals)
         (map flatten)
         (map #(vals (apply hash-map %))) ;; Take every other value
         )
    )
  )

(defn all-windows [board]
  "Returns all 4 element long 'windows' of a board."
  (mapcat (partial partition 4 1)
          (concat (seq board)
                  (u/transpose board)
                  (verticals board +)
                  (verticals board -))
          )
  )

(defn winning-window? [value window]
  (every? (partial = value) window)
  )

(defn win?
  "Checks if there is a winning condition on the board."
  ([board] (or (win? board 1) (win? board 2)))
  ([board value] (some (partial winning-window? value)
                       (all-windows board))
   )
  )

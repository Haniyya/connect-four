(ns connect-four.board
  (:require [connect-four.util :as u]))

(defn new-board
  "If no args are given, returns a 7x6 empty board.
  Width and height may be specified though."
  ([] (new-board 7 6))
  ([width height] (vec (repeat width
                               (vec (repeat height nil)))))
  )

(defn win?
  "Checks if there is a winning condition on the board."
  ([board] (or (win? board 1) (win? board 2)))
  ([board value] (let [col-wins (partition 4 1 board)             ;; Check columns for wins
                       row-wins (partition 4 1 (u/transpose board)) ;; Check rows for wins
                       windows (conj col-wins row-wins)]          ;; Check all windows for wins
                   (some? (some (partial every? (partial = value)) windows))))
  )

(defn move-valid? [col board]
  "Checks whether a given move is valid for a given board."
  (and
    (contains? (vec (range (count board))) col)
    ((comp nil? first) (get board col)))
  )

(defn place-stone [value col]
  "Let a stone fall into the column. Automatically lands on the bottom most row."
  (let [parts (split-with nil? col)
        top (rest (first parts))
        bottom (second parts)]
    (flatten [top value bottom]))
  )

(defn place-in-board [value col board]
  "Put a value (depending on player), into a column number in a board. Returns a new board."
  (assoc board col (place-stone value (get board col))))

(defn place-stone [value col]
  "Let a stone fall into the column. Automatically lands on the bottom most row."
  (let [parts (split-with nil? col)
        top (rest (first parts))
        bottom (second parts)]
    (flatten [top value bottom]))
  )

(defn place-in-board [value col board]
  "Put a value (depending on player), into a column number in a board. Returns a new board."
  (assoc board col (place-stone value (get board col))))

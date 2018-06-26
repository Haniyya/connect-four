(ns connect-four.core
  (:gen-class)
  (:require [clojure.string :as s]))

(defn transpose [board]
  "Turns the board by 90 degrees. Expects all columns to have the same length."
  (mapv (fn [i] (mapv #(get % i) board))
        (->> (first board) (count) (range)))
  )

(defn new-board
  "If no args are given, returns a 7x6 empty board.
  Width and height may be specified though."
  ([] (new-board 7 6))
  ([width height] (vec (repeat width
                               (vec (repeat height nil)))))
  )

(defn format-row [row]
  (s/join "|" (replace {nil \_ 1 \X 2 \O} row)))

(defn display [board]
  (println (s/join "\n" (map format-row (transpose board)))))

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

(defn win?
  "Checks if there is a winning condition on the board."
  ([board] (or (win? board 1) (win? board 2)))
  ([board value] (let [col-wins (partition 4 1 board)             ;; Check columns for wins
                       row-wins (partition 4 1 (transpose board)) ;; Check rows for wins
                       windows (conj col-wins row-wins)]          ;; Check all windows for wins
                   (some? (some (partial every? (partial = value)) windows))))
  )

(defn play []
  "The main game loop. Display board, ask for input, mutate board, rinse, repeat."
  (do (println "Hey there. Play a round.")
      (loop [board (new-board)
             player 1]
        (do (display board)
            (println (str "Player " player ", please enter a column to drop your stone."))
            (let [input (read-string (read-line))]
              (cond
                (move-valid? input board) (recur (place-in-board player input board) (get {1 2 2 1} player))
                :else (do (println "Invalid input. Try again.")
                          (recur board player))
                ))
            )
        )))

(defn -main
  [& args]
  (play))

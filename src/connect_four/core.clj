(ns connect-four.core
  (:gen-class)
  (:require [connect-four.board :as b]
            [connect-four.view :as view]
            [connect-four.win :as win]))

(defn play []
  "The main game loop. Display board, ask for input, mutate board, rinse, repeat."
  (do (println "Hey there. Play a round.")
      (loop [board (b/new-board)
             player 1]
        (do (view/display board)
            (when (win/win? board player)
              (println "You won! Yeah!")
              (System/exit 0))
            (println (str "Player " player ", please enter a column to drop your stone."))
            (let [input (read-string (read-line))]
              (cond
                (b/move-valid? input board) (recur (b/place-in-board player input board) (get {1 2 2 1} player))
                :else (do (println "Invalid input. Try again.") (recur board player)))
              )
            )
        )))

(defn -main
  [& args]
  (play))

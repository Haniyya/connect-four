(ns connect-four.win-test
  (:require [clojure.test :refer :all]
            [connect-four.board :refer :all]
            [connect-four.win :refer :all]))

(deftest small-verticals
  (testing "A small matrix returns the correct verticals"
    (let [board (vector [1 2] [3 4])]
      (is (= (verticals board +)
             (vector [1] [3 2] [4])))
      )
    )
  )

(deftest small-verticals
  (testing "A small matrix returns the correct verticals the other way around"
    (let [board (vector [1 2] [3 4])]
      (is (= (verticals board -)
             (vector [1 4] [3] [2])))
      )
    )
  )

(deftest windows-test
  (testing "A 2 x 2 matrix should have 0 windows"
    (is (= 0 (count (all-windows (vector [1 2] [3 4]))))
        )
    )
  )

(deftest windows-medium-test
  (testing "A 4 x 4 matrix should have 18 windows"
    (is (= 10 (count (all-windows (new-board 4 4)))))
    )
  )

(deftest small-winner
  (testing "A small 4x4 can win."
    (let [board [[nil nil nil 1]
                 [nil nil nil 1]
                 [nil nil nil 1]
                 [nil nil nil 1]]
          tr-board [[1 1 1 1]
                    [nil nil nil nil]
                    [nil nil nil nil]
                    [nil nil nil nil]]
          ]
      (are [b] (true? (win? b))
           board tr-board)
      )
    )
  )

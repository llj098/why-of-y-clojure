
;; the one way of derive Y from paper: Why of Y ( http://www.dreamsongs.com/Files/WhyOfY.pdf )


(let [g (fn[h] 
          (fn [n] (if (< n 2) 1 (* n ((h h) (- n 1))))))]
  ((g g) 10))


;; abstract the `if` expression over the `(h h)` and n

(let [g (fn [h]
          (fn [n]
            (let [f (fn [q n] (if (< n 2) 1 (* n (q (- n 1)))))]
              (f (h h) n))))]
  ((g g) 10))


;; curry the definition of function `f`

(let [g (fn [h]
          (fn [n]
            (let [f (fn [q]
                      (fn [n]
                        (if (< n 2) 1 (* n (q (- n 1))))))]
              ((f (h h)) n))))]
  ((g g) 10))


;; function `f` does not need to be deeply embedded in the function `g`

(let [f (fn [q] (fn [n] (if (< n 2) 1 (* n (q (- n 1))))))]
  (let [g (fn [h] (fn [n] ((f (h h)) n)))]
    ((g g) 10)))


;; `f` is once again be parameterized form of factorial; we can abstract this expression over f, which produces Y:

(def Y (fn [f]
         (let [g (fn [h] (fn [x] ((f (h h)) x)))]
           (g g))))



((Y (fn [q] (fn [n] (if (< n 2) 1 (* n (q (- n 1))))))) 10)



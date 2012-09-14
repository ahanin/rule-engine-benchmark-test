(import foo.bar.model.*)
(import foo.bar.model.Greeting)
(import foo.bar.model.Salutation)
(import foo.bar.model.Gender)
(import foo.bar.model.MaritalStatus)

(deftemplate Case          (declare (from-class Case)))
(deftemplate Customer      (declare (from-class Customer)))
(deftemplate Greeting      (declare (from-class Greeting)))
(deftemplate Salutation    (declare (from-class Salutation)))
(deftemplate Gender        (declare (from-class Gender)))

(defrule greeting-good_morning
    (Case {hourOfDay >= 0 && hourOfDay < 12 })
    =>
    (add (Greeting.GOOD_MORNING))
)

(defrule greeting-good_afternoon
    (Case {hourOfDay >= 12 && hourOfDay < 18 })
    =>
    (add (Greeting.GOOD_AFTERNOON))
)

(defrule greeting-good_evening
    (Case {hourOfDay >= 18 && hourOfDay < 23 })
    =>
    (add (Greeting.GOOD_EVENING))
)

(defrule greeting-good_night
    (Case {hourOfDay >= 23 && hourOfDay <= 24 })
    =>
    (add (Greeting.GOOD_NIGHT))
)

(defrule add_customer
    (Case (customer ?customer))
    =>
    (add ?customer)
)

(defrule salutation_mr
    (Customer {gender == (Gender.MALE)})
    =>
    (add (Salutation.MR))
)

(defrule salutation_ms
    (Customer {gender == (Gender.FEMALE) && maritalStatus == (MaritalStatus.SINGLE)})
    =>
    (add (Salutation.MS))
)

(defrule salutation_mrs
    (Customer {gender == (Gender.FEMALE) && maritalStatus == (MaritalStatus.MARRIED)})
    =>
    (add (Salutation.MRS))
)

(defrule say_hello
    (Case (customer ?customer))
    ?g <- (Greeting)
    ?s <- (Salutation)
    =>
    (printout t ?g.literal ", " ?s.literal " " ?customer.name crlf )
)
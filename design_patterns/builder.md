# Builder 

Same idea as Iterator: encapsulate creation of a class in a builder object. Client asks builder object to create instance of the desired class.

## Class list


Example for a vacation planner:

* AbstractBuilder (interface)
  * public AbstractBuilder buildDay() 
  * public AbstractBuilder addHotel()
  * public AbstractBuilder addResevervatin()
* VacationBuilder implements AbstractBuilder
  * private Vaction vacation;
  * //implements the methods
  * VacationBuilder getVacationPlanner() { return this.vacaction; }

  Client would use it like:
  ```
  AbstractBuilder builder = new VacationBuilder()
  Vacation vacation = builder.buildDay(1)
         .addHote("hilton")
         .addReservation("blah")
         .getVacationPlanner
```

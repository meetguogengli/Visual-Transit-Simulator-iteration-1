package edu.umn.cs.csci3081w.project.model;

import java.util.List;

public class PassengerLoader {

  /**
   * Loads a passenger.
   * This function returns the number of passengers added to the bus.
   * Currently this is either one or zero: either passenger was added or they
   * weren't. This allows us to change the Passenger Loader without having to change
   * the Bus.
   *
   * @param maxPass max number of passengers in bus
   * @param newPassenger new passenger to be laoded
   * @param passengers list of passengers
   * @return the true if passengers added, false otherwise
   */

  public boolean loadPassenger(Passenger newPassenger, int maxPass, List<Passenger> passengers) {
    // Normally would use a boolean, but for extensibility,
    // using count of those added_passenger
    // Currently, only one could be added, so possible values are 0 or 1.
    int addedPassengers = 0;
    if (passengers.size() >= maxPass) {
      return false;
    } else if (passengers.size() < maxPass) {
      passengers.add(newPassenger);
      newPassenger.getOnBus();
      addedPassengers = 1;
    }
    return true;
  }
}

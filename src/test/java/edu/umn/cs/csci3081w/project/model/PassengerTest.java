package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerTest {

  /**
   * Setup operations before each runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }
  /**
   * Create a bus with outgoing and incoming routes and three stops per route.
   */
  public Bus createBus() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    Stop stop3 = new Stop(2, 44.975392, -93.226632);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    stopsIn.add(stop3);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    distancesIn.add(0.008631);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.025);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRouteIn = new Route("testRouteIn", stopsIn, distancesIn, 3, generatorIn);
    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop3);
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.008631);
    distancesOut.add(0.008784);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    return new Bus("TestBus", testRouteOut, testRouteIn, 5, 1);
  }
  /**
   * Test after using constructor.
   */
  @Test
  public void testConstructorNormal(){
    Passenger passenger=new Passenger(1,"Goldy");
    assertEquals(1, passenger.getDestination());
    //assertEquals("Goldy",passenger.getName());
  }
  /**
   * Test passenger's name after name generation.
   */
  @Test
  public void testNameGeneration(){
    String generatedName=PassengerFactory.nameGeneration();
    assertEquals("Goldy",generatedName);
  }
  /**
   * Test the status for passenger on the bus-> not on the bus.
   */
  @Test
  public void testIsOnBus1(){
    Passenger passenger=new Passenger(1,"Goldy");
    boolean status=passenger.isOnBus();
    assertEquals(false,status);
  }
  /**
   * Test the status for passenger on the bus->on the bus.
   */
  @Test
  public void testIsOnBus2(){
    Bus testBus=createBus();
    Passenger passenger=new Passenger(1,"Goldy");
    testBus.getNextStop().addPassengers(passenger);
    testBus.getNextStop().loadPassengers(testBus);
    boolean status=passenger.isOnBus();
    assertEquals(true,status);
  }



}

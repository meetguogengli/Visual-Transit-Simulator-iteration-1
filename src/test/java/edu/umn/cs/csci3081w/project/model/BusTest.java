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

public class BusTest {

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
    probabilitiesIn.add(0.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(0.0025);
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
    probabilitiesOut.add(0.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(0.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    return new Bus("TestBus", testRouteOut, testRouteIn, 5, 1);
  }

  /**
   * Test state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
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
    Bus bus = new Bus("TestBus", testRouteOut, testRouteIn, 5, 1);
    assertEquals("TestBus", bus.getName());
    assertEquals(testRouteIn, bus.getIncomingRoute());
    assertEquals(testRouteOut, bus.getOutgoingRoute());
    assertEquals(5, bus.getCapacity());
    //assertEquals(2,bus.getIncomingRoute().getTotalRouteDistance());
    //needs modification
  }

  /**
   * Test for reporting bus without passenger.
   */
  @Test
  public void testReportWithout() {
    try {
      Bus bus1 = createBus();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      bus1.move();
      bus1.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Bus Info Start####" + System.lineSeparator()
              + "Name: TestBus" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Distance to next stop: 0.008784" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Bus Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }
  /**
   * Test for reporting bus with passenger.
   */
  @Test
  public void testReportWith() {
    try {
      Bus bus1 = createBus();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      Passenger passenger3 = new Passenger(2, "Gophq");
      bus1.move();
      bus1.loadPassenger(passenger3);
      bus1.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.008784" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 1" + System.lineSeparator()
                      + "####Passenger Info Start####" + System.lineSeparator()
                      + "Name: Gophq" + System.lineSeparator()
                      + "Destination: 2" + System.lineSeparator()
                      + "Total wait: 1" + System.lineSeparator()
                      + "Wait at stop: 0" + System.lineSeparator()
                      + "Time on bus: 1" + System.lineSeparator()
                      + "####Passenger Info End####" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }
  /**
  * Test for whether the trip is complete.
  */
  @Test
  public void testIsTripComplete() {
    Bus bus1 = createBus();
    bus1.move();
    assertEquals(false, bus1.isTripComplete());
    bus1.move();
    bus1.move();
    assertEquals(true, bus1.isTripComplete());
  }

  /**
   * Test for loading passenger within or beyond bus capacity when stop is empty .
   */
  @Test
  public void testLoadPassengerEmptyStopWithinOrBeyondCapacity() {
    Bus bus1 = createBus();
    //Passenger passenger0=new Passenger(null);
    Passenger passenger1 = new Passenger(2, "Gopher");
    Passenger passenger2 = new Passenger(2, "Gophm");
    Passenger passenger3 = new Passenger(2, "Gophq");
    Passenger passenger4 = new Passenger(2, "Gophk");
    Passenger passenger5 = new Passenger(2, "Gophl");
    Passenger passenger6 = new Passenger(2, "Gophy");
    assertEquals(true, bus1.loadPassenger(passenger1));
    bus1.loadPassenger(passenger2);
    bus1.loadPassenger(passenger3);
    bus1.loadPassenger(passenger4);
    bus1.loadPassenger(passenger5);
    //beyond maxCapacity
    assertEquals(false, bus1.loadPassenger(passenger6));
  }
  /**
   * Test for loading passenger within or beyond bus capacity when stop is empty .
   */
  @Test
  public void testLoadPassengerNonEmptyStopWithinCapacity() {
    Bus bus1 = createBus();
    //Passenger passenger0=new Passenger(null);
    Passenger passenger1 = new Passenger(2, "Gopher");
    Passenger passenger2 = new Passenger(2, "Gophm");
    Passenger passenger3 = new Passenger(2, "Gophq");
    Passenger passenger4 = new Passenger(2, "Gophk");
    Passenger passenger5 = new Passenger(2, "Gophl");
    Passenger passenger6 = new Passenger(2, "Gophy");
    bus1.getNextStop().addPassengers(passenger1);
    bus1.getNextStop().addPassengers(passenger2);
    bus1.getNextStop().addPassengers(passenger3);
    int passengersLoaded = bus1.getNextStop().loadPassengers(bus1);
    assertEquals(3,passengersLoaded);
    assertEquals(true, bus1.loadPassenger(passenger6));
  }
  /**
   * Test for whether the bus moves.
   */
  @Test
  public void testMove() {
    Bus bus = createBus();
    List<Passenger> pasList = new ArrayList<Passenger>();
    Passenger passenger1 = new Passenger(2, "Gopher");
    Passenger passenger2 = new Passenger(2, "Gophm");
    pasList.add(passenger1);
    pasList.add(passenger2);
    bus.loadPassenger(passenger1);
    bus.move();
    bus.loadPassenger(passenger2);
    bus.move();
    assertEquals(0,bus.getNextStop().getId());
    assertEquals(true, bus.move());
    assertEquals(2,bus.getNextStop().getId());
    assertEquals(2, passenger1.getTotalWait());
    assertEquals(3, passenger2.getTotalWait());
  }

  /**
   * Test for update bus data.
   */
  @Test
  public void testUpdateBusData() {
      Bus bus1 = createBus();
    Passenger passenger1 = new Passenger(1, "Bopher");
    Passenger passenger2 = new Passenger(1, "Qopher");
    Passenger passenger3 = new Passenger(1, "Nopher");
    //System.out.println(bus1.getBusData().getPosition().getXcoordLoc());
    //System.out.println(bus1.getBusData().getPosition().getYcoordLoc());
    //System.out.println(bus1.getBusData().getPosition().getXcoordLoc());
    //System.out.println(bus1.getBusData().getPosition().getYcoordLoc());
    bus1.loadPassenger(passenger1);
    bus1.loadPassenger(passenger2);
    bus1.loadPassenger(passenger3);
    //System.out.println(bus1.getNextStop().getId()); //-? print out: 2. so this stop is 1
    //bus1.update();//MOVE AND UPDATE
    bus1.move();
    bus1.updateBusData();
    //System.out.println("PASSENGERS"+bus1.getBusData().getNumPassengers()); //->3
    //System.out.println(bus1.getNextStop().getId()); //-? print out: 2. so this stop is 1
    assertEquals(3, bus1.getBusData().getNumPassengers());
    assertEquals(5, bus1.getBusData().getCapacity());
    assertEquals(44.97358, bus1.getBusData().getPosition().getXcoordLoc());
    assertEquals(0,bus1.getNextStop().getId());
    //bus1.update();
    bus1.move();
    bus1.updateBusData();
    assertEquals(3, bus1.getBusData().getNumPassengers());
    assertEquals(5, bus1.getBusData().getCapacity());
    assertEquals(44.972392, bus1.getBusData().getPosition().getXcoordLoc());
    assertEquals(1,bus1.getNextStop().getId());

    //System.out.println("PASSENGERS"+bus1.getBusData().getNumPassengers()); //->3
    //System.out.println(bus1.getNextStop().getId());
    //bus1.update();
    bus1.move();
    bus1.updateBusData();
    //System.out.println("PASSENGERS"+bus1.getBusData().getNumPassengers()); //->3
    //System.out.println(bus1.getNextStop().getId());
    //assertEquals(44.975392,bus1.getBusData().getPosition().getXcoordLoc());
    //assertEquals(-93.226632,bus1.getBusData().getPosition().getYcoordLoc());
    assertEquals(0, bus1.getBusData().getNumPassengers());
    assertEquals(44.97358, bus1.getBusData().getPosition().getXcoordLoc());
    assertEquals(-93.235071, bus1.getBusData().getPosition().getYcoordLoc());
    assertEquals(2,bus1.getNextStop().getId());

    //assertEquals(1,bus1.getBusData().getNumPassengers());
  }

  /**
   * Test for update bus.
   */
  @Test
  public void testUpdate() {
    Bus bus1 = createBus();
    Passenger passenger3 = new Passenger(1, "Nopher");
    bus1.getNextStop().addPassengers(passenger3);
    bus1.getNextStop().loadPassengers(bus1);
    bus1.update();
    assertEquals(0, bus1.getNextStop().getId());
    //System.out.println(bus1.getBusData().getPosition().getXcoordLoc());
    assertEquals(44.97358, bus1.getBusData().getPosition().getXcoordLoc());
    bus1.update();
    assertEquals(1, bus1.getNextStop().getId());
    assertEquals(44.972392, bus1.getBusData().getPosition().getXcoordLoc());
    //assertEquals("TestBus",bus1.getBusData().getId());
    assertEquals(5, bus1.getBusData().getCapacity());
    assertEquals(1, bus1.getBusData().getNumPassengers());

  }
}

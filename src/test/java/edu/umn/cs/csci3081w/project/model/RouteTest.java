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

public class RouteTest {

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
   * Test states after constructor.
   */
  @Test
  public void testConstructorNormal() {
    Bus testBus = createBus();
    //Passenger passenger1=new Passenger(1,"Goldy");
    //testBus.getNextStop().addPassengers(passenger1);
    //testBus.getNextStop().loadPassengers(testBus);
    //passenger1.pasUpdate();
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    //System.out.println(distancesIn);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    PassengerGenerator testGenIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRoute1 = new Route("testIn", stopsIn, distancesIn, 2, testGenIn);
    testRoute1.toNextStop();
    assertEquals("testIn", testRoute1.getName());
    assertEquals(stopsIn, testRoute1.getStops());
    assertEquals(1, testRoute1.getDestinationStopIndex());
    assertEquals(distancesIn.get(0), testRoute1.getTotalRouteDistance());
    //how to test random passenger generator??
    //System.out.println(testGenIn);
    //assertEquals(3,testGenIn);
  }

  /**
   * Test shallow copy.
   */
  @Test
  public void testShallowCopy() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    //System.out.println(distancesIn);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    PassengerGenerator testGenIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRoute1 = new Route("testIn", stopsIn, distancesIn, 2, testGenIn);
    Route copiedRoute = testRoute1.shallowCopy();
    //assertEquals(false,testRoute1==copiedRoute);
    assertEquals(copiedRoute.getName(), testRoute1.getName());
    assertEquals(copiedRoute.getTotalRouteDistance(), testRoute1.getTotalRouteDistance());
    assertEquals(copiedRoute.getRouteData(), testRoute1.getRouteData());
  }

  /**
   * Test for generate new passenger.
   */
  @Test
  public void testGenerateNewPassenger() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    Stop stop3 = new Stop(0, 44.972392, -93.243774);
    Stop stop4 = new Stop(1, 44.973580, -93.235071);
    List<Stop> stopsIn1 = new ArrayList<Stop>();
    stopsIn1.add(stop3);
    stopsIn1.add(stop4);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    //System.out.println(distancesIn);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.7);
    //System.out.println(distancesIn1);
    List<Double> probabilitiesIn1 = new ArrayList<Double>();
    probabilitiesIn1.add(.7);
    PassengerGenerator testGenIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    PassengerGenerator testGenIn2 = new RandomPassengerGenerator(probabilitiesIn1, stopsIn1);
    Route testRoute1 = new Route("testIn", stopsIn, distancesIn, 2, testGenIn);
    Route testRoute2 = new Route("testIn1", stopsIn1, distancesIn, 2, testGenIn2);
    //System.outfail(.println(testRoute1.generateNewPassengers());
    assertEquals(testRoute1.generateNewPassengers(), testRoute2.generateNewPassengers());
  }

  /**
   * Testing update for route data.
   */
  @Test
  public void testUpdate() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    //System.out.println(distancesIn);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    PassengerGenerator testGenIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRoute1 = new Route("testIn", stopsIn, distancesIn, 2, testGenIn);
    testRoute1.update();
    //System.out.println(testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait());
    //System.out.println(testRoute1.getStops().get(0).getPassengers().get(0).getDestination());
    assertEquals(1, testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait());
    assertEquals(1, testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait());
    assertEquals(stopsIn.get(0).getLongitude(),
        testRoute1.getRouteData().getStops().get(0).getPosition().getXcoordLoc());
    assertEquals(stopsIn.get(0).getLatitude(),
        testRoute1.getRouteData().getStops().get(0).getPosition().getYcoordLoc());
    assertEquals(stopsIn.get(0).getNumPassengersPresent(),
        testRoute1.getRouteData().getStops().get(0).getNumPeople());
    assertEquals(Integer.toString(stopsIn.get(0).getId()),
        testRoute1.getRouteData().getStops().get(0).getId());
    /*try{
    final Charset charset=StandardCharsets.UTF_8;
    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
    PrintStream testStream = new PrintStream(outputStream,true,charset.name());
    testRoute1.getStops().get(0).getPassengers().get(0).report(testStream);
    outputStream.flush();
    String data=new String(outputStream.toByteArray(),charset);
    testStream.close();
    outputStream.close();
    //System.out.println(data);
    assertEquals(1,testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait());
    assertEquals(stopsIn.get(0).getLongitude(),
    testRoute1.getRouteData().getStops().get(0).getPosition().getXcoordLoc());
    assertEquals(stopsIn.get(0).getLatitude(),
    testRoute1.getRouteData().getStops().get(0).getPosition().getYcoordLoc());
    assertEquals(stopsIn.get(0).getNumPassengersPresent(),
    testRoute1.getRouteData().getStops().get(0).getNumPeople());
    assertEquals(Integer.toString(stopsIn.get(0).getId()),
    testRoute1.getRouteData().getStops().get(0).getId());
    } catch(IOException ioe){
      fail();
    }
    */


    //System.out.println(testRoute1.getRouteData().getId());
    //System.out.println("sdadasdasdasd/n");
    //System.out.println(testRoute1.getName());
    //System.out.println(testRoute1.getRouteData().getStops().get(0).getId());

    //Passenger p1=testRoute1.getStops().get(0).getPassengers().get(0);

    //testRoute1.update();
    //System.out.println(p1);
    // int a=testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait();
    // testRoute1.update();
    //int b=testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait();
    //System.out.println(a);
    //System.out.println(b);

  }

  /**
   * Test for route with only one stop.
   */
  @Test
  public void testReport0() {
    try {
      Stop stop1 = new Stop(0, 44.972392, -93.243774);
      List<Stop> stopsIn = new ArrayList<Stop>();
      stopsIn.add(stop1);
      List<Double> distancesIn = new ArrayList<Double>();
      //System.out.println(distancesIn);
      List<Double> probabilitiesIn = new ArrayList<Double>();
      PassengerGenerator testGenIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
      Route testRoute1 = new Route("testIn", stopsIn, distancesIn, 1, testGenIn);
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testRoute1.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Route Info Start####" + System.lineSeparator()
              + "Name: testIn" + System.lineSeparator()
              + "Num stops: 1" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test for report.
   */
  @Test
  public void testReport1() {
    try {
      Stop stop1 = new Stop(0, 44.972392, -93.243774);
      Stop stop2 = new Stop(1, 44.973580, -93.235071);
      List<Stop> stopsIn = new ArrayList<Stop>();
      stopsIn.add(stop1);
      stopsIn.add(stop2);
      List<Double> distancesIn = new ArrayList<Double>();
      distancesIn.add(0.008784);
      //System.out.println(distancesIn);
      List<Double> probabilitiesIn = new ArrayList<Double>();
      probabilitiesIn.add(.15);
      probabilitiesIn.add(0.3);
      PassengerGenerator testGenIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
      Route testRoute1 = new Route("testIn", stopsIn, distancesIn, 2, testGenIn);
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testRoute1.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Route Info Start####" + System.lineSeparator()
              + "Name: testIn" + System.lineSeparator()
              + "Num stops: 2" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test for passenger is not at an end.
   */
  @Test
  public void testIsNotAtEnd() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    //testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait()
    testRoute.update();
    //System.out.println(testRoute.getStops().get(0).getPassengers().get(0).getDestination());
    assertEquals(false, testRoute.isAtEnd());
    //System.out.println(testRoute.isAtEnd());
  }

  /**
   * Test for passenger is at an end.
   */
  @Test
  public void testIsAtEnd() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 2, generatorIn);
    //testRoute1.getStops().get(0).getPassengers().get(0).getTotalWait()
    testRoute.toNextStop();
    testRoute.toNextStop();
    assertEquals(true, testRoute.isAtEnd());
  }

  /**
   * Test for previous stop.
   */
  @Test
  public void testPrevStop() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    testRoute.toNextStop();
    testRoute.toNextStop();
    int index1 = testRoute.getDestinationStopIndex();
    //System.out.println(index1);
    int prevStopsId = testRoute.prevStop().getId();
    //System.out.println(testRoute.prevStop().getId());
    assertEquals(index1, prevStopsId + 1);
    //testRoute.prevStop();

  }

  /**
   * Test for toNextStop.
   */
  @Test
  public void testToNextStop() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    testRoute.update();
    int testStop1 = testRoute.getDestinationStopIndex();
    //System.out.println(testRoute.getDestinationStopIndex());
    testRoute.toNextStop();
    int testStop2 = testRoute.getDestinationStopIndex();
    //System.out.println(testRoute.getDestinationStopIndex());
    assertEquals(testStop1 + 1, testStop2);
  }

  /**
   * Test for getDestinationStop .
   */
  @Test
  public void testGetDestinationStop() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    testRoute.toNextStop();
    testRoute.toNextStop();
    int stopIndex = testRoute.getDestinationStopIndex();
    assertEquals(2, stopIndex);
  }

  /**
   * Test for get total distance.
   */
  @Test
  public void testGetTotalRouteDistance() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    double distance = testRoute.getTotalRouteDistance();
    assertEquals((0.008784 + 0.008631), distance);
  }

  /**
   * Test for get next stop distance.
   */
  @Test
  public void testNextStopDistance() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    testRoute.update();
    double noDistance = testRoute.getNextStopDistance();
    assertEquals(0.0, noDistance);
    testRoute.toNextStop();
    double firstDistance = testRoute.getNextStopDistance();
    assertEquals(0.008784,  firstDistance);
  }

  /**
   * Test for update route data.
   */
  @Test
  public void testUpdateRouteData() {
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
    Route testRoute = new Route("testRoute", stopsIn, distancesIn, 3, generatorIn);
    //System.out.println(testRoute.getRouteData().getId());
    assertEquals("", testRoute.getRouteData().getId());
    assertEquals(0, testRoute.getRouteData().getStops().size());
    testRoute.updateRouteData();
    assertEquals("testRoute", testRoute.getRouteData().getId());
    assertEquals(3, testRoute.getRouteData().getStops().size());
  }
}
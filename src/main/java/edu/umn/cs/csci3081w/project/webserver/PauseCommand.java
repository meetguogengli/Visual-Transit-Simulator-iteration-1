package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.BusData;
import java.util.List;

public class PauseCommand extends MyWebServerCommand {
  private VisualizationSimulator mySim;

  public PauseCommand(VisualizationSimulator sim){
  	this.mySim=sim;
  }
  /**
   * Pause the state of the simulation.
   *
   * @param session current simulation session
   * @param command the update simulation command content
   * @param state the state of the simulation session
   */
  @Override
  public void execute(MyWebServerSession session, JsonObject command,
                      MyWebServerSessionState state){
    mySim.setPause();

  }

}
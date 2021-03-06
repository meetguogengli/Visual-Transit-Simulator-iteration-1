package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.BusData;
import java.util.List;

public class ResumeCommand extends MyWebServerCommand {
  private VisualizationSimulator mySim;

  public ResumeCommand(VisualizationSimulator sim) {
    this.mySim = sim;
  }

  /**
   * Resume the state of the simulation.
   *
   * @param session current simulation session
   * @param command the update simulation command content
   * @param state the state of the simulation session
   */
  @Override
  public void execute(MyWebServerSession session, JsonObject command,
                      MyWebServerSessionState state) {
    mySim.setResume();

  }

}
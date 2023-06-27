package com.janwue.pong;

import com.badlogic.gdx.math.Intersector;

import java.awt.*;

public class Player {
  private Panel panel;
  private int points = 0;

  public Player(Panel panel) {
    this.panel = panel;
  }

  public void increasePoints() {
    this.points++;
  }

  public Panel getPanel() {
    return panel;
  }

  public void setPanel(Panel panel) {
    this.panel = panel;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }


}

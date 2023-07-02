package com.janwue.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Ball {
  private Circle circle;
  private final ShapeRenderer shapeRenderer = new ShapeRenderer();
  private float x;
  private float y;
  private final float radius;
  private float velocity = 100;
  private Color color;

  public Ball() {
    this((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 20, Color.YELLOW);
    circle = new Circle(this.x, this.y, this.radius);
  }

  public Ball(float x, float y, float radius, Color color) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    circle = new Circle(this.x, this.y, this.radius);
    this.color = color;
  }

  public void draw(OrthographicCamera camera) {
    camera.update();
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.setColor(this.color);
    shapeRenderer.circle(this.x, this.y, this.radius);
    shapeRenderer.end();
  }

  public void moveRight() {
    float delta = Gdx.graphics.getDeltaTime();
    this.x += velocity * delta;
    updateCircle();
  }

  public void moveLeft() {
    float delta = Gdx.graphics.getDeltaTime();
    this.x -= velocity * delta;
    updateCircle();
  }

  public void moveUp() {
    float delta = Gdx.graphics.getDeltaTime();
      this.y += velocity * delta;
    updateCircle();
  }

  public void moveDown() {
    float delta = Gdx.graphics.getDeltaTime();
      this.y -= velocity * delta;
    updateCircle();
  }

  public void increaseVelocity() {
    this.velocity += 20;
  }



  public float getY() {
    return y;
  }



  public Circle getCircle() {
    return circle;
  }

  public void updateCircle() {

    circle.x = this.x;
    circle.y = this.y;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }

  public void setX(float x) {
    this.x = x;
  }

  public void setY(float y) {
    this.y = y;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void resetVelocity() {
    this.velocity =  100;
  }

  public float getX() {
    return x;
  }

  public float getRadius() {
    return radius;
  }
  public Vector3 getPosition() {
    return new Vector3(this.x, this.y, 0);
  }
}

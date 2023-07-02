package com.janwue.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Panel {
  private com.badlogic.gdx.math.Rectangle rectangle;

  private ShapeRenderer shapeRenderer = new ShapeRenderer();
  private float x;
  private float y;
  private float width;
  private float height;
  private int downKey;
  private int upKey;
  Color color;
  OrthographicCamera camera;

  private final float VELOCITY = 500;

  public Panel(int upKey, int downKey, float x) {
   this(upKey, downKey, x ,(float) 500 / 2 - (float) 100 / 2, 30, 100,Color.WHITE );
   rectangle = new com.badlogic.gdx.math.Rectangle(this.x, this.y, this.width, this.height);
  }

  public Panel(float x, int downKey, int upKey, Color color) {

    this(upKey, downKey, x ,(float) 500 / 2 - (float) 100 / 2, 30, 100,color);
    rectangle = new com.badlogic.gdx.math.Rectangle(this.x, this.y, this.width, this.height);
  }

  public Panel(int upKey, int downKey, float x, float y, float width, float height, Color color) {
    this.upKey = upKey;
    this.downKey = downKey;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    rectangle = new com.badlogic.gdx.math.Rectangle(this.x, this.y, this.width, this.height);
  }

  public void draw(OrthographicCamera camera) {
     shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
     shapeRenderer.setProjectionMatrix(camera.combined);
     shapeRenderer.setColor(this.color);
     shapeRenderer.rect(this.x, this.y, this.width, this.height);
     shapeRenderer.end();
  }

  public void move() {
    float delta = Gdx.graphics.getDeltaTime();
    if(Gdx.input.isKeyPressed(upKey)){
      if(y + height < Gdx.graphics.getHeight())
       y += VELOCITY * delta;
    }

    if(Gdx.input.isKeyPressed(downKey)){
      if(this.y  > 0) {
        y -= VELOCITY * delta;
      }
    }
    updateRectangle();
  }

  public void updateRectangle() {

    rectangle.x = this.x;
    rectangle.y = this.y;
  }



  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}

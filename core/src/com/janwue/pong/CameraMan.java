package com.janwue.pong;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class CameraMan {

  OrthographicCamera camera;
  ZoomState zoomState = ZoomState.NO_ZOOM;
  Vector3 zoomPosition;
  float zoomTime = 0;
  final float TOTAL_ZOOM_IN_TIME_S = 2;
  final float TOTAL_ZOOM_OUT_TIME_S = 2;
  float startZoomValue;
  float targetZoomValue;
  Vector3 targetZoomPosition;
  Vector3 startZoomPosition;
  boolean isShaking = false;
  float shakeStrength;
  Random random = new Random();
  Vector3 camPositionBeforeShaking;
  final float REDUCE_SHAKE_STRENGTH =  0.9f;

  public CameraMan(OrthographicCamera camera) {
    this.camera = camera;
  }

  public void startZoom(Vector3 zoomPosition, float targetZoomValue) {
    this.zoomTime = 0;
    this.zoomPosition = zoomPosition;
    this.zoomState = ZoomState.ZOOM_IN;
    this.targetZoomValue = targetZoomValue;
    this.startZoomValue = camera.zoom;
    this.startZoomPosition = new Vector3(camera.position);
    this.targetZoomPosition = zoomPosition;
  }

  public void update(float deltaTime) {
    updateZoom(deltaTime);
    if (isShaking) {
      shake();
    }

  }

  private void shake() {
    Vector2 unitVector = new Vector2(1, 0);
    float randomDegree = random.nextFloat(366);
    if (this.zoomState == ZoomState.NO_ZOOM){ //
      this.camera.position.set(this.camPositionBeforeShaking);
    }
    unitVector.rotateDeg(randomDegree);
    unitVector.scl(shakeStrength);
    camera.translate(unitVector);

    this.shakeStrength *= REDUCE_SHAKE_STRENGTH;
    if(shakeStrength < 0.1) {
      this.isShaking = false;
      this.camera.position.set(camPositionBeforeShaking);
    }
  }

  private float linearInterpolation(float startValue, float targetValue, float currentTime, float totalTime) {
    float t = (currentTime / totalTime);
    return startValue + t * (targetValue - startValue);
  }

  public void startShake(float shakeStrength) {
    this.shakeStrength = shakeStrength;
    this.isShaking = true;

    camPositionBeforeShaking = new Vector3(camera.position);
  }

  public void updateZoom(float deltaTime) {

    if (this.zoomState == ZoomState.NO_ZOOM) {
      return;
    }
    zoomTime += deltaTime;
    if (this.zoomState == ZoomState.ZOOM_IN) {
      zoomTime = Math.min(TOTAL_ZOOM_IN_TIME_S, zoomTime);
      camera.zoom = linearInterpolation(this.startZoomValue, this.targetZoomValue, this.zoomTime, TOTAL_ZOOM_IN_TIME_S);
      float t = zoomTime / TOTAL_ZOOM_IN_TIME_S;
      Vector3 cameraPosition = new Vector3(this.startZoomPosition).lerp(this.targetZoomPosition, t);
      camera.position.set(cameraPosition);
      camera.update();

      if (zoomTime >= TOTAL_ZOOM_IN_TIME_S) {
        this.zoomState = ZoomState.ZOOM_OUT;
        this.zoomTime = 0;
      }
    } else if (this.zoomState == ZoomState.ZOOM_OUT) {
      zoomTime = Math.min(TOTAL_ZOOM_OUT_TIME_S, zoomTime);
      camera.zoom = linearInterpolation(this.targetZoomValue, this.startZoomValue, this.zoomTime, TOTAL_ZOOM_OUT_TIME_S);
      float t = zoomTime / TOTAL_ZOOM_OUT_TIME_S;
      Vector3 cameraPosition = new Vector3(this.targetZoomPosition).lerp(this.startZoomPosition, t);
      camera.position.set(cameraPosition);
      camera.update();

      if (zoomTime >= TOTAL_ZOOM_OUT_TIME_S) {
        this.zoomState = ZoomState.NO_ZOOM;
        this.zoomTime = 0;
      }
    }
  }
}

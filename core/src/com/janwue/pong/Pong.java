package com.janwue.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

public class Pong extends Game {
  private SpriteBatch batch;
  private Ball ball;
  private OrthographicCamera camera;
  private Direction leftRightDirection;
  private Direction upDownDirection;
 private Music ballOverlapsRightPanelSound;
 private Sound ballOverLapsesLeftPanelSound;

  private final int upArrow = Input.Keys.UP;
  private final int downArrow = Input.Keys.DOWN;
  private final int wKey = Input.Keys.W;

  private final int sKey = Input.Keys.S;
  private Player player1;
  private Player player2;
  private BitmapFont font1;
  private BitmapFont font2;

  @Override
  public void create() {
    ball = new Ball();

    batch = new SpriteBatch();
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 500);

    player1 = new Player(new Panel(20,sKey, wKey, Color.FIREBRICK));
    player2 = new Player(new Panel(Gdx.graphics.getWidth() - 50, downArrow,upArrow, Color.BLUE));
    leftRightDirection = Direction.East;
     font1 = new BitmapFont();
     font2 = new BitmapFont();
     ballOverlapsRightPanelSound = Gdx.audio.newMusic(Gdx.files.internal("boing.mp3"));
     ballOverLapsesLeftPanelSound = Gdx.audio.newSound(Gdx.files.internal("boing2.mp3"));


  }

  @Override
  public void render() {

    ScreenUtils.clear(0, 0, 0.2f, 1);
    camera.update();

    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    font1.draw(batch, String.valueOf(player1.getPoints()), 50, Gdx.graphics.getHeight() -100);
    font2.draw(batch, String.valueOf(player2.getPoints()), Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() -100);
    font1.getData().setScale(3);
    font2.getData().setScale(3);
    batch.end();
    ball.draw();

    player1.getPanel().draw();
    player2.getPanel().draw();

    player1.getPanel().move();
    player2.getPanel().move();

    if (isBallTouchingLeftPanel()) {
      ballOverLapsesLeftPanelSound.play();
      ball.increaseVelocity();
      upDownDirection = getMovingDirection(player1);
      leftRightDirection = Direction.East;
    }
    if(isBallTouchingRightPanel()) {
      ballOverlapsRightPanelSound.play();
      ball.increaseVelocity();
      upDownDirection = getMovingDirection(player2);
      leftRightDirection = Direction.West;
    }
   if(isBallOverlappingWindowBottom()) {
     upDownDirection = Direction.North;
   }
   if(isBallOverlappingWindowTop()) {
     upDownDirection = Direction.South;
   }

    if(upDownDirection == Direction.North) {
       ball.moveUp();
    }
    if(upDownDirection == Direction.South) {
      ball.moveDown();
    }

    if(leftRightDirection == Direction.West) {
      ball.moveLeft();
      ball.moveLeft();
    }

    if(leftRightDirection == Direction.East) {
      ball.moveRight();
      ball.moveRight();
    }

    if(isBallOutOfWindowRightSide()) {
      player1.increasePoints();
      startNewRound();
    }
    if(isBallOutOfWindowLeftSide()) {
      player2.increasePoints();
      startNewRound();
    }
    exitGameWithEsc();
  }

  public void exitGameWithEsc() {
    if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit();
    }
  }



  @Override
  public void dispose() {
    batch = new SpriteBatch();

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 500);


  }

  public boolean isBallTouchingRightPanel() {
    return Intersector.overlaps(ball.getCircle(), player2.getPanel().getRectangle());

  }

  public boolean isBallTouchingLeftPanel() {
    return Intersector.overlaps(ball.getCircle(), player1.getPanel().getRectangle());
  }

  public Direction getMovingDirection(Player player) {
    float mediumHeight = player.getPanel().getY() + player.getPanel().getHeight() / 2;
    float overHeight = mediumHeight + 20;
    float lowHeight = mediumHeight - 20;
    if (ball.getY() >= overHeight) {
      return Direction.North;
    } else if (ball.getY() <= lowHeight) {
      return Direction.South;
    } else {
      return null;
    }
  }
  public boolean isBallOverlappingWindowTop() {
    return ball.getY() >= Gdx.graphics.getHeight();
  }

  public boolean isBallOverlappingWindowBottom() {
    return ball.getY() <= 0;
  }

  public boolean isBallOutOfWindowRightSide() {
    return ball.getX() > Gdx.graphics.getWidth() + 30;
  }
  public boolean isBallOutOfWindowLeftSide() {
    return ball.getX()  < -30;
  }

 public void startNewRound() {
    this.ball = new Ball();
    player1.setPanel(new Panel(20,sKey, wKey, Color.BLUE));
    player2.setPanel(new Panel(Gdx.graphics.getWidth() - 50,downArrow, upArrow, Color.RED));
 }

}

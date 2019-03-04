package edu.neumont.lopez;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * Controller of the Canvas that is in charge of drawing the whole game
 */
public class CanvasController {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_HEIGHT = 70;
    private final int PADDLE_WIDTH = 15;
    /**
     * The canvas that's been placed on the stackpane in the fxml file
     */
    public Canvas drawingCanvas;
    private int ballX = WIDTH / 2, ballY = HEIGHT / 2;
    private int ballWidth = 20, ballHeight = 20;
    private int ballXVelocity = 1, ballYVelocity = 1;
    private Color ballColor = Color.WHITE;
    private int player1Score = 0;
    private int player2Score = 0;
    private GraphicsContext g;

    private double leftYPos = HEIGHT / 2;
    private double rightYPos = HEIGHT / 2;

    private Timeline timeline;

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case W:
                    leftYPos += -30;
                    break;
                case S:
                    leftYPos += 30;
                    break;
                case UP:
                    rightYPos += -30;
                    break;
                case DOWN:
                    rightYPos += 30;
                    break;
                case SPACE:
                    Random random = new Random();
                    ballX = WIDTH / 2;
                    ballY = random.nextInt(600);
                    ballYVelocity *= -1;
                    ballXVelocity *= -1;
                    timeline.play();

            }
        }
    };

    void init(Stage stage) {

        stage.setTitle("Pong Game");
        stage.show();

        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void draw() {

        ballX += ballXVelocity;
        ballY += ballYVelocity;

        if (ballX + ballWidth >= WIDTH - PADDLE_WIDTH &&
                ballY + (ballHeight / 2) >= rightYPos &&
                ballY + (ballHeight / 2) <= rightYPos + PADDLE_HEIGHT &&
                ballXVelocity > 0) {
            ballXVelocity *= -1;
            ballXVelocity -= 1;
        }

        if (ballX <= PADDLE_WIDTH &&
                ballY + (ballHeight / 2) >= leftYPos &&
                ballY + (ballHeight / 2) <= leftYPos + PADDLE_HEIGHT &&
                ballXVelocity < 0) {
            ballXVelocity *= -1;
            ballXVelocity += 2;
        }

        if (ballX - ballWidth > drawingCanvas.getWidth()) {
            player1Score += 1;
            g.fillText("Press Space to continue.", 400, 300);
            timeline.stop();
        }

        if (ballX <= 0 - ballWidth) {
            player2Score += 1;
            g.fillText("Press Space to continue.", 400, 300);
            timeline.stop();
        }

        if (ballY + ballHeight >= drawingCanvas.getHeight() || ballY <= 0) {
            ballYVelocity *= -1;
        }

        //Controlling left paddle to not go anywhere outside of the screen size
        if (leftYPos > HEIGHT - 70) {
            leftYPos = HEIGHT - 70;
        }
        if (leftYPos < 0) {
            leftYPos = 0;
        }

        //Controlling right paddle to not go anywhere outside of the screen size
        if (rightYPos > HEIGHT - 70) {
            rightYPos = HEIGHT - 70;
        }
        if (rightYPos < 0) {
            rightYPos = 0;
        }

        run();
    }


    private void run() {
        g = drawingCanvas.getGraphicsContext2D();
        g.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        g.setFill(ballColor);
        g.fillOval(ballX, ballY, ballWidth, ballHeight);

        int leftXPos = 0;
        g.fillRect(leftXPos, leftYPos, PADDLE_WIDTH, PADDLE_HEIGHT);
        int rightXPos = WIDTH - PADDLE_WIDTH;
        g.fillRect(rightXPos, rightYPos, PADDLE_WIDTH, PADDLE_HEIGHT);
        drawSplit(g, ballColor);
        g.setFont(new Font("HP Simplified Light", 50));
        if (player1Score >= 10) {
            g.fillText("" + player1Score, 280, 75);
        } else {
            g.fillText("" + player1Score, 300, 75);
        }
        if (player2Score >= 10) {
            g.fillText("" + player2Score, 470, 75);
        } else {
            g.fillText("" + player2Score, 480, 75);
        }

        drawingCanvas.setFocusTraversable(true);
        drawingCanvas.setOnKeyPressed(keyPressed);
        //drawingCanvas.setOnKeyReleased(keyReleased);

    }

    private void drawSplit(GraphicsContext g, Color color) {
        g.setFill(color);
        int lineWidth = 7; // even numbers!
        int lineLength = 24;
        int margin = 16;
        int numOfLines = 16;

        for (int i = 0; i < numOfLines; i++) {
            g.fillRect(drawingCanvas.getWidth() / 2 - lineWidth / 2, i * lineLength + i * margin, lineWidth, lineLength);
        }
    }

}

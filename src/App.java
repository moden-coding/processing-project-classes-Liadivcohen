import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.*;

public class App extends PApplet {
    // bubble firstOne;
    ArrayList<bubble> bubbles; // Creates an array list for falling objects
    Paddle paddle; // Diclairs the paddle
    double timer;
    int scene; // scene 0 = intro scene, scene 1 = game play, scene 2 = game over
    double highscore;// Making it in arraylist makes the highscore not reset
    double gameStart;
    int lastSpeedIncreaseTime;
    int speedLevel = 0;
    int speed = 3;
    int lives;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {

        bubbles = new ArrayList<>(); // Assigns + creates the array list
        lives = 3;
        speed = 3;
        for (int i = 0; i < 8; i++) { // How many bubbles appear on the screen so 8 will appear
            bubbleMaker();
        }
        scene = 0;
        gameStart = millis();
        paddle = new Paddle(this); // *This* giving the bubble access to the screen
        lastSpeedIncreaseTime = millis();
    }

    public void settings() { // Creates the size of screen
        size(700, 500);
        readHighScore();
    }

    public void draw() {
        background(0);
        if (scene == 0) { // Intro scene
            introScene();
        }
        if (scene == 1) {
            for (int i = bubbles.size() - 1; i >= 0; i--) {
                bubble b = bubbles.get(i);

                b.display(); // Displays the bubbles
                b.update();
                displayLives(); // Displays lives

                offScreen(b, i);
                if (b.touchesPaddle(paddle)) {// Checks to see if the bubble is touching the paddle if it is game over
                    lives--; // remove one live
                    bubbles.remove(i); // remove that bubble
                    bubbleMaker();// make a new one that replaces it

                    if (lives <= 0) { // If lives is less than 0 or equal to 0 go to scene 2
                        scene = 2;
                    }
                }
            }
            if (millis() - lastSpeedIncreaseTime >= 10000) { // 10,000 ms = 10 seconds
                speedLevel++;
                increaseBubbleSpeed();
                lastSpeedIncreaseTime = millis();
            }
            paddle.display(); // Displays paddle and high score
            paddle.movement();
            displayingHighScore();
        }
        if (scene == 2) {
            if (timer > highscore || highscore == 0) {
                highscore = timer;
                saveHighScore();
            }
            readHighScore();
            gameOver();
        }
    }

    public void increaseBubbleSpeed() {
        speed++;

        for (bubble b : bubbles) {
            b.increaseSpeed(1);
        }
    }

    public void saveHighScore() {// Saving and reading high score

        try (PrintWriter writer = new PrintWriter("highscore.txt")) { // only running when i know i have a highscore
            writer.println(highscore); // Writes the integer to the file
            writer.close(); // Closes the writer and saves the file
            System.out.println("Integer saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void bubbleMaker() {// Makes the bubble and gives it a random place to go to
        int x = (int) random(700);
        int y = (int) random(0, 250);

        bubble bubble = new bubble(x, y, this, speed);
        bubbles.add(bubble); // Adding to the array list
    }

    public void offScreen(bubble b, int i) {
        if (b.isDead()) { // If bubble goes off the screen remove it safetly from the screen and replace
                          // it with a new one
            bubbles.remove(i);
            bubbleMaker();
        }
    }

    public void keyPressed() {// What happens when you press the arrow keys
        if (keyCode == LEFT) {
            paddle.moveLeft();
        }
        if (keyCode == RIGHT) {
            paddle.moveRight();
        }
        if (key == ' ' && scene == 0) {
            scene = 1;
        }
        if (key == 's') {
            resetGame();
        }

    }

    public void keyReleased() {// Stop paddle when key is released
        paddle.stop();
    }

    public void readHighScore() { // Saves the high score doesnt need to be in draw
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {

            // we read the file until all lines have been read
            while (scanner.hasNextLine()) {
                // we read one line
                String row = scanner.nextLine();
                // we print the line that we read
                highscore = Double.valueOf(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void introScene() {
        background(0);
        textSize(25);
        text("FALLING OBJECTS!!", 5, 50);
        text("Rules: Dodge the falling objects using the left and right keys", 5, 100);
        text("Everytime an object hits you game is over", 5, 150);
        text("Press space bar to start the game.", 5, 200);
    }

    public void gameOver() {
        background(0);
        textSize(35);
        text("Game Over", 10, 200);
        text("Score " + timer, 10, 300);
        text("High Score " + highscore, 10, 350);
        text("Press 's' to start new game!", 10, 250);
    }

    public void resetGame() {
        setup();
        scene = 0;
    }

    public void displayingHighScore() {
        textSize(25);
        textAlign(LEFT); // Makes it allign to the top right
        timer = millis() - gameStart;
        timer = ((int) (timer / 100)) / 10.0; // thousands of seconds its been running
        text("" + timer, width - 100, 50); // Makes the "" not mad

    }

    public void displayLives() {
        fill(255);
        textSize(25);
        text("Lives " + lives, 10, 50);

    }

}

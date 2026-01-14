import processing.core.*;

public class Paddle {
    private float x;
    private float y;
    private float w;
    private float h;
    private float speed;
    private PApplet canvas; // Gives access to the main screen
    private boolean left, right;

    public float getX() { // Makes it accessable to bubble class
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public Paddle(PApplet c) {// Declaring the variables
        canvas = c;
        w = 50;
        h = 20;
        x = (canvas.width - w) / 2;
        y = canvas.height - 40;
        speed = 15;
    }

    public void display() { // Creates the paddle
        canvas.fill(255);
        canvas.rect(x, y, w, h);
    }

    public void moveLeft() { // When pressing the key it moves left
        left = true;
        right = false;
    }

    public void moveRight() { // When pressing the key it moves right
        right = true;
        left = false;
    }

    public void movement() { // Creates smooth movment for the paddle
        if (left) {
            x -= speed;
        }

        if (right) {
            x += speed;

        }
        x = canvas.constrain(x, 0, canvas.width - w); // Keeps paddle on screen
    }

    public void stop() {
        left = false;
        right = false;
    }
}

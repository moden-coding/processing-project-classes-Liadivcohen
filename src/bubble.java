import processing.core.*;

public class bubble {
    private int x;
    private int y;
    private int size;
    private PApplet canvas; //a screen variable
    private int speed;
    private int color;
    private int health;

    public bubble(int xPos, int yPos, PApplet c){ //Setting up the variables
        x = xPos;
        y = yPos;
        size = 45;
        canvas = c; //what the variable is equal to 
        speed = 3;
        color = canvas.color(0,255,0);
        health = 3;

    }

    public void display(){
        canvas.fill(color);
        canvas.circle(x,y,size); // Make sure you do the canvas so the bubble knows where it is appearing
        if (health == 2){
            color = canvas.color(255,255,0);
        }else if (health == 1){
            color = canvas.color(255,0,0);
            
        }
    }

    public void update(){ //Makes bubble move
        y += speed;
        if ( y - size/2 < 0){ // Makes objects fall of the screen
            speed = -speed;
            health--;
        }

    }
    public int randomColor(){ //Method for random color
        return  color = canvas.color(canvas.random(255),canvas.random(255),canvas.random(255)); //Random color for ball

    }

    public int getSize(){
        return size;
    }
    public boolean checkTouch(int mouseX, int mouseY){
        float distanceFromCenter = canvas.dist(x, y, mouseX, mouseY);
        if(distanceFromCenter < size/2){
             health--;
        }
       
        if (health > 0){
            return true;
        }else{
            return false;
        }
    }
    }



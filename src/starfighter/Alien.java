package starfighter;

//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {
    private boolean active;
    private int speed;
    private Image image;
    private int activeDirection = (int)Math.round((Math.random()*2)-1);

    public Alien() {
        this(0, 0, 30, 30, 0);
    }

    public Alien(int x, int y) {
        this(x, y, 30, 30, 0);
    }

    public Alien(int x, int y, int s) {
        this(x, y, 30, 30, 0);
        speed = s;
    }

    public Alien(int x, int y, int w, int h, int s) {
        super(x, y, w, h);
        speed = s;
        try {
            URL url = getClass().getResource("/images/alien.jpg");
            image = ImageIO.read(url);
        } catch (Exception e) {
            //feel free to do something here
        }
    }

    public void setSpeed(int s) {
        speed=s;
    }

    public int getSpeed() {
        return speed;
    }
    
    public boolean active(){
        return active;
    }
    
    public void setActive(boolean a){
        active=a;
    }

    public void move(String direction) {
        switch (direction) {
            case "UP":
                setY(getY() - speed);
                break;
            case "DOWN":
                setY(getY() + speed);
                break;
            case "RIGHT":
                setX(getX() + speed);
                break;
            case "LEFT":
                setX(getX() - speed);
                break;
        }

    }

    public void draw(Graphics window) {
        window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }

    public String toString() {
        return "";
    }

    public int getActiveDirection() {
        return activeDirection;
    }

    public void setActiveDirection(int activeDirection) {
        this.activeDirection = activeDirection;
    }
}

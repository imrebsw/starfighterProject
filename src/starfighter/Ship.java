package starfighter;

//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static java.lang.Character.toUpperCase;
import javax.imageio.ImageIO;

public class Ship extends MovingThing {

    private int speed;
    private Image image;

    public Ship() {
        this(10, 10, 10, 10, 10);
    }

    public Ship(int x, int y) {
        this(x, y, 10, 10, 10);
        //add code here

    }

    public Ship(int x, int y, int s) {
        this(x, y, 10, 10, s);
    }

    public Ship(int x, int y, int w, int h, int s) {
        super(x, y, w, h);
        speed = s;
        loadImage();
    }

    private void loadImage() {
        try {
            URL url = getClass().getResource("/images/ship.jpg");
            image = ImageIO.read(url);
        } catch (Exception e) {
            //feel free to do something here
            System.out.println("You messed up the ship class");
        }
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public int getSpeed() {
        return speed;
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

        //window.setColor(Color.white);
        //window.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    public String toString() {
        return super.toString() + " " + getSpeed();
    }
}

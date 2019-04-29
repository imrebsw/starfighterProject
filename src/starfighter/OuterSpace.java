package starfighter;

//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class OuterSpace extends Canvas implements KeyListener, Runnable {

    private Ship ship;
    private Alien alienOne;
    private Alien alienTwo;
    private AlienHorde horde;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private int resets = 0;
    private long score = 0;
    private int timesince = 0;
    private int lives = 3;
    private Image shipPic;
    private int timeSinceDeath = 0;
    private long nextGoal =20000;
    /* uncomment once you are ready for this part
     *
     private AlienHorde horde;
     private Bullets shots;
     */
    private boolean[] keys;
    private BufferedImage back;
    private Bullets bullets;

    public OuterSpace() {
        try {
            URL url = getClass().getResource("/images/ship.jpg");
            shipPic = ImageIO.read(url);
        } catch (Exception e) {
            //feel free to do something here
            System.out.println("You messed up the ship class");
        }
        setBackground(Color.black);

        keys = new boolean[5];

        //instantiate other instance variables
        //Ship, Alien
        ship = new Ship(70, 70, 60, 50, 3);
        alienOne = new Alien(70, 70, 60, 50, 3);
        horde = new AlienHorde(0);
        bullets = new Bullets();
        //horde.add(alienOne);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 6; x++) {
                horde.add(new Alien(70 + 100 * x, 70 + 100 * y, 60, 50, 3));
            }
        }
        this.addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        //set up the double buffering to make the game animation nice and smooth
        Graphics2D twoDGraph = (Graphics2D) window;

        //take a snap shop of the current screen and same it as an image
        //that is the exact same width and height as the current screen
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics graphToBack = back.createGraphics();

        if (horde.isEmpty()) {
            resets++;
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 6; x++) {
                    horde.add(new Alien(70 + 100 * x, 70 + 100 * y, 60, 50, 3));
                }
            }
        }
        
        if (score>nextGoal){
            nextGoal+=10000;
            lives++;
        }
        if(lives<0){
            lives=3;
            horde.clear();
            score=0;
        }
        graphToBack.setColor(Color.BLACK);
        graphToBack.fillRect(0, 0, 800, 600);
        timesince++;
        timeSinceDeath++;
        
        if((int) (Math.random() * 300)==(int) (Math.random() * 300))
            horde.activate((int)(Math.random() * (horde.getList().size()-1)));
        
        graphToBack.setColor(Color.BLUE);
        graphToBack.drawString("StarFighter ", 25, 50);
        bullets.drawAll(graphToBack);
        bullets.moveAll();
        for(int i=0;i<lives;i++)
            graphToBack.drawImage(shipPic, 100+30*i, 100, 30, 30, null);
        graphToBack.setColor(Color.white);
        
        Font font = new Font("Helvetica", Font.PLAIN, 24);
        graphToBack.setFont(font);
        graphToBack.drawString("Score: " + score, 100, 100);

        if (keys[0] == true) {
            ship.move("LEFT");
        }
        if (keys[1] == true) {
            ship.move("RIGHT");
        }
        if (keys[2] == true) {
            ship.move("UP");
        }
        if (keys[3] == true) {
            ship.move("DOWN");
        }
        if (keys[4] == true) {
            if (timesince > 50&&timeSinceDeath>250) {
                timesince = 0;
                bullets.add(new Ammo(ship.getX(), ship.getY(), 3));
                bullets.add(new Ammo(ship.getX() + ship.getWidth() - 10, ship.getY(), 3));
            }
        }
        
        if(timeSinceDeath>250){
            horde.moveAll();
            ship.draw(graphToBack);
            checkShip();}
        else 
            if(timeSinceDeath%(101-timeSinceDeath/5)<10)
                ship.draw(graphToBack);

        check();


        horde.drawAll(graphToBack);
        //horde.moveAll();

        //alienOne.draw(graphToBack);
        //add code to move Shidap, Alien, etc.
        if (ship.getX() < 0) {
            ship.setX(WIDTH);
        }
        if (ship.getX() > WIDTH) {
            ship.setX(0);
        }
        if (ship.getY() < 0) {
            ship.setY(HEIGHT);
        }
        if (ship.getY() > HEIGHT) {
            ship.setY(0);
        }
        //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
        twoDGraph.drawImage(back, null, 0, 0);
    }

    public void checkShip() {
            for (int j = 0; j < horde.getList().size(); j++) {
                Alien tempa = horde.getList().get(j);
                if (ship.getX() > tempa.getX() && ship.getX() < tempa.getX() + tempa.getWidth()) {
                    if (ship.getY() + ship.getHeight() > tempa.getY() && ship.getY() < tempa.getY() + tempa.getHeight()) {
                        horde.remove(j);
                        timeSinceDeath=0;
                        lives--;

                        return;
                    }
                }
            }

    }

    public void check() {
        for (int i = 0; i < bullets.getList().size(); i++) {
            for (int j = 0; j < horde.getList().size(); j++) {
                Alien tempa = horde.getList().get(j);
                Ammo tempb = bullets.getList().get(i);
                if (tempb.getX() > tempa.getX() && tempb.getX() < tempa.getX() + tempa.getWidth()) {
                    if (tempb.getY() + tempb.getHeight() > tempa.getY() && tempb.getY() < tempa.getY() + tempa.getHeight()) {
                        horde.remove(j);
                        bullets.remove(i);
                        score += 100;
                        return;
                    }
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = true;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = false;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {
        //no code needed here
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
        }
    }
}

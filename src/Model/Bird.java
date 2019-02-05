
package Model;

import Game.Game;
import GameEngine.Graphics.BufferedImageLoader;
import GameEngine.Graphics.SpriteSheet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bird {
    
    Game game;
    
    private float xPosition = 0, yPosition = 0;
    private float velocity = 0;
    private float acceleration = 0;
    
    private float gravity;
    
    private BufferedImage image;
    private SpriteSheet sprite;
    private float animation = 1.0f;
    private int direction = 1;
    
    public Bird(Game game) {
        
        this.game = game;
        xPosition = (float)game.getWidth()/3.0f;
        yPosition = (float)game.getHeight()/2.0f - 50;
        gravity = game.getHeight() * 1.7f;
        
        try {
            BufferedImageLoader loader = new BufferedImageLoader();
            sprite = new SpriteSheet(loader.loadImage("/img/birdSprite.png"));
            image = sprite.cropImage((int)animation, direction, 100, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void update() {

        if(game.getInput().isKey(KeyEvent.VK_SPACE) && (velocity >= gravity / 10.0f) && !game.getController().hasCollided()) {
            acceleration = 0;  
            velocity = - gravity / 4.0f;
        }        
        else {
            acceleration += gravity * game.getElapsedTime();
        }
        
        
        if(acceleration >= gravity) 
            acceleration = gravity;
        
        if(yPosition > game.getHeight() || yPosition + 50 < 0){
            game.getController().setCollided(true);
        }
        
        velocity += acceleration * game.getElapsedTime();
        yPosition += velocity * game.getElapsedTime();
        
        if(velocity > 0)
            direction = 1;
        else
            direction = 2;
        
        if(!game.getController().hasCollided()) {
            animation += 10 * game.getElapsedTime();

            if(animation >= 4) 
                animation = 1;
        }
        
        image = sprite.cropImage((int)animation, direction, 100, 100);
        
    }
    
    public void render(Graphics2D g) {
        g.drawImage(image, (int)xPosition,  (int)yPosition, null);
        //g.setColor(Color.red);
        //g.draw(getBounds());
    }
    
    public Polygon getBounds() {
        
        int xpos = (int) xPosition;
        int ypos = (int) yPosition;
        
        int[] x = { xpos + 30 , xpos + 30 , xpos + 60 , xpos + 97 , xpos + 74 };
        int[] y = { ypos + 50 , ypos + 57 , ypos + 60 , ypos + 40 , ypos + 27 };
        
        return new Polygon(x, y, 5);
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getYPosition() {
        return yPosition;
    }
    
    
    
}

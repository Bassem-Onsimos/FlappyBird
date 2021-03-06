package Game;

import GameEngine.Graphics.BufferedImageLoader;
import Model.Bar;
import Model.Bird;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Game game;
    //
    private boolean gameRunning = false;
    private boolean collided = false;
    //
    private Bird bird;
    private ArrayList<Bar> barList;
    //
    private float sectionWidth;
    private int density = 4;
    private float levelPosition = 0;
    private float speed = 200f;
    //
    private BufferedImage sky;
    private float skyPosition = 0;
    private float secondSkyPosition = 0;

    public Controller(Game game) {
        this.game = game;

        bird = new Bird(game);

        barList = new ArrayList<>();

        sectionWidth = (float) game.getWidth() / (float) (density - 1);
        
        BufferedImageLoader loader = new BufferedImageLoader();
        
        try {
            sky = loader.loadImage("/img/background.png");
        } catch (IOException e) {
        }
        
    }

    public void initiate() {
        
        skyPosition = 0;
        secondSkyPosition =  - sky.getWidth();
        levelPosition = 0;
        
        barList = null;
        barList = new ArrayList<>();

        for (int i = 0; i < density; i++) {
            barList.add(new Bar(game, 0));
        }

        game.getAttempts().setValue(game.getAttempts().getValue() + 1);
        game.getScore().setValue(0);

    }

    public void update() {
        if (gameRunning) {

            bird.update();

            if (!collided) {

                levelPosition += speed * game.getElapsedTime();
                skyPosition += speed * game.getElapsedTime();
                secondSkyPosition += speed * game.getElapsedTime();
                
                if(skyPosition >= sky.getWidth()) {
                    skyPosition -= sky.getWidth() * 2;
                }
                
                if(secondSkyPosition >= sky.getWidth()) {
                    secondSkyPosition -= sky.getWidth() * 2;
                }
                
                
                if (levelPosition > sectionWidth) {

                    levelPosition -= sectionWidth;
                    barList.remove(0);
                    barList.add(new Bar(game));
                    
                    game.getScore().setValue(game.getScore().getValue() + 10);
                    
                    if(game.getScore().getValue() > game.getHighScore().getValue())
                        game.getHighScore().setValue(game.getScore().getValue());

                }
                
                if(barList!=null)
                    barList.forEach((bar) -> {
                        bar.update(barList.indexOf(bar));
                    });

            } 
            else {
                if (game.getInput().isKeyUp(KeyEvent.VK_SPACE) && bird.getYPosition() >= game.getHeight()) {
                    collided = false;
                    initiate();
                    bird.setYPosition((float) game.getHeight() / 2.0f - 50);
                    bird.setVelocity(0);
                    bird.setAcceleration(0);
                    gameRunning = false;
                }
            }

        } 
        else {
            if (game.getInput().isKeyUp(KeyEvent.VK_SPACE)) {
                gameRunning = true;
            }
        }
    }

    public void render(Graphics2D g) {
        
        g.drawImage(sky, -(int)skyPosition, 0, null);
        g.drawImage(sky, -(int)secondSkyPosition, 0, null);
        
        if(barList!=null)
            barList.forEach((bar) -> {
                bar.render(g);
            });
        
        bird.render(g);
        
    }

    public float getSectionWidth() {
        return sectionWidth;
    }

    public float getLevelPosition() {
        return levelPosition;
    }

    public Bird getBird() {
        return bird;
    }

    public boolean hasCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}

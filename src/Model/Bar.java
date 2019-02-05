package Model;

import Game.Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

public class Bar {

    private Game game;
    
    private Random rand = new Random();
    
    private int x = 0, yTop = 0, yBottom = 0;
    private int gapHeight = 0;
    private int width = 0, heightTop = 0, heightBottom = 0;

    public Bar(Game game, int height) {
        this.game = game;
        heightBottom = height;

        if (height != 0) 
            doCaluculations();
    }
    
    public Bar(Game game) {
        this.game = game;
        
        heightBottom = rand.nextInt(game.getHeight() - (int)(game.getHeight() / 3.0f));
                
        if(heightBottom <= 50)
            heightBottom = 0;
        else
            doCaluculations();        
    }

    private void doCaluculations() {
        
        gapHeight = (int) ((float) (game.getHeight() / 3.0f));

        heightTop = game.getHeight() - heightBottom - gapHeight;

        width = (int) (game.getController().getSectionWidth() / 7.5f);

        yTop = 0;

        yBottom = game.getHeight() - heightBottom;

    }

    public void update(int index) {
        x = (int) (index * game.getController().getSectionWidth() + (game.getController().getSectionWidth() - width) / 2.0f - game.getController().getLevelPosition());
         
        if(birdCollided()){
            game.getController().setCollided(true);
        }
    }

    public void render(Graphics2D g) {

        g.setColor(Color.darkGray);

        g.fillRect(x, yTop, width, heightTop);

        g.fillRect(x, yBottom, width, heightBottom);

    }
    
    public boolean birdCollided() {
        Polygon bird = game.getController().getBird().getBounds();       
        return (bird.intersects(x, yTop, width, heightTop)) || (bird.intersects(x, yBottom, width, heightBottom)); 
    }

}

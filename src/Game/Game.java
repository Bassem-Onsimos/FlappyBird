
package Game;

import GameEngine.AbstractGame;
import GamePanel.GameData;
import GamePanel.IntegerPanelItem;
import Model.Bird;
import java.awt.Color;
import java.awt.Graphics2D;

public class Game extends AbstractGame {
    
    private Controller controller;
    //
    private IntegerPanelItem attempts, score, highScore;
    //
    
    public Game(int width, int height, float scale) {
        super(width, height, scale);
    }

    @Override
    public void initiate() {     
        controller = new Controller(this);
        setDebugInfoDisplayed(false);
        
        attempts = new IntegerPanelItem("Attempts", 0);
        score = new IntegerPanelItem("Score", 0);
        highScore = new IntegerPanelItem("Hight Score", 0);
        
        addGamePanel(new GameData() {
            @Override
            public void initiate() {
                addItem(attempts);
                addItem(score);
                addItem(highScore);
            }
        }, Color.darkGray, new Color(213, 238, 241), new Color(149, 184, 60), 17);
        
        controller.initiate();
        
    }

    @Override
    public void update() {
        controller.update();
    }

    @Override
    public void render(Graphics2D g) {
        controller.render(g);
    }

    public Controller getController() {
        return controller;
    }

    public IntegerPanelItem getAttempts() {
        return attempts;
    }

    public IntegerPanelItem getScore() {
        return score;
    }

    public IntegerPanelItem getHighScore() {
        return highScore;
    }
    
    

}


package Game;

import GameEngine.AbstractGame;
import Model.Bird;
import java.awt.Graphics2D;

public class Game extends AbstractGame {
    
    private Controller controller;
    
    public Game(int width, int height, float scale) {
        super(width, height, scale);
    }

    @Override
    public void initiate() {     
        controller = new Controller(this);
        controller.initiate();
        setDebugInfoDisplayed(false);
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
    
    

}

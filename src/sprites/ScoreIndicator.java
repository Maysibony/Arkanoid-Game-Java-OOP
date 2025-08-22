package sprites;
import biuoop.DrawSurface;
import game.Counter;
import game.Game;

import java.awt.Color;

public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructor.
     * @param score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(350, 16, "Score: " + score.getValue(), 15);
    }
    @Override
    public void timePassed() {
    }
    @Override
    public void addToGame(Game game){
        game.addSprite(this);
    }
    @Override
    public void removeFromGame(Game game){
        game.removeSprite(this);
    }
}

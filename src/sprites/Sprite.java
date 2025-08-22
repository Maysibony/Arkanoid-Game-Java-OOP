package sprites;
import biuoop.DrawSurface;
import game.Game;

/**
 * a Sprite is a game object that can be drawn to the screen.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     * @param d
     */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
    /**
     * add sprite to game.
     * @param game
     */
    void addToGame(Game game);
    /**
     * @param game the Game object from which the block should be removed.
     */
    public void removeFromGame(Game game);
}

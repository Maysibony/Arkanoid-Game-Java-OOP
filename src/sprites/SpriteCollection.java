package sprites;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * list of sprites.
 */
public class SpriteCollection {
    private List<Sprite> sprites;
    /**
     * constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }
    /**
     * add the given sprite to the collection.
     * @param s - Sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    /**
     * remove the given sprite to the collection.
     * @param s - Sprite object.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }
    /**
     * call drawOn(d) on all sprites.
     * @param d surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}

package sprites;
import collisions.HitListener;
import game.Counter;
import game.Game;

public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game
     * @param remainingBlocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Remove blocks the hited.
     * @param beingHit
     * @param hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.setColor(beingHit.getColor());
        this.remainingBlocks.decrease(1);
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
    }
}
import game.Game;

/**
 * play game.
 */
public class ArkanoidGame {
    /**
     * main method.
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}

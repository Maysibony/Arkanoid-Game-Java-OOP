package game;
import biuoop.DrawSurface;
import biuoop.GUI;
import collisions.Collidable;
import collisions.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import sprites.*;

import java.awt.Color;

/**
 * intilize the game and run.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private GUI gui;
    private static final int BLOCKTHICKNESS = 22;
    private Block background;
    private Counter remainingBlocks = new Counter(0);
    private Counter remainingBalls= new Counter(3);
    private Counter currentScore = new Counter(0);

    /**
     * constructor.
     */
    public Game() {
        this.sprites =  new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Game", WIDTH, HEIGHT);
        Color back = new Color(255, 233, 154);
        this.background = new Block(new Rectangle(new Point(BLOCKTHICKNESS / 2, BLOCKTHICKNESS / 2),
                WIDTH, HEIGHT), back);
    }
    /**
     * add collidable object.
     *
     * @param c
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    /**
     * add sprite object.
     *
     * @param s
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * remove collidable object.
     * @param c
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**
     * remove sprite object.
     * @param s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    /**
     * Initialize a new game: create the Blocks and Ball and Paddle.
     * add them to the game.
     */
    public void initialize() {
        //Add paddle
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(keyboard);
        paddle.addToGame(this);

        //Add balls
        int[][] velocities = {
                {2, -2},
                {-2, 2},
                {-2, -2}
        };
        Color ballColor = Color.WHITE;
        int radius = 5;
        for (int i = 0; i < remainingBalls.getValue(); i++) {
            Ball ball = new Ball(300, 300, radius, ballColor);
            ball.setVelocity(velocities[i][0], velocities[i][1]);
            ball.setGameEnvironment(environment);
            ball.addToGame(this);
        }
        BallRemover ballRemover = new BallRemover(this,this.remainingBalls);

        //Add border
        Color border = new Color(255, 213, 134);
        Block topBorder = new Block(new Rectangle(new Point(0, 0), WIDTH, BLOCKTHICKNESS), border);
        Block deathRegion = new Block(new Rectangle(new Point(0, HEIGHT + 2 * radius),
                WIDTH, BLOCKTHICKNESS), border);
        Block leftBorder = new Block(new Rectangle(new Point(0, 0), BLOCKTHICKNESS, HEIGHT), border);
        Block rightBorder = new Block(new Rectangle(new Point(WIDTH - BLOCKTHICKNESS, 0),
                BLOCKTHICKNESS, HEIGHT), border);
        topBorder.addToGame(this);
        deathRegion.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        deathRegion.addHitListener(ballRemover);

        //custom colors
        Color darkBlue = new Color(38, 70, 83);
        Color lightBlue = new Color(42, 157, 150);
        Color lightGreen = new Color(138, 177, 125);
        Color peach = new Color(236, 167, 127);
        Color lightPink = new Color(255, 197, 212);
        Color pink = new Color(224, 110, 128);

        //Add blocks
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(currentScore);
        ScoreIndicator scoreIndicator = new ScoreIndicator(currentScore);
        scoreIndicator.addToGame(this);
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        Color[] colors = {darkBlue, lightBlue, lightGreen, peach, lightPink, pink};
        int blocksInTopRow = BLOCKTHICKNESS / 2;
        int startY = BLOCKTHICKNESS * 4;

        for (int row = 0; row < colors.length; row++) {
            Color color = colors[row];
            int y = startY + row * BLOCKTHICKNESS;
            int blocksInRow = blocksInTopRow - row;

            for (int col = 0; col < blocksInRow; col++) {
                int x = WIDTH - BLOCKTHICKNESS - (col + 1) * BLOCKTHICKNESS * 2;
                Rectangle rect = new Rectangle(new Point(x, y), BLOCKTHICKNESS * 2, BLOCKTHICKNESS);
                Block block = new Block(rect, color);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTrackingListener);
                remainingBlocks.increase(1);
            }
        }
    }
    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            background.drawOn(d);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (this.remainingBlocks.getValue() == 0){
                currentScore.increase(100);
                System.out.println("You Win!\nYour score is: " + currentScore.getValue());
                d = gui.getDrawSurface();
                background.drawOn(d);
                this.sprites.drawAllOn(d);
                gui.show(d);
                sleeper.sleepFor(1000);
                gui.close();
            }
            if (this.remainingBalls.getValue() == 0){
                System.out.println("Game Over.\nYour score is: " + currentScore.getValue());
                sleeper.sleepFor(1000);
                gui.close();
            }

        }
    }

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

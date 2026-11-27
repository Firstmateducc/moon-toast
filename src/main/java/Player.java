import zero.GameApp;
import zero.ImageRegion;


public class Player {
    private static final double WIDTH = 70;
    private static final double HEIGHT = 64;
    private static final double Y = 500;

    private double x = 240;
    private final double speed = 100;
    private final ImageRegion sprite;

    public Player(ImageRegion sprite){
        this.sprite = sprite;
    }

    public void update(double horizontal, double seconds, double canvasWidth){
        x += horizontal * speed * seconds;
        x = Math.max(5, Math.min(x, canvasWidth - 75));
    }

    public boolean intersects(double otherX, double otherY, double otherWidth, double otherHeight){
        return x < otherX + otherWidth
            && x + WIDTH > otherX
            && Y < otherY + otherHeight
            && Y + HEIGHT > otherY;
    }

    public void draw(GameApp game){
        game.image(sprite, x, Y, WIDTH, HEIGHT);
    }
}

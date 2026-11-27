import zero.GameApp;
import zero.ImageRegion;


public class Player {
    private static final double WIDTH = 70;
    private static final double HEIGHT = 64;
    private static final double Y = 500;

    private double x = 240;
    private double y = Y;
    private boolean visible = true;
    private final double speed = 100;
    private final ImageRegion sprite;

    public Player(ImageRegion sprite){
        this.sprite = sprite;
    }

    public void update(double horizontal, double seconds, double canvasWidth){
        x += horizontal * speed * seconds;
        x = Math.max(5, Math.min(x, canvasWidth - 75));
    }

    public boolean flyTo(double targetCenterX, double targetCenterY, double seconds){
        double targetX = targetCenterX - WIDTH / 2;
        double targetY = targetCenterY - HEIGHT / 2;
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.hypot(dx, dy);
        double travel = 300 * seconds;

        if(distance <= travel){
            x = targetX;
            y = targetY;
            visible = false;
            return true;
        }

        x += dx / distance * travel;
        y += dy / distance * travel;
        return false;
    }

    public boolean intersects(double otherX, double otherY, double otherWidth, double otherHeight){
        return x < otherX + otherWidth
            && x + WIDTH > otherX
            && y < otherY + otherHeight
            && y + HEIGHT > otherY;
    }

    public void draw(GameApp game){
        if(visible){
            game.image(sprite, x, y, WIDTH, HEIGHT);
        }
    }
}

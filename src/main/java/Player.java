import zero.GameApp;
import zero.ImageRegion;


public class Player {
    private double x;
    private final double speed = 200;
    private final ImageRegion sprite;

    public Player(ImageRegion sprite){
        this.sprite = sprite;
    }

    public void update(int horizontal, double seconds, double canvasWidth){
        x += horizontal * speed * seconds;
        x = Math.max(0, Math.min(x, canvasWidth - 200));
    }

    public void draw(GameApp game){
        game.image(sprite, x, 200, 80, 64);
    }
}

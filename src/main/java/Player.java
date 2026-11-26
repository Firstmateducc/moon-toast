import zero.GameApp;
import zero.ImageRegion;


public class Player {
    private double x = 230;
    private final double speed = 100;
    private final ImageRegion sprite;

    public Player(ImageRegion sprite){
        this.sprite = sprite;
    }

    public void update(double horizontal, double seconds, double canvasWidth){
        x += horizontal * speed * seconds;
        x = Math.max(0, Math.min(x, canvasWidth - 80));
    }

    public void draw(GameApp game){
        game.image(sprite, x, 450, 80, 64);
    }
}

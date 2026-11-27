import zero.GameApp;
import zero.ImageRegion;

public class Enemy {
    private final int x;
    private int y = -50;
    private final int speed;
    private final ImageRegion sprite;
    
    public Enemy(ImageRegion sprite, int x, int speed){
        this.sprite = sprite;
        this.x = x;
        this.speed = speed;
    }

    public void update(double seconds){
        y += speed * seconds;
    }

    public boolean atBottom(double canvasHeight){
        return y >= canvasHeight;
    }

    public void draw(GameApp game){
        game.image(sprite, x, y, 100, 50);
    }

}

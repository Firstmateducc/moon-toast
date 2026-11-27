import zero.GameApp;
import zero.ImageRegion;

public class Enemy {
    public static final double WIDTH = 100;
    public static final double HEIGHT = 50;

    private final double x;
    private double y = -HEIGHT;
    private final ImageRegion sprite;
    
    public Enemy(ImageRegion sprite, double x){
        this.sprite = sprite;
        this.x = x;
    }

    public void update(double seconds, double speed){
        y += speed * seconds;
    }

    public boolean atBottom(double canvasHeight){
        return y + HEIGHT >= canvasHeight;
    }

    public boolean collidesWith(Player player){
        double horizontalInset = WIDTH * 0.15;
        double verticalInset = HEIGHT * 0.2;
        return player.intersects(
            x + horizontalInset,
            y + verticalInset,
            WIDTH - horizontalInset * 2,
            HEIGHT - verticalInset * 2
        );
    }

    public void draw(GameApp game){
        game.image(sprite, x, y, WIDTH, HEIGHT);
    }

}

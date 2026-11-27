import zero.ui.Label;
import zero.App;
import zero.Color;
import zero.ImageRegion;
import zero.GameApp;
import zero.ui.Button;
import zero.ui.Label;

public class Main extends GameApp {
    private ImageRegion toast;
    private Player player;
    private PlayerInput input;
    private boolean won;
    private double velocity;

    @Override public void settings() { 
        size(550, 700);
        title("Moon Toast");
     }

    public void setup() {
        var sheet = loadImage("/assets/toast.png");
        toast = imageRegion(sheet, 0, 0, 456, 434);
        input = new PlayerInput(this);
        restart();
        
    }

    public void restart(){
        player = new Player(toast);
        won = false;
    }

    @Override public void update(double seconds){
        int direction = input.direction();
        double maxSpeed = 3;
        double accel = 18;
        double deccel = 8;

        if(direction != 0){
            velocity += direction * accel * seconds;
            velocity = Math.max(-maxSpeed, Math.min(maxSpeed, velocity));
        }else{
            if(velocity > 0){
                velocity = Math.max(0, velocity - deccel * seconds);
            }else if(velocity < 0){
                velocity = Math.min(0, velocity + deccel * seconds);
            }
        }
        player.update(velocity, seconds, width());
    }

    @Override public void draw(){
        background(Color.rgb(79, 25, 96));
        player.draw(this);
    }

    public static void main(String[] args) { launch(args); }
}

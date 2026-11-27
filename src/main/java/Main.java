import zero.ui.Label;
import zero.Color;
import zero.ImageRegion;
import zero.GameApp;
import zero.ui.Button;
import zero.ui.Row;

public class Main extends GameApp {
    private ImageRegion toast;
    private ImageRegion cloud;
    private Player player;
    private Enemy enemy;
    private PlayerInput input;
    private boolean won;
    private double velocity;
    private ScoreDisplay score;
    private Label message;
    private int points;

    @Override public void settings() { 
        size(550, 700);
        title("Moon Toast");
     }

    public void setup() {
        var toastSheet = loadImage("/assets/toast.png");
        var cloudSheet = loadImage("/assets/cloud.png");
        toast = imageRegion(toastSheet, 0, 0, 456, 434);
        cloud = imageRegion(cloudSheet, 0, 0, 420, 200);
        input = new PlayerInput(this);
        score = new ScoreDisplay();
        score.setCaption("Score");
        message = new Label("Dodge the Clouds");
        var restartButton = new Button("Restart");
        restartButton.onClick(() -> restart());   
        controls(new Row(12, restartButton, score.view(), message));
        restart();
    }

    public void restart(){
        player = new Player(toast);
        enemy = new Enemy(cloud, 200, 100);
        won = false;
        message.setText("Dodge the Clouds");
        points = 0;
        score.setScore(points);
        showGame();
    }

    @Override public void update(double seconds){
        int direction = input.direction();
        double maxSpeed = 4;
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
        background(Color.rgb(4, 6, 27));
        player.draw(this);
        enemy.draw(this);
    }

    public static void main(String[] args) { launch(args); }
}

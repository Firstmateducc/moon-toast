import zero.ui.Label;
import zero.Color;
import zero.ImageRegion;
import zero.GameApp;
import zero.ui.Button;
import zero.ui.Row;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends GameApp {
    private ImageRegion toast;
    private ImageRegion cloud;
    private ImageRegion moon;
    private Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private PlayerInput input;
    private boolean won;
    private boolean lost;
    private double velocity;
    private ScoreDisplay score;
    private Label message;
    private int points;
    private double elapsedTime;
    private double spawnTimer;

    private static final double cloudSpawn = 2;
    private static final double cloudBaseSpeed = 100;
    private static final double cloudIncrease = 5;
    private static final int winningScore = 30;
    private static final double MOON_SIZE = 150;

    @Override public void settings() { 
        size(550, 700);
        title("Moon Toast");
     }

    public void setup() {
        var toastSheet = loadImage("/assets/toast.png");
        var cloudSheet = loadImage("/assets/cloud.png");
        moon = loadImageRegion("/assets/moon.png");
        toast = imageRegion(toastSheet, 0, 0, 456, 434);
        cloud = imageRegion(cloudSheet, 0, 0, 420, 200);
        input = new PlayerInput(this);
        score = new ScoreDisplay();
        score.setCaption("Score");
        message = new Label("Dodge the Clouds To reach the Moon!");
        var restartButton = new Button("Restart");
        restartButton.onClick(() -> restart());   
        controls(new Row(12, restartButton, score.view(), message));
        restart();
    }

    public void restart(){
        player = new Player(toast);
        enemies.clear();
        won = false;
        lost = false;
        message.setText("Dodge the Clouds To reach the Moon!");
        points = 0;
        score.setScore(points);
        velocity = 0;
        elapsedTime = 0;
        spawnTimer = 0;
        spawnCloud();
        showGame();
    }

    @Override public void update(double seconds){
        if(lost) return;
        if(won){
            player.flyTo(width() / 2, 20 + MOON_SIZE / 2, seconds);
            return;
        }
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

        elapsedTime += seconds;
        spawnTimer += seconds;
        while(spawnTimer >= cloudSpawn){
            spawnCloud();
            spawnTimer -= cloudSpawn;
        }

        double cloudSpeed = cloudBaseSpeed + elapsedTime * cloudIncrease;
        Iterator<Enemy> iterator = enemies.iterator();
        while(iterator.hasNext()){
            Enemy enemy = iterator.next();
            enemy.update(seconds, cloudSpeed);
            if(enemy.collidesWith(player)){
                lost = true;
                message.setText("You lost, Click Restart to try again!");
                return;
            }
            if(enemy.atBottom(height())){
                iterator.remove();
                points++;
                score.setScore(points);
                if(points >= winningScore){
                    won = true;
                    message.setText("You win! You reached the Moon!");
                    return;
                }
            }
        }
    }

    @Override public void draw(){
        background(Color.rgb(4, 6, 27));
        player.draw(this);
        for(Enemy enemy : enemies){
            enemy.draw(this);
        }
        if(won){
            image(moon, (width() - MOON_SIZE) / 2, 20, MOON_SIZE, MOON_SIZE);
        }
    }

    private void spawnCloud(){
        double maxX = width() - Enemy.WIDTH;
        double x = ThreadLocalRandom.current().nextDouble(maxX);
        enemies.add(new Enemy(cloud, x));
    }

    public static void main(String[] args) { launch(args); }
}

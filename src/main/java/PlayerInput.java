import zero.GameApp;
import zero.Key;

public class PlayerInput {
    private final GameApp game;

    public PlayerInput(GameApp game){
        this.game = game;
    }

    public int direction(){
        int direction = 0;
        if(game.keyDown(Key.LEFT) || game.keyDown(Key.A)) direction--;
        if(game.keyDown(Key.RIGHT) || game.keyDown(Key.D)) direction++;
        return direction;
    }
}

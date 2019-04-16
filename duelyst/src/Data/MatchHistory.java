package Data;

import java.util.ArrayList;

public class MatchHistory {
    private ArrayList<GameData> gameDatas = new ArrayList<>();

    public void addGameData(GameData gameData){
        gameDatas.add(gameData);
    }
}

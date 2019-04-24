package Data;

import java.util.ArrayList;

public class MatchHistory {
    private ArrayList<GameData> gameData = new ArrayList<>();

    public void addGameData(GameData gameData) {
        this.gameData.add(gameData);
    }

    public MatchHistory() {
    }

    public ArrayList<GameData> getGameData() {
        return gameData;
    }
}

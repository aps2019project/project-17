package Data;

import java.util.ArrayList;

public class MatchHistory {
    private ArrayList<GameData> gameData;

    public MatchHistory() {
        this.gameData = new ArrayList<>();
    }

    public void addGameData(GameData gameData) {
        this.gameData.add(gameData);
    }

    public ArrayList<GameData> getGameData() {
        return gameData;
    }
}

package Data;

import java.util.ArrayList;

class MatchHistory {
    private ArrayList<GameData> gameData;

    MatchHistory() {
        this.gameData = new ArrayList<>();
    }

    void addGameData(GameData gameData) {
        this.gameData.add(gameData);
    }
}

package Data;

import java.io.Serializable;
import java.util.ArrayList;

class MatchHistory implements Serializable {
    private ArrayList<GameData> gameData;

    MatchHistory() {
        this.gameData = new ArrayList<>();
    }

    void addGameData(GameData gameData) {
        this.gameData.add(gameData);
    }
}

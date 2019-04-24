package Data;

import java.time.LocalDateTime;

public class GameData {
    private String name;
    private Enum gameState;
    private LocalDateTime timeOfGame;

    public String getName() {
        return name;
    }

    public Enum getGameState() {
        return gameState;
    }

    public LocalDateTime getTimeOfGame() {
        return timeOfGame;
    }

    public GameData(String name, Enum gameState, LocalDateTime timeOfGame) {
        this.name = name;
        this.gameState = gameState;
        this.timeOfGame = timeOfGame;
    }
}

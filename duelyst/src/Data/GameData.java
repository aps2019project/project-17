package Data;

import java.time.LocalDateTime;

public class GameData {
    private String name;
    private LocalDateTime timeOfGame;
    private MatchState matchState;

    public String getName() {
        return name;
    }

    public LocalDateTime getTimeOfGame() {
        return timeOfGame;
    }

    public GameData(String name, LocalDateTime timeOfGame) {
        this.name = name;
        this.timeOfGame = timeOfGame;
    }

    public MatchState getMatchState() {
        return matchState;
    }

    public void setMatchState(MatchState matchState) {
        this.matchState = matchState;
    }
}

enum MatchState {
    WIN,
    LOSE,
    DRAW
}

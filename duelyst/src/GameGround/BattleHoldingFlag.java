package GameGround;

import Data.Player;

public class BattleHoldingFlag extends Battle {
    private Player whoHasFlag;
    private int timeHoldingFlag;

    public BattleHoldingFlag(Player playerOne, Player playerTwo, GameMode gameMode, BattleType battleType) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER, BattleType.HOLDING_FLAG);
    }
}

package effects;

import GameGround.Cell;

import java.util.ArrayList;

enum BuffType {POWER,}

public class Buff {
    private String desc;
    private int effectTime;
    private BuffData buffData;
    private ArrayList<Enum> buffEnums;

    private static enum BuffType {
        HOYLBUFF,
        POWERBUFF,
        POISONBUFF,
        WEAKNESSBUFF,
        STUNBUFF,
        DISARMBUFF
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEffectTime(int effectTime) {
        this.effectTime = effectTime;
    }

    public void action(ArrayList<Cell> cells) {

    }
}
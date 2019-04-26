package effects;

import GameGround.Cell;

import java.util.ArrayList;

public class Buff {
    private String desc;
    private ArrayList<BuffDetail> buffDetails;

    public Buff(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void action(ArrayList<Cell> cells) {
        for (BuffDetail buffdetail : buffDetails) {
            for (Cell cell : cells) {
                switch (buffdetail.getBuffType()) {
                    case HOYLBUFF:
                        holyBuff((Minion)cell.getCard());
                        break;
                    case WEAKNESSBUFF:

                        break;
                    case STUNBUFF:
                        stunBuff((Minion)cell.getCard());
                        break;
                    case DISARMBUFF:
                        disarmBuff((Minion)cell.getCard());
                        break;
                }
            }
        }

    }

    public void deletingWeaknessBuff(Minion minion, int time) {
        minion.changeHealth(time);
    }

    public void addBuff(BuffType buffType, int effectTime) {
        BuffDetail buffDetail = new BuffDetail(buffType, effectTime);
        this.buffDetails.add(buffDetail);
    }

    public void holyBuff(Minion minion) {
        minion.activeHolyBuff();
    }

    public void changeHealth(Minion minion, int changingValue) {
        minion.changeHealth(changingValue);
    }

    public void changeAttackPower(Minion minion, int changingValue) {
        minion.changeAttackPower(changingValue);
    }

    public void stunBuff(Minion minion) {
        minion.setCanMove(false);
    }

    public void disarmBuff(Minion minion) {
        minion.setCanCounterAttack(false);
    }
}
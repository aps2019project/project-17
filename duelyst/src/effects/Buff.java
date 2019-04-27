package effects;

import GameGround.Cell;

import java.util.ArrayList;

public class Buff {
    private String desc;
    private ArrayList<BuffDetail> buffDetails;
    private ArrayList<Minion> targetMinions = null;
    private ArrayList<BuffDetail> buffDetailsToRemove = null;

    public Buff(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void action(ArrayList<Cell> cells) {
        for (BuffDetail buffDetail : this.buffDetails) {
            for (Cell cell : cells) {
                switch (buffDetail.getBuffType()) {
                    case HOYLBUFF:
                        holyBuff((Minion) cell.getCard());
                        break;
                    case STUNBUFF:
                        stunBuff((Minion) cell.getCard());
                        break;
                    case DISARMBUFF:
                        disarmBuff((Minion) cell.getCard());
                        break;
                    case WEAKNESSBUFF:
                        targetMinions.add((Minion) cell.getCard());
                    case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                        changeHealthOrAttack((Minion) cell.getCard(), buffDetail.getChangeHealthValue(), buffDetail.getChangeAttackPowerValue());
                        break;
                    case CLEAR:
                        clearBuff((Minion) cell.getCard());
                        break;
                }
            }
            if (buffDetail.increaseEffectTime()) {
                buffDetailsToRemove.add(buffDetail);
            }
        }
        removeBuffs(buffDetailsToRemove);
    }

    private void removeBuffs(ArrayList<BuffDetail> buffDetails) {
        for (BuffDetail buffDetail : buffDetails) {
            if (buffDetail.getBuffType() == BuffType.WEAKNESSBUFF) {
                for (Minion targetMinion : targetMinions) {
                    deletingWeaknessBuff(targetMinion, buffDetail.getEffectTime());
                }
            }
        }
        this.buffDetails.removeAll(buffDetailsToRemove);
    }

    public void deletingWeaknessBuff(Minion minion, int time) {
        minion.changeHealth(time);
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buffDetails.add(buffDetail);
    }

    public void holyBuff(Minion minion) {
        minion.activeHolyBuff();
    }

    public void changeHealthOrAttack(Minion minion, int changeHealthValue, int changeAttackValue) {
        minion.changeHealth(changeHealthValue);
        minion.changeAttackPower(changeAttackValue);
    }

    public void stunBuff(Minion minion) {
        minion.setCanMove(false);
    }

    public void disarmBuff(Minion minion) {
        minion.setCanCounterAttack(false);
    }

    public void clearBuff(Minion minion) {
        minion.getBuff().clear();
    }

    private void clear() {
        this.buffDetails.clear();
    }
}
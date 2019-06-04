package Cards;

import Effects.Effect;
import GameGround.Battle;

import java.util.ArrayList;

public class Spell extends Card {
    private int manaPoint;
    private ArrayList<Effect> effects = new ArrayList<>();

    public Spell(String name, String id, int price, int manaPoint) {
        super(name, id, price);
        this.manaPoint = manaPoint;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - MP : " + this.manaPoint + " - Desc : " + this.desc);
    }

    public void action(int x, int y) {
        for (Effect effect : effects) {
            effect.action(Battle.getCurrentBattle().getCellFromBoard(x, y));
        }
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

}

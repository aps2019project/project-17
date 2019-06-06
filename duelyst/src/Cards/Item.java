package Cards;

import Effects.Effect;
import GameGround.Battle;

import java.util.ArrayList;

public class Item {
    private String name;
    private String id;
    private String desc;
    private int price;
    private ArrayList<Effect> effects = new ArrayList<>();
    private ArrayList<Effect> specialSituationBuff = new ArrayList<>();

    public Item(String name, String id, int price, String desc) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.desc = desc;
    }

    public void action(int x, int y) {
        for (Effect effect : effects) {
            effect.action(Battle.getCurrentBattle().getCellFromBoard(x, y));
        }
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void addSpecialSituationBuff(Effect effect) {
        specialSituationBuff.add(effect);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - Desc : " + this.desc);
    }

    public int getPrice() {
        return price;
    }
}

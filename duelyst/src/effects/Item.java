package effects;

import Data.Player;
import GameGround.Battle;
import GameGround.Cell;

import java.util.ArrayList;

public class Item {
    private String name;
    private String id;
    private String desc;
    private int price;
    private Buff buff;
    private BuffDetail specialSituationBuff;

    public Item(String name, String id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public void init() {
        this.buff = new Buff();
        this.specialSituationBuff = null;
    }

    public void action(int x, int y) {
        if (specialSituationBuff != null) {
            ((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard()).setSpecialSituationBuff(specialSituationBuff);
            ((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard()).setSpecialSituation(buff.getBuffDetails().get(0).getSituation());
        }
        buff.action(x, y, buff.getBuffDetails());
    }

    public void setSpecialSituationBuff(BuffDetail buffDetail) {
        this.specialSituationBuff = buffDetail;
    }

    public Buff getBuff() {
        return buff;
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buff.addBuff(buffDetail);
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

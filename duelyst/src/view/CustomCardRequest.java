package view;

import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import Effects.CellEffects.FireCell;
import Effects.CellEffects.HolyCell;
import Effects.CellEffects.Poison;
import Effects.Effect;
import Effects.MinionEffects.*;
import Effects.Player.ChangeMana;
import Effects.SpecialSituationBuff;
import Effects.enums.*;
import controller.GameController;

import java.io.IOException;
import java.util.Scanner;

class CustomCardRequest {

    static void checkSyntaxOfCustomCard(Scanner scanner) {
        String input;
        do {
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    try {
                        GameController.saveMinion(checkSyntaxOfMakeHero(scanner));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                        GameController.saveMinion(checkSyntaxOfMakeMinion(scanner));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    try {
                        GameController.saveItem(checkSyntaxOfMakeItem(scanner));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    try {
                        GameController.saveSpell(checkSyntaxOfMakeSpell(scanner));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    try {
                        GameController.saveEffect(checkSyntaxOfBuffMaker(scanner));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        while (!input.equals("end"));
    }

    private static Hero checkSyntaxOfMakeHero(Scanner scanner) {
        MinionType minionType = null;
        AttackType attackType = null;
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter id:");
        String id = scanner.next();
        System.out.println("Enter price:");
        int price = scanner.nextInt();
        System.out.println("Enter manaPoint:");
        int manaPoint = scanner.nextInt();
        System.out.println("Enter healthPoint:");
        int healthPoint = scanner.nextInt();
        System.out.println("Enter attackPower:");
        int attackPower = scanner.nextInt();
        System.out.println("Enter the number of minionType:");
        System.out.println("1.Melee");
        System.out.println("2.Ranged");
        System.out.println("3.Hybrid");
        String minionTypeString = scanner.next();
        switch (minionTypeString) {
            case "1":
                minionType = MinionType.MELEE;
                break;
            case "2":
                minionType = MinionType.RANGED;
                break;
            case "3":
                minionType = MinionType.HYBRID;
                break;
        }
        System.out.println("Enter attackRange:");
        int attackRange = scanner.nextInt();
        System.out.println("Enter distanceCanMove:");
        int distanceCanMove = scanner.nextInt();
        System.out.println("Enter maxRangeToInput:");
        int maxRangeToInput = scanner.nextInt();
        System.out.println("Enter the number of attackType:");
        System.out.println("1.ON_SPAWN");
        System.out.println("2.PASSIVE");
        System.out.println("3.ON_DEATH");
        System.out.println("4.ON_ATTACK");
        System.out.println("5.ON_DEFEND");
        System.out.println("6.ON_TURN");
        System.out.println("7.COMBO");
        System.out.println("8.NONE");
        String attackTypeString = scanner.next();
        switch (attackTypeString) {
            case "1":
                attackType = AttackType.ON_SPAWN;
                break;
            case "2":
                attackType = AttackType.PASSIVE;
                break;
            case "3":
                attackType = AttackType.ON_DEATH;
                break;
            case "4":
                attackType = AttackType.ON_ATTACK;
                break;
            case "5":
                attackType = AttackType.ON_DEFEND;
                break;
            case "6":
                attackType = AttackType.ON_TURN;
                break;
            case "7":
                attackType = AttackType.COMBO;
                break;
            case "8":
                attackType = AttackType.NONE;
                break;
        }
        System.out.println("Enter coolDown:");
        int coolDown = scanner.nextInt();
        System.out.println("Enter desc:");
        String desc = scanner.next();
        System.out.println("Hero created");
        return new Hero(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType, coolDown, desc);

    }

    private static Minion checkSyntaxOfMakeMinion(Scanner scanner) {
        MinionType minionType = null;
        AttackType attackType = null;
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter id:");
        String id = scanner.next();
        System.out.println("Enter price:");
        int price = scanner.nextInt();
        System.out.println("Enter manaPoint:");
        int manaPoint = scanner.nextInt();
        System.out.println("Enter healthPoint:");
        int healthPoint = scanner.nextInt();
        System.out.println("Enter attackPower:");
        int attackPower = scanner.nextInt();
        System.out.println("Enter the number of minionType:");
        System.out.println("1.Melee");
        System.out.println("2.Ranged");
        System.out.println("3.Hybrid");
        String minionTypeString = scanner.next();
        switch (minionTypeString) {
            case "1":
                minionType = MinionType.MELEE;
                break;
            case "2":
                minionType = MinionType.RANGED;
                break;
            case "3":
                minionType = MinionType.HYBRID;
                break;
        }
        System.out.println("Enter attackRange:");
        int attackRange = scanner.nextInt();
        System.out.println("Enter distanceCanMove:");
        int distanceCanMove = scanner.nextInt();
        System.out.println("Enter maxRangeToInput:");
        int maxRangeToInput = scanner.nextInt();
        System.out.println("Enter the number of attackType:");
        System.out.println("1.ON_SPAWN");
        System.out.println("2.PASSIVE");
        System.out.println("3.ON_DEATH");
        System.out.println("4.ON_ATTACK");
        System.out.println("5.ON_DEFEND");
        System.out.println("6.ON_TURN");
        System.out.println("7.COMBO");
        System.out.println("8.NONE");
        String attackTypeString = scanner.next();
        if ("1".equals(attackTypeString)) {
            attackType = AttackType.ON_SPAWN;
        } else if ("2".equals(attackTypeString)) {
            attackType = AttackType.PASSIVE;
        } else if ("3".equals(attackTypeString)) {
            attackType = AttackType.ON_DEATH;
        } else if ("4".equals(attackTypeString)) {
            attackType = AttackType.ON_ATTACK;
        } else if ("5".equals(attackTypeString)) {
            attackType = AttackType.ON_DEFEND;
        } else if ("6".equals(attackTypeString)) {
            attackType = AttackType.ON_TURN;
        } else if ("7".equals(attackTypeString)) {
            attackType = AttackType.COMBO;
        } else if ("8".equals(attackTypeString)) {
            attackType = AttackType.NONE;
        }
        System.out.println("Enter desc:");
        String desc = scanner.next();
        System.out.println("Minion created");
        return new Minion(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType, desc);

    }

    private static Item checkSyntaxOfMakeItem(Scanner scanner) {
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter id:");
        String id = scanner.next();
        System.out.println("Enter price:");
        int price = scanner.nextInt();
        System.out.println("Enter desc:");
        String desc = scanner.next();
        System.out.println("Item created");
        return new Item(name, id, price, desc);
    }

    private static Spell checkSyntaxOfMakeSpell(Scanner scanner) {
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter id:");
        String id = scanner.next();
        System.out.println("Enter price:");
        int price = scanner.nextInt();
        System.out.println("Enter manaPoint:");
        int manaPoint = scanner.nextInt();
        System.out.println("Enter desc:");
        String desc = scanner.next();
        System.out.println("Spell created");
        return new Spell(name, id, price, manaPoint, desc);
    }

    private static Effect checkSyntaxOfBuffMaker(Scanner scanner) {
        System.out.println("Enter the number of Buff you want to make:");
        printBuffTypes();
        String type = scanner.next();
        System.out.println("Enter id:");
        String id = scanner.next();
        System.out.println("Enter startTime:");
        int startTime = scanner.nextInt();
        System.out.println("Enter endTime:");
        int endTime = scanner.nextInt();
        System.out.println("Enter isContinues:  1.True  2.False");
        String result = scanner.next();
        boolean isContinues;
        isContinues = result.equals("1");
        System.out.println("Enter TargetRange:");
        printTargetRange();
        TargetRange targetRange;
        String targetRangeString = scanner.next();
        targetRange = switchForTargetRange(targetRangeString);
        System.out.println("Enter TargetType:");
        printTargetType();
        TargetType targetType;
        String targetTypeString = scanner.next();
        targetType = switchForTargetType(targetTypeString);
        System.out.println("Enter TargetDetail:");
        printTargetDetail();
        TargetDetail targetDetail;
        String targetDetailString = scanner.next();
        targetDetail = switchForTargetDetail(targetDetailString);
        BuffType buffType = null;
        if (type.equals("4")) {
            System.out.println("Enter BuffType");
            printBuffType();
            String buffTypeString = scanner.next();
            buffType = switchForBuffType(buffTypeString);
        }
        boolean buffModel = false;
        int changeHealthValue = 0;
        int changePowerValue = 0;
        if (type.equals("5")) {
            System.out.println("Enter Type of Buff: 1.PowerBuff 2.WeaknessBuff");
            String buffModelString = scanner.next();
            buffModel = buffModelString.equals("2");
            System.out.println("Enter ChangeHealthValue:");
            changeHealthValue = scanner.nextInt();
            System.out.println("Enter ChangePowerValue:");
            changePowerValue = scanner.nextInt();
        }
        int holyBuffState = 0;
        if (type.equals("8")) {
            System.out.println("Enter HolyBuffState:");
            holyBuffState = scanner.nextInt();
        }
        int manaChangeValue = 0;
        if (type.equals("10")) {
            System.out.println("Enter ManaChangeValue");
            manaChangeValue = scanner.nextInt();
        }
        SpecialSituation specialSituation = null;
        if (type.equals("11")) {
            System.out.println("Enter SpecialSituation");
            printSpecialSituation();
            String specialSituationString = scanner.next();
            specialSituation = switchForSpecialSituation(specialSituationString);
        }
        switch (type) {
            case "1":
                FireCell fireCell = new FireCell(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
                fireCell.setId(id);
                return fireCell;
            case "2":
                HolyCell holyCell = new HolyCell(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
                holyCell.setId(id);
                return holyCell;
            case "3":
                Poison poison = new Poison(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
                poison.setId(id);
                return poison;
            case "4":
                Anti anti = new Anti(startTime, endTime, isContinues, targetRange, targetType, targetDetail, buffType);
                anti.setId(id);
                return anti;
            case "5":
                ChangeProperties changeProperties = new ChangeProperties(startTime, endTime, isContinues, targetRange, targetType, targetDetail, changeHealthValue, changePowerValue, buffModel);
                changeProperties.setId(id);
                return changeProperties;
            case "6":
                Clear clear = new Clear(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
                clear.setId(id);
                return clear;
            case "7":
                Disarm disarm = new Disarm(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
                disarm.setId(id);
                return disarm;
            case "8":
                Holy holy = new Holy(startTime, endTime, isContinues, targetRange, targetType, targetDetail, holyBuffState);
                holy.setId(id);
                return holy;
            case "9":
                Stun stun = new Stun(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
                stun.setId(id);
                return stun;
            case "10":
                ChangeMana changeMana = new ChangeMana(startTime, endTime, isContinues, targetRange, targetType, targetDetail, manaChangeValue);
                changeMana.setId(id);
                return changeMana;
            case "11":
                SpecialSituationBuff specialSituationBuff = new SpecialSituationBuff(startTime, endTime, isContinues, targetRange, targetType, targetDetail, specialSituation);
                specialSituationBuff.setId(id);
                return specialSituationBuff;
        }
        return null;
    }

    private static SpecialSituation switchForSpecialSituation(String specialSituationString) {
        SpecialSituation specialSituation = null;
        switch (specialSituationString) {
            case "1":
                specialSituation = SpecialSituation.DIE;
                break;
            case "2":
                specialSituation = SpecialSituation.ATTACK;
                break;
            case "3":
                specialSituation = SpecialSituation.PUTT_IN_GROUND;
                break;
            case "4":
                specialSituation = SpecialSituation.NONE;
                break;
        }
        return specialSituation;
    }

    private static void printSpecialSituation() {
        System.out.println("1.DIE");
        System.out.println("2.ATTACK");
        System.out.println("3.PUTT_IN_GROUND");
        System.out.println("4.NONE");
    }

    private static BuffType switchForBuffType(String buffTypeString) {
        BuffType buffType = null;
        switch (buffTypeString) {
            case "1":
                buffType = BuffType.HOLY;
                break;
            case "2":
                buffType = BuffType.WEAKNESS;
                break;
            case "3":
                buffType = BuffType.STUN;
                break;
            case "4":
                buffType = BuffType.DISARM;
                break;
            case "5":
                buffType = BuffType.POWER_BUFF;
                break;
            case "6":
                buffType = BuffType.CLEAR;
                break;
            case "7":
                buffType = BuffType.FIRE_CELL;
                break;
            case "8":
                buffType = BuffType.POISON;
                break;
            case "9":
                buffType = BuffType.HOLY_CELL;
                break;
            case "10":
                buffType = BuffType.ANTI;
                break;
            case "11":
                buffType = BuffType.CHANGE_MANA;
                break;
            case "12":
                buffType = BuffType.SPECIAL_SITUATION_BUFF;
                break;
        }
        return buffType;
    }

    private static void printBuffType() {
        System.out.println("1.HOLY");
        System.out.println("2.WEAKNESS");
        System.out.println("3.STUN");
        System.out.println("4.DISARM");
        System.out.println("5.POWER_BUFF");
        System.out.println("6.CLEAR");
        System.out.println("7.FIRE_CELL");
        System.out.println("8.POISON_CELL");
        System.out.println("9.HOLY_CELL");
        System.out.println("10.ANTI");
        System.out.println("11.CHANGE_MANA");
        System.out.println("12.SPECIAL_SITUATION_BUFF");
    }

    private static TargetDetail switchForTargetDetail(String targetDetailString) {
        TargetDetail targetDetail = null;
        switch (targetDetailString) {
            case "1":
                targetDetail = TargetDetail.ENEMY;
                break;
            case "2":
                targetDetail = TargetDetail.INSIDER;
                break;
            case "3":
                targetDetail = TargetDetail.MELEE;
                break;
            case "4":
                targetDetail = TargetDetail.INSIDER_NOT_MELEE;
                break;
            case "5":
                targetDetail = TargetDetail.NONE;
        }
        return targetDetail;
    }

    private static void printTargetDetail() {
        System.out.println("1.ENEMY");
        System.out.println("2.INSIDER");
        System.out.println("3.MELEE");
        System.out.println("4.INSIDER_NOT_MELEE");
        System.out.println("5.NONE");
    }

    private static TargetType switchForTargetType(String targetTypeString) {
        TargetType targetType = null;
        switch (targetTypeString) {
            case "1":
                targetType = TargetType.CELL;
                break;
            case "2":
                targetType = TargetType.PLAYER;
                break;
            case "3":
                targetType = TargetType.MINION;
                break;
        }
        return targetType;
    }

    private static void printTargetType() {
        System.out.println("1.CELL");
        System.out.println("2.PLAYER");
        System.out.println("3.MINION");
    }

    private static TargetRange switchForTargetRange(String targetRangeString) {
        TargetRange targetRange = null;
        switch (targetRangeString) {
            case "1":
                targetRange = TargetRange.ONE;
                break;
            case "2":
                targetRange = TargetRange.TWO;
                break;
            case "3":
                targetRange = TargetRange.THREE;
                break;
            case "4":
                targetRange = TargetRange.ALL;
                break;
            case "5":
                targetRange = TargetRange.ALL_IN_COLUMN;
                break;
            case "6":
                targetRange = TargetRange.AROUND;
                break;
            case "7":
                targetRange = TargetRange.DISTANCE_TWO;
                break;
            case "8":
                targetRange = TargetRange.SELF;
                break;
            case "9":
                targetRange = TargetRange.CLOSEST_RANDOM;
                break;
            case "10":
                targetRange = TargetRange.RANDOM;
                break;
            case "11":
                targetRange = TargetRange.ENEMY_HERO;
                break;
            case "12":
                targetRange = TargetRange.INSIDER_HERO;
                break;
        }
        return targetRange;
    }

    private static void printTargetRange() {
        System.out.println("1.ONE");
        System.out.println("2.TWO");
        System.out.println("3.THREE");
        System.out.println("4.ALL");
        System.out.println("5.ALL_IN_COLUMN");
        System.out.println("6.AROUND");
        System.out.println("7.DISTANCE_TWO");
        System.out.println("8.SELF");
        System.out.println("9.CLOSEST_RANDOM");
        System.out.println("10.RANDOM");
        System.out.println("11.ENEMY_HERO");
        System.out.println("12.INSIDER_HERO");
    }

    private static void printBuffTypes() {
        System.out.println("1.FireCell");
        System.out.println("2.HolyCell");
        System.out.println("3.Poison");
        System.out.println("4.Anti");
        System.out.println("5.ChangeProperties");
        System.out.println("6.Clear");
        System.out.println("7.Disarm");
        System.out.println("8.Holy");
        System.out.println("9.Stun");
        System.out.println("10.ChangeMana");
        System.out.println("11.SpecialSituationBuff");
    }
}

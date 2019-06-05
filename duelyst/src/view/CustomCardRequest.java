package view;

import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import Effects.enums.AttackType;
import Effects.enums.MinionType;
import controller.GameController;

import java.io.IOException;
import java.util.Scanner;

class CustomCardRequest {

    static void checkSyntaxOfCustomCard(Scanner scanner) {
        String input;
        do {
            input=scanner.nextLine();
            switch (input) {
                case "1":
                    try {
                        GameController.saveMinion(checkSyntaxOfMakeHero(scanner), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                        GameController.saveMinion(checkSyntaxOfMakeMinion(scanner), false);
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
                    break;
            }
        }
        while (!input.equals("end"));
    }

    private static Hero checkSyntaxOfMakeHero(Scanner scanner){
        MinionType minionType=null;
        AttackType attackType=null;
        System.out.println("Enter name:");
        String name=scanner.next();
        System.out.println("Enter id:");
        String id=scanner.next();
        System.out.println("Enter price:");
        int price=scanner.nextInt();
        System.out.println("Enter manaPoint:");
        int manaPoint=scanner.nextInt();
        System.out.println("Enter healthPoint:");
        int healthPoint=scanner.nextInt();
        System.out.println("Enter attackPower:");
        int attackPower=scanner.nextInt();
        System.out.println("Enter the number of minionType:");
        System.out.println("1.Melee");
        System.out.println("2.Ranged");
        System.out.println("3.Hybrid");
        String minionTypeString=scanner.next();
        switch (minionTypeString){
            case "1":
                minionType=MinionType.MELEE;
                break;
            case "2":
                minionType=MinionType.RANGED;
                break;
            case "3":
                minionType=MinionType.HYBRID;
                break;
        }
        System.out.println("Enter attackRange:");
        int attackRange=scanner.nextInt();
        System.out.println("Enter distanceCanMove:");
        int distanceCanMove=scanner.nextInt();
        System.out.println("Enter maxRangeToInput:");
        int maxRangeToInput=scanner.nextInt();
        System.out.println("Enter the number of attackType:");
        System.out.println("1.ON_SPAWN");
        System.out.println("2.PASSIVE");
        System.out.println("3.ON_DEATH");
        System.out.println("4.ON_ATTACK");
        System.out.println("5.ON_DEFEND");
        System.out.println("6.ON_TURN");
        System.out.println("7.COMBO");
        System.out.println("8.NONE");
        String attackTypeString=scanner.next();
        switch (attackTypeString){
            case "1":
                attackType=AttackType.ON_SPAWN;
                break;
            case "2":
                attackType=AttackType.PASSIVE;
                break;
            case "3":
                attackType=AttackType.ON_DEATH;
                break;
            case "4":
                attackType=AttackType.ON_ATTACK;
                break;
            case "5":
                attackType=AttackType.ON_DEFEND;
                break;
            case "6":
                attackType=AttackType.ON_TURN;
                break;
            case "7":
                attackType=AttackType.COMBO;
                break;
            case "8":
                attackType=AttackType.NONE;
                break;
        }
        System.out.println("Enter coolDown:");
        int coolDown=scanner.nextInt();
        System.out.println("Enter desc:");
        String desc=scanner.next();
        System.out.println("Hero created");
        return new Hero(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType, coolDown, desc);

    }

    private static Minion checkSyntaxOfMakeMinion(Scanner scanner){
        MinionType minionType=null;
        AttackType attackType=null;
        System.out.println("Enter name:");
        String name=scanner.next();
        System.out.println("Enter id:");
        String id=scanner.next();
        System.out.println("Enter price:");
        int price=scanner.nextInt();
        System.out.println("Enter manaPoint:");
        int manaPoint=scanner.nextInt();
        System.out.println("Enter healthPoint:");
        int healthPoint=scanner.nextInt();
        System.out.println("Enter attackPower:");
        int attackPower=scanner.nextInt();
        System.out.println("Enter the number of minionType:");
        System.out.println("1.Melee");
        System.out.println("2.Ranged");
        System.out.println("3.Hybrid");
        String minionTypeString=scanner.next();
        switch (minionTypeString){
            case "1":
                minionType=MinionType.MELEE;
                break;
            case "2":
                minionType=MinionType.RANGED;
                break;
            case "3":
                minionType=MinionType.HYBRID;
                break;
        }
        System.out.println("Enter attackRange:");
        int attackRange=scanner.nextInt();
        System.out.println("Enter distanceCanMove:");
        int distanceCanMove=scanner.nextInt();
        System.out.println("Enter maxRangeToInput:");
        int maxRangeToInput=scanner.nextInt();
        System.out.println("Enter the number of attackType:");
        System.out.println("1.ON_SPAWN");
        System.out.println("2.PASSIVE");
        System.out.println("3.ON_DEATH");
        System.out.println("4.ON_ATTACK");
        System.out.println("5.ON_DEFEND");
        System.out.println("6.ON_TURN");
        System.out.println("7.COMBO");
        System.out.println("8.NONE");
        String attackTypeString=scanner.next();
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
        String desc=scanner.next();
        System.out.println("Minion created");
        return new Minion(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType, desc);

    }

    private static Item checkSyntaxOfMakeItem(Scanner scanner){
        System.out.println("Enter name:");
        String name=scanner.next();
        System.out.println("Enter id:");
        String id=scanner.next();
        System.out.println("Enter price:");
        int price=scanner.nextInt();
        System.out.println("Enter desc:");
        String desc=scanner.next();
        System.out.println("Item created");
        return new Item(name,id,price,desc);
    }

    private static Spell checkSyntaxOfMakeSpell(Scanner scanner){
        System.out.println("Enter name:");
        String name=scanner.next();
        System.out.println("Enter id:");
        String id=scanner.next();
        System.out.println("Enter price:");
        int price=scanner.nextInt();
        System.out.println("Enter manaPoint:");
        int manaPoint=scanner.nextInt();
        System.out.println("Enter desc:");
        String desc=scanner.next();
        System.out.println("Spell created");
        return new Spell(name, id, price, manaPoint, desc);
    }


}

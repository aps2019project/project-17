package Appearance;

import Cards.Minion;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinionAppearance {
    private int deathCount;
    private int breathingCount;
    private int runCount;
    private int idleCount;
    private int attackCount;
    private int hitCount;

    public class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Minion minion;
    private ImageView imageView;
    private Group root;
    private int width;
    private int height;
    private HashMap<Integer, Position> mapAttack = new HashMap<>();
    private HashMap<Integer, Position> mapIdle = new HashMap<>();
    private HashMap<Integer, Position> mapBreathing = new HashMap<>();
    private HashMap<Integer, Position> mapDeath = new HashMap<>();
    private HashMap<Integer, Position> mapRun = new HashMap<>();
    private HashMap<Integer, Position> mapHit = new HashMap<>();

    public MinionAppearance(Minion minion, String nameInFile, Group root) {
        this.minion = minion;
        String address = "selected/" + nameInFile;
        this.root = root;
        try {
            Image image = new Image(new FileInputStream(address + ".png"));
            this.imageView = new ImageView(image);
            FileReader fileReader = new FileReader(address + ".plist");
            StringBuilder data = new StringBuilder();
            int c;
            Pattern patternIndex = Pattern.compile("(?<name>attack|run|idle|breathing|death|hit)_\\d{3}.png");
            Matcher matcherIndex;
            Pattern patternPosition = Pattern.compile("<string>\\{\\{(?<x>\\d+),(?<y>\\d+)},\\{\\d+,\\d+}}</string>");
            Matcher matcherPosition;
            while ((c = fileReader.read()) != -1) {
                data.append((char) c);
            }
            this.attackCount = data.toString().split("attack").length - 1;
            this.idleCount = data.toString().split("idle").length - 1;
            this.runCount = data.toString().split("run").length - 1;
            this.breathingCount = data.toString().split("breathing").length - 1;
            this.deathCount = data.toString().split("death").length - 1;
            this.hitCount = data.toString().split("hit").length - 1;
            String copy = data.toString();
            int i = 1;
            int attackCounter = 0, runCounter = 0, idleCounter = 0, breathingCounter = 0, deathCounter = 0, hitCounter = 0;
            matcherIndex = patternIndex.matcher(copy);
            matcherPosition = patternPosition.matcher(copy);
            while (matcherIndex.find() && matcherPosition.find()) {
                int x = Integer.parseInt(matcherPosition.group("x"));
                int y = Integer.parseInt(matcherPosition.group("y"));
                switch (matcherIndex.group("name")) {
                    case "attack":
                        mapAttack.put(attackCounter, new Position(x, y));
                        attackCounter++;
                        break;
                    case "run":
                        mapRun.put(runCounter, new Position(x, y));
                        runCounter++;
                        break;
                    case "idle":
                        mapIdle.put(idleCounter, new Position(x, y));
                        idleCounter++;
                        break;
                    case "breathing":
                        mapBreathing.put(breathingCounter, new Position(x, y));
                        breathingCounter++;
                        break;
                    case "death":
                        mapDeath.put(deathCounter, new Position(x, y));
                        deathCounter++;
                        break;
                    case "hit":
                        mapHit.put(hitCounter, new Position(x, y));
                        hitCounter++;
                        break;
                }
                copy = copy.replaceFirst("<string>\\{\\{(?<x>\\d+),(?<y>\\d+)},\\{\\d+,\\d+}}</string>", "");
                copy = copy.replaceFirst("<string>\\{\\{(?<x>\\d+),(?<y>\\d+)},\\{\\d+,\\d+}}</string>", "");
                copy = copy.replaceFirst("(?<name>attack|run|idle|breathing|death|hit)_\\d{3}.png", "");
                matcherIndex = patternIndex.matcher(copy);
                matcherPosition = patternPosition.matcher(copy);
            }
            fileReader.close();
            Pattern pattern = Pattern.compile("<string>\\{(?<n>\\d{3}),(?<m>\\d{3})}</string>");
            Matcher matcher = pattern.matcher(data.toString());
            if (matcher.find()) {
                this.width = Integer.parseInt(matcher.group("n"));
                this.height = Integer.parseInt(matcher.group("m"));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int deltaX, int deltaY) {
        int duration = 100 * runCount;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), width, height, mapRun);
        Path path = new Path(new MoveTo(0, 0), new LineTo(deltaX, deltaY));
        PathTransition pathTransition = new PathTransition(Duration.millis(2000), path, imageView);
        root.getChildren().addAll(path);
        path.setVisible(false);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
        pathTransition.play();
    }

    public void breathing() {
        int duration = breathingCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), width, height, mapBreathing);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public void attack() {
        int duration = attackCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), width, height, mapAttack);
        animation.setCycleCount(1);
        animation.play();
    }

    public void death() {
        int duration = deathCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), width, height, mapDeath);
        animation.setCycleCount(1);
        animation.play();
    }

    public void idle() {
        int duration = idleCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), width, height, mapIdle);
        animation.setCycleCount(1);
        animation.play();
    }

    public void hit() {
        int duration = hitCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), width, height, mapHit);
        animation.setCycleCount(1);
        animation.play();
    }

    public void setLocation(double x, double y) {
        imageView.relocate(x, y);
    }

    public Minion getMinion() {
        return minion;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void add() {
        root.getChildren().add(imageView);
    }

    public void remove() {
        root.getChildren().remove(imageView);
    }
}

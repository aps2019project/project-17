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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinionAppearance {
    private Minion minion;
    private ImageView imageView;
    private Group root;
    private int attackCount;
    private int runCount;
    private int breathingCount;
    private int idleCount;
    private int deathCount;
    private int totalCount;
    private int width;
    private int height;
    private int attackStartIndex;
    private int breathingStartIndex;
    private int runStartIndex;
    private int idleStartIndex;
    private int deathStartIndex;
    private int columns = 12;

    public MinionAppearance(Minion minion, String nameInFile, Group root) {
        this.minion = minion;
        String address = "selected//" + nameInFile;
        this.root = root;
        try {
            Image image = new Image(new FileInputStream(address + ".png"));
            this.imageView = new ImageView(image);
            FileReader fileReader = new FileReader(address + ".plist");
            StringBuilder data = new StringBuilder();
            int c;
            Pattern patternIndex = Pattern.compile("(?<name>attack|run|idle|breathing|death)_\\d{3}.png");
            Matcher matcherIndex;
            while ((c = fileReader.read()) != -1) {
                data.append((char) c);
            }
            this.attackCount = data.toString().split("attack").length - 1;
            this.idleCount = data.toString().split("idle").length - 1;
            this.runCount = data.toString().split("run").length - 1;
            this.breathingCount = data.toString().split("breathing").length - 1;
            this.deathCount = data.toString().split("death").length - 1;
            totalCount = attackCount + idleCount + runCount + breathingCount + deathCount;
            int i = 0;
            String copy = data.toString();
            for (int j = 0; j < 5; j++) {
                matcherIndex = patternIndex.matcher(copy);
                if (matcherIndex.find()) {
                    if (matcherIndex.group("name").equals("attack")) {
                        copy = copy.replaceAll("attack", "");
                        attackStartIndex = i;
                        i += attackCount;
                    }
                    if (matcherIndex.group("name").equals("run")) {
                        copy = copy.replaceAll("run", "");
                        runStartIndex = i;
                        i += runCount;
                    }
                    if (matcherIndex.group("name").equals("death")) {
                        copy = copy.replaceAll("death", "");
                        deathStartIndex = i;
                        i += deathCount;
                    }
                    if (matcherIndex.group("name").equals("idle")) {
                        copy = copy.replaceAll("idle", "");
                        idleStartIndex = i;
                        i += idleCount;
                    }
                    if (matcherIndex.group("name").equals("breathing")) {
                        copy = copy.replaceAll("breathing", "");
                        breathingStartIndex = i;
                        i += breathingCount;
                    }
                }
            }
            fileReader.close();
            Pattern pattern = Pattern.compile("<string>\\{(?<n>\\d{3}),(?<m>\\d{3})}</string>");
            Matcher matcher = pattern.matcher(data.toString());
            if (matcher.find()) {
                this.width = Integer.parseInt(matcher.group("n"));
                this.height = Integer.parseInt(matcher.group("m"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.getChildren().add(imageView);
    }

    public void move(int startX, int startY, int endX, int endY) {
        int duration = 100 * runCount;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), columns, 0, 0, width, height, runStartIndex + runCount, runStartIndex, totalCount);
        Path path = new Path(new MoveTo(startX, startY), new LineTo(endX, endY));
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
        System.out.println(breathingStartIndex);
        System.out.println(breathingCount);
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), columns, 0, 0, width, height, breathingStartIndex, breathingStartIndex + breathingCount, totalCount);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public void attack() {
        int duration = attackCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), columns, 0, 0, width, height, attackStartIndex + attackCount, attackStartIndex, totalCount);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public void death() {
        int duration = deathCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), columns, 0, 0, width, height, deathStartIndex + deathCount, deathStartIndex, totalCount);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public void idle() {
        int duration = idleCount * 100;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), columns, 0, 0, width, height, idleStartIndex + idleCount, idleStartIndex, totalCount);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public void setLocation(int x, int y) {
        imageView.relocate(x, y);
    }
}

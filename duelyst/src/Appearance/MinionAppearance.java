package Appearance;

import Cards.Minion;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    private VBox root;
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

    public MinionAppearance(Minion minion, String nameInFile, VBox root) {
        this.minion = minion;
        String address = "boss_" + nameInFile;
        this.root = root;
        try {
            Image image = new Image(new FileInputStream(address + ".png"));
            this.imageView = new ImageView(image);
            FileReader fileReader = new FileReader(address + ".plist");
            StringBuilder data = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1)
                data.append((char) c);
            fileReader.close();
            Pattern pattern = Pattern.compile("<string>\\{(?<n>\\d{3}),(?<m>\\d{3})}</string>");
            Matcher matcher = pattern.matcher(data.toString());
            if (matcher.find()) {
                this.width = Integer.parseInt(matcher.group("n"));
                this.height = Integer.parseInt(matcher.group("m"));
            }
            attackStartIndex = data.indexOf("attack");
            runStartIndex = data.indexOf("run");
            deathStartIndex = data.indexOf("death");
            breathingStartIndex = data.indexOf("breathing");
            idleStartIndex = data.indexOf("idle");
            this.attackCount = data.toString().split("attack").length - 1;
            this.idleCount = data.toString().split("idle").length - 1;
            this.runCount = data.toString().split("run").length - 1;
            this.breathingCount = data.toString().split("breathing").length - 1;
            this.deathCount = data.toString().split("death").length - 1;
            totalCount = attackCount + idleCount + runCount + breathingCount + deathCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int startX, int startY, int endX, int endY) {
        Animation animation = new SpriteAnimation(imageView, Duration.millis(2000), runCount, 12, 0, 0, width, height, attackStartIndex, attackStartIndex + attackCount, totalCount);
        Path path = new Path(new MoveTo(startX, startY), new LineTo(endX, endY));
        PathTransition pathTransition = new PathTransition(Duration.millis(2000), path, imageView);
        root.getChildren().addAll(imageView, path);
        animation.play();
        pathTransition.play();
    }

    public void brething() {
        Animation animation = new SpriteAnimation(imageView, Duration.millis(2000), breathingCount, 12, 0, 0, width, height, breathingStartIndex, breathingStartIndex + breathingCount, totalCount);
        root.getChildren().add(imageView);
        animation.play();
    }
}

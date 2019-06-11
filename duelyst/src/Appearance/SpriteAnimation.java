package Appearance;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private final int width;
    private final int height;
    private HashMap<Integer, MinionAppearance.Position> map;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int width, int height, HashMap<Integer, MinionAppearance.Position> map) {
        this.imageView = imageView;
        this.width = width;
        this.height = height;
        this.map = map;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = (int) Math.floor(k * (map.size() - 1));
        final int x = map.get(index).x;
        final int y = map.get(index).y;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}

package hedspi.group01.force.controller.utils;

import javafx.animation.AnimationTimer;

public abstract class GameAnimationTimer extends AnimationTimer {
    private long lastFrameTimeNanos = 0;

    @Override
    public void handle(long now) {
        if (lastFrameTimeNanos == 0) {
            lastFrameTimeNanos = now;
            return;
        }

        double secondsSinceLastFrame = (now - lastFrameTimeNanos) / 1e9;
        lastFrameTimeNanos = now;

        tick(secondsSinceLastFrame);
    }

    public abstract void tick(double secondsSinceLastFrame);

    public void start() {
        lastFrameTimeNanos = 0;
        super.start();
    }

    public void play() {
        super.start();
    }

    public void pause() {
        super.stop();
    }

    public void stop() {
        super.stop();
        lastFrameTimeNanos = 0;
    }
}

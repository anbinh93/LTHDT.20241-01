package hedspi.group01.force.controller;

import hedspi.group01.force.controller.utils.GameAnimationTimer;
import hedspi.group01.force.model.Simulation;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
public class AnimationController {

    // Constants
    private static final int BACKGROUND_WIDTH = 2000;
    private static final float FAR_RATIO = 200.0f;
    private static final int NEAR_DURATION_MS = 15000;

    // Model and timer
    private GameAnimationTimer timer;
    private Simulation simulation;

    // UI elements (injected from FXML)
    @FXML
    private StackPane topStackPane, downStackPane;
    @FXML
    private ImageView backGroundMiddleUp, backGroundRightUp;
    @FXML
    private ImageView backGroundMiddleDown, backGroundRightDown;

    // Transitions for backgrounds
    private ParallelTransition parallelTransitionUp;
    private ParallelTransition parallelTransitionDown;

    public void init(Simulation sim) {
        this.simulation = sim;
        setupTransition();
        setupTimer();
        setupBinding();
        setupResponsiveUI();
    }

    // Configure how backgrounds should translate over time
    private void setupTransition() {
        // Create translate transitions for top backgrounds (far backgrounds)
        TranslateTransition translateRU = buildTranslateTransition(
                (long)(NEAR_DURATION_MS * FAR_RATIO), backGroundRightUp, -BACKGROUND_WIDTH
        );
        TranslateTransition translateMU = buildTranslateTransition(
                (long)(NEAR_DURATION_MS * FAR_RATIO), backGroundMiddleUp, -BACKGROUND_WIDTH
        );

        // Create translate transitions for bottom backgrounds (near backgrounds)
        TranslateTransition translateMD = buildTranslateTransition(
                NEAR_DURATION_MS, backGroundMiddleDown, -BACKGROUND_WIDTH
        );
        TranslateTransition translateRD = buildTranslateTransition(
                NEAR_DURATION_MS, backGroundRightDown, -BACKGROUND_WIDTH
        );

        // Group them into parallel transitions
        parallelTransitionUp = new ParallelTransition(translateRU, translateMU);
        parallelTransitionUp.setCycleCount(Animation.INDEFINITE);

        parallelTransitionDown = new ParallelTransition(translateMD, translateRD);
        parallelTransitionDown.setCycleCount(Animation.INDEFINITE);

        // Initial rate is set to zero so they stay still
        parallelTransitionUp.setRate(0.0);
        parallelTransitionDown.setRate(0.0);
    }

    // Helper method to build a TranslateTransition
    private TranslateTransition buildTranslateTransition(long durationMillis, ImageView target, double toX) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(durationMillis), target);
        tt.setFromX(0);
        tt.setToX(toX);
        tt.setInterpolator(Interpolator.LINEAR);
        return tt;
    }

    // Configure the custom game timer
    private void setupTimer() {
        timer = new GameAnimationTimer() {
            @Override
            public void tick(double secondsSinceLastFrame) {
                simulation.applyForceInTime(secondsSinceLastFrame);
            }
        };
    }

    // Bind the transitions' rate to the simulation velocity
    private void setupBinding() {
        simulation.sysVelProperty().addListener((obs, oldVal, newVal) -> {
            parallelTransitionUp.rateProperty().bind(
                    Bindings.when(newVal.valueProperty().isEqualTo(0, 0.2))
                            .then(10e-5 * 5)
                            .otherwise(newVal.valueProperty().multiply(0.05))
            );

            parallelTransitionDown.rateProperty().bind(
                    Bindings.when(newVal.valueProperty().isEqualTo(0, 0.2))
                            .then(10e-5 * 5)
                            .otherwise(newVal.valueProperty().multiply(0.05))
            );
        });
    }

    // Make images responsive to container size
    private void setupResponsiveUI() {
        backGroundRightUp.fitHeightProperty().bind(topStackPane.heightProperty());
        backGroundMiddleUp.fitHeightProperty().bind(topStackPane.heightProperty());
        backGroundRightDown.fitHeightProperty().bind(downStackPane.heightProperty());
        backGroundMiddleDown.fitHeightProperty().bind(downStackPane.heightProperty());
    }

    // Lifecycle controls
    public void startAnimation() {
        parallelTransitionUp.play();
        parallelTransitionDown.play();
        timer.start();
    }

    public void continueAnimation() {
        parallelTransitionUp.play();
        parallelTransitionDown.play();
        timer.play();
    }

    public void pauseAnimation() {
        parallelTransitionUp.pause();
        parallelTransitionDown.pause();
        timer.pause();
    }

    public void resetAnimation() {
        parallelTransitionUp.jumpTo(Duration.ZERO);
        parallelTransitionDown.jumpTo(Duration.ZERO);
        parallelTransitionUp.stop();
        parallelTransitionDown.stop();
        timer.stop();
    }
}

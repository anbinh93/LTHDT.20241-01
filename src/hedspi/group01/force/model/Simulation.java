package hedspi.group01.force.model;

import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.AppliedForce;
import hedspi.group01.force.model.vector.FrictionForce;
import hedspi.group01.force.model.vector.HorizontalVector;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The `Simulation` class aggregates and manages the `MainObject`, `Surface`, `AppliedForce`,
 * and associated forces. It oversees all operations related to these objects, such
 * as applying forces, updating object states, and handling the simulation's lifecycle.
 */
public class Simulation {

    // Simulation state properties
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);
    private final BooleanProperty isPaused = new SimpleBooleanProperty(true);

    // Core simulation properties and forces
    private final ObjectProperty<MainObject> mainObject = new SimpleObjectProperty<>();
    private final ObjectProperty<HorizontalVector> systemVelocity = new SimpleObjectProperty<>(new HorizontalVector(0, 1));
    private final ObjectProperty<HorizontalVector> systemAcceleration = new SimpleObjectProperty<>(new HorizontalVector(0, 1));
    private final DoubleProperty systemAngularAcceleration = new SimpleDoubleProperty(0);
    private final DoubleProperty systemAngularVelocity = new SimpleDoubleProperty(0);

    // Forces and surface
    private HorizontalVector netForce = new HorizontalVector(0, 1);
    private Surface surface;
    private AppliedForce appliedForce;
    private FrictionForce frictionForce;

    private AppliedForce aForce;
    private FrictionForce fForce;
    private BooleanProperty isStart = new SimpleBooleanProperty(false);
    private BooleanProperty isPause = new SimpleBooleanProperty(false);
    private DoubleProperty sysVel = new SimpleDoubleProperty(0.0);
    private DoubleProperty sysAcc = new SimpleDoubleProperty(0.0);
    private DoubleProperty sysAngVel = new SimpleDoubleProperty(0.0);
    private DoubleProperty sysAngAcc = new SimpleDoubleProperty(0.0);

    /**
     * Default constructor initializes the `Simulation` with default `Surface`, `AppliedForce`, and `FrictionForce`.
     */
    public Simulation() {
        try {
            this.surface = new Surface(Surface.MAX_STATIC_COEFFICIENT / 2, Surface.MAX_STATIC_COEFFICIENT / 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.appliedForce = new AppliedForce(0, 1);
        this.frictionForce = new FrictionForce(0, 1, surface.getStaticCoefficient(), surface.getKineticCoefficient());
    }

    /**
     * Constructor with `MainObject`, `Surface`, and `AppliedForce` explicitly specified.
     *
     * @param mainObject   The object's main simulation subject.
     * @param surface      The surface on which the simulation occurs.
     * @param appliedForce The force applied to the object.
     */
    public Simulation(MainObject mainObject, Surface surface, AppliedForce appliedForce) {
        this.mainObject.set(mainObject);
        this.surface = surface;
        this.appliedForce = appliedForce;
        this.frictionForce = new FrictionForce(0, 1, surface.getStaticCoefficient(), surface.getKineticCoefficient());
        updateNetForce();
    }

    //--- System Velocity and Acceleration Management ---
    public ObjectProperty<HorizontalVector> systemVelocityProperty() {
        return systemVelocity;
    }

    public void setSystemVelocity(HorizontalVector velocity) {
        this.systemVelocity.set(velocity);
    }

    public ObjectProperty<HorizontalVector> systemAccelerationProperty() {
        return systemAcceleration;
    }

    public void setSystemAcceleration(HorizontalVector acceleration) {
        this.systemAcceleration.set(acceleration);
    }

    public DoubleProperty systemAngularAccelerationProperty() {
        return systemAngularAcceleration;
    }

    public void setSystemAngularAcceleration(double angularAcceleration) {
        this.systemAngularAcceleration.set(angularAcceleration);
    }

    public DoubleProperty systemAngularVelocityProperty() {
        return systemAngularVelocity;
    }

    public void setSystemAngularVelocity(double angularVelocity) {
        this.systemAngularVelocity.set(angularVelocity);
    }

    //--- Main Object Management ---
    public ObjectProperty<MainObject> mainObjectProperty() {
        return mainObject;
    }

    public MainObject getMainObject() {
        return this.mainObject.get();
    }

    public void setMainObject(MainObject newObject) {
        this.mainObject.set(newObject);
        if (newObject == null) {
            systemAcceleration.set(new HorizontalVector(0, 1));
            systemVelocity.set(new HorizontalVector(0, 1));
        } else {
            systemAcceleration.set(new HorizontalVector(newObject.getAcceleration(), 0));
            systemVelocity.set(new HorizontalVector(newObject.getVelocity(), 0));
        }
    }

    public ObjectProperty<MainObject> objProperty() {
        return mainObject;
    }

    public MainObject getObj() {
        return mainObject.get();
    }

    public void setObject(MainObject obj) {
        this.mainObject.set(obj);
    }

    //--- Simulation State Management ---
    public BooleanProperty isPausedProperty() {
        return isPaused;
    }

    public boolean isPaused() {
        return isPaused.get();
    }

    public void setPaused(boolean paused) {
        this.isPaused.set(paused);
    }

    public BooleanProperty isRunningProperty() {
        return isRunning;
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void setRunning(boolean running) {
        this.isRunning.set(running);
    }

    //--- Force and Surface Management ---
    public Surface getSurface() {
        return surface;
    }

    public AppliedForce getAppliedForce() {
        return appliedForce;
    }

    public void updateAppliedForce(double magnitude, double direction) {
        this.appliedForce.setMagnitude(magnitude);
        this.appliedForce.setDirection(direction);
    }

    public FrictionForce getFrictionForce() {
        return frictionForce;
    }

    public HorizontalVector getNetForce() {
        return netForce;
    }

    public void updateNetForce() {
        this.netForce = HorizontalVector.sum(appliedForce, frictionForce);
    }

    //--- Simulation Control Methods ---
    public void start() {
        setRunning(true);
        setPaused(false);
    }

    public void pause() {
        setPaused(true);
    }

    public void resume() {
        setPaused(false);
    }

    public void restart() {
        setRunning(false);
        setPaused(true);
        setMainObject(null);
        appliedForce.setMagnitude(0);
        frictionForce.setMagnitude(0);

        try {
            surface.setStaticCoefficient(Surface.MAX_STATIC_COEFFICIENT / 2);
            surface.setKineticCoefficient(Surface.MAX_STATIC_COEFFICIENT / 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void conti() {
        // Implementation of the conti method
        // This method should continue the simulation from where it was paused
        setPaused(false);
    }

    //--- Object Updates ---
    public void applyForceToObject(double timeInterval) {
        if (mainObject.get() != null) {
            mainObject.get().applyForceInTime(netForce, frictionForce, timeInterval);
        }
    }

    public AppliedForce getaForce() {
        return aForce;
    }

    public FrictionForce getfForce() {
        return fForce;
    }

    public BooleanProperty isStartProperty() {
        return isStart;
    }

    public BooleanProperty isPauseProperty() {
        return isPause;
    }

    public DoubleProperty sysVelProperty() {
        return sysVel;
    }

    public DoubleProperty sysAccProperty() {
        return sysAcc;
    }

    public DoubleProperty getSysAngVel() {
        return sysAngVel;
    }

    public DoubleProperty getSysAngAcc() {
        return sysAngAcc;
    }

    public void setaForce(double value) {
        aForce.setValue(value);
    }

    public boolean getIsStart() {
        return isStart.get();
    }

    public void setIsStart(boolean isStart) {
        this.isStart.set(isStart);
    }

    public boolean getIsPause() {
        return isPause.get();
    }
}
package hedspi.group01.force.model;

import hedspi.group01.force.model.vector.AppliedForce;
import hedspi.group01.force.model.vector.FrictionForce;
import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.Force;
import hedspi.group01.force.model.vector.HorizontalVector;
import hedspi.group01.force.model.vector.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import jdk.jfr.FlightRecorder;

/**
 * Simulation aggregates MainObject, Surface, AppliedForce, and FrictionForce,
 * managing the lifecycle (start, pause, resume, restart) and net force updates.
 * It also exposes system-level velocity/acceleration properties for external binding.
 */
public class Simulation {

    // -- Simulation State --
    private final BooleanProperty started = new SimpleBooleanProperty(false);
    private final BooleanProperty paused  = new SimpleBooleanProperty(true);

    // The primary object in this simulation
    private final ObjectProperty<MainObject> mainObj = new SimpleObjectProperty<>();

    // System-level linear kinematics (tied to mainObj if not null)
    private final ObjectProperty<HorizontalVector> sysVel = new SimpleObjectProperty<>(new HorizontalVector(0.0));
    private final ObjectProperty<HorizontalVector> sysAcc = new SimpleObjectProperty<>(new HorizontalVector(0.0));

    // System-level angular kinematics (optional)
    private final DoubleProperty sysAngAcc = new SimpleDoubleProperty(0.0);
    private final DoubleProperty sysAngVel = new SimpleDoubleProperty(0.0);

    // Forces involved in this simulation
    private Force netForce;
    private AppliedForce aForce;
    private FrictionForce fForce;

    // Surface providing friction coefficients
    private Surface surface;

    // -------------------------------------
    //             CONSTRUCTORS
    // -------------------------------------

    /**
     * Default constructor initializes with a null MainObject,
     * default Surface, and zero AppliedForce.
     */
    public Simulation() {
        this(null, new Surface(), new AppliedForce(0.0));
    }

    /**
     * Constructor specifying the main object, surface, and applied force.
     *
     * @param obj     MainObject for the simulation (can be null).
     * @param surface Surface defining friction coefficients.
     * @param aForce  External applied force (non-friction).
     */
    public Simulation(MainObject obj, Surface surface, AppliedForce aForce) {
        this.surface = (surface != null) ? surface : new Surface();
        this.aForce  = (aForce  != null) ? aForce  : new AppliedForce(0.0);
        this.fForce  = new FrictionForce(0.0, this.surface, obj, this.aForce);
        this.netForce = new Force(0.0);

        setObject(obj);      // Link object to system velocity/acc
        updateNetForce();    // Calculate initial net force
    }

    // -------------------------------------
    //           MAIN OBJECT LOGIC
    // -------------------------------------

    /**
     * Sets the main object for this simulation and updates the system velocity
     * and acceleration references accordingly. Null object resets sysAcc/sysVel.
     */
    public void setObject(MainObject obj) {
        mainObj.set(obj);
        if (obj == null) {
            sysAcc.set(new HorizontalVector(0.0));
            sysVel.set(new HorizontalVector(0.0));
        } else {
            // Reflect the object's current acceleration/velocity in sysAcc/sysVel
            sysAcc.set(obj.getAcceleration());
            sysVel.set(obj.getVelocity());
        }
        updateNetForce();
    }

    /**
     * @return The current MainObject being simulated (or null if none).
     */
    public MainObject getObject() {
        return mainObj.get();
    }

    public ObjectProperty<MainObject> objectProperty() {
        return mainObj;
    }

    // -------------------------------------
    //         SYSTEM VELOCITY/ACC
    // -------------------------------------

    public ObjectProperty<HorizontalVector> sysVelProperty() {
        return sysVel;
    }

    public ObjectProperty<HorizontalVector> sysAccProperty() {
        return sysAcc;
    }

    // -------------------------------------
    //       SYSTEM ANGULAR PROPERTIES
    // -------------------------------------

    public DoubleProperty sysAngAccProperty() {
        return sysAngAcc;
    }

    public DoubleProperty sysAngVelProperty() {
        return sysAngVel;
    }

    // -------------------------------------
    //           FORCE MANAGEMENT
    // -------------------------------------

    /**
     * @return The net external force acting on the MainObject in this simulation.
     */
    public Force getNetForce() {
        return netForce;
    }

    /**
     * @return The applied (external) force object in this simulation.
     */
    public Force getAppliedForce() {
        return aForce;
    }

    /**
     * @return The friction force object in this simulation.
     */
    public Force getFrictionForce() {
        return fForce;
    }

    /**
     * Sets a new value for the applied external force and re-calculates the net force.
     */
    public void setAppliedForce(double value) {
        aForce.setValue(value);
        updateNetForce();
    }

    // -------------------------------------
    //            SURFACE/FRICTION
    // -------------------------------------

    public Surface getSurface() {
        return surface;
    }

    // -------------------------------------
    //        SIMULATION FLOW CONTROL
    // -------------------------------------

    public BooleanProperty startedProperty() {
        return started;
    }

    public BooleanProperty pausedProperty() {
        return paused;
    }

    public boolean isStarted() {
        return started.get();
    }

    public void setStarted(boolean value) {
        started.set(value);
    }

    public boolean isPaused() {
        return paused.get();
    }

    public void setPaused(boolean value) {
        paused.set(value);
    }

    public void start() {
        setStarted(true);
        setPaused(false);
    }

    public void pause() {
        setPaused(true);
    }

    public void resume() {
        setPaused(false);
    }

    /**
     * Resets the simulation to a default state:
     * - Clears the MainObject
     * - Resets applied force and friction to zero
     * - Restores surface friction coefficients to defaults
     * - Marks simulation as not started and paused
     */
    public void restart() {
        setStarted(false);
        setPaused(true);

        setObject(null);
        aForce.setValue(0.0);
        fForce.setValue(0.0);

        try {
            surface.setStaCoef(Surface.MAX_STA_COEF / 2);
            surface.setKiCoef(Surface.MAX_STA_COEF / 4);
        } catch (Exception e) {
            // fallback attempt
            try {
                surface.setKiCoef(Surface.MAX_STA_COEF / 4);
                surface.setStaCoef(Surface.MAX_STA_COEF / 2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // -------------------------------------
    //      PHYSICS UPDATES EACH FRAME
    // -------------------------------------

    /**
     * Updates the net force from applied + friction. Call whenever aForce or fForce change.
     */
    public void updateNetForce() {
        // friction depends on object, surface, and applied force
        Force combined = Force.sumTwoForce(aForce, fForce);
        netForce.setValue(combined.getValue());
    }

    /**
     * Applies the net force and friction to the MainObject over a time interval t.
     * This internally calls {@code mainObj.applyForceInTime(netForce, friction, t)}
     * if a MainObject is present.
     *
     * @param t Time step in seconds since last update.
     */
    public void applyForceInTime(double t) {
        MainObject obj = getObject();
        if (obj != null) {
            obj.applyForceInTime(getNetForce(), getFrictionForce(), t);
        }
    }

    public HorizontalVector getaForce() {
    }

    public ObjectProperty<MainObject> objProperty() {
        return this.obj;
    }

    public void setaForce(double aForce) {
        this.aForce.setValue(aForce);
    }

    public Object getfForce() {
        return fForce;
    }
}



package hedspi.group01.force.model.object;
import hedspi.group01.force.model.vector.Force;
import hedspi.group01.force.model.vector.HorizontalVector;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * Abstract class representing a physical object with mass, horizontal velocity,
 * horizontal acceleration, and position.
 */
public abstract class MainObject {

    // Default mass constant
    public static final double DEFAULT_MASS = 50.0;

    // Mass of the object (must be > 0)
    private final DoubleProperty mass = new SimpleDoubleProperty(DEFAULT_MASS);

    // Horizontal acceleration and velocity properties
    private final HorizontalVector acceleration = new HorizontalVector(0.0);
    private final HorizontalVector velocity = new HorizontalVector(0.0);

    // Position along a single axis
    private final DoubleProperty position = new SimpleDoubleProperty(0.0);

    /**
     * Default constructor with a pre-defined mass.
     */
    public MainObject() {
        // mass defaults to 50
    }

    /**
     * Constructor allowing a user-defined mass.
     *
     * @param mass The initial mass of this MainObject (must be > 0).
     * @throws IllegalArgumentException if mass <= 0
     */
    public MainObject(double mass) {
        setMass(mass);
    }

    // -- Mass Handling --
    public DoubleProperty massProperty() {
        return mass;
    }

    public double getMass() {
        return mass.get();
    }

    public void setMass(double newMass) {
        if (newMass <= 0) {
            throw new IllegalArgumentException("Object's mass must be > 0");
        }
        mass.set(newMass);
    }

    // -- Acceleration and Velocity --
    public HorizontalVector getAcceleration() {
        return acceleration;
    }

    public HorizontalVector getVelocity() {
        return velocity;
    }

    public void setAcceleration(double accValue) {
        acceleration.setValue(accValue);
    }

    public void setVelocity(double velValue) {
        velocity.setValue(velValue);
    }

    /**
     * Updates the acceleration of this object based on the applied force and mass.
     * Uses F = m * a, so a = F / m.
     *
     * @param force The net force applied horizontally.
     */
    public void updateAcceleration(Force force) {
        setAcceleration(force.getValue() / getMass());
    }

    /**
     * Updates the velocity over a given time interval using basic kinematics:
     * v_new = v_old + (a * t).
     *
     * If the velocity crosses zero in that interval (sign change),
     * the velocity is clamped to 0 (prevents overshoot).
     *
     * @param deltaTime The time interval in seconds.
     */
    public void updateVelocity(double deltaTime) {
        double oldVel = getVelocity().getValue();
        double newVel = oldVel + getAcceleration().getValue() * deltaTime;
        setVelocity((oldVel * newVel < 0) ? 0 : newVel);
    }

    // -- Position --
    public DoubleProperty positionProperty() {
        return position;
    }

    public double getPosition() {
        return position.get();
    }

    public void setPosition(double posValue) {
        position.set(posValue);
    }

    /**
     * Updates position based on uniform acceleration:
     * x_new = x_old + v_old * t + 0.5 * a * t^2
     *
     * @param oldVel The velocity at the start of the interval.
     * @param deltaTime The time interval in seconds.
     */
    public void updatePosition(double oldVel, double deltaTime) {
        double displacement = oldVel * deltaTime + 0.5 * getAcceleration().getValue() * deltaTime * deltaTime;
        setPosition(getPosition() + displacement);
    }

    /**
     * Applies both net force and friction force over a time interval, updating
     * acceleration, velocity, and position accordingly.
     *
     * @param netForce The net external force applied.
     * @param friction The frictional force applied.
     * @param deltaTime The time interval for integration.
     */
    public void applyForceInTime(Force netForce, Force friction, double deltaTime) {
        double oldVel = getVelocity().getValue();
        updateAcceleration(netForce);
        updateVelocity(deltaTime);
        updatePosition(oldVel, deltaTime);
    }

    public HorizontalVector accProperty() {
        return acceleration;
    }

    public HorizontalVector velProperty() {
        return velocity;
    }

}






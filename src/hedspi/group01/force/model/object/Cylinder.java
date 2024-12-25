package hedspi.group01.force.model.object;

import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.ForceCalculatable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------

public class Cylinder extends MainObject implements Rotatable {
    //-------------------------------------------------------------------------------------------

    private DoubleProperty radius = new SimpleDoubleProperty(MAX_RADIUS * 0.3);

    private DoubleProperty angularVelocity = new SimpleDoubleProperty();

    private DoubleProperty angularAcceleration = new SimpleDoubleProperty();
    
    private BooleanProperty isSliding;

    public static final double MAX_RADIUS = 1.0;
    public static final double MIN_RADIUS = 0.1;
    
  //-------------------------------------------------------------------------------------------
    
    
    public Cylinder(double mass, double position, double velocity, double acceleration, double radius) throws Exception {
        super(mass, position, velocity, acceleration);
        setRadius(radius);
    }
    
    
    //-------------------------------------------------------------------------------------------
    //getters + setters
    
    public DoubleProperty radiusProperty() {
        return radius;
    }
    public double getRadius() {
        return radius.get();
    }
    public void setRadius(double radius) throws Exception {
        if (radius < MIN_RADIUS || radius > MAX_RADIUS) {
            this.radius.set(MAX_RADIUS * 0.3);
            throw new Exception("The radius of object must be >= " + MIN_RADIUS + " and <= " + MAX_RADIUS);
        } else {
            this.radius.set(radius);
        }
    }
    
    
    
    public DoubleProperty angularVelocityProperty() {
        return angularVelocity;
    }
    public double getAngularVelocity() {
        return angularVelocity.get();
    }
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity.set(angularVelocity);
    }
    
    
    
    public DoubleProperty angularAccelerationProperty() {
        return angularAcceleration;
    }
    public double getAngularAcceleration() {
        return angularAcceleration.get();
    }
    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration.set(angularAcceleration);
    }

    
    
    public BooleanProperty isSlidingProperty() {
        return isSliding;
    }
    public Boolean isSliding() {
        return isSliding.get();
    }
    public void setSliding(Boolean state) {
        this.isSliding.set(state);
    }
    
  //-------------------------------------------------------------------------------------------
    @Override
    public void reset() {
        super.reset();
        this.angularAcceleration.set(0);
        this.angularVelocity.set(0); 
    }
    
    public double calculateFriction(double appliedForce, Surface surface) {
        double normalForce = ForceCalculatable.calculateNormalForce(getMass());
        double staticCo = surface.getStaticCoefficient();
        double kineticCo = surface.getKineticCoefficient();

        if (appliedForce == 0) {
            if (!isMoving()) {
                setMoving(false);
            } else {
                setMoving(true);
            }
            setSliding(false);
            return appliedForce / 3;
        } else if (appliedForce > 0 && appliedForce <= 3 * normalForce * staticCo) {
            setMoving(true);
            // di chuyen lan ko truot
            setSliding(false);
            return appliedForce / 3;
        } else {
            setMoving(true);
            // di chuyen lan co truot
            setSliding(true);
            return normalForce * kineticCo;
        }

    }

    @Override
    public void update(double deltaTime, Surface surface, double appliedForce) {
        double frictionForce = calculateFriction(appliedForce, surface); // Lực ma sát

        double netForce = ForceCalculatable.calculateNetForce(appliedForce, frictionForce); // Lực tổng

        if (!isMoving()) {

            setAcceleration(0);
            setPosition(getPosition());
            setVelocity(0);
            setAngularAcceleration(0);
            setAngularVelocity(0);
        } else {

            // Tính gia tốc tịnh tiến (linear acceleration)
            double newAcceleration = Movable.calculateAcceleration(netForce, getMass());

            // Tính vận tốc tịnh tiến mới
            double newVelocity = Movable.calculateVelocity(getVelocity(), newAcceleration, deltaTime);

            // Nếu silinder không trượt (rolling without slipping)
            if (!isSliding()) {

                // Tính vị trí mới
                double newPosition = Movable.calculatePosition(getPosition(), getVelocity(), newAcceleration,
                        deltaTime);

                // Tính gia tốc góc (angular acceleration) từ gia tốc tịnh tiến
                double newAngularAcceleration = newAcceleration / getRadius(); // α = a / r

                // Tính vận tốc góc từ vận tốc tịnh tiến (v = ω * r)
                double newAngularVelocity = newVelocity / getRadius();

                // Cập nhật các giá trị
                setAcceleration(newAcceleration);
                setVelocity(newVelocity);
                setPosition(newPosition);
                setAngularVelocity(newAngularVelocity);
                setAngularAcceleration(newAngularAcceleration);

            }
            // Nếu silinder đang trượt
            else {

                // Tính vị trí mới
                double newPosition = Movable.calculatePosition(getPosition(), newVelocity, newAcceleration,
                        deltaTime);

                // Tính gia tốc quay (angular acceleration) từ lực ma sát
                double angularAcceleration = Rotatable.calculateAngularAcceleration(frictionForce, getMass(),
                        getRadius());

                // Tính vận tốc góc mới
                double newAngularVelocity = Rotatable.calculateAngularVelocity(getAngularVelocity(),
                        angularAcceleration, deltaTime);

                // Tính vị trí góc mới (điều chỉnh góc)
                double deltaAngularPosition = Rotatable.calculateDeltaAngularPosition(getAngularVelocity(),
                        angularAcceleration, deltaTime, getRadius());

                // Cập nhật các giá trị
                setAcceleration(newAcceleration);
                setVelocity(newVelocity);
                setAngularVelocity(newAngularVelocity);
                setPosition(newPosition + deltaAngularPosition); /// ?

            }

        }

    }

    
    //-------------------------------------------------------------------------------------------
}
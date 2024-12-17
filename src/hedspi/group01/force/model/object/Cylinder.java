package hedspi.group01.force.model.object;

import hedspi.group01.force.model.vector.Force;
import hedspi.group01.force.model.vector.FrictionForce;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Cylinder extends MainObject implements MovingObject {
    private DoubleProperty angle = new SimpleDoubleProperty();
    private DoubleProperty angAcc = new SimpleDoubleProperty();
    private DoubleProperty angVel = new SimpleDoubleProperty();
    private DoubleProperty radius = new SimpleDoubleProperty(MAX_RADIUS * 0.3);

    public static final double MAX_RADIUS = 1.0;
    public static final double MIN_RADIUS = 0.1;

    public Cylinder() throws Exception {
        super();
    }

    public Cylinder(double mass) throws Exception {
        super(mass);
    }

    public Cylinder(double mass, double radius) throws Exception {
        this(mass);
        setRadius(radius);
    }

    @Override
    public DoubleProperty angAccProperty() {
        return angAcc;
    }

    @Override
    public double getAngAcc() {
        return angAcc.get();
    }

    @Override
    public void setAngAcc(double angAcc) {
        this.angAcc.setValue(angAcc);
    }

    @Override
    public void updateAngAcc(Force force) {
        if (force instanceof FrictionForce) {
            setAngAcc(-force.getValue() / (0.5 * getMass() * getRadius() * getRadius()));
        }
    }

    @Override
    public DoubleProperty angVelProperty() {
        return angVel;
    }

    @Override
    public double getAngVel() {
        return angVel.get();
    }

    @Override
    public void setAngVel(double angVel) {
        this.angVel.setValue(angVel);
    }

    @Override
    public void updateAngVel(double t) {
        setAngVel(getAngVel() + getAngAcc() * t);
    }

    @Override
    public DoubleProperty angleProperty() {
        return angle;
    }

    @Override
    public double getAngle() {
        return angle.get();
    }

    @Override
    public void setAngle(double angle) {
        this.angle.setValue(angle);
    }

    @Override
    public void updateAngle(double oldAngVel, double t) {
        setAngle(getAngle() + oldAngVel * t + 0.5 * getAngAcc() * t * t);
    }

    @Override
    public DoubleProperty radiusProperty() {
        return radius;
    }

    @Override
    public double getRadius() {
        return radius.get();
    }

    @Override
    public void setRadius(double radius) throws Exception {
        if (radius < MIN_RADIUS || radius > MAX_RADIUS) {
            this.radius.setValue(MAX_RADIUS * 0.3);
            throw new Exception("The radius of object must be >= " + MIN_RADIUS + " and <= " + MAX_RADIUS);
        } else {
            this.radius.setValue(radius);
        }
    }

    @Override
    public void applyForceInTimeRotate(Force force, double t) {
        double oldAngVel = getAngVel();
        updateAngAcc(force);
        updateAngVel(t);
        updateAngle(oldAngVel, t);
    }

//    @Override
//    public void applyForceInTime(Force netforce, Force fForce, double t) {
//        super.applyForceInTime(netforce, fForce, t);
//        this.applyForceInTimeRotate(fForce, t);
//    }
}
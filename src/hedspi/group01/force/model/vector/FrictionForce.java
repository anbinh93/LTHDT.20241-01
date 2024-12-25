package hedspi.group01.force.model.vector;

import hedspi.group01.force.model.object.MainObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------
/**
 * Represents the friction force between a Surface and a MainObject.
 * Inherits from Force, computing friction magnitude and direction
 * based on surface friction coefficients, the object's mass,
 * the applied force, and the object's velocity.
 */
public class FrictionForce extends HorizontalVector {
    private DoubleProperty value;
    private MainObject mainObj;
    private DoubleProperty staticCoefficient;
    private DoubleProperty kineticCoefficient;

    //-------------------------------------------------------------------------------------------
    //constructor 
    public FrictionForce(double magnitude, double direction,double staticCoefficient, double kineticCoefficient ) {
        super(magnitude, direction);
        this.value = new SimpleDoubleProperty(magnitude);
        this.staticCoefficient = new SimpleDoubleProperty(staticCoefficient);
        this.kineticCoefficient = new SimpleDoubleProperty(kineticCoefficient);
    }
    //-------------------------------------------------------------------------------------------

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setMainObj(MainObject mainObj) {
        this.mainObj = mainObj;
    }

    public void updateFrictionForce() {
        if (mainObj != null) {
            double normalForce = mainObj.getMass() * 9.81; // Assuming gravity is 9.81 m/s^2
            double frictionMagnitude = mainObj.isMoving() ? kineticCoefficient.get() * normalForce : staticCoefficient.get() * normalForce;
            setMagnitude(frictionMagnitude);
            this.value.set(frictionMagnitude);
        }
    }

    @Override
    public void setMagnitude(double magnitude) {
        super.setMagnitude(magnitude);
        this.value.set(magnitude);
    }

    public double getStaticCoefficient() {
        return staticCoefficient.get();
    }

    public void setStaticCoefficient(double staticCoefficient) {
        this.staticCoefficient.set(staticCoefficient);
    }

    public DoubleProperty staticCoefficientProperty() {
        return staticCoefficient;
    }

    public double getKineticCoefficient() {
        return kineticCoefficient.get();
    }

    public void setKineticCoefficient(double kineticCoefficient) {
        this.kineticCoefficient.set(kineticCoefficient);
    }

    public DoubleProperty kineticCoefficientProperty() {
        return kineticCoefficient;
    }

    public double getValue() {
        return getMagnitude();
    }
}

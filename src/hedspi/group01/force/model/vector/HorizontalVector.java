package hedspi.group01.force.model.vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HorizontalVector {
    protected BooleanProperty direction = new SimpleBooleanProperty(true);
    protected DoubleProperty magnitude = new SimpleDoubleProperty(0.0);

    public HorizontalVector(double value) {
        this.setValue(value);
    }

    public HorizontalVector(double magnitude, boolean direction) {
        this.setValue(magnitude);
        this.setDirection(direction);
    }

    public BooleanProperty directionProperty() {
        return this.direction;
    }

    public boolean getDirection() {
        return this.direction.get();
    }

    public void setDirection(boolean isRight) {
        this.direction.set(isRight);
        updateDirectionValue();
    }

    public DoubleProperty valueProperty() {
        return this.magnitude;
    }

    public double getValue() {
        return this.magnitude.get();
    }

    public void setValue(double value) {
        this.magnitude.set(value);
        updateValueDirection();
    }

    public double getLength() {
        return Math.abs(this.magnitude.doubleValue());
    }

    protected void updateValueDirection() {
        if (this.getValue() >= 0) {
            this.direction.set(true);
        } else {
            this.direction.set(false);
        }
    }

    protected void updateDirectionValue() {
        double absValue = Math.abs(this.getValue());
        if (this.getDirection()) {
            this.magnitude.set(absValue);
        } else {
            this.magnitude.set(-absValue);
        }
    }
}

package hedspi.group01.force.model.vector;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------

public class HorizontalVector {
    //-------------------------------------------------------------------------------------------
    protected DoubleProperty magnitude = new SimpleDoubleProperty(0.0); // Độ lớn của vector
    protected DoubleProperty direction = new SimpleDoubleProperty(0.0); // Hướng của vector, 1 cho phải, -1 cho trái
    
    //-------------------------------------------------------------------------------------------

    public HorizontalVector(double magnitude, double direction) {
        // if  magnitude <0 thif throw exception , vi mac dinh magnitude luon >0
        this.magnitude.set(magnitude);
        this.direction.set(direction); //???? 
    }
    
    //-------------------------------------------------------------------------------------------
    public DoubleProperty directionProperty() {
        return this.direction;
    }
    public double getDirection() {
        return this.direction.get();
    }
    public void setDirection(double direction) {
        this.direction.set(direction);
    }

    
    public DoubleProperty magnitudeProperty() {
        return this.magnitude;
    }
    public double getMagnitude() {
        return this.magnitude.get();
    }
    public void setMagnitude(double value) {
        this.magnitude.set(value);
    }

    public DoubleProperty valueProperty() {
        return this.magnitude;
    }


    //-------------------------------------------------------------------------------------------

    /**
     * Sums two HorizontalVector objects and returns the resultant vector.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return The resultant HorizontalVector.
     */
    public static HorizontalVector sum(HorizontalVector vector1, HorizontalVector vector2) {
        double resultantMagnitude = vector1.getMagnitude() + vector2.getMagnitude();
        double resultantDirection = vector1.getDirection(); // Assuming same direction for simplicity
        return new HorizontalVector(resultantMagnitude, resultantDirection);
    }
}

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
  //-------------------------------------------------------------------------------------------
}

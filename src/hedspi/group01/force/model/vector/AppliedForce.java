package hedspi.group01.force.model.vector;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------
//This class represents an applied force that actor applies on the MainObject
public class AppliedForce extends HorizontalVector {
	//-------------------------------------------------------------------------------------------

    //Holds the absolute maximum magnitude of applied force

    public static final double ABS_MAX_AFORCE = 500;
    
    private DoubleProperty value;
    
  //-------------------------------------------------------------------------------------------
    public AppliedForce(double magnitude, double direction) {
        super(magnitude, direction);
        this.value = new SimpleDoubleProperty(magnitude);
    }
    

     // Changes the magnitude of this AppliedForce
    @Override
    public void setMagnitude(double magnitude) {
        // When force exceeds its maximum and minimum threshold
        if (magnitude > ABS_MAX_AFORCE) {
            setMagnitude(ABS_MAX_AFORCE);
        } 
        else {
            setMagnitude(magnitude);
        }
        this.value.set(magnitude);
    }
    
    public DoubleProperty valueProperty() {
        return value;
    }

    public double getValue() {
        return getMagnitude();
    }
  //-------------------------------------------------------------------------------------------
}
package hedspi.group01.force.model.vector;

//-------------------------------------------------------------------------------------------
//This class represents an applied force that actor applies on the MainObject
public class AppliedForce extends HorizontalVector {
	//-------------------------------------------------------------------------------------------

    //Holds the absolute maximum magnitude of applied force

    public static final double ABS_MAX_AFORCE = 500;
    
  //-------------------------------------------------------------------------------------------
    public AppliedForce(double magnitude, double direction) {
        super(magnitude, direction);
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
    }
  //-------------------------------------------------------------------------------------------
}
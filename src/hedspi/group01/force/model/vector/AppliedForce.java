package hedspi.group01.force.model.vector;

/**
 * This class represents an applied force that actor applies on the MainObject
 * 
 * 
 */
public class AppliedForce extends Force {

    /**
     * Holds the absolute maximum value of applied force
     */
    public static final double ABS_MAX_AFORCE = 500;

    /**
     * Class constructor specifying value of this AppliedForce
     *
     * @param value The value of this AppliedForce
     */
    public AppliedForce(double value) {
        super(value);
    }

    /**
     * Changes the value of this AppliedForce
     */
    @Override
    public void setValue(double value) {
        // When force exceeds its maximum and minimum threshold, set it to this
        // threshold
        if (value > ABS_MAX_AFORCE) {
            setValue(ABS_MAX_AFORCE);
        } else if (value < -ABS_MAX_AFORCE) {
            setValue(-ABS_MAX_AFORCE);
        } else {
            super.setValue(value);
        }
    }

}
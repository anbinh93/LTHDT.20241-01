package hedspi.group01.force.model.vector;

//-------------------------------------------------------------------------------------------
/**
 * Represents the friction force between a Surface and a MainObject.
 * Inherits from Force, computing friction magnitude and direction
 * based on surface friction coefficients, the object's mass,
 * the applied force, and the object's velocity.
 */
public class FrictionForce extends HorizontalVector {

	//-------------------------------------------------------------------------------------------
	//constructor 
	public FrictionForce(double magnitude, double direction,double staticCoefficient, double kineticCoefficient ) {
		super(magnitude, direction);
	}
	//-------------------------------------------------------------------------------------------
	
}

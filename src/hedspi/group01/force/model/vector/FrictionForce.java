package hedspi.group01.force.model.vector;
import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.object.*;
import hedspi.group01.force.model.surface.Surface;

public class FrictionForce extends Force {


	private double staticCoefficient;
	private double kineticCoefficient;
	private Surface surface;
	private MainObject object;
	private AppliedForce appliedForce;
	private boolean isMoving;
	private static final double GRAVITY = 10;


	public FrictionForce(double magnitude, double direction,double staticCoefficient, double kineticCoefficient ) {
		super(magnitude);
		this.staticCoefficient = staticCoefficient;
		this.kineticCoefficient = kineticCoefficient;
	}


	public double calculateFriction(double appliedForce, double normalForce, boolean isMoving) {
		if (appliedForce <= (normalForce*staticCoefficient)) {

			return appliedForce;
		}
		else if (!isMoving && appliedForce > (normalForce*staticCoefficient) ) {
			return normalForce*kineticCoefficient;
		}
		else if (isMoving && appliedForce <= 3*(normalForce*staticCoefficient)) {
			return appliedForce/3;
		}
		else {
			return normalForce*kineticCoefficient;
		}
	}



}

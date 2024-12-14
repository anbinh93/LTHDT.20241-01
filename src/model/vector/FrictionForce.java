package Force;

public class FrictionForce extends Force {
	
	
	private double staticCoefficient;
	private double kineticCoefficient;
	
	public FrictionForce(double magnitude, double direction,double staticCoefficient, double kineticCoefficient ) {
		super(magnitude, direction);
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

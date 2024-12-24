package hedspi.group01.force.model;

public class PhysicsCalculator {
	//-------------------------------------------------------------------------------------------
	
	public static final double g = 10.0;  // gia tốc trọng trường giả định là 10 m/s^2
	
	//-------------------------------------------------------------------------------------------
	
	
	// Tính lực kéo thuần
	public static double calculateNetForce(double appliedForce, double frictionForce) {
		return Math.abs(appliedForce - frictionForce);
	}
	
	
	// Tính lực pháp tuyến
	public static double calculateNormalForce(double mass) {
		return mass * 10; // gia tốc trọng trường giả định là 10 m/s^2
	}
	
	//-------------------------------------------------------------------------------------------
	
	// Tính gia tốc
	/**
     * Updates the acceleration of this object based on the applied force and mass.
     * Uses F = m * a, so a = F / m.
     *
     * @param force The net force applied horizontally.
     */
	
	public static double calculateAcceleration(double netForce, double mass) {
		return netForce / mass;
	}
	
	// Tính vận tốc tuyến tính
	public static double calculateVelocity(double initialVelocity, double acceleration, double deltaTime) {
		return initialVelocity + acceleration * deltaTime;
	}

	// Tính vị trí tuyến tính
	public static double calculatePosition(double initialPosition, double initialVelocity, double acceleration,
			double deltaTime) {
		return initialPosition + initialVelocity * deltaTime + 0.5 * acceleration * deltaTime * deltaTime;
	}
	
	//-------------------------------------------------------------------------------------------
	

	// Tính gia tốc góc (y)
	public static double calculateAngularAcceleration(double frictionForce, double mass, double radius) {
		return frictionForce / ( 1/2 * mass * radius * radius);
	}
	
	// Tính vận tốc góc (ω)
	public static double calculateAngularVelocity(double initialAngularVelocity, double angularAcceleration,
				double deltaTime) {
			return initialAngularVelocity + angularAcceleration * deltaTime;
	} 

	// Tính quãng đường di chuyển được ứng với góc quay
	public static double calculateDeltaAngularPosition(double initialAngularVelocity,
			double angularAcceleration, double deltaTime, double radius ) {
		return initialAngularVelocity * deltaTime * radius + 0.5 * angularAcceleration * deltaTime * deltaTime * radius;
	}

	//-------------------------------------------------------------------------------------------

}

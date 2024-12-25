package hedspi.group01.force.model.object;

public interface Rotatable {
	// -------------------------------------------------------------------------------------------
	//Các Phương thức Tĩnh
	
	// Tính gia tốc góc (y)
	public static double calculateAngularAcceleration(double frictionForce, double mass, double radius) {
		return frictionForce / (1 / 2 * mass * radius * radius);
	}

	// Tính vận tốc góc (ω)
	public static double calculateAngularVelocity(double initialAngularVelocity, double angularAcceleration,
			double deltaTime) {
		return initialAngularVelocity + angularAcceleration * deltaTime;
	}

	// Tính quãng đường di chuyển được ứng với góc quay
	public static double calculateDeltaAngularPosition(double initialAngularVelocity, double angularAcceleration,
			double deltaTime, double radius) {
		return initialAngularVelocity * deltaTime * radius + 0.5 * angularAcceleration * deltaTime * deltaTime * radius;
	}

	// -------------------------------------------------------------------------------------------
}

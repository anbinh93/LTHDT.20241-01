package hedspi.group01.force.model.object;

public interface Movable {
	// -------------------------------------------------------------------------------------------

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

	// -------------------------------------------------------------------------------------------
}

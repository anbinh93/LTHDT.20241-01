package hedspi.group01.force.model.vector;
import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.object.Cube;
import hedspi.group01.force.model.object.Cylinder;

/**
 * Represents the friction force between a Surface and a MainObject.
 * Inherits from Force, computing friction magnitude and direction
 * based on surface friction coefficients, the object's mass,
 * the applied force, and the object's velocity.
 */
public class FrictionForce extends Force {

	// Current surface, object, and applied force influencing friction
	private Surface surface;
	private MainObject mainObj;
	private AppliedForce aForce;

	// Gravity constant (m/s^2)
	public static final double g = 10.0;

	// Velocity threshold for transitioning between static and kinetic friction
	public static final double VEL_THRESHOLD = 0.001;

	/**
	 * Minimal constructor specifying initial friction value.
	 *
	 * @param value Initial friction force (pseudo-value, will be recalculated).
	 */
	public FrictionForce(double value) {
		super(value);
	}

	/**
	 * Detailed constructor specifying friction force value, surface, main object, and applied force.
	 * Immediately recalculates friction based on the arguments provided.
	 */
	public FrictionForce(double value, Surface surface, MainObject mainObj, AppliedForce aForce) {
		super(value);
		this.surface = surface;
		this.mainObj = mainObj;
		this.aForce  = aForce;
		updateFrictionForce();
	}

	/**
	 * Recalculates friction force based on the current state of surface, mainObj, and aForce.
	 * - If mainObj is null, does nothing.
	 * - Determines direction from object velocity or applied force sign.
	 * - Applies static friction if velocity is near zero and applied force is insufficient to move the object.
	 * - Otherwise applies kinetic friction or a special case for Cylinder objects.
	 */
	public void updateFrictionForce() {
		if (mainObj == null) {
			return;
		}

		double normalForce = mainObj.getMass() * g;
		double aForceValue = Math.abs(aForce.getValue());

		// Determine the friction direction (sign).
		// Typically friction opposes motion or potential motion.
		double direction = 0.0;

		// Check object's horizontal velocity magnitude
		double velocityMag = Math.abs(mainObj.getVelocity().getValue());
		boolean velocityPositive = mainObj.getVelocity().getValue() > 0;

		if (velocityMag != 0) {
			// If object is moving, friction is opposite velocity direction
			direction = velocityPositive ? -1 : 1;
		} else {
			// Object is at (or near) rest: friction direction is opposite the applied force
			if (aForceValue == 0) {
				setValue(0.0);
				return;
			} else {
				boolean appliedForcePositive = aForce.getValue() > 0;
				direction = appliedForcePositive ? -1 : 1;
			}
		}

		// Compute friction force depending on the shape type and velocity threshold
		if (mainObj instanceof Cube) {
			// Static friction if applied force <= max static friction and velocity is under threshold
			if (aForceValue <= surface.getStaCoef() * normalForce && velocityMag < VEL_THRESHOLD) {
				setValue(-aForce.getValue()); // perfectly balances the applied force
			} else {
				// Kinetic friction
				setValue(direction * surface.getKiCoef() * normalForce);
			}

		} else if (mainObj instanceof Cylinder) {
			// Custom friction logic for Cylinder
			if (aForceValue <= 3 * surface.getStaCoef() * normalForce && aForceValue > 0) {
				// Partial friction condition
				setValue(direction * (aForceValue / 3.0));
			} else {
				// If the applied force is zero or exceeds that threshold, use kinetic friction
				setValue(direction * surface.getKiCoef() * normalForce);
			}
		}
	}

	/**
	 * Updates the MainObject associated with this FrictionForce and recalculates the friction.
	 */
	public void setMainObj(MainObject obj) {
		this.mainObj = obj;
		updateFrictionForce();
	}

	// public void setSurface(Surface surface) {
	//     this.surface = surface;
	//     updateFrictionForce();
	// }

	// public void setAppliedForce(AppliedForce aForce) {
	//     this.aForce = aForce;
	//     updateFrictionForce();
	// }
}

package Simulation;

public class PhysicsCalculator {
	public static double calculateNetForce(double appliedForce, double frictionForce) {
		return Math.abs(appliedForce - frictionForce); 
	}
	
	public static double calculateAcceleration(double netForce, double mass) {
		return netForce/mass;
	}
	
	
	
}

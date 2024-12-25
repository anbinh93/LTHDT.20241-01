package hedspi.group01.force.model.vector;

public interface ForceCalculatable {
	//-------------------------------------------------------------------------------------------
	
		public static final double g = 10.0;  // gia tốc trọng trường giả định là 10 m/s^2
		
		//-------------------------------------------------------------------------------------------
		
		
		// Tính hợp lực 
		public static double calculateNetForce(double appliedForce, double frictionForce) {
			return Math.abs(appliedForce - frictionForce);
		}
		
		
		// Tính lực pháp tuyến
		public static double calculateNormalForce(double mass) {
			return mass * 10; // gia tốc trọng trường giả định là 10 m/s^2
		}
		
		//-------------------------------------------------------------------------------------------
}

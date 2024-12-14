package Force;

public class Force extends HorizontalVector {
	public Force(double magnitude, double direction) {
		super(magnitude, direction);
	}
	
	public double calculateAcceleration(double mass) {
        // Gia tốc = Lực / Khối lượng
        return getMagnitude() * getDirection() / mass;
    }
	
//	@Override
//    public String toString() {
//        return "Force{" +
//               "magnitude=" + getMagnitude() +
//               ", direction=" + (getDirection() > 0 ? "Right" : "Left") +
//               '}';
//    }
	
}

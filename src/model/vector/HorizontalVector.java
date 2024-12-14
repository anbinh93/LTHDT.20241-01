package Force;

public abstract class HorizontalVector {
	private double magnitude; // Độ lớn của vector
    private double direction; // Hướng của vector, 1 cho phải, -1 cho trái

    public HorizontalVector(double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = Math.signum(direction); // Chỉ nhận -1 hoặc 1, hoặc 0, nhận giá trị 1 nếu như direction >0, =0 nếu như =0, =-1 nếu như <0
    }
    
    //getters and setters
    public double getMagnitude() {
        return magnitude;
    }

    public double getDirection() {
        return direction;
    }
    
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    public void setDirection(double direction) {
        this.direction = Math.signum(direction);
    }
}

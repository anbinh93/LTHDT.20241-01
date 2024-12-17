package hedspi.group01.force.model.vector;

public class Force extends HorizontalVector {
    public Force(double value) {
        super(value);
    }

    public double calculateAcceleration(double mass) {
        // Gia tốc = Lực / Khối lượng
        return getValue() / mass;
    }

    // Tính tổng của 2 lực
    public static Force sumTwoForce(Force force1, Force force2) {
        Force netForce = new Force(force1.getValue() + force2.getValue());
        netForce.updateValueDirection();
        return netForce;
    }
    @Override
    public String toString() {
        return "Force{" +
               "value=" + getValue() +
               ", direction=" + (getDirection() ? "Right" : "Left") +
               '}';
    }
}

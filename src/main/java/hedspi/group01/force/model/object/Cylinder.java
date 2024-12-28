package hedspi.group01.force.model.object;

import hedspi.group01.force.model.vector.Force;
import hedspi.group01.force.model.vector.FrictionForce;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Cylinder extends MainObject implements Rotatable {
	/**
	 * Đảm nhiệm thuộc tính góc của Cylinder này
	 */
	private DoubleProperty angle = new SimpleDoubleProperty();
	/**
	 * Đảm nhiệm thuộc tính gia tốc góc của Cylinder này
	 */
	private DoubleProperty angAcc = new SimpleDoubleProperty();
	/**
	 * Đảm nhiệm thuộc tính vận tốc góc của Cylinder này
	 */
	private DoubleProperty angVel = new SimpleDoubleProperty();
	/**
	 * Đảm nhiệm thuộc tính bán kính của Cylinder này. Bán kính mặc định = 0.3 *
	 */
	private DoubleProperty radius = new SimpleDoubleProperty(MAX_RADIUS * 0.3);
	/**
	 * Đảm nhiệm bán kính tối đa của lớp Cylinder
	 */
	public static final double MAX_RADIUS = 1.0;
	/**
	 * Đảm nhiệm bán kính tối thiểu của lớp Cylinder
	 */
	public static final double MIN_RADIUS = 0.1;

	/**
	 * Hàm khởi tạo mặc định của lớp
	 */
	public Cylinder() throws Exception {
		super();
	}

	/**
	 * Hàm khởi tạo lớp chỉ định khối lượng
	 */
	public Cylinder(double mass) throws Exception {
		super(mass);
	}

	/**
	 * Hàm khởi tạo lớp chỉ định khối lượng và kích thước
	 */
	public Cylinder(double mass, double radius) throws Exception {
		this(mass);
		setRadius(radius);
	}

	@Override
	public DoubleProperty angAccProperty() {
		return angAcc;
	}

	@Override
	public double getAngAcc() {
		return angAcc.get();
	}

	@Override
	public void setAngAcc(double angAcc) {
		this.angAcc.setValue(angAcc);
	}

	@Override
	public void updateAngAcc(Force force) {
		// Trường hợp không có lực ma sát, cylinder chỉ dịch chuyển
		if (force instanceof FrictionForce) {
			setAngAcc(-force.getValue() / (0.5 * getMass() * getRadius() * getRadius()));
		}
	}

	@Override
	public DoubleProperty angVelProperty() {
		return angVel;
	}

	@Override
	public double getAngVel() {
		return angVel.get();
	}

	@Override
	public void setAngVel(double angVel) {
		this.angVel.setValue(angVel);
	}

	@Override
	public void updateAngVel(double t) {
		setAngVel(getAngVel() + getAngAcc() * t);
	}

	@Override
	public DoubleProperty angleProperty() {
		return angle;
	}

	@Override
	public double getAngle() {
		return angle.get();
	}

	@Override
	public void setAngle(double angle) {
		this.angle.setValue(angle);
	}

	@Override
	public void updateAngle(double oldAngVel, double t) {
		double newAngle = getAngle() + oldAngVel * t + 0.5 * getAngAcc() * t * t;
		setAngle(newAngle % 360); // Giới hạn giá trị góc trong khoảng 0-360
	}

	@Override
	public DoubleProperty radiusProperty() {
		return radius;
	}

	@Override
	public double getRadius() {
		return radius.get();
	}

	@Override
	public void setRadius(double radius) throws Exception {
		if (radius < MIN_RADIUS || radius > MAX_RADIUS) {
			this.radius.setValue(MAX_RADIUS * 0.3);
			throw new Exception("Bán kính của đối tượng phải >= " + MIN_RADIUS + " và <= " + MAX_RADIUS);
		} else {
			this.radius.setValue(radius);
		}
	}

	@Override
	public void applyForceInTimeRotate(Force force, double t) {
		double oldAngVel = getAngVel();
		updateAngAcc(force);
		updateAngVel(t);
		updateAngle(oldAngVel, t);
	}
	/**
	 * netForce gây ra dịch chuyển và fForce gây ra quay
	 */
	@Override
	public void applyForceInTime(Force netforce, Force fForce, double t) {
		// Áp dụng netForce (tổng của các lực) cho dịch chuyển
		super.applyForceInTime(netforce, fForce, t);
		// Áp dụng fForce (lực ma sát) cho quay
		this.applyForceInTimeRotate(fForce, t);
	}
}

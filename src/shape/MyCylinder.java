package shape;

import javafx.scene.shape.Cylinder;

public class MyCylinder extends MyShape {
    private double radius;

    public MyCylinder(double radius, double mass) {
        super(mass);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


    @Override
    public Cylinder render() {
        return new Cylinder(radius, 1.0);
    }
}

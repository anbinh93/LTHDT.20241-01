package shape;

import javafx.scene.shape.Shape3D;

public abstract class MyShape {
    protected double mass;

    public MyShape(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    // Phương thức abstract để render hình dạng 3D
    public abstract Shape3D render();
}

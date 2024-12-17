package shape;

import javafx.scene.shape.Box;

public class MyCube extends MyShape {
    private double sideLength;

    public MyCube(double sideLength, double mass) {
        super(mass);
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public Box render() {
        return new Box(sideLength, sideLength, sideLength);
    }
}

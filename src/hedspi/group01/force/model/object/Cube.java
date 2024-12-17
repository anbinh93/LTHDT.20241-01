package hedspi.group01.force.model.object;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Cube extends MainObject {
    private DoubleProperty size = new SimpleDoubleProperty(MAX * 0.3);
    public static final double MAX = 1.0;
    public static final double MIN = 0.1;

    public Cube() {
        super();
    }

    public Cube(double mass) throws Exception {
        super(mass);
    }

    public Cube(double mass, double size) throws Exception {
        this(mass);
        setSize(size);
    }

    public DoubleProperty sizeProperty() {
        return size;
    }

    public double getSize() {
        return size.get();
    }

    public void setSize(double size) throws Exception {
        if (size < MIN || size > MAX) {
            setSize(MAX * 0.3);
            throw new Exception("Cube's size must be >= " + MIN + " and <= " + MAX);
        } else {
            this.size.setValue(size);
        }
    }
}
package hedspi.group01.force.model.object;

import javafx.beans.property.DoubleProperty;

public interface Rotatable {
    DoubleProperty angAccProperty();
    DoubleProperty angVelProperty();
    DoubleProperty angleProperty();
}

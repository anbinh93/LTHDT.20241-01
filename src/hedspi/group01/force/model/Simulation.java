package hedspi.group01.force.model;
import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.AppliedForce;
import hedspi.group01.force.model.vector.Force;
import hedspi.group01.force.model.vector.FrictionForce;
import hedspi.group01.force.model.vector.HorizontalVector;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class is used to contain all information about model. It aggregates
 * MainObject, Surface, AppliedForce and AppliedForce and controls all actions
 * relating to these class such as changing MainObject, adjusting static/kinetic
 * coefficient and applying forces on MainObject. It also performs starting,
 * pausing and continuing simulation.
 *
 */
public class Simulation {

    private BooleanProperty isStart = new SimpleBooleanProperty(false);
    private BooleanProperty isPause = new SimpleBooleanProperty(true);
    private ObjectProperty<MainObject> obj = new SimpleObjectProperty<>();
    private ObjectProperty<HorizontalVector> sysVel = new SimpleObjectProperty<>();
    private ObjectProperty<HorizontalVector> sysAcc = new SimpleObjectProperty<>();
    private DoubleProperty sysAngAcc = new SimpleDoubleProperty(0);
    private DoubleProperty sysAngVel = new SimpleDoubleProperty(0);
    private Force netForce = new Force(0);
    private Surface surface;
    private Force aForce;
    private Force fForce;

    public Simulation() {
        this.surface = new Surface();
        this.aForce = new AppliedForce(0);
        this.fForce = new FrictionForce(0);
    }

    public Simulation(MainObject mainObj, Surface surface, AppliedForce aForce) {
        this.obj.set(mainObj);
        this.surface = surface;
        this.aForce = aForce;
        this.fForce = new FrictionForce(0, surface, mainObj, aForce);
        updateNetForce();
    }

    public void setSysVel(HorizontalVector horizontalVector) {
        this.sysVel.set(horizontalVector);
    }

    public void setSysAcc(HorizontalVector horizontalVector) {
        this.sysAcc.set(horizontalVector);
    }

    public ObjectProperty<HorizontalVector> sysVelProperty() {
        return sysVel;
    }

    public ObjectProperty<HorizontalVector> sysAccProperty() {
        return sysAcc;
    }

    public DoubleProperty getSysAngAcc() {
        return sysAngAcc;
    }

    public void setSysAngAcc(double sysAngAcc) {
        this.sysAngAcc.set(sysAngAcc);
    }

    public DoubleProperty getSysAngVel() {
        return sysAngVel;
    }

    public void setSysAngVel(double sysAngVel) {
        this.sysAngVel.set(sysAngVel);
    }

    public ObjectProperty<MainObject> objProperty() {
        return this.obj;
    }

    public void setObject(MainObject obj) {
        this.obj.set(obj);
        if (obj == null) {
            this.sysAcc.set(new HorizontalVector(0));
            this.sysVel.set(new HorizontalVector(0));
        } else {
            this.sysAcc.set(obj.accProperty());
            this.sysVel.set(obj.velProperty());
        }
    }

    public MainObject getObj() {
        return this.obj.get();
    }

    public BooleanProperty isPauseProperty() {
        return isPause;
    }

    public boolean getIsPause() {
        return isPause.get();
    }

    public void setIsPause(boolean isPause) {
        this.isPause.set(isPause);
    }

    public BooleanProperty isStartProperty() {
        return this.isStart;
    }

    public boolean getIsStart() {
        return this.isStart.get();
    }

    public void setIsStart(boolean isStart) {
        this.isStart.set(isStart);
    }

    public Surface getSur() {
        return surface;
    }

    public Force getaForce() {
        return aForce;
    }

    public void setaForce(double aForce) {
        this.aForce.setValue(aForce);
    }

    public Force getfForce() {
        return fForce;
    }

    public Force getNetForce() {
        return netForce;
    }

    public void start() {
        setIsStart(true);
        setIsPause(false);
    }

    public void pause() {
        setIsPause(true);
    }

    public void conti() {
        setIsPause(false);
    }

    public void restart() {
        setIsStart(false);
        setIsPause(true);
        setObject(null);
        aForce.setValue(0);
        fForce.setValue(0);

        try {
            surface.setStaCoef(Surface.MAX_STA_COEF / 2);
            surface.setKiCoef(Surface.MAX_STA_COEF / 4);
        } catch (Exception e) {
            try {
                surface.setKiCoef(Surface.MAX_STA_COEF / 4);
                surface.setStaCoef(Surface.MAX_STA_COEF / 2);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void updateObjAcc() {
        getObj().updateAcceleration(getNetForce());
    }

    public HorizontalVector getObjVel() {
        return getObj().velProperty();
    }

    public void updateNetForce() {
        Force newNerForce = Force.sumTwoForce(aForce, fForce);
        netForce.setValue(newNerForce.getValue());
    }

    public void applyForceInTime(double t) {
        obj.get().applyForceInTime(getNetForce(), getfForce(), t);
    }
}
package hedspi.group01.force.model.object;

import hedspi.group01.force.model.PhysicsCalculator;
import hedspi.group01.force.model.surface.Surface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------


public class Cube extends MainObject {
	//-------------------------------------------------------------------------------------------
    public static final double MAX_SIZE = 1.0;
    public static final double MIN_SIZE = 0.1;

    private DoubleProperty sideLength = new SimpleDoubleProperty(MAX_SIZE * 0.3);

    public Cube(double mass, double position, double velocity, 
    		double acceleration, double sideLength) throws Exception {
        super(mass, position,velocity,acceleration);
        this.sideLength.setValue(sideLength);
    }

  //-------------------------------------------------------------------------------------------
    
    // -- sideLength --
    public DoubleProperty sideLengthProperty() {
        return sideLength;
    }
    public double getSideLength() {
    	return sideLength.get();
    }
    
    public void setSideLength(double sideLength )throws Exception  {
        if (sideLength < MIN_SIZE || sideLength > MAX_SIZE) {
            this.sideLength.set(MAX_SIZE * 0.3);
            throw new Exception("Cube's side length must be >= " + MIN_SIZE + " and <= " + MAX_SIZE);
        } else {
            this.sideLength.set(sideLength);
        }		
	}
    
  //-------------------------------------------------------------------------------------------

//	@Override
//	public double calculateFriction(double appliedForce, Surface surface) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
	@Override
    public double calculateFriction(double appliedForce, Surface surface) {
		double normalForce = PhysicsCalculator.calculateNormalForce(getMass());
		double staticCo = surface.getStaticCoefficient();
		double kineticCo = surface.getKineticCoefficient();
		
		if (appliedForce <= normalForce * staticCo ) {
			if (!isMoving()) {
				setMoving(false);
			}
			else {
				setMoving(true);
			}
			return appliedForce;
		}
		else {
			setMoving(true);
			return normalForce * kineticCo;
		}
		
		
		//throw exception
		//them ca phan code throw exception khi nhap vao mot so sai format ? la phan cua controller hay gi ?

		
	}

	@Override
    public void update(double deltaTime , Surface surface, double appliedForce) {
        
        double frictionForce = calculateFriction(appliedForce, surface );
        double netForce = PhysicsCalculator.calculateNetForce(appliedForce, frictionForce);
        
        //neu dang dung yen
        if (!isMoving()) {
        	
        	setAcceleration(0);
        	setPosition(getPosition());
        	setVelocity(0);
        }
        else {
        	double newAcceleration = PhysicsCalculator.calculateAcceleration(netForce, getMass());
            
            double newVelocity = PhysicsCalculator.calculateVelocity(getVelocity(), newAcceleration, deltaTime);
            //nếu như chuyển động chậm dần tới v =0 thì cho nó dừng lại
            if (newVelocity <0) {
            	setMoving(false);
            	return;
            }
            
            double newPosition = PhysicsCalculator.calculatePosition(getPosition(), getVelocity(), newAcceleration, deltaTime);
            
            setAcceleration(newAcceleration);
            setPosition(newPosition);
            setVelocity(newVelocity);	
        }
        
    	return;
    }
	//-------------------------------------------------------------------------------------------
}
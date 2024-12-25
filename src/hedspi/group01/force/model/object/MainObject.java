package hedspi.group01.force.model.object;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.ForceCalculatable;
import hedspi.group01.force.model.vector.FrictionForce;
import hedspi.group01.force.model.vector.HorizontalVector;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------

/**
 * Abstract class representing a physical object with mass, horizontal velocity,
 * horizontal acceleration, and position.
 */

public abstract class MainObject implements Movable,ForceCalculatable {
	//-------------------------------------------------------------------------------------------
	private double id;
    public static int nbObject =0;
	
    // Default mass constant
    public static final double DEFAULT_MASS = 50.0;

    // Mass of the object (must be > 0)
    private DoubleProperty mass = new SimpleDoubleProperty(DEFAULT_MASS);
    
    // Position along x axis , no y axis
    private DoubleProperty position = new SimpleDoubleProperty(0.0);
    
    // Horizontal acceleration and velocity
    private DoubleProperty velocity = new SimpleDoubleProperty(0.0);
    private DoubleProperty acceleration = new SimpleDoubleProperty(0.0);
    
    
    
  //indicates whether the object is currently in motion or not
    private BooleanProperty isMoving;
    
  //-------------------------------------------------------------------------------------------
    
    /**
     * Constructor allowing a user-defined mass.
     *
     * @param mass The initial mass of this MainObject (must be > 0).
     * @throws IllegalArgumentException if mass <= 0
     */

    public MainObject(double mass, double initialPosition, double velocity, double acceleration) {
    	if (mass <= 0)  throw new IllegalArgumentException("Mass must be postive");
    	this.id = ++nbObject;
    	
    	this.mass.set(mass); 
		this.position.set(initialPosition);
		this.velocity.set(0); // Initial velocity is 0
		this.acceleration.set(0); // Initial acceleration is 0
		this.isMoving = new SimpleBooleanProperty(false); // Initialize isMoving property
    }
    
    
    public abstract double calculateFriction(double appliedForce,  Surface surface);

	public abstract void update(double deltaTime, Surface surface, double appliedForce );
    
	public void reset() {
		// TODO Auto-generated method stub
		this.position.set(0);
		this.velocity.set(0); 
		this.acceleration.set(0);
		this.isMoving.set(false);
	}

	//-------------------------------------------------------------------------------------------
    
    // -- Id --
 	public double getId() {
 		return id;
 	}
 	public void setId(double id) {
 		this.id = id;
 	}
 	
 	
    // -- Mass Handling --
    public DoubleProperty massProperty() {
        return mass;
    }
    public double getMass() {
        return mass.get();
    }
    public void setMass(double newMass) {
        if (newMass <= 0) {
            throw new IllegalArgumentException("Object's mass must be > 0");
        }
        this.mass.set(newMass);
    }
    
    // -- Position --
    public DoubleProperty positionProperty() {
        return position;
    }
    public double getPosition() {
        return position.get();
    }
    public void setPosition(double posValue) {
        position.set(posValue);
    }
    

    // -- Acceleration  --	

    public DoubleProperty accProperty() {
        return acceleration;
    }
    public double getAcceleration() {
        return acceleration.get();
    }
    public void setAcceleration(double accValue) {
        acceleration.setValue(accValue);
    }
    
    
    // -- Velocity  --
    public DoubleProperty velProperty() {
        return velocity;
    }
    public double getVelocity() {
        return velocity.get();
    }  
    public void setVelocity(double velValue) {
        velocity.setValue(velValue);
    }
    
    
    // -- isMoving --
    public BooleanProperty isMovingProperty() {
		return isMoving;
	}
	public boolean isMoving() {
		return isMoving.get();
	}
	public void setMoving(Boolean state) {
		this.isMoving.set(state);
	}
    

	//-------------------------------------------------------------------------------------------
    
    /**
     * Applies the given forces to the object over a specified time interval.
     *
     * @param netForce      The net force applied to the object.
     * @param frictionForce The friction force acting on the object.
     * @param timeInterval  The time interval over which the forces are applied.
     */
    public void applyForceInTime(HorizontalVector netForce, FrictionForce frictionForce, double timeInterval) {
        // Calculate the net acceleration
        double netAcceleration = netForce.getMagnitude() / getMass();

        // Update the velocity and position based on the net acceleration
        double newVelocity = getVelocity() + netAcceleration * timeInterval;
        double newPosition = getPosition() + getVelocity() * timeInterval + 0.5 * netAcceleration * timeInterval * timeInterval;

        // Update the object's properties
        setVelocity(newVelocity);
        setPosition(newPosition);
        setAcceleration(netAcceleration);
    }
	
	
	

}






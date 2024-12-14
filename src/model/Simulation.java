package Simulation;

import Force.AppliedForce;
import Force.Force;
import Force.FrictionForce;
import MainObject.MainObject;
import Surface.Surface;

public class Simulation {
	private MainObject object;
	private Surface surface;
	private AppliedForce appliedForce;
    private FrictionForce frictionForce;
	
    public Simulation(MainObject object, Surface surface, AppliedForce appliedForce, FrictionForce frictionForce) {
        this.object = object;
        this.surface = surface;
        this.appliedForce = appliedForce;
        this.frictionForce = frictionForce;
    }

    
    public void update(double deltaTime) {
        double normalForce = object.getMass() * 10; // Gravity
        boolean isMoving = object.getVelocity() > 0;
        double friction = frictionForce.calculateFriction(appliedForce.getMagnitude(), normalForce, isMoving);

        double netForce = PhysicsCalculator.calculateNetForce(appliedForce.getMagnitude(), friction);
        double acceleration = PhysicsCalculator.calculateAcceleration(netForce, object.getMass());

        object.setAcceleration(acceleration);
        object.updatePosition(deltaTime);
    }
	
}

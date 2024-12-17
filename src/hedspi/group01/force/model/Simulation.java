package hedspi.group01.force.model;

import hedspi.group01.force.model.vector.AppliedForce;
import hedspi.group01.force.model.vector.Force;
import hedspi.group01.force.model.vector.FrictionForce;
import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.surface.Surface;

public class Simulation {
    private final MainObject object;
    private final Surface surface;
    private final AppliedForce appliedForce;
    private final FrictionForce frictionForce;

    public Simulation(MainObject object, Surface surface, AppliedForce appliedForce, FrictionForce frictionForce) {
        this.object = object;
        this.surface = surface;
        this.appliedForce = appliedForce;
        this.frictionForce = frictionForce;
    }

    public void update(double deltaTime) {
        double normalForce = object.getMass() * 10; // Gravity
        // boolean isMoving = object.getVelocity() > 0;
        double friction = frictionForce.calculateFriction(appliedForce.getMagnitude(), normalForce, isMoving);

        double netForce = PhysicsCalculator.calculateNetForce(appliedForce.getMagnitude(), friction);
        double acceleration = PhysicsCalculator.calculateAcceleration(netForce, object.getMass());

        // object.setAcceleration(acceleration);
        // object.updatePosition(deltaTime);
    }
}

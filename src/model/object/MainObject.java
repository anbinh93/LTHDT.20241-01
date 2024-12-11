package model.object;
public abstract class MainObject {
    private int id;
    private float xpos;
    private float ypos;
    private String type;
    private float friction_coefficient; //Reserved for object's surface friction coefficient
    private float mass;
    private float vel;
    private float accel;


    public MainObject(int id, int xpos, int ypos, String type, float friction_coefficient, float mass, float vel, float accel) {
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
        this.type = type;
        this.friction_coefficient = friction_coefficient;
        this.mass = mass;
        this.vel = vel;
        this.accel = accel;
    }

    public MainObject(int id, int xpos, int ypos, String type, float mass, float vel, float accel) {
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
        this.type = type;
        this.mass = mass;
        this.vel = vel;
        this.accel = accel;
    }

    public int getId() {
        return id;
    }

    public float getXpos() {
        return xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public String getType() {
        return type;
    } 

    public float getFriction_coefficient() {
        return friction_coefficient;
    }

    public float getMass() {
        return mass;
    }

    public float getVel() {
        return vel;
    }
    
    public float getAccel() {
        return accel;
    }

    public void setxpos(float xpos) {
        this.xpos = xpos;
    }

    public void setypos(float ypos) {
        this.ypos = ypos;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setVel(float vel) {
        this.vel = vel;
    }

    public void setAccel(float accel) {
        this.accel = accel;
    }
}


package hedspi.group01.force.model.object;
public abstract class MainObject {
    private int id;
    private float xpos;
    private float ypos;
    private String type;
    private float friction_coefficient; //Reserved for object's surface friction coefficient
    private float mass;


    public MainObject(int id, int xpos, int ypos, String type, float friction_coefficient, float mass) {
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
        this.type = type;
        this.friction_coefficient = friction_coefficient;
        this.mass = mass;
    }

    public MainObject(int id, int xpos, int ypos, String type, float mass) {
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
        this.type = type;
        this.mass = mass;
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

    public void setxpos(float xpos) {
        this.xpos = xpos;
    }

    public void setypos(float ypos) {
        this.ypos = ypos;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

}


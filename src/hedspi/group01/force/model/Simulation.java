package hedspi.group01.force.model;
import hedspi.group01.force.model.object.MainObject;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.AppliedForce;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

//-------------------------------------------------------------------------------------------

/**
 * This class is used to contain all information about model. It aggregates
 * MainObject, Surface, AppliedForce and AppliedForce and controls all actions
 * relating to these class such as changing MainObject, adjusting static/kinetic
 * coefficient and applying forces on MainObject. It also performs starting,
 * pausing and continuing simulation.
 *
 */
public class Simulation {
	//-------------------------------------------------------------------------------------------
	// Thêm danh sách các đối tượng cần mô phỏng
	private MainObject object;
	private Surface surface;
	private AppliedForce appliedForce;
	
	private BooleanProperty isPaused;
    private BooleanProperty isStarted;
  //-------------------------------------------------------------------------------------------
    
    public Simulation(MainObject object, Surface surface, AppliedForce appliedForce) {
        this.object = object;
        this.surface = surface;
        this.appliedForce = appliedForce;
        this.isPaused = new SimpleBooleanProperty(true); // Mặc định là tạm dừng khi khởi tạo
        this.isStarted = new SimpleBooleanProperty(false); // Mặc định chưa bắt đầu
    }
    
    /**
     * Applies both net force and friction force over a time interval, updating
     * acceleration, velocity, and position accordingly.
     *
     * @param deltaTime The time interval for integration.
     */
    public void update(double deltaTime) {
        if (isPaused.get() || !isStarted.get()) {
            return; // Không cập nhật nếu mô phỏng đang tạm dừng hoặc chưa bắt đầu
        }

        double appliedForceMagnitude = appliedForce.getMagnitude();
        object.update(deltaTime, surface, appliedForceMagnitude);
    }

    /**
     * Runs the simulation over a specified total time and time step.
     *
     * @param totalTime The total simulation time.
     * @param timeStep  The time step for updates.
     */
    public void runSimulation(double totalTime, double timeStep) {
        double currentTime = 0.0;

        while (currentTime < totalTime) {
            if (isPaused.get()) {
                continue; // Chờ đến khi resume
            }

            System.out.println("Time: " + currentTime + "s");
            System.out.println("Position: " + object.getPosition() + "m");
            System.out.println("Velocity: " + object.getVelocity() + "m/s");
            System.out.println("Acceleration: " + object.getAcceleration() + "m/s²");
            System.out.println("----------------------------------");

            // Cập nhật trạng thái của hệ thống
            update(timeStep);

            // Tăng thời gian
            currentTime += timeStep;
        }
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        isStarted.set(true);
        isPaused.set(false);
        System.out.println("Simulation started.");
    }

    /**
     * Pauses the simulation.
     */
    public void pause() {
        if (isStarted.get()) {
            isPaused.set(true);
            System.out.println("Simulation paused.");
        }
    }

    /**
     * Resumes the simulation from a paused state.
     */
    public void resume() {
        if (isStarted.get() && isPaused.get()) {
            isPaused.set(false);
            System.out.println("Simulation resumed.");
        }
    }

    /**
     * Resets the simulation to its initial state.
     */
    public void reset() {
        isStarted.set(false);
        isPaused.set(true);

        // Đặt lại đối tượng, bề mặt và lực tác động về trạng thái ban đầu
        object.reset();
        surface.reset();
        appliedForce.setMagnitude(0);

        System.out.println("Simulation reset.");
    }
    
  //-------------------------------------------------------------------------------------------
    //getter + setter
    //Checks if the simulation is paused.
    
    public BooleanProperty isPausedProperty() {
        return isPaused;
    }
    public boolean isPaused() {
    	return isPaused.get();
    }
    public void setPaused(boolean state) {
    	isPaused.set(state);
    }

    //Checks if the simulation has started.
     
    public BooleanProperty isStartedProperty() {
        return isStarted;
    }
    public boolean isStarted() {
    	return isStarted.get();
    }
    public void setStarted(boolean state) {
    	isStarted.set(state);
    }
  //-------------------------------------------------------------------------------------------
}
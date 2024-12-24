package hedspi.group01.force.model.surface;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//-------------------------------------------------------------------------------------------

/**
 * This class is used to represent surface which contains attributes and methods
 * for adjusting static friction coefficient and kinetic friction coefficient
 *
 */
public class Surface {
	//-------------------------------------------------------------------------------------------
    //Holds the max static coefficient of class Surface
    public static final double MAX_STATIC_COEFFICIENT = 1.0;
    
    //Holds the static friction property of this Surface
    private DoubleProperty staticCoefficient = new SimpleDoubleProperty(MAX_STATIC_COEFFICIENT / 2);
    
    
    //Holds the kinetic friction coefficient of this Surface
    private DoubleProperty kineticCoefficient = new SimpleDoubleProperty(MAX_STATIC_COEFFICIENT / 4);
    
    
    //Holds the step of static/kinetic coefficient of class Surface

    public static final double STEP_COEF = 0.001;

  //-------------------------------------------------------------------------------------------
    //Class constructor specifying static friction coefficient and kinetic friction

    public Surface(double staticCoefficient, double kineticCoefficient) throws Exception {
    	//note : đưa ra lỗi nếu như staticCoeeficient <= kineticCoefficient
		 if (staticCoefficient <= kineticCoefficient) {
		        throw new IllegalArgumentException("Static coefficient must be greater than kinetic coefficient");
		 }
		 else {
			 this.staticCoefficient.set(kineticCoefficient); 
			 this.kineticCoefficient.set(kineticCoefficient); 
		 }
		 
    }
    

	public void reset() {
		this.staticCoefficient.set(0);
		this.kineticCoefficient.set(0);
	}
    
  //-------------------------------------------------------------------------------------------
    // Gets the static friction coefficient property of this Surface

    public DoubleProperty staticCoefficientProperty() {
        return staticCoefficient;
    }
    public double getStaticCoefficient() {
        return staticCoefficient.get();
    }
    public void setStaticCoefficient(double coefficient) throws Exception {
    	if (coefficient == 0) {
            // Sets both staticCoefficient and kineticCoefficient to 0 if staticCoefficient = 0
            kineticCoefficient.set(0);
            this.staticCoefficient.setValue(0);
            
        } else if (coefficient <= getKineticCoefficient()) {
            this.staticCoefficient.setValue(getKineticCoefficient() + STEP_COEF);
            throw new Exception("Static friction coefficient must be greater than the kinetic friction coefficient: "
                    + String.format("%.3f", getKineticCoefficient()));
            
        } else {
            this.staticCoefficient.set(coefficient);
        }
    }
    
    
    //Changes the kinetic friction coefficient of this Surface

    public DoubleProperty kineticCoefficientProperty() {
        return kineticCoefficient;
    }
    public double getKineticCoefficient() {
        return kineticCoefficient.get();
    }
    public void setKineticCoefficient(double coefficient) throws Exception {
    	
    	if (getStaticCoefficient() <= coefficient) {
            // Handles case when staticCoefficient has already = 0
            this.kineticCoefficient.setValue(Math.max(0, getStaticCoefficient() - STEP_COEF));
            throw new Exception("Kinetic friction coefficient must be less than the static friction coefficient: "
                    + String.format("%.3f", getStaticCoefficient()));
        } else {
            this.kineticCoefficient.set(coefficient);
        }
    }
  //-------------------------------------------------------------------------------------------


}

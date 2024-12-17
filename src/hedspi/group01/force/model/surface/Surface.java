package hedspi.group01.force.model.surface;


public class Surface {
	 private double staticCoefficient;
	 private double kineticCoefficient;
	
	 public Surface(double staticCoefficient, double kineticCoefficient) {
		 //note : đưa ra lỗi nếu như staticCoeeficient <= kineticCoefficient
		 
		 this.staticCoefficient = staticCoefficient;
		 this.kineticCoefficient = kineticCoefficient;
	 }
	


	public double getStaticCoefficient() {
        return staticCoefficient;
    }

    public double getKineticCoefficient() {
        return kineticCoefficient;
    }
    
	public void setStaticCoefficient(double staticCoefficient) {
		this.staticCoefficient = staticCoefficient;
	}

	public void setKineticCoefficient(double kineticCoefficient) {
		this.kineticCoefficient = kineticCoefficient;
	}
	
	 
}

//Imports
import java.util.Scanner;

class Service {

    private String typeOfService;
    private double costOfParts;
    private double lengthOfService;
    
    Service(String typeOfService, double costOfParts, double lengthOfService) {
        
        this.typeOfService = typeOfService;
        this.costOfParts = costOfParts;
        this.lengthOfService = lengthOfService;
    }
    
    
    
    public String getTypeOfService() {
        return typeOfService;
    }
    
    public double getCostOfParts() {
        return costOfParts;
    }
    
    public double getLengthOfService() {
        return lengthOfService;
    }
    
    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }
    
    public void setCostOfParts (double costOfParts) {
        this.costOfParts = costOfParts;
    }
    
    public void setLengthOfService (double lengthOfService) {
        this.lengthOfService = lengthOfService;
    }
    
    
}
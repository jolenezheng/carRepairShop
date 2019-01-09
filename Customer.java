//Imports
import java.util.Scanner;

class Customer {
    
    private String name;
    private String carDescription;
    private String serviceReq;
    private boolean scheduled;
    
    Customer(String name, String carDescription, String serviceReq, boolean scheduled) {
        this.name = name;
        this.carDescription = carDescription;
        this.serviceReq = serviceReq;       
        this.scheduled = scheduled;
    }
      
    public String getName() {
        return name;
    }
    
    public String getCarDescription() {
        return carDescription;
    }
    
    public String getServiceReq() {
        return serviceReq;
    }
    
    public boolean getScheduled() {
        return scheduled;   
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }
    
    public void setServiceReq(String serviceReq) {
        this.serviceReq = serviceReq;
    }
    
    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }
    
}
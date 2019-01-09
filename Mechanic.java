//Imports
import java.util.Scanner;
import java.util.ArrayList;

class Mechanic {
    
    private String name;
    private double payRate;
    private ArrayList<String> servicesPerformable = new ArrayList<String>();
    private double lastAppt;
    private boolean newDay;
    
    Mechanic(String name, double payRate, ArrayList<String> servicesPerformable, double lastAppt, boolean newDay) {
        this.name = name;
        this.payRate = payRate;
        this.servicesPerformable = servicesPerformable;  
        this.lastAppt = lastAppt;
        this.newDay = newDay;
    }
    
    
    
    public String getName() {
        return name;
    }
    
    public double getPayRate() {
        return payRate;
    }
    
    public ArrayList<String> getServicesPerformable() {
        return servicesPerformable;
    }
    
    public double getLastAppt() {
        return lastAppt;       
    }
    
    public boolean getNewDay() {
        return newDay;       
    }

            
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }
    
    public void setServicesPerformable(ArrayList<String> servicePerformable) {
        this.servicesPerformable = servicePerformable;
    }
    
    public void setLastAppt(double lastAppt) {
        this.lastAppt = lastAppt;
    }
    
    public void setNewDay(boolean newDay) {
        this.newDay = newDay;
    }
    
    
    
}
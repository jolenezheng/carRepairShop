class Appointment {
    
    private String apptStartTime;
    private String apptEndTime;
    private String workshop;
    private String customerName;
    private String customerCar;
    private String serviceReq;
    private String mechName;
    private String calculatedCost;
    
    Appointment(String apptStartTime, String apptEndTime, String workshop, String customerName, String customerCar, String serviceReq, String mechName, String calculatedCost) {
        
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.workshop = workshop;
        this.customerName = customerName;
        this.customerCar = customerCar;
        this.serviceReq = serviceReq;
        this.mechName = mechName;
        this.calculatedCost = calculatedCost;
         
    }
                
    
    public String getApptStartTime() {
        return apptStartTime;
    }
    
    public String getApptEndTime() {
        return apptEndTime;
    }
    
    public String getWorkshop() {
        return workshop;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getCustomerCar() {
        return customerCar;
    }
    
    public String getServiceReq() {
        return serviceReq;
    }
   
    public String getMechName() {
        return mechName;
    }
    
    public String getCalculatedCost() {
        return calculatedCost;
    }
    
    
    public void setApptStartTime(String apptStartTime) {
        this.apptStartTime = apptStartTime;
    }
    
    public void setApptEndTime(String apptEndTime) {
        this.apptEndTime = apptEndTime;
    }
    
    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public void setCustomerCar(String customerCar) {
        this.customerCar = customerCar;
    }
    
    public void setServiceReq(String serviceReq) {
        this.serviceReq = serviceReq;
    }
    
    public void setMechName(String mechName) {
        this.mechName = mechName;
    }
    
    public void setCalculatedCost(String calculatedCost) {
        this.calculatedCost = calculatedCost;
    }

    
}
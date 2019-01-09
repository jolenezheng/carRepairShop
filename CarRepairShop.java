//Imports
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;

/** Reads customer files and creates a schedule for the car repair shop
  * @author Jolene Zheng
  * @version 1.0 Build 0123 Oct 24, 2017 */
class CarRepairShop {
    public static void main(String[] args) throws Exception {
        
        //DECALRE VARIABLES
        
        //Stores each file where customer, service, mechanics, and scheudle information is stored respectively
        File customerFile = new File("customer.txt");
        File servicesFile = new File("services.txt");
        File mechanicsFile = new File("mechanics.txt");
        File scheduleFile = new File("schedule.txt");
        
        //Scanners to read individual files
        Scanner cFileInput = new Scanner(customerFile);
        Scanner sFileInput = new Scanner(servicesFile);
        Scanner mFileInput = new Scanner(mechanicsFile);
        
        //Printwriter to output final schedule to its text file
        PrintWriter fileOutput = new PrintWriter(scheduleFile);
        
        ArrayList<Customer> customers = new ArrayList<Customer>(); //Stores each customer object found in the customer text file
        ArrayList<Service> services = new ArrayList<Service>(); //Stores each service object found in the service text file
        ArrayList<Mechanic> mechanics = new ArrayList<Mechanic>(); //Stores each mechanic object found in the mechanic text file
        ArrayList<Appointment> appointment = new ArrayList<Appointment>(); //Stores each appointment made from the program
        
        Customer currentCustomer = null; //holds the current customer object being scheduled- changes with each loop
        Customer previousCustomer; //holds the previous customer that was scheduled in
        
        String currentLine; //holds the current line being looped through in the text file being read
        String workshop; //stores the workshop name the current customer's car is in (4 workshops total- 4 cars can be worked on at a time)
        String startDisplayTime = ""; //stores when the appointment starts in HH:MM(a/pm) format
        String endDisplayTime = ""; //stores when the appointment ends in HH:MM(a/pm) format
        String costStatement; //stores the calculated cost statement the customer owes
        
        int lineNum = 0; //stores which line the current line being read from the text file is
        int weekNum = 1; //stores the week number the schedule is on (week number increases when customers can't be fitted into one week)
        int carsInWorkshop = 0; //stores the number of cars in each workshop per day
        int mechanicIndex; //stores the index of the mechanic that's doing the current customer's service
        int previousCustomerIndex = 0; //stores the index of the previous customer that was scheduled in
        int scheduledCustomers = 0; //stores the number of customers successfully scheduled in
        
        double serviceCost = 0; //stores the cost of the service (parts)
        double startTime = 9; //stores when the customer's service starts (starts at 9)
        double endTime = 9; //stores when the customer's service finishes, in terms of hours elapsed since the start time (deafult is 9 for first customer)
        double serviceLength = 0; //stores the length of the service to be done
        
        boolean customerScheduled = false; //stores whether the customer has been successfully scheduled in
        boolean firstApptOfDay = true; //stores whether the current customer is the first customer of the day
        boolean doneSchedule = false; //stores whether the final schedule is completed
        boolean foundCustomer = false; //stores whether a customer has been found from the text file 
        boolean workshopFull = false; //stores whether the workshop is full for the day
        boolean matchedService = false; //stores whether the customer's service requires matches the service the company provides
        
        //Customer variables
        String customerName = ""; //stores the customer's name
        String carDescription = ""; //stores the customer's car description
        String serviceReq = ""; //stores the service required by the customer
        boolean scheduled = false; //stores whehter the customer has been successfully scheduled in
        
        //Services variables
        String typeOfService = ""; //stores the type of service offered
        double costOfParts = 0.0; //stores the cost of service parts
        double lengthOfService = 0.0; //stores the duration of the service offered in hours
        
        //Mechanics variables
        String mechanicName = ""; //stores the mechanic's name
        double payRate = 0.0; //stores the pay rate mechanic charges
        ArrayList<String> servicesPerformable = new ArrayList<String>(); //stores the services each mechanic is able to perform
        double lastAppt = 0; //stores when the mechanic finished its last job
        boolean newDay = true; //stores whether it's a new day of the week
        
        
        //READS EACH FILE AND CREATES THEIR RESPECTIVE OBJECTS
        
        //Reads customer file
        while (cFileInput.hasNext()) { //execute code as long as file has more lines
            
            currentLine = cFileInput.nextLine(); //holds current line
            
            if (lineNum == 0) {
                customerName = currentLine; //first line stores customer's name
            } else if (lineNum == 1) { //second line stores customer's car description
                carDescription = currentLine;             
            } else if (lineNum == 2) { //third line stores the service the customer requires
                serviceReq = currentLine;
                customers.add(new Customer(customerName, carDescription, serviceReq, scheduled)); //creates a new customer object after all customer information has been read from file           
            }
            
            lineNum++; //line number increases after each line has been read
            
            if (currentLine.equals("")) { //if nothing's stored on the current line
                lineNum = 0; //resets line number to 0
            }
            
        }
        
        lineNum = 0; //resets line number before reading service file
        
        
        //Reads services file - same way of reading file as customer's
        while (sFileInput.hasNext()) {
            
            currentLine = sFileInput.nextLine();
            
            if (lineNum == 0) {
                typeOfService = currentLine;
            } else if (lineNum == 1) {
                costOfParts = Double.parseDouble(currentLine.substring(1));               
            } else if (lineNum == 2) {
                lengthOfService = Double.parseDouble(currentLine);
                services.add(new Service(typeOfService, costOfParts, lengthOfService));                
            }
            
            lineNum++;
            
            if (currentLine.equals("")) {   
                lineNum = 0;
            }
            
        }
        
        lineNum = 0;
        
        
        //Reads mechanics file - same way of reading file as customer's
        while (mFileInput.hasNext()) {
            
            currentLine = mFileInput.nextLine();
            
            if (lineNum == 0) {
                mechanicName = currentLine;
            } else if (lineNum == 1) {
                payRate = Double.parseDouble(currentLine);               
            } else if (lineNum > 1 && !currentLine.equals("")) { 
                servicesPerformable.add(currentLine);                              
            }
            
            lineNum++;
            
            if (currentLine.equals("")) { 
                mechanics.add(new Mechanic(mechanicName, payRate, servicesPerformable, 0, newDay)); //adds mechanic object with information gathered            
                servicesPerformable = new ArrayList<String>(); //
                lineNum = 0;
            }         
        }
        
        mechanics.add(new Mechanic(mechanicName, payRate, servicesPerformable, 0, newDay)); //once there are no more lines, adds creates the last mechnic object
        
        //close file scanners
        cFileInput.close();
        sFileInput.close();
        mFileInput.close();
              
        
        //SCHEDULE IN EACH CUSTOMER
        
        fileOutput.println("CAR REPAIR SHOP SCHEDULE");
        
        do { //loop so long as there are more customers to be scheduled
            
            fileOutput.println("\nW E E K  N U M B E R :  " + weekNum); //prints the current week schedule is on
            
            for (int dayOfWeek = 1; dayOfWeek <= 5 && !doneSchedule; dayOfWeek++) { //day of the week increases by one each iteration
                
                //prints the current day of the week
                if (dayOfWeek == 1) {
                    fileOutput.println("\n----------MONDAY----------\n");
                } else if (dayOfWeek == 2) {
                    fileOutput.println("\n----------TUESDAY----------\n");
                } else if (dayOfWeek == 3) {
                    fileOutput.println("\n----------WEDNESDAY----------\n");
                } else if (dayOfWeek == 4) {
                    fileOutput.println("\n----------THURSDAY----------\n");
                } else {
                    fileOutput.println("\n----------FRIDAY----------\n");
                }
                
                for (int i = 0; i < mechanics.size(); i++) {                    
                    mechanics.get(i).setNewDay(true); //calls setter
                }
                
                for (int workshopNum = 1; workshopNum <= 4 && !doneSchedule; workshopNum++) { //changes workshop number once current workshop is full (up to 4) as long as schedule isn't complete
                    
                    workshop = "Workshop: " + Integer.toString(workshopNum); //stores the current workshop number
                    fileOutput.println(workshop); //prints the current workshop
                    workshopFull = false; //resets workshop to not full
                    firstApptOfDay = true; //resets to first appointment of the day
                    carsInWorkshop = 0; //resets the number of cars in the workshop to 0
                    
                    while (!workshopFull && !doneSchedule) { //continue looping as long as the workshop isn't full and schedule is not complete
                        foundCustomer = false;
                        
                        for (int i = 0; i < customers.size() && !foundCustomer && !doneSchedule; i++) {
                            
                            if (customers.get(i).getScheduled() == false) { 
                                
                                currentCustomer = customers.get(i); 
                                previousCustomerIndex = i - 1;
                                serviceReq = currentCustomer.getServiceReq(); 
                                matchedService = false;
                                
                                for (int j = 0; j < services.size() && !matchedService; j++) { //matches the service required to the service object
                                    
                                    if (customers.get(i).getServiceReq().equals(services.get(j).getTypeOfService())) { //if the type of service matches the service required by the customer
                                        
                                        serviceLength = services.get(j).getLengthOfService(); //gets the service length of the matched service object
                                        serviceCost = services.get(j).getCostOfParts(); //gets the cost of service of the matched service object
                                        matchedService = true;
                                        
                                    }                                
                                    
                                } //end of matching service   
                                
                            } //end of code to be executed if customer hasn't been scheduled in yet
                            
                        } 
                        
                        
                        if (firstApptOfDay) {
                            
                            startTime = 9; 
                            
                        } else {
                                                        
                            if (Double.parseDouble(appointment.get(previousCustomerIndex).getApptEndTime().substring(0,1)) <= 5) {
                                
                                startTime = Double.parseDouble(appointment.get(previousCustomerIndex).getApptEndTime().substring(0,1)) + 12; //if the end of the last appointment is past 12, add 12 to time to put it in 24hour format
                                
                                if (appointment.get(previousCustomerIndex).getApptEndTime().substring(2,3).equals("3")) { //if there is a 30 minute interval, add 0.5 to start time (counted in hours)
                                    startTime += 0.5;
                                }
                                
                            } else {
                                startTime = Double.parseDouble(appointment.get(previousCustomerIndex).getApptEndTime().substring(0,1));
                                
                                if (appointment.get(previousCustomerIndex).getApptEndTime().substring(2,3).equals("3")) {
                                    startTime += 0.5;
                                }
                            }
                            
                        }
                                            
                        
                        endTime = startTime + serviceLength;                  
                        
                        do {
                            
                            mechanicIndex = findMechanicIndex(startTime, mechanics, serviceReq, serviceLength); //calls findMechanicIndex method
                            
                            if (mechanicIndex != -1) { //if index returned is not -1 (indicating a mechanic is available at this time)
                                mechanicName = mechanics.get(mechanicIndex).getName(); //call getter method to get the mechanic's name
                            } else {
                                startTime += 0.5;
                                endTime += 0.5;
                            }
                            
                        } while (mechanicIndex == -1);
                        
                        
                        if (endTime > 17) { 
                            
                            workshopFull = true;
                            
                        } else if (endTime <= 17) { //if end time falls within store hours
                            
                            currentCustomer.setScheduled(true); 
                            mechanics.get(mechanicIndex).setNewDay(false); //sets new day to false, indicating the next appointment will not be the first of a new day
                            
                            costStatement = calculateCost(serviceCost, mechanics.get(mechanicIndex).getPayRate(), serviceLength); //calls calculateCost method to get the calculated cost               
                            
                            startDisplayTime = formatStartTime(startTime); //calls formatStartTime method to change the startTime (in hours elapsed since 9) to HH:MM(a/pm) format
                            endDisplayTime = formatEndTime(endTime); //calls formatEndTime method to change the endTime (in hours elapsed since 9) to HH:MM(a/pm) format                    
                            
                            appointment.add(new Appointment(startDisplayTime, endDisplayTime, workshop, currentCustomer.getName(), currentCustomer.getCarDescription(), currentCustomer.getServiceReq(), mechanicName, costStatement));
                            
                            firstApptOfDay = false; //indicates that next appointment will not be the first appointment of the day
                            newDay = false; //indicates that next appointment is not a new day
                            
                            fileOutput.println(appointment.get(scheduledCustomers).getApptStartTime() + " - " + appointment.get(scheduledCustomers).getApptEndTime());
                            fileOutput.println(appointment.get(scheduledCustomers).getCustomerName() + " - " + appointment.get(scheduledCustomers).getCustomerCar());
                            fileOutput.println(appointment.get(scheduledCustomers).getServiceReq() + " performed by " + appointment.get(scheduledCustomers).getMechName());
                            fileOutput.println(appointment.get(scheduledCustomers).getCalculatedCost());
                            
                            scheduledCustomers++;
                            
                            if (scheduledCustomers == customers.size()) {
                                doneSchedule = true;                     
                            }
                            
                            carsInWorkshop++;
                            
                        }
                    }
                    
                    if (carsInWorkshop == 0) { //print if there are no cars scheduled in the workshop
                        fileOutput.println("No cars in this workshop\n");
                    }
                } 
            } 
            weekNum++; 
            
        } while (!doneSchedule); //the number of scheduled customers does not match the number of customers to be scheduled
        
        
        fileOutput.close(); //close printwriter
        
        
    } //end of main
 
} //END OF CLASS
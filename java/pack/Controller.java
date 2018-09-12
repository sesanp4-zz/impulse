/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import Entities.ActiveUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("api")
public class Controller {

    @Context
    private UriInfo context;
    
    JsonObject obj, obj2;
    Gson gson;
    Dao dao= new Dao();
    Actions action = new Actions();
    String status;
    
    Logger log=Logger.getLogger("Controller.class");
    

    /**
     * Creates a new instance of Controller
     */
    public Controller() {
        
    }

    /**
     * Retrieves representation of an instance of pack.Controller
     * @return an instance of java.lang.String
     */
    
    
        @Path("/test")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String test(String payload){
           gson = new Gson();
           status="i see you";
           
           log.logp(Level.INFO, "Controller.class", "activate", "Request***********"+payload);
           log.logp(Level.INFO, "Controller.class", "activate", "Response***********"+status);
           return status;
        }
   
        @Path("/activate")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String activate(String payload){
            
           obj = new JsonParser().parse(payload).getAsJsonObject();
           ActiveUser user = new ActiveUser();
           gson = new Gson();
           
           user.setFirstname(obj.get("firstname").getAsString());
           user.setLastname(obj.get("lastname").getAsString());
           user.setPhonenumber(obj.get("phonenumber").getAsString());
           user.setSavingsperiod(obj.get("savingperiod").getAsString());
           user.setActivation_date(LocalDate.now().toString());
           user.setPercentage(obj.get("percentage").getAsString());
           
           log.logp(Level.INFO, "Controller.class", "activate", gson.toJson(user));
           
           status = dao.activate(user);
           JsonObject obj2 = new JsonObject();
           obj2.addProperty("message", status);
           return obj2.toString();
           
        }
        
        
        @Path("/calculate")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String calculate(String payload){
           obj = new JsonParser().parse(payload).getAsJsonObject();
           String phonenumber = obj.get("phonenumber").getAsString();
           double amount =  Double.parseDouble(obj.get("amount").getAsString());
           return action.makeTransaction(phonenumber, amount);           
        }
        
        
        @Path("/update/savings")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String calculateAndReturn(String payload){
           obj = new JsonParser().parse(payload).getAsJsonObject();
           String phonenumber = obj.get("phonenumber").getAsString();
           double amount =  Double.parseDouble(obj.get("amount").getAsString());
           String responseCode = obj.get("responseCode").getAsString();
           return action.updateSavings(phonenumber, Double.toString(amount), responseCode);
           
        }
        
        
        @Path("/return/savings")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String getSavingsBack(String payload){
           obj = new JsonParser().parse(payload).getAsJsonObject();
           obj2 = new JsonObject();
           String phonenumber = obj.get("phonenumber").getAsString();
           double total =  action.ReturnSavings(phonenumber);
           obj2.addProperty("savings", total);
           return obj2.toString();
           
        }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import Entities.ActiveUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author centricgateway
 */
public class Actions {
    
    JsonObject obj;
    Gson gson;
    Dao dao;
    
    public Actions(){
       dao= new Dao();
    }
    
     public void activate(ActiveUser user){
      // dao= new Dao();
       String status =  dao.activate(user);        
        }
    
    
    public String makeTransaction(String phonenumber,double amount){
       
        obj = new JsonObject();
        //dao= new Dao();
        
       if(amount<1000){
         obj.addProperty("Message", "amount is below 1000.00");
       }else{
            //Get the percentage set on the user profile
             String percentage = dao.getUserPercentage(phonenumber);
             String amount_to_charge=Double.toString(calculate(Integer.parseInt(percentage), amount));
             obj.addProperty("amount", amount_to_charge);
       }   
        return obj.toString();
       }
    
   
    public double calculate(int percentage,double amount){
        double calculated_amount=(amount/100)*percentage;
        return calculated_amount;
    }
    
    public String updateSavings(String email, String amount, String responseCode){
        //dao = new Dao();
        return dao.updateSavings(email, amount, responseCode);
        
    }
    
    
    public double ReturnSavings(String email){
        return   dao.ReturnSavings(email);       
    }
    
    
    public void calculateChecksum(){
        String firstname="abu";
        String lastname="barkar";
        String email="abu@gmail.com";
        String phonenumber="07053191016";
        String savingperiod="3";
        String percentage ="20";
        
        String checksum="abu|barkar|abu@gmail.com|07053191016|3|20";
      
         String sha256hex = DigestUtils.sha1Hex(checksum.getBytes());
         System.out.println(DigestUtils.md5Hex(sha256hex));     
    
    }
    
   
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
       // Actions act= new Actions();
       // act.calculateChecksum();
        //System.out.println(act.makeTransaction("abu@gmail.com", 1000.00));
        //SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //System.out.println(new String(DigestUtils.sha512("password")));
        //System.out.println(DigestUtils.sha512Hex("password"));
        
         
        
    }
    
}

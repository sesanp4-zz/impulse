/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import Entities.ActiveUser;
import Entities.Savings;
import com.google.gson.JsonObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author centricgateway
 */
public class Dao {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ImpulsePU");
    EntityManager em = emf.createEntityManager();
    
    Query query;
    JsonObject obj;
    
    public String activate(ActiveUser user){
      
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        
        return "Successful";
        
    }
    
    public String getUserPercentage(String phonenumber){
      
        em.getTransaction().begin();
        query = em.createQuery("select user from ActiveUser user where user.phonenumber=?1");
        query.setParameter(1, phonenumber);
        ActiveUser user =(ActiveUser) query.getSingleResult();
        em.getTransaction().commit();
        return user.getPercentage();
        
    }
    
    
    public String updateSavings(String phonenumber, String amount, String responseCode){
        obj= new JsonObject();
        if(responseCode.equals("00")){
            
            Savings savings = new Savings();
            savings.setAmount(Double.parseDouble(amount));
            savings.setPhonenumber(phonenumber);
            savings.setTransaction_date(LocalDateTime.now().toString());
            
             em .getTransaction().begin();
             em.persist(savings);
             em.getTransaction().commit();
             em.close();
             
            obj.addProperty("Message", "update successful");
            
        }else{
            
          obj.addProperty("Message", "Invalid Transaction");
        }
      return obj.toString();
    }
    
    
    public double ReturnSavings(String phonenumber){
    
      em.getTransaction().begin();
      Query query = em.createQuery("select SUM(saving.amount) from Savings saving where saving.phonenumber=?1");
      query.setParameter(1, phonenumber);
      double total =(double)query.getSingleResult();
      em.getTransaction().commit();
        return total;
    
    }
    
    
    
    public static void main(String[] args) {
        //new Dao().getUser("abu@gmail.com");
        //new Dao().calculateAllAndReturnSavings("abu@gmail.com");
    }
    
}

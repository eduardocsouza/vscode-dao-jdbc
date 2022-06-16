package app;

import java.util.Date;

import models.dao.DaoFactory;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

public class App {
    public static void main(String[] args){

        SellerDao seller = DaoFactory.createSellerDao();
        //Seller sl = new Seller();
        // sl = seller.findById(2);

        // System.out.println(sl); 

        

        Seller obj = new Seller(null, "dudu", "dudu@gmail.com", new Date(), 8400.0, new Department(2, null));

        seller.insert(obj);

        System.out.println("ID do novo Usuário: " + obj.getId());
        
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;
import DatabaseLayer.DatabaseConnection;
import Model.User;

/**
 *
 * @author Stefan
 */
public class UpdatePasswordServices {
    
    
      public boolean UpdatePassword(String username,String password){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "Update LoginTable set Password='" + password+ "' where Username='" + username+"'";
        return conn.Insert(query);
    }
}

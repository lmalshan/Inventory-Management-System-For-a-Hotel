/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;
import Model.User;
import DatabaseLayer.DatabaseConnection;

/**
 *
 * @author Stefan
 */
public class UserServices {
    
    
    public boolean AddUser(User user) {

        DatabaseConnection conn = new DatabaseConnection();
        String query = "Insert into LoginTable(FirstName,LastName,USerType,Username,Password,date,time)values('" + user.getfName()+ "','" + user.getlName()+ "','" + user.getUserType() + "','" + user.getUserName()+ "','"+user.getPassword()+"','"+user.getDate()+"','"+user.getTime()+"')";
                return conn.Insert(query);
    }
    
    
}

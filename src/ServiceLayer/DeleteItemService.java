/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;


import DatabaseLayer.DatabaseConnection;
/**
 *
 * @author Supun Thiwanka
 */
public class DeleteItemService {
    
    public boolean DeleteItem(int ItemID){
        
        DatabaseConnection conn=new DatabaseConnection();
        String query="delete from ItemTable where ItemID="+ItemID;
        return conn.Insert(query);
    }
    
    
}

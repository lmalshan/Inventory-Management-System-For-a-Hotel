/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;
import DatabaseLayer.DatabaseConnection;
/**
 *
 * @author Dean
 */
public class ViewItemService {
    public boolean viewitem(){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "select * from ItemTable";
        return conn.Insert(query);
    }
}

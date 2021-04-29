/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

/**
 *
 * @author Supun Thiwanka
 */
import DatabaseLayer.DatabaseConnection;
import Model.Item;

public class UpdateItemService {

    public boolean UpdateItem(Item item, int ItemID) {

        DatabaseConnection conn = new DatabaseConnection();
        String query = "Update ItemTable set Name='" + item.getItemName() + "',Category='" + item.getCategory() + "',Price='" + item.getPrice() + "',ExpDate='" + item.getExpdate() + "',ROAmount='"+item.getROAmount()+"',ROLevel='"+item.getROLevel()+"' where ItemID=" + ItemID;
        return conn.Insert(query);
    }

}

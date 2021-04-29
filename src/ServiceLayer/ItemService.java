package ServiceLayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.Item;
import DatabaseLayer.DatabaseConnection;

/**
 *
 * @author Supun Thiwanka
 */
public class ItemService {

    public boolean AddItem(Item item) {

        DatabaseConnection conn = new DatabaseConnection();
        String query = "Insert into ItemTable(Name,Category,Price,ExpDate,ROAmount,ROLevel)values('" + item.getItemName() + "','" + item.getCategory() + "','" + item.getPrice() + "','" + item.getExpdate() + "','"+item.getROAmount()+"','"+item.getROLevel()+"')";
        return conn.Insert(query);
    }
}

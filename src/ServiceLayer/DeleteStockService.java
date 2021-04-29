/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

/**
 *
 * @author Supun Thiwanka
 *
 *
 */
import DatabaseLayer.DatabaseConnection;

public class DeleteStockService {

    public boolean DeleteStock(int ItemID,String Date) {

        DatabaseConnection conn = new DatabaseConnection();

        return conn.Insert("delete from StockTable where ItemID=" + ItemID+" && Date='"+Date+"'");
    }

}

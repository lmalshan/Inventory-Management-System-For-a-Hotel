/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;
import DatabaseLayer.DatabaseConnection;
import Model.Stock;
/**
 *
 * @author Dean
 */
public class UpdateStockService {
    public boolean UpdateStock(Stock stock, String date) {

        DatabaseConnection conn = new DatabaseConnection();
        String query = "Update StockTable set ItemID='" + stock.getItemID() + "',Name='" + stock.getName() + "',Category='" + stock.getCategory() + "',Quantity='" + stock.getQuantity() + "',UnitPrice='" + stock.getUnitPrice() + "',SupplierID='" + stock.getSupplierID() + "',Date='" +stock.getDate() + "' where Date=" + date;
        return conn.Insert(query);
    }
}

      //  String query = "Update ItemTable set Name='" + item.getItemName() + "',Category='" + item.getCategory() + "',Price='" + item.getPrice() + "',ExpDate='" + item.getExpdate() + "',ROAmount='"+item.getROAmount()+"',ROLevel='"+item.getROLevel()+"' where ItemID=" + ItemID;

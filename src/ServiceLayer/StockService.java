/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import Model.Stock;
import DatabaseLayer.DatabaseConnection;



/**
 *
 * @author Supun Thiwanka
 */
public class StockService {
    
    
    public boolean AddStock(Stock stock){
        
        DatabaseConnection conn=new DatabaseConnection();
        String query="Insert into StockTable(ItemID,Name,Category,Quantity,UnitPrice,SupplierID,Date) values('"+stock.getItemID()+"','"+stock.getName()+"','"+stock.getCategory()+"','"+stock.getQuantity()+"','"+stock.getUnitPrice()+"','"+stock.getSupplier()+"','"+stock.getDate()+"')";
        return conn.Insert(query);
    }
}

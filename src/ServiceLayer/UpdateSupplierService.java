/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;
import Model.Supplier;
import DatabaseLayer.DatabaseConnection;
/**
 *
 * @author Supun Thiwanka
 */
public class UpdateSupplierService {
    
    public boolean UpdateSupplier(Supplier supplier, int SupplierID){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "Update SupplierTable set Name='" + supplier.getSupplierName() + "',Address='" + supplier.getAddress() + "',Telephone='" + supplier.getTelephone() + "',Category='"+supplier.getCategory()+"',Email='" + supplier.getEmail() + "' where SupplierID=" + SupplierID;
        return conn.Insert(query);
    }
}

        //String query = "Update ItemTable set Name='" + item.getItemName() + "',Category='" + item.getCategory() + "',Price='" + item.getPrice() + "',ExpDate='" + item.getExpdate() + "',ROAmount='"+item.getROAmount()+"',ROLevel='"+item.getROLevel()+"' where ItemID=" + ItemID;

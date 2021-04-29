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
public class SupplierService {
    public boolean AddSupplier(Supplier supplier) {

        DatabaseConnection conn = new DatabaseConnection();
        String query = "Insert into SupplierTable(Name,Address,Telephone,Category,Email)values('" + supplier.getSupplierName()+ "','" + supplier.getAddress()+ "','" + supplier.getTelephone() + "','"+supplier.getCategory()+"','" + supplier.getEmail()+ "')";
        return conn.Insert(query);
    }
}

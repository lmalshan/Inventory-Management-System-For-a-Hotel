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
public class DeleteSupplierService {

    public boolean DeleteSupplier(int SupplierID) {

        DatabaseConnection conn = new DatabaseConnection();

        return conn.Insert("delete from SupplierTable where SupplierID=" + SupplierID);
    }
}

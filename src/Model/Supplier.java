/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Supun Thiwanka
 */
public class Supplier {

    private int SupplierID;
    private String SupplierName;
    private String Address;
    private String Telephone;
    private String Category;
    private String Email;

    public Supplier(int SupplierID, String SupplierName, String Address, String Telephone, String Category, String Email) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.Address = Address;
        this.Telephone = Telephone;
        this.Category = Category;
        this.Email = Email;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    

    /**
     * @return the SupplierName
     */
    public String getSupplierName() {
        return SupplierName;
    }

    /**
     * @param SupplierName the SupplierName to set
     */
    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * @return the Telephone
     */
    public String getTelephone() {
        return Telephone;
    }

    /**
     * @param Telephone the Telephone to set
     */
    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return the Category
     */
    public String getCategory() {
        return Category;
    }

    /**
     * @param Category the Category to set
     */
    public void setCategory(String Category) {
        this.Category = Category;
    }
}

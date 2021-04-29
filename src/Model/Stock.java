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
public class Stock {

    private int ItemID;
    private String Name;
    private String Category;
    private int Quantity;
    private float UnitPrice;
    private int SupplierID;
    private String Date;

    public Stock(int ItemID, String Name, String Category, int Quantity, float UnitPrice, int SupplierID, String Date) {
        this.ItemID = ItemID;
        this.Name = Name;
        this.Category = Category;
        this.Quantity = Quantity;
        this.UnitPrice = UnitPrice;
        this.SupplierID = SupplierID;
        this.Date = Date;
    }

    /**
     * @return the ItemID
     */
    public int getItemID() {
        return ItemID;
    }

    /**
     * @param ItemID the ItemID to set
     */
    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
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

    /**
     * @return the Quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * @return the Supplier
     */
    public int getSupplier() {
        return SupplierID;
    }

    /**
     * @param SupplierID
     */
    public void setSupplier(int SupplierID) {
        this.SupplierID = SupplierID;
    }

    /**
     * @return the UnitPrice
     */
    public float getUnitPrice() {
        return UnitPrice;
    }

    /**
     * @param UnitPrice the UnitPrice to set
     */
    public void setUnitPrice(float UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public String getDate() {
        return Date;
    }

    public void setSupplierID(int SupplierID) {
        this.SupplierID = SupplierID;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    
    

}

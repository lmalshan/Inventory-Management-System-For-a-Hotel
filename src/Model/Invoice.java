/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Dean
 */
public class Invoice {
    private int ItemID;
    private String Name;
    private String Category;
    private String Quantity;
    private float Price;
    private float SubTotal;
    private float TotalPrice;
    private String Date;
    private String Time; 

    public Invoice(int ItemID, String Name, String Category, String Quantity, float Price, float SubTotal, float TotalPrice, String Date, String Time) {
        this.ItemID = ItemID;
        this.Name = Name;
        this.Category = Category;
        this.Quantity = Quantity;
        this.Price = Price;
        this.SubTotal = SubTotal;
        this.TotalPrice = TotalPrice;
        this.Date = Date;
        this.Time = Time;
    }

    public void setSubTotal(float SubTotal) {
        this.SubTotal = SubTotal;
    }

   

    public int getItemID() {
        return ItemID;
    }

    public String getName() {
        return Name;
    }

    public String getCategory() {
        return Category;
    }

    public String getQuantity() {
        return Quantity;
    }

    public float getPrice() {
        return Price;
    }

    public float getTotalPrice() {
        return TotalPrice;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public void setTotalPrice(float TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
   
    
}

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
public class Item {

    private int ItemID;
    private final String ItemName;
    private String Category;
    private float Price;
    private String Expdate;
    private int ROAmount;
    private int ROLevel;

    public Item(int ItemID, String ItemName, String Category, float Price, String Expdate, int ROAmount, int ROLevel) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.Category = Category;
        this.Price = Price;
        this.Expdate = Expdate;
        this.ROAmount = ROAmount;
        this.ROLevel = ROLevel;
    }

    public int getItemID() {
        return ItemID;
    }

    

    public String getItemName() {
        return ItemName;
    }

    public String getCategory() {
        return Category;
    }

    public float getPrice() {
        return Price;
    }

    public String getExpdate() {
        return Expdate;
    }

    public int getROAmount() {
        return ROAmount;
    }

    public int getROLevel() {
        return ROLevel;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public void setExpdate(String Expdate) {
        this.Expdate = Expdate;
    }

    public void setROAmount(int ROAmount) {
        this.ROAmount = ROAmount;
    }

    public void setROLevel(int ROLevel) {
        this.ROLevel = ROLevel;
    }

}

package com.rathandevaki.farmersworld;

public class SingleProductPost {

    // Variable to store data corresponding
    // to firstname keyword in database
    private String ProductName;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String Verity;

    // Variable to store data corresponding
    // to age keyword in database
    private String Quantity;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public SingleProductPost() {}

    // Getter and setter method
    public String getProductName()
    {
        return ProductName;
    }
    public void setProductName(String ProductName)
    {
        this.ProductName = ProductName;
    }
    public String getVerity()
    {
        return Verity;
    }
    public void setVerityName(String Verity)
    {
        this.Verity = Verity;
    }
    public String getQuantity()
    {
        return Quantity;
    }
    public void setQuantity(String Quantity)
    {
        this.Quantity = Quantity;
    }
}

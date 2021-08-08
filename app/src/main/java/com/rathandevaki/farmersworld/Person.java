package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.SharedPreferences;

public class Person extends ProfileFragment
{
   // ProfileFragment uid= new ProfileFragment();
    Context context;
    private String ProductName;
    private String likedUsedId="";
    private String Rate,ProductPhoto,Required,UserID,UserName,Verity;
    SharedPreferences sharedPreferences;

    private String Quantity;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Person() {
    }


    // Getter and setter method
   /* public String getPrefID ()
    {
        sharedPreferences= context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
       likedUsedId= sharedPreferences.getString("UserID", "");
        return likedUsedId;
    }*/
    public String getUserID ()
    {
        return UserID;
    }
    public void setUserID(String UserID)
    {
        this.UserID=UserID;
    }



    public String getUserName()
    {
        return UserName;
    }
    public void setUserName(String UserName)
    {
        this.UserName=UserName;
    }

    public String getVerity()
    {
        return Verity;
    }
    public void setVerity(String Verity)
    {
        this.Verity=Verity;
    }

    public String getRequired()
    {
        return Required;
    }
    public void setRequired(String Required)
    {
        this.Required = Required;
    }


    public String getProductPhoto()
    {
        return ProductPhoto;
    }
    public void setProductPhoto(String ProductPhoto)
    {
        this.ProductPhoto = ProductPhoto;
    }

    public String getProductName()
    {
        return ProductName;
    }
    public void setProductName(String ProductName)
    {
        this.ProductName = ProductName;
    }


    public String getRate()
    {
        return Rate;
    }
    public void setRate(String Rate)
    {
        this.Rate = Rate;
    }
    public String getQuantity()
    {
        return Quantity;
    }
    public void Quantity(String Quantity)
    {
        this.Quantity = Quantity;
    }
}
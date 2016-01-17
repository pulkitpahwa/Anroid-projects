package in.pulkitpahwa.fooodies;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pulkit on 18/4/15.
 */
public class MyCart {

//the home-delivery app will send the get request to a given url. The url will send back a json reply to the app.
    //the reply will contain the receipt number of the app.
    //we can do one thing, that is to start a service once a request is sent. What this service will do is to keep checking after every 30 seconds, to check if the order is approved.
    //if the order is approved, it can let us know how much time will it take.

    private Integer basket_id = 0;
    private String item_name = new String();
    private String item_id = new String();
    private Double item_price ;
    private Integer item_qty = 1;
    private Double taxable ;
    private Double tax ;
    private Double total ;
    //private List<Double> item_total = new ArrayList<Double>();
    //private String bill_no = "0";// default = 000 if it is the first order for the particular bill, then before the order is placed, the bill_no will be 0. later we will fetch it as json.


    public MyCart()
    {}


    //this will happen only in case : we have individual item to be added to the cart.
    // I think we will add items only one by one. atleast in this app. If the interface is like foodpanda, we can use this constructor,
    //If the interface is like tinyowl, we will have to use other constructor.
    public MyCart(Integer basket_id, String name, String iid, Double item_price, Integer qty, Double taxable,
                  Double tax, Double total) {

        this.item_name = name;
        this.item_id = iid;
        this.item_price = item_price;
        this.item_qty = qty ;
        this.taxable = taxable;
        this.tax = tax;
        this.total = taxable*(1+ 0.01*tax);
        //this.bill_no = bill_no;
        //this.current_total = this.current_total + price;
        Log.w("myapp","item_names : " + item_name.toString());
        Log.w("myapp","item_ids : " + item_id.toString());
        Log.w("myapp","item_price : " + item_price.toString());
        Log.w("myapp","item_qty : " + item_qty.toString());
        //Log.w("myapp", "current total: " + current_total.toString());
    }



    public String getItemName()
    {
        return this.item_name;
    }
    public String getItemID(){
        return this.item_id;
    }
    public Double getTaxableAmt()
    {
        return this.taxable;
    }
    public Double getTAX(){
        return this.tax;
    }
    public Double getTotal()
    {
        return this.total;
    }
    public Integer getQty()
    {
        return this.item_qty;
    }
    public Integer getBasketID()
    {
        return this.basket_id;
    }
    public  Double getPrice() {
        return item_price;
    }

    public void setBASKET_ID(Integer BASKET_ID) {
        this.basket_id = BASKET_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.item_id = ITEM_ID;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.item_name = ITEM_NAME;
    }

    public void setTaxable(Double taxable) {
        this.taxable = taxable;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void setQty(Integer qty) {
        this.item_qty = qty;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    public void setPrice(double price) {
        this.item_price = price;
    }
}

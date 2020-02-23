package com.example.jus_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    int price=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        boolean checkWhip=checkWhippedCream();
        boolean checkChoc=checkChocolate();

        EditText editText=(EditText) findViewById(R.id.name);
        String name = editText.getText().toString();

        int totPrice=calculatePrice(quantity,checkWhip,checkChoc);
        String message=createOrderSummary(totPrice,checkWhip,checkChoc,name);
        //display(quantity);
        //displayMessage(message);
        composeEmail(message);

    }

    public void composeEmail( String text) {
        String[] addresses={"smitshrestha101@outlook.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String msg) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(msg);
    }

    public void increment(View view){
        if (quantity<100){
        quantity++;}
        else Toast.makeText(this,"Cannot order more than 100",Toast.LENGTH_SHORT).show();
        display(quantity);
        //displayPrice("Total: $"+ quantity*price);
    }

    public void decrement(View view){
        if(quantity>0){
        quantity--;}
        else Toast.makeText(MainActivity.this,"Cannot order less than 0",Toast.LENGTH_SHORT).show();
        display(quantity);
        //displayPrice("Total: $"+ quantity*price);
    }

    private int calculatePrice(int quantity,boolean checkWhip,boolean checkChoc){
        int basePrice=5;
        if (checkWhip){
            basePrice++;}
        if(checkChoc){
            basePrice=basePrice+2;
        }
        return basePrice*quantity;
    }

    private String createOrderSummary(int totalPrice, boolean checkWhip, boolean checkChoc, String name){
        String msg= "Name: "+ name +
                "\nAdd Whipped cream? "+ checkWhip+
                "\n Add Chocolate? "+ checkChoc+
                "\nQuantity: "+quantity+
                "\nTotal: $"+totalPrice+
                "\nThank You!";
        return msg;

    }

    private boolean checkWhippedCream(){
        CheckBox checkbox=(CheckBox) findViewById(R.id.checkWhipped);
        if (checkbox.isChecked()){
            return true;
        }
        return false;
    }

    private boolean checkChocolate(){
        CheckBox checkbox=(CheckBox) findViewById(R.id.checkChocolate);
        if (checkbox.isChecked()){
            return true;
        }
        return false;
    }
}

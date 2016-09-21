// Add your package below
/* package com.example.android; */

package com.example.android.brewmaster;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean whippedCreamVariable = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean chocolateVariable = chocolate.isChecked();
        EditText name = (EditText) findViewById(R.id.getname);
        String nameVariable = name.getText().toString();
        int price = calculatePrice(whippedCreamVariable, chocolateVariable);
        ImageView background = (ImageView) findViewById(R.id.background);
        if (quantity != 0) {
            background.setImageResource(R.drawable.full112);
        } else background.setImageResource(R.drawable.empty123);
        if (quantity == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Order atleast 1 cup!!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        if (quantity == 0) {
            displayMessage("₹0");
            confirmOrder(quantity);
        } else {
            String confirmationMessage = createOrderSummary(whippedCreamVariable, chocolateVariable, nameVariable, price);
            displayMessage(confirmationMessage);
            boolean confirmation = confirmOrder(quantity);
            if (confirmation == true) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:7c611e86@opayq.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order summary for " + nameVariable);
                intent.putExtra(Intent.EXTRA_TEXT, confirmationMessage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        }
    }

    /**
     * This method is called when the + button is clicked.
     */

    public void increment(View view) {
        if (quantity >= 20) {
            Context context = getApplicationContext();
            CharSequence text = "You have reached the maximum limit per order!!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;

        displayQuantity(quantity);
        ImageView background = (ImageView) findViewById(R.id.background);
        if (quantity != 0) {
            background.setImageResource(R.drawable.full112);
        }
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * price calculator
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = 15;
        if (whippedCream) {
            price += 10;
        }
        if (chocolate) {
            price += 12;
        }
        return price * quantity;


    }

    /**
     * order summary
     */
    private String createOrderSummary(boolean whippedCream, boolean chocolate, String name, int price) {
        String a = "You have added whipped cream";
        String b = ",chocolate";

        String summary = "Name : ";
        summary += name;
        summary += "\n";
        if (whippedCream) {
            summary += a;
        }
        if (chocolate) {
            summary += b;
        }
        if (whippedCream | chocolate) {
            summary += "!!\n";
        }
        summary += "Quantity : " + quantity;
        summary += "\nTotal : ₹" + price;
        summary += "\nThank You!!";

        return summary;
    }

    private boolean confirmOrder(int number) {
        if (number > 0) {
            Button confirm = (Button) findViewById(R.id.button);
            confirm.setText("CONFIRM");
            return true;
        } else {
            Button confirm = (Button) findViewById(R.id.button);
            confirm.setText("ORDER");
            return false;
        }
    }

}
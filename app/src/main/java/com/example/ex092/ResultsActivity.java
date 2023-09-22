package com.example.ex092;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Ori Roitzaid <or1901 @ bs.amalnet.k12.il>
 * @version 1
 * @since 18/9/2023
 * The results activity:
 * Calculates the 20 first values of a given series which its data was got from the main activity,
 * and presents them in a list view to the user.
 */
public class ResultsActivity extends AppCompatActivity implements View.OnCreateContextMenuListener,
        AdapterView.OnItemLongClickListener {
    ListView lv;
    ArrayAdapter<Double> adp;
    TextView infoTv;
    Intent gi;
    int seriesType, itemPosition;
    double firstValue, diffQuot, seriesSum;
    Double[] seriesArr = new Double[20];
    String oper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Links each xml element to an object
        lv = (ListView) findViewById(R.id.lv);
        infoTv = (TextView) findViewById(R.id.infoTv);

        gi = getIntent();

        // Extracts the values from the intent
        seriesType = gi.getIntExtra("Type", -999);
        firstValue = gi.getDoubleExtra("First", -999);
        diffQuot = gi.getDoubleExtra("Diff", -999);

        createSeriesArr(seriesType, firstValue, diffQuot, seriesArr);

        // Creates the adapter and connects the series array to the list view
        adp = new ArrayAdapter<Double>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, seriesArr);
        lv.setAdapter(adp);

        lv.setOnItemLongClickListener(this);
        lv.setOnCreateContextMenuListener(this);

    }

    /**
     * This function calculates the 20 first values of a given series, and saves them into an array.
     * <p>
     *
     * @param type The type of the series - 0 for arithmetic, 1 for geometric.
     * @param first The first value of the series.
     * @param d The difference/quotient of the series.
     * @param arr The array to save the values of the series in.
     */
    public void createSeriesArr(int type, double first, double d, Double[] arr){
        if(type == 0){
            for(int i = 0; i < 20; i++) {
                arr[i] = first + d * i;
            }
        }
        else{
            for(int i = 0; i < 20; i++) {
                arr[i] = first * Math.pow(d, i);
            }
        }
    }

    /**
     * This function calculates the sum of a given series, from the first value of it to a given
     * value in the series.
     * <p>
     *
     * @param type The type of the series - 0 for arithmetic, 1 for geometric.
     * @param n The index of the given value in the series.
     * @param a1 The first value of the series.
     * @param d The difference/quotient of the series.
     * @return The sum of the series from a1 to an.
     */
    public double calcSeriesSum(int type, int n, double a1, double d){
        double Sn = 0;

        if(type == 0)
            Sn = ((2 * a1 + d * (n - 1)) * n) / 2;
        else
            Sn = a1 * ((Math.pow(d, n) - 1) / (d - 1));

        return Sn;
    }

    /**
     * This function builds a context menu of the actions to do when a series item is selected,
     * and presents it to the screen.
     * <p>
     *
     * @param menu The context menu that is being built.
     * @param v The view for which the context menu is being built.
     * @param menuInfo Extra information about the item for which the
     *            context menu should be shown. This information will vary
     *            depending on the class of v.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.setHeaderTitle("Choose info to display");
        menu.add("Position of the item in series");
        menu.add("Series sum until this item");
    }

    /**
     * This function reacts to the choice of the user from the context menu.
     * <p>
     *
     * @param item The context menu item that was selected.
     * @return true.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item){
        oper = item.getTitle().toString();

        if(oper.startsWith("Position")){
            infoTv.setText("n=" + itemPosition);
            return true;
        }
        else if(oper.startsWith("Series")){
            seriesSum = calcSeriesSum(seriesType, itemPosition, firstValue, diffQuot);
            infoTv.setText("Sn=" + seriesSum);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * This function saves the position of the item in series which was long clicked.
     * <p>
     *
     * @param parent The AbsListView where the click happened.
     * @param view The view within the AbsListView that was clicked.
     * @param position The position of the view in the list.
     * @param id The row id of the item that was clicked.
     *
     * @return false.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        itemPosition = position + 1;

        return false;
    }

    /**
     * This function moves the user back to the main activity.
     * <p>
     *
     * @param view The view object of the clicked button.
     */
    public void goBack(View view) {
        finish();
    }
}
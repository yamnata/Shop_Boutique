package com.yamnata.shopping;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yamnata.shopping.CheckRecyclerViewAdapter;
import com.yamnata.shopping.ProductObject;
import com.yamnata.shopping.MySharedPreference;
import com.yamnata.shopping.SimpleDividerItemDecoration;
import com.yamnata.shopping.CheckRecyclerViewAdapter;
import com.yamnata.shopping.ProductActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = CheckoutActivity.class.getSimpleName();
    private RecyclerView checkRecyclerView;
    public static TextView subTotal;
    public static double mSubTotal = 0;
    private List<ProductObject> productList;
    public static MySharedPreference sharedPreference;
    private Gson gson;
    private int cartProductNumber;
    private ProductObject[] addCartProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPreference = new MySharedPreference(CheckoutActivity.this);
        subTotal = (TextView )findViewById(R.id.sub_total);
        checkRecyclerView = (RecyclerView)findViewById(R.id.checkout_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckoutActivity.this);
        checkRecyclerView.setLayoutManager(linearLayoutManager);
        checkRecyclerView.setHasFixedSize(true);
        checkRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(CheckoutActivity.this));
        // get content of cart

        //MySharedPreference mShared = new MySharedPreference(CheckoutActivity.this);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        addCartProducts = gson.fromJson(sharedPreference.retrieveProductFromCart(), ProductObject[].class);
        productList = convertObjectArrayToListObject(addCartProducts);
        CheckRecyclerViewAdapter mAdapter = new CheckRecyclerViewAdapter(CheckoutActivity.this, productList);
        checkRecyclerView.setAdapter(mAdapter);

        mSubTotal = getTotalPrice(productList);
        subTotal.setText("Sous-total hors taxe et expédition: " + String.valueOf(mSubTotal) + " euros");

        Button shoppingButton = (Button)findViewById(R.id.shopping);
        assert shoppingButton != null;
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingIntent = new Intent(CheckoutActivity.this, ShoppingActivity.class);
                startActivity(shoppingIntent);
            }
        });
        Button checkButton = (Button)findViewById(R.id.checkout);
        assert checkButton != null;
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(CheckoutActivity.this, PayPalCheckoutActivity.class);
                paymentIntent.putExtra("TOTAL_PRICE", mSubTotal);
                startActivity(paymentIntent);
            }
        });
    }
    private List<ProductObject> convertObjectArrayToListObject(ProductObject[] allProducts){
        List<ProductObject> mProduct = new ArrayList<ProductObject>();
        if (allProducts==null) {

        }
        else   Collections.addAll(mProduct, allProducts);
        return mProduct;
    }
    private int returnQuantityByProductName(String productName, List<ProductObject> mProducts){
        int quantityCount = 0;
        for(int i = 0; i < mProducts.size(); i++){
            ProductObject pObject = mProducts.get(i);
            if(pObject.getProductName().trim().equals(productName.trim())){
                quantityCount++;
            }
        }
        return quantityCount;
    }
    public static double getTotalPrice(List<ProductObject> mProducts){
        double totalCost = 0;
        for(int i = 0; i < mProducts.size(); i++){
            ProductObject pObject = mProducts.get(i);
            totalCost = totalCost + pObject.getProductPrice();
        }
        return totalCost;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = sharedPreference.retrieveProductCount();
        menuItem.setIcon(buildCounterDrawable(mCount, R.drawable.cart));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(CheckoutActivity.this, CheckoutActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.shopping_layout, null);
        view.setBackgroundResource(backgroundImageId);
        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return new BitmapDrawable(getResources(), bitmap);
    }
    public void invalidateCart() {
        invalidateOptionsMenu();
    }
}
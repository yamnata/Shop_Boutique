package com.yamnata.shopping;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yamnata.shopping.R;
import com.yamnata.shopping.ProductObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class CheckRecyclerViewAdapter extends RecyclerView.Adapter<CheckRecyclerViewHolder> {
    customButtonListener customListner;
    ProductObject p;
    private Gson gson;
    public interface customButtonListener {
        public void onButtonClickListner(int position, ProductObject p);

    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
    private Context context;
    private List<ProductObject> mProductObject;

    public CheckRecyclerViewAdapter(Context context, List<ProductObject> mProductObject) {
        this.context = context;
        this.mProductObject = mProductObject;
    }
    @Override
    public CheckRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_layout, parent, false);
        CheckRecyclerViewHolder productHolder = new CheckRecyclerViewHolder(layoutView);
        return productHolder;
    }
    @Override
    public void onBindViewHolder(CheckRecyclerViewHolder holder, final int position) {
        //get product quantity
        holder.quantity.setText("1");
        holder.productName.setText(mProductObject.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(mProductObject.get(position).getProductPrice()) + " euro");
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mProductObject.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mProductObject.size());
               CheckoutActivity.sharedPreference.removeCart();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                List<ProductObject> cartProductList = new ArrayList<ProductObject>();
                ProductObject[] temp = new ProductObject[mProductObject.size()];

                for (int i=0; i< mProductObject.size(); i++) {

                        temp[i ] = mProductObject.get(i);

                    cartProductList.add(temp[i]);
                    String cartValue = gson.toJson(cartProductList);
                    CheckoutActivity.sharedPreference.addProductToTheCart(cartValue);

                }
                CheckoutActivity.sharedPreference.addProductCount(mProductObject.size());
                ((CheckoutActivity)context).invalidateOptionsMenu();
                CheckoutActivity.mSubTotal=CheckoutActivity.getTotalPrice(mProductObject);
                CheckoutActivity.subTotal.setText("Sous-total hors taxe et expédition: " + String.valueOf(CheckoutActivity.mSubTotal) + " euros");
            }
        });
    }
    @Override
    public int getItemCount() {
        return mProductObject.size();
    }



}
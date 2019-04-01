package com.yamnata.shopping;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yamnata.shopping.R;
public class CheckRecyclerViewHolder extends RecyclerView.ViewHolder{
    public TextView quantity, productName, productPrice, removeProduct;
    public CheckRecyclerViewHolder(View itemView) {
        super(itemView);
        quantity = (TextView)itemView.findViewById(R.id.quantity);
        productName =(TextView)itemView.findViewById(R.id.product_name);
        productPrice = (TextView)itemView.findViewById(R.id.product_price);
        removeProduct = (Button)itemView.findViewById(R.id.remove_from_cart);
    }
}

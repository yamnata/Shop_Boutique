package com.yamnata.shopping;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yamnata.shopping.ShopRecyclerViewAdapter;
import com.yamnata.shopping.ProductObject;
import com.yamnata.shopping.SpacesItemDecoration;
import java.util.ArrayList;
import java.util.List;
public class ShoppingActivity extends AppCompatActivity {
    private static final String TAG = ShoppingActivity.class.getSimpleName();
    private RecyclerView shoppingRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        shoppingRecyclerView = (RecyclerView)findViewById(R.id.product_list);
        GridLayoutManager mGrid = new GridLayoutManager(ShoppingActivity.this, 2);
        shoppingRecyclerView.setLayoutManager(mGrid);
        shoppingRecyclerView.setHasFixedSize(true);
        shoppingRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 12, false));
        ShopRecyclerViewAdapter shopAdapter = new ShopRecyclerViewAdapter(ShoppingActivity.this, getAllProductsOnSale());
        shoppingRecyclerView.setAdapter(shopAdapter);
    }
    private List<ProductObject> getAllProductsOnSale(){
        List<ProductObject> products = new ArrayList<ProductObject>();
        products.add(new ProductObject(1, "Top noir élégant", R.drawable.topnoir, "Beau top noir élégant pour tenue décontractée et promenade en soirée", 20, 38, "noir"));
        products.add(new ProductObject(1, "Robe noir", R.drawable.robenoir, "Belle robe noire élégante pour soirée", 80, 38, "noir"));
        products.add(new ProductObject(1, "Robe blanc Blouse", R.drawable.robeblanche, "Belle robe blanc élégante pour soirée", 20, 38, "blanc"));
        products.add(new ProductObject(1, "Robe bleu", R.drawable.robebleu, "Belle robe bleu élégante pour soirée", 20, 38, "bleu"));
        products.add(new ProductObject(1, "Robe à pois", R.drawable.robeverte, "Belle robe élégante pour soirée", 100, 38, "vert"));
        products.add(new ProductObject(1, "Robe colorée", R.drawable.robecolor, "Belle robe élégante pour soirée", 40, 38, "Multi-couleur"));
        return products;
    }

}
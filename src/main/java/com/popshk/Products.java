package com.popshk;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Products {
    @SerializedName("AllColorVariants")
    private List<ProductData> list = new ArrayList<ProductData>();

    public void add(ProductData productData){
        list.add(productData);
    }

    public List<ProductData> get(){
        return list;
    }

    public void reset(){
        list = new ArrayList<ProductData>();
    }
    @Override
    public String toString() {
        return Arrays.deepToString(list.toArray());
    }
}

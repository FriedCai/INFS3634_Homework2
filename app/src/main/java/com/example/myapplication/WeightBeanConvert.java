package com.example.myapplication;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class WeightBeanConvert {
    @TypeConverter
    public static Cats.WeightBean revert(String bean) {
           return new Gson().fromJson(bean,Cats.WeightBean.class);

    }

    @TypeConverter
    public static String converter(Cats.WeightBean areaInfoStr) {
        return new Gson().toJson(areaInfoStr);
    }
}

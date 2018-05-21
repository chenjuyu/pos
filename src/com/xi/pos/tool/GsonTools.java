package com.xi.pos.tool;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTools {

    public GsonTools() {
        // TODO Auto-generated constructor stub
    }

    //ʹ��Gson���н���Person
    public static <T> T getUser(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }
    
    
    // ʹ��Gson���н��� List<Person>
    public static <T> List<T> getUsers(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

}
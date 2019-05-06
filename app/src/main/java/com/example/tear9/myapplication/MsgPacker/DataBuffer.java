package com.example.tear9.myapplication.MsgPacker;

import java.util.HashMap;
import java.util.Map;

public class DataBuffer {
    Map<String, String> map;

    public DataBuffer() {
        map = new HashMap<String, String>();
    }


    public void set_data(String reqeust_number, String request_data) {
        map.put(reqeust_number, request_data);
    }

    public int sizeof(){
        int size=0;
        size = map.size();
        return size;
    }

    public boolean clear_buffer(){
        try{
            map.clear();
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public String get_data() {
        String temp_data = map.toString();
        return temp_data;
    }
}

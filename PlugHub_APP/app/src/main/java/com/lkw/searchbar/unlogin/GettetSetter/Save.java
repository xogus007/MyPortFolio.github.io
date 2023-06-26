package com.lkw.searchbar.unlogin.GettetSetter;

import net.daum.mf.map.api.MapPOIItem;

import java.util.ArrayList;

public class Save {
    private static Save instance;
    private ArrayList<MapPOIItem> markerList = new ArrayList<>();
    private ArrayList<MapPOIItem> red = new ArrayList<>();
    private ArrayList<MapPOIItem> blue = new ArrayList<>();
    private ArrayList<MapPOIItem> able = new ArrayList<>();
    private ArrayList<MapPOIItem> disable = new ArrayList<>();

    Save(){
        // 인스턴스화 방지를 위한 private 생성자
    }
    public static synchronized Save getInstance(){
        if (instance == null){
            instance = new Save();
        }
        return instance;
    }

    public ArrayList<MapPOIItem> getMarkerList() {
        return markerList;
    }
    public void addMarker(MapPOIItem marker) {
        markerList.add(marker);
    }

    public ArrayList<MapPOIItem> getRed() {
        return red;
    }

    public void setRed(ArrayList<MapPOIItem> red) {
        this.red = red;
    }

    public ArrayList<MapPOIItem> getBlue() {
        return blue;
    }

    public void setBlue(ArrayList<MapPOIItem> blue) {
        this.blue = blue;
    }

    public ArrayList<MapPOIItem> getAble() {
        return able;
    }

    public void setAble(ArrayList<MapPOIItem> able) {
        this.able = able;
    }

    public ArrayList<MapPOIItem> getDisable() {
        return disable;
    }

    public void setDisable(ArrayList<MapPOIItem> disable) {
        this.disable = disable;
    }
    public void clearAbleMarkers() {
        able.clear();
    }
    public void clearDisableMarkers() {
        disable.clear();
    }
}

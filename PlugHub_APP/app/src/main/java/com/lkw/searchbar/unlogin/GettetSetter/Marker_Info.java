package com.lkw.searchbar.unlogin.GettetSetter;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class Marker_Info {
    private ArrayList<MapPOIItem> markers = new ArrayList<>();
    private ArrayList<MapPOIItem> markerList = new ArrayList<>();
    private static Marker_Info instance;

    private Marker_Info() {
    }

    public static Marker_Info getInstance() {
        if (instance == null) {
            instance = new Marker_Info();
        }
        return instance;
    }

    public ArrayList<MapPOIItem> getMarkers() {
        return markers;
    }

    public void setMarkers(MapPOIItem marker) {
        markers.add(marker);
    }

    public void removeMarker(MapView mapView) {
        if (markers != null && markers.size() > 0) {
            for (MapPOIItem marker : markers) {
                mapView.removePOIItem(marker);
            }
            markers.clear();
        }
    }

    public ArrayList<MapPOIItem> getMarkerList() {
        return markerList;
    }

    public void clearMarkerList() {
        markerList.clear();
    }

    public void addMarker(MapPOIItem marker) {
        markerList.add(marker);
    }

    public void removeMarkers(MapView mapView) {
        if (markerList != null && markerList.size() > 0) {
            for (MapPOIItem marker : markerList) {
                mapView.removePOIItem(marker);
            }
            markerList.clear();
        }
    }
}

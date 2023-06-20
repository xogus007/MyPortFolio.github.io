package com.lkw.searchbar.BottomSheet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lkw.searchbar.GettetSetter.ArrayInfo;
import com.lkw.searchbar.GettetSetter.Marker_Info;
import com.lkw.searchbar.GettetSetter.Save;
import com.lkw.searchbar.MainActivity;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    //    private final View mCalloutBalloon;
//    private LayoutInflater mInflater;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView address;
    private TextView csNm;
    private TextView cpTp;
    private TextView chargeTp;
    private TextView spStat;
    private TextView statUpdateDatetime;
    private MapView mapView;
    private Context context;
    private TextView cpId;
    private TextView csId;
    private TextView cpNm;
    private ArrayInfo info = ArrayInfo.getInstance();

    //    private View calloutBalloonLayout; // 커스텀 콜아웃 뷰의 레이아웃
    public CustomCalloutBalloonAdapter(
            BottomSheetBehavior bottomSheetBehavior,
            TextView address, TextView csNm, TextView cpTp, TextView chargeTp, TextView spStat,
            TextView statUpdateDatetime, TextView cpId, TextView csId, TextView cpNm,
            MapView mapView,
            Context context) {
        // 바텀 시트
        this.bottomSheetBehavior = bottomSheetBehavior;
        // 바텀 시트에 TextView
        this.address = address;
        this.csNm = csNm;
        this.cpTp = cpTp;
        this.chargeTp = chargeTp;
        this.spStat = spStat;
        this.statUpdateDatetime = statUpdateDatetime;
        this.cpId = cpId;
        this.csId = csId;
        this.cpNm = cpNm;
        // 메인 화면에 지도
        this.mapView = mapView;
        // 메인 화면에 상태
        this.context = context;
//        // 커스텀 콜아웃 뷰의 레이아웃 초기화
//        LayoutInflater inflater = (LayoutInflater) mapView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        calloutBalloonLayout = inflater.inflate(R.layout.bottom, null);
    }
    //    public CustomCalloutBalloonAdapter(LayoutInflater inflater) {
//        mInflater = inflater;
//        //mCalloutBalloon = mInflater.inflate(R.layout.custom_callout_balloon, null);
//    }

    //    @Override
//    public View getCalloutBalloon(MapPOIItem poiItem) {
//        return null;
//        ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.search_icon);
//        ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
//        ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("Custom CalloutBalloon");
//        return mCalloutBalloon;
//    }

    private void updateUIOnMainThread(Runnable runnable) {
        // 메인 화면 상태 값에 접근
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        }
    }

    @Override
    public View getCalloutBalloon(MapPOIItem mapPOIItem) {
        return null;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        Log.e("jhuijio", "getPressedCalloutBalloon");
        // 마커 클릭 시 바텀시트의 정보 변경
        if (poiItem.getTag() == 0) {
            address.setText("ex) 주소 (현재 위치 정보)");
            csNm.setText("ex) 충전소 명칭 (마커 0)");
            cpTp.setText("ex) 충전 방식");
            chargeTp.setText("ex) 급속 충전/완속 충전");
            spStat.setText("ex) 충전기 상태 코드");
            statUpdateDatetime.setText("ex) 충전기 상태 갱신 시각");
        } else {
            final int index = poiItem.getTag() - 1;
            updateUIOnMainThread(new Runnable() {
                @Override
                public void run() {
                    address.setText("(충전소 주소)\n" + info.getAddr().get(index));
                    csNm.setText("(충전소 명칭)\n" + info.getCsNm().get(index));
                    csId.setText("충전소 ID: " + info.getCsId().get(index));
                    cpNm.setText("충전기 명칭: " + info.getCpNm().get(index));
                    cpId.setText("충전기 ID: " + info.getCpId().get(index));
                    cpTp.setText("충전 방식: " + cpTp(info.getCpTp().get(index)));
                    chargeTp.setText("충전기 타입: " + chargeTp(info.getChargeTp().get(index)));
                    spStat.setText("충전기 상태: " + spStat(info.getSpStat().get(index)));
                    statUpdateDatetime.setText("충전기 상태 갱신 시각:\n" +info.getStatUpdateDatetime().get(index));
                }
            });
        }

        // 바텀 시트 상태 변경
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // 마커 선택 해제
        mapView.deselectPOIItem(poiItem);

        if (Save.getInstance().getRed() != null &&
                poiItem.getMarkerType().equals(MapPOIItem.MarkerType.RedPin)){
            redMarker(poiItem); // 필터에 걸러진 마커
        } else if (Save.getInstance().getBlue() != null &&
        poiItem.getMarkerType().equals(MapPOIItem.MarkerType.BluePin)) {
            blueMarker(poiItem);
        } else {
            createMarker(poiItem); // 마커 선택 후 새로운 마커 추가
        }

        // 커스텀 콜아웃 뷰의 레이아웃 반환
        return null;
    }

    private void createMarker(MapPOIItem poiItem) {
        ArrayInfo arrayInfo = ArrayInfo.getInstance();
        MapPoint markerPoint = MapPoint.mapPointWithGeoCoord(
                Double.parseDouble(arrayInfo.getLat().get(poiItem.getTag() - 1)),
                Double.parseDouble(arrayInfo.getLongi().get(poiItem.getTag() - 1)));
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(arrayInfo.getCsNm().get(poiItem.getTag() - 1) + " " + poiItem.getTag() + "번");
        marker.setTag(poiItem.getTag());
        marker.setMapPoint(markerPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setShowCalloutBalloonOnTouch(true);
        // 새로운 마커를 지도에 추가
        mapView.addPOIItem(marker);
        // 새로운 마커가 보이도록 지도 중심 및 줌 레벨 설정
        mapView.setMapCenterPoint(markerPoint, true);
        Marker_Info.getInstance().setMarkers(marker); // 마커 리스트 추가 (2)
    }

    private void redMarker(MapPOIItem poiItem) {
        ArrayInfo arrayInfo = ArrayInfo.getInstance();
        MapPoint markerPoint = MapPoint.mapPointWithGeoCoord(
                Double.parseDouble(arrayInfo.getLat().get(poiItem.getTag() - 1)),
                Double.parseDouble(arrayInfo.getLongi().get(poiItem.getTag() - 1)));
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(arrayInfo.getCsNm().get(poiItem.getTag() - 1) + " " + poiItem.getTag() + "번");
        marker.setTag(poiItem.getTag());
        marker.setMapPoint(markerPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setShowCalloutBalloonOnTouch(true);
        // 새로운 마커를 지도에 추가
        mapView.addPOIItem(marker);
        // 새로운 마커가 보이도록 지도 중심 및 줌 레벨 설정
        mapView.setMapCenterPoint(markerPoint, true);
        Marker_Info.getInstance().setMarkers(marker); // 마커 리스트 추가 (2)
    }

    private void blueMarker(MapPOIItem poiItem) {
        ArrayInfo arrayInfo = ArrayInfo.getInstance();
        MapPoint markerPoint = MapPoint.mapPointWithGeoCoord(
                Double.parseDouble(arrayInfo.getLat().get(poiItem.getTag() - 1)),
                Double.parseDouble(arrayInfo.getLongi().get(poiItem.getTag() - 1)));
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(arrayInfo.getCsNm().get(poiItem.getTag() - 1) + " " + poiItem.getTag() + "번");
        marker.setTag(poiItem.getTag());
        marker.setMapPoint(markerPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setShowCalloutBalloonOnTouch(true);
        // 새로운 마커를 지도에 추가
        mapView.addPOIItem(marker);
        // 새로운 마커가 보이도록 지도 중심 및 줌 레벨 설정
        mapView.setMapCenterPoint(markerPoint, true);
        Marker_Info.getInstance().setMarkers(marker); // 마커 리스트 추가 (2)
    }

    public CharSequence cpTp(String i) {
        switch (i) {
            // 해당 충전 방식 저장
            case "1":
                return "B타입(5핀)";
            case "2":
                return "C타입(5핀)";
            case "3":
                return "BC타입(5핀)";
            case "4":
                return "BC타입(7핀)";
            case "5":
                return "DC차데모";
            case "6":
                return "AC3상";
            case "7":
                return "DC콤보";
            case "8":
                return "DC차데모 +DC콤보";
            case "9":
                return "DC차데모 +AC3상";
            case "10":
                return "DC차데모 +DC콤보 +AC3상";
            default:
                return "";
        }
    }

    public CharSequence chargeTp(String i) {
        switch (i) {
            // 해당 충전기 타입 저장
            case "1":
                return "완속";
            case "2":
                return "급속";
            default:
                return "";
        }
    }

    public CharSequence spStat(String i) {
        switch (i) {
            // 해당 충전기 상태 저장
            case "0":
                return "상태확인불가";
            case "1":
                return "충전가능";
            case "2":
                return "충전중";
            case "3":
                return "고장/점검";
            case "4":
                return "통신장애";
            case "9":
                return "충전예약";
            default:
                return "";
        }
    }

}



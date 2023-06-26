package com.lkw.searchbar.login.bottomsheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.Map;

public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter{
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

    private boolean shouldCreateMarker = true;
    private Map<Integer, String> info;

    public CustomCalloutBalloonAdapter(
        BottomSheetBehavior bottomSheetBehavior,
        TextView address, TextView csNm, TextView cpTp, TextView chargeTp, TextView spStat,
        TextView statUpdateDatetime, TextView cpId, TextView csId, TextView cpNm,
        MapView mapView,
        Context context,Map<Integer, String> info
    ) {
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
        if (info != null) {
            this.info = info;
        } else {
            this.info = new HashMap<>();
        }
    }

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


    @SuppressLint("DefaultLocale")
    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        Log.e("jhuijio", "getPressedCalloutBalloon");
        // 마커 클릭 시 바텀시트의 정보 변경
        this.info = ArrayInfoManager.getArrayInfo();
        final int index = poiItem.getTag();
        if (!info.containsKey(index)) {
            Log.e("CustomCalloutBalloonAdapter", "info does not contain key: " + index);
            return null;
        }
        updateUIOnMainThread(new Runnable() {
            @Override
            public void run() {
                String infoString = info.get(index);
                if (infoString != null) {
                    String[] infoArray = infoString.split(",");
                    address.setText("(충전소 주소)\n" + infoArray[0]);
                    csNm.setText("(충전기 명칭)\n" + infoArray[9]);
                    csId.setText("충전소 ID: " + infoArray[8]);
                    cpNm.setText("충전기 명칭: " + infoArray[3]);
                    cpId.setText("충전기 ID: " + infoArray[5]);
                    cpTp.setText("충전 방식: " + cpTp(infoArray[7]));
                    chargeTp.setText("충전기 타입: " + chargeTp(infoArray[4]));
                    spStat.setText("충전기 상태: " + spStat(infoArray[6]));
                    statUpdateDatetime.setText("충전기 상태 갱신 시각:\n" + infoArray[10]);
                }
            }
        });

        // 바텀 시트 상태 변경
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // 마커 선택 해제
        mapView.deselectPOIItem(poiItem);

        // 마커 다시 생성
        if (!shouldCreateMarker) {
            Log.e("CustomCalloutBalloonAdapter", "shouldCreateMarkerNull");
            return null;
        }else{
            createMarker(poiItem);
        }

        // 커스텀 콜아웃 뷰의 레이아웃 반환
        return null;
    }

private MapPOIItem createMarker(MapPOIItem poiItem) {
    int tag = poiItem.getTag(); // 태그 받아옴
    String infoString = info.get(tag);

    String[] infoArray = null;
    if (infoString != null) {
        infoArray = infoString.split(",");
    } else {
        // infoString이 null인 경우에 대한 오류 처리
        // 예를 들어, 로그를 출력하거나 기본값으로 대체할 수 있습니다.
        Log.e("CustomCalloutBalloonAdapter", "infoString is null for tag: " + tag);
        infoArray = new String[0]; // 빈 배열로 초기화하거나 적절한 기본값으로 대체합니다.
    }

    double latitude = 0.0;
    double longitude = 0.0;

    if (infoArray.length > 1) {
        latitude = Double.parseDouble(infoArray[1]);
    }
    if (infoArray.length > 2) {
        longitude = Double.parseDouble(infoArray[2]);
    }

    String csName = "";
    if (infoArray.length > 9) {
        csName = infoArray[9];
    }

    String markerName = csName + " " + poiItem.getTag() + "번";

    MapPoint markerPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);

    MapPOIItem marker = new MapPOIItem();
    marker.setItemName(markerName);
    marker.setTag(poiItem.getTag());
    marker.setMapPoint(markerPoint);
    marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
    marker.setShowCalloutBalloonOnTouch(true);
    // 새로운 마커를 지도에 추가
    mapView.addPOIItem(marker);
    // 새로운 마커가 보이도록 지도 중심 및 줌 레벨 설정
    mapView.setMapCenterPoint(markerPoint, true);

    return marker;
}


    public CharSequence cpTp(String i) {
        if (i == null) {
            return ""; // 또는 원하는 기본값을 반환할 수 있습니다.
        }

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
                return "DC차데모 + DC콤보";
            case "9":
                return "DC차데모 + AC3상";
            case "10":
                return "DC차데모 + DC콤보 + AC3상";
            default:
                return "";
        }
    }


    public CharSequence chargeTp(String i) {
        if (i == null) {
            return ""; // 또는 원하는 기본값을 반환할 수 있습니다.
        }

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
        if (i == null) {
            return ""; // 또는 원하는 기본값을 반환할 수 있습니다.
        }
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
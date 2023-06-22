package com.lkw.searchbar.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.lkw.searchbar.R;
import com.lkw.searchbar.login.BottomSheet.CustomCalloutBalloonAdapter;
import com.lkw.searchbar.login.GettetSetter.ArrayInfo;
import com.lkw.searchbar.login.GettetSetter.Marker_Info;
import com.lkw.searchbar.login.GettetSetter.Save;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, MapView.CurrentLocationEventListener, MapView.MapViewEventListener {
    private PopupWindow popupWindow;
    private boolean isCheckboxVisible = false;
    private boolean isUsingTrueChecked = false;
    private boolean isUsingFalseChecked = false;
    private boolean isSpeedFast = false;
    private boolean isSpeedFull = false;
    private boolean ischargeTypeB5 = false;
    private boolean ischargeTypeC5 = false;
    private boolean ischargeTypeBC5 = false;
    private boolean ischargeTypeBC7 = false;
    private boolean ischargeTypeDc = false;
    private boolean ischargeTypeAc = false;
    private boolean ischargeTypecomb = false;
    private TextView textView;
    private CheckBox checkBoxPos;
    private CheckBox checkBoxImps;
    private CheckBox speedBoxPos;
    private CheckBox speedBoxImps;
    private CheckBox chargeTypeB5;
    private CheckBox chargeTypeC5;
    private CheckBox chargeTypeBC5;
    private CheckBox chargeTypeBC7;
    private CheckBox chargeTypeDc;
    private CheckBox chargeTypeAc;
    private CheckBox chargeTypecomb;
    public static MapView mapView;
    private ViewGroup mapViewContainer;
    // ------------------------
    public Activity activity;
    private LinearLayout bottomSheet;
    private View bottomSheetView;
    private TextView address;
    private TextView csNm;
    private TextView cpTp;
    private TextView chargeTp;
    private TextView spStat;
    private TextView statUpdateDatetime;
    private TextView cpId;
    private TextView csId;
    private TextView cpNm;
    private MapPOIItem selectedMarker; // 선택된 마커를 저장하기 위한 변수
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView menu;
    //------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Hash 코드 출력
        HashUtils.printHashKey(getApplicationContext());

        menu = findViewById(R.id.user_manual);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder menu = new AlertDialog.Builder(
                        MainActivity.this, R.style.RoundedAlertDialogStyle);
                menu.setTitle("PlugHub 충전소 앱");

                // 내용을 담을 LinearLayout 생성
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // 내용 뷰 (TextView, 이미지 등)를 LinearLayout에 추가
                TextView view = new TextView(MainActivity.this);
                view.setText("\n     PlugHub 앱은 전기차 충전소를 조회하고\n" +
                        "     지도에서 확인할 수 있습니다.");

                TextView main1 = new TextView(MainActivity.this);
                main1.setText("\n     메인 화면 기능 ↓↓↓↓↓");
                main1.setTextSize(13);
                main1.setTypeface(main1.getTypeface(), Typeface.BOLD);
                TextView main2 = new TextView(MainActivity.this);
                main2.setText(
                        "     - 검색창 이동 바" +
                                "\n      (검색 화면으로 이동)" +
                                "\n     - 마커 필터" +
                                "\n      (지도에 표시된 마커를 선별 &\n" +
                                "       각각의 필터의 체크 박스 일괄 해제)\n\n" +
                                "     ★ 필터 1가지 선택 : 빨간색\n\n" +
                                "     ★ 필터 2가지 선택 : 파란색\n" +
                                "     ☆ (이전 마커 덮어 씌우기)\n" +
                                "\n     ★ 필터 3가지 선택 : 파란색" +
                                "\n     ☆ (필터 2가지 선택과 동일한 방식)\n");
                main2.setTextSize(11);
                TextView main3 = new TextView(MainActivity.this);
                main3.setText("     ※ 필터가 정상 작동을 하지 않을 경우 \n" +
                        "         체크박스를 해제했다가 다시 클릭해보세요.\n");
                main3.setTextSize(11);
                main3.setTypeface(main1.getTypeface(), Typeface.BOLD);
                TextView main4 = new TextView(MainActivity.this);
                main4.setText("     - 도움말" +
                        "\n      (PlugHub 사용법)");
                main4.setTextSize(11);

                TextView sub1 = new TextView(MainActivity.this);
                sub1.setText("\n     검색 화면 기능 ↓↓↓↓↓");
                sub1.setTextSize(13);
                sub1.setTypeface(sub1.getTypeface(), Typeface.BOLD);
                TextView sub2 = new TextView(MainActivity.this);
                sub2.setText(
                        "     - 검색 바" +
                                "\n      (검색어로 전기차 충전소 조회)" +
                                "\n     - 검색 버튼" +
                                "\n      (버튼 클릭 시 조회)" +
                                "\n     - 검색 량 확인란" +
                                "\n      (전기차 충전소 개수)" +
                                "\n     - 검색 결과 확인란" +
                                "\n      (전기차 충전소 조회 결과)");
                sub2.setTextSize(11);
                TextView danger = new TextView(MainActivity.this);
                danger.setText("\n     ※ 사용자가 지정한 필터의 조합이 없는 경우\n" +
                        "         마커가 나타나지 않거나 변화가 없을 수 있습니다.\n");
                danger.setTextSize(11);
                danger.setTypeface(sub1.getTypeface(), Typeface.BOLD);

                // LinearLayout에 요소 추가
                layout.addView(view);
                layout.addView(main1);
                layout.addView(main2);
                layout.addView(main3);
                layout.addView(main4);
                layout.addView(sub1);
                layout.addView(sub2);
                layout.addView(danger);

                menu.setView(layout);

                // 스크롤뷰 추가
                ScrollView scrollView = new ScrollView(MainActivity.this);
                scrollView.addView(layout);
                menu.setView(scrollView);

                menu.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 다이얼로그 닫을 때만 사용
                    }
                });
                // 다이얼로그 생성
                AlertDialog dialog = menu.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        TextView closeButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        if (closeButton != null) {
                            closeButton.setTextSize(12);
                            closeButton.setTextColor(Color.BLACK);  // 텍스트 색상 변경
                            closeButton.setBackgroundColor(getResources().getColor(R.color.white));
                            closeButton.setBackgroundResource(android.R.drawable.alert_dark_frame);  // 버튼 스타일 변경
                        }
                    }
                });
                // 다이얼로그 표시
                dialog.show();

                // 다이얼로그 크기 제한
//                int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
                int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.8);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                if (window != null) {
                    layoutParams.copyFrom(window.getAttributes());
//                    layoutParams.width = dialogWidth;
                    layoutParams.height = dialogHeight;
                    window.setAttributes(layoutParams);
                }
            }
        });

        findViewById(R.id.more_btn).setOnClickListener(this);

        textView = findViewById(R.id.include);

//        // 권한ID를 가져옵니다
//        int permission = ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.INTERNET);
//
//        int permission2 = ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION);
//
//        int permission3 = ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        // 권한이 열려있는지 확인
//        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
//            // 마쉬멜로우 이상버전부터 권한을 물어본다
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
//                requestPermissions(
//                        new String[]{android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
//                        1000);
//            }
//            return;
//        }

        //지도를 띄우자
        // java code
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

        // Kakao SDK 초기화
//        KakaoSdk.init(this, "{058aba7c75990149427e6a9956137af0}");
//        mapView = new MapView(this);
//        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
//        mapView.setMapViewEventListener(this);
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        Chip using = findViewById(R.id.using_btn);
        using.setOnClickListener(this);

        Chip using1 = findViewById(R.id.type_btn);
        using1.setOnClickListener(this);

        Chip using2 = findViewById(R.id.speed_btn);
        using2.setOnClickListener(this);

        // ------------------------
        textView.setOnClickListener(this);
        textView.setOnFocusChangeListener(this);
        activity = this;

        // -------------------------- 바텀 시트 초기화 -------------------------------
        bottomSheetView = findViewById(R.id.viewBottomSheet); // 바텀 시트의 레이아웃
        // 바텀 시트에서 텍스트를 설정할 TextView
        address = bottomSheetView.findViewById(R.id.address); // 주소 값 초기화
        csNm = bottomSheetView.findViewById(R.id.address_road);
        cpTp = bottomSheetView.findViewById(R.id.cpTp);
        chargeTp = bottomSheetView.findViewById(R.id.charge_val);
        spStat = bottomSheetView.findViewById(R.id.spStat);
        statUpdateDatetime = bottomSheetView.findViewById(R.id.Datetime);
        csId = bottomSheetView.findViewById(R.id.csi);
        cpId = bottomSheetView.findViewById(R.id.cpi);
        cpNm = bottomSheetView.findViewById(R.id.cpn);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView); // 바텀 시트 초기 상태
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter(
                bottomSheetBehavior,
                address, csNm, cpTp, chargeTp, spStat, statUpdateDatetime,
                csId, cpId, cpNm,
                mapView, MainActivity.this));

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // 바텀 시트 슬라이드 동작 감지
            }
        });

        // 마커 이벤트 리스너 설정
        mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
            @Override
            public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
                if (selectedMarker != mapPOIItem) {
                    selectedMarker = mapPOIItem; // 선택된 마커 저장
//                    updateBottomSheet(); // 바텀 시트 업데이트
                }
            }

            @Override
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
            }

            @Override
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
            }

            @Override
            public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
            }
        });

        // 바텀시트가 최소 크기일때 누르면 최대 크기로 확장하기
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        // ------------------------
    }

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == false) {
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        switch (id) {
            case R.id.more_btn:
                showCheckBox();
                break;
            case R.id.include:
                startDestination();
                break;
            case R.id.using_btn: // 이용시간
                viewOnClickUsing(v);
                break;
            case R.id.type_btn: //  충전 타입
                viewOnClickType(v);
                break;
            case R.id.speed_btn: // 충전 속도
                viewOnClickSpeed(v);
                break;
            default:
                hidecheckbox();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        startDestination();
    }

    void startDestination() {
        Intent intent = new Intent(this, DestinationActivity.class);
        startActivity(intent);
    }

    private void showCheckBox() {
        Chip speedBtn = findViewById(R.id.speed_btn);
        Chip typeBtn = findViewById(R.id.type_btn);
        Chip usingBtn = findViewById(R.id.using_btn);

        isCheckboxVisible = !isCheckboxVisible;
        speedBtn.setVisibility(isCheckboxVisible ? View.VISIBLE : View.GONE);
        typeBtn.setVisibility(isCheckboxVisible ? View.VISIBLE : View.GONE);
        usingBtn.setVisibility(isCheckboxVisible ? View.VISIBLE : View.GONE);

    }

    private void hidecheckbox() {
        if (isCheckboxVisible) {
            showCheckBox();
        }
    }
// ----------------------------- 이용가능 필터 로직----------------------------------
    private void viewOnClickUsing(View v) {
        ArrayList<String> list = ArrayInfo.getInstance().getSpStat();
        ArrayList<String> type = ArrayInfo.getInstance().getCpTp();
        ArrayList<String> speed = ArrayInfo.getInstance().getChargeTp();
        ArrayList<MapPOIItem> markerList = Marker_Info.getInstance().getMarkerList();
        ArrayList<MapPOIItem> strings = new ArrayList<>();
        ArrayList<String> info = new ArrayList<>();
        Chip chip = (Chip) v;

        if (type != null) {
            for (String li : type) {
                if (li != null) {
                    switch (li) {
                        // 해당 충전 방식 저장
                        case "1":
                            info.add("1"); // B타입(5핀)
                            break;
                        case "2":
                            info.add("2"); // C타입(5핀)
                            break;
                        case "3":
                            info.add("BC타입(5핀)");
                            break;
                        case "4":
                            info.add("BC타입(7핀)");
                            break;
                        case "5":
                            info.add("DC차데모");
                            break;
                        case "6":
                            info.add("AC3상");
                            break;
                        case "7":
                            info.add("DC콤보");
                            break;
                        case "8":
                            info.add("DC차데모 +DC콤보");
                            break;
                        case "9":
                            info.add("DC차데모 +AC3상");
                            break;
                        case "10":
                            info.add("DC차데모 +DC콤보 +AC3상");
                            break;
                    }
                }
            }
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.using_checkmenu, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        checkBoxPos = popupView.findViewById(R.id.usingtrue);
        checkBoxImps = popupView.findViewById(R.id.usingfalse);

        // 체크박스 상태 설정
        checkBoxPos.setChecked(isUsingTrueChecked);
        checkBoxImps.setChecked(isUsingFalseChecked);

        // 체크박스 클릭 리스너 설정
        checkBoxPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUsingTrueChecked = checkBoxPos.isChecked();
                if (isUsingTrueChecked && ischargeTypeB5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && tp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && ischargeTypeC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && tp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && ischargeTypeBC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && tp.contains("BC타입(5핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && ischargeTypeBC7) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && tp.contains("BC타입(7핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && ischargeTypeDc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && tp.contains("DC차데모")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && ischargeTypeAc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && tp.contains("AC3상")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && ischargeTypecomb) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String sp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && sp.contains("DC콤보")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("1") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingTrueChecked) {            // 옵션 하나 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "사용가능(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            String infoValue = list.get(j);
                            if (j < markerList.size()) {
                                MapPOIItem marker = markerList.get(j);
                                if (infoValue.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                    mapView.addPOIItem(marker);
//                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setRed(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else {
                    if (checkBoxImps.isChecked()) {
                        // 이용가능 일괄 해제
                        checkBoxImps.setChecked(false);
                        isUsingFalseChecked = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                    // 필터에 걸러진 마커의 정보를 삭제
                    strings.clear();
                }
                updateUsingChip(chip);
            }
        });

        checkBoxImps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUsingFalseChecked = checkBoxImps.isChecked();
                if (isUsingFalseChecked && ischargeTypeB5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && tp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && ischargeTypeC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && tp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && ischargeTypeBC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && tp.contains("BC타입(5핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && ischargeTypeBC7) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && tp.contains("BC타입(7핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && ischargeTypeDc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && tp.contains("DC차데모")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && ischargeTypeAc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String tp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && tp.contains("AC3상")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && ischargeTypecomb) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String sp = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && sp.contains("DC콤보")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String able = list.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (able.contains("2") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setBlue(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else if (isUsingFalseChecked) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "사용불가(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            String infoValue = list.get(j);
                            if (j < markerList.size()) {
                                MapPOIItem marker = markerList.get(j);
                                if (!infoValue.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                    mapView.addPOIItem(marker);
                                    // 필터에 걸러진 마커 정보
                                    strings.add(marker);
                                    Save.getInstance().setRed(strings); // 마커 클릭 임시 방편
                                }
                            }
                        }
                    }
                } else {
                    if (checkBoxPos.isChecked()) {
                        // 이용가능 일괄 해제
                        checkBoxPos.setChecked(false);
                        isUsingTrueChecked = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
//                     필터에 걸러진 마커의 정보를 삭제
                    strings.clear();
                }
                updateUsingChip(chip);
            }
        });
        // 초기 상태 설정
        updateUsingChip(chip);
        popupWindow.showAsDropDown(v);
    }

    // Chip 뷰의 상태를 업데이트하는 메서드
    private void updateUsingChip(@NonNull Chip chip) {
        boolean isAnyChecked = isUsingTrueChecked || isUsingFalseChecked;
        chip.setChecked(isAnyChecked);
    }
    // ----------------------------- 충전타입 필터 로직----------------------------------
    private void viewOnClickType(View v) {
        ArrayList<String> able = ArrayInfo.getInstance().getSpStat();
        ArrayList<String> list = ArrayInfo.getInstance().getCpTp();
        ArrayList<String> speed = ArrayInfo.getInstance().getChargeTp();
        ArrayList<String> info = new ArrayList<>();
        ArrayList<MapPOIItem> markerList = Marker_Info.getInstance().getMarkerList();
        Chip chip = (Chip) v;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.using_typemenu, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        chargeTypeB5 = popupView.findViewById(R.id.speed_b5);
        chargeTypeC5 = popupView.findViewById(R.id.speed_c5);
        chargeTypeBC5 = popupView.findViewById(R.id.speed_bc5);
        chargeTypeBC7 = popupView.findViewById(R.id.speed_bc7);
        chargeTypeDc = popupView.findViewById(R.id.speed_dc);
        chargeTypeAc = popupView.findViewById(R.id.speed_ac);
        chargeTypecomb = popupView.findViewById(R.id.speed_dccomb);

        chargeTypeB5.setChecked(ischargeTypeB5);
        chargeTypeC5.setChecked(ischargeTypeC5);
        chargeTypeBC5.setChecked(ischargeTypeBC5);
        chargeTypeBC7.setChecked(ischargeTypeBC7);
        chargeTypeDc.setChecked(ischargeTypeDc);
        chargeTypeAc.setChecked(ischargeTypeAc);
        chargeTypecomb.setChecked(ischargeTypecomb);

        if (list != null) {
            for (String li : list) {
                if (li != null) {
                    switch (li) {
                        // 해당 충전 방식 저장
                        case "1":
                            info.add("1"); // B타입(5핀)
                            break;
                        case "2":
                            info.add("2"); // C타입(5핀)
                            break;
                        case "3":
                            info.add("BC타입(5핀)");
                            break;
                        case "4":
                            info.add("BC타입(7핀)");
                            break;
                        case "5":
                            info.add("DC차데모");
                            break;
                        case "6":
                            info.add("AC3상");
                            break;
                        case "7":
                            info.add("DC콤보");
                            break;
                        case "8":
                            info.add("DC차데모 +DC콤보");
                            break;
                        case "9":
                            info.add("DC차데모 +AC3상");
                            break;
                        case "10":
                            info.add("DC차데모 +DC콤보 +AC3상");
                            break;
                    }
                }
            }
        }

        chargeTypeB5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypeB5 = chargeTypeB5.isChecked();
                if (ischargeTypeB5 && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("1") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeB5 && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("1") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeB5 && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("1") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeB5 && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("1") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeB5) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "DC차데모(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("1")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeC5.isChecked() ||
                            chargeTypeBC5.isChecked() ||
                            chargeTypeBC7.isChecked() ||
                            chargeTypeDc.isChecked() ||
                            chargeTypeAc.isChecked() ||
                            chargeTypecomb.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeC5.setChecked(false);
                        ischargeTypeC5 = false;
                        chargeTypeBC5.setChecked(false);
                        ischargeTypeBC5 = false;
                        chargeTypeBC7.setChecked(false);
                        ischargeTypeBC7 = false;
                        chargeTypeDc.setChecked(false);
                        ischargeTypeDc = false;
                        chargeTypeAc.setChecked(false);
                        ischargeTypeAc = false;
                        chargeTypecomb.setChecked(false);
                        ischargeTypecomb = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });

        chargeTypeC5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypeC5 = chargeTypeC5.isChecked();
                if (ischargeTypeC5 && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("2") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeC5 && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("2") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeC5 && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("2") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeC5 && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("2") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeC5) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "DC차데모(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("2")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeB5.isChecked() ||
                            chargeTypeBC5.isChecked() ||
                            chargeTypeBC7.isChecked() ||
                            chargeTypeDc.isChecked() ||
                            chargeTypeAc.isChecked() ||
                            chargeTypecomb.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeB5.setChecked(false);
                        ischargeTypeB5 = false;
                        chargeTypeBC5.setChecked(false);
                        ischargeTypeBC5 = false;
                        chargeTypeBC7.setChecked(false);
                        ischargeTypeBC7 = false;
                        chargeTypeDc.setChecked(false);
                        ischargeTypeDc = false;
                        chargeTypeAc.setChecked(false);
                        ischargeTypeAc = false;
                        chargeTypecomb.setChecked(false);
                        ischargeTypecomb = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });

        chargeTypeBC5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypeBC5 = chargeTypeBC5.isChecked();
                if (ischargeTypeBC5 && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(5핀)") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC5 && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(5핀)") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC5 && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(5핀)") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC5 && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(5핀)") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC5) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "DC차데모(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("BC타입(5핀)")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeB5.isChecked() ||
                            chargeTypeC5.isChecked() ||
                            chargeTypeBC7.isChecked() ||
                            chargeTypeDc.isChecked() ||
                            chargeTypeAc.isChecked() ||
                            chargeTypecomb.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeB5.setChecked(false);
                        ischargeTypeB5 = false;
                        chargeTypeC5.setChecked(false);
                        ischargeTypeC5 = false;
                        chargeTypeBC7.setChecked(false);
                        ischargeTypeBC7 = false;
                        chargeTypeDc.setChecked(false);
                        ischargeTypeDc = false;
                        chargeTypeAc.setChecked(false);
                        ischargeTypeAc = false;
                        chargeTypecomb.setChecked(false);
                        ischargeTypecomb = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });

        chargeTypeBC7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypeBC7 = chargeTypeBC7.isChecked();
                if (ischargeTypeBC7 && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(7핀)") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC7 && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(7핀)") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC7 && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(7핀)") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC7 && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("BC타입(7핀)") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeBC7) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "DC차데모(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("BC타입(7핀)")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeB5.isChecked() ||
                            chargeTypeC5.isChecked() ||
                            chargeTypeBC5.isChecked() ||
                            chargeTypeDc.isChecked() ||
                            chargeTypeAc.isChecked() ||
                            chargeTypecomb.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeB5.setChecked(false);
                        ischargeTypeB5 = false;
                        chargeTypeC5.setChecked(false);
                        ischargeTypeC5 = false;
                        chargeTypeBC5.setChecked(false);
                        ischargeTypeBC5 = false;
                        chargeTypeDc.setChecked(false);
                        ischargeTypeDc = false;
                        chargeTypeAc.setChecked(false);
                        ischargeTypeAc = false;
                        chargeTypecomb.setChecked(false);
                        ischargeTypecomb = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });

        chargeTypeDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypeDc = chargeTypeDc.isChecked();
                if (ischargeTypeDc && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC차데모") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeDc && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC차데모") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeDc && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC차데모") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeDc && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC차데모") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeDc) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "DC차데모(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("DC차데모")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeB5.isChecked() ||
                            chargeTypeC5.isChecked() ||
                            chargeTypeBC5.isChecked() ||
                            chargeTypeBC7.isChecked() ||
                            chargeTypeAc.isChecked() ||
                            chargeTypecomb.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeB5.setChecked(false);
                        ischargeTypeB5 = false;
                        chargeTypeC5.setChecked(false);
                        ischargeTypeC5 = false;
                        chargeTypeBC5.setChecked(false);
                        ischargeTypeBC5 = false;
                        chargeTypeBC7.setChecked(false);
                        ischargeTypeBC7 = false;
                        chargeTypeAc.setChecked(false);
                        ischargeTypeAc = false;
                        chargeTypecomb.setChecked(false);
                        ischargeTypecomb = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });

        chargeTypeAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypeAc = chargeTypeAc.isChecked();
                if (ischargeTypeAc && isSpeedFast) {
                    if (list != null & speed != null) {            // 옵션 두개 적용 상태
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("AC3상") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeAc && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("AC3상") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeAc && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("AC3상") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeAc && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("AC3상") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypeAc) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "AC3상(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("AC3상")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeB5.isChecked() ||
                            chargeTypeC5.isChecked() ||
                            chargeTypeBC5.isChecked() ||
                            chargeTypeBC7.isChecked() ||
                            chargeTypeDc.isChecked() ||
                            chargeTypecomb.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeB5.setChecked(false);
                        ischargeTypeB5 = false;
                        chargeTypeC5.setChecked(false);
                        ischargeTypeC5 = false;
                        chargeTypeBC5.setChecked(false);
                        ischargeTypeBC5 = false;
                        chargeTypeBC7.setChecked(false);
                        ischargeTypeBC7 = false;
                        chargeTypeDc.setChecked(false);
                        ischargeTypeDc = false;
                        chargeTypecomb.setChecked(false);
                        ischargeTypecomb = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });

        chargeTypecomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischargeTypecomb = chargeTypecomb.isChecked();
                if (ischargeTypecomb && isSpeedFull) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC콤보") && sp.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypecomb && isSpeedFast) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & speed != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String sp = speed.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC콤보") && sp.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypecomb && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC콤보") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypecomb && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String ch = info.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (ch.contains("DC콤보") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (ischargeTypecomb) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "DC콤보(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < info.size(); j++) {
                        String infoValue = info.get(j);
                        if (j < markerList.size()) {
                            MapPOIItem marker = markerList.get(j);
                            if (infoValue.contains("DC콤보")) {
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapView.addPOIItem(marker);
                            }
                        }
                    }
                } else {
                    if (chargeTypeB5.isChecked() ||
                            chargeTypeC5.isChecked() ||
                            chargeTypeBC5.isChecked() ||
                            chargeTypeBC7.isChecked() ||
                            chargeTypeDc.isChecked() ||
                            chargeTypeAc.isChecked()) {
                        // 충전타입 일괄 해제
                        chargeTypeB5.setChecked(false);
                        ischargeTypeB5 = false;
                        chargeTypeC5.setChecked(false);
                        ischargeTypeC5 = false;
                        chargeTypeBC5.setChecked(false);
                        ischargeTypeBC5 = false;
                        chargeTypeBC7.setChecked(false);
                        ischargeTypeBC7 = false;
                        chargeTypeDc.setChecked(false);
                        ischargeTypeDc = false;
                        chargeTypeAc.setChecked(false);
                        ischargeTypeAc = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateTypeChip(chip);
            }
        });
        updateTypeChip(chip);
        popupWindow.showAsDropDown(v);
    }

    private void updateTypeChip(@NonNull Chip chip) {
        boolean isAnyChecked = chargeTypeB5.isChecked()
                || chargeTypeC5.isChecked()
                || chargeTypeBC5.isChecked()
                || chargeTypeBC7.isChecked()
                || chargeTypeDc.isChecked()
                || chargeTypeAc.isChecked()
                || chargeTypecomb.isChecked();
        chip.setChecked(isAnyChecked);
    }
    // ----------------------------- 충전속도 필터 로직----------------------------------
    private void viewOnClickSpeed(View v) {
        ArrayList<String> able = ArrayInfo.getInstance().getSpStat();
        ArrayList<String> type = ArrayInfo.getInstance().getCpTp();
        ArrayList<String> list = ArrayInfo.getInstance().getChargeTp();
        ArrayList<String> info = new ArrayList<>();
        ArrayList<MapPOIItem> markerList = Marker_Info.getInstance().getMarkerList();
        Chip chip = (Chip) v;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.using_speed, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        speedBoxPos = popupView.findViewById(R.id.char_full);
        speedBoxImps = popupView.findViewById(R.id.char_speed);

        speedBoxPos.setChecked(isSpeedFast);
        speedBoxImps.setChecked(isSpeedFull);

        if (type != null) {
            for (String li : type) {
                if (li != null) {
                    switch (li) {
                        // 해당 충전 방식 저장
                        case "1":
                            info.add("1"); // B타입(5핀)
                            break;
                        case "2":
                            info.add("2"); // C타입(5핀)
                            break;
                        case "3":
                            info.add("BC타입(5핀)");
                            break;
                        case "4":
                            info.add("BC타입(7핀)");
                            break;
                        case "5":
                            info.add("DC차데모");
                            break;
                        case "6":
                            info.add("AC3상");
                            break;
                        case "7":
                            info.add("DC콤보");
                            break;
                        case "8":
                            info.add("DC차데모 +DC콤보");
                            break;
                        case "9":
                            info.add("DC차데모 +AC3상");
                            break;
                        case "10":
                            info.add("DC차데모 +DC콤보 +AC3상");
                            break;
                    }
                }
            }
        }

//        if (list != null) {
//            for (String li : list) {
//                if (li != null) {
//                    switch (li) {
//                        // 해당 충전 방식 저장
//                        case "2":
//                            info.add("완속");
//                            break;
//                        case "1":
//                            info.add("급속");
//                            break;
//                    }
//                }
//            }
//        }

        speedBoxPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpeedFast = speedBoxPos.isChecked();
                if (isSpeedFast && ischargeTypeB5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && ischargeTypeC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && ischargeTypeBC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("BC타입(5핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && ischargeTypeBC7) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("BC타입(7핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && ischargeTypeDc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("DC차데모")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && ischargeTypeAc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("AC3상")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && ischargeTypecomb) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ch.contains("DC콤보")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null && able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null && able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("1") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFast) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "완속(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            String infoValue = list.get(j);
                            if (j < markerList.size()) {
                                MapPOIItem marker = markerList.get(j);
                                if (infoValue.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else {
                    if (speedBoxImps.isChecked()) {
                        // 충전속도 일괄 해제
                        speedBoxImps.setChecked(false);
                        isSpeedFull = false;
                    }
                    if (markerList != null) {
                        // 모든 마커를 삭제
                        mapView.removeAllPOIItems();
                        // 체크박스가 해제되었을 때의 동작 추가
                        // 모든 마커를 다시 추가
                        for (MapPOIItem marker : markerList) {
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                            mapView.addPOIItem(marker);
                        }
                    }
                }
                updateSpeedChip(chip);
            }
        });

        speedBoxImps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpeedFull = speedBoxImps.isChecked();
                if (isSpeedFull && ischargeTypeB5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && ischargeTypeC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && ischargeTypeBC5) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("BC타입(5핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && ischargeTypeBC7) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("BC타입(7핀)")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && ischargeTypeDc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("DC차데모")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && ischargeTypeAc) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            System.out.println(sp + " " + ch);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("AC3상")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && ischargeTypecomb) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null & type != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ch = info.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ch.contains("DC콤보")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && isUsingTrueChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null && able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ab.contains("1")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull && isUsingFalseChecked) {            // 옵션 두개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    if (list != null && able != null) {
                        for (int s = 0; s < list.size(); s++) {
                            String sp = list.get(s);
                            String ab = able.get(s);
                            if (s < markerList.size()) {
                                MapPOIItem marker = markerList.get(s);
                                if (sp.contains("2") && ab.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else if (isSpeedFull) {            // 옵션 한개 적용 상태
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 선택되었을 때의 동작 추가
                    // 예: Toast 메시지 출력
//                    Toast.makeText(getApplicationContext(), "급속(이/가) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            String infoValue = list.get(j);
                            if (j < markerList.size()) {
                                MapPOIItem marker = markerList.get(j);
                                if (infoValue.contains("2")) {
                                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }
                    }
                } else {
                    if (speedBoxPos.isChecked()) {
                        // 충전속도 일괄 해제
                        speedBoxPos.setChecked(false);
                        isSpeedFast = false;
                    }
                    // 모든 마커를 삭제
                    mapView.removeAllPOIItems();
                    // 체크박스가 해제되었을 때의 동작 추가
                    // 모든 마커를 다시 추가
                    for (MapPOIItem marker : markerList) {
                        marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                        mapView.addPOIItem(marker);
                    }
                }
                updateSpeedChip(chip);
            }
        });

        updateSpeedChip(chip);
        popupWindow.showAsDropDown(v);
    }

    private void updateSpeedChip(@NonNull Chip chip) {
        boolean isAnyChecked = isSpeedFast || isSpeedFull;
        chip.setChecked(isAnyChecked);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

//        // 권한ID를 가져옵니다
//        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        // 권한이 열려있는지 확인
//        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
//            // 마쉬멜로우 이상버전부터 권한을 물어본다
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
//            }
//            return;
//        }
//
//        // 현재 위치 가져오기
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager != null) {
//            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (lastKnownLocation != null) {
//                double latitude = lastKnownLocation.getLatitude();
//                double longitude = lastKnownLocation.getLongitude();
/*
                // 마커1를 추가할 좌표 생성
                MapPoint markerPoint1 = MapPoint.mapPointWithGeoCoord(latitude, longitude);
                // 마커1 생성 및 설정
                MapPOIItem marker1 = new MapPOIItem();
                marker1.setItemName("현재 위치"); // 마커 이름 설정
                marker1.setTag(0); // 마커 태그 설정
                marker1.setMapPoint(markerPoint1); // 마커 좌표 설정
                marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 아이콘 타입 설정
                marker1.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 선택 시 아이콘 타입 설정
                marker1.setShowCalloutBalloonOnTouch(true);
                // 마커1를 지도에 추가
                mapView.addPOIItem(marker1);
                // 마커1가 보이도록 지도 중심 및 줌 레벨 설정
                mapView.setMapCenterPoint(markerPoint1, true);

 */
//                mapView.setZoomLevel(5, true);
//            }
//        }
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

package com.lkw.searchbar.unlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.kakao.sdk.common.KakaoSdk;
import com.lkw.searchbar.R;
import com.lkw.searchbar.unlogin.bottomsheet.CustomCalloutBalloonAdapter;
import com.lkw.searchbar.unlogin.charge_controller.DatabaseCharge;
import com.lkw.searchbar.unlogin.model.category_search.Document;
import com.lkw.searchbar.unlogin.states.ChargeType;
import com.lkw.searchbar.unlogin.states.SpeedType;
import com.lkw.searchbar.unlogin.states.UsingType;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.Map;

// 컴포넌트, parcelable
public class UnLoginMainActivity extends AppCompatActivity implements
        View.OnClickListener,
        View.OnFocusChangeListener,
        MapView.CurrentLocationEventListener,
        MapView.MapViewEventListener{

    private MapView mapView;
    private int markerCount;

    // DataBase
    private DatabaseCharge databaseCharge;
    private boolean isCheckboxVisible = false;
    private UsingType usingType = UsingType.ENTIRE;
    private SpeedType speedType = SpeedType.ENTIRE;
    private ChargeType chargeType = ChargeType.ENTIRE;
    //ㅡㅡㅡㅡㅡㅡ
    private boolean isTrackingMode = false;
    private boolean isSpeedEntire = false;
    private boolean isSpeedFull = false;
    private boolean isSpeedFast = false;
    private boolean isChargeEntire = false;
    private boolean isChargeBtype = false;
    private boolean isChargeCtype = false;
    private boolean isChargeBCtypeFiv = false;
    private boolean isChargeBCtypeSev = false;
    private boolean isChargeDC = false;
    private boolean isChargeAC = false;
    private boolean isChargeDCCOMB = false;
    private boolean isChargeDC_TYPE_DC_COMB = false;
    private boolean isChargeDC_AC = false;
    private boolean isChargeDC_DC_COMB_AC = false;
    private double mCurrentLng;
    private double mCurrentLat;
    private MapPoint currentMapPoint;
    private double mSearchLng = -1;
    private double mSearchLat = -1;
    private String mSearchName;
    MapPOIItem searchMarker;
    private EditText searchText;
    private Button currentBtn;
    private Chip chargeUsingType;
    private Chip chargeTotalType;
    private Chip chargeSpeedType;
    private PopupWindow popupWindow;
    private RadioButton speedBoxPos;
    private RadioButton speedBoxImps;
    private RadioButton speedBoxEntire;
    private RadioButton entireType;
    private RadioButton chargeTypeDc;
    private RadioButton chargeTypeAc;
    private RadioButton chargeTypecomb;
    private RadioButton chargeTypeBtype;
    private RadioButton chargeTypeCtype;
    private RadioButton chargeTypeBCtypeSev;
    private RadioButton chargeTypeBCtypeFiv;
    private RadioButton chargeTypeDCtypeDCComb;
    private RadioButton chargeTypeDC_AC;
    private RadioButton chargeTypeDC_DCComb_AC;
    private ViewGroup mapViewContainer;

    // ------------ Bottom Sheet ------------
    private LinearLayout bottomSheet;

    private View bottomSheetView;
    //
    private TextView address;
    private TextView csNm;
    private TextView cpTp;
    private TextView cpId;
    private TextView csId;
    private TextView cpNm;
    private TextView chargeTp;
    private TextView spStat;
    private TextView statUpdateDatetime;
    private BottomSheetBehavior bottomSheetBehavior;
    //------------------------
    private Map<Integer, String> info;
    public void setStatusBarTransparent(Activity activity) {
        Window window = activity.getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if (Build.VERSION.SDK_INT >= 30) { // API 30에 적용
            WindowCompat.setDecorFitsSystemWindows(window, false);
        }
    }

    public void onMarkerCountReceived(int markerCount) {
        this.markerCount = markerCount;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent(this);

        setContentView(R.layout.unlogin_search);

        findViewById(R.id.unlogin_more_btn).setOnClickListener(this);

        searchText = findViewById(R.id.unlogin_searchbar);

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

        bottomSheetView = findViewById(R.id.unlogin_viewBottomSheet);

        address = bottomSheetView.findViewById(R.id.address);
        csNm = bottomSheetView.findViewById(R.id.address_road);
        cpTp = bottomSheetView.findViewById(R.id.cpTp);
        chargeTp = bottomSheetView.findViewById(R.id.charge_val);
        spStat = bottomSheetView.findViewById(R.id.spStat);
        statUpdateDatetime = bottomSheetView.findViewById(R.id.Datetime);
        csId = bottomSheetView.findViewById(R.id.csi);
        cpId = bottomSheetView.findViewById(R.id.cpi);
        cpNm = bottomSheetView.findViewById(R.id.cpn);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // 바텀 시트 슬라이드 동작 감지
            }
        });

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

        KakaoSdk.init(this, "{058aba7c75990149427e6a9956137af0}");
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setCalloutBalloonAdapter(
            new CustomCalloutBalloonAdapter(
                bottomSheetBehavior,
                address, csNm, cpTp, chargeTp, spStat, statUpdateDatetime,
                csId, cpId, cpNm,
                mapView, UnLoginMainActivity.this, info
            )
        );
        updateMarker();

        chargeUsingType = findViewById(R.id.unlogin_using_btn);
        chargeUsingType.setCheckable(true);
        chargeUsingType.setCheckedIconVisible(false);
        chargeUsingType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateUsing(isChecked ? UsingType.USING : UsingType.ENTIRE);
            }
        });
        chargeUsingType.setOnClickListener(this);

        chargeTotalType = findViewById(R.id.unlogin_type_btn);
        chargeTotalType.setCheckable(true);
        chargeTotalType.setCheckedIconVisible(false);
        chargeTotalType.setOnClickListener(this);

        chargeSpeedType = findViewById(R.id.unlogin_speed_btn);
        chargeSpeedType.setCheckedIconVisible(false);
        chargeSpeedType.setOnClickListener(this);

        searchText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        switch (id) {
            case R.id.unlogin_more_btn:
                showCheckBox();
                break;
            case R.id.unlogin_searchbar:
                if (searchText.length() > 0) {
                    searchText.getText().clear();
                    searchText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0);
                    mapView.removePOIItem(searchMarker);
                } else {
                    startDestination();
                }
                break;
            case R.id.unlogin_using_btn:
                viewOnClickUsing(v);
                break;
            case R.id.unlogin_type_btn:
                viewOnClickType(v);
                break;
            case R.id.unlogin_speed_btn:
                viewOnClickSpeed(v);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        startDestination();
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent review = result.getData();
                    if (review != null) {
                        Document document = review.getParcelableExtra("document");
                        searchText.setText(document.getPlaceName());

                        mSearchName = document.getPlaceName();
                        mSearchLng = Double.parseDouble(document.getX());
                        mSearchLat = Double.parseDouble(document.getY());
                        if (!isTrackingMode) {
                            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                            currentBtn = findViewById(R.id.current_btn);
                            currentBtn.setVisibility(View.VISIBLE);
                            currentBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                                    currentBtn.setVisibility(View.GONE);
                                }
                            });
                        }

                        if(searchMarker == null) {
                            searchMarker = new MapPOIItem();
                        }

                        searchMarker.setItemName(mSearchName);
                        searchMarker.setTag(markerCount);
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mSearchLat, mSearchLng), true);
                        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(mSearchLat, mSearchLng);
                        searchMarker.setMapPoint(mapPoint);
                        mapView.addPOIItem(searchMarker);
                        searchMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                        searchMarker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin);
                        //마커 드래그 가능하게 설정
                        searchMarker.setDraggable(false);
                        markerCount++;
                    }
                }
            });


    void startDestination() {
        if (!searchText.equals("")) {
            searchText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.seach_clear, 0);
        }
        Intent intent = new Intent(this, UnLoginDestinationActivity.class);
        launcher.launch(intent);
    }
    private void showCheckBox() {
        Chip speedBtn = findViewById(R.id.unlogin_speed_btn);
        Chip typeBtn = findViewById(R.id.unlogin_type_btn);
        Chip usingBtn = findViewById(R.id.unlogin_using_btn);

        isCheckboxVisible = !isCheckboxVisible;
        speedBtn.setVisibility(isCheckboxVisible ? View.VISIBLE : View.GONE);
        typeBtn.setVisibility(isCheckboxVisible ? View.VISIBLE : View.GONE);
        usingBtn.setVisibility(isCheckboxVisible ? View.VISIBLE : View.GONE);
    }
    private void viewOnClickUsing(View v) {
        HorizontalScrollView scrollView = findViewById(R.id.scroll_view);
        int maxScrollX = scrollView.getChildAt(0).getWidth() - scrollView.getWidth();
        scrollView.scrollTo(-maxScrollX, 0);
        usingType = chargeUsingType.isChecked() ? UsingType.USING : UsingType.ENTIRE;
        updateUsing(usingType);
    }
    private void viewOnClickSpeed(View v) {
        Chip chip = (Chip) v;
        HorizontalScrollView scrollView = findViewById(R.id.scroll_view);
        int maxScrollX = scrollView.getChildAt(0).getWidth() - scrollView.getWidth();
        scrollView.scrollTo(maxScrollX, 0);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.unlogin_using_speed, null);
        popupWindow = new PopupWindow(popupView, 250, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        speedBoxEntire = popupView.findViewById(R.id.entire_charge);
        speedBoxPos = popupView.findViewById(R.id.char_full);
        speedBoxImps = popupView.findViewById(R.id.char_speed);

        speedBoxEntire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedType = speedBoxEntire.isChecked() ? SpeedType.ENTIRE : speedType;
                isSpeedEntire = speedBoxEntire.isChecked();
                updateSpeedType(speedType,chip);
                chip.setText("전체속도");

            }
        });

        speedBoxPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedType = speedBoxPos.isChecked() ? SpeedType.FULL : speedType;
                isSpeedFull = speedBoxPos.isChecked();
                updateSpeedType(speedType,chip);
                chip.setText("완속");
            }
        });

        speedBoxImps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedType = speedBoxImps.isChecked() ? SpeedType.FAST : speedType;
                isSpeedFast = speedBoxImps.isChecked();
                updateSpeedType(speedType,chip);
                chip.setText("급속");
            }
        });
        updateSpeedType(speedType,chip);

        speedBoxEntire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateSpeedType(speedType.ENTIRE, chip);
                }
            }
        });
        speedBoxPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateSpeedType(speedType.FULL, chip);
                }
            }
        });

        speedBoxImps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateSpeedType(speedType.FAST, chip);
                }
            }
        });
        int offsetX = 15;
        int offsetY = 0;

        popupWindow.showAsDropDown(v, offsetX, offsetY);
    }

    private void viewOnClickType(View v) {
        Chip chip = (Chip) v;
        HorizontalScrollView scrollView = findViewById(R.id.scroll_view);
        int maxScrollX = scrollView.getChildAt(0).getWidth() - scrollView.getWidth();
        int centerX = maxScrollX / 2;
        scrollView.scrollTo(-centerX, 0);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.unlogin_using_typemenu, null);

        popupWindow = new PopupWindow(popupView, 330, 600);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        entireType = popupView.findViewById(R.id.unlogin_entire_type);
        chargeTypeBtype = popupView.findViewById(R.id.b_type);
        chargeTypeCtype = popupView.findViewById(R.id.c_type);
        chargeTypeBCtypeFiv = popupView.findViewById(R.id.bc_type_5);
        chargeTypeBCtypeSev = popupView.findViewById(R.id.bc_type_7);
        chargeTypeDc = popupView.findViewById(R.id.unlogin_speed_dc);
        chargeTypeAc = popupView.findViewById(R.id.unlogin_speed_ac);
        chargeTypecomb = popupView.findViewById(R.id.login_speed_dccomb);
        chargeTypeDCtypeDCComb = popupView.findViewById(R.id.dc_dccomb);
        chargeTypeDC_AC = popupView.findViewById(R.id.dc_ac);
        chargeTypeDC_DCComb_AC = popupView.findViewById(R.id.dc_dccomb_ac);

        entireType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = entireType.isChecked() ? ChargeType.ENTIRE : chargeType;
                isChargeEntire = entireType.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("전체 타입");
            }
        });
        chargeTypeBtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeBtype.isChecked() ? ChargeType.B_TYPE : chargeType;
                isChargeBtype = chargeTypeBtype.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("B타입(5핀)");
            }
        });
        chargeTypeCtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeCtype.isChecked() ? ChargeType.C_TYPE : chargeType;
                isChargeCtype = chargeTypeCtype.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("C타입(5핀)");
            }
        });
        chargeTypeBCtypeFiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeBCtypeFiv.isChecked() ? ChargeType.BC_TYPE_FIV : chargeType;
                isChargeBCtypeFiv = chargeTypeBCtypeFiv.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("BC타입(5핀)");
            }
        });
        chargeTypeBCtypeSev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeBCtypeSev.isChecked() ? ChargeType.BC_TYPE_SEV : chargeType;
                isChargeBCtypeSev = chargeTypeBCtypeSev.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("BC타입(7핀)");
            }
        });
        chargeTypeDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeDc.isChecked() ? ChargeType.DC : chargeType;
                isChargeDC = chargeTypeDc.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("DC차데모");
            }
        });
        chargeTypeAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeAc.isChecked() ? ChargeType.AC : chargeType;
                isChargeAC = chargeTypeAc.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("AC3상");
            }
        });
        chargeTypecomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypecomb.isChecked() ? ChargeType.DC_COMB : chargeType;
                isChargeDCCOMB = chargeTypecomb.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("DC콤보");
            }
        });
        chargeTypeDCtypeDCComb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeDCtypeDCComb.isChecked() ? ChargeType.DC_TYPE_DC_COMB : chargeType;
                isChargeDC_TYPE_DC_COMB = chargeTypeDCtypeDCComb.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("DC차데모 + DC콤보");
            }
        });
        chargeTypeDC_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeDC_AC.isChecked() ? ChargeType.DC_AC : chargeType;
                isChargeDC_AC = chargeTypeDC_AC.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("DC차데모 + AC3상");
            }
        });
        chargeTypeDC_DCComb_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeType = chargeTypeDC_DCComb_AC.isChecked() ? ChargeType.DC_DC_COMB_AC : chargeType;
                isChargeDC_DC_COMB_AC = chargeTypeDC_DCComb_AC.isChecked();
                updateChargeType(chargeType,chip);
                chip.setText("DC차데모 + DC콤보 + AC3상");
            }
        });

        updateChargeType(chargeType, chip);

        entireType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.ENTIRE, chip);
                }
            }
        });
        chargeTypeBtype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.B_TYPE, chip);
                }
            }
        });

        chargeTypeCtype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.C_TYPE, chip);
                }
            }
        });
        chargeTypeBCtypeFiv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.BC_TYPE_FIV, chip);
                }
            }
        });
        chargeTypeBCtypeSev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.BC_TYPE_SEV, chip);
                }
            }
        });

        chargeTypeDc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.DC, chip);
                }
            }
        });
        chargeTypeAc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.AC, chip);
                }
            }
        });
        chargeTypecomb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.DC_COMB, chip);
                }
            }
        });

        chargeTypeDCtypeDCComb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.DC_TYPE_DC_COMB, chip);
                }
            }
        });
        chargeTypeDC_AC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.DC_AC, chip);
                }
            }
        });
        chargeTypeDC_DCComb_AC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateChargeType(chargeType.DC_DC_COMB_AC, chip);
                }
            }
        });


        popupWindow.showAsDropDown(v);
    }

    private void updateUsing(UsingType usingType) {
        chargeUsingType.setChecked(usingType == UsingType.USING);
        chargeUsingType.setText(usingType == UsingType.USING? "사용가능" : "전체");

        if(this.usingType != usingType) {
            this.usingType = usingType;
            updateMarker();
        }
    }

    private void updateSpeedType(SpeedType speedType, Chip chip) {
        speedBoxEntire.setChecked(speedType == SpeedType.ENTIRE);
        speedBoxPos.setChecked(speedType == SpeedType.FULL);
        speedBoxImps.setChecked(speedType == SpeedType.FAST);
        boolean isChipState = isSpeedEntire || isSpeedFull || isSpeedFast;
        chip.setChecked(isChipState);


        if(this.speedType != speedType) {
            this.speedType = speedType;
            updateMarker();
        }
    }
    private void updateChargeType(ChargeType chargeType, Chip chip) {
        entireType.setChecked(chargeType == ChargeType.ENTIRE);
        chargeTypeBtype.setChecked(chargeType == ChargeType.B_TYPE);
        chargeTypeCtype.setChecked(chargeType == ChargeType.C_TYPE);
        chargeTypeBCtypeFiv.setChecked(chargeType == ChargeType.BC_TYPE_FIV);
        chargeTypeBCtypeSev.setChecked(chargeType == ChargeType.BC_TYPE_SEV);
        chargeTypeDc.setChecked(chargeType == ChargeType.DC);
        chargeTypeAc.setChecked(chargeType == ChargeType.AC);
        chargeTypecomb.setChecked(chargeType == ChargeType.DC_COMB);
        chargeTypeDCtypeDCComb.setChecked(chargeType == ChargeType.DC_TYPE_DC_COMB);
        chargeTypeDC_AC.setChecked(chargeType == ChargeType.DC_AC);
        chargeTypeDC_DCComb_AC.setChecked(chargeType == ChargeType.DC_DC_COMB_AC);

        boolean isChipState = isChargeEntire
                || isChargeBtype
                || isChargeCtype
                || isChargeBCtypeFiv
                || isChargeBCtypeSev
                || isChargeDC
                || isChargeAC
                || isChargeDCCOMB
                || isChargeDC_TYPE_DC_COMB
                || isChargeDC_AC
                || isChargeDC_DC_COMB_AC;
        chip.setChecked(isChipState);

        if(this.chargeType != chargeType){
            this.chargeType = chargeType;
            updateMarker();
        }
    }

    private void updateMarker() {
        if(databaseCharge == null) {
            databaseCharge = new DatabaseCharge(this, mapView);
        }

        mapView.removeAllPOIItems(); // 모든 마커 제거
        databaseCharge.updateData(
            this.usingType,
            this.speedType,
            this.chargeType,
            new DatabaseCharge.OnMarkerCountReceivedListener() {
                @Override
                public void onMarkerCountReceived(int markerCount) {

                }
            }
        );
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
        mapView.setMapCenterPoint(currentMapPoint, true);
        mCurrentLat = mapPointGeo.latitude;
        mCurrentLng = mapPointGeo.longitude;
        if (!isTrackingMode) {
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        }
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }


    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
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
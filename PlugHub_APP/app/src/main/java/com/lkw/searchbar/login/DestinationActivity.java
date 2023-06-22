package com.lkw.searchbar.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkw.searchbar.R;
import com.lkw.searchbar.login.GettetSetter.ArrayInfo;
import com.lkw.searchbar.login.GettetSetter.Marker_Info;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DestinationActivity extends AppCompatActivity {
    SearchView search; // 검색 바
    TextView text; // 택스트 형태로 결과 출력
    Button button; // 검색 버튼
//    RecyclerView list; // 리스트 형태로 결과 출력
//    ArrayAdapter<String> adapter; // 리스트 형태로 결과 출력
//    MyAdapter adapter1; // 리스트 형태로 결과 출력
    String key = "FTwkQJPaWbBulxiQe8rtI5nLnbhyvD9bMWJn3Ism2%2BqN3fQHeAMAoNuUml5%2BMFi7ggLnRbb4VVzOwwqE65T54A%3D%3D";
//    private volatile String data;
    String address = null; // 주소를 저장할 변수
    String csNm = null;
    String cpTp = null;
    String cpId = null;
    String csId = null;
    String cpNm = null;
    String chargeTp = null;
    String spStat = null;
    String statUpdateDatetime = null;
    String Lat = null;
    String Longi = null;
//    LinearLayoutManager layoutManager; // 리스트 형태로 결과 출력
    ArrayInfo arrayInfo = ArrayInfo.getInstance();
    public ArrayList<String> cpId_set = new ArrayList<>();
    public ArrayList<String> csId_set = new ArrayList<>();
    public ArrayList<String> cpNm_set = new ArrayList<>();
    public ArrayList<String> address_set = new ArrayList<>();
    public ArrayList<String> chargeTp_set = new ArrayList<>();
    public ArrayList<String> csNm_set = new ArrayList<>();
    public ArrayList<String> spStat_set = new ArrayList<>();
    public ArrayList<String> cpTp_set = new ArrayList<>();
    public ArrayList<String> statUpdateDatetime_set = new ArrayList<>();
    public ArrayList<String> Lat_set = new ArrayList<>();
    public ArrayList<String> Longi_set = new ArrayList<>();
    TextView cnt_re; // 검색량
    String result_c;
    int cnt = 0; // 주소 카운팅

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to the main screen
            }
        });

        search = (SearchView) findViewById(R.id.search);
        // 텍스트 형태로 검색 결과창
        text = (TextView) findViewById(R.id.result);
//        // 리스트 형태로 검색 결과창 <androidx.recyclerview.widget.RecyclerView>
//        list = (RecyclerView) findViewById(R.id.result);
//        layoutManager = new LinearLayoutManager(this);
//        list.setLayoutManager(layoutManager);
    }

    public void mOnClick(View v) {
        if (v.getId() == R.id.button) {

            String searchQuery = search.getQuery().toString();
            if (searchQuery.isEmpty()) {
                // 검색창이 비어있으면 동작하지 않도록 처리
                Toast.makeText(getApplicationContext(), "검색어를 입력하시오", Toast.LENGTH_SHORT).show();
                return;
            }
            // 버튼 상태 변경
            button = findViewById(R.id.button);
            button.setBackgroundResource(R.drawable.isbutton);
            button.setText("검색 중");

            // 검색량
            cnt_re = findViewById(R.id.ar_time);
            // 재 검색 시 time 초기화
            cnt_re.setText("");
            cnt_re.setBackgroundColor(getResources().getColor(R.color.white));

            // 검색 출력 화면 초기화
            text.setText("");
            text.setBackgroundColor(getResources().getColor(R.color.white));

            new Thread(new Runnable() {
                @Override
                public void run() {

                    // 검색 결과 가져오기
                     String data = getXmlData();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result_c = String.format("검색량 %d개", cnt); // 검색량 조회
                            cnt_re.setBackgroundResource(R.drawable.istime);
                            cnt_re.setText(result_c);

                            cnt = 0; // 주소 카운팅 초기화

                            button.setBackgroundResource(R.drawable.search_button);
                            button.setText("검색");

                            // 검색 결과 텍스트 설정
                            text.setBackgroundResource(R.drawable.result_view);
                            text.setText(data);

                            // 기존 마커 제거
                            Marker_Info.getInstance().removeMarkers(MainActivity.mapView);
                            Marker_Info.getInstance().removeMarker(MainActivity.mapView);

                            // 새로운 마커들 추가
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    MapPoint markerPoint = MapPoint.mapPointWithGeoCoord(
                                            Double.parseDouble(arrayInfo.getLat().get(i)),
                                            Double.parseDouble(arrayInfo.getLongi().get(i)));
                                    MapPOIItem marker = new MapPOIItem();
                                    marker.setItemName(arrayInfo.getCsNm().get(i) + " " + (i + 1) + "번");
                                    marker.setTag(i + 1);
                                    marker.setMapPoint(markerPoint);
                                    marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                                    marker.setShowCalloutBalloonOnTouch(true);
                                    // 새로운 마커를 지도에 추가
                                    MainActivity.mapView.addPOIItem(marker);
                                    // 마커 리스트에 추가
                                    Marker_Info.getInstance().addMarker(marker);
                                    // 마커의 정보가 들어가는지 확인용
                                    System.out.println(Marker_Info.getInstance().getMarkerList());
                                    // 새로운 마커가 보이도록 지도 중심 및 줌 레벨 설정
                                    MapPOIItem lastMarker = Marker_Info.getInstance().getMarkerList().get(Marker_Info.getInstance().getMarkerList().size() - 1);
                                    MainActivity.mapView.setMapCenterPoint(lastMarker.getMapPoint(), true);

                                    System.out.println("마커 데이터 개수: " + Marker_Info.getInstance().getMarkerList().size());
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    String getXmlData() {
        StringBuilder buffer = new StringBuilder(1024);
        String str = search.getQuery().toString();
        String location = URLEncoder.encode(str);
        String queryUrl = "http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList?addr=" + location + "&pageNo=1&numOfRows=3402&ServiceKey=" + key;
        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();

                        if (tag.equals("item")) ;
                        else if (tag.equals("addr")) {
//                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");

                            address = xpp.getText();
                            address_set.add(address);
                            arrayInfo.setAddr(address_set);

                            cnt++; // 주소 카운팅 시작

                        } else if (tag.equals("chargeTp")) {
//                            buffer.append("충전기 타입 : "); // 급속/완속
                            xpp.next();
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");

                            chargeTp = xpp.getText();
                            chargeTp_set.add(chargeTp);
                            arrayInfo.setChargeTp(chargeTp_set);

                        } else if (tag.equals("cpId")) { // 숫자
//                            buffer.append("충전기 ID :");
                            xpp.next();
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");

                            cpId = xpp.getText();
                            cpId_set.add(cpId);
                            arrayInfo.setCpId(cpId_set);

                        } else if (tag.equals("csNm")) {  // 명칭
//                            buffer.append("충전소 명칭 :");
                            xpp.next();
                            buffer.append("→ " + xpp.getText() + " ←");
                            buffer.append("\n");

                            csNm = xpp.getText();
                            csNm_set.add(csNm);
                            arrayInfo.setCsNm(csNm_set);

                        } else if (tag.equals("cpStat")) { // 숫자
//                            buffer.append("충전기 상태 코드 :");
                            xpp.next();
//                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가

                            spStat = xpp.getText();
                            spStat_set.add(spStat);
                            arrayInfo.setSpStat(spStat_set);

                        } else if (tag.equals("cpTp")) { // 숫자
//                            buffer.append("충전 방식 :");
                            xpp.next();
//                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가

                            cpTp = xpp.getText();
                            cpTp_set.add(cpTp);
                            arrayInfo.setCpTp(cpTp_set);

                        } else if (tag.equals("csId")) { // 숫자
//                            buffer.append("충전소 ID :");
                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가

                            csId = xpp.getText();
                            csId_set.add(csId);
                            arrayInfo.setCsId(csId_set);

                        } else if (tag.equals("cpNm")) { //
//                            buffer.append("충전기 명칭 :");
                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가

                            cpNm = xpp.getText();
                            cpNm_set.add(cpNm);
                            arrayInfo.setCpNm(cpNm_set);

                        } else if (tag.equals("lat")) { // 숫자
//                            buffer.append("위도 :");
                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가

                            Lat = xpp.getText();
                            Lat_set.add(Lat);
                            arrayInfo.setLat(Lat_set);

                        } else if (tag.equals("longi")) { // 숫자
//                            buffer.append("경도 :");
                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가

                            Longi = xpp.getText();
                            Longi_set.add(Longi);
                            arrayInfo.setLongi(Longi_set);

                        } else if (tag.equals("statUpdateDatetime")) { // 숫자
//                            buffer.append("충전기 상태 갱신 시각 :");
                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가

                            statUpdateDatetime = xpp.getText();
                            statUpdateDatetime_set.add(statUpdateDatetime);
                            arrayInfo.setStatUpdateDatetime(statUpdateDatetime_set);

                            buffer.append("-".repeat(5)).append("\n"); // Decoration line
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        buffer.append("파싱 끝\n"); // 파싱이 제대로 되는지 확인하기 위해 임시 출력
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....

}
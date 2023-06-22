# PlugHub_App
- # Function (login)
<img src="https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/4b13ca30-bf18-48d1-a844-88f2fa5b1c8a" alt="app" width="500px">
---
 # 1.프로젝트 계획이유
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/6c445c67-2b3b-4543-9ef6-5b4def69c94f)
---
 # 2.개발 구상도
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/ae8c1b14-983e-43b4-826e-608f791efa68)
--- 
 # 3.PlugHub_App 기능

> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/2703a181-0af8-4976-8b43-6d4e94f6c9b6)
---
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/12ae5ecf-c327-4cc8-a602-c311af3a2da6)
---
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/d06cfeac-a115-410c-bcd4-f34a8c8f7818)
---
### 공공데이터포털 정보 파싱
```java
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
```
---
### 지도에 마커 추가
```java
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
```
---
### 다이얼 로그 (로그인 시 도움말)
```java
private TextView menu;
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
```

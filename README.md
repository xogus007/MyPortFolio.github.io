## PlugHub_App
 - 프로젝트 계획이유
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/7eefb0f1-ba95-418c-997f-f670939fed93)
---
 - PlugHub_App 기능

> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/1681a401-d632-4a71-9002-59a68d23f060)
---
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/39eb94a8-c28d-4a46-ae76-3f23771fad3e)
---
> ![image](https://github.com/xogus007/MyPortFolio.github.io/assets/121161535/d7735303-d935-4486-966e-a085e53cb764)
---
- 다이얼 로그 (도움말)
```java
private TextView menu;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

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
                                "         (필터 2가지 선택 시 이전 마커 덮어 씌우기)\n" +
                                "     ☆ 체크박스 하나를 해제 했다가 다시 선택하면 파란 마커만 표시\n" +
                                "\n     ★ 필터 3가지 선택 : 파란색" +
                                "\n         (2가지 필터 선택과 색상이 동일한 방식)\n" +
                                "     ☆ 2가지 필터와 동일한 색상이기에 마지막에 고른 필터\n" +
                                "          체크 해제했다가 다시한번 체크하시기 바랍니다.");
                main2.setTextSize(11);
                TextView main3 = new TextView(MainActivity.this);
                main3.setText("\n     ★ 필터가 정상 작동을 하지 않을 경우 \n" +
                        "          체크박스를 해제했다가 다시 클릭해보세요.\n");
                main3.setTextSize(11);
//                main3.setTypeface(main1.getTypeface(), Typeface.BOLD);
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

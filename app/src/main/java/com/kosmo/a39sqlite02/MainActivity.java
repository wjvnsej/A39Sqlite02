package com.kosmo.a39sqlite02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO61";

    SQLiteDatabase database;
    SingerAdapter adapter;
    TextView textView2;

    String dbName;
    String tname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //테이블명
        tname = "customer";

        //어댑터객체 생성 및 리스트뷰 위젯에 설정하기
        adapter = new SingerAdapter(this);
        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        //리스트뷰에 리스너 부착하기
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                특정 아이템을 클릭했을 때 전달되는 인덱스(i)를 통해
                어댑터 항목을 가져온다. getName()으로 이름을 토스트로 출력한다.
                 */
                SingerDTO item = (SingerDTO)adapter.getItem(i);
                Toast.makeText(getApplicationContext(),
                        "선택항목 : " + item.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        textView2 = findViewById(R.id.textView2);

        createMyDatabase();
        createMyTable();
        selectAllData();
    }//// onCreate End

    //추가하기버튼
    public void onBtn1Clicked(View v) {

        //2개의 데이터를 입력 한 후 추가가
       String sql1 = "INSERT INTO customer (name, age, mobile) " +
                            "VALUES ('방탄소년단', 7, '010-1234-5678') ";
        String sql2 = "INSERT INTO customer (name, age, mobile) " +
                            "VALUES ('레드벨벳', 5, '010-9876-5432') ";

        try {
            database.execSQL(sql1);
            printInfo("데이터 추가 : 1");

            database.execSQL(sql2);
            printInfo("데이터 추가 : 2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//// onBtn1Clicked End

    //조회하기 버튼
    public void onBtn2Clicked(View v) {
        selectAllData();
    }

    //매개변수로 전달되는 스트링을 텍스트뷰에 추가한다.
    private void printInfo(String msg) {
        textView2.append(msg + "\n");
    }

    private void createMyDatabase() {

        /*
        customer.sqlite 데이터베이스가 있다면 열고, 없다면 생성한다.
         */
        try {
            database = openOrCreateDatabase("customer.sqlite", Activity.MODE_PRIVATE, null);

            printInfo("데이터베이스 생성 : customer.sqlite");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//// createMyDatabase End

    private void createMyTable() {
        /*
        customer 테이블이 존재하지 않으면 생성한다.
         */
        String sql = "CREATE TABLE IF NOT EXISTS customer (name text, age integer, mobile text) ";

        try {
            //위에서 생성한 쿼리문을 실행한다.
            database.execSQL(sql);

            printInfo("테이블 만듬 : customer");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//// createMyTable End

    //테이블의 모든 레코드를 조회한다.
    private void selectAllData() {

        String sql = "SELECT * FROM customer";

        try {
            //select 의 결과를 얻어온다.
            Cursor cursor = database.rawQuery(sql, null);

            //getCount()를 통해 데이터갯수 조회
            int count = cursor.getCount();
            printInfo("데이터 갯수 : " + count);

            //select 된 결과셋의 갯수만큼 반복
            int i = 0;
            while (i < count) {
                //결과셋의 다음 항목으로 이동한다.
                cursor.moveToNext();
                //레코드에서 하나의 항목을 인덱스를 통해 가져온다.
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                Log.d(TAG, "#" + name + " : " + age + " : " + mobile);
                printInfo("#" + name + " : " + age + " : " + mobile);

                //하나의 레코드를 DTO에 저장한 후 어댑터에 저장한다.
                SingerDTO item = new SingerDTO(name, age, mobile);
                adapter.addItem(item);

                i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//// selectAllData End

}
























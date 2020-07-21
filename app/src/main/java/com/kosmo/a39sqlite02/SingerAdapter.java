package com.kosmo.a39sqlite02;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SingerAdapter extends BaseAdapter {
    Context context;
    ArrayList<SingerDTO> items = new ArrayList<>();

    //생성자
    public SingerAdapter(Context context) {
        this.context = context;
    }

    //매개변수로 전달되는 DTO객체를 데이터로 추가함
    public void addItem(SingerDTO item) {
        items.add(item);
    }

    //BaseAdapter클래스를 상속한후 기본적으로 오버라이딩 하는 메소드 4가지
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SingerItemView view = null;
        if(convertView == null) {
            view = new SingerItemView(context);
        }
        else {
            view = (SingerItemView)convertView;
        }

        //인덱스에 해당하는 item을 얻어와서 어뎁터항목을 설정한다.
        final SingerDTO item = items.get(position);
        view.setName(item.getName());
        view.setAge(item.getAge());
        view.setMobile(item.getMobile());

        return view;
    }
}


































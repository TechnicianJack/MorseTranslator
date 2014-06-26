//package com.example.morsetranslator;
//
//import android.content.Context;
//
//import android.view.*;
//import android.widget.*;
//import android.util.*;
//
//import java.util.*;
//
//public class CodeGridAdapter extends BaseAdapter
//implements View.OnClickListener
//{
//    private Context mContext;
//    private Character[] keys;
//    private String[] vals;
//
//    public CodeGridAdapter(Context ctx)
//    {
//        mContext = ctx;
//        int size = MorseCodeMap.map.size();
//
//
//        keys = (Character[])MorseCodeMap.map.keySet().toArray(new Character[0]);
//        vals = (String[])MorseCodeMap.map.values().toArray(new String[0]);
//    }
//
//    public int getCount()
//    {
//        return keys.length;
//    }
//
//    public Object getItem(int pos)
//    {
//        return null;
//    }
//
//
//    public long getItemId(int pos)
//    {
//        return 0;
//    }
//
//    public View getView(int pos, View view, ViewGroup parent)
//    {
//        Button btn;
//        if (view == null) {
//            btn = new Button(mContext);
//            btn.setBackgroundResource(R.drawable.code_btn);
//            btn.setOnClickListener(this);
//            btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);
//        } else {
//            btn = (Button)view;
//        }
//
//        btn.setTag(String.valueOf(keys[pos]));
//        btn.setText(String.valueOf(keys[pos]) + "\n" + vals[pos]);
//
//        return btn;
//    }
//
//    public void onClick(View v)
//    {
//        ((MorseCodeActivity)mContext).playSineWave((String)v.getTag());
//    }
//}
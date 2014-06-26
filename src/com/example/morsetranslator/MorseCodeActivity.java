//package com.example.morsetranslator;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import android.view.*;
//import android.widget.*;
//
//import android.view.inputmethod.EditorInfo;
//
//import android.media.AudioTrack;
//import android.media.AudioManager;
//import android.media.AudioFormat;
//
//import android.graphics.Color;
//
//import android.util.Log;
//
//public class MorseCodeActivity extends Activity
//implements View.OnClickListener
//{
//    static boolean mRecording = false;
//
//    Button[] tab_btn = new Button[2];
//
//    ViewGroup main_view;
//    View[] selected_view = new View[2];
//
//    EditText code_edit;
//
//    TextView send_txt;
//    TextView rx_txt;
//    TextView rx_info_txt;
//
//    // for synchronizing
//    private SineWaveTask mSineWaveTask;
//
//    private int mFrequency  = 1000;         // limit: 100 ~ 5100
//    private int mSpeed      = 10;           // limit: 5 ~ 35
//    private int mThemeColor = R.drawable.ginza_line;
//
//    private static final int tx = 0;
//    private static final int rx = 1;
//
//    private boolean isTX = true;
//
//    static String lang;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        SharedPreferences settings = getSharedPreferences("MYCONF", 0);
//        mFrequency = settings.getInt("frequency", mFrequency);
//        mSpeed = settings.getInt("speed", mSpeed);
//        mThemeColor = settings.getInt("theme_color", mThemeColor);
//
//        initView();
//
//        lang = getResources().getConfiguration().locale.getLanguage();
//    }
//
//    public void onStop()
//    {
//        super.onStop();
//
//        SharedPreferences settings = getSharedPreferences("MYCONF", 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("frequency", mFrequency);
//        editor.putInt("speed", mSpeed);
//        editor.putInt("theme_color", mThemeColor);
//        editor.commit();
//
//        if (mRecording) {
//            mRecording = false;
//        }
//
//        if (mSineWaveTask != null) {
//            mSineWaveTask.cancel(true);
//            mSineWaveTask = null;
//        }
//    }
//
//    private void initView()
//    {
//
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        setContentView(R.layout.main);
//
//        tab_btn[tx] = (Button)findViewById(R.id.btn_tx);
//        tab_btn[rx] = (Button)findViewById(R.id.btn_rx);
//        tab_btn[tx].setOnClickListener(this);
//        tab_btn[rx].setOnClickListener(this);
//
//        LayoutInflater inflater = (LayoutInflater)
//            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        selected_view[tx] = inflater.inflate(R.layout.tx_view, null);
//        selected_view[rx] = inflater.inflate(R.layout.rx_view, null);
//
//        inflater = null;
//
//        setThemeColor(mThemeColor);
//
//        main_view = (ViewGroup)findViewById(R.id.main_view);
//        main_view.addView(selected_view[tx]);
//
//        setViewTX();
//        setViewRX();
//    }
//
//    private void setViewTX()
//    {
//        GridView code_grid = (GridView)(selected_view[tx].findViewById(R.id.code_grid));
//        code_grid.setAdapter(new CodeGridAdapter(this));
//
//        code_edit = (EditText)(selected_view[tx].findViewById(R.id.code_edit));
//        code_edit.clearFocus();
//
//        send_txt = (TextView)(selected_view[tx].findViewById(R.id.send_txt));
//        send_txt.setText("\n");
//    }
//
//    private void setViewRX()
//    {
//        rx_txt = (TextView)(selected_view[rx].findViewById(R.id.rx_txt));
//        rx_info_txt = (TextView)(selected_view[rx].findViewById(R.id.rx_info_txt));
//    }
//
//    public void onClickSend(View v)
//    {
//        CharSequence code = code_edit.getText();
//        code_edit.clearFocus();
//
//        playSineWave(code.toString());
//    }
//
//    public void onClickSendStop(View v)
//    {
//        if (mSineWaveTask != null) {
//            mSineWaveTask.cancel(true);
//            mSineWaveTask = null;
//        }
//    }
//
//    public void onClickReceive(View v)
//    {
//        if (!mRecording) {
//            new MorseRecordTask(this, rx_txt).execute("");
//            Toast.makeText(this, "RECEIVE", Toast.LENGTH_SHORT).show();
//            mRecording = true;
//            rx_info_txt.setText("Receiving...");
//        }
//
//    }
//
//    public void onClickReceiveStop(View v)
//    {
//        if (mRecording) {
//            Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
//            mRecording = false;
//            rx_info_txt.setText("");
//        }
//    }
//
//    // === tab click event ===
//    public void onClick(View v)
//    {
//        if (v == tab_btn[tx]) {
//            isTX = true;
//            setTabActive(tab_btn[tx]);
//            setTabHide(tab_btn[rx]);
//            main_view.removeAllViews();
//            main_view.addView(selected_view[tx]);
//        } else if (v == tab_btn[rx]) {
//            isTX = false;
//            setTabActive(tab_btn[rx]);
//            setTabHide(tab_btn[tx]);
//            main_view.removeAllViews();
//            main_view.addView(selected_view[rx]);
//        } 
//    }
//
//    private void setTabActive(Button btn)
//    {
//        btn.setBackgroundColor(Color.BLACK);
//        btn.setTextColor(Color.WHITE);
//    }
//
//    private void setTabHide(Button btn)
//    {
//        btn.setBackgroundColor(0);
//        btn.setTextColor(R.color.black_fade);
//    }
//
//    void setThemeColor(int color) {
//        mThemeColor = color;
//        if (isTX) {
//            tab_btn[rx].setBackgroundResource(0);
//        } else {
//            tab_btn[tx].setBackgroundResource(0);
//        }
//
//        findViewById(R.id.top_view).setBackgroundResource(color);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.setting, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId()) {
//            case R.id.menu_theme:
//                new ThemeDialog(this).show();
//                return true;
//            case R.id.menu_audio_speed:
//                new AudioSpeedDialog(this).show();
//                return true;
//            case R.id.menu_help:
//                new HelpDialog(this).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void toast(String msg)
//    {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
//
//    void playSineWave(String code)
//    {
//        if (mSineWaveTask != null) {
//            mSineWaveTask.cancel(true);
//            mSineWaveTask = null;
//        }
//        mSineWaveTask = new SineWaveTask(this, mFrequency, mSpeed);
//        mSineWaveTask.execute(code);
//
//        String upperCase = code.toString().toUpperCase();
//        StringBuffer sb = new StringBuffer(upperCase);
//        sb.append("\n");
//        int size = upperCase.length();
//        for (int i = 0; i < size; i++) {
//            sb.append(MorseCodeMap.map.get(upperCase.charAt(i)));
//            sb.append("  ");
//        }
//        send_txt.setText(sb.toString());
//    }
//
//    void setFrequency(int frequency)
//    {
//        mFrequency = frequency;
//    }
//
//    void setSpeed(int speed)
//    {
//        mSpeed = speed;
//    }
//
//    int getFrequency()
//    {
//        return mFrequency;
//    }
//
//    int getSpeed()
//    {
//        return mSpeed;
//    }
//}

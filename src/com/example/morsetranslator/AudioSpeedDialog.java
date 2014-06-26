/////*//package com.example.morsetranslator;
//
//import android.content.Context;
//import android.content.DialogInterface;
//
//import android.app.Dialog;
//import android.app.AlertDialog;
//import android.view.*;
//import android.widget.*;
//
//
//public class AudioSpeedDialog 
//implements SeekBar.OnSeekBarChangeListener
//{
//    private Context mContext;
//    private Dialog mDialog;
//
//    private TextView freq_txt;
//    private TextView speed_txt;
//
//    public AudioSpeedDialog(Context ctx)
//    {
//        mContext = ctx;
//
//        View view = getInitView(ctx);
//
//        initViewComponents(view);
//
//        mDialog = new AlertDialog.Builder(ctx)
//            .setTitle(R.string.menu_audio_speed)
//            .setView(view)
//            .setPositiveButton(ctx.getString(R.string.ok), 
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which)
//                        {
//                        }
//            })
//            .create();
//
//    }
//
//    private View getInitView(Context ctx)
//    {
//        LayoutInflater inflater = (LayoutInflater)
//            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        return inflater.inflate(R.layout.menu_audio_speed, null);
//    }
//
//    public void show()
//    {
//        mDialog.show();
//    }
//
//    @Override
//    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser)
//    {
//        String tag = (String)seekbar.getTag();
//        int rate;
//
//        if (tag.equals("freq_bar")) {
//            rate = progress * 100 + 100;
//            freq_txt.setText(
//                    String.format("%s : %d Hz", mContext.getString(R.string.frequency), rate));
//        } else {
//            rate = progress + 5;
//            speed_txt.setText(
//                    String.format("%s : %d", mContext.getString(R.string.speed), rate));
//        }
//    }
//
//    private void initViewComponents(View view)
//    {
//        SeekBar freq_bar  = (SeekBar)(view.findViewById(R.id.freq_bar));
//        freq_bar.setOnSeekBarChangeListener(this);
//        SeekBar speed_bar = (SeekBar)(view.findViewById(R.id.speed_bar));
//        speed_bar.setOnSeekBarChangeListener(this);
//
//        freq_txt = (TextView)(view.findViewById(R.id.freq_txt));
//        speed_txt = (TextView)(view.findViewById(R.id.speed_txt));
//
//        int progress;
//
//        int freq = ((MorseCodeActivity)mContext).getFrequency();
//        progress = (freq / 100) - 1;
//        freq_bar.setProgress(progress);
//
//        freq_txt.setText(
//                String.format("%s : %d Hz", mContext.getString(R.string.frequency), freq));
//
//        int speed = ((MorseCodeActivity)mContext).getSpeed();
//        progress = speed - 5;
//        speed_bar.setProgress(progress);
//
//        speed_txt.setText(
//                String.format("%s : %d", mContext.getString(R.string.speed), speed));
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekbar)
//    {
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekbar)
//    {
//        String tag = (String)seekbar.getTag();
//        int rate;
//
//        if (tag.equals("freq_bar")) {
//            rate = seekbar.getProgress() * 100 + 100;
//            ((MorseCodeActivity)mContext).setFrequency(rate);
//        } else {
//            rate = seekbar.getProgress() + 5;
//            ((MorseCodeActivity)mContext).setSpeed(rate);
//        }
//    }
//
//}*/
package com.example.morsetranslator;

import android.os.AsyncTask;
import android.os.Debug;
import android.content.Context;

import android.util.Log;

import android.widget.*;

import android.media.AudioRecord;
import android.media.AudioFormat;
import android.media.MediaRecorder.AudioSource;

import java.io.*;
import java.util.*;
// Class for decoding Morse. Uses an audio buffer to record live audio and process it.
public class MorseRecordTask extends AsyncTask<String, String, String>
{
    private Context mContext;
    private AudioRecord mRecord;

    private TextView mTextView;

    public MorseRecordTask(Context ctx, TextView tv)
    {
        mContext = ctx;
        mTextView = tv;
    }

    protected void onPreExecute()
    {
        setAudioRecord();
    }

    protected String doInBackground(String... params)
    {

        while (mRecord.getState() != mRecord.STATE_INITIALIZED);

        mRecord.startRecording();


        short[] buf = new short[44100];
        int min_size = buf.length;
        int size;
        int on_cnt = 0;
        int off_cnt = 0;
        int space = 0;
        int off_idx = 0;
        int on_idx = 0;

        ArrayList<Integer> on_list = new ArrayList<Integer>();
        ArrayList<Integer> off_list = new ArrayList<Integer>();
        StringBuffer sb = new StringBuffer();
        // Starts the decoder
        while (true) {
            if (!MainActivity.mRecording) {
                mRecord.stop();
                mRecord.release();
                break;
            }
            if (MainActivity.mRestart) {
                //mRecord.stop();
            	sb = new StringBuffer();
                MainActivity.mRestart = false;
                //mRecord.startRecording();
            }


            size = readCodeSize(buf);

            if (min_size > size) {
                min_size = size;
            }

            if (min_size + 20 < size) { // signal ON
                if (off_cnt > 0) {
                    off_list.add(off_cnt);
                    off_cnt = 0;
                }
                on_cnt++;

            } else {                    // signal OFF
                if (on_cnt > 0) {
                    on_list.add(on_cnt);
                    on_cnt = 0;
                }
                off_cnt++;
            }

            if (on_cnt == 0 && space != 0 && off_cnt > space * 7) {
                if (off_list.get(off_list.size()-1) < space * 7) {
                    off_list.add(off_cnt);
                }
            }

            log("ON :"+on_list.toString());
            log("OFF:"+off_list.toString());


            if (on_list.size() == 1 && off_list.size() == 2 && space == 0) {
                space = off_list.get(1);
            }
            log("SPACE:"+space);


            int tmp = off_list.size();
            for (int i = 0; i < tmp; i++) {
                int x = off_list.get(i);

                if (space == 0) {
                    break;
                }


                if (x > space * 2) { // word space

                    if (off_idx >= i) {
                        continue;
                    }

                    StringBuffer code = new StringBuffer();

                    int on_len = on_list.size();
                    int j, y;
                    for (j = on_idx; j < on_len; j++) {
                        y = on_list.get(j);
                        if (y <= space * 2) {   // dot code
                            code.append(".");
                        } else {
                            code.append("-");     // dash code
                        }
                    }
                    Character morse = MorseCodeMap.map_key.get(code.toString());

                    if (morse != null) {
                        sb.append(morse);
                    }
                    log("off_index:"+i+" code:"+code.toString());

                    off_idx = i;
                    on_idx = j;
                    
                    if (x > space * 7) { // space space
                        int len = sb.length();
                        if (len != 0 && sb.charAt(len-1) != ' ') {
                            sb.append(" ");
                        }
                    }
                }
            }

            publishProgress(sb.toString());
        }


        return sb.toString();
    }
    // Displays decoded audio to textview
    protected void onProgressUpdate(String... values)
    {
    	mTextView.setText("\nRX:\n" + values[0]);
		//mTextView.setText("\nRX:\n" + values[0] + "\n" + mTextView.getText());
    }

    protected void onPostExecute(String res)
    {
        //mTextView.setText("\nPOSTEXEC:\n" + res + "\n" + mTextView.getText());
    	mTextView.setText("POSTEXEC: " + res);
    }

    private int readCodeSize(short[] buf)
    {
        mRecord.read(buf, 0, buf.length);
        //StringBuffer sbr = new StringBuffer();
        
        int off = 0;
        int cnt = 0;

        for (int i = 0; i < buf.length; i+=10) {
            if (buf[i] > 1000) { // 1000
                //sbr.append("-");
                cnt++;

            } else {
                if (off >= 10) { // 10
                    cnt++;
                    //sbr.append("."); 
                    off = 0;
                }
                off++;
            }
        }
        return cnt;
    }


    // Audio buffer code. Defines buffer size and Audio format (PCM is WAV)
    private void setAudioRecord()
    {
        int size = AudioRecord.getMinBufferSize(
                44100, 
                AudioFormat.CHANNEL_IN_STEREO, 
                AudioFormat.ENCODING_PCM_16BIT);

        mRecord = new AudioRecord(
                AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                size);

    }

    private void log(String msg)
    {
        //Log.v("MorseRecordTask", msg);
    }

}
package com.example.morsetranslator;

import android.os.AsyncTask;
import android.content.Context;

import android.widget.Toast;

import android.media.AudioTrack;
import android.media.AudioManager;
import android.media.AudioFormat;

import android.util.Log;

import java.util.HashMap;

public class SineWaveTask extends AsyncTask<String, Void, Integer>
{
    private Context mContext;

    private static double DEFAULT_SAMPLE_RATE       = 44100.0;
    private static int DEFAULT_CHANNEL_CONFIG       = AudioFormat.CHANNEL_OUT_MONO;
    private static int DEFAULT_AUDIO_FORMAT         = AudioFormat.ENCODING_PCM_16BIT;

    private AudioTrack mTrack;

    private int[] len = new int[5];
    // len array indexes
    private static int dash         = 0;
    private static int dot          = 1;
    private static int dot_space    = 2;
    private static int word_space   = 3;
    private static int space_space  = 4;

    private double mIncrement;

    private double mFrequency;

    public SineWaveTask(Context ctx, int freq, int speed)
    {
        mContext = ctx;
        mFrequency = (double)freq;

        newAudioTrack();

        len[dot]            = (int)(DEFAULT_SAMPLE_RATE / (double)speed);
        len[dash]           = len[dot] * 3;
        len[dot_space]      = len[dot];
        len[word_space]     = len[dot] * 3;
        len[space_space]    = len[dot] * 7;
    }


    protected void onPreExecute()
    {
    }

    protected Integer doInBackground(String... params)
    {
        mIncrement = (2.0 * Math.PI) * mFrequency / DEFAULT_SAMPLE_RATE;

        mTrack.play();

        String original = params[0].toUpperCase();

        int char_len = original.length();
        for (int i = 0; i < char_len; i++) {
            if (isCancelled()) return 0;


            char ch = original.charAt(i);

            String code = MorseCodeMap.map.get((Character)ch);
            if (code == null) {
                code = " ";
            }

            int code_len = code.length();
            for (int j = 0; j < code_len; j++) {
                if (isCancelled()) return 0;

                char morse = code.charAt(j);
                switch (morse) {
                    case '.': 
                        dotCode(); break;
                    case '-':
                        dashCode(); break;
                    case ' ':
                        spaceSpaceCode(); break;
                    default:
                        break;
                }
            }

            wordSpaceCode();

        }

        return 0;
    }

    protected void onPostExecute(Integer res)
    {
        mTrack.flush();
        mTrack.release();
    }

    private void dashCode()
    {
        double angle = 0;
        short[] buf = new short[len[dash]];

        for (int i = 0; i < buf.length; i++) {
            buf[i] = (short)(Math.sin(angle) * Short.MAX_VALUE);
            angle += mIncrement;
        }
        mTrack.write(buf, 0, buf.length);

        space();
    }

    private void dotCode()
    {
        double angle = 0;
        short[] buf = new short[len[dot]];

        for (int i = 0; i < buf.length; i++) {
            buf[i] = (short)(Math.sin(angle) * Short.MAX_VALUE);
            angle += mIncrement;
        }
        mTrack.write(buf, 0, buf.length);

        space();
    }

    private void space()
    {
        short[] buf = new short[len[dot_space]];

        mTrack.write(buf, 0, buf.length);
    }

    private void wordSpaceCode()
    {
        short[] buf = new short[len[word_space]];

        mTrack.write(buf, 0, buf.length);
    }

    private void spaceSpaceCode()
    {
        short[] buf = new short[len[space_space]];

        mTrack.write(buf, 0, buf.length);
    }


    /** 
     *  create AudioTrack object.
     */
    private void newAudioTrack()
    {
        int size = AudioTrack.getMinBufferSize((int)DEFAULT_SAMPLE_RATE,
                DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT);

        mTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC, 
                (int)DEFAULT_SAMPLE_RATE,
                DEFAULT_CHANNEL_CONFIG,
                DEFAULT_AUDIO_FORMAT,
                size,
                AudioTrack.MODE_STREAM);
    }
}

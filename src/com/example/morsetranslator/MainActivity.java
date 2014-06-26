package com.example.morsetranslator;

//import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
//import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toast;

public class MainActivity extends Activity {
	
	MorsePlayer mp;
	//Timer timer;
    static boolean mRecording = false;
    static boolean mRestart = false;
    private int mFrequency  = 1000;         // limit: 100 ~ 5100
    private int mSpeed      = 10;           // limit: 5 ~ 35
    TextView rx_txt;
	
//	Handler timerHandler = new Handler();
//    Runnable timerRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//            
//        	
//            timerHandler.postDelayed(this, 1000);
//        }
//    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Numbers can be changed. '900' is the frequency of the Morse tones
        // 25 is the words per minute (speed) of the Morse
        mp = new MorsePlayer(900, 25);
    
        setContentView(R.layout.activity_main);    
        // Clear and send buttons
        Button buttonSend = (Button) findViewById(R.id.buttonSend);
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        
        EditText ed = (EditText) findViewById(R.id.editText1);
        
        rx_txt = (TextView) findViewById(R.id.textView2);
        mRecording = true;
        new MorseRecordTask(this, rx_txt).execute("");
        // TextChangedListener will prevent the send button from being pressed
        // if the text box is empty. This is to prevent the program crashing
        // if the user tries to send an empty input.
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	Button b = (Button) findViewById(R.id.buttonSend);
            	b.setEnabled(true);
            	
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        
        if (ed.getText().toString().equals("") )
  		{
        	buttonSend.setEnabled(false); //disable button;        		
  		}

       
        buttonClear.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
	          TextView textView2 = (TextView) findViewById(R.id.textView2);
	          TextView textViewTX = (TextView) findViewById(R.id.textViewTX);
	          textView2.setText("");
	          textViewTX.setText("");
	          mRestart = true;
	          //mRecording = false;
          }
        });
    
    
        buttonSend.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {        	
        	EditText ed = (EditText) findViewById(R.id.editText1);
        	String txt = new String();
          	String topstring;
          	Button b = (Button) findViewById(R.id.buttonSend);

          	//mRecording = true;
          	// Get text from edittext and play the entered message as morse
        	txt=ed.getText().toString();
          	mp.setMessage(txt);
          	mp.playMorse();
          	
            TextView textViewTX = (TextView) findViewById(R.id.textViewTX);

            //get text from edittext and convert it to string
            String messageString=ed.getText().toString();

            //Add to text window
            topstring=textViewTX.getText().toString();
            
            
            //set string from edittext to textview
            textViewTX.setText("\nTX:" + messageString + "\n" + topstring);
        		
            //clear edittext after sending text to message
            ed.setText("");
            
            if (ed.getText().toString().equals("") )
      		{
            	b.setEnabled(false); //disable button;        		
      		}else{
      			b.setEnabled(true);
      		}
            
          }
        });
        
    }
    
    void record() {
        
        //Toast.makeText(this, "RECEIVE", Toast.LENGTH_SHORT).show();
        
      //rx_info_txt.setText("Receiving...");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
      void setFrequency(int frequency)
	  {
	      mFrequency = frequency;
	  }
	
	  void setSpeed(int speed)
	  {
	      mSpeed = speed;
	  }
	
	  int getFrequency()
	  {
	      return mFrequency;
	  }
	
	  int getSpeed()
	  {
	      return mSpeed;
	  }
}

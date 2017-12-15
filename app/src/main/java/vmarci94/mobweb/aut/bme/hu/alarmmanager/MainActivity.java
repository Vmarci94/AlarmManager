package vmarci94.mobweb.aut.bme.hu.alarmmanager;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SET MY ALARM";
    private static boolean timerIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Date currentDate = Calendar.getInstance().getTime();
                int currentHour = currentDate.getHours();
                int currentMin = currentDate.getMinutes();

                if(currentHour > i ||
                        (currentHour == i &&  currentMin  > i1) ){
                    timePicker.setHour(currentHour);
                    timePicker.setMinute(currentMin);
                }
            }
        });

        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm(timePicker.getHour(), timePicker.getMinute());
            }
        });

    }

    void setAlarm(int setHour, int setMin){ //csak előrefele lehet állítani az időt ...
        Date date = Calendar.getInstance().getTime();

        int differentHour = setHour - date.getHours();
        int differentMinute = setMin - date.getMinutes();

        int timeAtMinute = (differentHour*60) + differentMinute;
        long differentTimeAtMillisec = (long) timeAtMinute*60*1000;

        Log.i("MTAG", "alarm: " + differentTimeAtMillisec);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if(alarmManager != null && !timerIsActive) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + differentTimeAtMillisec, TAG, new AlarmManager.OnAlarmListener() {
                @Override
                public void onAlarm() {
                    timerIsActive = false;
                    Toast.makeText(getApplicationContext(), "TIMEE", Toast.LENGTH_LONG).show();
                }
            }, null);
            timerIsActive = true;
        }
    }
}

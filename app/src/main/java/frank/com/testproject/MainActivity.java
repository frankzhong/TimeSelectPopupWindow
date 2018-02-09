package frank.com.testproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String[] timeArr = {"09:00-15:00", "15:00-19:00"};
    private String[] standardtimeArr = {"15:00", "19:00"};
    private TimeSelectPopupWindow popupWindow;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日[EEE]");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList<String> dateArr;
    private ArrayList<String> standardDateArr;
    private EditText mEditText;
    private String selectedDate, selectedTime;
    private int currentDatePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.edt);

        dateArr = getFutureDates(7, sdf1);
        standardDateArr = getFutureDates(7, sdf2);

    }

    private ArrayList<String> getFutureDates(int i, SimpleDateFormat sdf) {
        ArrayList<String> futureDateArr = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            futureDateArr.add(getFutureDate(j, sdf));
        }
        return futureDateArr;
    }

    private String getFutureDate(int j, SimpleDateFormat sdf) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + j);
        Date date = calendar.getTime();
        String result = sdf.format(date);
        return result;
    }

    public void selectTime(View v) {
        String text = mEditText.getText().toString().trim();


        Toast.makeText(this, "点击了按钮", Toast.LENGTH_SHORT).show();
        popupWindow = new TimeSelectPopupWindow(this, dateArr, Arrays.asList(timeArr));
        if(!TextUtils.isEmpty(text)) {
            String[] currentTime = text.split(" ");

            int dateIndex = standardDateArr.indexOf(currentTime[0]);
            currentDatePosition = dateIndex;
            int timeIndex = Arrays.asList(standardtimeArr).indexOf(currentTime[1]);
            popupWindow.setCurrentIndex(dateIndex, timeIndex);
        }

        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDateItemClickerLister(new TimeSelectPopupWindow.OnDateItemClickListener() {
            @Override
            public void setOnDateClickerListener(int position, View v) {
                Toast.makeText(MainActivity.this, "选择了" + dateArr.get(position), Toast.LENGTH_SHORT).show();
                selectedDate = standardDateArr.get(position);
                currentDatePosition = position;
            }
        });
        popupWindow.setOnTimeItemClickerLister(new TimeSelectPopupWindow.OnTimeItemClickListener() {
            @Override
            public void setOnTimeClickerListener(int position, View v, boolean isDateSelect) {
                Toast.makeText(MainActivity.this, "选择了" + timeArr[position], Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                selectedTime = standardtimeArr[position];
                if (!isDateSelect) {
                    selectedDate = standardDateArr.get(currentDatePosition);
                }
                mEditText.setText(selectedDate + " " + selectedTime);
            }
        });
    }
}

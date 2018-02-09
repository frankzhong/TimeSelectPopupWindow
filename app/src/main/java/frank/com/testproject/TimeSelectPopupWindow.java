package frank.com.testproject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongchao on 2018/2/8.
 */

public class TimeSelectPopupWindow extends PopupWindow implements AdapterView.OnItemClickListener{
    private Context context;
    private LayoutInflater inflater;
    private View rootView;
    private ImageView closeImg;
    private ListView dataListView, timeListView;
    private List<String> dateArr, timeArr;
    private OnDateItemClickListener mDateListener;
    private OnTimeItemClickListener mTimeListener;
    private boolean isDateSelected = false;
    private SimpleArrayAdapter dateAdapter, timeAdapter;


    public interface OnDateItemClickListener {
        void setOnDateClickerListener(int position, View v);
    }

    public interface OnTimeItemClickListener {
        void setOnTimeClickerListener(int position, View v, boolean isDateSelected);
    }

    public void setOnDateItemClickerLister(OnDateItemClickListener listener) {
        this.mDateListener = listener;
    }

    public void setOnTimeItemClickerLister(OnTimeItemClickListener listener) {
        this.mTimeListener = listener;
    }

    public TimeSelectPopupWindow(Context context, List<String> dateArr, List<String> timeArr) {
        super(context);
        this.context = context;
        this.dateArr = dateArr;
        this.timeArr = timeArr;
        inflater = LayoutInflater.from(context);
        initPopupWindow();
    }

    private void initPopupWindow() {
        rootView = inflater.inflate(R.layout.time_select_layout, null);
        closeImg = (ImageView) rootView.findViewById(R.id.iv_close);
        dataListView = (ListView) rootView.findViewById(R.id.lv_date);
        dataListView.setOnItemClickListener(this);
        timeListView = (ListView) rootView.findViewById(R.id.lv_time);
        timeListView.setOnItemClickListener(this);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dateAdapter = new SimpleArrayAdapter(context, R.layout.simple_list_item, R.id.item, dateArr);
        dataListView.setAdapter(dateAdapter);
        timeAdapter = new SimpleArrayAdapter(context, R.layout.simple_list_item, R.id.item, timeArr);
        timeListView.setAdapter(timeAdapter);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
//        this.setAnimationStyle(R.style.time_select_anim);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_date:
                if(mDateListener != null)
                    mDateListener.setOnDateClickerListener(position, view);
                isDateSelected = true;

                dateAdapter.setCurrentItemPosition(position);
                dateAdapter.notifyDataSetChanged();
                break;
            case R.id.lv_time:
                if(mTimeListener != null)
                    mTimeListener.setOnTimeClickerListener(position, view, isDateSelected);

                timeAdapter.setCurrentItemPosition(position);
                timeAdapter.notifyDataSetChanged();
                break;
        }

    }


    public void setCurrentIndex(int index, int index1) {
        dateAdapter.setCurrentItemPosition(index);
        dateAdapter.notifyDataSetChanged();
        timeAdapter.setCurrentItemPosition(index1);
        timeAdapter.notifyDataSetChanged();
    }
}

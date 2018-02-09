package frank.com.testproject;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhongchao on 2018/2/9.
 */

public class SimpleArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resouceId;
    private int textResouceId;
    private List<String> data;
    private LayoutInflater mInflater;
    private int currentItemPosition = 0;


    public SimpleArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resouceId = resource;
        this.textResouceId = textViewResourceId;
        this.data = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(resouceId, parent, false);
        }
        String text = data.get(position);
        TextView textView = ((TextView)convertView.findViewById(textResouceId));
        textView.setText(text);
        if (position == currentItemPosition) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.white));
            textView.setTextColor(Color.RED);
        }else {
            textView.setBackgroundColor(context.getResources().getColor(R.color.whitesmoke));
            textView.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    public void setCurrentItemPosition(int pos){
        currentItemPosition = pos;
    }
}

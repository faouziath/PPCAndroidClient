package client;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.fy.ppc.R;

import java.util.List;

/**
 * Created by fy on 2015-12-19.
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> texts;

    public MyAdapter(Context c, List<String> texts) {
        mContext = c;
        this.texts = texts;
    }

    public int getCount() {
        return texts.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            textView.setPadding(5, 5, 5, 5);
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(0x999999); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(1, 0xFF000000);
            textView.setBackground(gd);
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }
        System.out.println(texts.get(position));
        textView.setText(texts.get(position));
        return textView;
    }

}
package com.wodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;

public class ScoreAdapter extends BaseAdapter {
    String[] arrFont;
    private Context mContext;
    private LayoutInflater mInflater;

    public ScoreAdapter(Context mcontext) {
        mContext = mcontext;
        mInflater = LayoutInflater.from(mcontext);
        arrFont = mContext.getResources().getStringArray(R.array.score_arrs);
    }

    @Override
    public int getCount() {
        return arrFont.length;
    }

    @Override
    public Object getItem(int position) {
        return arrFont[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_list_score, parent, false);
            holder.tvfont = (TextView) convertView.findViewById(R.id.item_font);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvfont.setText(arrFont[position]);
        LakRun.setFontTV(mContext, holder.tvfont, LakConst.FONT_HEV_REGULAR);
        return convertView;
    }

    class ViewHolder {
        TextView tvfont;
    }
}

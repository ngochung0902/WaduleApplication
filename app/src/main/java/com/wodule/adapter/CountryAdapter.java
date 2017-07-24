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

public class CountryAdapter extends BaseAdapter {
    String[] arrContent;
    private Context mContext;
    private LayoutInflater mInflater;

    public CountryAdapter(Context mcontext, String[] arrContent) {
        this.mContext = mcontext;
        this.mInflater = LayoutInflater.from(mcontext);
        this.arrContent = arrContent;
    }

    @Override
    public int getCount() {
        return arrContent.length;
    }

    @Override
    public Object getItem(int position) {
        return arrContent[position];
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
            convertView = mInflater.inflate(R.layout.row_country_list, parent, false);
            holder.textCountry = (TextView) convertView.findViewById(R.id.textCountry);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textCountry.setText(arrContent[position]);
        LakRun.setFontTV(mContext, holder.textCountry, LakConst.FONT_HEV_REGULAR);
        return convertView;
    }

    class ViewHolder {
        TextView textCountry;
    }
}

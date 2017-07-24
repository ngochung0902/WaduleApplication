package com.wodule.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.wodule.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SectionedAdapterCustom extends SectionedBaseAdapterCustom {

    private final Context mContext;
    private LayoutInflater mInflater;

    public SectionedAdapterCustom(Context mcontext) {
        mContext = mcontext;
        mInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public Object getItem(int section, int position) {
        return position;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getSectionCount() {
        return 10;
    }

    @Override
    public int getCountForSection(int section) {
        return section;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.row_item_history, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder holder ;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.row_header_history, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class HeaderViewHolder {
        TextView titleHeaader;
        TextView dayofMont;
    }

    class ViewHolder {
        TextView titleEvent;
        TextView time;
        TextView address;
        ImageView ivItem ;
    }
//    public void setData(ArrayList<CameraProfile> arrList, ArrayList<String> headerList, List<ProfileMapping> listProfile) {
//        this.arrList = arrList;
//        this.headerList = headerList;
//        this.listProfile = listProfile;
//        this.notifyDataSetChanged();
//    }
    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
    private String checkDate(String sDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Log.e("date", sDate);
        Date newDate = null;
        String date = "";
        try {
            newDate = sdf.parse(sDate);
            sdf = new SimpleDateFormat("EEE, MM-dd-yyyy");
            date = sdf.format(newDate);
            String arr_date[] = date.split("-");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    private String getTimeofDay(String sdate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date newDate = null;
        String date = "";
        try {
            newDate = sdf.parse(sdate);
            String arr_date[] = date.split("-");

            sdf = new SimpleDateFormat("hh:mm a");
            date = sdf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    private String getDayofMonth(String sdate){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM-dd-yyyy");

        Date newDate = null;
        String date = "";
        try {
            newDate = sdf.parse(sdate);
            String arr_date[] = date.split("-");

            sdf = new SimpleDateFormat("dd");
            date = sdf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    private String getDayofWeek(String sdate){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM-dd-yyyy");

        Date newDate = null;
        String date = "";
        try {
            newDate = sdf.parse(sdate);
            String arr_date[] = date.split("-");

            sdf = new SimpleDateFormat("EEE");
            date = sdf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

package com.wodule.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wodule.R;
import com.wodule.object.ExamObject;
import com.wodule.object.UserObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseTFragment extends Fragment {
    protected ProgressDialog pDialog;
    //    protected HomeActivity mActivity;
    protected static File audiofile1 = null;
    protected static File audiofile2 = null;
    protected static File audiofile3 = null;
    protected static File audiofile4 = null;
    protected static UserObject newUser = null;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        try {
//            mActivity = (HomeActivity) context;
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");


//        } catch (ClassCastException e) {
//            Log.e("MainActivity","Fragment is not attached from MainActivity class.");
//        }
    }

    protected void back() {
        this.getActivity().getSupportFragmentManager().popBackStack();
    }

    public abstract String getFragmentTitle();

    public void startNewScreen(BaseTFragment current, BaseTFragment newFragment) {
        current.onPause();
        FragmentManager fm = this.getActivity().getSupportFragmentManager();
        FragmentTransaction fs = fm.beginTransaction();
        fs.add(R.id.fragmentHolder, newFragment);
        fs.hide(current);
        fs.addToBackStack(null);
        fs.commit();
    }

    public abstract void onBackPressed();

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getActivity().getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 1280) ? (originalSize / 1280) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = getActivity().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    public static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }


}

package com.wodule.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.custom.CropImageView;
import com.wodule.fragment.ProfileFragment3;
import com.wodule.fragment.ProfileFragment4;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by TuanQTS on 06/28/2017.
 */
public class ChooseCropAct extends BaseTFragment {
    private ImageView ivBack;
    private TextView btnSubmit, lbTitle;
    private CropImageView mCropView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_crop_layout, container, false);
        lbTitle = (TextView) view.findViewById(R.id.lbTitle);
        mCropView = (CropImageView) view.findViewById(R.id.cropImageView);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        btnSubmit = (TextView) view.findViewById(R.id.btnSubmit);
        if (LakRun.getIsEdit(getActivity())) {
            lbTitle.setText("EDIT USER");
            if (LakConst.bmAvatar != null){
                mCropView.setImageBitmap(LakConst.bmAvatar);
                mCropView.setCropMode(CropImageView.CropMode.RATIO_1_1);
//                LakConst.bmAvatar = mCropView.getCroppedBitmap();
//                LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
            }else {
                if (HomeActivity.userObj.getPicture() != null) {
                    Glide.with(getActivity()).load("http://wodule.io/" + String.valueOf(HomeActivity.userObj.getPicture()))
                            .asBitmap()
                            .fitCenter()
                            .signature(new StringSignature(UUID.randomUUID().toString()))
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mCropView);
                    mCropView.setCropMode(CropImageView.CropMode.RATIO_1_1);
//                    LakConst.bmAvatar = mCropView.getCroppedBitmap();
//                    LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
                }else LakConst.bmAvatar = null;
            }
        } else {
            if (LakConst.bmAvatar != null){
                mCropView.setImageBitmap(LakConst.bmAvatar);
                mCropView.setCropMode(CropImageView.CropMode.RATIO_1_1);
//                LakConst.bmAvatar = mCropView.getCroppedBitmap();
//                LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");

            }
        }
        FontUtils.setFont(btnSubmit);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LakRun.getIsEdit(getActivity())) {
                    pDialog.show();
                    if (mCropView.getCroppedBitmap() != null){
                        LakConst.bmAvatar = mCropView.getCroppedBitmap();
                        LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
                        mCropView.setImageBitmap(LakConst.bmAvatar);
                        mCropView.setCropEnabled(false);
                    }
                    getUpdateApi();
                } else {
                    if (LakConst.bmAvatar != null){
                        LakConst.bmAvatar = mCropView.getCroppedBitmap();
                        LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
                    }
                    startNewScreen(ChooseCropAct.this, new ProfileFragment4());
                }

            }
        });
        return view;
    }


    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {

    }
    private File getFiles(Bitmap bitmap, String file_name) {
        File file = null;
        try {
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            file = new File(path, file_name + ".jpg");
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private void getUpdateApi() {
        if (LakConst.pictureFile != null) {
            Log.e("getUpdateApi", "LakConst.picture # null");
            Ion.with(getActivity())
                    .load(ApiRequest.URL_UPDATE)
                    .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getActivity()))
                    .setMultipartFile(ApiRequest.picture, LakConst.pictureFile)
                    .setMultipartParameter("_method", "PATCH")
                    .setMultipartParameter(ApiRequest.firstname, newUser.getFirst_name())
                    .setMultipartParameter(ApiRequest.midname, newUser.getMiddle_name())
                    .setMultipartParameter(ApiRequest.lastname, newUser.getLast_name())
                    .setMultipartParameter(ApiRequest.nativename, newUser.getNative_name())
                    .setMultipartParameter(ApiRequest.city, newUser.getCity())
                    .setMultipartParameter(ApiRequest.country, newUser.getCountry())
                    .setMultipartParameter(ApiRequest.country_of_birth, newUser.getCountry_of_birth())
                    .setMultipartParameter(ApiRequest.date_of_birth, newUser.getDate_of_birth())
                    .setMultipartParameter(ApiRequest.address, newUser.getAddress())
                    .setMultipartParameter(ApiRequest.ethnicity, newUser.getEthnicity())
                    .setMultipartParameter(ApiRequest.gender, newUser.getGender())
                    .setMultipartParameter(ApiRequest.status, newUser.getStatus())
                    .setMultipartParameter(ApiRequest.ln_first, newUser.getIn_first())
                    .setMultipartParameter(ApiRequest.Suffx, newUser.getSuffx())
                    .setMultipartParameter(ApiRequest.telephone, newUser.getTelephone())
                    .setMultipartParameter(ApiRequest.nationality, newUser.getNationality())
                    .setMultipartParameter(ApiRequest.religion, newUser.getReligion())
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (pDialog != null) {
                                pDialog.dismiss();
                                pDialog.cancel();
                            }
                            if (e == null) {
                                Log.e("getActivity()", "result:" + result);
                                LakRun.setIsReload(getActivity(), true);
                                getActivity().finish();
                            }
                        }
                    });
        } else {
            Log.e("getUpdateApi", "LakConst.picture null");
            Ion.with(getActivity())
                    .load(ApiRequest.URL_UPDATE)
                    .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getActivity()))
                    .setMultipartParameter("_method", "PATCH")
                    .setMultipartParameter(ApiRequest.firstname, newUser.getFirst_name())
                    .setMultipartParameter(ApiRequest.midname, newUser.getMiddle_name())
                    .setMultipartParameter(ApiRequest.lastname, newUser.getLast_name())
                    .setMultipartParameter(ApiRequest.nativename, newUser.getNative_name())
                    .setMultipartParameter(ApiRequest.city, newUser.getCity())
                    .setMultipartParameter(ApiRequest.country, newUser.getCountry())
                    .setMultipartParameter(ApiRequest.country_of_birth, newUser.getCountry_of_birth())
                    .setMultipartParameter(ApiRequest.date_of_birth, newUser.getDate_of_birth())
                    .setMultipartParameter(ApiRequest.address, newUser.getAddress())
                    .setMultipartParameter(ApiRequest.ethnicity, newUser.getEthnicity())
                    .setMultipartParameter(ApiRequest.gender, newUser.getGender())
                    .setMultipartParameter(ApiRequest.status, newUser.getStatus())
                    .setMultipartParameter(ApiRequest.ln_first, newUser.getIn_first())
                    .setMultipartParameter(ApiRequest.Suffx, newUser.getSuffx())
                    .setMultipartParameter(ApiRequest.telephone, newUser.getTelephone())
                    .setMultipartParameter(ApiRequest.nationality, newUser.getNationality())
                    .setMultipartParameter(ApiRequest.religion, newUser.getReligion())
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (pDialog != null) {
                                pDialog.dismiss();
                                pDialog.cancel();
                            }
                            if (e == null) {
                                Log.e("getActivity()", "result:" + result);
                                LakRun.setIsReload(getActivity(), true);
                                getActivity().finish();
                            }
                        }
                    });
        }
    }
}

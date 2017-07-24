package com.wodule.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.activities.AssessorActivity;
import com.wodule.activities.HomeActivity;
import com.wodule.activities.MainAssessmentActivity;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.object.UserObject;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 5/9/2017.
 */

public class ProfileFragment4 extends BaseTFragment {
    private ImageView ivBack, ivAvatar;
    private TextView btnSubmit, btnReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile4, container, false);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        btnSubmit = (TextView) view.findViewById(R.id.btnSubmit);
        btnReset = (TextView) view.findViewById(R.id.btnReset);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newUser != null) {
                    pDialog.show();
                    getRegisterApi();
                }
            }

        });
        if (LakConst.bmAvatar != null) {
            ivAvatar.setImageBitmap(LakRun.getRoundedCornerBitmap(LakConst.bmAvatar, 15));
        } else {
            if (LakRun.getIsEdit(getActivity())) {
                setProfile();
            }
        }
        ivAvatar.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont(btnReset);
        FontUtils.setFont(btnSubmit);
        FontUtils.setFont((TextView) view.findViewById(R.id.lbTitle));
        return view;
    }

    private void setProfile() {
        if (HomeActivity.userObj.getPicture() != null) {
            Glide.with(getActivity()).load("http://wodule.io/" + String.valueOf(HomeActivity.userObj.getPicture()))
                    .asBitmap()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivAvatar);
        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {

    }
    private void getRegisterApi(){
        Ion.with(getActivity())
                .load(ApiRequest.URL_REGISTER)
                .setMultipartFile(ApiRequest.picture, LakConst.pictureFile)
                .setMultipartParameter(ApiRequest.firstname, newUser.getFirst_name())
                .setMultipartParameter(ApiRequest.midname, newUser.getMiddle_name())
                .setMultipartParameter(ApiRequest.lastname, newUser.getLast_name())
                .setMultipartParameter(ApiRequest.nativename, newUser.getNative_name())
                .setMultipartParameter(ApiRequest.username, newUser.getUser_name())
                .setMultipartParameter(ApiRequest.city, newUser.getCity())
                .setMultipartParameter(ApiRequest.country, newUser.getCountry())
                .setMultipartParameter(ApiRequest.country_of_birth, newUser.getCountry_of_birth())
                .setMultipartParameter(ApiRequest.date_of_birth, newUser.getDate_of_birth())
                .setMultipartParameter(ApiRequest.address, newUser.getAddress())
                .setMultipartParameter(ApiRequest.ethnicity, newUser.getEthnicity())
                .setMultipartParameter(ApiRequest.gender, newUser.getGender())
                .setMultipartParameter(ApiRequest.status, newUser.getStatus())
                .setMultipartParameter(ApiRequest.ln_first, newUser.getIn_first())
                .setMultipartParameter(ApiRequest.code, newUser.getCode())
                .setMultipartParameter(ApiRequest.email, newUser.getEmail())
                .setMultipartParameter(ApiRequest.Suffx, newUser.getSuffx())
                .setMultipartParameter(ApiRequest.password, newUser.getPassword())
                .setMultipartParameter(ApiRequest.telephone, newUser.getTelephone())
                .setMultipartParameter(ApiRequest.nationality, newUser.getNationality())
                .setMultipartParameter(ApiRequest.religion, newUser.getReligion())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            Log.e("getActivity()Register", result);
                            if (result.contains("token")){
                                try {
                                    JSONObject json = new JSONObject(result);
                                    LakRun.setAccessToken(getActivity(), String.valueOf(json.get("token").toString()));
                                    LakRun.setIsLogin(getActivity(), true);
                                    LakRun.setIsFirstLogin(getActivity(), false);
                                    getProfile(String.valueOf(json.get("token").toString()));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }else {
                                if (pDialog != null) {
                                    pDialog.dismiss();
                                    pDialog.cancel();
                                }
                                LakRun.showToast(getActivity(), result);
                            }
                        }else {
                            if (pDialog != null) {
                                pDialog.dismiss();
                                pDialog.cancel();
                            }
                        }
                    }
                });
    }
    private void getProfile(String token) {
        Ion.with(getActivity())
                .load(ApiRequest.URL_GETPROFILE)
                .setHeader("Authorization", "Bearer " + token)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (pDialog != null) {
                            pDialog.dismiss();
                            pDialog.cancel();
                        }
                        if (e == null) {
                            Log.e("LoginActivity", "Profiles:" + result);
                            Gson gson = new Gson();
                            UserObject userObj = new UserObject();
                            try {
                                JSONObject json = new JSONObject(result);
                                LakRun.setUserObject(getActivity(), json.getJSONObject("user").toString());
                                userObj = gson.fromJson(json.getJSONObject("user").toString(), UserObject.class);
                                    LakRun.setIsLogin(getActivity(), true);
                                    if (userObj.getType().equalsIgnoreCase("Examinee")) {
                                        LakRun.setIsStudent(getActivity(), true);
                                        Intent intent = new Intent(getActivity(),
                                                MainAssessmentActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        LakRun.setIsStudent(getActivity(), false);
                                        Intent intent = new Intent(getActivity(),
                                                AssessorActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }
}

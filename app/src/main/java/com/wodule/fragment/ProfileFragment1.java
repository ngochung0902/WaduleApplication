package com.wodule.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.activities.HomeActivity;
import com.wodule.adapter.CountryAdapter;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.object.UserObject;
import com.wodule.utils.FontUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 5/9/2017.
 */

public class ProfileFragment1 extends BaseTFragment {
    private ImageView ivNext;
    private ImageView ivChecked;
    private TextView lbTitle, edDate, edCountry;
    private EditText edFirstName, edMiddleName, edLastName, edNativeName, edSuffx;
    boolean isCheck = false;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile1, container, false);
        lbTitle = (TextView) view.findViewById(R.id.lbTitle);
        ivNext = (ImageView) view.findViewById(R.id.ivNext);
        edFirstName = (EditText) view.findViewById(R.id.edFirstName);
        edMiddleName = (EditText) view.findViewById(R.id.edMiddleName);
        edLastName = (EditText) view.findViewById(R.id.edLastName);
        edNativeName = (EditText) view.findViewById(R.id.edNativeName);
        edSuffx = (EditText) view.findViewById(R.id.edSuffx);
        edCountry = (TextView) view.findViewById(R.id.edCountry);
        ivChecked = (ImageView) view.findViewById(R.id.ivChecked);
        edDate = (TextView) view.findViewById(R.id.edDate);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        newUser = new UserObject();
        setDateTimeField();
        if (LakRun.getIsEdit(getActivity())){
            lbTitle.setText("EDIT USER");
            getProfile();
        }
        setOnListener();
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont((LinearLayout) view.findViewById(R.id.ll_group_center));
        FontUtils.setFont((TextView)view.findViewById(R.id.lbCheckbox));
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbHeader));
        FontUtils.setFont((TextView)view.findViewById(R.id.lbTitle));
        return view;
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void getProfile(){
        edFirstName.setText(String.valueOf(HomeActivity.userObj.getFirst_name()));
        edLastName.setText(String.valueOf(HomeActivity.userObj.getLast_name()));
        edMiddleName.setText(String.valueOf(HomeActivity.userObj.getMiddle_name()));
        edNativeName.setText(String.valueOf(HomeActivity.userObj.getNative_name()));
        edCountry.setText(String.valueOf(HomeActivity.userObj.getCountry_of_birth()));
        edDate.setText(String.valueOf(HomeActivity.userObj.getDate_of_birth()));
        edSuffx.setText(String.valueOf(HomeActivity.userObj.getSuffx()));
    }
    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {

    }

    private void setOnListener(){
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid().equalsIgnoreCase("isOk")) {
                    setProfiles();
                    startNewScreen(ProfileFragment1.this, new ProfileFragment2());
                }
                else LakRun.ShowpopupMessage(getActivity(),checkValid());
            }
        });
        ivChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck){
                    isCheck = !isCheck;
                    ivChecked.setImageResource(R.drawable.ic_tick);
                }else {
                    isCheck = !isCheck;
                    ivChecked.setImageResource(R.drawable.ic_ticked);
                }
            }
        });

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        edCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCountry();
            }
        });
    }
    private String checkValid(){
        if (edFirstName.getText().toString().trim().length() == 0){
            return getString(R.string.check_fname);
        }
        if (edMiddleName.getText().toString().trim().length() == 0){
            return getString(R.string.check_mname);
        }
        if (edLastName.getText().toString().trim().length() == 0){
            return getString(R.string.check_lname);
        }
        if (edNativeName.getText().toString().trim().length() == 0){
            return getString(R.string.check_nativename);
        }
        if (edSuffx.getText().toString().length() == 0){
            return getString(R.string.check_suffx);
        }
        if (edDate.getText().toString().trim().length() == 0){
            return getString(R.string.check_dateofbirth);
        }
        if (edCountry.getText().toString().trim().length() == 0){
            return getString(R.string.check_country_of_birth);
        }

        return "isOk";
    }
    private void setProfiles(){
        newUser.setFirst_name(edFirstName.getText().toString());
        newUser.setMiddle_name(edMiddleName.getText().toString());
        newUser.setLast_name(edLastName.getText().toString());
        newUser.setNative_name(edNativeName.getText().toString());
        newUser.setSuffx(edSuffx.getText().toString());
        newUser.setDate_of_birth(LakRun.formatDatetime1(edDate.getText().toString()));
        newUser.setCountry_of_birth(edCountry.getText().toString());
        if (isCheck)
            newUser.setIn_first("1");
        else newUser.setIn_first("0");
    }
    private void dialogCountry(){
        final Dialog dialog_font = new Dialog(getActivity());
        dialog_font.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_font.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_font.setTitle("SELECT COUNTRY");
        dialog_font.setContentView(R.layout.layout_list_country);
        ListView lv = (ListView)dialog_font.findViewById(R.id.listCountry);
        CountryAdapter adapter = new CountryAdapter(getActivity(), getResources().getStringArray(R.array.country_arrs));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edCountry.setText(String.valueOf(parent.getItemAtPosition(position)));
                dialog_font.dismiss();
            }
        });
        dialog_font.setCancelable(true);
        dialog_font.show();
    }
}

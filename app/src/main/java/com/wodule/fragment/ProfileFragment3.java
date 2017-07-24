package com.wodule.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.activities.ChooseCropAct;
import com.wodule.activities.HomeActivity;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;
import com.wodule.utils.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Administrator on 5/9/2017.
 */

public class ProfileFragment3 extends BaseTFragment {
    private ImageView ivBack, ivCamera;
    private ImageView ivNext;
    private TextView lbTitle, edStatus, edGender;
    private EditText edReligion, edCode, edUsername, edPassword;
    private int w;
    private String userChoosenTask;
    private final int REQUEST_CAMERA = 0, CAPTURE_PICTURE = 1, SELECT_FILE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile3, container, false);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        ivNext = (ImageView) view.findViewById(R.id.ivNext);
        lbTitle = (TextView) view.findViewById(R.id.lbTitle);

        ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        edStatus = (TextView) view.findViewById(R.id.edStatus);
        edGender = (TextView) view.findViewById(R.id.edGender);
        edReligion = (EditText) view.findViewById(R.id.edReligion);
        edCode = (EditText) view.findViewById(R.id.edCode);
        edUsername = (EditText) view.findViewById(R.id.edUsername);
        edPassword = (EditText) view.findViewById(R.id.edPassword);
        if (LakRun.getIsEdit(getActivity())) {
            lbTitle.setText("EDIT USER");
            getProfile();
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid().equalsIgnoreCase("isOk")) {
                    setProfiles();
                    if (newUser != null) {
                        startNewScreen(ProfileFragment3.this, new ChooseCropAct());
                    }
                } else LakRun.ShowpopupMessage(getActivity(), checkValid());
            }
        });
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        if (LakRun.getIsEdit(getActivity())) {
            edCode.setVisibility(View.GONE);
        } else {
            edCode.setVisibility(View.VISIBLE);
        }
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont((LinearLayout) view.findViewById(R.id.ll_group_center));
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView) view.findViewById(R.id.lbTitle));


        edGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGender();
            }
        });
        edStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStatus();
            }
        });
        return view;
    }

    private void getProfile() {
        edStatus.setText(String.valueOf(HomeActivity.userObj.getStatus()));
        edGender.setText(String.valueOf(HomeActivity.userObj.getGender()));
        edReligion.setText(String.valueOf(HomeActivity.userObj.getReligion()));
        edUsername.setText(String.valueOf(HomeActivity.userObj.getUser_name()));
//        edPassword.setText(String.valueOf(HomeActivity.userObj.getPassword()));
        edUsername.setEnabled(false);
        edPassword.setEnabled(false);
        if (HomeActivity.userObj.getPicture() != null) {
            Glide.with(getActivity()).load("http://wodule.io/" + String.valueOf(HomeActivity.userObj.getPicture()))
                    .asBitmap()
                    .fitCenter()
                    .signature(new StringSignature(UUID.randomUUID().toString()))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivCamera);
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {

    }

    private void selectImage() {
        final CharSequence[] items = {"Capture photo", "Choose photo from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Select a picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Capture photo")) {
                    userChoosenTask = "Capture photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose photo from Gallery")) {
                    userChoosenTask = "Choose photo from Gallery";
                    if (result)
                        galleryIntent();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Capture photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose photo from Gallery"))
                        galleryIntent();
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission for the app in Settings", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void cameraIntent() {
        Log.e("MainActivity", "Showing camera");
        try {
            if (Build.VERSION.SDK_INT < 30) {
                Uri imageUri = Uri.fromFile(getTempFile(getActivity()));
                Intent intent = createIntentForCamera(imageUri);
                startActivityForResult(intent, CAPTURE_PICTURE);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                this.startActivityForResult(intent, REQUEST_CAMERA);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Please grant camera permission for the app in Settings", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "" + e.toString());
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA},
                    2);
        }
    }

    private File getTempFile(Context context) {
        String fileName = "temp_hva_photo.jpg";
        File path = new File(Environment.getExternalStorageDirectory(),
                context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, fileName);
    }

    private Intent createIntentForCamera(Uri imageUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri uri = data.getData();
                setPhoto(3, uri);
                Log.e("MainActivity", "SELECT_FILE:" + data.getData().toString());
            } else if (requestCode == REQUEST_CAMERA) {
                Uri uri = data.getData();
                setPhoto(1, uri);
                Log.e("MainActivity", "REQUEST_CAMERA:" + data.getData().toString());
            } else if (requestCode == CAPTURE_PICTURE) {
                Uri uri = Uri.fromFile(getTempFile(getActivity()));
                setPhoto(2, uri);
                Log.e("MainActivity", "CAPTURE_PICTURE:" + uri.toString());
            }
        }
    }

    private void setPhoto(int is_scase, Uri imageUri) {
        if (LakConst.bmAvatar != null) {
            LakConst.bmAvatar.recycle();
            LakConst.bmAvatar = null;
        }
        if (is_scase == 1) {
            try {
                LakConst.bmAvatar = getThumbnail(imageUri);
                int rotation = LakRun.checkRotation(imageUri);
                if (rotation != 0) {
                    try {
                        LakConst.bmAvatar = LakRun.scaleDown(LakRun.rotateImage(LakConst.bmAvatar, rotation), true);
                    } catch (Exception e) {
                        LakConst.bmAvatar = LakRun.scaleDown(LakConst.bmAvatar, true);
                    }
                } else {
                    LakConst.bmAvatar = LakRun.scaleDown(LakConst.bmAvatar, true);
                }
                ivCamera.setImageBitmap(LakConst.bmAvatar);
                ivCamera.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (is_scase == 2) {
            try {
                LakConst.bmAvatar = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                int rotation = LakRun.checkRotation(imageUri);
                if (rotation != 0) {
                    try {
                        LakConst.bmAvatar = LakRun.scaleDown(LakRun.rotateImage(LakConst.bmAvatar, rotation), true);
                    } catch (Exception e) {
                        LakConst.bmAvatar = LakRun.scaleDown(LakConst.bmAvatar, true);
                    }
                } else {
                    LakConst.bmAvatar = LakRun.scaleDown(LakConst.bmAvatar, true);
                }
                ivCamera.setImageBitmap(LakConst.bmAvatar);
                ivCamera.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (is_scase == 3) {
            try {
                LakConst.bmAvatar = LakRun.scaleImage(getActivity(), imageUri);// MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                int rotation = LakRun.checkRotation(imageUri);
                Log.e("PictureFragment", "rotation:" + rotation);
                if (rotation != 0) {
                    try {
                        LakConst.bmAvatar = LakRun.rotateImage(LakConst.bmAvatar, rotation);
                    } catch (Exception e) {
                        LakConst.bmAvatar = LakRun.scaleDown(LakConst.bmAvatar, true);
                    }
                } else {
                    LakConst.bmAvatar = LakRun.scaleDown(LakConst.bmAvatar, true);
                }
                ivCamera.setImageBitmap(LakConst.bmAvatar);
                ivCamera.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LakConst.pictureFile = getFiles(LakConst.bmAvatar, "avaWodule");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

    private String checkValid() {
        if (edStatus.getText().toString().trim().length() == 0) {
            return getString(R.string.check_status);
        }
        if (edReligion.getText().toString().trim().length() == 0) {
            return getString(R.string.check_religion);
        }
        if (edGender.getText().toString().trim().length() == 0) {
            return getString(R.string.check_gender);
        }

        if (!LakRun.getIsEdit(getActivity())) {
            if (edUsername.getText().toString().trim().length() == 0) {
                return getString(R.string.check_username);
            }
            if (edPassword.getText().toString().trim().length() == 0) {
                return getString(R.string.check_password);
            }
            if (LakConst.bmAvatar == null) {
                return getString(R.string.check_Picture);
            }
            if (edCode.getText().toString().trim().length() == 0) {
                return getString(R.string.check_code);
            }
        }
        return "isOk";
    }

    private void setProfiles() {
        newUser.setStatus(edStatus.getText().toString());
        newUser.setReligion(edReligion.getText().toString());
        newUser.setGender(edGender.getText().toString());
        newUser.setUser_name(edUsername.getText().toString());
        if (!LakRun.getIsEdit(getActivity())){
            newUser.setPassword(edPassword.getText().toString());
            newUser.setCode(edCode.getText().toString());
        }
    }

    private void selectStatus() {
        final CharSequence[] itemStatus = {"Single", "Married"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SELECT STATUS");
        builder.setItems(itemStatus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                edStatus.setText(itemStatus[item]);
            }
        });
        builder.show();
    }

    private void selectGender() {
        final CharSequence[] itemGender = {"Male", "Female"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SELECT GENDER");
        builder.setItems(itemGender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                edGender.setText(itemGender[item]);
            }
        });
        builder.show();
    }

}

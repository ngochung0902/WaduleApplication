package com.wodule.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.steelkiwi.instagramhelper.InstagramHelper;
import com.steelkiwi.instagramhelper.InstagramHelperConstants;
import com.steelkiwi.instagramhelper.model.InstagramUser;
import com.wodule.R;
import com.wodule.WoduleApplication;
import com.wodule.adapter.CountryAdapter;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.object.ResponCode;
import com.wodule.object.UserObject;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 007;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edUserName, edPassword;
    private ProgressDialog pDialog;
    private String facebook_access_token = "";
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private ImageView iconFacebook, iconGPlus, iconInstagram;
    //    private SignInButton btnSignIn;
    private InstagramHelper instagramHelper;
    GoogleApiClient mGoogleApiClient;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        instagramHelper = WoduleApplication.getInstagramHelper();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassword);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        iconFacebook = (ImageView) findViewById(R.id.iconFacebook);
        iconGPlus = (ImageView) findViewById(R.id.iconGPlus);
        iconInstagram = (ImageView) findViewById(R.id.iconInstagram);
//        btnSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        setProperties();


        LakRun.setIsAssessment(getApplicationContext(), false);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edUserName.getText().toString().trim().length() > 0) {
                    if (edPassword.getText().toString().trim().length() > 0) {
                        if (LakRun.isNetworkAvailable(getApplicationContext())){
//                            showProgressDialog();
                            mProgressDialog = new ProgressDialog(LoginActivity.this);
                            mProgressDialog.setMessage("Loading...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.show();
                            Ion.with(getApplicationContext())
                                    .load(ApiRequest.URL_LOGIN)
                                    .setBodyParameter(ApiRequest.username, edUserName.getText().toString())
                                    .setBodyParameter(ApiRequest.password, edPassword.getText().toString())
                                    .setBodyParameter(ApiRequest.social, "false")
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
//                                        hideProgressDialog();
                                            if (e == null) {
                                                if (result.toString().contains("token")) {
                                                    Log.e("LoginActivity"," token:" + String.valueOf(result.get("token").toString()));
                                                    LakRun.setAccessToken(getApplicationContext(), String.valueOf(result.get("token").toString()));
                                                    LakRun.setIsLogin(getApplicationContext(), true);
                                                    LakRun.setIsFirstLogin(getApplicationContext(), false);
                                                    getProfile();
                                                } else {
                                                    hideProgressDialog();
                                                    LakRun.ShowpopupMessage(LoginActivity.this, String.valueOf(result.get("error").toString()));
                                                }
                                            }else hideProgressDialog();
                                        }
                                    });
                        }else
                            LakRun.ShowpopupMessage(LoginActivity.this, getString(R.string.check_network));
                    } else
                        LakRun.ShowpopupMessage(LoginActivity.this, getString(R.string.check_password));
                } else
                    LakRun.ShowpopupMessage(LoginActivity.this, getString(R.string.check_username));

            }
        });
        TextView tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        ForgetPassActivity.class);
                startActivity(intent);
            }
        });
        TextView tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LakRun.setIsEdit(getApplicationContext(), false);
                Intent intent = new Intent(LoginActivity.this,
                        HomeActivity.class);
                intent.putExtra("isnewUser", true);
                startActivity(intent);
            }
        });
        TextView tvLicense = (TextView) findViewById(R.id.tvLicense);
        tvLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        LicenseActivity.class);
                startActivity(intent);
            }
        });
        iconFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeFacebook();
            }
        });
        iconInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagramHelper.loginFromActivity(LoginActivity.this);
            }
        });
        iconGPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void setProperties() {
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFonts((LinearLayout) findViewById(R.id.ll_group_bottom));
        FontUtils.setFonts((LinearLayout) findViewById(R.id.ll_group_center));
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView) findViewById(R.id.lbSignin));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == InstagramHelperConstants.INSTA_LOGIN) {
                InstagramUser user = instagramHelper.getInstagramUser(this);
//                LakRun.showToast(getApplicationContext(), String.valueOf(user.getData().getFullName()));
                Log.e("LoginActivity", "user instagram: id=" + user.getData().getId() +", full name =="+user.getData().getFullName());
                Ion.with(getApplicationContext())
                        .load(ApiRequest.URL_LOGIN)
                        .setBodyParameter(ApiRequest.username, "u02" + user.getData().getId())
                        .setBodyParameter(ApiRequest.password, "instagram")
                        .setBodyParameter(ApiRequest.social, "true")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
//                                hideProgressDialog();
                                if (e == null) {
                                    Log.e("LoginActivity", "user instagram: token" + result);
                                    if (result.toString().contains("token")) {
                                        LakRun.setAccessToken(getApplicationContext(), String.valueOf(result.get("token").toString()));
                                        LakRun.setIsFirstLogin(getApplicationContext(), result.get("first").getAsBoolean());
                                        getProfile();
                                    } else {
                                        hideProgressDialog();
                                        LakRun.ShowpopupMessage(LoginActivity.this, String.valueOf(result.get("error").toString()));
                                    }
                                }else hideProgressDialog();
                            }
                        });

            } else if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            } else {
//                Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
            }
        } else {

        }
    }

    LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    private void initializeFacebook() {
        loginManager = getLoginManager();
        loginManager.logInWithReadPermissions(this,
                Arrays.asList("public_profile", "email"));

//        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult result) {
                        Log.e("Login.this", "Login.initializeFacebook.onSuccess Granted Permissions= " + result.getRecentlyGrantedPermissions().toString());
                        Log.e("Login.this", "result.getAccessToken():" + result.getAccessToken().getToken());
                        facebook_access_token = result.getAccessToken().getToken();
                        GraphRequest request = GraphRequest.newMeRequest(result.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                        } else {
                                            LakRun.setIsFBLogin(getApplicationContext(), true);
                                            fbLogin(object, facebook_access_token);
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("Login.this", "Login.initializeFacebook.onError " + error.getMessage());
                        if (error instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.e("Login Facebook", "Login attempt canceled.");
                    }
                });
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void fbLogin(JSONObject object, String facebook_access_token) {
//        HashMap<String, String> keyValues = new HashMap<String, String>();
//        showProgressDialog();
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Log.e("Login.this", "Login.initializeFacebook Object= " + object.toString());
        String email = object.optString("email");
        String id = object.optString("id");
        String[] name = object.optString("name").split(" ");
        String firstName = name != null ? name[0] : "";
        String lastName = (name != null && name.length > 1) ? name[1] : "";

        Log.e("Login.this", "Login.fbLogin Email= " + email);
        Log.e("Login.this", "Login.fbLogin Facebook Id= " + id);
        Log.e("Login.this", "Login.fbLogin FirstName= " + firstName);
        Log.e("Login.this", "Login.fbLogin LastName= " + lastName);
        if (email.isEmpty()) {
            hideProgressDialog();
            LakRun.setIsFBLogin(getApplicationContext(), false);
            LakRun.ShowDialogMessage(LoginActivity.this, "Sorry", getResources().getString(R.string.check_login_fb));
        } else {
            Ion.with(getApplicationContext())
                    .load(ApiRequest.URL_LOGIN)
                    .setBodyParameter(ApiRequest.username, "u01" + id)
                    .setBodyParameter(ApiRequest.password, "facebook")
                    .setBodyParameter(ApiRequest.social, "true")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
//                            hideProgressDialog();
                            if (e == null) {
                                if (result.toString().contains("token")) {
                                    Log.e("FBLogin" , "token:"+ String.valueOf(result.get("token").toString()));
                                    LakRun.setAccessToken(getApplicationContext(), String.valueOf(result.get("token").toString()));
                                    LakRun.setIsFirstLogin(getApplicationContext(), result.get("first").getAsBoolean());
                                    getProfile();
                                } else {
                                    hideProgressDialog();
                                    LakRun.ShowpopupMessage(LoginActivity.this, String.valueOf(result.get("error").toString()));
                                }
                            }else hideProgressDialog();
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
//            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(true);
//        }else
//            mProgressDialog.show();
//    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

//                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display profile: " + acct.getId());
            Log.e(TAG, "display name: " + acct.getDisplayName());
            Ion.with(getApplicationContext())
                    .load(ApiRequest.URL_LOGIN)
                    .setBodyParameter(ApiRequest.username, "u03" + acct.getId())
                    .setBodyParameter(ApiRequest.password, "google")
                    .setBodyParameter(ApiRequest.social, "true")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
//                            hideProgressDialog();
                            if (e == null) {
                                if (result.toString().contains("token")) {
                                    LakRun.setAccessToken(getApplicationContext(), String.valueOf(result.get("token").toString()));
                                    LakRun.setIsFirstLogin(getApplicationContext(), result.get("first").getAsBoolean());
                                    getProfile();
                                } else {
                                    hideProgressDialog();
                                    LakRun.ShowpopupMessage(LoginActivity.this, String.valueOf(result.get("error").toString()));
                                }
                            }else hideProgressDialog();
                        }
                    });
//            String personName = acct.getDisplayName();
//            String personPhotoUrl = acct.getPhotoUrl().toString();
//            String email = acct.getEmail();
//
//            Log.e(TAG, "Name: " + personName + ", email: " + email
//                    + ", Image: " + personPhotoUrl);

//            txtName.setText(personName);
//            txtEmail.setText(email);
//            Glide.with(getApplicationContext()).load(personPhotoUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);

//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            LakRun.showToast(getApplicationContext(), "Login failed");
//            updateUI(false);
        }
    }

    private void dialogCode() {
        final Dialog dialog_code = new Dialog(LoginActivity.this);
        dialog_code.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_code.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_code.setTitle("INPUT CODE");
        dialog_code.setContentView(R.layout.activity_input_code);

        final EditText edCode = (EditText) dialog_code.findViewById(R.id.edEnterCode);
        Button btnOk = (Button) dialog_code.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog_code.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LakRun.setIsLogin(getApplicationContext(), false);
                dialog_code.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edCode.getText().toString().trim().length() > 0){
                    dialog_code.dismiss();
//                    showProgressDialog();
                    mProgressDialog = new ProgressDialog(LoginActivity.this);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();
                    Ion.with(getApplicationContext())
                            .load("GET", ApiRequest.URL_CODE + edCode.getText().toString())
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    hideProgressDialog();
                                    if (e == null) {
                                        Log.e("InputCode", "result code:"+result);
                                        Gson gson = new Gson();
                                        Type listType = new TypeToken<List<ResponCode>>() {
                                        }.getType();
                                        final List<ResponCode> listCode = (List<ResponCode>) gson.fromJson(result, listType);
                                        if (listCode != null && listCode.size() > 0) {
                                            Ion.with(getApplicationContext())
                                                    .load(ApiRequest.URL_UPDATE)
                                                    .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getApplicationContext()))
                                                    .setBodyParameter(ApiRequest.organization, listCode.get(0).getOrganization())
                                                    .setBodyParameter("student_class", listCode.get(0).getClass_school())
                                                    .setBodyParameter(ApiRequest.adviser, listCode.get(0).getAdviser())
                                                    .setBodyParameter(ApiRequest._type, listCode.get(0).get_type())
                                                    .setBodyParameter("_method", "PATCH")
                                                    .asString()
                                                    .withResponse()
                                                    .setCallback(new FutureCallback<Response<String>>() {
                                                        @Override
                                                        public void onCompleted(Exception e, Response<String> result) {
                                                            hideProgressDialog();
                                                            Log.e("LoginActivity", "result:" + result.getResult());
                                                            if (result.getHeaders().code() == 200) {
                                                                if (result.getResult().equalsIgnoreCase("1")) {
                                                                    LakRun.setIsLogin(getApplicationContext(), true);
                                                                    if (listCode.get(0).get_type().equalsIgnoreCase("Examinee")) {
                                                                        LakRun.setIsStudent(getApplicationContext(), true);
                                                                        Intent intent = new Intent(LoginActivity.this,
                                                                                MainAssessmentActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    } else {
                                                                        LakRun.setIsStudent(getApplicationContext(), false);
                                                                        Intent intent = new Intent(LoginActivity.this,
                                                                                AssessorActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                } else {

                                                                }
                                                            } else {
                                                            }
                                                        }
                                                    });

                                        } else {
                                            LakRun.setIsLogin(getApplicationContext(), false);
                                            LakRun.ShowpopupMessage(LoginActivity.this, "Invalid code");
//                                            dialogCode();
                                        }
                                    }
                                }
                            });
                }else LakRun.showToast(getApplicationContext(), "Please input code!");
            }
        });
        dialog_code.setCancelable(true);
        dialog_code.show();
    }

    private void getProfile() {
        Ion.with(getApplicationContext())
                .load(ApiRequest.URL_GETPROFILE)
                .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getApplicationContext()))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        hideProgressDialog();
                        if (e == null) {
                            Log.e("LoginActivity", "result:" + result);
                            Gson gson = new Gson();
                            UserObject userObj = new UserObject();
                            try {
                                JSONObject json = new JSONObject(result);
                                LakRun.setUserObject(getApplicationContext(), json.getJSONObject("user").toString());
                                userObj = gson.fromJson(json.getJSONObject("user").toString(), UserObject.class);
                                if (userObj.getType() == null) {
                                    dialogCode();
                                } else {
                                    LakRun.setIsLogin(getApplicationContext(), true);
                                    if (userObj.getType().equalsIgnoreCase("Examinee")) {
                                        LakRun.setIsStudent(getApplicationContext(), true);
                                        Intent intent = new Intent(LoginActivity.this,
                                                MainAssessmentActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        LakRun.setIsStudent(getApplicationContext(), false);
                                        Intent intent = new Intent(LoginActivity.this,
                                                AssessorActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

}

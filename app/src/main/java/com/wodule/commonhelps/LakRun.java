/**
 * Project    : Tingle
 * Author     : TuanCuong
 * Date        : Dec 8, 2012
 **/
package com.wodule.commonhelps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TuanCuong
 * 
 */
public class LakRun {
	private static final float MAX_IMAGE_DIMENSION = 1280;
	private static String LOG_TAG = "Wodule.com";
	private static boolean debug = true;
	// Flag
	public final static int FLAG_OFF = 0;
	public final static int FLAG_ON = 1;
	public static void setLayoutView(View view, int width, int height) {
		view.getLayoutParams().width = width;
		view.getLayoutParams().height = height;
	}
	// Show Toast
	@SuppressWarnings("WrongConstant")
	public static void showToast(Context context, String str_message) {
		Toast toast = Toast.makeText(context, str_message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 50);
		toast.setDuration(LakConst.TIME_WAIT);
		toast.show();
	}
	public static void setFontEditText(Context context, EditText tv, String font) {
		try {
			Typeface face = Typeface.createFromAsset(context
					.getAssets(), font);
			tv.setTypeface(face);
		} catch (Exception e) {
			Log.d("ERROR set FONTS", e.getMessage());
		}
	}
	public static void setFontTV(Context context, TextView tv, String font) {
		try {
			Typeface face = Typeface.createFromAsset(context
					.getAssets(), font);
			tv.setTypeface(face);
		} catch (Exception e) {
			Log.d("ERROR set FONTS", e.getMessage());
		}
	}
	
	public static void setFontButton(Context context, Button tv, String font) {
		try {
			Typeface face = Typeface.createFromAsset(context
					.getAssets(), font);
			tv.setTypeface(face);
		} catch (Exception e) {
			Log.d("ERROR set FONTS", e.getMessage());
		}
	}

	// Send E-mail
	public static void sendFeedback(Context context, String emailAddress,
									String title) {
		final Intent emailIntent = new Intent(
				Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);

		emailIntent.putExtra(Intent.EXTRA_TEXT, "");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
		try {
			context.startActivity(Intent.createChooser(emailIntent,
					"Complete action using"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(null, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	// Share via E-mail
	public static void shareViaEmail(Context context, String content,
									 String title) {
		final Intent emailIntent = new Intent(
				Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
		emailIntent.putExtra(Intent.EXTRA_TEXT, content);
		try {
			context.startActivity(Intent.createChooser(emailIntent,
					"Complete action using"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(null, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}
	public static void shareApp(Context context, String text) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		share.putExtra(Intent.EXTRA_SUBJECT, "DaysBuddy");
		share.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(share, "Share"));
	}
	// Share via SMS
	public static void shareViaSMS(Context context, String content) {
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.setData(Uri.parse("sms:"));
		sendIntent.putExtra("sms_body", content);
		context.startActivity(sendIntent);
	}

	// Share via MMS
	public static void sendMMS(Context context, String patch) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setClassName("com.android.mms",
				"com.android.mms.ui.ComposeMessageActivity");
		sendIntent.putExtra("sms_body", "Content");
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(patch));
		sendIntent.setType("image/png");
		context.startActivity(sendIntent);
	}

	// Check INTERNET connection
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Go to web page
	public static void goWebPage(Activity context, String URL) {
		Intent browse = new Intent(Intent.ACTION_VIEW);
		browse.setData(Uri.parse(URL));
		context.startActivity(browse);
	}

	// Go to Google Play store
	public static void goGooglePlayStore(Context context, String packageName) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + packageName));
			context.startActivity(intent);
		} catch (android.content.ActivityNotFoundException anfe) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri
					.parse("http://play.google.com/store/apps/details?id="
							+ packageName));
			context.startActivity(intent);
		}
	}

	// Go to Amazon appstore
	public static void goAmazonAppStore(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p="
				+ packageName));
		context.startActivity(intent);
	}

	// Check status password
	public static boolean Getprotectstatus(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("protect", false);
	}

	public static void Setprotectstatus(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("protect", num);
		editor.commit();
	}

	// Set password
	// /////////////////////////
	public static String Getpass(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getString("pass", "");
	}

	public static void Setpass(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("pass", content);
		editor.commit();
	}

	// //////////////////////////
	public static Bitmap getBitmapfromURL(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL(url))
					.openConnection();
			conn.connect();
			return BitmapFactory.decodeStream(conn.getInputStream());
		} catch (Exception ex) {
			return null;
		}
	}

	// ///////////
	public static void SetRemind(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("remind", num);
		editor.commit();
	}

	public static boolean GetRemind(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("remind", false);
	}
	
	// ///////////set shake or not
	public static void setShake(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("SHAKE", num);
		editor.commit();
	}

	public static boolean getShake(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("SHAKE", false);
	}

	// //////////////////
	public static void SetNorate(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("norate", num);
		editor.commit();
	}

	public static boolean GetNorate(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("norate", false);
	}

	// ///////////////////////////
	public static int Getnum(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getInt("numrate", 0);
	}

	public static void Setnum(Context context, int num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("numrate", num);
		editor.commit();
	}
	///////////////// GCM ID ////////////////
	public static String getRegistrationId(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("RegistrationId", "");
	}
	public static void setRegistrationId(Context context, String RegistrationId){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("RegistrationId", RegistrationId);
		editor.commit();
	}
	///////////////// user ID ////////////////
	public static String getUserId(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("userId", "");
	}
	public static void setUserId(Context context, String userId){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("userId", userId);
		editor.commit();
	}
	///////////////// get background ///////////////////////
	public static int GetBackGr(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getInt("newbg", 1);
	}

	public static void SetBackGr(Context context, int newbg) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("newbg", newbg);
		editor.commit();
	}
	// /////////////////////////// Height device
	public static int GetHeightDevice(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getInt("height_device", 800);
	}

	public static void SetHeightDevice(Context context, int num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("height_device", num);
		editor.commit();
	}

	// /////////////////////////// Width device
	public static int GetWidthDevice(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getInt("width_device", 480);
	}

	public static void SetWidthDevice(Context context, int num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("width_device", num);
		editor.commit();
	}

	// ///////////////////////////If this is the first time the app open
	public static void SetIsFirstTimeOpenApp(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("first_time_open", num);
		editor.commit();
	}

	public static boolean GetIsFirstTimeOpenApp(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("first_time_open", true);
	}

	// ///////////////////////////If upgrade the app to FULL version
	public static void setIsPurchasedFullVersion(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("is_purchased_full_version", num);
		editor.commit();
	}

	public static boolean getIsPurchasedFullVersion(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("is_purchased_full_version", false);
	}

	// ////////////////////////////////////////////////
	// Set purchase message err
	// /////////////////////////
	public static String GetErrorMsg(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getString("purchaseerr", "");
	}

	public static void SetErrorMsg(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("purchaseerr", content);
		editor.commit();
	}
	/////////////////// check notification ///////////////////
	public static boolean getNotifMsg(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("is_msgnew", false);
	}

	public static void setNotifMsg(Context context, Boolean status) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("is_msgnew", status);
		editor.commit();
	}
	//////////////////check email /////////////////
	public static boolean checkEmailCorrect(String Email) {
        String pttn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(Email);

        if (m.matches()) {
               return true;
        }
        return false;
 }
	////////////get Date ///////////////
	public static Date getDate(String dt) {
		try  {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.parse(dt);
		} catch (Exception e) {
			return new Date();
		}
	}
	
	public static String getDate(Date dt) {
		try  {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.format(dt);
		} catch (Exception e) {
			return "";
		}
	}
	////////////////////save form information ///////////////


	public static boolean getIsFirstLogin(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("IsFirstLogin", false);
	}

	public static void setIsFirstLogin(Context context, boolean IsFirstLogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("IsFirstLogin", IsFirstLogin);
		editor.commit();
	}
		public static boolean getIsRegister(Context context) {
			int mode = Activity.MODE_PRIVATE;
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					LakConst.PREFERENCES, mode);
			return sharedPreferences.getBoolean("isRegister", false);
		}

		public static void setIsRegister(Context context, boolean isRegister) {
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					LakConst.PREFERENCES, 0);
			Editor editor = sharedPreferences.edit();
			editor.putBoolean("isRegister", isRegister);
			editor.commit();
		}
	// ////////////purchase sync calendar events/////////////
	public static boolean getIsCalendarEvent(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isCalendarEvent", false);
	}

	public static void setIsCalendarEvent(Context context, boolean isCalendarEvent) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isCalendarEvent", isCalendarEvent);
		editor.commit();
	}
	// ////////////purchase close Ads/////////////
	public static boolean getIsAssessment(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isAssessment", false);
	}

	public static void setIsAssessment(Context context, boolean isAssessment) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isAssessment", isAssessment);
		editor.commit();
	}
	// ////////////On/Offf receive push notification/////////////
	public static boolean getIsPush(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("ispush", false);
	}

	public static void setIsPush(Context context, boolean ispush) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("ispush", ispush);
		editor.commit();
	}
	// ////////////purchase Water Mark/////////////
	public static boolean getIsWatermark(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("watermark", false);
	}

	public static void setIsWatermark(Context context, boolean watermark) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("watermark", watermark);
		editor.commit();
	}
	// ////////////Change Water Mark/////////////
	public static boolean getIsChangeWatermark(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("watermarkChange", false);
	}

	public static void setIsChangeWatermark(Context context, boolean watermarkChange) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("watermarkChange", watermarkChange);
		editor.commit();
	}
	// ////////////Change Add Event to Calendar/////////////
	public static boolean getIsChangeSyncCalendar(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("syncCalendar", false);
	}

	public static void setIsChangeSyncCalendar(Context context, boolean syncCalendar) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("syncCalendar", syncCalendar);
		editor.commit();
	}
	// ////////////Set is reload screen/////////////
	public static boolean getIsReload(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isReload", false);
	}

	public static void setIsReload(Context context, boolean isReload) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isReload", isReload);
		editor.commit();
	}
	public static String getAccessToken(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("accessToken", "");
	}
	public static void setAccessToken(Context context, String accessToken){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("accessToken", accessToken);
		editor.commit();
	}
	public static String getTokenType(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("tokeType", "");
	}
	public static void setTokenType(Context context, String tokeType){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("tokeType", tokeType);
		editor.commit();
	}
	public static int getExpires_in(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getInt("expires_in", 0);
	}
	public static void setExpires_in(Context context, int expires_in){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putInt("expires_in", expires_in);
		editor.commit();
	}
	public static String getUserObject(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("UserObject", "");
	}
	public static void setUserObject(Context context, String UserObject){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("UserObject", UserObject);
		editor.commit();
	}
	public static String getUsername(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Username", "");
	}
	public static void setUsername(Context context, String Username){
		SharedPreferences sharedPre = context.getSharedPreferences(
				LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Username", Username);
		editor.commit();
	}

	public static void ShowpopupMessage(Activity activity, String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		dialog.setMessage(message);
		dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();

	}
	public static void ShowDialogMessage(Activity activity, String title, String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();

	}
	public static void writeList(Context context, List<String> list, String prefix)
	{
		SharedPreferences prefs = context.getSharedPreferences(LakConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();

		int size = prefs.getInt(prefix+"_size", 0);

		// clear the previous data if exists
		for(int i=0; i<size; i++)
			editor.remove(prefix+"_"+i);

		// write the current list
		for(int i=0; i<list.size(); i++)
			editor.putString(prefix + "_" + i, list.get(i));

		editor.putInt(prefix+"_size", list.size());
		editor.commit();
	}
	public static List<String> readList (Context context, String prefix)
	{
		SharedPreferences prefs = context.getSharedPreferences(LakConst.PREFERENCES, Context.MODE_PRIVATE);

		int size = prefs.getInt(prefix + "_size", 0);

		List<String> data = new ArrayList<String>(size);
		for(int i=0; i<size; i++)
			data.add(prefs.getString(prefix + "_" + i, null));

		return data;
	}

	// ////////////set text signature /////////////
	public static String GetSignedText(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getString("signedText", " ");
	}

	public static void SetSignedText(Context context, String signedText) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("signedText", signedText);
		editor.commit();
	}
	//========== Buy Stickers of Month ===============
	// ////////////purchase Water Mark/////////////
	public static boolean getIsEdit(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("editprofile", false);
	}

	public static void setIsEdit(Context context, boolean editprofile) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("editprofile", editprofile);
		editor.commit();
	}


	public static int GetnumShow(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getInt("numshow", 1);
	}

	public static void SetnumShow(Context context, int numshow) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("numshow", numshow);
		editor.commit();
	}

	//===========login with facebook ==========
	public static boolean getIsFBLogin(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isFblogin", false);
	}

	public static void setIsFBLogin(Context context, boolean isFblogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isFblogin", isFblogin);
		editor.commit();
	}
	public static boolean getIsLogin(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("islogin", false);
	}

	public static void setIsLogin(Context context, boolean islogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("islogin", islogin);
		editor.commit();
	}
	public static boolean getIsStudent(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isloginStudent", false);
	}

	public static void setIsStudent(Context context, boolean isloginStudent) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isloginStudent", isloginStudent);
		editor.commit();
	}
	public static boolean getIsTeacher(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isloginTeacher", false);
	}

	public static void setIsTeacher(Context context, boolean isloginTeacher) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				LakConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isloginTeacher", isloginTeacher);
		editor.commit();
	}
	public static String formatDay(String datetime){
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");//2017-03-30T22:00:00
		Date newDate = null;
		try {
			newDate = sdf.parse(datetime);
			sdf = new SimpleDateFormat("EEE");
			date = sdf.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String formatDatetime1(String datetime) {
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date newDate = null;
		try {
			newDate = sdf.parse(datetime);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String formatDatetime2(String datetime){
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//2017-06-29 03:35:37
		Date newDate = null;
		try {
			newDate = sdf.parse(datetime);
			sdf = new SimpleDateFormat("dd.MM.yy");
			date = sdf.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
//	public static String getAge(int year, int month, int day){
//		Calendar dob = Calendar.getInstance();
//		Calendar today = Calendar.getInstance();
//
//		dob.set(year, month, day);
//
//		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//		Log.e("sagjhgkj", "age:" +age);
//
//		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR) && today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)){
//			age--;
//		}
//
//		Integer ageInt = new Integer(age);
//		String ageS = ageInt.toString();
//		Log.e("sagjhgkj age", "age:" +age);
//		if(ageInt < 0)
//			return "0";
//		return ageS;
//	}
	public static int getAge (int _year, int _month, int _day) {

		GregorianCalendar cal = new GregorianCalendar();
		int y, m, d, a;

		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH);
		d = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(_year, _month, _day);
		a = y - cal.get(Calendar.YEAR);
		if ((m < cal.get(Calendar.MONTH))
				|| ((m == cal.get(Calendar.MONTH)) && (d < cal
				.get(Calendar.DAY_OF_MONTH)))) {
			--a;
		}
		if(a < 0)
			return 0;
		return a;
	}
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	public static Bitmap getRoundedCornerTopBitmap(Context context, Bitmap input, int pixels , int w , int h , boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR  ) {

		Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final float densityMultiplier = context.getResources().getDisplayMetrics().density;

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);

		//make sure that our rounded corner is scaled appropriately
		final float roundPx = pixels*densityMultiplier;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


		//draw rectangles over the corners we want to be square
		if (squareTL ){
			canvas.drawRect(0, 0, w/2, h/2, paint);
		}
		if (squareTR ){
			canvas.drawRect(w/2, 0, w, h/2, paint);
		}
		if (squareBL ){
			canvas.drawRect(0, h/2, w/2, h, paint);
		}
		if (squareBR ){
			canvas.drawRect(w/2, h/2, w, h, paint);
		}

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(input, 0,0, paint);

		return output;
	}
	public static int checkRotation(Uri uri) throws IOException {
		ExifInterface ei = new ExifInterface(uri.getPath());
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				return 90;
			case ExifInterface.ORIENTATION_ROTATE_180:
				return 180;
			case ExifInterface.ORIENTATION_ROTATE_270:
				return 270;
			default:
				return 0;
		}
	}
	public static Bitmap scaleDown(Bitmap realImage,
								   boolean filter) {
		float ratio = Math.min(
				(float) MAX_IMAGE_DIMENSION / realImage.getWidth(),
				(float) MAX_IMAGE_DIMENSION / realImage.getHeight());
		int width = Math.round((float) ratio * realImage.getWidth());
		int height = Math.round((float) ratio * realImage.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
				height, filter);
		return newBitmap;
	}
	public static Bitmap scaleImage(Context context, Uri photoUri) throws IOException, FileNotFoundException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
					srcBitmap.getHeight(), matrix, true);
		}

		String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (type.equals("image/png")) {
			srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		} else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		}
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
	}

	public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	public static Bitmap rotateImage(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
				true);
	}
}

package database;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.playzone.kidszone.Parent;
import com.playzone.kidszone.models.AppListModel;
import com.playzone.kidszone.models.ChildModel;
import com.playzone.kidszone.models.Goals_Model;
import com.playzone.kidszone.models.Internet_Reg_stats_model;
import com.playzone.kidszone.models.SettingsModel;
import com.playzone.kidszone.models.Web_SettingsModel;
import com.playzone.kidszone.models.access_type_model;
import com.playzone.kidszone.models.active_child_model;
import com.playzone.kidszone.models.active_user_model;
import com.playzone.kidszone.models.credit_model;
import com.playzone.kidszone.models.fri_model;
import com.playzone.kidszone.models.game_model;
import com.playzone.kidszone.models.lock_home_model;
import com.playzone.kidszone.models.mon_model;
import com.playzone.kidszone.models.parent_model;
import com.playzone.kidszone.models.sat_model;
import com.playzone.kidszone.models.statistics_model;
import com.playzone.kidszone.models.sun_model;
import com.playzone.kidszone.models.thurs_model;
import com.playzone.kidszone.models.tues_model;
import com.playzone.kidszone.models.wed_model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = DataBaseHelper.class.getName();
	// Database Version
	private static final int DATABASE_VERSION = 11;

	// Database Name
	private static final String DATABASE_NAME = "kidszone";

	// Table Names





	private static final String TABLE_ACCESS_TYPE = "access_type";
	private static final String TABLE_APP_LIST = "applist";
	private static final String TABLE_CHILD = "child_table";
	private static final String TABLE_PARENT = "parent_table";
	private static final String TABLE_STATISTICS = "user_statistics";
	private static final String TABLE_CREDENTIALS = "active_user_details";
	private static final String TABLE_GAMES = "games";
	private static final String TABLE_CREDIT = "credit";
	private static final String TABLE_GOALS = "goals";

	private static final String FRIDAY_TABLE = "friday";
	private static final String MONDAY_TABLE = "monday";
	private static final String SATURDAY_TABLE = "saturday";
	private static final String SUNDAY_TABLE = "sunday";
	private static final String THURSDAY_TABLE = "thursday";
	private static final String TUESDAY_TABLE = "tuesday";
	private static final String WEDNESDAY_TABLE = "wednesday";

	private static final String TABLE_WEB_SETTINGS = "web_settings";
	public static final String TABLE_SETTINGS = "setting";
	public static final String TABLE_INTERNET_REG_STATUS = "internet_registration_status";

	public static final String TABLE_HOME_SETTING = "home_setting";

	public static final String TABLE_ACTIVE_OWNER = "active_device_owner";
	public static final String TABLE_TOTAL_REWARD = "total_reward";




	//column names for lock home  settings
	private static final String P_ID_LOCK = "pid";
	private static final String LOCK_VALUE = "lock_value";

	//column names for total reward
	private static final String K_ID_R = "kid";
	private static final String TOTAL_REWARD = "total_reward";

	//column names for TABLE_ACTIVE_OWNER  settings
	private static final String ACTIVE_KID = "active_kid";
	private static final String ACTIVE_PID = "active_pid";


	public static final String ID = "id";

	//column names for Parent Account
	private static final String P_NAME = "name";
	private static final String P_EMAIL = "email";
	private static final String FIRST_TIME = "first";
	private static final String SUBSCRIBED = "subscribed";
	private static final String ELAPSED_TIME = "elapsed_time";
	private static final String END_DATE = "end_date";
	private static final String START_DATE = "start_date";
	private static final String P_PASS = "password";
	private static final String PACKAGE_NAME = "package_name";
	private static final String DURATION = "duration";


//column names for CREDIT
	private static final String CRED_AMOUNT = "credit_amount";
	private static final String CRED_OWNER_ID = "credit_owner_id";

//column names for Internet reg stats
	private static final String REG_STATUS = "reg_status";
	private static final String TODAY_STATUS = "todays_display_status";
	private static final String OFFLINE_CHANGES = "offline_changes";
	private static final String OFFLINE_COUNT = "offline_count";

//column names for Games
	private static final String G_NAME = "gname";
	private static final String G_URL = "gurl";
	private static final String G_BANNER = "g_banner";
	private static final String G_LOGO = "g_logo";
	private static final String G_DESCRIPTION = "g_description";
	private static final String G_CATEGORY = "g_category";

	//column names for Child statistics
	private static final String C_ID = "cid";
	private static final String APP_NAME = "app_name";
	private static final String TIMES_OPEN = "times_opened";
	private static final String DATE = "date_open";
	private static final String APP_ICON = "app_icon";
	private static final String P_ID = "p_id";

//column names for EDUCATIONAL GOALS

	private static final String K_ID = "kid";
	private static final String P_G_EMAIL = "p_g_email";
	private static final String TASK_NAME = "taskname";
	private static final String ALOTTED_TIME = "alotted_time";
	private static final String ACTIVE_DAY = "active_day";
	private static final String STATUS = "status";
	private static final String SPENT = "spent";
	private static final String LEARN_FIRST = "learn_first";
	private static final String REWARD_EARNED = "reward_earned";


//column names for User Credentials Account
	private static final String KID_ID = "kids_id";
	private static final String IS_LOGGEDIN = "islogin";
	private static final String TIMED = "is_timed";




	// Common column names for access type model
	private static final String KEY_KID_ID = "kid_id";
	private static final String KEY_ACCESS_TYPE= "access_type";



	//column names for app list
	private static final String KEY_NAME = "name";
	private static final String KEY_ID = "id_value";
	private static final String KEY_ICON = "icon";
	private static final String KEY_PACKAGES = "packages";
	private static final String KEY_CHECKED = "checked";




	// Common column names for child model
	private static final String KEY_name = "name";
	private static final String KEY_pemail= "pemail";
	private static final String KEY_icon = "icon";
	private static final String KEY_gender = "gender";
	private static final String KEY_dob = "dob";
	private static final String KEY_pass = "pass";
	private static final String KEY_starttime = "starttime";
	private static final String KEY_endtime = "endtime";
	private static final String KEY_wholeweek = "wholeweek";
	private static final String KEY_singleday = "singleday";
	private static final String KEY_kidid = "kidid";
	private static final String KEY_accesstype = "accesstype";
	private static final String KEY_icon2 = "icon2";
	private static final String SET_CONTENT_RESTRICTION = "content_restriction";
	private static final String TOTAL_REWARD_EARNED = "total_reward_earned";

	// Common column names for the day of the week
	private static final String KEY_start_time = "start_time";
	private static final String KEY_end_time= "end_time";
	private static final String KEY_access_type = "access_type";
	private static final String KEY_kid_id = "kid_id";


	// Common column names SETTINGS MODEL
	private static final String SET_pname = "pname";
	private static final String SET_Pemail = "pemail";
	private static final String SET_LOCK_HOME_BUTTON= "lock_home_button";
	private static final String SET_FULL_SCREEN= "full_screen";
	private static final String SET_LOCK_NOTIFICATION_BAR= "lock_notification_bar";
	private static final String SET_LOCK_VOLUME_BOTTON = "lock_volume_botton";
	private static final String SET_MASTER_PASSWORD = "MasterPassword";

	private static final String SET_LANGUAGE = "language";
	private static final String SET_STORAGE_PATH = "storage_path";
	private static final String SET_FEEDBACK = "feedback";

	private static final String SET_LEARN_FIRST = "learn_first";
	private static final String SET_GOAL= "goal";




	// Common column names WEB_SETTINGS MODEL

	public static final String  NAME = "name";
	public static final String  URL = "url";
	public static final String  KEYWORD = "keyword";
	public static final String  ID_KID = "kid_id";
	public static final String  CONTENT_ID = "cid";
	public static final String  P_email = "pemail";





	// Table Create Statements
	// DbUSer table create statement
	//arraylist store table

	private static final String TABLE_ACTIVE_USER = "CREATE TABLE " +
			TABLE_CREDENTIALS + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + KID_ID + " TEXT," + TIMED + " TEXT," + IS_LOGGEDIN + " TEXT);";

private static final String TABLE_CREATE_TOTAL_REWARD = "CREATE TABLE " +
			TABLE_TOTAL_REWARD + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + K_ID_R + " TEXT," + TOTAL_REWARD + " TEXT);";



	private static final String TABLE_ACTIVE_CHILD = "CREATE TABLE " +
			TABLE_ACTIVE_OWNER + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + ACTIVE_KID + " TEXT," + ACTIVE_PID + " TEXT);";


	private static final String TABLE_LOCK_HOME = "CREATE TABLE " +
			TABLE_HOME_SETTING + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + P_ID_LOCK + " TEXT," + LOCK_VALUE + " TEXT);";





	private static final String TABLE_CREATE_GAME = "CREATE TABLE " +
			TABLE_GAMES + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + G_NAME + " TEXT," + G_URL + " TEXT," + G_CATEGORY + " TEXT,"+ G_BANNER + " TEXT," + G_LOGO + " TEXT,"
		+ G_DESCRIPTION + " TEXT);";

private static final String TABLE_CREATE_CREDIT = "CREATE TABLE " +
			TABLE_CREDIT + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + CRED_AMOUNT + " TEXT," + CRED_OWNER_ID + " TEXT);";

private static final String TABLE_INTERNET_REG = "CREATE TABLE " +
			TABLE_INTERNET_REG_STATUS + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + REG_STATUS + " TEXT," + OFFLINE_CHANGES + " TEXT,"+
		OFFLINE_COUNT + " TEXT," + TODAY_STATUS + " TEXT);";


	private static final String TABLE_ACCESS = "CREATE TABLE " +
			TABLE_ACCESS_TYPE + " ("  + KEY_KID_ID + " TEXT PRIMARY KEY," +  KEY_ACCESS_TYPE + " TEXT);";

	private static final String TABLE_APPLIST = "CREATE TABLE " +
			TABLE_APP_LIST + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT," +   KEY_ICON + " TEXT,"
			+   KEY_ID + " TEXT," +
			KEY_PACKAGES + " TEXT," + KEY_CHECKED + " TEXT);";


	private static final String CREATE_TABLE_CHILD = "CREATE TABLE " +
			TABLE_CHILD + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_name + " TEXT," +   KEY_pemail + " TEXT," +    KEY_icon + " TEXT,"
			+ KEY_gender + " TEXT," + KEY_dob + " TEXT," + KEY_pass + " TEXT," + KEY_starttime + " TEXT, " +
			KEY_endtime + " TEXT ," + KEY_wholeweek + " TEXT ," + KEY_singleday + " TEXT ,"+ KEY_kidid + " TEXT ,"
			+ KEY_icon2 + " TEXT ,"  +  SET_CONTENT_RESTRICTION + " TEXT,"  +  TOTAL_REWARD_EARNED + " TEXT," + KEY_accesstype + " TEXT);";


	private static final String CREATE_TABLE_FRIDAY = "CREATE TABLE " +
			FRIDAY_TABLE + " (" +  KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
			KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

private static final String CREATE_TABLE_SATURDAY = "CREATE TABLE " +
			SATURDAY_TABLE + " (" +   KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
		KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

private static final String CREATE_TABLE_SUNDAY = "CREATE TABLE " +
			SUNDAY_TABLE + " (" +   KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
		KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

private static final String CREATE_TABLE_MONDAY = "CREATE TABLE " +
			MONDAY_TABLE + " (" +  KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
		KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

private static final String CREATE_TABLE_TUESDAY = "CREATE TABLE " +
			TUESDAY_TABLE + " (" +   KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
		KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

private static final String CREATE_TABLE_WEDNESDAY = "CREATE TABLE " +
			WEDNESDAY_TABLE + " (" +   KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
		KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

private static final String CREATE_TABLE_THURSDAY = "CREATE TABLE " +
			THURSDAY_TABLE + " (" +  KEY_start_time + " TEXT," +   KEY_end_time + " TEXT," +
		KEY_access_type + " TEXT," + KEY_kid_id + " TEXT PRIMARY KEY);";

	private static final String CREATE_TABLE_WEB_SETTINGS = "CREATE TABLE " +
			TABLE_WEB_SETTINGS + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT," +   URL + " TEXT," +   P_email + " TEXT,"
			+  ID_KID + " TEXT," +
			CONTENT_ID + " TEXT," +    KEYWORD + " TEXT);";

	private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " +
			TABLE_SETTINGS + " (" +  SET_pname + " TEXT PRIMARY KEY," +   SET_LOCK_HOME_BUTTON + " TEXT,"+
			SET_FULL_SCREEN + " TEXT," +
			SET_LOCK_NOTIFICATION_BAR + " TEXT," + SET_LOCK_VOLUME_BOTTON + " TEXT," +
			SET_MASTER_PASSWORD + " TEXT," +  SET_LANGUAGE + " TEXT,"+  SET_Pemail + " TEXT,"
			+
			SET_LEARN_FIRST + " TEXT," + SET_GOAL + " TEXT," +
			SET_STORAGE_PATH + " TEXT," +   SET_FEEDBACK + " TEXT);";



	private static final String CREATE_TABLE_PARENT = "CREATE TABLE " +
			TABLE_PARENT + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + P_NAME + " TEXT," +   P_EMAIL + " TEXT," +   FIRST_TIME + " TEXT,"+
			ELAPSED_TIME + " LONG,"+ END_DATE + " TEXT,"+ START_DATE + " TEXT,"+ PACKAGE_NAME + " TEXT,"+ DURATION + " TEXT,"+
			SUBSCRIBED + " TEXT,"  + P_PASS + " TEXT);";

	private static final String CREATE_TABLE_STATISTICS = "CREATE TABLE " +
			TABLE_STATISTICS + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + C_ID + " TEXT," +   APP_NAME + " TEXT," +
			P_ID + " TEXT," +   TIMES_OPEN + " TEXT,"
			+   APP_ICON + " TEXT," + DATE + " TEXT);";


private static final String CREATE_TABLE_GOALS = "CREATE TABLE " +
			TABLE_GOALS + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + K_ID + " TEXT," +   P_G_EMAIL + " TEXT," +
		TASK_NAME + " TEXT," +   ALOTTED_TIME + " TEXT,"
			+   ACTIVE_DAY + " TEXT,"+   SPENT + " TEXT," + STATUS + " TEXT,"  + REWARD_EARNED + " TEXT," + LEARN_FIRST + " TEXT);";


	public DataBaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_SETTINGS);
		db.execSQL(CREATE_TABLE_WEB_SETTINGS);
		db.execSQL(CREATE_TABLE_THURSDAY);
		db.execSQL(CREATE_TABLE_MONDAY);
		db.execSQL(CREATE_TABLE_TUESDAY);
		db.execSQL(CREATE_TABLE_WEDNESDAY);
		db.execSQL(CREATE_TABLE_FRIDAY);
		db.execSQL(CREATE_TABLE_SATURDAY);
		db.execSQL(CREATE_TABLE_SUNDAY);
		db.execSQL(CREATE_TABLE_CHILD);
		db.execSQL(TABLE_APPLIST);
		db.execSQL(TABLE_ACCESS);
		db.execSQL(CREATE_TABLE_PARENT);
		db.execSQL(TABLE_ACTIVE_USER);
		db.execSQL(CREATE_TABLE_STATISTICS);
		db.execSQL(TABLE_CREATE_GAME);
		db.execSQL(TABLE_CREATE_CREDIT);
		db.execSQL(TABLE_INTERNET_REG);
		db.execSQL(CREATE_TABLE_GOALS);

		db.execSQL(TABLE_LOCK_HOME);
		db.execSQL(TABLE_ACTIVE_CHILD);
		db.execSQL(TABLE_CREATE_TOTAL_REWARD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables


		//db.execSQL("ALTER TABLE " + TABLE_PARENT +"  ADD COLUMN subscribed  TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_CHILD +"  ADD COLUMN pemail  TEXT");
//		db.execSQL("ALTER TABLE " + TABLE_STATISTICS +"  ADD COLUMN p_id   TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_WEB_SETTINGS +"  ADD COLUMN pemail   TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_GAMES +"  ADD COLUMN g_category   TEXT");

		//db.execSQL("ALTER TABLE " + TABLE_INTERNET_REG_STATUS +"  ADD COLUMN offline_count   TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_PARENT +"  ADD COLUMN elapsed_time   LONG");
		//db.execSQL("ALTER TABLE " + TABLE_PARENT +"  ADD COLUMN duration   TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_SETTINGS +"  ADD COLUMN content_restriction   TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_SETTINGS +"  ADD COLUMN learn_first   TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_CHILD +"  ADD COLUMN content_restriction  TEXT");
		//db.execSQL("ALTER TABLE " + TABLE_CHILD +"  ADD COLUMN total_reward_earned  TEXT");

		db.execSQL(TABLE_CREATE_TOTAL_REWARD);
		//db.execSQL(CREATE_TABLE_GOALS);



		// create new tables
		// onCreate(db);
	}






	// Adding new CHILD detail
	public Long addActiveChild(active_child_model list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ TABLE_ACTIVE_OWNER);
		db.execSQL("delete from sqlite_sequence where name=  'active_device_owner' " );

		//onUpgrade(db);
		long res=0;

		ContentValues values = new ContentValues();
		values.put(ACTIVE_KID, list2.getKid_id());
		values.put(ACTIVE_PID, list2.getPid());


		res= db.insertWithOnConflict(TABLE_ACTIVE_OWNER, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}


	public active_child_model getActive_child() {
		active_child_model scdLists = new active_child_model();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ACTIVE_OWNER  ;

		SQLiteDatabase db = this.getWritableDatabase();

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {


					scdLists.setKid_id(cursor.getString(cursor.getColumnIndex(ACTIVE_KID)));
					scdLists.setPid(cursor.getString(cursor.getColumnIndex(ACTIVE_PID)));



				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return scdLists;
	}




	// Adding new Web_SETTINGS detail
	public Long addLock_home(lock_home_model List) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ TABLE_HOME_SETTING);
		db.execSQL("delete from sqlite_sequence where name=  'home_setting' " );


		ContentValues values = new ContentValues();
		values.put(P_ID_LOCK, List.getPid());
		values.put(LOCK_VALUE, List.getValue());




		long res= db.insertWithOnConflict(TABLE_HOME_SETTING, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}





	public lock_home_model getlock_home_value() {
		lock_home_model scdLists = new lock_home_model();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_HOME_SETTING ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			scdLists.setPid(cursor.getString(cursor.getColumnIndex(P_ID_LOCK)));
			scdLists.setValue(cursor.getString(cursor.getColumnIndex(LOCK_VALUE)));

		}


		// return category list
		return scdLists;
	}











	// Adding new CHILD detail
	public Long addChild(List<ChildModel> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ TABLE_CHILD);
		db.execSQL("delete from sqlite_sequence where name=  'child_table' " );

		//onUpgrade(db);
		long res=0;
for(int r=0; r<=list2.size()-1; r++){
	ChildModel list=new ChildModel();
	list=list2.get(r);

			ContentValues values = new ContentValues();
			values.put(KEY_name, list.getName());
			values.put(KEY_pemail, list.getPemail());

			values.put(KEY_icon, list.getIcon());

			values.put(KEY_gender, list.getGender());
			values.put(KEY_dob, list.getDob());
			values.put(KEY_pass, list.getPass());
			values.put(KEY_starttime, list.getStart_time());
			values.put(KEY_endtime, list.getEnd_time());
			values.put(KEY_wholeweek, list.getWhole_week().toString());
			values.put(KEY_singleday, list.getSingle_day().toString());
			values.put(KEY_kidid, list.getKid_id());
			values.put(KEY_accesstype, list.getAccess_type());
			values.put(KEY_icon2, list.geticon2().toString());
	       values.put(SET_CONTENT_RESTRICTION, list.getContent_Restriction());
	        values.put(TOTAL_REWARD_EARNED, list.getReward_earned());
			 res= db.insertWithOnConflict(TABLE_CHILD, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.close(); // Closing database connection

		return res;
	}


// Adding new GAME detail
	public Long addGAME(List<game_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ TABLE_GAMES);
		db.execSQL("delete from sqlite_sequence where name=  'game' " );

		//onUpgrade(db);
		long res=0;
for(int r=0; r<=list2.size()-1; r++){
	game_model list=new game_model();
	list=list2.get(r);


			ContentValues values = new ContentValues();
			values.put(G_NAME, list.getName());
			values.put(G_URL, list.getUrl());

			values.put(G_BANNER, list.getBanner());

			values.put(G_LOGO, list.getLogo());
			values.put(G_DESCRIPTION, list.getDescription());
			values.put(G_CATEGORY, list.getCategory());

			 res= db.insertWithOnConflict(TABLE_GAMES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.close(); // Closing database connection

		return res;
	}




	public List<game_model> getGames() {
		List<game_model> scdLists = new ArrayList<game_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_GAMES  ;

		SQLiteDatabase db = this.getWritableDatabase();

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {

					game_model list = new game_model();
					list.setName(cursor.getString(cursor.getColumnIndex(G_NAME)));
					list.setUrl(cursor.getString(cursor.getColumnIndex(G_URL)));

					list.setBanner(cursor.getString(cursor.getColumnIndex(G_BANNER)));

					list.setLogo(cursor.getString(cursor.getColumnIndex(G_LOGO)));
					list.setDescription(cursor.getString(cursor.getColumnIndex(G_DESCRIPTION)));
					list.setCategory(cursor.getString(cursor.getColumnIndex(G_CATEGORY)));

					scdLists.add(list);
				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return scdLists;
	}



	// Adding new Educational Goal detail
	public Long addGoal(List<Goals_Model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		//db.execSQL("delete from "+ TABLE_GOALS);
		//db.execSQL("delete from sqlite_sequence where name=  'goals' " );

		//onUpgrade(db);
		long res=0;
for(int r=0; r<=list2.size()-1; r++){
	Goals_Model list=new Goals_Model();
	list=list2.get(r);

			ContentValues values = new ContentValues();
			values.put(K_ID, list.getKid());
			values.put(P_G_EMAIL, list.getP_email());

			values.put(TASK_NAME, list.getTaskname());

			values.put(ALOTTED_TIME, list.getAlotted_time());
			values.put(ACTIVE_DAY, list.getActive_day());
			values.put(SPENT, list.getSpent());
			values.put(STATUS, list.getStatus());
			values.put(LEARN_FIRST, list.getLearn_first());
			values.put(REWARD_EARNED, list.getReward_earned());

			// res= db.insertWithOnConflict(TABLE_GOALS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

	res=db.update(TABLE_GOALS, values, K_ID + " = ?  AND " + TASK_NAME + " = ?",
			new String[] { list.getKid(), list.getTaskname() });

	if(res==0){
		db.insertWithOnConflict(TABLE_GOALS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

	}
		}
		db.close(); // Closing database connection

		return res;
	}


	// Adding new Educational Goal detail
	public Long editGoal(List<Goals_Model> list2, String kid) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;
for(int r=0; r<=list2.size()-1; r++){
	Goals_Model list=new Goals_Model();
	list=list2.get(r);

			ContentValues values = new ContentValues();
			values.put(K_ID, list.getKid());
			values.put(P_G_EMAIL, list.getP_email());

			values.put(TASK_NAME, list.getTaskname());

			values.put(ALOTTED_TIME, list.getAlotted_time());
			values.put(ACTIVE_DAY, list.getActive_day());
			values.put(SPENT, list.getSpent());
			values.put(STATUS, list.getStatus());
			values.put(LEARN_FIRST, list.getLearn_first());
			values.put(REWARD_EARNED, list.getReward_earned());

	res=   db.update(TABLE_GOALS, values, K_ID + " = ?  AND " + TASK_NAME + " = ?",
			new String[] { kid, list.getTaskname() });


}
		db.close(); // Closing database connection

		return res;
	}

	// EDITING TOTAL REWARD
	public Long editReward(String reward, String kid) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;

			ContentValues values = new ContentValues();
			values.put(K_ID, kid);
			values.put(TOTAL_REWARD, reward);

	res=   db.update(TABLE_TOTAL_REWARD, values, K_ID + " = ?",
			new String[] { kid });

		if(res==0){
			db.insertWithOnConflict(TABLE_TOTAL_REWARD, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		}

		db.close(); // Closing database connection

		return res;
	}





	public String getReward_byId(String id) {

		// Select All Query
		//String selectQuery = "SELECT  * FROM " + TABLE_GOALS  ;
String reward= "0";
		SQLiteDatabase db = this.getWritableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_TOTAL_REWARD + " WHERE " + K_ID + " = " + "'" + id + "'";



		try {
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {


					reward= cursor.getString(cursor.getColumnIndex(TOTAL_REWARD));


				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return reward;
	}


// Adding new Educational Goal detail
	public Long addnGoal(List<Goals_Model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

	//	db.execSQL("delete from "+ TABLE_GOALS);
	//	db.execSQL("delete from sqlite_sequence where name=  'goals' " );

		//onUpgrade(db);
		long res=0;
for(int r=0; r<=list2.size()-1; r++){
	Goals_Model list=new Goals_Model();
	list=list2.get(r);

			ContentValues values = new ContentValues();
			values.put(K_ID, list.getKid());
			values.put(P_G_EMAIL, list.getP_email());

			values.put(TASK_NAME, list.getTaskname());

			values.put(ALOTTED_TIME, list.getAlotted_time());
			values.put(ACTIVE_DAY, list.getActive_day());
			values.put(SPENT, list.getSpent());
			values.put(STATUS, list.getStatus());
			values.put(LEARN_FIRST, list.getLearn_first());
			values.put(REWARD_EARNED, list.getReward_earned());

			 res= db.insertWithOnConflict(TABLE_GOALS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

}
		db.close(); // Closing database connection

		return res;
	}




	public List<Goals_Model> getGoals() {
		List<Goals_Model> scdLists = new ArrayList<Goals_Model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_GOALS  ;

		SQLiteDatabase db = this.getWritableDatabase();

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {

					Goals_Model list = new Goals_Model();
					list.setKid(cursor.getString(cursor.getColumnIndex(K_ID)));
					list.setP_email(cursor.getString(cursor.getColumnIndex(P_G_EMAIL)));

					list.setTaskname(cursor.getString(cursor.getColumnIndex(TASK_NAME)));

					list.setAlotted_time(cursor.getString(cursor.getColumnIndex(ALOTTED_TIME)));
					list.setActive_day(cursor.getString(cursor.getColumnIndex(ACTIVE_DAY)));
					list.setSpent(cursor.getString(cursor.getColumnIndex(SPENT)));
					list.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
					list.setLearn_first(cursor.getString(cursor.getColumnIndex(LEARN_FIRST)));
					list.setReward_earned(cursor.getString(cursor.getColumnIndex(REWARD_EARNED)));

					scdLists.add(list);
				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return scdLists;
	}


public List<Goals_Model> getGoals_byId(String id) {
		List<Goals_Model> scdLists = new ArrayList<Goals_Model>();
		// Select All Query
		//String selectQuery = "SELECT  * FROM " + TABLE_GOALS  ;

		SQLiteDatabase db = this.getWritableDatabase();

	String selectQuery = "SELECT  * FROM " + TABLE_GOALS + " WHERE " + K_ID + " = " + "'" + id + "'";



	try {
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {

					Goals_Model list = new Goals_Model();
					list.setKid(cursor.getString(cursor.getColumnIndex(K_ID)));
					list.setP_email(cursor.getString(cursor.getColumnIndex(P_G_EMAIL)));

					list.setTaskname(cursor.getString(cursor.getColumnIndex(TASK_NAME)));

					list.setAlotted_time(cursor.getString(cursor.getColumnIndex(ALOTTED_TIME)));
					list.setActive_day(cursor.getString(cursor.getColumnIndex(ACTIVE_DAY)));
					list.setSpent(cursor.getString(cursor.getColumnIndex(SPENT)));
					list.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
					list.setLearn_first(cursor.getString(cursor.getColumnIndex(LEARN_FIRST)));
					list.setReward_earned(cursor.getString(cursor.getColumnIndex(REWARD_EARNED)));

					scdLists.add(list);
				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return scdLists;
	}



	// Adding new CREDIT detail
	public Long addCREDIT(credit_model list2) {
		SQLiteDatabase db = this.getWritableDatabase();
		credit_model list = new credit_model();

		String count= "1";

		list=  getCredit(list2.getKid());

		if(list!=null){
			count= String.valueOf(Integer.parseInt(list.getCredit()) + 2);
		}


		//onUpgrade(db);


			ContentValues values = new ContentValues();
			values.put(CRED_AMOUNT, count);
			values.put(CRED_OWNER_ID, list2.getKid());

		long res=   db.update(TABLE_CREDIT, values, CRED_OWNER_ID + " = ? ",
				new String[] { list2.getKid() });

		if(res==0){
			db.insertWithOnConflict(TABLE_CREDIT, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		}
		db.close(); // Closing database connection

		return res;
	}
	// Adding new CREDIT detail
	public Long editCREDIT(String cred, String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		credit_model list = new credit_model();


		ContentValues values = new ContentValues();
		values.put(CRED_AMOUNT, cred);
		values.put(CRED_OWNER_ID, id);

		long res=   db.update(TABLE_CREDIT, values, CRED_OWNER_ID + " = ? ",
				new String[] { id });

		db.close(); // Closing database connection

		return res;
	}


	public List<credit_model> getCredit() {
		List<credit_model> scdLists = new ArrayList<credit_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CREDIT  ;

		SQLiteDatabase db = this.getWritableDatabase();

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {

					credit_model list = new credit_model();
					list.setCredit(cursor.getString(cursor.getColumnIndex(CRED_AMOUNT)));
					list.setKid(cursor.getString(cursor.getColumnIndex(CRED_OWNER_ID)));

					scdLists.add(list);
				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return scdLists;
	}

	public credit_model getCredit( String id) {
		// Select All Query


		SQLiteDatabase db = this.getWritableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_CREDIT + " WHERE " + CRED_OWNER_ID + " = " + "'" + id + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		credit_model list = new credit_model();

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				list.setCredit(cursor.getString(cursor.getColumnIndex(CRED_AMOUNT)));
				list.setKid(cursor.getString(cursor.getColumnIndex(CRED_OWNER_ID)));

			} while (cursor.moveToNext());
		}

		// return category list
		return list;
	}

	// Adding new CHILD detail
	public Long UpdateChild(ChildModel list, String id) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


			ContentValues values = new ContentValues();
			values.put(KEY_name, list.getName());
			values.put(KEY_pemail, list.getPemail());

			values.put(KEY_icon, list.getIcon());

			values.put(KEY_gender, list.getGender());
			values.put(KEY_dob, list.getDob());
			values.put(KEY_pass, list.getPass());

		values.put(KEY_accesstype, list.getAccess_type());



	res= db.update(TABLE_CHILD, values, KEY_kidid + " = ?",
			new String[] {id});

		db.close(); // Closing database connection

		return res;
	}
	// Adding new CHILD detail
	public Long UpdateChildContent_Restriction(String list, String id) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


			ContentValues values = new ContentValues();

			values.put(SET_CONTENT_RESTRICTION, list);




	res= db.update(TABLE_CHILD, values, KEY_kidid + " = ?",
			new String[] {id});

		db.close(); // Closing database connection

		return res;
	}



public Long UpdateChildReward_eaarned(String list, String id) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


			ContentValues values = new ContentValues();

			values.put(TOTAL_REWARD_EARNED, list);




	res= db.update(TABLE_CHILD, values, KEY_kidid + " = ?",
			new String[] {id});

		db.close(); // Closing database connection

		return res;
	}


// updating new CHILD detail
	public Long UpdateChildAccessType(String access, String id) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


			ContentValues values = new ContentValues();


		values.put(KEY_accesstype, access);



	res= db.update(TABLE_CHILD, values, KEY_kidid + " = ?",
			new String[] {id});

		db.close(); // Closing database connection

		return res;
	}

// deleting CHILD detail
	public Long deleteChild(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		long deleted = db.delete(TABLE_CHILD,  KEY_kidid + "=?" ,new String[]{id});

		//onUpgrade(db);

		db.close(); // Closing database connection

		return deleted;
	}



	public List<ChildModel> getchildren(String pemail) {
		List<ChildModel> scdLists = new ArrayList<ChildModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CHILD + " WHERE " + KEY_pemail + " = " + "'" + pemail + "'" ;

		SQLiteDatabase db = this.getWritableDatabase();

		try {
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					ChildModel list = new ChildModel();
					list.setName(cursor.getString(cursor.getColumnIndex(KEY_name)));
					list.setPemail(cursor.getString(cursor.getColumnIndex(KEY_pemail)));

					list.setIcon(cursor.getString(cursor.getColumnIndex(KEY_icon)));

					list.setGender(cursor.getString(cursor.getColumnIndex(KEY_gender)));
					list.setDob(cursor.getString(cursor.getColumnIndex(KEY_dob)));
					list.setPass(cursor.getString(cursor.getColumnIndex(KEY_pass)));

					list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_starttime)));
					list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_endtime)));

					list.setWhole_week(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_wholeweek))));
					list.setSingle_day(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_singleday))));
					list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kidid)));
					list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_accesstype)));
					list.setIcon2(Uri.parse(cursor.getString(cursor.getColumnIndex(KEY_icon2))));
					list.setContent_Restriction(cursor.getString(cursor.getColumnIndex(SET_CONTENT_RESTRICTION)));
					list.setReward_earned(cursor.getString(cursor.getColumnIndex(TOTAL_REWARD_EARNED)));
					scdLists.add(list);
				} while (cursor.moveToNext());
			}
		}
		catch (Exception f){

		}
		// return category list
		return scdLists;
	}


// Adding new SETTINGS detail
	public Long addSettings(SettingsModel List) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ TABLE_SETTINGS);
		db.execSQL("delete from sqlite_sequence where name=  'setting' " );

		//onUpgrade(db);
		long res=0;


			ContentValues values = new ContentValues();
			values.put(SET_pname, List.getpname());
			values.put(SET_Pemail, List.getpemail());
			values.put(SET_LOCK_HOME_BUTTON, String.valueOf(List.getlock_volume_botton()));
			values.put(SET_FULL_SCREEN, String.valueOf(List.getfullscreen_mode()));

			values.put(SET_LOCK_NOTIFICATION_BAR, String.valueOf(List.getlock_notification_bar()));

			values.put(SET_LOCK_VOLUME_BOTTON, String.valueOf(List.getlock_volume_botton()));
			values.put(SET_MASTER_PASSWORD, List.getMasterPassword());
			values.put(SET_LANGUAGE, List.getlanguage());
			values.put(SET_STORAGE_PATH, List.getstorage_path());
			values.put(SET_FEEDBACK, List.getfeedback());

			values.put(SET_LEARN_FIRST, List.getLearn_first());
			values.put(SET_GOAL, List.getGoal());


			res = db.insertWithOnConflict(TABLE_SETTINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);



		db.close(); // Closing database connection

		return res;
	}



	public List<SettingsModel> getSettings() {
		List<SettingsModel> scdLists = new ArrayList<SettingsModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SETTINGS ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				SettingsModel list = new SettingsModel();
				list.setPname(cursor.getString(cursor.getColumnIndex(SET_pname)));
				list.setPemail(cursor.getString(cursor.getColumnIndex(SET_Pemail)));
				list.setlock_home_button(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(SET_LOCK_HOME_BUTTON))));
				list.setFullscreen_mode(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(SET_FULL_SCREEN))));

				list.setlock_notification_bar(Boolean.parseBoolean(
						cursor.getString(cursor.getColumnIndex(SET_LOCK_NOTIFICATION_BAR))));

				list.setlock_volume_botton(Integer.parseInt(
						cursor.getString(cursor.getColumnIndex(SET_LOCK_VOLUME_BOTTON))));

				list.setMasterPassword(cursor.getString(cursor.getColumnIndex(SET_MASTER_PASSWORD)));
				list.setlanguage(cursor.getString(cursor.getColumnIndex(SET_LANGUAGE)));
				list.setstorage_path(cursor.getString(cursor.getColumnIndex(SET_STORAGE_PATH)));
				list.setfeedback(cursor.getString(cursor.getColumnIndex(SET_FEEDBACK)));

				list.setLearn_first(cursor.getString(cursor.getColumnIndex(SET_LEARN_FIRST)));
				list.setGoal(cursor.getString(cursor.getColumnIndex(SET_GOAL)));


				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}




// Adding new Web_SETTINGS detail
	public Long addWeb_Settings(Web_SettingsModel List) {
		SQLiteDatabase db = this.getWritableDatabase();

		//onUpgrade(db);
		//db.execSQL("delete from "+ TABLE_WEB_SETTINGS);
		//db.execSQL("delete from sqlite_sequence where name=  'web_settings' " );

		ContentValues values = new ContentValues();
		values.put(NAME, List.getname());
		values.put(URL, List.geturl());
		values.put(ID_KID, List.getId());
		values.put(CONTENT_ID, List.getCID());
		values.put(P_email, List.getP_email());

		values.put(KEYWORD,List.getKeyword());



		long res= db.insertWithOnConflict(TABLE_WEB_SETTINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}
// Adding new Web_SETTINGS detail
	public Long addWeb_Settings2(List<Web_SettingsModel> List2) {
		SQLiteDatabase db = this.getWritableDatabase();


		db.execSQL("delete from "+ TABLE_WEB_SETTINGS);
		db.execSQL("delete from sqlite_sequence where name=  'web_settings' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=List2.size()-1; r++) {
			Web_SettingsModel List = new Web_SettingsModel();
			List = List2.get(r);

			ContentValues values = new ContentValues();
			values.put(NAME, List.getname());
			values.put(URL, List.geturl());
			values.put(ID_KID, List.getId());
			values.put(CONTENT_ID, List.getCID());
			values.put(P_email, List.getP_email());

			values.put(KEYWORD, List.getKeyword());


			res = db.insertWithOnConflict(TABLE_WEB_SETTINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.close(); // Closing database connection

		return res;
	}


	// deleting CHILD detail
	public Long RemoveWebSetting(String id ,String cid) {
		SQLiteDatabase db = this.getWritableDatabase();

		long deleted = db.delete(TABLE_WEB_SETTINGS,  ID_KID + "=? AND " +  CONTENT_ID + "=?"
				,new String[]{id, cid});

		//onUpgrade(db);

		db.close(); // Closing database connection

		return deleted;
	}

	public List<Web_SettingsModel> getweb_Settings() {
		List<Web_SettingsModel> scdLists = new ArrayList<Web_SettingsModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WEB_SETTINGS ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Web_SettingsModel list = new Web_SettingsModel();
				list.setname(cursor.getString(cursor.getColumnIndex(NAME)));
				list.seturl(cursor.getString(cursor.getColumnIndex(URL)));
				list.setKeyword(cursor.getString(cursor.getColumnIndex(KEYWORD)));
				list.setCID(cursor.getString(cursor.getColumnIndex(CONTENT_ID)));
				list.setID(cursor.getString(cursor.getColumnIndex(ID_KID)));
				list.setP_email(cursor.getString(cursor.getColumnIndex(P_email)));


				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}



// Adding new App list detail
	public Long addApplist(List<AppListModel> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		//onUpgrade(db);

		db.execSQL("delete from "+ TABLE_APP_LIST);
		db.execSQL("delete from sqlite_sequence where name=  'applist' " );

		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			AppListModel List = new AppListModel();
			List = list2.get(r);

			ContentValues values = new ContentValues();
			values.put(KEY_NAME, List.getName());
			values.put(KEY_ID, List.getId());
			values.put(KEY_ICON, "icon");  //WORK

			values.put(KEY_PACKAGES, List.getPackages());
			values.put(KEY_CHECKED, List.getChecked().toString());


			res = db.insertWithOnConflict(TABLE_APP_LIST, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		}
		db.close(); // Closing database connection

		return res;
	}




	// Adding new App list detail
	public Long deleteApplist(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		//onUpgrade(db);

		//	String deleteQuery="delete from "+ TABLE_APP_LIST + " WHERE " + KEY_ID + "=" + "'" + id + "'" ;

		long deleted = db.delete(TABLE_APP_LIST,  KEY_ID + "=?" ,new String[]{id});
		//db.execSQL("delete from sqlite_sequence where name=  'applist' " );

		db.close(); // Closing database connection

		return deleted;
	}




	// Adding new App list detail
	public Long addApplist(List<AppListModel> list2, String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		//onUpgrade(db);

	//	String deleteQuery="delete from "+ TABLE_APP_LIST + " WHERE " + KEY_ID + "=" + "'" + id + "'" ;

		int deleted = db.delete(TABLE_APP_LIST,  KEY_ID + "=?" ,new String[]{id});

		//db.execSQL("delete from "+ TABLE_APP_LIST);
		db.execSQL("delete from sqlite_sequence where name=  'applist' " );

		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			AppListModel List = new AppListModel();
			List = list2.get(r);

			ContentValues values = new ContentValues();
			values.put(KEY_NAME, List.getName());
			values.put(KEY_ID, List.getId());
			values.put(KEY_ICON, "icon");  //WORK

			values.put(KEY_PACKAGES, List.getPackages());
			values.put(KEY_CHECKED, List.getChecked().toString());


			res = db.insertWithOnConflict(TABLE_APP_LIST, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<AppListModel> getApplist() {
		List<AppListModel> scdLists = new ArrayList<AppListModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_APP_LIST ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				AppListModel list = new AppListModel();

				list.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
				list.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
				list.setIcon(null);
				list.setPackages(cursor.getString(cursor.getColumnIndex(KEY_PACKAGES)));
				list.setCheck(Boolean.getBoolean(cursor.getString(cursor.getColumnIndex(KEY_CHECKED))));



				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}

public List<AppListModel> getApplist(String id) {
		List<AppListModel> scdLists = new ArrayList<AppListModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_APP_LIST  + " WHERE id_value  = " + "'" + id + "'" ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				AppListModel list = new AppListModel();

				list.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
				list.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
				list.setIcon(null);
				list.setPackages(cursor.getString(cursor.getColumnIndex(KEY_PACKAGES)));
				list.setCheck(Boolean.getBoolean(cursor.getString(cursor.getColumnIndex(KEY_CHECKED))));



				scdLists.add(list);
			} while (cursor.moveToNext());
		}


		return scdLists;
	}


	public List<AppListModel> checkAppAvailability(Activity activ, List<AppListModel> data) {

		PackageManager pm = activ.getPackageManager();
		List<AppListModel> apps = new ArrayList<>();
		Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
		intent.addCategory("android.intent.category.LAUNCHER");
		int count = 0;



		for (ResolveInfo ri : pm.queryIntentActivities(intent, 0)) {

			List<ActivityManager.RunningTaskInfo> runningtask = new ArrayList<>();
			ActivityManager.RunningTaskInfo run;

			String appName = ri.loadLabel(pm).toString();
			Drawable icon = ri.activityInfo.loadIcon(pm);
			String packages = ri.activityInfo.packageName;
			boolean checked = false;


			if(data != null){

				for(int d=0; d<=data.size()-1; d++) {


					if(data.get(d).getPackages().equals(packages)) {

						apps.add(new AppListModel(appName, icon, packages, true, Parent.kid_id));

						break;

					}

				}
			}

			count++;

		}

		Parent.selectedApp_2.clear();
		Parent.selectedApp_2.addAll(apps);
		Parent.selectedApp_2.addAll(0, Parent.inbuildApp);


		return Parent.selectedApp_2;
	}


	// Adding new ACCESS TYPE detail
	public Long addAccessType(List<access_type_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ TABLE_ACCESS_TYPE);
		db.execSQL("delete from sqlite_sequence where name=  'access_type' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {

			access_type_model List = new access_type_model();
			List = list2.get(r);

			ContentValues values = new ContentValues();
			values.put(KEY_ACCESS_TYPE, List.getAccess_type());
			values.put(KEY_KID_ID, List.getKid_id());


			res = db.insertWithOnConflict(TABLE_ACCESS_TYPE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.close(); // Closing database connection

		return res;
	}



	public List<access_type_model> getAccessType() {
		List<access_type_model> scdLists = new ArrayList<access_type_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ACCESS_TYPE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				access_type_model list = new access_type_model();

				list.setAccess_type(cursor.getString(cursor.getColumnIndex(TABLE_ACCESS_TYPE)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_KID_ID)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}

	// Adding new PARENT detail
	public Long addParentEdit(parent_model List) {
		SQLiteDatabase db = this.getWritableDatabase();


		db.execSQL("delete from "+ TABLE_PARENT);
		db.execSQL("delete from sqlite_sequence where name=  'parent_table' " );

		ContentValues values = new ContentValues();
		values.put(P_NAME, List.getPname());
		values.put(P_EMAIL, List.getP_email());
		values.put(P_PASS, List.getPass());
		values.put(FIRST_TIME, List.getFirst());
		values.put(SUBSCRIBED, List.getSubscribed());
		values.put(ELAPSED_TIME, List.getElapsed_time());
		values.put(END_DATE, List.getEnd_date());
		values.put(START_DATE, List.getStart_date());
		values.put(DURATION, List.getDuration());
		values.put(PACKAGE_NAME, List.getPackage_name());


		long res= db.insertWithOnConflict(TABLE_PARENT, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}
// Adding new PARENT detail
	public Long addParent(parent_model List) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEB_SETTINGS);
		db.execSQL("DROP TABLE IF EXISTS " + WEDNESDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TUESDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + THURSDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SUNDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SATURDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + MONDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FRIDAY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_LIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESS_TYPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDIT);


		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME_SETTING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE_OWNER);






		db.execSQL(CREATE_TABLE_SETTINGS);
		db.execSQL(CREATE_TABLE_WEB_SETTINGS);
		db.execSQL(CREATE_TABLE_THURSDAY);
		db.execSQL(CREATE_TABLE_MONDAY);
		db.execSQL(CREATE_TABLE_TUESDAY);
		db.execSQL(CREATE_TABLE_WEDNESDAY);
		db.execSQL(CREATE_TABLE_FRIDAY);
		db.execSQL(CREATE_TABLE_SATURDAY);
		db.execSQL(CREATE_TABLE_SUNDAY);
		db.execSQL(CREATE_TABLE_CHILD);
		db.execSQL(TABLE_APPLIST);
		db.execSQL(TABLE_ACCESS);
		db.execSQL(CREATE_TABLE_PARENT);
		db.execSQL(TABLE_ACTIVE_USER);
		db.execSQL(CREATE_TABLE_STATISTICS);
		db.execSQL(TABLE_CREATE_CREDIT);


		db.execSQL(CREATE_TABLE_GOALS);

		db.execSQL(TABLE_LOCK_HOME);
		db.execSQL(TABLE_ACTIVE_CHILD);
		//db.execSQL("delete from "+ TABLE_PARENT);
		//db.execSQL("delete from sqlite_sequence where name=  'parent_table' " );

		ContentValues values = new ContentValues();
		values.put(P_NAME, List.getPname());
		values.put(P_EMAIL, List.getP_email());
		values.put(P_PASS, List.getPass());
		values.put(FIRST_TIME, List.getFirst());
		values.put(SUBSCRIBED, List.getSubscribed());
		values.put(ELAPSED_TIME, List.getElapsed_time());
		values.put(END_DATE, List.getEnd_date());
		values.put(START_DATE, List.getStart_date());
		values.put(DURATION, List.getDuration());
		values.put(PACKAGE_NAME, List.getPackage_name());


		long res= db.insertWithOnConflict(TABLE_PARENT, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}


	public Long UpdateParent(String value, String pemail) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


		ContentValues values = new ContentValues();


		values.put(FIRST_TIME, value);



		res= db.update(TABLE_PARENT, values, P_EMAIL + " = ?",
				new String[] {pemail});

		db.close(); // Closing database connection

		return res;
	}



	public Long UpdatePassParent(String value, String pemail) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


		ContentValues values = new ContentValues();


		values.put(P_PASS, value);



		res= db.update(TABLE_PARENT, values, P_EMAIL + " = ?",
				new String[] {pemail});

		db.close(); // Closing database connection

		return res;
	}

	public Long UpdateSubscribeParent(String value , String pemail, Long elapsed, String enddate, String startdate,
									  String duration,String packagename) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


		ContentValues values = new ContentValues();


		values.put(SUBSCRIBED, value);
		values.put(ELAPSED_TIME, elapsed);
		values.put(END_DATE, enddate);
		values.put(START_DATE, startdate);
		values.put(DURATION, duration);
		values.put(PACKAGE_NAME, packagename);



		res= db.update(TABLE_PARENT, values, P_EMAIL + " = ?",
				new String[] {pemail});

		db.close(); // Closing database connection

		return res;
	}
public Long UpdateSubscribeParent(String value , String pemail) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


		ContentValues values = new ContentValues();


		values.put(SUBSCRIBED, value);




		res= db.update(TABLE_PARENT, values, P_EMAIL + " = ?",
				new String[] {pemail});

		db.close(); // Closing database connection

		return res;
	}
public Long UpdateStartDateParent(String date , Long elapsed, String pemail) {
		SQLiteDatabase db = this.getWritableDatabase();


		//onUpgrade(db);
		long res=0;


		ContentValues values = new ContentValues();


		values.put(START_DATE, date);
		values.put(ELAPSED_TIME, elapsed);




		res= db.update(TABLE_PARENT, values, P_EMAIL + " = ?",
				new String[] {pemail});

		db.close(); // Closing database connection

		return res;
	}


	public List<parent_model> getParent() {
		List<parent_model> scdLists = new ArrayList<parent_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PARENT ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				parent_model list = new parent_model();

				list.setPname(cursor.getString(cursor.getColumnIndex(P_NAME)));
				list.setP_email(cursor.getString(cursor.getColumnIndex(P_EMAIL)));
				list.setP_pass(cursor.getString(cursor.getColumnIndex(P_PASS)));
				list.setFirst(cursor.getString(cursor.getColumnIndex(FIRST_TIME)));
				list.setSubscribed(cursor.getString(cursor.getColumnIndex(SUBSCRIBED)));
				list.setElapsed_time(cursor.getLong(cursor.getColumnIndex(ELAPSED_TIME)));
				list.setEnd_date(cursor.getString(cursor.getColumnIndex(END_DATE)));
				list.setStart_date(cursor.getString(cursor.getColumnIndex(START_DATE)));
				list.setDuration(cursor.getString(cursor.getColumnIndex(DURATION)));
				list.setPackage_name(cursor.getString(cursor.getColumnIndex(PACKAGE_NAME)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}



// Adding new ACTIVE USER detail
	public Long addActiveUser(active_user_model List) {
		SQLiteDatabase db = this.getWritableDatabase();

		//onUpgrade(db);
		db.execSQL("delete from "+ TABLE_CREDENTIALS);
		db.execSQL("delete from sqlite_sequence where name=  'active_user_details' " );

		ContentValues values = new ContentValues();
		values.put(KID_ID, List.getKid_id());
		values.put(IS_LOGGEDIN, List.getislogged());
		values.put(TIMED, List.getTimed());



		long res= db.insertWithOnConflict(TABLE_CREDENTIALS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}



	public List<active_user_model> getActiveUser() {
		List<active_user_model> scdLists = new ArrayList<active_user_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CREDENTIALS ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				active_user_model list = new active_user_model();

				list.setKid_id(cursor.getString(cursor.getColumnIndex(KID_ID)));
				list.setIs_loggedin(cursor.getString(cursor.getColumnIndex(IS_LOGGEDIN)));
				list.setTimed(cursor.getString(cursor.getColumnIndex(TIMED)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}




// Adding new MONDAY detail
	public Long addMonday(List<mon_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ MONDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'monday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			mon_model List = new mon_model();
			List = list2.get(r);
			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


		        res = db.insertWithOnConflict(MONDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<mon_model> getMonday() {
		List<mon_model> scdLists = new ArrayList<mon_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + MONDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				mon_model list = new mon_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}


	// Adding new TUESDAY detail
	public Long addTuesday(List<tues_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		//onUpgrade(db);
		db.execSQL("delete from "+ TUESDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'tuesday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			tues_model List = new tues_model();
			List = list2.get(r);

			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


			res = db.insertWithOnConflict(TUESDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<tues_model> getTuesday() {
		List<tues_model> scdLists = new ArrayList<tues_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TUESDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				tues_model list = new tues_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}


// Adding new WEDNESDAY detail
	public Long addWednesday(List<wed_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ WEDNESDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'wednesday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			wed_model List = new wed_model();
			List = list2.get(r);


			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


			res = db.insertWithOnConflict(WEDNESDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<wed_model> getWednesday() {
		List<wed_model> scdLists = new ArrayList<wed_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + WEDNESDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				wed_model list = new wed_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}

// Adding new THURSDAY detail
	public Long addThursday(List<thurs_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ THURSDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'thursday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			thurs_model List = new thurs_model();
			List = list2.get(r);


			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


			 res = db.insertWithOnConflict(THURSDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<thurs_model> getThursday() {
		List<thurs_model> scdLists = new ArrayList<thurs_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + THURSDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				thurs_model list = new thurs_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}


// Adding new FRIDAY detail
	public Long addFriday(List<fri_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ FRIDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'friday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			fri_model List = new fri_model();
			List = list2.get(r);


			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


			 res = db.insertWithOnConflict(FRIDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<fri_model> getFriday() {
		List<fri_model> scdLists = new ArrayList<fri_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + FRIDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				fri_model list = new fri_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}


// Adding new SATURDAY detail
	public Long addSaturday(List<sat_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from "+ SATURDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'saturday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			sat_model List = new sat_model();
			List = list2.get(r);

			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


			res = db.insertWithOnConflict(SATURDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.close(); // Closing database connection

		return res;
	}



	public List<sat_model> getSaturday() {
		List<sat_model> scdLists = new ArrayList<sat_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + SATURDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				sat_model list = new sat_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}




// Adding new SUNDAY detail
	public Long addSunday(List<sun_model> list2) {
		SQLiteDatabase db = this.getWritableDatabase();


		db.execSQL("delete from "+ SUNDAY_TABLE);
		db.execSQL("delete from sqlite_sequence where name=  'sunday' " );

		//onUpgrade(db);
		long res=0;
		for(int r=0; r<=list2.size()-1; r++) {
			sun_model List = new sun_model();
			List = list2.get(r);


			ContentValues values = new ContentValues();
			values.put(KEY_start_time, List.getStart_time());
			values.put(KEY_end_time, List.getEnd_time());
			values.put(KEY_access_type, List.getAccess_type());
			values.put(KEY_kid_id, List.getKid_id());


			res = db.insertWithOnConflict(SUNDAY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}
		//db.close(); // Closing database connection

		return res;
	}



	public List<sun_model> getSunday() {
		List<sun_model> scdLists = new ArrayList<sun_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + SUNDAY_TABLE ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				sun_model list = new sun_model();

				list.setStart_time(cursor.getString(cursor.getColumnIndex(KEY_start_time)));
				list.setEnd_time(cursor.getString(cursor.getColumnIndex(KEY_end_time)));
				list.setAccess_type(cursor.getString(cursor.getColumnIndex(KEY_access_type)));
				list.setKid_id(cursor.getString(cursor.getColumnIndex(KEY_kid_id)));




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}


	// Adding new stats detail
	public Long addstatistics(statistics_model List) {
		SQLiteDatabase db = this.getWritableDatabase();



		ContentValues values = new ContentValues();
		values.put(C_ID, List.getCid());
		values.put(APP_NAME, List.getApp_name());
		values.put(TIMES_OPEN, List.getTimes_opened());
		values.put(DATE, List.getDate_open());
		values.put(APP_ICON, "icon");  //WORK);


		long res= db.insertWithOnConflict(TABLE_STATISTICS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}

	public Long addstatistics(statistics_model List, String app_name, String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		statistics_model list = new statistics_model();

		String count= "1";

      list=  getstatistics(app_name, id);

      if(list!=null){
      count= String.valueOf(Integer.parseInt(list.getTimes_opened()) + 1);

      Parent.current_app_click_count=count;
	  }

		ContentValues values = new ContentValues();
		values.put(C_ID, List.getCid());
		values.put(APP_NAME, List.getApp_name());
		values.put(TIMES_OPEN, count);
		values.put(DATE, List.getDate_open());
		values.put(P_ID, List.getP_ID());
		values.put(APP_ICON, "icon");  //WORK);


		long res=   db.update(TABLE_STATISTICS, values, APP_NAME + " = ?  AND " + C_ID + " = ? ",
				new String[] { app_name, id });

		if(res==0){
			 db.insertWithOnConflict(TABLE_STATISTICS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		}
		//db.close(); // Closing database connection

		return res;
	}


	public Long addstatistics_from_internet(statistics_model List, String app_name, String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		statistics_model list = new statistics_model();


		//list=  getstatistics(app_name, id);



		ContentValues values = new ContentValues();
		values.put(C_ID, List.getCid());
		values.put(APP_NAME, List.getApp_name());
		values.put(TIMES_OPEN, List.getTimes_opened());
		values.put(DATE, List.getDate_open());
		values.put(P_ID, List.getP_ID());
		values.put(APP_ICON, "icon");  //WORK);


			long res= db.insertWithOnConflict(TABLE_STATISTICS, null, values, SQLiteDatabase.CONFLICT_REPLACE);


		return res;
	}



	public List<statistics_model> getstatistics(String id) {
		List<statistics_model> scdLists = new ArrayList<statistics_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE " + C_ID + " = " + "'" + id + "'";

		SQLiteDatabase db = this.getWritableDatabase();


		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				statistics_model list = new statistics_model();

				list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
				list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
				list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
				list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
				list.setApp_Icon(null);




				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}

	public List<statistics_model> getstatistics(String id, Date date, Activity con, String command) throws ParseException {
		List<statistics_model> scdLists = new ArrayList<statistics_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE " + C_ID + " = " + "'" + id + "'";

		SQLiteDatabase db = this.getWritableDatabase();


		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				if(command.equals("today")) {
					//SimpleDateFormat formatterOut = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
					SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

					String store_date = cursor.getString(cursor.getColumnIndex(DATE));


					Calendar date2 = Calendar.getInstance();
					String today = formateDate2.format(date2.getTime());
				/*try {
					Handler handle = new Handler(Looper.getMainLooper());
					handle.post(new Runnable() {
									@Override
									public void run() {


										StyleableToast.makeText(con,yesterday,
						Toast.LENGTH_LONG, R.style.mytoast).show();

									}
					});
				}
				catch (Exception d){

				}
*/
					if (store_date.equals(today)) {

						statistics_model list = new statistics_model();

						list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
						list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
						list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
						list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
						list.setP_ID(cursor.getString(cursor.getColumnIndex(P_ID)));
						list.setApp_Icon(null);


						scdLists.add(list);
					}
				}


				else if(command.equals("yesterday")) {
					//SimpleDateFormat formatterOut = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
					SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

					String store_date = cursor.getString(cursor.getColumnIndex(DATE));

					//Date data = formateDate2.parse(hi);


					Calendar date2 = Calendar.getInstance();
					date2.add(Calendar.DATE, -1);

					String yesterday = formateDate2.format(date2.getTime());

					if (store_date.equals(yesterday)) {

						statistics_model list = new statistics_model();

						list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
						list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
						list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
						list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
						list.setP_ID(cursor.getString(cursor.getColumnIndex(P_ID)));
						list.setApp_Icon(null);


						scdLists.add(list);
					}
				}

				else if(command.equals("week")){
					SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

					String store_date = cursor.getString(cursor.getColumnIndex(DATE));

					Date data = formateDate2.parse(store_date);


					Calendar date2 = Calendar.getInstance();
					date2.setFirstDayOfWeek(Calendar.MONDAY);
					date2.set(Calendar.DAY_OF_WEEK, date2.getFirstDayOfWeek());
					date2.add(Calendar.DAY_OF_WEEK, -1); //this week

					String startDate_lastweek=formateDate2.format(date2.getTime());
					String endDate_lastweek;

					date2.add(Calendar.DAY_OF_MONTH, 6);
					endDate_lastweek=formateDate2.format(date2.getTime());

					//String week = formateDate2.format(date2.getTime());
					Date this_week_end =  formateDate2.parse(endDate_lastweek);
					Date this_week_start =  formateDate2.parse(startDate_lastweek);


					if (data.compareTo(this_week_start)>0 && data.compareTo(this_week_end)<=0) {

						statistics_model list = new statistics_model();

						list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
						list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
						list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
						list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
						list.setP_ID(cursor.getString(cursor.getColumnIndex(P_ID)));
						list.setApp_Icon(null);


						scdLists.add(list);
					}
				}

				else if(command.equals("last week")){
					SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

					String store_date = cursor.getString(cursor.getColumnIndex(DATE));

					Date data = formateDate2.parse(store_date);


					Calendar date2 = Calendar.getInstance();
					date2.setFirstDayOfWeek(Calendar.MONDAY);
					date2.set(Calendar.DAY_OF_WEEK, date2.getFirstDayOfWeek());
					date2.add(Calendar.DAY_OF_WEEK, -8); //last week

					String startDate_lastweek=formateDate2.format(date2.getTime());
					String endDate_lastweek;

					date2.add(Calendar.DAY_OF_MONTH, 6);
					endDate_lastweek=formateDate2.format(date2.getTime());

					//String week = formateDate2.format(date2.getTime());
					Date last_week_end =  formateDate2.parse(endDate_lastweek);
					Date last_week_start =  formateDate2.parse(startDate_lastweek);

				/*	try {
						Handler handle = new Handler(Looper.getMainLooper());
						handle.post(new Runnable() {
							@Override
							public void run() {


								StyleableToast.makeText(con,startDate_lastweek + "  end: " +endDate_lastweek,
										Toast.LENGTH_LONG, R.style.mytoast).show();

							}
						});
					}
					catch (Exception d){

					}
*/

					if (data.compareTo(last_week_start)>=0 && data.compareTo(last_week_end)<=0) {

						statistics_model list = new statistics_model();

						list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
						list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
						list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
						list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
						list.setP_ID(cursor.getString(cursor.getColumnIndex(P_ID)));
						list.setApp_Icon(null);


						scdLists.add(list);
					}
				}

				else if(command.equals("last month")){
					SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-yyyy", Locale.US);

					String store_date = cursor.getString(cursor.getColumnIndex(DATE));

					Date data = formateDate2.parse(store_date);


					Calendar date2 = Calendar.getInstance();
					date2.add(Calendar.MONTH, -1); //last month


					String last_mnt =formateDate2.format(date2.getTime());


					Date last_month =  formateDate2.parse(last_mnt);

				/*	try {
						Handler handle = new Handler(Looper.getMainLooper());
						handle.post(new Runnable() {
							@Override
							public void run() {


								StyleableToast.makeText(con,startDate_lastweek + "  end: " +endDate_lastweek,
										Toast.LENGTH_LONG, R.style.mytoast).show();

							}
						});
					}
					catch (Exception d){

					}

				 */


					if (data.compareTo(last_month) ==0 ) {

						statistics_model list = new statistics_model();

						list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
						list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
						list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
						list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
						list.setP_ID(cursor.getString(cursor.getColumnIndex(P_ID)));
						list.setApp_Icon(null);


						scdLists.add(list);
					}
				}
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}

	public statistics_model getstatistics(String app_name, String id) {
		// Select All Query


		SQLiteDatabase db = this.getWritableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE " + APP_NAME + " = " + "'" + app_name + "'"
				+  "AND "  + C_ID + " = " +  "'" + id + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		statistics_model list = new statistics_model();

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				list.setCid(cursor.getString(cursor.getColumnIndex(C_ID)));
				list.setApp_name(cursor.getString(cursor.getColumnIndex(APP_NAME)));
				list.setTimes_opened(cursor.getString(cursor.getColumnIndex(TIMES_OPEN)));
				list.setDate_open(cursor.getString(cursor.getColumnIndex(DATE)));
				list.setP_ID(cursor.getString(cursor.getColumnIndex(P_ID)));
				list.setApp_Icon(null);





			} while (cursor.moveToNext());
		}

		// return category list
		return list;
	}









	public List<Internet_Reg_stats_model> getInternet_reg_stats() {
		List<Internet_Reg_stats_model> scdLists = new ArrayList<Internet_Reg_stats_model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_INTERNET_REG_STATUS ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Internet_Reg_stats_model list = new Internet_Reg_stats_model();

				list.setHas_reg(cursor.getString(cursor.getColumnIndex(REG_STATUS)));
				list.setTimes(cursor.getString(cursor.getColumnIndex(TODAY_STATUS)));
				list.setOffline_changes(cursor.getString(cursor.getColumnIndex(OFFLINE_CHANGES)));
				list.setOffline_count(cursor.getString(cursor.getColumnIndex(OFFLINE_COUNT)));





				scdLists.add(list);
			} while (cursor.moveToNext());
		}

		// return category list
		return scdLists;
	}



	// Adding new ACTIVE USER detail
	public Long addIsRegInternet(Internet_Reg_stats_model list) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_INTERNET_REG_STATUS);
		db.execSQL("delete from sqlite_sequence where name=  'internet_registration_status' " );

		ContentValues values = new ContentValues();
		values.put(REG_STATUS, list.getHas_reg());
		values.put(TODAY_STATUS,list.getTimes());
		values.put(OFFLINE_CHANGES,list.getOffline_changes());
		values.put(OFFLINE_COUNT,list.getOffline_count());




		long res= db.insertWithOnConflict(TABLE_INTERNET_REG_STATUS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

		db.close(); // Closing database connection

		return res;
	}





}
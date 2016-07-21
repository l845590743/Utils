
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 */
public class PreferenceUtil {
	private static final String FILE_NAME = "config";

	public static void putBoolean(Context context, String key, boolean value){
		//(name xml文件名, mode 权限);
		SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		//写入操作基于事务，必须手动提交
		editor.commit();
	}
	
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean(key, defValue);
	}	
	
	public static void putString(Context context, String key, String value){
		//(name xml文件名, mode 权限);
		SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		//写入操作基于事务，必须手动提交
		editor.commit();
	}
	
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getString(key, defValue);
	}	
	
	public static void putInt(Context context, String key, int value){
		//(name xml文件名, mode 权限);
		SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt(key, value);
		//写入操作基于事务，必须手动提交
		editor.commit();
	}
	
	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt(key, defValue);
	}	
}

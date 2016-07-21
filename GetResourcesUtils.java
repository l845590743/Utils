
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;


/**
 * 常用工具方法,如获取各种资源的方法(字符串，颜色，dimens资源)
 *
 */
public class Utils {
    /**
     * 在主线程执行任务
     */
    public static void runOnUIThread(Runnable runnable){
        App.mainHandler.post(runnable);
    }

    public static String getString(int resId){
        return App.context.getResources().getString(resId);
    }

    public static String[] getStringArray(int resId){
        return App.context.getResources().getStringArray(resId);
    }

    public static int getColor(int resId){
        return App.context.getResources().getColor(resId);
    }

    public static Drawable getDrawable(int resId){
        return App.context.getResources().getDrawable(resId);
    }

    /**
     * 获取定义的dp资源,并且会自动将dp值转为像素
     * @param resId
     * @return
     */
    public static int getDimens(int resId){
        return App.context.getResources().getDimensionPixelSize(resId);
    }

    public static ArrayList<PagerInfo> initFragment(Fragment[] fragments, String[] titles) {

        ArrayList<PagerInfo> infos = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            infos.add(new PagerInfo(fragments[i],titles[i]));

        }
        return infos;

    }
}

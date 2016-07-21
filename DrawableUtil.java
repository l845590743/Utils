
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;


public class DrawableUtil {
    /**
     * 生成shape图像
     * @return
     */
    public static GradientDrawable generateDrawable(float radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置矩形
        drawable.setCornerRadius(radius);//设置角度
        drawable.setColor(ColorUtil.randomBeautifulColor());
        return drawable;
    }

    /**
     * 动态生成状态选择器
     * @return
     */
    public static StateListDrawable generateSelector(Drawable normal,Drawable pressed){
        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed},pressed);//设置按下状态对应的图片
        listDrawable.addState(new int[]{},normal);//添加默认状态对应的图片
        return listDrawable;
    }
}


import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {
    private static Toast toast;
    public static void shwoToast(String text){
        if(toast==null){
            //创建toast
            toast = Toast.makeText(osChinaApp.context,text,Toast.LENGTH_SHORT);
        }
        //如果吐司已经创建，那么直接更改吐司的文本即可
        toast.setText(text);
        //最后显示
        toast.show();
    }
}

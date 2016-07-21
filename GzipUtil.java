import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

 
public class GzipUtil {
	
	/**
	 * 对文件进行压缩
	 * @param src 未压缩的原始文件
	 * @param dest 压缩后的目标文件
	 */
	public static void zip(File src, File dest){
		InputStream in = null;
		GZIPOutputStream gos = null;
		try {
			//得到文件输入流
			in = new FileInputStream(src);
			
			//要将原始文件压缩到那个文件里
			OutputStream out = new FileOutputStream(dest);
			//压缩
			gos = new GZIPOutputStream(out);
			//将输入流写到压缩文件里
			int len = -1;
			byte[] buffer = new byte[1024]; 
			while ((len = in.read(buffer)) != -1) {
				gos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(in);
			close(gos);
		}
	}
	
	/**
	 * 对文件进行解压
	 * @param zip 需要进行解压的压缩包
	 * @param dest 压缩后的目标文件
	 * @throws ZipException 
	 */
	public static void upzip(File zip, File dest) throws ZipException{
		GZIPInputStream gis = null;
		OutputStream os = null;
		try {
			InputStream in = new FileInputStream(zip);
			//压缩输入流，用来读取压缩包文件流
			gis = new GZIPInputStream(in);
			
			//把压缩流写到普通文件里
			os = new FileOutputStream(dest);
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = gis.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
		}catch (ZipException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(gis);
			close(os);
		}
	}
	
	//关闭流
	public static void close(Closeable c){
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

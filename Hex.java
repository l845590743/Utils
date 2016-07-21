
/**
 * 字节数组与16进制字符串转换工具
 */
public class Hex {

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 字节数组转换成16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encode(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(bytes.length * 2);
		// 27对应的十六进制为1b,对应的二进制是00011011
		// 取高位和低位：00011011-》0001,1011-》1,b
		for (int i = 0; i < bytes.length; ++i) {
			// 取高位：跟0xf0做与运算后再右移4位
			int high = (bytes[i] & 0xf0) >> 4;// 0xf0: 11110000
			// 取低位：跟0x0f做与运算
			int low = bytes[i] & 0x0f;// 0x0f: 00001111
			// 字符映射
			sb.append(HEX_CHAR[high]).append(HEX_CHAR[low]);
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转换为字节数组
	 * 
	 * @param hex
	 *            16进制字符
	 * @return
	 */
	public static byte[] decode(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		// 16进制转byte，长度减半，"1b"-->27
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		String highStr = null;
		String lowStr = null;
		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			// 高位值
			highStr = hex.substring(i * 2, i * 2 + 1);// "1b"的高位为"1"
			high = Integer.parseInt(highStr, 16);// 高位转为10进制
			// 低位值
			lowStr = hex.substring(i * 2 + 1, i * 2 + 2);// "1b"的低位为"b"
			low = Integer.parseInt(lowStr, 16);// 低位转为10进制
			// 合计值
			result[i] = (byte) ((high << 4) + low);// 相当于:(高位*16) + 低位
		}
		return result;
	}

}

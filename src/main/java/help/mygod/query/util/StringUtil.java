package help.mygod.query.util;

import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import help.mygod.query.constant.Constant;

public abstract class StringUtil extends StringUtils {

	/**
	 * (0,1)之间的数
	 * 
	 * @return
	 */
	public static boolean is0And1(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("^0\\.\\d*[1-9]+\\d*");
	}

	/**
	 * 连接字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String contact(String... strs) {
		StringBuilder sb = new StringBuilder();
		for (String s : strs) {
			if (isNotBlank(s)) {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	/**
	 * 脱敏字符串
	 * 
	 * @param first
	 *            前面显示几个
	 * @param tail
	 *            后面显示几个
	 * @return
	 */
	public static String desensitization(int first, int tail, String str) {
		if (StringUtils.isNotBlank(str)) {
			int len = str.length();
			if (len - first - tail > 0) {
				return str.substring(0, first) + StringUtils.repeat("*", len - first - tail)
						+ str.substring(len - tail, len);
			}
			return str;
		} else {
			return Constant.EMPTY;
		}
	}
	
	/**
	  * 生成随即密码
	  * @param pwd_len 生成的密码的总长度
	  * @return  密码的字符串
	  */
	 public static String genRandomNum(int pwd_len){
	  //35是因为数组是从0开始的，26个字母+10个数字
	  final int  maxNum = 36;
	  int i;  //生成的随机数
	  int count = 0; //生成的密码的长度
	  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
	    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	  
	  StringBuffer pwd = new StringBuffer("");
	  Random r = new Random();
	  while(count < pwd_len){
	   //生成随机数，取绝对值，防止生成负数，
	   
	   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
	   
	   if (i >= 0 && i < str.length) {
	    pwd.append(str[i]);
	    count ++;
	   }
	  }
	  
	  return pwd.toString();
	 }
	 
	 public static String lowerCaseName(String name) {
			return name.toLowerCase(Locale.US);
		}
}

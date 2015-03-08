package cn.com.secbuy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: DateConver
 * @Description: TODO(日期转化操作)
 * @author fan
 * @date 2015年3月2日 上午1:04:10
 * @version V1.0
 */
public class DateConver {
	/**
	 * 获取时间戳
	 * 
	 * @param date
	 *            日期
	 * @return String 时间戳
	 */
	public static String getTimeStamp(Date date) {
		long timeStamp = date.getTime();
		return String.valueOf(timeStamp);
	}

	/**
	 * 时间转换YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @param date
	 *            日期
	 * @return String YYYY-MM-DD HH:mm:ss格式时间
	 */
	public static String converDateToYYYYMMDDHHMMSS(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
		return dateFormat.format(date);
	}
}

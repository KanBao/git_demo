import java.math.BigDecimal;

/**
 * 用于将角度转换为X°X′X″
 * @author Administrator
 */
public class TestDegrees {

	/**
	 * 用于将输入的小数角度转换为StringBuffer的度分秒
	 * @param degree float型输入
	 * @return StringBuffer
	 */
	public static StringBuffer toDegMinSec(float degree) {
		int hour=(int)degree;
		int min=(int)((degree-hour)*60);
		int sec=(int)(((degree-hour)*60-min)*60);
		StringBuffer sb = new StringBuffer();
		sb.append(hour).append("°").append(min).append("′").append(sec).append("″");
		//System.out.println(hour+"   "+min+"   "+sec);
		//System.out.println(sb);
		return sb;
	}

	/**
	 * 用于指定小数的位数为2位，并四舍五入保留2位小数位
	 * @param orif 原始需要转换的小数
	 * @return float 只有两位小数位
	 */
	public static float twoDigit(float orif){
		BigDecimal bd=new BigDecimal(orif);
		float f1 =bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		//System.out.println(f1);
		return f1;
	}
}

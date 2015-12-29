import java.math.BigDecimal;

/**
 * ���ڽ��Ƕ�ת��ΪX��X��X��
 * @author Administrator
 */
public class TestDegrees {

	/**
	 * ���ڽ������С���Ƕ�ת��ΪStringBuffer�Ķȷ���
	 * @param degree float������
	 * @return StringBuffer
	 */
	public static StringBuffer toDegMinSec(float degree) {
		int hour=(int)degree;
		int min=(int)((degree-hour)*60);
		int sec=(int)(((degree-hour)*60-min)*60);
		StringBuffer sb = new StringBuffer();
		sb.append(hour).append("��").append(min).append("��").append(sec).append("��");
		//System.out.println(hour+"   "+min+"   "+sec);
		//System.out.println(sb);
		return sb;
	}

	/**
	 * ����ָ��С����λ��Ϊ2λ�����������뱣��2λС��λ
	 * @param orif ԭʼ��Ҫת����С��
	 * @return float ֻ����λС��λ
	 */
	public static float twoDigit(float orif){
		BigDecimal bd=new BigDecimal(orif);
		float f1 =bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		//System.out.println(f1);
		return f1;
	}
}

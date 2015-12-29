import java.util.*;

//���ڽ������յ��Ķ�����Ϣ����
public class RecAnalyse {

	//������Ϣ������������
	static byte mot_info[]=new byte[]{
		(byte)(byte)0xaa, (byte)(byte)0x55, (byte)(byte)0x40, (byte)(byte)0x00,
		(byte)(byte)0x03, (byte)(byte)0x08, (byte)(byte)0x01, (byte)(byte)0x00, 
		(byte)(byte)0x0c, (byte)(byte)0xb6, (byte)(byte)0x90, (byte)(byte)0x14, 
		(byte)(byte)0x00, (byte)(byte)0x35, (byte)(byte)0x08, (byte)(byte)0xeb,
		(byte)(byte)0x37, (byte)(byte)0x2b, (byte)(byte)0x00, (byte)(byte)0x2f, 
		(byte)(byte)0x12, (byte)(byte)0x37, (byte)(byte)0x0c, (byte)(byte)0xfd, 
		(byte)(byte)0x17, (byte)(byte)0x42, (byte)(byte)0x21, (byte)(byte)0xab, 
		(byte)(byte)0x09, (byte)(byte)0x66, (byte)(byte)0x18, (byte)(byte)0x52, 
		(byte)(byte)0x16, (byte)(byte)0x72, (byte)(byte)0x08, (byte)(byte)0x99,
		(byte)(byte)0x06, (byte)(byte)0x07, (byte)(byte)0x12, (byte)(byte)0x1a, 
		(byte)(byte)0x14, (byte)(byte)0x56, (byte)(byte)0xc3, (byte)(byte)0xae, 
		(byte)(byte)0x0c, (byte)(byte)0xdd, (byte)(byte)0x04, (byte)(byte)0x46, 
		(byte)(byte)0x18, (byte)(byte)0xe8, (byte)(byte)0x38, (byte)(byte)0x00,
		(byte)(byte)0x00, (byte)(byte)0x00, (byte)(byte)0x00, (byte)(byte)0x00,
		(byte)(byte)0x00, (byte)(byte)0x00, (byte)(byte)0x00, (byte)(byte)0x00,
		(byte)(byte)0x48, (byte)(byte)0x48, (byte)(byte)0x00, (byte)(byte)0xff
	};
	

	static List<Short> start_flag=new ArrayList<Short>();
	//���ڴ��byte[]�����б��ĵ���ʼλ��
	
	static List<Short> length_flag=new ArrayList<Short>();
	//���ڴ�����Ӧ�ı��ĵĳ���
	
	static List<Short> device_flag=new ArrayList<Short>();
	//���ڴ�����Ӧ�ı��ĵ��豸��ʶ
	
	static List<Short> type_flag=new ArrayList<Short>();
	//���ڴ�����Ӧ�ı��ĵı�������
	
	static List<Integer> time_flag=new ArrayList<Integer>();
	//���ڴ�����Ӧ�ı��ĵ�ʱ��
	
	public static void main(String[] args) {
		findStart(mot_info);
		findLength(mot_info);
		findDevice(mot_info);
		findType(mot_info);
		findTime(mot_info);
		printCollect(start_flag,10);
		System.out.println();
		printCollect(length_flag,10);
		System.out.println();
		printCollect(device_flag,16);
		System.out.println();
		printCollect(type_flag,16);
		System.out.println();
		printCollect(time_flag,"");
	}

	//�������е�����Ԫ�ش�ӡ(short)
	public static void printCollect(Collection<Short> con,int i){
		if(i==10){
			for(Iterator<Short> it = con.iterator(); it.hasNext(); ) {
				Short s = it.next();
				System.out.println(s);
			}
		}
		//ʮ�������
		
		
		if(i==16){
			for(Iterator<Short> it = con.iterator(); it.hasNext(); ) {
				Short s = it.next();
				toIntHex(toCharBits(s),16);
				System.out.println();
			}
		}
		//ʮ���������
	}
	
	//�������е�����Ԫ�ش�ӡ(integer)
	public static void printCollect(Collection<Integer> con,String str){
		for(Iterator<Integer> it = con.iterator(); it.hasNext(); ) {
			Integer i = it.next();
			toIntHex(toCharBits(i),32);
			System.out.println();
		}
	}
	
	//�ҳ�byte�����е�0X55AA(ʮ����Ϊ21930)�������ĵ���ʼλ�ã�������int������
	public static void findStart(byte[] rec){
		int size=rec.length;
		for(short i=0;i<size;i+=2){
			int c=((0xff00&(rec[i+1]<<8))+(0x00ff&rec[i]));
			if(c==0X55AA){
				start_flag.add(i);
			}
		}
	}
	
	//�ҳ�ÿһ�����ĺ����ŵ����Ӧ���ĵĳ���
	public static void findLength(byte[] rec){
		for(int i=0;i<start_flag.size();i++){
			length_flag.add((short) ((0xff00&(rec[start_flag.get(i)+3]<<8))+(0x00ff&rec[start_flag.get(i)+2])));
		}
	}
	
	//�ҳ�ÿһ�����ĺ����ŵ����Ӧ�ķ��ͷ���ʶ
	public static void findDevice(byte[] rec){
		for(int i=0;i<start_flag.size();i++){
			device_flag.add((short) ((0xff00&(rec[start_flag.get(i)+5]<<8))+(0x00ff&rec[start_flag.get(i)+4])));
		}
	}
	
	//�ҳ�ÿһ�����ĺ����ŵ����Ӧ�ı�������
	public static void findType(byte[] rec){
		for(int i=0;i<start_flag.size();i++){
			type_flag.add((short) ((0xff00&(rec[start_flag.get(i)+7]<<8))+(0x00ff&rec[start_flag.get(i)+6])));
		}
	}
	
	//�ҳ�ÿһ�����ĺ����ŵ����Ӧ��ʱ����ʶ
	public static void findTime(byte[] rec){
		//��ʼ���ҵ���INTEGERʱ��
		for(int i=0;i<start_flag.size();i++){
			time_flag.add(((0xff000000&(rec[start_flag.get(i)+11]<<24))+(0x00ff0000&(rec[start_flag.get(i)+10]<<16))+(0x0000ff00&(rec[start_flag.get(i)+9]<<8))+(0x000000ff&rec[start_flag.get(i)+8])));
		}
		
		//��ÿһ��ʱ������Ϊʱ���ַ���
		for(int i=0;i<time_flag.size();i++){
			time_flag.get(i);
		}
	}
	
	//��short����תΪ0��1�ı���char��������洢
	public static char[] toCharBits(short num){
		char bits[]=new char[16];
		for(int i=16-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}
	
	//��int����תΪ0��1�ı���char��������洢
	public static char[] toCharBits(int num){
		char bits[]=new char[32];
		for(int i=32-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}
	
	//��0��1�ı���char��������ÿһ�����
	public static void outputBits(char[] charBits) {
		for(int i=0;i<charBits.length;i++){
			System.out.print(charBits[i]);
		}
		System.out.println();
	}
	
	//��0��1�ı���char��������תΪʮ���������
	public static void toIntHex(char[] bits,int size){
		String str=new String(bits);
		int ii;
		for(int i=0;i<size;i=i+4){	
			ii=Integer.parseInt(str.substring(i, i+4),2);
			System.out.format("%x",ii);
		}
	}
}
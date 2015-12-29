import java.util.*;

//用于解析接收到的动力信息报文
public class RecAnalyse {

	//动力信息完整报文内容
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
	//用于存放byte[]数组中报文的起始位置
	
	static List<Short> length_flag=new ArrayList<Short>();
	//用于存放相对应的报文的长度
	
	static List<Short> device_flag=new ArrayList<Short>();
	//用于存放相对应的报文的设备标识
	
	static List<Short> type_flag=new ArrayList<Short>();
	//用于存放相对应的报文的报文类型
	
	static List<Integer> time_flag=new ArrayList<Integer>();
	//用于存放相对应的报文的时戳
	
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

	//将容器中的所有元素打印(short)
	public static void printCollect(Collection<Short> con,int i){
		if(i==10){
			for(Iterator<Short> it = con.iterator(); it.hasNext(); ) {
				Short s = it.next();
				System.out.println(s);
			}
		}
		//十进制输出
		
		
		if(i==16){
			for(Iterator<Short> it = con.iterator(); it.hasNext(); ) {
				Short s = it.next();
				toIntHex(toCharBits(s),16);
				System.out.println();
			}
		}
		//十六进制输出
	}
	
	//将容器中的所有元素打印(integer)
	public static void printCollect(Collection<Integer> con,String str){
		for(Iterator<Integer> it = con.iterator(); it.hasNext(); ) {
			Integer i = it.next();
			toIntHex(toCharBits(i),32);
			System.out.println();
		}
	}
	
	//找出byte数组中的0X55AA(十进制为21930)，即报文的起始位置，并放入int数组中
	public static void findStart(byte[] rec){
		int size=rec.length;
		for(short i=0;i<size;i+=2){
			int c=((0xff00&(rec[i+1]<<8))+(0x00ff&rec[i]));
			if(c==0X55AA){
				start_flag.add(i);
			}
		}
	}
	
	//找出每一个报文后面存放的相对应报文的长度
	public static void findLength(byte[] rec){
		for(int i=0;i<start_flag.size();i++){
			length_flag.add((short) ((0xff00&(rec[start_flag.get(i)+3]<<8))+(0x00ff&rec[start_flag.get(i)+2])));
		}
	}
	
	//找出每一个报文后面存放的相对应的发送方标识
	public static void findDevice(byte[] rec){
		for(int i=0;i<start_flag.size();i++){
			device_flag.add((short) ((0xff00&(rec[start_flag.get(i)+5]<<8))+(0x00ff&rec[start_flag.get(i)+4])));
		}
	}
	
	//找出每一个报文后面存放的相对应的报文类型
	public static void findType(byte[] rec){
		for(int i=0;i<start_flag.size();i++){
			type_flag.add((short) ((0xff00&(rec[start_flag.get(i)+7]<<8))+(0x00ff&rec[start_flag.get(i)+6])));
		}
	}
	
	//找出每一个报文后面存放的相对应的时戳标识
	public static void findTime(byte[] rec){
		//初始化找到的INTEGER时戳
		for(int i=0;i<start_flag.size();i++){
			time_flag.add(((0xff000000&(rec[start_flag.get(i)+11]<<24))+(0x00ff0000&(rec[start_flag.get(i)+10]<<16))+(0x0000ff00&(rec[start_flag.get(i)+9]<<8))+(0x000000ff&rec[start_flag.get(i)+8])));
		}
		
		//将每一个时戳解析为时间字符串
		for(int i=0;i<time_flag.size();i++){
			time_flag.get(i);
		}
	}
	
	//将short类型转为0、1的比特char类型数组存储
	public static char[] toCharBits(short num){
		char bits[]=new char[16];
		for(int i=16-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}
	
	//将int类型转为0、1的比特char类型数组存储
	public static char[] toCharBits(int num){
		char bits[]=new char[32];
		for(int i=32-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}
	
	//将0、1的比特char类型数组每一个输出
	public static void outputBits(char[] charBits) {
		for(int i=0;i<charBits.length;i++){
			System.out.print(charBits[i]);
		}
		System.out.println();
	}
	
	//将0、1的比特char类型数组转为十六进制输出
	public static void toIntHex(char[] bits,int size){
		String str=new String(bits);
		int ii;
		for(int i=0;i<size;i=i+4){	
			ii=Integer.parseInt(str.substring(i, i+4),2);
			System.out.format("%x",ii);
		}
	}
}
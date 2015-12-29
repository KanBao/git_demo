/**
 * 用于计算16位的反码校验和
 * @author Administrator
 */
public class ReverseSum {

	public static void main(String[] args) {
		byte[] temp={};
		float f=188.6f;
		System.out.println(f);
		short mot_cksum=checkSum(temp);
		char[] bits=toCharBits(mot_cksum,16);
		outputBits(bits);
		toIntHex(bits);
	}

	public static short checkSum(byte[] info){
		int size=info.length;
		int cksum=0;
		
		for(int i=0;i<size;i+=2){
			int c=((0xff00&(info[i+1]<<8))+(0x00ff&info[i]));
			//c必须是int类型，应为short为有符号数，相加后符号位会有影响
			cksum+=c;
		}
		while((cksum>>>16)!=0){
			cksum=(cksum>>>16)+(cksum&0x0000ffff);
		}
		
		return (short)(~cksum);
	}
	
	public static char[] toCharBits(int num,int size){
		char bits[]=new char[size];
		for(int i=size-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}
	
	public static void outputBits(char[] charBits) {
		for(int i=0;i<charBits.length;i++){
			System.out.print(charBits[i]);
		}
		System.out.println();
	}
	
	public static void toIntHex(char[] bits){
		String str=new String(bits);
		int ii;
		for(int i=0;i<16;i=i+4){	
			ii=Integer.parseInt(str.substring(i, i+4),2);
			System.out.format("%x",ii);
		}
	}
}

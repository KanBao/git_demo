/**
 * 用于计算小数、负的整数的二进制补码
 * @author Administrator
 */
public class TestBinComplement {

	public static void main(String[] args) {
		/*float f=29.6f;
		char[] fc=toCharBits(Float.floatToIntBits(f),32);
		outputBits(fc);
		toIntHex(fc);
		System.out.println();
		System.out.println((byte)0xf4);*/
		short s=-333;
		char[] sc=toCharBits(s,16);
		outputBits(sc);
		toIntHex(sc);
		/*int i=-2111;
		System.out.println(Integer.toBinaryString(i));
		toIntHex(Integer.toBinaryString(i));*/
	}
	
	public static void outputBits(char[] charBits) {
		for(int i=0;i<charBits.length;i++){
			System.out.print(charBits[i]);
		}
		System.out.println();
	}

	public static char[] toCharBits(long num,int size){
		char bits[]=new char[size];
		for(int i=size-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}
	
	public static char[] toCharBits(short num,int size){
		char bits[]=new char[size];
		for(int i=size-1;i>=0;--i){
			bits[i]=(num&1)==0?'0':'1';
			num>>>=1;
		}
		return bits;
	}

	public static void toIntHex(String str){
		int ii;
		for(int i=0;i<32;i=i+4){	
			ii=Integer.parseInt(str.substring(i, i+4),2);
			System.out.format("%x",ii);
		}
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

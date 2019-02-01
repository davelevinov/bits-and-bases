public class Bits {

	/**
	 * Given an 8-byte long composed of the bytes B_1, B_2, ... , B_8, return the long with byte order reversed:
	 * B_8, B_7, ..., B_1
	 * The implementation of this method shouldn't use any function calls.
	 * @param a the number to reverse
	 * @return
	 */
	public static long byteReverse(long a) {
		if (a == 0L) return 0;

		long reversedLong = 0L;
		int bytesNum = 8;
		int counter  = 0;

		while(counter < bytesNum){
			reversedLong <<= bytesNum;
			reversedLong |= a & 255L;
			a >>>= bytesNum;
			++counter;
		}
		return reversedLong;
	}
	
	/**
	 * Given a 32-bit integer composed of 32 bits: b_31,b_30,...b_1,b_0,  return the integer whose bit representation is
	 * b_{31-n},b_{30-n},...,b_1,b_0,b_31,...,b_{31-n+1}. 
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls.
	 * @param a the integer that we are rotating left (ROLing)
	 * @param n the number of bits to rotate.
	 * @return the ROL of a
	 */
	public static int rol(int a, int n) {
		return a << n | a >>> 32 - n;
	}

	/**
	 * Given two 32-bit integers a_31,...,a_0 and b_31,...,b_0, return the 64-bit long that contains their bits interleaved:
	 * a_31,b_31,a_30,b_30,...,a_0,b_0.
	 * The implementation of this method shouldn't use any function calls.
	 * @param a
	 * @param b
	 * @return
	 */
	public static long interleave(int a, int b) {
		long bBitTaker = 0L;
		long aBitTaker = 0L;

		// spacing the bits with 0s
		for(int i = 0; i < 32; ++i) {
			aBitTaker <<= 1;
			aBitTaker |= (long)(a >>> 31 & 1);
			aBitTaker <<= 1;
			a <<= 1;
			bBitTaker <<= 2;
			bBitTaker |= (long)(b >>> 31 & 1);
			b <<= 1;
		}
		// Or operator between the two creates interleaving
		return bBitTaker | aBitTaker;
	}
	
	/**
	 * Pack several values into a compressed 32-bit representation. 
	 * The packed representation should contain
	 * <table>
	 * <tr><th>bits</th>	<th>value</th></tr>
	 * <tr><td>31</td>		<td>1 if b1 is true, 0 otherwise</td></tr>
	 * <tr><td>30-23</td>	<td>the value of the byte a</td></tr>
	 * <tr><td>22</td>		<td>1 if b2 is true, 0 otherwise</td></tr>
	 * <tr><td>21-6</td>	<td>the value of the char c</td></tr>
	 * <tr><td>5-0</td>		<td>the constant binary value 101101</td></tr>
	 * </table>
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls
	 * (you may use the conditional operator "?:").
	 * @param a
	 * @param b1
	 * @param b2
	 * @param c
	 * @return
	 */
	public static int packStruct(byte a, boolean b1, boolean b2, char c) {
		return (b1? 1:0) << 31 | (a & 255) << 23 | (b2? 1:0) << 22 | c << 6 | 45;
	}
	
	/**
	 * Given a packed struct (with the same format as {@link #packStruct(byte, boolean, boolean, char)}, update
	 * its byte value (bits 23-30) to the new value a.
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls.
	 * @param struct
	 * @param a
	 * @return
	 */
	public static int updateStruct(int struct, byte a) {

		// this number has all bits 1s except those we want to turn zero
		return struct & -2139095041 | (a & 255) << 23;
	}
}

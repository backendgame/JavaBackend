package gameonline.config;

import java.util.ArrayList;
import java.util.Random;

public class RandomCache {
	private Random random;
	private Object lock;
	private long timeId;
	private RandomCache(){
		random=new Random();
		lock=new Object();
		timeId=0;
	}
	private static RandomCache instance=null;
	public static final RandomCache gI() {
		if(instance==null){
			instance=new RandomCache();
		}
		return instance;
	}
	
	public long randomTimeId() {
		synchronized (lock) {
			if(System.currentTimeMillis()>timeId)
				timeId=System.currentTimeMillis();
			else
				timeId++;
			return timeId;
		}
	}
	
	public final byte nextByte() {
		synchronized (lock) {
			return (byte) random.nextInt();
		}
	}
	
	public final int nextN_For_Join() {
		synchronized (lock) {
			return random.nextInt(900000)+100000;
		}
	}
	
	   /**
     * Generates random bytes and places them into a user-supplied
     * byte array.  The number of random bytes produced is equal to
     * the length of the byte array.
     *
     * <p>The method {@code nextBytes} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public void nextBytes(byte[] bytes) {
     *   for (int i = 0; i < bytes.length; )
     *     for (int rnd = nextInt(), n = Math.min(bytes.length - i, 4);
     *          n-- > 0; rnd >>= 8)
     *       bytes[i++] = (byte)rnd;
     * }}</pre>
     *
     * @param  bytes the byte array to fill with random bytes
     * @throws NullPointerException if the byte array is null
     * @since  1.1
     */
	public final void nextBytes(byte[] data) {
		if(data==null || data.length==0)
			return;
		synchronized (lock) {
			random.nextBytes(data);
		}
	}
	
	public final void nextBytes(byte[] data,int indexNotZero) {
		random.nextBytes(data);
		while(data[indexNotZero]==0)
			data[indexNotZero]=(byte) random.nextInt();
	}
	
	public final int nextInt() {
		synchronized (lock) {
			return random.nextInt();
		}
	}
	/**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextInt} is that one {@code int} value in the specified range
     * is pseudorandomly generated and returned.  All {@code bound} possible
     * {@code int} values are produced with (approximately) equal
     * probability.  The method {@code nextInt(int bound)} is implemented by
     * class {@code Random} as if by:
     *  <pre> {@code
     * public int nextInt(int bound) {
     *   if (bound <= 0)
     *     throw new IllegalArgumentException("bound must be positive");
     *
     *   if ((bound & -bound) == bound)  // i.e., bound is a power of 2
     *     return (int)((bound * (long)next(31)) >> 31);
     *
     *   int bits, val;
     *   do {
     *       bits = next(31);
     *       val = bits % bound;
     *   } while (bits - val + (bound-1) < 0);
     *   return val;
     * }}</pre>
     *
     * <p>The hedge "approximately" is used in the foregoing description only
     * because the next method is only approximately an unbiased source of
     * independently chosen bits.  If it were a perfect source of randomly
     * chosen bits, then the algorithm shown would choose {@code int}
     * values from the stated range with perfect uniformity.
     * <p>
     * The algorithm is slightly tricky.  It rejects values that would result
     * in an uneven distribution (due to the fact that 2^31 is not divisible
     * by n). The probability of a value being rejected depends on n.  The
     * worst case is n=2^30+1, for which the probability of a reject is 1/2,
     * and the expected number of iterations before the loop terminates is 2.
     * <p>
     * The algorithm treats the case where n is a power of two specially: it
     * returns the correct number of high-order bits from the underlying
     * pseudo-random number generator.  In the absence of special treatment,
     * the correct number of <i>low-order</i> bits would be returned.  Linear
     * congruential pseudo-random number generators such as the one
     * implemented by this class are known to have short periods in the
     * sequence of values of their low-order bits.  Thus, this special case
     * greatly increases the length of the sequence of values returned by
     * successive calls to this method if n is a small power of two.
     *
     * @param bound the upper bound (exclusive).  Must be positive.
     * @return the next pseudorandom, uniformly distributed {@code int}
     *         value between zero (inclusive) and {@code bound} (exclusive)
     *         from this random number generator's sequence
     * @throws IllegalArgumentException if bound is not positive
     * @since 1.2
     */
	public final int nextInt(int n) {
		synchronized (lock) {
			return random.nextInt(n);
		}
	}
    /**
     * Returns the next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence. The general
     * contract of {@code nextLong} is that one {@code long} value is
     * pseudorandomly generated and returned.
     *
     * <p>The method {@code nextLong} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public long nextLong() {
     *   return ((long)next(32) << 32) + next(32);
     * }}</pre>
     *
     * Because class {@code Random} uses a seed with only 48 bits,
     * this algorithm will not return all possible {@code long} values.
     *
     * @return the next pseudorandom, uniformly distributed {@code long}
     *         value from this random number generator's sequence
     */
	public final long nextLong() {
		synchronized (lock) {
			return random.nextLong();
		}
	}
	public final long nextLong(long n) {
		if(n==0)return 0;
		long result=0;
		synchronized (lock) {
			result=random.nextLong();
		}
		if (result < 0)
			return (-result)%n;
		else
			return result%n;
	}
	public final double nextDouble() {
		synchronized (lock) {
			return random.nextDouble();
		}
	}
	public final float nextFloat() {
		synchronized (lock) {
			return random.nextFloat();
		}
	}
	public final boolean nextBoolean() {
		synchronized (lock) {
			return random.nextBoolean();
		}
	}
	public final void nextBoolean(byte[] data) {
		synchronized (lock) {
			random.nextBytes(data);
		}
	}
	
	public final int getVerifyCode() {
		synchronized (lock) {
			return 100000 + random.nextInt(900000);
		}
	}
	
	public final byte[] randomByteSpace(byte numberRandom,byte valueBegin,byte valueFinish) {
		if(valueBegin>=valueFinish) {
			System.err.println("Invalidate value("+valueBegin+","+valueFinish+")");
			return null;
		}
		byte[] result=new byte[numberRandom];
		boolean flagExist;

		byte valueTmp;
		byte SPACE=(byte) (valueFinish-valueBegin+1);
		byte count=0;
		while(count<numberRandom) {
			synchronized (lock) {valueTmp=(byte) (random.nextInt(SPACE)+valueBegin);}
			flagExist=false;
			for(byte i=0;i<count;i++)
				if(result[i]==valueTmp) {
					flagExist=true;
					break;
				}
			if(flagExist==false) {
				result[count]=valueTmp;
				count++;
			}
		}
		
		return result;
	}
	
	public final int[] randomIntSpace(int numberRandom,int valueBegin,int valueFinish) {
		if(valueBegin>=valueFinish) {
			System.err.println("Invalidate value("+valueBegin+","+valueFinish+")");
			return null;
		}
		int[] result=new int[numberRandom];
		boolean flagExist;

		int valueTmp;
		int SPACE=valueFinish-valueBegin+1;
		int count=0;
		while(count<numberRandom) {
			synchronized (lock) {valueTmp=random.nextInt(SPACE)+valueBegin;}
			flagExist=false;
			for(int i=0;i<count;i++)
				if(result[i]==valueTmp) {
					flagExist=true;
					break;
				}
			if(flagExist==false) {
				result[count]=valueTmp;
				count++;
			}
		}
		return result;
	}
	
	public final byte[] getCardsInBox(byte numberCard) {
		if(numberCard>52) {
			System.out.println("Không thể rút quá 52 lá bài trong bộ");
			return null;
		}
		byte[] result = new byte[numberCard];
		byte temp=0;
		boolean flag;
		for(byte i=0;i<numberCard;i++) {
			flag=false;
			while(flag==false) {
				synchronized (lock) {temp=(byte) random.nextInt(52);}
				flag=true;
				for(byte j=0;j<i;j++)
					if(result[j]==temp) {
						flag=false;
						break;
					}
			}
			result[i]=temp;
		}
		return result;
	}
	
	//Random mảng n phần tử từ 0->n
	public final byte[] randomArrayBytesUniqueSequence(byte n) {
		if(n<1) return null;
		byte[] result=new byte[n];
		synchronized (lock) {result[0]=(byte)random.nextInt(n);}
		byte i=1;
		byte j;
		byte temp;
		while(i<n) {
			synchronized (lock) {temp=(byte)random.nextInt(n);}
			j=0;
			while(j<i)
				if(result[j]==temp) {
					temp=-1;
					break;
				}else
					j++;
			if(temp>-1) {
				result[i]=temp;
				i++;
			}
		}
		return result;
	}
	public final short[] randomArrayShortSUniqueSequence(int n) {
		if(n<1) return null;
		short[] result=new short[n];
		synchronized (lock) {result[0]=(short)random.nextInt(n);}
		short i=1;
		short j;
		short temp;
		while(i<n) {
			synchronized (lock) {temp=(short)random.nextInt(n);}
			j=0;
			while(j<i)
				if(result[j]==temp) {
					temp=-1;
					break;
				}else
					j++;
			if(temp>-1) {
				result[i]=temp;
				i++;
			}
		}
		return result;
	}
	
	public final void randomArrayShortSUniqueSequence(short[] listRandom,int numberUnit) {
		if(numberUnit<1) return;
		synchronized (lock) {listRandom[0]=(short)random.nextInt(numberUnit);}
		short i=1;
		short j;
		short temp;
		while(i<numberUnit) {
			synchronized (lock) {temp=(short)random.nextInt(numberUnit);}
			j=0;
			while(j<i)
				if(listRandom[j]==temp) {
					temp=-1;
					break;
				}else
					j++;
			if(temp>-1) {
				listRandom[i]=temp;
				i++;
			}
		}
	}
	
	public int weight(int ...arrWeight) {return weight(arrWeight.length,arrWeight);}
	public int weight(int numberValue,int ...arrWeight) {
		int bound=0;
		for(int i=0;i<numberValue;i++)
			if(arrWeight[i]>0)
				bound+=arrWeight[i];
		
		if(bound==0)
			return -1;
		
		int valueRandom = nextInt(bound)+1;
		int count=0;
		for(int i=0;i<numberValue;i++)
			if(arrWeight[i]>0){
				count+=arrWeight[i];
				if(count>=valueRandom)
					return i;
			}
		return -1;
	}
	public int weight(ArrayList<Integer> listWeight) {
		int numberValue=listWeight.size();
		int bound=0;
		for(int i=0;i<numberValue;i++)
			if(listWeight.get(i)>0)
				bound+=listWeight.get(i);
	
		if(bound==0)
			return -1;
		
		int valueRandom = nextInt(bound)+1;
		int count=0;
		for(int i=0;i<numberValue;i++) 
			if(listWeight.get(i)>0){
				count+=listWeight.get(i);
				if(count>=valueRandom)
					return i;
			}
		return -1;
	}
	
	public short randomIndexInPercents(long[] listPercent) {
		short numberUnit = (short) listPercent.length;
		long sumPercent = 0;
		for(short i=0;i<numberUnit;i++)
			sumPercent=sumPercent+listPercent[i];
		sumPercent=nextLong(sumPercent);
		for(short i=0;i<numberUnit;i++)
			if(sumPercent<listPercent[i]) {
				return i;
			}else
				sumPercent=sumPercent-listPercent[i];
		return (short) (numberUnit-1);
	}
	public short randomIndexInPercents(int[] listPercent) {
		short numberUnit = (short) listPercent.length;
		long sumPercent = 0;
		for(short i=0;i<numberUnit;i++)
			sumPercent=sumPercent+listPercent[i];
		sumPercent=nextLong(sumPercent);
		for(short i=0;i<numberUnit;i++)
			if(sumPercent<listPercent[i]) {
				return i;
			}else
				sumPercent=sumPercent-listPercent[i];
		return (short) (numberUnit-1);
	}
	public byte randomIndexInPercents(int sum,short[] listPercent) {
		byte numberUnit = (byte) listPercent.length;
		int sumPercent = nextInt(sum);
		for(byte i=0;i<numberUnit;i++)
			if(sumPercent<listPercent[i]) {
				return i;
			}else
				sumPercent=sumPercent-listPercent[i];
		return (byte) (numberUnit-1);
	}
	public byte randomIndexInPercents(short[] listPercent) {
		byte numberUnit = (byte) listPercent.length;
		int sumPercent = 0;
		for(byte i=0;i<numberUnit;i++)
			sumPercent=sumPercent+listPercent[i];
		sumPercent=nextInt(sumPercent);
		for(byte i=0;i<numberUnit;i++)
			if(sumPercent<listPercent[i]) {
				return i;
			}else
				sumPercent=sumPercent-listPercent[i];
		return (byte) (numberUnit-1);
	}
	public byte randomIndexInPercents(int sum,byte[] listPercent) {
		int numberUnit = listPercent.length;
		int sumPercent = nextInt(sum);
		for(byte i=0;i<numberUnit;i++)
			if(sumPercent<listPercent[i]) {
				return i;
			}else
				sumPercent=sumPercent-listPercent[i];
		return (byte) (numberUnit-1);
	}
	public byte randomIndexInPercents(byte[] listPercent) {
		int numberUnit = listPercent.length;
		int sumPercent = 0;
		for(int i=0;i<numberUnit;i++)
			sumPercent=sumPercent+listPercent[i];
		sumPercent=nextInt(sumPercent);
		for(byte i=0;i<numberUnit;i++)
			if(sumPercent<listPercent[i]) {
				return i;
			}else
				sumPercent=sumPercent-listPercent[i];
		return (byte) (numberUnit-1);
	}
	
	public String getRandomName() {
		switch (nextInt(100)) {
			case 0:return "aaa";
			case 1:return "bbb";
			default:return "ccc";
		}
	}
	
	public final String fuckyou() {
		int result=0;
		synchronized (lock) {result=random.nextInt(15);}
		switch (result) {
			case 0:return "F**k you 🖕";
			case 1:return "💩💩💩";
			case 2:return "Oh no!";
			case 3:return "Oh dear";
			case 4:return "How infuriating";
			case 5:return "really makes me cross";
			case 6:return "isn't nice";
			case 7:return "Oh, that's great!";
			case 8:return "What the hell?";
			case 9:return "Oh, hell, no.";
			case 10:return "Oh bloody hell!";
			case 11:return "Oh d*mn!";
			case 12:return "bl*st!";
			case 13:return "hell";
			case 14:return "Hell's bells!";
			default:return "🖕🖕🖕";
		}
	}
}

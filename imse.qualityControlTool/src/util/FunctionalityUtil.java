package util;

import java.util.HashMap;

/**Utility methods for all control functionality
 * 
 * @author Johannes
 *
 */
public class FunctionalityUtil {
	
	/** Integer to Letter Mapping */
	public static HashMap<Integer, String> intToLetter = new HashMap<Integer, String>();
	
	/** 0->A , 1->B, ...*/
	public static void setupIntToLetterMap(){
		for(int i=0; i< 26;++i){
			if(i==0)
				intToLetter.put(i, "A");
			else if (i==1)
				intToLetter.put(i, "B");
			else if (i==2)
				intToLetter.put(i, "C");
			else if (i==3)
				intToLetter.put(i, "D");
			else if (i==4)
				intToLetter.put(i, "E");
			else if (i==5)
				intToLetter.put(i, "F");
			else if (i==6)
				intToLetter.put(i, "G");
			else if (i==7)
				intToLetter.put(i, "H");
			else if (i==8)
				intToLetter.put(i, "I");	
			else if (i==9)
				intToLetter.put(i, "J");
			else if (i==10)
				intToLetter.put(i, "K");
			else if (i==11)
				intToLetter.put(i, "L");
			else if (i==12)
				intToLetter.put(i, "M");
			else if (i==13)
				intToLetter.put(i, "N");
			else if (i==14)
				intToLetter.put(i, "O");
			else if (i==15)
				intToLetter.put(i, "P");
			else if (i==16)
				intToLetter.put(i, "Q");
			else if (i==17)
				intToLetter.put(i, "R");
			else if (i==18)
				intToLetter.put(i, "S");
			else if (i==19)
				intToLetter.put(i, "T");
			else if (i==20)
				intToLetter.put(i, "U");
			else if (i==21)
				intToLetter.put(i, "V");
			else if (i==22)
				intToLetter.put(i, "W");
			else if (i==23)
				intToLetter.put(i, "X");
			else if (i==24)
				intToLetter.put(i, "Y");
			else if (i==25)
				intToLetter.put(i, "Z");
			else
				intToLetter.put(i, "?");
		}
		
	}
	
}

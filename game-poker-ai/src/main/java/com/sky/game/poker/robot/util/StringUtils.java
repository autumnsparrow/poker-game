/**
 * 
 */
package com.sky.game.poker.robot.util;

/**
 * @author sparrow
 *
 */
public class StringUtils {

	/**
	 * 
	 */
	public StringUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static Integer[]  stringToArray(String s){
		String t=s.replace("[", "").replace("]", "");
		String[] arrays=t.split(",");
		Integer  integerArrays[]=null;
		if(arrays!=null){
			integerArrays=new Integer[arrays.length];
			for(int i=0;i<arrays.length;i++)
				integerArrays[i]=Integer.decode(arrays[i].trim());
		}
		return integerArrays;
	}

}

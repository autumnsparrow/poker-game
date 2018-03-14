/**
 * 
 */
package com.sky.game.texas.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sparrow
 *
 */
public class Utils {

	/**
	 * 
	 */
	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * 
	 * @param t
	 * @return
	 */
	public static <T> List<T> asList(T[]  t){
		List<T> values=new LinkedList<T>();
		for(int i=0;i<t.length;i++){
			values.add(t[i]);
		}
		return values;
		
	}
	
	

}

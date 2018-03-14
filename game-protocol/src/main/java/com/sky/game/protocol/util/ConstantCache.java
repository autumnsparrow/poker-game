/**
 * 
 */
package com.sky.game.protocol.util;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 *
 */
public class ConstantCache {

	/**
	 * 
	 */
	public ConstantCache() {
		// TODO Auto-generated constructor stub
	}

	public static ConcurrentHashMap<String, Validate>  validateList = new ConcurrentHashMap<String, Validate>();

	public static ConcurrentHashMap<String, Validate> getValidateList() {
		return validateList;
	}
	public static void setValidateList(
			ConcurrentHashMap<String, Validate> validateList) {
		ConstantCache.validateList = validateList;
	}
	public static ConcurrentHashMap<String, Validate> listValidate(){
    	return validateList;
    }
    public static void addValidate(String key,Validate vaidate){
    	validateList.put(key, vaidate);
    	
    }
    public static void removeValidate(String key){
    	validateList.remove(key);
    }
    public static int selectValidate(String key,String validate){
    	
    	int i=-1;
    	Validate val=validateList.get(key);
    	if(val!=null && validate!=null && validate.equals(val.getVal())){
    		i= 1;
    	}else {
    		i= -1; 
    	}
    	ConstantCache.removeValidate(key);
    	return i;
    }
}

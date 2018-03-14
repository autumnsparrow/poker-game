package com.sky.poke.ai;   
/**     
 * Function: CAIAnalyzer工厂类 
 *
 * ClassName:CAIAnalyzerFactory 
 * Date:     2013-6-18 下午06:08:49    
 * @author   chshyin   
 * @version     
 * @since    JDK 1.6   
 * Copyright (c) 2013, palm-commerce All Rights Reserved.         
 */
public class CAIAnalyzerFactory {
	
	// 获取一个牌型分析对象
	public static CAIAnalyzer getInstance(){
		
		return new CAIAnalyzerImpl();
	}
}
   

/**
 * 
 */
package com.sky.game.service.logic;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.SystemChargeCard;
import com.sky.game.service.persistence.SystemChargeCardMapper;


/**
 * @author Administrator
 * 充值卡Service
 * 
 */
@Service
public class SystemChargeCardService {

	/**
	 * 
	 */
	public SystemChargeCardService() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	SystemChargeCardMapper systemChargeCardMapper;
	/**
	 * 产生那种类型 的充值卡 type
	 * 产生type类型的充值卡count张
	 * @param type
	 * @param count
	 */
	
    public void produceChargeCard(long type,int count){
    	
    	for(int i=0;i<count;i++){
    	SystemChargeCard scc=new SystemChargeCard();
    		String cardPassword=null;
    				try {
    					Thread.sleep(50);
    					Random r=new Random();
    					cardPassword="2015"+System.currentTimeMillis()+r.nextInt(9);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    		scc.setCardPassword(cardPassword);
    		scc.setChargeCardAttributeId(type);
    		systemChargeCardMapper.insert(scc);
   	}
    }
    
    public static void main(String[] args) {
		for(int i=0;i<50;i++){
			
//			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//			long currentCpuTime = threadMXBean.getCurrentThreadCpuTime();
			//int d = GetNumber();
			//String strTimeSpan = DateTime.Now.ToString("yyyyMMddHHmmssf");
			//Trace.WriteLine("createID="+strTimeSpan);
			 try {
				Thread.sleep(50);
				System.out.println(System.currentTimeMillis());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

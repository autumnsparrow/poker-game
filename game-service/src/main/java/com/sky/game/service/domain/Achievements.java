/**
 * 
 */
package com.sky.game.service.domain;

import java.util.List;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.GameUtil;

/**
 * @author Administrator
 *
 */
public class Achievements {
	
	List<Achievement> achievements;
	
	

	public List<Achievement> getAchievements() {
		return achievements;
	}



	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}



	/**
	 * 
	 */
	public Achievements() {
		// TODO Auto-generated constructor stub
	}

	
	public static void main(String[]  args){
		Achievements o=GameUtil.obtain(Achievements.class);
		List<Achievement> objs=GameUtil.getList();
		o.setAchievements(objs);
		
		for (int i=0;i<3;i++){
			objs.add(Achievement.obtain());
		}
		
		String json=GameContextGlobals.getJsonConvertor().format(o);
		System.out.println(json);
	}
}

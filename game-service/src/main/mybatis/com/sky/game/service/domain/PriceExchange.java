/**
 * 
 */
package com.sky.game.service.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class PriceExchange {

	/**
	 * 
	 */
	public PriceExchange() {
		// TODO Auto-generated constructor stub
	}
   long id;
   String iconImg;
   String fromName;
   String toName;
   int    fromCount;
   int    toCount;
   Date   startTime;
   Date   endTime;
   int leftCount;
   int con;
   int resCount;
   int resDay;
   int resStopDay;
   String useCondition;
   String startTimeStr;
   String endTimeStr;
   String description;
public int getResStopDay() {
	return resStopDay;
}
public void setResStopDay(int resStopDay) {
	this.resStopDay = resStopDay;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public int getResCount() {
	return resCount;
}
public void setResCount(int resCount) {
	this.resCount = resCount;
}
public int getResDay() {
	return resDay;
}
public void setResDay(int resDay) {
	this.resDay = resDay;
}
public String getStartTimeStr() {
	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return sim.format(getStartTime());
}
public void setStartTimeStr(String startTimeStr) {
	this.startTimeStr = startTimeStr;
}

public String getEndTimeStr() {
	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return sim.format(getEndTime());
}
public void setEndTimeStr(String endTimeStr) {
	this.endTimeStr = endTimeStr;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getIconImg() {
	return iconImg;
}
public void setIconImg(String iconImg) {
	this.iconImg = iconImg;
}
public String getFromName() {
	return fromName;
}
public void setFromName(String fromName) {
	this.fromName = fromName;
}
public String getToName() {
	return toName;
}
public void setToName(String toName) {
	this.toName = toName;
}
public Date getStartTime() {
	return startTime;
}
public void setStartTime(Date startTime) {
	this.startTime = startTime;
}
public Date getEndTime() {
	return endTime;
}
public void setEndTime(Date endTime) {
	this.endTime = endTime;
}
public int getLeftCount() {
	return leftCount;
}
public void setLeftCount(int leftCount) {
	this.leftCount = leftCount;
}
public String getUseCondition() {
	String uc=null;
	if(getCon()>0){
		uc="你还需要"+getCon()+"元宝";
	}else{
		uc="可以对换该物品";
	}
	return uc;
}
public void setUseCondition(String useCondition) {
	this.useCondition = useCondition;
}

public PriceExchange(long id, String iconImg, String fromName, String toName,
		int fromCount, int toCount, Date startTime, Date endTime,
		int leftCount, String useCondition,int resCount,int resDay,int resStopDay,String description) {
	super();
	this.id = id;
	this.iconImg = iconImg;
	this.fromName = fromName;
	this.toName = toName;
	this.fromCount = fromCount;
	this.toCount = toCount;
	this.startTime = startTime;
	this.endTime = endTime;
	this.leftCount = leftCount;
	this.useCondition = useCondition;
	this.resCount=resCount;
	this.resDay=resDay;
	this.resStopDay=resStopDay;
	this.description=description;
}
public int getFromCount() {
	return fromCount;
}
public void setFromCount(int fromCount) {
	this.fromCount = fromCount;
}
public int getToCount() {
	return toCount;
}
public void setToCount(int toCount) {
	this.toCount = toCount;
}

public int getCon() {
	return con;
}
public void setCon(int con) {
	this.con = con;
}
@Override
public String toString() {
	return "PriceExchange [id=" + id + ", iconImg=" + iconImg + ", fromName="
			+ fromName + ", toName=" + toName + ", fromCount=" + fromCount
			+ ", toCount=" + toCount + ", startTime=" + startTime
			+ ", endTime=" + endTime + ", leftCount=" + leftCount + ", con="
			+ con + ", resCount=" + resCount + ", resDay=" + resDay
			+ ", resStopDay=" + resStopDay + ", useCondition=" + useCondition
			+ ", startTimeStr=" + startTimeStr + ", endTimeStr=" + endTimeStr
			+ ", description=" + description + "]";
}
public static void main(String[] args) {
	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	System.out.println(sim.format(new Date()));
}
}


///**
// * 
// */
//package com.sky.game.service.domain;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author Administrator
// *
// */
//public class PriceExchange {
//
//	/**
//	 * 
//	 */
//	public PriceExchange() {
//		// TODO Auto-generated constructor stub
//	}
//   long id;
//   String iconImg;  //图片名
//   String fromName; //元宝
//   String toName;   //奖品
//   int    fromCount; //元宝数
//   int    toCount;   //奖品数
//   Date   startTime; //开始时间
//   Date   endTime;   //结束时间
//   int leftCount;
//   int con;
//   int resCount;
//   int resDay;
//   int resStopDay;
//   String useCondition;
////   String startTimeStr;
////   String endTimeStr;
//   String description;
////public int getResStopDay() {
////	return resStopDay;
////}
////public void setResStopDay(int resStopDay) {
////	this.resStopDay = resStopDay;
////}
//public String getDescription() {
//	return description;
//}
//public void setDescription(String description) {
//	this.description = description;
//}
//public int getResCount() {
//	return resCount;
//}
//public void setResCount(int resCount) {
//	this.resCount = resCount;
//}
//
//public Date getStartTime() {
//	return startTime;
//}
//public void setStartTime(Date startTime) {
//	this.startTime = startTime;
//}
//public Date getEndTime() {
//	return endTime;
//}
//public void setEndTime(Date endTime) {
//	this.endTime = endTime;
//}
//public int getResDay() {
//	return resDay;
//}
//public void setResDay(int resDay) {
//	this.resDay = resDay;
//}
//public int getResStopDay() {
//	return resStopDay;
//}
//public void setResStopDay(int resStopDay) {
//	this.resStopDay = resStopDay;
//}
//public long getId() {
//	return id;
//}
//public void setId(long id) {
//	this.id = id;
//}
//public String getIconImg() {
//	return iconImg;
//}
//public void setIconImg(String iconImg) {
//	this.iconImg = iconImg;
//}
//public String getFromName() {
//	return fromName;
//}
//public void setFromName(String fromName) {
//	this.fromName = fromName;
//}
//public String getToName() {
//	return toName;
//}
//public void setToName(String toName) {
//	this.toName = toName;
//}
////public Date getStartTime() {
////	return startTime;
////}
////public void setStartTime(Date startTime) {
////	this.startTime = startTime;
////}
////public Date getEndTime() {
////	return endTime;
////}
////public void setEndTime(Date endTime) {
////	this.endTime = endTime;
////}
//public int getLeftCount() {
//	return leftCount;
//}
//public void setLeftCount(int leftCount) {
//	this.leftCount = leftCount;
//}
//public String getUseCondition() {
//	String uc=null;
//	if(getCon()>0){
//		uc="你还需要"+getCon()+"元宝";
//	}else{
//		uc="可以对换该物品";
//	}
//	return uc;
//}
//public void setUseCondition(String useCondition) {
//	this.useCondition = useCondition;
//}
//
//
//public int getFromCount() {
//	return fromCount;
//}
//public void setFromCount(int fromCount) {
//	this.fromCount = fromCount;
//}
//public int getToCount() {
//	return toCount;
//}
//public void setToCount(int toCount) {
//	this.toCount = toCount;
//}
//
//public int getCon() {
//	return con;
//}
//public void setCon(int con) {
//	this.con = con;
//}
//
//public static void main(String[] args) {
//	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	System.out.println(sim.format(new Date()));
//}
//}

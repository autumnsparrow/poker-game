package com.sky.game.service.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;

public class ReportItem{
@JsonIgnoreProperties(ignoreUnknown = true)
	public ReportItem() {
		// TODO Auto-generated constructor stub
	}
	int days;
	int value;
	String item;
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	
	
	public static class Reports{
		HashMap<Integer,ReportItem> items;

		public HashMap<Integer, ReportItem> getItems() {
			return items;
		}

		public void setItems(HashMap<Integer, ReportItem> items) {
			this.items = items;
		}

		@Override
		public String toString() {
			return "Reports [items=" + items + "]";
		}
		
	};
	
	
	
	public static void main(String args[]) throws IOException{
//	HashMap<Integer,ReportItem> tasksMap=null;
//	tasksMap=new HashMap<Integer,ReportItem>();
//	Reports  tasks=new Reports();
//	List<ReportItem> items=GameUtil.getList();
//	for(int i=0;i<10;i++){
//		ReportItem item=new ReportItem();
//		item.setDays(i);
//		item.setItem(String.valueOf(i)+"-item");
//		item.setValue(i*200);
//		tasksMap.put(Integer.valueOf(i), item);
//	}
//		tasks.setItems(tasksMap);
//		 FileOutputStream outstream = new FileOutputStream("F:/game.v2.prj/game-service/src/main/resources/META-INF/libary.json");
//	     ObjectOutputStream out = new ObjectOutputStream(outstream);
//	     out.writeObject(tasks);
//	     out.flush();
//	     out.close();
	
//		String java=GameContextGlobals.getJsonConvertor().format(tasks);
//	
//		System.out.println(java);

		InputStream is=ReportItem.class.getResourceAsStream("/META-INF/report.json");
		Reports reports =GameContxtConfigurationLoader.loadConfiguration(is, Reports.class);  
		System.out.println(reports.toString());
		String java1=GameContextGlobals.getJsonConvertor().format(reports);
		System.out.println(java1);

	}
	
}

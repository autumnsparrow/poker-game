package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
public class TG0006Beans {
	
		public TG0006Beans() {
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * 
		 * 
		 * 
		 * @author Administrator
		 *
		 */
		@HandlerRequestType(transcode="TG0006")
		
		public static class Gamebear
		{
			List<TG0007Beans.Gamebear> list = new ArrayList<TG0007Beans.Gamebear>();

			public List<TG0007Beans.Gamebear> getList() {
				return list;
			}

			public void setList(List<TG0007Beans.Gamebear> list) {
				this.list = list;
			}
		}
}

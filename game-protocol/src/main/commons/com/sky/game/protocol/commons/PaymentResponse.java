package com.sky.game.protocol.commons;
public  class PaymentResponse {

		public PaymentResponse() {
			// TODO Auto-generated constructor stub
		}

		private int status;
		private String orderId;
		private String tn;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getTn() {
			return tn;
		}
		public void setTn(String tn) {
			this.tn = tn;
		}
		
		
	}
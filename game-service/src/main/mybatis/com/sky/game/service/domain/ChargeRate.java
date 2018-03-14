/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */

//暂时没有用到
public class ChargeRate {

	/**
	 * 
	 */
		String objectName;
		int objectCount;
		String RMB;
		int RMBCount;
		int zsCount;

		public String getObjectName() {
			return objectName;
		}

		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}

		public int getObjectCount() {
			return objectCount;
		}

		public void setObjectCount(int objectCount) {
			this.objectCount = objectCount;
		}

		public String getRMB() {
			return RMB;
		}

		public void setRMB(String rMB) {
			RMB = rMB;
		}

		public int getRMBCount() {
			return RMBCount;
		}

		public void setRMBCount(int rMBCount) {
			RMBCount = rMBCount;
		}

		public int getZsCount() {
			return zsCount;
		}

		public void setZsCount(int zsCount) {
			this.zsCount = zsCount;
		}

		public ChargeRate() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ChargeRate(String objectName, int objectCount, String rMB,
				int rMBCount, int zsCount) {
			super();
			this.objectName = objectName;
			this.objectCount = objectCount;
			RMB = rMB;
			RMBCount = rMBCount;
			this.zsCount = zsCount;
		}

		@Override
		public String toString() {
			return "ChargeRate [objectName=" + objectName + ", objectCount="
					+ objectCount + ", RMB=" + RMB + ", RMBCount=" + RMBCount
					+ ", zsCount=" + zsCount + "]";
		}
}

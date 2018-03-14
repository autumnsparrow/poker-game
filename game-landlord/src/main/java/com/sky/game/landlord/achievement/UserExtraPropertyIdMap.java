/**
 * 
 */
package com.sky.game.landlord.achievement;

/**
 * @author Administrator
 *
 */
public class UserExtraPropertyIdMap {
	
	
	private static int[][] dashifen={
		{0,0,0,0},
		{0,3,2,1},
		{0,5,3,1},
		{0,20,15,5}
		
	};

	/**
	 * 
	 */
	public UserExtraPropertyIdMap() {
		// TODO Auto-generated constructor stub
	}
	
	public static int getFen(int tournamentType,int rank){
		int fen=0;
		if(tournamentType>=0&&tournamentType<4){
			if(rank>0&&rank<4){
				fen=dashifen[tournamentType][rank];
			}
		}
		
		return fen;
	}

}

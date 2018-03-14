/**
 * 
 */
package com.sky.game.poker.robot.poker;

/**
 * @author sparrow
 *
 */
public class RoundOfPoker {
	
	byte poker;
	
	byte position;
	
	

	/**
	 * 
	 */
	public RoundOfPoker() {
		// TODO Auto-generated constructor stub
	}

	
	public RoundOfPoker(byte position, int round) throws Exception {
		super();
		this.position=position;
		byte owner=(byte)((position<<6));
		byte p=(byte)(owner|((byte)(0x3f&round)));
		this.poker=p;
		if(round>0x3f){
			throw new Exception("Round can not larger than 63");
		}
	}
	
	
	public byte getPoker() {
		return poker;
	}


	
	
	public int getRound(){
		return (this.poker&0x3f);
	}


	@Override
	public String toString() {
		return "RoundOfPoker [poker=" + poker + ", round=" + getRound() +",player="+position+ "]";
	}
	
	
	
	

}

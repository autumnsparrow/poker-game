/**
 * 
 */
package com.sky.game.texas.util;

import com.sky.game.context.util.HexDump;
import com.sky.game.texas.domain.GameTexasException;

/**
 * @author sparrow
 *
 */
public class Hex {

	private static final int HEX_LENGHT=16;
	
	String hex;
	
	byte[]  bytes;

	/**
	 * 
	 */
	public Hex() {
		// TODO Auto-generated constructor stub
	}
	public static final String HEX="0123456789abcdef";
	public static final byte[] BINARY=new byte[]{
		0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f
	};

	public Hex(String hex) throws GameTexasException {
		super();
		this.hex = hex;
		if(HEX_LENGHT!=this.hex.length()){
			throw new GameTexasException("Hex length must be :"+HEX_LENGHT);
		}
		hex=hex.toLowerCase();
		bytes=new byte[HEX_LENGHT/2];
		for(int i=0;i<HEX_LENGHT;i+=2){
			char hc=hex.charAt(i);
			char lc=hex.charAt(i+1);
			
			byte h=getHexValue(hc);
			byte l=getHexValue(lc);
			bytes[i/2]=(byte) ((h<<4&0xf0)^(l&0x0f));
		}
		
	}
	
	
	
	public Hex(byte[] bytes) {
		super();
		this.bytes = bytes;
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<8;i++){
			if(i<this.bytes.length){
				byte b=bytes[i];
				byte h=(byte) ((b>>4)&0x0f);
				byte l=(byte) (b&0x0f);
				
				sb.append(HEX.charAt(h)).append(HEX.charAt(l));
			}else{
				sb.append("00");
			}
		}
		this.hex=sb.toString();
		
	}



	private byte getHexValue(char c){
		byte p=0;
		if(c>='0'&&c<='9'){
			p=(byte) (c-'0');
		}else if(c>='a'&&c<='f'){
			p=(byte) (c-'a'+10);
		}
		return p;
	}

	
	
	@Override
	public String toString() {
		return "Hex [hex=" + hex + ", bytes=" + HexDump.dumpHexString(bytes) + "]";
	}


	private static void log(String message){
		System.out.println(message);
	}

	public static void main(String args[]) throws GameTexasException{
		
		byte[]  cards=new byte[]{0x13,0x2e,(byte) 0x87};
		
		Hex hex=new Hex(cards);
		log(hex.toString());
		Hex hh=new Hex(hex.hex);
		
		log(hh.toString());
		
		
		
		
	}



	public String getHex() {
		return hex;
	}



	public byte[] getBytes() {
		return bytes;
	}
	
	
	
	
	

}

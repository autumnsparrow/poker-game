/**
 * 
 */
package com.sky.game.poker.robot.poker;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author sparrow
 *
 */
public class ConsecutiveCompare {
	
	public static class ConsecutiveElement{
		boolean zero;
		byte size;
		int position;
		
		public ConsecutiveElement(boolean zero, byte size) {
			super();
			this.zero = zero;
			this.size = size;
		}
		
		public static ConsecutiveElement instance(boolean zero,byte size){
			return new ConsecutiveElement(zero, size);
		}

		@Override
		public String toString() {
			return (zero?"-":"+") + String.format("%02x", size) + "";
		}
		
		
		public ConsecutiveElement clone(){
			return instance(zero, size);
		}
		
		public void decreaseSize(int length){
			if(this.size>(byte)length)
				this.size=(byte)(this.size-(byte)length);
		}
		
		public void increaseSize(int length){
			this.size=(byte)(this.size+(byte)length);
		}
		
		public void position(int position){
			this.position=position;
		}
		
	}

	//LinkedList<ConsecutiveElement> 
	ConsecutiveElement consecutiveElements[];
	Stack<ConsecutiveElement> stack;
	byte statics[];
	/**
	 * 
	 *  consecutive
	 * 
	 */
	public ConsecutiveCompare(byte consecutive[]) {
		// TODO Auto-generated constructor stub
		// the consecutive is from A ... 3
		// let's reverse it.
		
		// statics of consective offset 2
		statics=new byte[consecutive.length];
		
		stack=new Stack<ConsecutiveCompare.ConsecutiveElement>();
		stack.push(ConsecutiveElement.instance(true, (byte)0));
		
		for(int j=consecutive.length-1;j>=2;j--){
			ConsecutiveElement element=stack.peek();
			if(consecutive[j]==0){
				if(element.zero)
					element.size++;
				else{
					stack.push(ConsecutiveElement.instance(true, (byte)1));
				}
				
			}else{
				if(!element.zero)
					element.size++;
				else{
					stack.push(ConsecutiveElement.instance(false, (byte)1));
				}
			}
			
		}
		
		consecutiveElements=new ConsecutiveElement[stack.size()];
		byte position=(byte)(consecutiveElements.length-1);
		while(!stack.empty()){
			
			consecutiveElements[position--]=stack.pop();
			//consecutiveElements[position].position(position);
			//consecutiveElements[position].position=position;
		}
		
		
	}
	
	/*
	 * 
	 * get the statics data by the rank size and consecutive length.
	 * 
	 * @param rankSize set the rank size to the statics.
	 * @param consecutiveLength check the consecutive length of the rank size.
	 * 
	 */
	public byte[] getStatics(int rankSize,int consecutiveLength){
		// reset the data.
		for(int i=0;i<statics.length;i++){
			statics[i]=0x0;
		}
		
		int position=statics.length;
		for(int i=0;i<consecutiveElements.length;i++){
			
			if(consecutiveElements[i].zero){
				for(int j=0;j<consecutiveElements[i].size;j++){
					position--;
				}
			}else{
				if(consecutiveElements[i].size>=consecutiveLength){
					for(int j=0;j<consecutiveElements[i].size;j++){
						position--;
						statics[position]=(byte)rankSize;
						
					}
				}else{
					for(int j=0;j<consecutiveElements[i].size;j++){
						position--;
					}
				}
			}
			
				
		}
		
		
		return statics;
	}
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return 
				Arrays.toString(consecutiveElements) ;
	}
	
	
	/**
	 * 
	 *                     [-00, -02, -01, +03, -01, +01, -01, +01]
	 *  [PairConsecutive  ][-00, +02, -01, +04, -01, +01, -01, +01]
	 *  
	 * 
	 * @param compare
	 * @return
	 */
	/**
	 * mark the statics 
	 *                    [-00, +02, -01, +04, -01, +01, -01, +01]
	 *                    
	 * [PairConsecutive  ][-00, -03, +03, -05]
	 * 
	 * the find process:
	 * 			1, find the non-zero element larger or equal the size of comparor
	 * 			2, 
	 * 
	 * 
	 */
	public boolean compare(ConsecutiveCompare compare){
		boolean larger=false;
		
		ConsecutiveElement compareElement=compare.getElementByIndex(1);
		int offsetOfCompare=compare.offsetBeforeNonZeroElementN(1);
		int countOfNonzeroElements=countOfNonzeroElement();
		if(compareElement!=null){
		int length=compareElement.size;
		ConsecutiveElement found=null;
		for(int i=1;i<countOfNonzeroElements+1;i++){
			int offset=offsetBeforeNonZeroElementN(i);
			found=getElementByIndex(i);
			if(found.size>=compareElement.size){
				// 
				if(offsetOfCompare<offset){
					generateStatics(offset, length);
					larger=true;
					break;
				}else if(offsetOfCompare==offset){
					if(found.size>compareElement.size){
						generateStatics(offset+1, length);
						larger=true;
						break;
					}
				}else if(offsetOfCompare>offset){
					if((offsetOfCompare+length)<(offset+found.size)){
						generateStatics(offsetOfCompare+1,length );
						larger=true;
						break;
					}
				}
			}
			
			
		}
		}
		
		
		return larger;
	}
	
	private void generateStatics(int offset,int length){
		for(int i=0;i<statics.length;i++){
			statics[i]=0x0;
		}
		
		for(int i=offset;i<offset+length;i++){
			statics[statics.length-1-i]=0x01;
		}
	}
	
	
	/**
	 * 
	 * Compare compare=null;
	 * boolean larger=false;
	 * larger=compare.compare(cube.consecutivePairs());
	 * if(larger){
	 *  
	 *  	PokerCube cube=compare.getComparedStatics(Cube.consecutivePair.value);
	 *  }
	 * 
	 * 
	 * 
	 * @param rankSize
	 * @return
	 */
	public byte[] getComparedStatics(int rankSize){
		for(int i=0;i<statics.length;i++){
			if(statics[i]==0x01){
				statics[i]=(byte)rankSize;
			}
		}
		return statics;
	}
	
	/**
	 * 
	 * @return
	 */
	private int countOfNonzeroElement(){
		int count=0;
		for(int i=0;i<consecutiveElements.length;i++){
			if(!consecutiveElements[i].zero){
				count++;
			}
			
				
		}
		return count;
	}
	
	/**
	 * 
	 * @return
	 */
	public int sumOfElements(){
		int count=0;
		for(int i=0;i<consecutiveElements.length;i++){
			if(!consecutiveElements[i].zero){
				count=count+consecutiveElements[i].size;
			}
			
				
		}
		return count;
		
	}

	
	/**
	 * [SoloConsecutive  ][-02, +01, -04, +01, -03, +01]
	 * if the indexOfNonZeroElement equal 2 
	 * 			the consecutiveElement should be  ,-04, [+01] ,-03
	 *
	 * 
	 * @param indexOfNonZeroElement
	 * @return ConsecutiveElement
	 */
	private ConsecutiveElement getElementByIndex(int indexOfNonZeroElement){
		int index=indexOfNonZeroElement;
		ConsecutiveElement element=null;
		for(int i=0;i<consecutiveElements.length;i++){
			if(!consecutiveElements[i].zero){
				index--;
			}
			if(index==0){
				element=consecutiveElements[i];
				break;
			}
				
		}
		return element;
	}
	
	/**
	 * [SoloConsecutive  ][-02, +01, -04, +01, -03, +01]
	 * if the indexOfNonZeroElements equa 2
	 * 		the offset before index 2 non-zero element should be (2+1+4) = 7
	 * 
	 * @param indexOfNonZeroElements
	 * @return
	 */
	public int offsetBeforeNonZeroElementN(int indexOfNonZeroElements){
		int offset=0;
		int index=indexOfNonZeroElements;
		for(int i=0;i<consecutiveElements.length;i++){
			
			if(!consecutiveElements[i].zero){
				index--;
			}
			if(index<=0){
				break;
			}
			offset=offset+consecutiveElements[i].size;
		}
		return offset;
	}
	
	
	
	
	
	
	
	
	

}

package com.sky.poke.solution;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
/**
 * 出牌方案
 * @author shahuaitao
 *
 */
public class CardSolution {
	/**
	 * 拆牌的每一手牌
	 */
	private List<Poke> oneSendCards=null;
	/**
	 * 剩余的对牌或单牌
	 */
	private List<Poke> singleOrDouble=null;
	
	/**
	 * 单牌
	 */
	private List<Poke> single=new ArrayList<Poke>();
	/**
	 * 对子
	 */
	private List<Poke> pair=new ArrayList<Poke>();
	/**
	 * 关键牌 包括 2 和 大小王
	 */
	private List<Poke> keycard=new ArrayList<Poke>();
	
	
	/**
	 * 获取单牌手数，用于选择最优方案
	 * 单牌手数，
	 * 炸弹可将单牌手数-2
	 * 飞机（x连续个）可将单牌手数-x（x>2）
	 * 三张可将单牌手数减1
	 * 除此之外，对所有手牌 进行判断被压牌比值，如果为0，-0.5，如果singleOrDouble -1.5
	 * @return
	 */
	public double getSingleCount(){
		
		//初始值为singleOrDouble
		double initCount=singleOrDouble.size();
		
		
		if(oneSendCards!=null){
			for(Poke osc:oneSendCards){
				if(osc.getType()==PokeRegulation.BOMB){
					
						//是王炸不能带单牌，但是可以作为被压牌比值为0的牌，所以
						//可以抵消1.5张牌
						
					initCount=initCount-2;
				}else if(osc.getType()==PokeRegulation.AIRCRAFT){
					initCount=initCount-osc.getLength()/3;
				}else if(osc.getType()==PokeRegulation.THREE){
					initCount=initCount-1;
				}
			}
			//对于单牌和双牌，如果被压牌比值=0，则本身不视为单牌手数，并且可以将
		}
//		
//		//除此之外，对所有手牌 进行判断被压牌比值，如果为0，-0.5，如果singleOrDouble -1.5
//		if(oneSendCards!=null){
//			for(Poke osc:oneSendCards){
//				if(osc.getLittleRate()==0){
//					//别压牌比值为0，-0.5
//					initCount=initCount-0.5;
//				}
//			}
//			
//		}
//		if(this.singleOrDouble!=null){
//			for(Poke osc:this.singleOrDouble){
//				if(osc.getLittleRate()==0){
//					//别压牌比值为0，-0.5
//					initCount=initCount-1.5;
//				}
//			}
//		}
		
		return initCount;
	}
	
	public Poke jugeCardSolution(Poke prepoke){
		if(prepoke==null){
			//用户自主出牌
			int index=-1;
			Poke indexPoke=null;
			if((this.singleOrDouble==null||this.singleOrDouble.size()<=0)&&(this.oneSendCards==null||this.oneSendCards.size()<=0)){//牌已经出完
				return null;
			}
			if(this.singleOrDouble==null||this.singleOrDouble.size()<=0){//当单张 和对子已经出完的情况
				Poke poke=null;
				int i = 0;
				for (; i < oneSendCards.size(); i++) {
					 poke=oneSendCards.get(i);
					 if(poke.getType()==PokeRegulation.BOMB){
						 continue;
					 }else{					
						 break;
					 }
				}
				indexPoke=this.oneSendCards.remove(i);
				return indexPoke;
			}
            if(this.oneSendCards==null||this.oneSendCards.size()<=0){//当光剩下对子和单张的时候
            	indexPoke=this.singleOrDouble.remove(0);
            	
            	this.single.remove(indexPoke);
            	this.pair.remove(indexPoke);
            	this.keycard.remove(indexPoke);
            	return indexPoke;
			}
			if(this.singleOrDouble.get(0).getMinIndexOfMaxAppear()<this.oneSendCards.get(0).getMinIndexOfMaxAppear()){
				
				
				
				indexPoke=this.singleOrDouble.remove(0);
				this.single.remove(indexPoke);
            	this.pair.remove(indexPoke);
            	this.keycard.remove(indexPoke);
			}else{
				Poke poke=null;
				int i = 0;
				for (; i < oneSendCards.size(); i++) {
					 poke=oneSendCards.get(i);
					 if(poke.getType()==PokeRegulation.BOMB){//保证炸弹最后出
						 continue;
					 }else{					
						 break;
					 }
				}
				if(poke.getType()==PokeRegulation.BOMB){
					indexPoke=this.oneSendCards.remove(i);
				}else if(poke.getType()==PokeRegulation.AIRCRAFT){
					indexPoke=this.oneSendCards.remove(i);
					
				}else if(poke.getType()==PokeRegulation.THREE){
					indexPoke=this.oneSendCards.remove(i);
					if(this.singleOrDouble!=null&&this.singleOrDouble.size()>0){
						indexPoke.addPoke(singleOrDouble.remove(0));
					}
				}else{
					indexPoke=this.oneSendCards.remove(i);
				}
			}
			return indexPoke;
		}
		
		//出牌 压牌
		
		
		
		return null;
	}
	
	
	public List<Poke> getOneSendCards() {
		return oneSendCards;
	}
	public void setOneSendCards(List<Poke> oneSendCards) {
		
		this.oneSendCards = oneSendCards;
	}
	public List<Poke> getSingleOrDouble() {
		return singleOrDouble;
	}
	public void setSingleOrDouble(List<Poke> singleOrDouble) {
		
		this.singleOrDouble = singleOrDouble;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String total="";
		if(oneSendCards!=null){
			for(Poke osc:oneSendCards){
				total+=osc.getType()+":"+osc.toString();
			}
		}
		if(singleOrDouble!=null){
			for(Poke osc:singleOrDouble){
				total+=osc.getType()+":"+osc.toString();
			}
		}

		//total+="单牌手数:"+this.getSingleCount();
		return total;
	}
	/**
	 * 获取总的出牌手数=oneSendCards的个数+单牌手数
	 * @return
	 */
	public double getSendCount() {
		// TODO Auto-generated method stub
		int count=0;
		if(this.oneSendCards!=null){
			count=this.oneSendCards.size();
		}
		return count+this.getSingleCount();
	}
	
	
	/**
	 * 整理
	 */

	public void trim(){
		//对非单张对子进行排列
		if(this.oneSendCards!=null)
		for (int i = 0; i < oneSendCards.size(); i++) {
			Poke ipoke=oneSendCards.get(i);
			for (int j = i+1; j < oneSendCards.size(); j++) {
				Poke jpoke=oneSendCards.get(j);
				if(ipoke.getMinIndexOfMaxAppear()>jpoke.getMinIndexOfMaxAppear()){
					oneSendCards.set(i, jpoke);
					oneSendCards.set(j, ipoke);
					ipoke=jpoke;
				}
			}
		}
		//对单张 和 对子进行整理排序 按照牌的大小顺利排列
		if(this.singleOrDouble!=null)
		for (int i = 0; i < singleOrDouble.size(); i++) {
			Poke ipoke=singleOrDouble.get(i);
			for (int j = i+1; j < singleOrDouble.size(); j++) {
				Poke jpoke=singleOrDouble.get(j);
				if(ipoke.getMinIndexOfMaxAppear()>jpoke.getMinIndexOfMaxAppear()){
					singleOrDouble.set(i, jpoke);
					singleOrDouble.set(j, ipoke);
					ipoke=jpoke;
				}
			}
		}
		
		if(this.singleOrDouble!=null)
			for (int i = 0; i < singleOrDouble.size(); i++) {
				Poke ipoke=singleOrDouble.get(i);
				if(ipoke.getMinIndexOfMaxAppear()>=12){
					this.keycard.add(ipoke);
				}else if(ipoke.getLength()==1){
					this.single.add(ipoke);
				}else if(ipoke.getLength()==2){
					this.pair.add(ipoke);
				}
			}
		
		
		
	}

	public List<Poke> getSingle() {
		return single;
	}

	public void setSingle(List<Poke> single) {
		this.single = single;
	}

	public List<Poke> getPair() {
		return pair;
	}

	public void setPair(List<Poke> pair) {
		this.pair = pair;
	}

	public List<Poke> getKeycard() {
		return keycard;
	}

	public void setKeycard(List<Poke> keycard) {
		this.keycard = keycard;
	}
	
	
}

/**
 * 
 * @Date:Nov 24, 2014 - 10:28:40 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.pack;

import java.util.List;

import com.sky.game.protocol.commons.GT0005Beans.Hands;

/**
 * @author sparrow
 *
 */
public class GameTexasCardMatrix {
	
	/**         horizontal 
	 *  vertical +  2 .......       A   verticals
	 *           S                     
	 *           D 
	 *           H
	 *           C 
	 *                  horizontals.
	 * 
	 */
	private static final  int VERTICAL=4;
	private static final  int HORIZONTAL=15;
	
	boolean matrix[][];
	int verticals[];
	int horizontals[];
	
	
	GameCardRankEnum  rankType;
	GameTexasCards  cards;
	
	
	public static GameTexasCardMatrix obtain(){
		return new GameTexasCardMatrix();
	}


	public GameCardRankEnum getRankType() {
		return rankType;
	}


	public GameTexasCards getCards() {
		return cards;
	}


	/**
	 * 
	 */
	public GameTexasCardMatrix() {
		// TODO Auto-generated constructor stub
		//matrix=new boolean[HORIZONTAL][HORIZONTAL];
		matrix=new boolean[VERTICAL][];
		for(int i=0;i<VERTICAL;i++){
			matrix[i]=new boolean[HORIZONTAL];
		}
		
		verticals=new int[VERTICAL];
		horizontals=new int[HORIZONTAL];
		
		cards=new GameTexasCards();
		
		
		
		reset();
		
	}
	
	/**
	 * reest the 
	 * 
	 * 
	 */
	private void reset(){
		rankType=GameCardRankEnum.Nothing;
		
		for(int i=0;i<VERTICAL;i++)
			for(int j=0;j<HORIZONTAL;j++){
				matrix[i][j]=false;
			}
			
		for(int i=0;i<VERTICAL;i++)
			verticals[i]=0;
		for(int i=0;i<HORIZONTAL;i++)
			horizontals[i]=0;
	}
	
	/**
	 * 
	 * 
	 * @param card　adding card into the matrix.
	 */
	public void add(GameTexasCard card,boolean flag){
		switch (card.suitEnum.value) {
		case (byte)0x80:
			matrix[3][card.valueEnum.value]=flag;
			break;
		case 0x40:
			matrix[2][card.valueEnum.value]=flag;
			break;
		case 0x20:
			matrix[1][card.valueEnum.value]=flag;
			break;
		case 0x10:
			matrix[0][card.valueEnum.value]=flag;
			break;

		default:
			break;
		}
	}
	
	
	public void addCards(GameTexasCards cards){
		//reset();
		for(GameTexasCard card:cards.cards){
			this.add(card,true);
			
		}
	}
	
	
	/**
	 * calculate the matrix vertical and horizontal
	 * 
	 */
	public void calculate(){
		for(int i=0;i<VERTICAL;i++){
			for(int j=0;j<HORIZONTAL;j++){
				if(matrix[i][j]){
					verticals[i]++;
					
				}
			}
		}
		
		for(int j=0;j<HORIZONTAL;j++)
			for(int i=0;i<VERTICAL;i++){
				if(matrix[i][j]){
					horizontals[j]++;
				}
			}
				
	}
	
	public void judgement(){
		calculate();
		// first check if the straight flush.
		if(isStraightflush()){
			rankType=GameCardRankEnum.StraightFlush;
		}else if(isFourOfKind()){
			rankType=GameCardRankEnum.FourOfind;
		}else if(isFullHouse()){
			rankType=GameCardRankEnum.FullHouse;
		}else if(isFlush()){
			rankType=GameCardRankEnum.Flush;
		}else if(isStraight()){
			rankType=GameCardRankEnum.Straight;
		}else if(isThreeOfKind()){
			rankType=GameCardRankEnum.ThreeOfKind;
		}else if(isTwoPair()){
			rankType=GameCardRankEnum.TwoPairs;
		}else if(isPair()){
			rankType=GameCardRankEnum.Pair;
		}else{
			rankType=GameCardRankEnum.Nothing;
		}
		
		// checking
		// if the cards less than 5, fetch the biggest of the oldcards.
	
		chooseBiggest();
		
	}
	private void chooseBiggest(){
		int size=5-this.cards.cards.size();
		
		// first should disable the true of already add cards
		for(GameTexasCard card:this.cards.cards){
			this.add(card, false);
		}
		
		for(int j=14;j>=2;j--){
			for(int i=0;i<VERTICAL;i++){
				//System.out.println(" x,y"+j+" - "+i );
				if(matrix[i][j]){
					size--;
					cards.addCard(obtainCard(i, j));
					
				}
			}
			if(size<=0){
				break;
			}
		}
	}
	
	
	private static final GameTexasCardSuitsEnum[] suits=new GameTexasCardSuitsEnum[]{
		GameTexasCardSuitsEnum.Spade,GameTexasCardSuitsEnum.Diamond,GameTexasCardSuitsEnum.Heart,GameTexasCardSuitsEnum.Club
		
	};
	private GameTexasCard  obtainCard(int v,int h){
		
		return GameTexasCard.obtain(suits[v], GameTexasCardValueEnum.getCardValue((byte)h));
	}
	
	
	
	/**
	 * the same suit.
	 * 
	 * @return  check the vertical size large than 5 that is flush.
	 * 
	 * 
	 */
	private boolean isFlush(){
		boolean ret=false;
		int suit=0;
		for(int i=0;i<VERTICAL;i++){
			if(verticals[i]>=5){
				ret=true;
				suit=i;
				break;
			}
		}
		
		//
		if(ret){
			cards.clear();
			for(int i=0;i<HORIZONTAL;i++){
				if(matrix[suit][i]){
					cards.addCard(obtainCard(suit, i));
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isStraight(){
		boolean ret=false;
		int counter=0;
		for(int j=0;j<HORIZONTAL;j++){
			if(horizontals[j]>0){
				counter++;
			}else{
				counter=0;
			}
			
		}
		if(counter>=5){
			ret=true;
		}
		
		if(ret){
			cards.clear();
			for(int i=0;i<HORIZONTAL;i++){
				if(horizontals[i]>0){
				for(int j=0;j<VERTICAL;j++){
					//if()
					if(matrix[j][i]){
						cards.addCard(obtainCard(j, i));
						break;
					}
				}
				}
			}
			
		}
		
		// special case
		ret=isSpecialStraight();
		
		return ret;
	}
	
	private boolean isSpecialStraight(){
		boolean ret=false;
		
		ret=horizontals[2]>0&horizontals[3]>0&horizontals[4]>0&horizontals[4]>0&horizontals[14]>0;
		if(ret){
			cards.clear();
			for(int j=1;j<HORIZONTAL;j++)
			for(int i=0;i<VERTICAL;i++){
				if(matrix[i][j]){
					cards.addCard(obtainCard(i, j));
				}else{
					cards.clear();
				}
			}
		}
		
		return ret;
	}
	
	
	private boolean isSizeLarger(int size){
		boolean ret=false;
		int pos=0;
		for(int j=0;j<HORIZONTAL;j++){
			if(horizontals[j]==size){
				ret=true;
				pos=j;
				break;
			}
		}
		
		if(ret){
			cards.clear();
			for(int i=0;i<VERTICAL;i++){
				if(matrix[i][pos]){
					cards.addCard(obtainCard(i, pos));
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private boolean isPair(){
		boolean ret=false;
		
		ret=isSizeLarger(2);
		return ret;
		
	}
	/**
	 * 
	 * @return
	 */
	private boolean isThreeOfKind(){
		boolean ret=false;
		ret=isSizeLarger(3);
		return ret;
	}
	/**
	 * 
	 * @return
	 */
	private boolean isFourOfKind(){
		boolean ret=isSizeLarger(4);
		return ret;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isTwoPair(){
		boolean ret=false;
		int counter=0;
		for(int j=0;j<HORIZONTAL;j++){
			if(horizontals[j]==2){
				counter++;
			}
		}
		
		if(counter>=2){
			ret=true;
			
			
		}
		
		if(ret){
			cards.clear();
			for(int j=0;j<HORIZONTAL;j++){
				if(horizontals[j]==2){
					for(int i=0;i<VERTICAL;i++){
						if(matrix[i][j]){
							cards.addCard(obtainCard(i, j));
						}
					}
				}
			}
		}
		
		return ret;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isFullHouse(){
		boolean ret=false;
		boolean two=false,three=false;
		for(int j=0;j<HORIZONTAL;j++){
			if(horizontals[j]==2){
				two=true;
			}
			if(horizontals[j]==3){
				three=true;
			}
		}
		ret=(two&&three);
		
		if(ret){
			cards.clear();
			for(int j=0;j<HORIZONTAL;j++){
				if(horizontals[j]==2){
					for(int i=0;i<VERTICAL;i++){
						if(matrix[i][j]){
							cards.addCard(obtainCard(i, j));
						}
					}
				}
				if(horizontals[j]==3){
					for(int i=0;i<VERTICAL;i++){
						if(matrix[i][j]){
							cards.addCard(obtainCard(i, j));
						}
					}
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private boolean isStraightflush(){
		
		boolean ret=false;
		int suit=0;
		for(int i=0;i<VERTICAL;i++){
			if(verticals[i]>=5){
				
				suit=i;
				break;
			}
		}
		int counter=0;
		for(int j=0;j<HORIZONTAL;j++){
			if(matrix[suit][j]){
				counter++;
			}else{
				counter=0;
			}
		}
		if(counter>=5){
			ret=true;
		}
		
		if(ret){
			cards.clear();
			for(int j=0;j<HORIZONTAL;j++){
				if(matrix[suit][j]){
					cards.addCard(obtainCard(suit, j));
				}else{
					cards.clear();
				}
			}
			
		}
		
		// specical case
		ret=matrix[suit][2]&matrix[suit][3]&matrix[suit][4]&matrix[suit][5]&matrix[suit][14];
		
		if(ret){
			cards.clear();
			cards.addCard(obtainCard(suit, 2));
			cards.addCard(obtainCard(suit, 3));
			cards.addCard(obtainCard(suit, 4));
			cards.addCard(obtainCard(suit, 5));
			cards.addCard(obtainCard(suit, 14));
		}
		
		return ret;
		
	}
	
	public GameTexasCardMatrix handRank(GameTexasCards tableCards,GameTexasCards playerCards){
		reset();
		addCards(tableCards);
		addCards(playerCards);
		judgement();
		
		return this;
		
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return "GameTexasCardMatrix [rankType=" + rankType.getState() + ", cards=" + cards.getHex()
				+ "]";
	}

	public static void main(String args[]){
		// 6-6-4-3-2
		
		GameTexasCards cards=new GameTexasCards();
		cards.addCard(new GameTexasCard(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v6));
		cards.addCard(new GameTexasCard(GameTexasCardSuitsEnum.Heart, GameTexasCardValueEnum.v6));
		cards.addCard(new GameTexasCard(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v4));
		cards.addCard(new GameTexasCard(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v3));
		cards.addCard(new GameTexasCard(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v2));
		
		GameTexasCardMatrix matrix=new GameTexasCardMatrix();
		
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
		
		//Q-Q-5-5-4.
		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vQ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Diamond, GameTexasCardValueEnum.vQ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v5));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Diamond, GameTexasCardValueEnum.v5));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Club, GameTexasCardValueEnum.v4));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
		// 5-5-5-3-2
		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v2));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Heart, GameTexasCardValueEnum.v5));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v5));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Diamond, GameTexasCardValueEnum.v5));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v3));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
		//A-K-Q-J-10
		
		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vA));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Heart, GameTexasCardValueEnum.vK));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vQ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Diamond, GameTexasCardValueEnum.vJ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v10));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println("Straight");
		System.out.println(matrix.toString());
		
		//spadeK-spadeJ-spade9-spade3-spade2,
		
		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vK));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vJ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v9));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v3));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v2));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vA));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
		
		//Full House 9-9-9-4-4 

		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v9));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Heart, GameTexasCardValueEnum.v9));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Diamond, GameTexasCardValueEnum.v9));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Heart, GameTexasCardValueEnum.v4));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v4));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v5));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
		// Four of a kind   heartJ-diamondJ-clubJ-spadeJ-spade9
		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vJ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Heart, GameTexasCardValueEnum.vJ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Diamond, GameTexasCardValueEnum.vJ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Club, GameTexasCardValueEnum.vJ));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v4));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v9));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v8));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
		//clubJ-club10-club9-club8-club7
		
		cards.clear();
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v5));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v4));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v3));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.v2));
		cards.addCard(GameTexasCard.obtain(GameTexasCardSuitsEnum.Spade, GameTexasCardValueEnum.vA));
		
		matrix.addCards(cards);
		matrix.judgement();
		System.out.println(matrix.toString());
		
	}


	/**
	 * @return
	 */
	public Hands wrap() {
		// TODO Auto-generated method stub
		Hands hands=new Hands();
		hands.setHex(this.cards.getHex());
		hands.setRankState(rankType.getState());
		return hands;
	}
	
	
}

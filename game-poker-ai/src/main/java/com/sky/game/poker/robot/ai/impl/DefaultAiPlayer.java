/**
 * 
 */
package com.sky.game.poker.robot.ai.impl;

import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.ai.BidStates;
import com.sky.game.poker.robot.ai.IAiPlayer;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 
 * 
 * 
 * @author sparrow
 * 
 */
public class DefaultAiPlayer implements IAiPlayer {
	private static final Log logger = LogFactory.getLog(DefaultAiPlayer.class);

	PokerCube pokerCube;
	PokerCube bottomPokerCube;
	
	
	Stack<PokerCube> leftPlayer;
	Stack<PokerCube> rightPlayer;
	Stack<PokerCube> currentPlayer;
	Stack<PokerCube> stackOfPokerCube;
	Stack<PokerCube> stackOfZeroPokerCube;
	

	PokerCubeType pokerCubeType;
	BidStates bidStates;

	PokerCube pokerHands;

	int score;

	boolean landLord;

	int positionOfDeck;

	boolean roundStart;

	int rounds;

	IPokerCubeAnalyzer pokerCubeAnalyzer;

	/**
	 * 
	 */
	public DefaultAiPlayer() {
		// TODO Auto-generated constructor stub
		pokerCubeAnalyzer = new DefaultPokerCubeAnalyzer();
		
		leftPlayer=new Stack<PokerCube>();
		rightPlayer=new Stack<PokerCube>();
		currentPlayer=new Stack<PokerCube>();
		stackOfPokerCube=new Stack<PokerCube>();
		stackOfZeroPokerCube=new Stack<PokerCube>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.poker.robot.ai.IAiPlayer#setInitializePoker(com.sky.game.poker.robot
	 * .poker.PokerCube)
	 */
	public void setInitializePoker(PokerCube pokerCube) {
		// TODO Auto-generated method stub
		this.pokerCube = pokerCube;
		this.pokerCube.replaceCubeValue();
		this.rounds = 0;
		this.stackOfPokerCube.clear();
		
		this.leftPlayer.clear();
		this.rightPlayer.clear();
		this.stackOfPokerCube.clear();
		this.stackOfZeroPokerCube.clear();
		this.pokerCube.setDeckPoistion(this.getDeckPosition());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.poker.robot.ai.IAiPlayer#setBottomPoker(com.sky.game.poker.robot.poker
	 * .PokerCube)
	 */
	public void setBottomPoker(PokerCube pokerCube) {
		// TODO Auto-generated method stub
		this.bottomPokerCube = pokerCube;

	}

	/**
	 * 
	 * 
	 * @return
	 */
	private PokerCube activeShowHands() {
		PokerCube pc =null;
		// AI anaylze
		// Core part of AI
		
		pc = pokerCubeAnalyzer.getBestActivePokerCube();
		
		roundStart = false;
		rounds++;

		return pc;
	}
	
	private PokerCube passiveShowHands(){
		PokerCube pc =null;
		//
		// analyze the poker then search the pattern
		
		
		pc=pokerCubeAnalyzer.getBestPassivePokerCube();
		
		
		return pc;

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IAiPlayer#showHands()
	 */
	public PokerCube showHands() {
		// TODO Auto-generated method stub
		PokerCube pc = null;
		
		// here should check the round start flag.
		if(stackOfPokerCube.isEmpty()){
			roundStart=true;
		}
		
		//
		//  
		//
		//
	//	pokerCubeAnalyzer.analyze(pokerCube,leftPlayer,currentPlayer,rightPlayer,bottomPokerCube,stackOfPokerCube);

		//
		// Core AI functions
		//
		
		pc=roundStart?activeShowHands():passiveShowHands();
		int kk=0;
		// attach the poker meta.
		pc.setBelongToLandloard(isLandLorder());
		pc.setDeckPoistion(getDeckPosition());
		
		pokerCube.subPokerCube(pc);
		
		
		
		
		return pc;
	}

	@Override
	public String toString() {
		return "DefaultAiPlayer ["+" score=" + score + ", landLord="
				+ landLord + ", positionOfDeck=" + positionOfDeck
				+ ", roundStart=" + roundStart + ", rounds=" + rounds + "]";
	}

	
	public void notifyBidStates(BidStates bidStates) {
		// TODO Auto-generated method stub
		this.bidStates = bidStates;

	}

	public BidStates offerBidStates() {
		// TODO Auto-generated method stub
		return BidStates.Pass;
	}

	public void notifyHands(PokerCube pokerCube) {
		// according with the Empty PokerCube determine should
		
		if(pokerCube.isEmpty()&&pokerCube.getDeckPosition()!=getDeckPosition()){
			stackOfZeroPokerCube.push(pokerCube);
			if(!stackOfZeroPokerCube.isEmpty()&&stackOfZeroPokerCube.size()==2){
				stackOfZeroPokerCube.clear();
				roundStart=true;
			}
			return;
		}
		
		stackOfZeroPokerCube.clear();
		
			
		
		// TODO Auto-generated method stub	
		this.pokerHands = pokerCube;
		// push the cube into stack
		
		stackOfPokerCube.push(pokerHands);
		// just with the current poker cube position
		int cubePosition=pokerCube.getDeckPosition();
		//
		int owerPosition=this.getDeckPosition();
		if((owerPosition+1)%3==cubePosition){
			// the cube belongs to the right player.
			this.rightPlayer.push(this.pokerHands.clone());
		}else if(owerPosition==cubePosition){
			// the current player self
			this.currentPlayer.add(this.pokerHands.clone());
		}else {
			// the left player.
			this.leftPlayer.add(this.pokerHands.clone());
		}
		
	}
	

	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = this.score + score;
	}

	public BidStates offerLowestBidStates() {
		// TODO Auto-generated method stub
		return BidStates.OnePoint;
	}

	public int pokerRemains() {
		// TODO Auto-generated method stub
		return this.pokerCube.remains();
	}

	public void setLandLordFlag(boolean landLorder) {
		// TODO Auto-generated method stub
		this.landLord = landLorder;
	}

	public boolean isLandLorder() {
		// TODO Auto-generated method stub
		return landLord;
	}

	public boolean offerKick() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDeckPosition(int position) {
		// TODO Auto-generated method stub
		this.positionOfDeck = position;
	}

	public int getDeckPosition() {
		// TODO Auto-generated method stub
		return this.positionOfDeck;
	}

	public void combinatePokerCube() {
		// TODO Auto-generated method stub
		pokerCube = pokerCube.addPokerCubeWithValue(bottomPokerCube,
				PokerCube.Cube.ShufflesRemains.value);
		pokerCube.replaceCubeValue();
		this.pokerCube.setDeckPoistion(this.getDeckPosition());
		logger.debug(this.pokerCube);
	}

	
	
	

}

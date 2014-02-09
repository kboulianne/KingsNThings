package com.presenter;

import com.model.Battle;
import com.model.Battle.BattlePhase;
import com.view.BattleView;

public class BattlePresenter {
	private BattleView view;
	
	Battle battle;
	
	public BattlePresenter(BattleView v){
		view = v;
		view.setPresenter(this);
	}

	public BattleView getView() {
		return view;
	}
	
	public void startPhase(){	
		battle = view.getBattle();
		if(!battle.isPlayerRetreated()||!battle.isPlayerHasWon()){ //
			switch (battle.getBattlePhase()){ 
				case MAGIC:
					magicPhase();
					break;
				case RANGED:
					rangedPhase();
					break;  
				case MELEE:
					meleePhase();
					break;
				case RETREAT:
					retreatPhase();
					break;
				case POSTCOMBAT:
					postCombatPhase();
					break;
			}
			
			//nextBattleRound();
		}	
	}
	
	private void magicPhase(){
		battle.setBattlePhase(BattlePhase.MAGIC);
	}
	private void rangedPhase(){
		
	}
	private void meleePhase(){
		
	}
	private void retreatPhase(){
		
	}
	private void postCombatPhase(){
		
	}
	
	protected void nextRound(){
		//TODO
		battle.setRoundNumber(battle.getRoundNumber()+1);
		battle.setBattlePhase(BattlePhase.MAGIC);
	}
	
	public void nextBattleRound(){
		//TODO
		if(battle.getBattlePhase().ordinal()+1 == BattlePhase.values().length )
			nextRound();
		else
			battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal()+1]);
	}
}

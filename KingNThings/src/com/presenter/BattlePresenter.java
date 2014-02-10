package com.presenter;

import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Battle.BattlePhase;
import com.model.Creature;
import com.model.Player;
import com.view.BattleView;
import com.view.DiceView;

public class BattlePresenter {
	private BattleView view;
	
	Battle battle;
	Creature offSelectedCreature;
	
	Iterator<Creature> offIt;
	Iterator<Creature> defIt;
	
	boolean isDefender = false;
	
	public BattlePresenter(BattleView v){
		view = v;
		view.setPresenter(this);
	}

	public BattleView getView() {
		return view;
	}
	
	public void startPhase(){	
	
		battle = view.getBattle(); // called more than once
		offIt = battle.getOffenderCreatures().iterator();
		defIt = battle.getDefenderItemsThatAreCreatures().iterator();
		
		//if(!battle.isPlayerRetreated()||!battle.isPlayerHasWon()){ //
			switch (battle.getBattlePhase()){ 
				case MAGIC:
					magicPhase(battle.getCurrentPlayer());
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
		//}	
	}
	
	// Roll Phases
	private void magicPhase(Player currentPlayer){	
		if(isDefender){
			rollPhaseHelper("Defender", BattlePhase.MAGIC, defIt);
		}else{ 
			rollPhaseHelper("Offender", BattlePhase.MAGIC, offIt);
		}
	}
	private void rangedPhase(){
		//update model
		//TODO
		final String player ="Offender";
		rollPhaseHelper(player, BattlePhase.RANGED, battle.getOffenderCreatures().iterator());
		
	}
	private void meleePhase(){
		//TODO
		final String player ="Offender";
		rollPhaseHelper(player, BattlePhase.MELEE, battle.getOffenderCreatures().iterator());
	}
	private void rollPhaseHelper(String player, BattlePhase phase, Iterator<Creature> it){
		
		battle.setBattlePhase(phase);
		//final Iterator<Creature> offIt = battle.getOffenderCreatures().iterator();
		offSelectedCreature = null;
		offSelectedCreature = findNextCreature(it, battle.getBattlePhase());
		
		if(offSelectedCreature==null){
			nextBattlePhase();
			return;
		}
		//update view
		view.getOffDice().setVisible(true);
		view.getOffButtonBox().setVisible(false);
		view.refreshView(player+" must roll for "+battle.getBattlePhase().phaseAsString+" Creature: "+offSelectedCreature.getName());
		handleRollBtn(player, view.getOffDice().getRollBtn(), it, view.getOffDice());
	}
	
	private void handleRollBtn(final String player, Button button, final Iterator<Creature> it, final DiceView dv){
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				int rolledValue = dv.getPresenter().roll(); 
				rollForCreature(offSelectedCreature, rolledValue);
				offSelectedCreature = findNextCreature(it, battle.getBattlePhase());
				if(offSelectedCreature==null){
					nextBattlePhase();
				}else{
					view.refreshView(player+" must roll for "+battle.getBattlePhase().phaseAsString+" Creature  "+offSelectedCreature.getName());
				}
			}
		});
	}

	private Creature findNextCreature(Iterator<Creature> it, BattlePhase phase){
		Creature creature = null;
		while(it.hasNext()){
			Creature c = it.next();
			if(phase==BattlePhase.MAGIC){
				if(c.getMagic()){
					creature = c;
					break;
				}
			}else if(phase==BattlePhase.RANGED){
				if(c.getRanged() && !c.getMagic()){
					creature = c;
					break;
				}
			}else if(phase==BattlePhase.MELEE){
				if(!(c.getRanged() || c.getMagic())){
					creature = c;
					break;
				}
			}
		}
		return creature;
	}
	
	private void rollForCreature(Creature c, int rolledValue){
		if(c.getMagic()){
			Util.log("TODO roll for magic creature "+rolledValue);
		}else if(c.getRanged() && !c.getMagic()){
			Util.log("TODO roll for ranged creature "+rolledValue);
		}else if(!c.getRanged() || !c.getMagic()){ //melee
			Util.log("TODO roll for melee creature "+rolledValue);
		}		
	}
	
	
	
	private void retreatPhase(){
		//update model
		battle.setBattlePhase(BattlePhase.RETREAT);
		
		//update view
		view.getOffDice().setVisible(false);
		view.getDefDice().setVisible(false);
		view.getOffButtonBox().setVisible(true);
		view.getDefButtonBox().setVisible(true);
		
		view.getOffRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				nextBattlePhase();
			}
		});
		view.getOffContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				nextBattlePhase();
			}
		});

		view.refreshView("Retreat or Continue");
	}
	private void postCombatPhase(){
		//update model
		battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				
		//update view
		view.getOffRetreatBtn().setVisible(false);
		view.getDefRetreatBtn().setVisible(false);
		view.refreshView("Post Combat");
		view.getOffContinueBtn().setText("Finish Battle");
		view.getOffContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				KNTAppFactory.getPopupPresenter().dismissPopup();
			}
		});
	}
	
	protected void nextRound(){
		//TODO
		battle.setRoundNumber(battle.getRoundNumber()+1);
		battle.setBattlePhase(BattlePhase.MAGIC);
	}
	
	protected void switchToNextPhase(){
		battle.setCurrentPlayer(battle.getOffender());
		if(battle.getBattlePhase() == BattlePhase.RETREAT )
			nextRound();
		else if(battle.getBattlePhase() == BattlePhase.POSTCOMBAT){ /*do nothing*/ }
		else
			battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal()+1]);
	}
	
	public void nextBattlePhase(){
		//TODO
		if(battle.getCurrentPlayer() == null){
			Util.log("1");
			isDefender = false;
			switchToNextPhase();
		}if(battle.getCurrentPlayer().equals( battle.getOffender() )){
			Util.log("2");
			isDefender = true;
			battle.setCurrentPlayer(battle.getDefender());
		}else{
			Util.log("1.");
			isDefender = false;
			switchToNextPhase();
		}
		startPhase();
	}
}

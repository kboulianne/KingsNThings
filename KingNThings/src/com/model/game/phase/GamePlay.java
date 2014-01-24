/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.game.phase;

import com.game.services.GameService;
import com.presenter.Util;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.SortedMap;
import java.util.TreeMap;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.init.ExchangePhase;
import com.model.game.phase.init.PlayerOrderPhase;
import com.model.game.phase.init.StartingForcesPhase;
import com.model.game.phase.init.StartingKingdomPhase;
import com.model.game.phase.init.StartingPosPhase;

/**
 *  The Context class in the Strategy Pattern.  This is the Game's "Behaviour".
 * 
 * @author kurtis
 */
//TODO make final
public class GamePlay {
    /**
     * Current gameplay logic to execute.
     */
    private AbstractPhaseStrategy phaseLogic;
    // Shared Model this strategy acts upon
    
    
    /** Initial phases */
    private final LinkedHashSet<AbstractPhaseStrategy> initPhases;
    private final LinkedHashSet<AbstractPhaseStrategy> gamePhases;
    // Current phase being played by the players.
    private Iterator<AbstractPhaseStrategy> phaseIt;
    
    // TODO Add a model?
    // Maps dice total to the player that made the roll.
    private final SortedMap<Integer, Player> rolls;
    

    public GamePlay() {
		initPhases = new LinkedHashSet<>();
		gamePhases = new LinkedHashSet<>();
		rolls = new TreeMap<>(new Util.ReverseIntegerSortComparator());
		
		createInitPhases();
		createGamePhases();
    }
   
    private void createInitPhases() {
	initPhases.add(new PlayerOrderPhase(this));
	initPhases.add(new StartingPosPhase(this));
	initPhases.add(new StartingKingdomPhase(this));
	initPhases.add(new StartingForcesPhase(this));
	initPhases.add(new ExchangePhase(this));
	
	// Set current phase logic/strategy
	phaseIt = initPhases.iterator();
	phaseLogic = phaseIt.next();
	
	// TODO move me elsewhere.
	// Signal start of phase
	phaseLogic.phaseStart();
    }
    
    private void createGamePhases() {
	gamePhases.add(new GoldCollectPhase(this));
	gamePhases.add(new RecruitCharPhase(this));
	gamePhases.add(new RecruitThingsPhase(this));
	gamePhases.add(new RandomEventPhase(this));
	gamePhases.add(new MovementPhase(this));
	gamePhases.add(new CombatPhase(this));
	gamePhases.add(new ConstructionPhase(this));
	gamePhases.add(new SpecialPowersPhase(this));
	gamePhases.add(new ChangePlayOrderPhase(this));
    }
    
    public void addPlayerRoll(int total, Player p) {
	// TODO Handle case where two players have the same dice total.
	rolls.put(total, p);
    }
    
    public void clearRolls() {
	rolls.clear();
    }
    
    public final Collection<Player> getPlayersHighToLow() {
	return rolls.values();
    }
    
    private void nextPhase() {
	
	if (!phaseIt.hasNext()) {    
	    // Move iterator to gamePhases.
	    phaseIt = gamePhases.iterator();
	}
	phaseLogic = phaseIt.next();

	// Phase start
	phaseLogic.phaseStart();
    }
    
    /**
     * Goes to the next player, and switches phases if needed.
     */
    public final void next() {
	// TODO make sure to no mismatch Game "state" stuff and game "logic" stuff in here. State stuff goes in Game.
	Game game = GameService.getInstance().getGame();
	
	// Execute logic then go to next player / phase
	phaseLogic.execute(null);
	
	// Signal end of phase
	if (game.isLastPlayer()) {
	    phaseLogic.phaseEnd();
	    nextPhase();    // Next call to execute, does new phase logic.
	}
	
	// State modification. Call method in game
	game.nextPlayer();
    }

}

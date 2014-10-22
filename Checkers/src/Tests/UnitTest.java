package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import AI.AI;
import Game.Game;

import org.junit.*;

import Visuals.BlackToken;
import Visuals.RedToken;
import Visuals.Tokens;

public class UnitTest extends Game {
	@SuppressWarnings("unused")
	private static Game game;
	protected  List<Tokens> rInitial;
	protected  List<Tokens> bInitial;
	private int[] rFinal;
	private int[] bFinal;
	private ArrayList<Tokens> rExpected;
	private ArrayList<Tokens> bExpected;
	private static int count=1;

	@Before
	public void setupTest(){
		System.out.println("Test: " + count++);
		game = new Game();
		rExpected = new ArrayList<Tokens>();
		bExpected = new ArrayList<Tokens>();
	
	}

	/**
	 * helper: Create List of Initial Tokens
	 */
	public void setupInitialState() {
		rInitial = new ArrayList<Tokens>();
		bInitial = new ArrayList<Tokens>();
		for (int i : rStart){
			RedToken tok = new RedToken(i);
			rInitial.add(tok);
		}
		for (int i : bStart){
			BlackToken tok = new BlackToken(i);
			bInitial.add(tok);
		}
	}
	
	/**
	 * Helper Create List of Final Tokens
	 */
	public void setupExpectedState() {
		rExpected = new ArrayList<Tokens>();
		bExpected = new ArrayList<Tokens>();
		for (int i : rFinal){
			RedToken tok = new RedToken(i);
			rExpected.add(tok);
		}
		for (int i : bFinal){
			BlackToken tok = new BlackToken(i);
			bExpected.add(tok);
		}
	}
		
	
	@Test
	public void testAvoid1(){
		// Move away from attacking piece
		// direction right
		
		//Create test board
		rStart = new int[]{35};
		bStart = new int[]{53};
		rFinal = new int[]{35};
		bFinal = new int[]{46};
		setupGame();
		setupInitialState();
		setupExpectedState();
		
		//Assert starting Positions
		assertEquals(rInitial,rTokens);
		assertEquals(bInitial,bTokens);
		
		//make AI make a move
		AI ai = new AI();
		ai.findAMove();
		
		//Assert expected behavior
		assertEquals(rExpected,rTokens);
		assertEquals(bExpected,bTokens);
	}
	
	@Test
	public void testAvoid2(){
		// Move away from attacking piece
		// direction left
		
		//Create test board
		rStart = new int[]{35};
		bStart = new int[]{49};
		rFinal = new int[]{35};
		bFinal = new int[]{40};
		setupGame();
		setupInitialState();
		setupExpectedState();
		
		//Assert starting Positions
		assertEquals(rInitial,rTokens);
		assertEquals(bInitial,bTokens);
		
		//make AI make a move
		AI ai = new AI();
		ai.findAMove();
		
		//Assert expected behavior
		assertEquals(rExpected,rTokens);
		assertEquals(bExpected,bTokens);
		
	}

	@Test
	public void testAvoid3(){
		// has a guard 
		// direction right
		
		//Create test board
		rStart = new int[]{35,39,46};
		bStart = new int[]{51,53};
		rFinal = new int[]{35,39,46};
		bFinal = new int[]{44,53};
		setupGame();
		setupInitialState();
		setupExpectedState();
		
		//Assert starting Positions
		assertEquals(rInitial,rTokens);
		assertEquals(bInitial,bTokens);
		
		//make AI make a move
		AI ai = new AI();
		ai.findAMove();
		
		//Assert expected behavior
		assertEquals(rExpected,rTokens);
		assertEquals(bExpected,bTokens);
		
	}
	
}

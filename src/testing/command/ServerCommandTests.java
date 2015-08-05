package testing.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerCommandTests {

	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	
	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"testing.command.AcceptTradeCommand",
				"testing.command.BuildCityCommand",
				"testing.command.BuildRoadCommand",
				"testing.command.BuildSettlementCommand",
				"testing.command.BuyDevCardCommand",
				"testing.command.CreateGameCommand",
				"testing.command.DiscardCardsCommand",
				"testing.command.FinishTurnCommand",
				"testing.command.MaritimeTradeCommand",
				"testing.command.MonopolyCommand",
				"testing.command.MonumentCommand",
				"testing.command.OfferTradeCommand",
				"testing.command.RoadBuildingCommand",
				"testing.command.RollNumberCommand",
				"testing.command.RobPlayerCommand",
				"testing.command.SendChatCommand",
				"testing.command.SoldierCommand",
				"testing.command.YearOfPlentyCommand"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

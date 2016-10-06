package test;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.DataStore;
import pojo.FootballTeam;

public class TestRestfulTeams {

	private DataStore dataHouse = new DataStore();

	//Add 2 teams manually for tests - see setup();
	FootballTeam tinyTeam = new FootballTeam();
	FootballTeam mcfc = new FootballTeam();
	
	@Before
	public void setUp() throws Exception {
		Calendar cal = Calendar.getInstance();
		
		mcfc.setName("Manchester City");
		mcfc.setCompetition("Premier League");
		cal.set(Calendar.YEAR, 1880);
		mcfc.setDateOfCreation(cal.getTime());
		mcfc.setCity("Manchester");
		mcfc.setNumberOfPlayers(48);
		mcfc.setOwner("Sheikh Mansour");
		mcfc.setStadiumCapacity(60000);
		
		dataHouse.addFootballTeam(mcfc);
		
		tinyTeam.setName("Tiny Team FC");
		tinyTeam.setCompetition("Non League");
		cal.set(Calendar.YEAR, 1995);
		tinyTeam.setDateOfCreation(cal.getTime());
		tinyTeam.setCity("Woodford");
		tinyTeam.setNumberOfPlayers(17);
		tinyTeam.setOwner("Pub Jeff");
		tinyTeam.setStadiumCapacity(500);
		
		dataHouse.addFootballTeam(tinyTeam);
	}

	@Test
	public void testAddFootballTeam() {
		dataHouse.addFootballTeam(FootballTeam.SALISBURY_FC);
		// The team was added to the dataHouse with this name.
		assertTrue(dataHouse.teamIsStoredWithName("Salisbury FC"));
	}
	
	@Test
	public void testGetFootballTeamByName() {
		FootballTeam testTeam = dataHouse.getFootballTeamWithName("Nonsense Team Name FC");
		// Check null returned, no errors.
		assertTrue(testTeam == null);
		
		// Try an actual team
		testTeam = dataHouse.getFootballTeamWithName("Tiny Team FC");
		// Tiny Team play in Woodfood.
		assertTrue(testTeam.getCity().equals("Woodford"));
	}

	@Test
	public void testGetAllFootballTeams() {
		List<FootballTeam> allteams = dataHouse.getAllFootballTeams();
		// Man City, Tiny Team FC.
		assertTrue(allteams.size() == 2);
		// Add one, does it increment?
		dataHouse.addFootballTeam(FootballTeam.SALISBURY_FC);
		allteams = dataHouse.getAllFootballTeams();
		assertTrue(allteams.size() == 3);
		// Does it have all our teams?
		assertThat(allteams, hasItems(mcfc, tinyTeam, FootballTeam.SALISBURY_FC));
	}

	@Test
	public void testGetAllTeamsByCapacity() {
		dataHouse.addFootballTeam(FootballTeam.SALISBURY_FC);
		List<FootballTeam> allteams = dataHouse.getAllFootballTeamsSorted();
		assertTrue(allteams.get(0).getName().equals("Manchester City"));
		// Even though we just added Salisbury, they are bigger than "Tiny Team".
		assertTrue(allteams.get(1).getName().equals("Salisbury FC"));
		assertTrue(allteams.get(2).getName().equals("Tiny Team FC"));
	}
}

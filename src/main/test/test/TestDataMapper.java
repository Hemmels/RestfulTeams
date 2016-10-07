package test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import data.DataMapper;
import pojo.FootballTeam;

public class TestDataMapper {
	
	@Test
	public void testDataToJson(){
		FootballTeam testTeam = FootballTeam.SALISBURY_FC;
		String json = DataMapper.dataToJson(testTeam);
		assertTrue(json != null);
		
		// Does it make sense?
		assertTrue(json != null);
		assertTrue(json.length() > 0);
		assertTrue(json.contains("owner") && json.contains("numberOfPlayers"));
		assertTrue(json.contains("Salisbury") && json.contains("Matt Grigsby"));
	}

	@Test
	public void testJsonToTeam(){
		// String checks
		String legitMessage = DataMapper.dataToJson(FootballTeam.SALISBURY_FC);
		FootballTeam decoded = DataMapper.jsonToTeam(legitMessage);
		
		// Is it a good, fully working object?
		assertTrue(decoded != null);
		assertTrue(decoded.getCity()!= null && decoded.getCity().length() > 0);
		assertTrue(decoded.getOwner().length() > 0);
		assertTrue(decoded.getOwner().length() < 50);
		assertTrue(decoded.getDateOfCreation() instanceof Date);
		assertTrue(decoded.getNumberOfPlayers() > 0);
	}

}

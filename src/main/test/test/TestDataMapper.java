package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.DataMapper;
import pojo.FootballTeam;

public class TestDataMapper {
	
	@Test
	public void testDataToJson(){
		FootballTeam testTeam = FootballTeam.SALISBURY_FC;
		String json = DataMapper.dataToJson(testTeam);
		assertTrue(json != null);
		
		// Does it make sense?
		assertTrue(json.contains("Salisbury") && json.contains("Matt Grigsby"));
	}

	@Test
	public void testJsonToTeam(){
		// String checks
		String message = DataMapper.dataToJson(FootballTeam.SALISBURY_FC);
		assertTrue(message != null);
		assertTrue(message.length() > 0);
		assertTrue(message.contains("owner") && message.contains("numberOfPlayers"));
		
		// Is it Json?
		ObjectMapper mapper = new ObjectMapper();
		FootballTeam decoded = null;
		try {
			decoded = mapper.readValue(message, FootballTeam.class);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(decoded != null);
		
		// Does it make sense?
		assertTrue(decoded.getCity()!= null && decoded.getCity().length() > 0);
		assertTrue(decoded.getOwner().length() > 0);
		assertTrue(decoded.getOwner().length() < 50);
		assertTrue(decoded.getDateOfCreation() instanceof Date);
		assertTrue(decoded.getNumberOfPlayers() > 0);
	}

}

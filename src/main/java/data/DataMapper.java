package data;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import pojo.FootballTeam;

public class DataMapper {

	/**
	 * Convert our object into a JSON String using Jackson's ObjectMapper.
	 * @param data
	 * @return
	 */
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
    
    /**
     * Attempt to recreate a FootballTeam from the given JSON.
     * @param json
     * @return
     */
    public static FootballTeam jsonToTeam(String json){
    	ObjectMapper mapper = new ObjectMapper();
    	FootballTeam team = null;
    	try {
			team = mapper.readValue(json, FootballTeam.class);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    	return team;
    }

    /**
     * A "helpful" method for demonstrating the system
     * @param name 
     * @param city
     * @param owner
     * @param stadiumCapacity
     * @param competition
     * @param numberOfPlayers
     * @param dateOfCreation
     * @return
     */
    public static FootballTeam createFootballTeam(String name, String city, String owner, int stadiumCapacity, 
    		String competition, int numberOfPlayers, Date dateOfCreation){
        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setName(name);
        footballTeam.setCity(city);
        footballTeam.setOwner(owner);
        footballTeam.setStadiumCapacity(stadiumCapacity);
        footballTeam.setCompetition(competition);
        footballTeam.setNumberOfPlayers(numberOfPlayers);
        footballTeam.setDateOfCreation(dateOfCreation);
        return footballTeam;
    }
}

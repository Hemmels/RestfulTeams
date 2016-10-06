package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import pojo.FootballTeam;

public class DataStore {

	private final static Logger logger = LoggerFactory.getLogger(DataStore.class);

	private static final MongoClient mongo = new MongoClient();
	private static final MongoDatabase db = mongo.getDatabase("sis");
	private static final MongoCollection<Document> mongoTable = db.getCollection("jsonfootballteams");
	
	// Used here instead of a database structure
    private Map<String, FootballTeam> footballTeams = new HashMap<String, FootballTeam>();
    
    // TODO: use UUID.
    public String addFootballTeam(FootballTeam footballTeam){
    	logger.info("Adding " + footballTeam.getName());
        footballTeams.put(footballTeam.getName(), footballTeam);
        return footballTeam.getName();
    }
    
    public List<FootballTeam> getAllFootballTeams(){
    	// Easier to grab the stream of keys and get each one
         return footballTeams.keySet().stream().map((id) -> footballTeams.get(id)).collect(Collectors.toList());
    }
    
    public List<FootballTeam> getAllFootballTeamsSorted(){
         List<FootballTeam> storedTeams = getAllFootballTeams();
         // TODO: Is the collection sort faster than sorting the stream?
         storedTeams.sort((FootballTeam a, FootballTeam b) -> (int) (b.getStadiumCapacity() - a.getStadiumCapacity()));
         return storedTeams;
    }

	public FootballTeam getFootballTeamWithName(String teamName) {
    	logger.info("Searching data for " + teamName);
		// TODO: Is .get() on the collection faster than the stream filter?
		List<FootballTeam> filteredTeams = footballTeams.keySet().stream().filter(n -> n.equals(teamName))
				.map((id) -> footballTeams.get(id)).collect(Collectors.toList());
		// TODO: Force unique adding so this can only retuurn 1 or 0 team.
		if (filteredTeams.size() > 0){
			return filteredTeams.get(0);
		}
		else return null;
	}
	
	@VisibleForTesting
	public boolean teamIsStoredWithName(String name){
		return footballTeams.keySet().contains(name);
	}

	public void saveTeam(String uuid, FootballTeam teamFromJson) {
        mongoTable.insertOne(new Document(uuid, teamFromJson));
	}
}

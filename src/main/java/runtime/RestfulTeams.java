package runtime;

import static spark.Spark.get;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.DataMapper;
import data.DataStore;
import pojo.FootballTeam;

public class RestfulTeams {

	public static final int HTTP_OK = 200;
	public static final int HTTP_BAD_REQUEST = 400;
	public static final int HTTP_SERVICE_UNAVAILABLE = 503;
	
	private static final int ALL_TEAMS = 1, NAMED_TEAM = 2, SORTED = 3;

	private final static Logger logger = LoggerFactory.getLogger(RestfulTeams.class);
	
	public static void main( String[] args) {
		DataStore dataHouse = new DataStore();
		dataHouse.addFootballTeam(FootballTeam.SALISBURY_FC);
		
		get("/teams", (request, response) -> {
			int teamMethod = ALL_TEAMS;
        	String nameParam = request.queryParams("name");
        	String sortParam = request.queryParams("sort");
        	
        	// Was the request for "sorted"?
        	if (sortParam != null && !sortParam.equals("false")){
            	teamMethod = SORTED;
    	    	logger.info("Get request for '/teams' sorted");
        	}
        	// Or for 1 team?
        	else if (nameParam != null){
            	teamMethod = NAMED_TEAM;
    	    	logger.info("Get request for '/teams' with name: " + nameParam);
        	}
        	else logger.info("Get request for '/teams'");
        	
		    response.status(HTTP_OK);
		    response.type("application/json");
		    
		    switch (teamMethod){
			    case SORTED:{
			    	 return DataMapper.dataToJson(dataHouse.getAllFootballTeamsSorted());
			    }
			    case NAMED_TEAM:{
			    	 return DataMapper.dataToJson(dataHouse.getFootballTeamWithName(nameParam));
			    }
			    default:{
			    	return DataMapper.dataToJson(dataHouse.getAllFootballTeams());
			    }
		    }
		});
        
        post("/teams", (request, response) -> {
        	logger.info("Post request to '/teams'");
        	String jsonTeam = request.body();
            FootballTeam teamFromJson = DataMapper.jsonToTeam(jsonTeam);
            if (teamFromJson == null || !teamFromJson.isValid()) {
            	// TODO: Better error handling (String / Message?) of why it failed.
            	logger.warn("Invalid team json posted in request body");
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            // TODO: Unique ids?
            String uuid = dataHouse.addFootballTeam(teamFromJson);
            dataHouse.saveTeam(uuid, teamFromJson);
            response.status(HTTP_OK);
            response.type("application/json");
        	logger.info("Added a new team: " + uuid);
            return uuid;
        });
        
        System.out.println("Ready for GET/POST requests!");
        System.out.println("Here's a JSON team for reference (this has already been added! Try querying with a GET to '/teams')\n" + DataMapper.dataToJson(FootballTeam.SALISBURY_FC));
    }

}

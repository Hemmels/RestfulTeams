package runtime;

import static spark.Spark.get;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.DataMapper;
import data.DataStore;
import pojo.FootballTeam;

public class RestfulTeams {

	public static int HTTP_OK = 200;
	public static int HTTP_BAD_REQUEST = 400;
	public static int HTTP_SERVICE_UNAVAILABLE = 503;

	private final static Logger logger = LoggerFactory.getLogger(RestfulTeams.class);
	
	public static void main( String[] args) {
		DataStore dataHouse = new DataStore();
		
		get("/teams", (request, response) -> {
			boolean sort = false;
        	String sortParam = request.queryParams("sort");
        	
        	// Was the request for "sorted"?
        	if (sortParam != null && !sortParam.equals("false")){
        		sort = true;
    	    	logger.info("Get request for '/teams' sorted");
        	}
        	else logger.info("Get request for '/teams'");
        	
		    response.status(HTTP_OK);
		    response.type("application/json");
		    if (sort){
		    	 return DataMapper.dataToJson(dataHouse.getAllFootballTeamsSorted());
		    }
		    return DataMapper.dataToJson(dataHouse.getAllFootballTeams());
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
    }

}

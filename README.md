# RestfulTeams

A Sparkjava web server with a simple in-memory Football team REST/Mongo service for lookup and entry via the web.

07/10/16 11:27 - There is now a completed branch for running the webserice, mongo and docker from one file. 
Checkout the **mongoenabled** branch, and run
`docker-compose up` 
from the checked-out directory.  
(This utalises 2 images, the mongo base, and a small centos install with java and the (fat) jar file pre-installed in /usr/lib) 

### Methodology
1. Read the requirements. Decided becuase this isn't a web application it doesn't need Spring anywhere.
1. Created a Java 8 Web app project
1. Added gradle nature; ran init (Gradle is super useful for configuring builds/projects)
1. Added maven/jcenter repos and configured project dependencies into gradle build file.
1. Ran gradle compileJ
1. Built application:
  - Created POJO (with lombok)
  - Created RestfulTeams.java (with Sparkjava) and required get/set interceptors
  - Created DataMapper for all JSON related activities (with Jackson)
  - Created DataStore for managing our in-memory data and testing.
  - Added mongo as an afterthought.
  - Wrote tests to cover off all business logic methods.

### Building and running
To run, clone the repository and from the command line, run:

`gradlew build`

And then

`gradlew run`

Or, run in an IDE by running the RestfulTeams class in the runtime package. If importing as a project, import as a general, or java project if prompted.

If you have docker-machine running, you could try going 1 directory up, and building the Dockerfile (not guaranteed!) with

`docker build RestfulTeams -t thisTag`

and then

`docker run thisTag`

## Usage Instructions

There are currently 4 accessable functions:

**getAllFootballTeams** - any GET request not otherwise mentioned to '/teams'  
**getAllFootballTeamsSorted** - a GET request to '/teams' with a parameter of "sort" that isn't "false" (this can be anything "?sort", "?sort=x" etc, but not "?sort=false")  
**getFootballTeamWithName** - a GET request with a "name" parameter. This will override any of the above.  
**addFootballTeam** - a POST request to '/teams'. If there is a valid JSON entity for a team, this will be added. A team must have at least a name, a city, and a date of creation.  

On simply loading the application, it will print to the console an example team that was used to add itself (for copy/paste).

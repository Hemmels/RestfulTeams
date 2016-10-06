# RestfulTeams

A Sparkjava web server with a simple in-memory Football team REST/Mongo service for lookup and entry via the web.

Methodology:
Read the requirements. Decided becuase this isn't a web application it doesn't need Spring anywhere.
Created a Java 8 Web app project
Added gradle nature; ran init (Gradle is super useful for configuring builds/projects)
Added maven/jcenter repos and configured project dependencies into gradle build file.
Ran gradle compileJ
Built application:
  - Created POJO (with lombok)
  - Created RestfulTeams.java (with Sparkjava) and required get/set interceptors
  - Created DataMapper for all JSON related activities (with Jackson)
  - Created DataStore for managing our in-memory data and testing.
  - Added mongo as an afterthought.
  - Wrote tests to cover off all business logic methods.

To run, clone the repository and from the command line, run:

gradlew build

And then

gradlew run

Or, run in an IDE by running the RestfulTeams class in the runtime package. If importing as a project, import as a general, or java project if prompted.

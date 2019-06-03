# XXE-lab
Educational application for testing XXE vulnerabilities. The lab consists of 2 applications (API+frontend). Go through the following steps to locally run the lab.

## Backend
 
 > Java spring project with maven

#### Running locally
 - Main class: `xxe.Launcher`
 - Working directory: xxe
 - Classpath: xxe
 
 In case of using Intellij IDEA:
 ```
 - Set up new configuration
 - mark configuration as Spring Boot 
 - mark main class as xxe.Launcher
 - start API application in run or debug mode
 - check if API is running - https://localhost:9020/swagger-ui.html
 ```
 ## Frontend
 
 > A Vue.js project
 
 #### Running locally
 
 ```
 1) Open terminal from /.../frontend directory
 
 2) install dependencies
 npm install
 or
 yarn install
 
 3) serve at localhost:8081
 npm run dev
 or
 yarn run dev
 
 now the application is served at http://localhost:8081

 ```
 
 ###Starting the XXE-LAB
 * After going through the previous steps and running the applications verify that API and frontend applications are running. Then:
    * Go to http://localhost:8081
    * Read the introductions and use the code snippets in the upcoming lessons.
    * Go to XXE-attack tab and try to demonstrate the XXE vulnerability. You will have to create a payload to be uploaded via XLM upload. Study the requests and responses between frontend and backend and create the payload. Try to read server's (your local environment) files and cause a denial of service attack. If you choose to read server files, the data is put into java object that will be reflected back to frontend via the table below.
    * The remaining 4 tabs - try to make the application immune to XXE. Look for the specific parser method and configure the parser to prevent XXE. Restart API and push the check solution button in order to get feedback if your solution was correct.
 
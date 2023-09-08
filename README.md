# github-releases-manager
This application monitors a GitHub repository for new releases of a certain tag every 15 minutes and sends out a webhook if there is a new one. It also provides a /download/{repositoryId} endpoint that redirects to the download of the latest release. The regex for the release file to download can be set in the database.

The database server needs to be configured in the application.properties file and the database tables will be auto-created on first startup.

All application configuration is done in the database and the current data in it will be loaded on each task run.
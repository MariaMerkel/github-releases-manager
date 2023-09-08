# github-releases-manager
This application monitors a GitHub repository for new releases every 15 minutes and sends out a webhook if there is a new one. It also provides a /download/{repositoryId} endpoint that redirects to the download of the latest release. It will only check for new releases that are flagged as latest release.

The database server needs to be configured in the application.properties file and the database tables will be auto-created on first startup.

All application configuration is done in the database and the current data in it will be loaded on each task run.

The application was initially built to trigger deployments of third-party software when a new release is published via a third-party CI (in my case TeamCity), but it could be useful for other use cases as well.
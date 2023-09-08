package cc.maria.githubreleasesmanager;

public class GitHubReleaseResponse {
    public int id;
    public Asset[] assets;

    public static class Asset {
        public String name;
        public String browser_download_url;
    }
}

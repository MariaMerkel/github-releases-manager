package cc.maria.githubreleasesmanager;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Repository {
    @Id
    private String id;
    private String repo;
    private String webhook;
    private String lastRelease;

    protected Repository () {}

    public String getId() {
        return id;
    }

    public String getRepo() {
        return repo;
    }

    public String getWebhook() {
        return webhook;
    }

    public String getLastRelease() {
        return lastRelease;
    }

    public void setLastRelease(String lastRelease) {
        this.lastRelease = lastRelease;
    }
}

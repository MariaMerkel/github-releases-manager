package cc.maria.githubreleasesmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    @Autowired
    private RepositoriesRepository repositoriesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled (fixedRateString = "PT15M")
    public void sendWebhooks () {
        for (Repository repository : repositoriesRepository.findAll()) {
            GitHubReleaseResponse response = restTemplate.getForObject("https://api.github.com/repos/" + repository.getRepo() + "/releases/latest", GitHubReleaseResponse.class);
            if (Integer.toString(response.id).equals(repository.getLastRelease())) continue;

            restTemplate.postForObject(repository.getWebhook(), null, Void.class);
            repository.setLastRelease(Integer.toString(response.id));
            repositoriesRepository.save(repository);
        }
    }
}

package cc.maria.githubreleasesmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "Bearer eyJ0eXAiOiAiVENWMiJ9.VlNuX1lTX0VYVURiMHpGbTNXSnZ5bGFVQm1B.ZmQ2NWQ2NTctMjNjNC00MWMzLWJiZTgtNjczYzVlZmRiOTAx");
            restTemplate.postForObject(repository.getWebhook(), new HttpEntity<>("", headers), Void.class);
            repository.setLastRelease(Integer.toString(response.id));
            repositoriesRepository.save(repository);
        }
    }
}

package cc.maria.githubreleasesmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class DownloadController {
    @Autowired
    private RepositoriesRepository repositoriesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping ("/download/{id}")
    public RedirectView download (@PathVariable String id) {
        Optional<Repository> optionalRepository = repositoriesRepository.findById(id);
        if (optionalRepository.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        GitHubReleaseResponse response = restTemplate.getForObject("https://api.github.com/repos/" + optionalRepository.get().getRepo() + "/releases/latest", GitHubReleaseResponse.class);

        for (GitHubReleaseResponse.Asset asset : response.assets)
            if (asset.name.matches(optionalRepository.get().getDownloadRegex()))
                return new RedirectView(asset.browser_download_url);

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

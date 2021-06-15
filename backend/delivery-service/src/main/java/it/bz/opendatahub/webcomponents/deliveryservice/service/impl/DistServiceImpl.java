package it.bz.opendatahub.webcomponents.deliveryservice.service.impl;

import it.bz.opendatahub.webcomponents.deliveryservice.data.DistFile;
import it.bz.opendatahub.webcomponents.deliveryservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.deliveryservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.deliveryservice.service.DistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DistServiceImpl implements DistService {
    private final WorkspaceRepository workspaceRepository;
    private final WebcomponentRepository webcomponentRepository;

    @Autowired
    public DistServiceImpl(final WorkspaceRepository workspaceRepository,
                           final WebcomponentRepository webcomponentRepository) {
        this.workspaceRepository = workspaceRepository;
        this.webcomponentRepository = webcomponentRepository;
    }

    @Override
    public DistFile getDistFile(String webcomponentId, String file, String version) {
        if(version == null) {
            version = webcomponentRepository.getLatestVersionOfWebcomponent(webcomponentId);
        }

        Path filePath = Paths.get(webcomponentId, version, "dist", file);

        byte[] data = workspaceRepository.readFile(filePath);

        String mimetype = detectMimetype(file);

        return DistFile.of(file, mimetype, data);
    }

	private String detectMimetype(String filename) {
		Path path = new File(filename).toPath();
		try {
			String result = Files.probeContentType(path);
			if(result == null) {
				String result2 = URLConnection.guessContentTypeFromName(filename);
				if(result2 == null) {
					return "text/javascript";
				}

				return result2;
			}
			return result;
		} catch (IOException e) {
			return "text/plain";
		}
    }
}

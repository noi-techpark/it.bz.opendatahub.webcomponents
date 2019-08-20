package it.bz.opendatahub.webcomponents.deliveryservice.service.impl;

import it.bz.opendatahub.webcomponents.deliveryservice.data.DistFile;
import it.bz.opendatahub.webcomponents.deliveryservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.deliveryservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.deliveryservice.service.DistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DistServiceImpl implements DistService {
    private WorkspaceRepository workspaceRepository;
    private WebcomponentRepository webcomponentRepository;

    @Autowired
    public DistServiceImpl(WorkspaceRepository workspaceRepository,
                           WebcomponentRepository webcomponentRepository) {
        this.workspaceRepository = workspaceRepository;
        this.webcomponentRepository = webcomponentRepository;
    }

    @Override
    public DistFile getDistFile(String webcomponentId, String file, String version) {
        if(version == null) {
            version = webcomponentRepository.getLatestVersionOfWebcomponent(webcomponentId);
        }

        Path filePath = Paths.get(webcomponentId, "dist", version, file);

        byte[] data = workspaceRepository.readFile(filePath);

        String mimetype = detectMimetype(file);

        return DistFile.of(file, mimetype, data);
    }

    private String detectMimetype(String filename) {
        String mimetype = "text/plain";

        if(filename.endsWith(".css")) {
            mimetype = "text/css";
        }
        else if(filename.endsWith(".js")) {
            mimetype = "application/javascript";
        }

        return mimetype;
    }
}

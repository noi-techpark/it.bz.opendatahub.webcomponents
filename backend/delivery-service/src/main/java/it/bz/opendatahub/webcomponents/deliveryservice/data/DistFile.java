package it.bz.opendatahub.webcomponents.deliveryservice.data;

import it.bz.opendatahub.webcomponents.deliveryservice.service.DistService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class DistFile {
    public static DistFile of(String file, String mimetype, byte[] data) {
        DistFile result = new DistFile();
        result.setName(file);
        result.setMimetype(mimetype);
        result.setPayload(data);

        return result;
    }

    private String name;

    private String mimetype;

    private byte[] payload;
}

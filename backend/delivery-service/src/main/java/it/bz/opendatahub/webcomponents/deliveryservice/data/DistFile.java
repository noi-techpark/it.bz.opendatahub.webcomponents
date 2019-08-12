package it.bz.opendatahub.webcomponents.deliveryservice.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class DistFile {
    private String name;

    private String mimetype;

    private byte[] payload;
}

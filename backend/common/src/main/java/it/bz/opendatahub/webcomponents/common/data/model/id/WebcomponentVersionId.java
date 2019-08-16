package it.bz.opendatahub.webcomponents.common.data.model.id;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public final class WebcomponentVersionId implements Serializable {
    private String webcomponentUuid;

    private String versionTag;

    public static WebcomponentVersionId of(String webcomponentUuid, String versionTag) {
        return new WebcomponentVersionId(webcomponentUuid, versionTag);
    }
}

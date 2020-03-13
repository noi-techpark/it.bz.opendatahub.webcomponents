package it.bz.opendatahub.webcomponents.common.data.model.id;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public final class WebcomponentVersionId implements Serializable {
	private static final long serialVersionUID = -3551018510641602498L;

	private String webcomponentUuid;

    private String versionTag;

    public static WebcomponentVersionId of(String webcomponentUuid,
                                           String versionTag) {

        return new WebcomponentVersionId(webcomponentUuid, versionTag);
    }
}

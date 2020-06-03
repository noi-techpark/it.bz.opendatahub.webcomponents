package it.bz.opendatahub.webcomponents.crawlerservice.data.model.id;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public final class OriginTagId implements Serializable {
	private static final long serialVersionUID = -2445325599108672031L;

	private String originUuid;

    private String tagName;

    public static OriginTagId of(String originUuid,
                                 String tagName) {

        return new OriginTagId(originUuid, tagName);
    }
}

package it.bz.opendatahub.webcomponents.crawlerservice.data.model.id;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public final class OriginTagId implements Serializable {
    private String originUuid;

    private String tagName;

    public static OriginTagId of(String originUuid, String tagName) {
        return new OriginTagId(originUuid, tagName);
    }
}
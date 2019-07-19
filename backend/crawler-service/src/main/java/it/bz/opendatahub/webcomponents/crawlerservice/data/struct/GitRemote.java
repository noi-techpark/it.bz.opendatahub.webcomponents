package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GitRemote {
    private String uuid;

    private String url;

    private String api;
}

package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitRemote {
    public static GitRemote of(String url, String api) {
        GitRemote gitRemote = new GitRemote();

        gitRemote.setUrl(url);
        gitRemote.setApi(api);

        return gitRemote;
    }

    public static GitRemote of(Origin mappedOrigin) {
        GitRemote gitRemote = new GitRemote();

        gitRemote.setUrl(mappedOrigin.getUrl());
        gitRemote.setApi(mappedOrigin.getApi());

        return gitRemote;
    }

    public static GitRemote of(OriginModel originModel) {
        GitRemote gitRemote = new GitRemote();

        gitRemote.setUrl(originModel.getUrl());
        gitRemote.setApi(originModel.getApi());

        return gitRemote;
    }

    private String url;

    private String api;
}

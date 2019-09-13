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
        return GitRemote.of(mappedOrigin.getUrl(), mappedOrigin.getApi());
    }

    public static GitRemote of(OriginModel originModel) {
        return GitRemote.of(originModel.getUrl(), originModel.getApi());
    }

    private String url;

    private String api;
}

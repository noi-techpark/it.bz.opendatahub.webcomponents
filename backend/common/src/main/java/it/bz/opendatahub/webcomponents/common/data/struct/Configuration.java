package it.bz.opendatahub.webcomponents.common.data.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Configuration {

    private String tagName;

    private List<Options> options = new ArrayList<>();

    @Getter
    @Setter
    public static class Options {
        private String key;

        private String type;

        private Object options;
    }
}
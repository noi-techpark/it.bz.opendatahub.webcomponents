package it.bz.opendatahub.webcomponents.common.data.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Dist {
    private String basePath;

    private List<String> files = new ArrayList<>();

    public static Dist of(String basePath, List<String> files) {
        Dist newDist = new Dist();

        newDist.setBasePath(basePath);
        newDist.setFiles(new ArrayList<>(files));

        return newDist;
    }
}

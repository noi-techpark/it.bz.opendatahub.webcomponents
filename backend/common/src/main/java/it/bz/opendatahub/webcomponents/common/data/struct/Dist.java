// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

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
	private List<DistSource> scripts = new ArrayList<>();

    public static Dist of(String basePath, List<String> files, List<DistSource> scripts) {
        Dist newDist = new Dist();

        newDist.setBasePath(basePath);
		newDist.setFiles(new ArrayList<>(files));
		newDist.setScripts(new ArrayList<>(scripts));

        return newDist;
    }
}

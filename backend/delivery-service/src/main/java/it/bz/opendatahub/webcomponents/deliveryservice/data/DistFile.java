// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistFile {
    public static DistFile of(String file,
                              String mimetype,
                              byte[] data) {

        DistFile result = new DistFile();
        result.setName(file);
        result.setMimetype(mimetype);
        result.setPayload(data);

        return result;
    }

    private String name;

    private String mimetype;

    private byte[] payload;
}

// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.common.data.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
    private String name;

    private String email;

    private String organization;

    private String organizationUrl;
}

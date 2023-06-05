// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.service;

import it.bz.opendatahub.webcomponents.deliveryservice.data.DistFile;

public interface DistService {
    DistFile getDistFile(String webcomponentId, String file, String version);
}

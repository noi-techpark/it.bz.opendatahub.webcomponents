// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.SpdxLicenseRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;

@Converter
public class SpdxLicenseWebConverter extends BeanConverter<SpdxLicense, SpdxLicenseRest> {
}

// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

@Converter
public class WebcomponentVersionPersistenceConverter extends BeanConverter<WebcomponentVersionModel, WebcomponentVersion> {
}

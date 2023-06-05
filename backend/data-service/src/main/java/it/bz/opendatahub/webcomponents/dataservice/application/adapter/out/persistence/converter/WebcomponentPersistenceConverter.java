// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;

@Converter
public class WebcomponentPersistenceConverter extends BeanConverter<WebcomponentModel, Webcomponent> {
}

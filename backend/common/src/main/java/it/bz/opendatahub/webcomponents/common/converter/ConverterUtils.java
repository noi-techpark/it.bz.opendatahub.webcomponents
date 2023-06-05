// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.common.converter;

import org.springframework.beans.BeanUtils;

public class ConverterUtils {
	private ConverterUtils() {}

	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}
}

// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WebcomponentVersionRest {
    private String versionTag;

    private Date releaseTimestamp;

	private Integer distSizeTotalKb;

	private Integer lighthouseMobilePerformanceRating;

	private Integer lighthouseDesktopPerformanceRating;

	private String readMe;

	private String licenseAgreement;
}

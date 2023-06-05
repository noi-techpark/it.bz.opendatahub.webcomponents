// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import io.swagger.v3.oas.annotations.Hidden;
import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@WebAdapter
@Hidden
@Controller
public class SwaggerController {
    @GetMapping("/")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}

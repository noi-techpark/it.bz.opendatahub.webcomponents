// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
public class SwaggerController {
    @GetMapping("/")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}

// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
@Setter
public final class ErrorMessage {
    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("stacktrace")
    private String stacktrace;

    public ErrorMessage() {
        this.errorCode = 500;
        this.message = "n/a";
        this.stacktrace = "n/a";
    }

    public ErrorMessage(Throwable ex, boolean verbose) {
        this.errorCode = 500;
        this.message = ex.getMessage();
        this.stacktrace = verbose?getStackTraceAsString(ex):"";
    }

    public ErrorMessage(CoreException ex, boolean verbose) {
        this.errorCode = ex.getErrorCode();
        this.message = ex.getMessage();
        this.stacktrace = verbose?getStackTraceAsString(ex):"";
    }

    private static String getStackTraceAsString(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}

// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

export interface ContactFormRequest {
  category: string;

  nameFirst: string;

  nameLast: string;

  email: string;

  phone: string;

  text: string;

  captchaToken: string;
}

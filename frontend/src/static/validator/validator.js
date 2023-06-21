// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

// SPDX-License-Identifier: AGPL-3.0-or-later

// This script is placed here so it can be called from other places like the webcomp-test github action.
// It would probably be better to provide an API which can validate wcs-mainfest files but this works for now.

import Ajv from 'ajv';

export default function parseJson() {
  let wcsManifestParsed = {};
  this.errors = [];
  this.config = null;
  const ajv = new Ajv({
    $data: true,
    verbose: false,
    allErrors: true,
    format: 'full',
  });
  ajv.addKeyword('validDefault', {
    type: ['string', 'null', 'array'],
    modifying: true,
    validate(
      schema,
      data,
      parentSchema,
      dataPath,
      parentData,
      parentDataProperty,
      rootData
    ) {
      return (
        !!parentData.default &&
        !!parentData.values &&
        parentData.values.includes(parentData.default)
      );
    },
    errors: true,
  });
  try {
    const validate = ajv.compile(this.Schema);
    wcsManifestParsed = JSON.parse(this.wcsManifest);
    if (!validate(wcsManifestParsed)) {
      const errors = validate.errors
        .filter((error) => {
          switch (error.message) {
            case 'boolean schema is false':
            case 'should match "then" schema':
            case 'should match "else" schema':
            case 'should match some schema in anyOf':
              return false;
          }
          return true;
        })
        .map((error) => {
          if (error.params.keyword && error.params.keyword === 'validDefault') {
            error.params.keyword = 'Choose any item inside .values';
          }
          return {
            text: error.message,
            path: error.dataPath,
            params:
              Object.keys(error.params).length === 0 &&
              error.params.constructor === Object
                ? null
                : error.params,
          };
        });
      this.errors = [...this.errors, ...errors];

      return;
    }
  } catch (e) {
    if (e instanceof SyntaxError) {
      const lineNumber = this.getLineNumber(e.message);
      this.errors = [
        ...this.errors,
        {
          text: 'Syntax error' + (lineNumber ? ' @ line ' + lineNumber : ''),
        },
      ];
    } else {
      this.errors = [...this.errors, { text: e.message }];
    }
    return;
  }
  this.errors = null;
  this.config = wcsManifestParsed.configuration;
}

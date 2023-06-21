// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import Ajv from 'ajv';
import Schema from '../static/schemas/wcs-manifest-schema.json';

const validator = {
    wcsManifest: "",
    Schema,
    config: null,
    errors: null,
    getLineNumber(errorMsg) {
        let lineNumber = null;
  
        /* Firefox gives us the line number */
        let match = /line (\d+)/.exec(errorMsg);
        if (match) {
          lineNumber = match.length > 1 ? match[1] : match[0];
        } else {
          /* Other browsers just give a position in the JSON string */
          match = /position (\d+)/.exec(errorMsg);
          if (match) {
            const position = match.length > 1 ? match[1] : match[0];
            const tmp = this.wcsManifest.substring(0, position);
            lineNumber = (tmp.match(/\n/g) || '').length + 1;
          }
        }
        return lineNumber;
    },
    parseJson() {
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
                if (
                    error.params.keyword &&
                    error.params.keyword === 'validDefault'
                ) {
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
                text:
                    'Syntax error' + (lineNumber ? ' @ line ' + lineNumber : ''),
                },
            ];
            } else {
            this.errors = [...this.errors, { text: e.message }];
            }
            return;
        }
        this.errors = null;
        this.config = wcsManifestParsed.configuration;
    },
}

const express = require("express");
const app = express();

app.use(express.json())

app.post("/", (req, res) => {
    try {
        validator.wcsManifest = JSON.stringify(req.body);
        validator.parseJson();
        // const validatorJSON = JSON.stringify(validator);
        res.json(validator);
    } catch (e) {
        console.error(e);
    }
});

export default app
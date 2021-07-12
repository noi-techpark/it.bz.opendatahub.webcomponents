import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';

function buildAttribute(rawKey: string, rawValue: string): string {
  const key = rawKey.trim();
  const value = rawValue.trim();
  return key + '="' + value + '";';
}

export function parseSnippetAttributes(
  snipp: string,
  config: WebcomponentConfigurationModel
): string {
  console.log(snipp);
  if (!snipp) {
    return '';
  }

  let pos = snipp.search(config.configuration.tagName);
  let result = '';

  if (pos < 0) {
    return result;
  }

  pos += config.configuration.tagName.length;

  let isKey = true;
  let isValue = false;
  let key = '';
  let value = '';
  let isQuoted = false;
  for (let i = pos; i < snipp.length; i++) {
    const c = snipp.charAt(i);
    if (isKey) {
      switch (c) {
        case '=':
          isKey = false;
          isValue = true;
          break;
        case '>':
          if (key.trim().length > 0) {
            result += key.trim() + ';';
          }
          return result;
        case ' ':
          if (key.trim().length > 0) {
            result += key.trim() + ';';
          }
          break;
        default:
          key += c;
      }
    } else if (isValue) {
      switch (c) {
        case '"':
          if (isQuoted) {
            result += buildAttribute(key, value);
            isKey = true;
            isValue = false;
            isQuoted = false;
            key = '';
            value = '';
          } else {
            isQuoted = true;
          }
          break;
        case ' ':
          if (isQuoted) {
            value += ' ';
          } else {
            result += buildAttribute(key, value);
            isKey = true;
            isValue = false;
            key = '';
            value = '';
          }
          break;
        case '>':
          if (isQuoted) {
            value += '>';
          } else {
            result += buildAttribute(key, value);
            return result;
          }
          break;
        default:
          value += c;
      }
    }
  }
  return result;
}

export function getDistIncludes(
  config: WebcomponentConfigurationModel
): Array<string> {
  const scripts = [];

  // Wait until the async loadData method has finished
  // eslint-disable-next-line no-prototype-builtins
  if (config.hasOwnProperty('dist')) {
    if (
      // eslint-disable-next-line no-prototype-builtins
      config.dist.hasOwnProperty('scripts') &&
      config.dist.scripts.length > 0
    ) {
      config.dist.scripts.forEach((item) => {
        scripts.push(
          '<script ' +
            item.parameter +
            ' src="' +
            config.deliveryBaseUrl +
            '/' +
            config.dist.basePath +
            '/' +
            item.file +
            '"></scr' +
            'ipt>'
        );
      });
    } else {
      config.dist.files.forEach((item) => {
        scripts.push(
          '<script src="' +
            config.deliveryBaseUrl +
            '/' +
            config.dist.basePath +
            '/' +
            item +
            '"></scr' +
            'ipt>'
        );
      });
    }
  }

  return scripts;
}

import WebcomponentRepository from '~/domain/repository/impl/WebcomponentRepository';
import SearchtagRepository from '~/domain/repository/impl/SearchtagRepository';

export interface API {
  webcomponent: WebcomponentRepository;
  searchtag: SearchtagRepository;
  baseUrl: string;
}

export default function (ctx): API {
  const api = {
    webcomponent: new WebcomponentRepository(ctx, errorHandler),
    searchtag: new SearchtagRepository(ctx, errorHandler),
    baseUrl: process.env.API_LOCATION,
  };

  return api;
}

function errorHandler(ctx, error, localHandler) {
  if (!localHandler) {
    throw new Error(error);
  } else {
    localHandler(error);
  }
}

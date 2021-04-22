import WebcomponentRepository from '~/domain/repository/impl/WebcomponentRepository';
import SearchtagRepository from '~/domain/repository/impl/SearchtagRepository';
import ContactRepository from '~/domain/repository/impl/ContactRepository';

export interface API {
  contact: ContactRepository;
  webcomponent: WebcomponentRepository;
  searchtag: SearchtagRepository;
  baseUrl: string;
}

export default function (ctx): API {
  return {
    contact: new ContactRepository(ctx, errorHandler),
    webcomponent: new WebcomponentRepository(ctx, errorHandler),
    searchtag: new SearchtagRepository(ctx, errorHandler),
    baseUrl: process.env.API_LOCATION,
  };
}

function errorHandler(ctx, error, localHandler) {
  if (!localHandler) {
    throw new Error(error);
  } else {
    localHandler(error);
  }
}

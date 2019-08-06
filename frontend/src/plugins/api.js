import WebcomponentRepository from '~/api/WebcomponentRepository';
import SearchtagRepository from '~/api/SearchtagRepository';

export default function(ctx, inject) {
  const api = {
    webcomponent: WebcomponentRepository(ctx.$axios),
    searchtag: SearchtagRepository(ctx.$axios)
  };

  inject('api', api);
}

import WebcomponentRepository from '~/api/WebcomponentRepository';

export default function(ctx, inject) {
  const api = {
    webcomponent: WebcomponentRepository(ctx.$axios)
  };

  inject('api', api);
}

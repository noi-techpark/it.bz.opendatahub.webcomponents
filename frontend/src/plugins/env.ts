import { API } from './api';

export default function (ctx, inject) {
  inject('env', ctx.env);
}

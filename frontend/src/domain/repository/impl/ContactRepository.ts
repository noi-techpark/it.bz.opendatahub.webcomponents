import ARepository from '~/domain/repository/ARepository';
import { ContactFormRequest } from '~/domain/request/ContactFormRequest';

const basePath = 'contact';

export default class ContactRepository extends ARepository {
  constructor(ctx: any, defaultErrorCallback: any) {
    super(ctx, defaultErrorCallback, basePath);
  }

  send(request: ContactFormRequest, errorHandler?: any): Promise<void> {
    return this.$post(``, request, errorHandler);
  }
}

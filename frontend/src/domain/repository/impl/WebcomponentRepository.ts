import ARepository from '~/domain/repository/ARepository';
import { PageRequest } from '~/domain/repository/PagingAndSorting';

const basePath = 'webcomponent';

export default class WebcomponentRepository extends ARepository {
  constructor(ctx: any, defaultErrorCallback: any) {
    super(ctx, defaultErrorCallback, basePath);
  }

  listAllPaged(pageRequest: PageRequest, errorHandler?: any) {
    return this.$get(
      `?${this.pageQuery(pageRequest)}&latest=true`,
      errorHandler
    );
  }

  findAllPaged(
    pageRequest: PageRequest,
    tags: string,
    term: string,
    errorHandler?: any
  ) {
    return this.$get(
      `?${this.pageQuery(pageRequest)}&tags=${tags}&searchTerm=${term}`,
      errorHandler
    );
  }

  getOneById(id: number, errorHandler?: any) {
    return this.$get(`/${id}`, errorHandler);
  }

  getConfigById(id: number, version, errorHandler?: any) {
    return this.$get(`/${id}/config/${version}`, errorHandler);
  }
}

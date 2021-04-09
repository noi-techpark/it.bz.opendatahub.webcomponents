import ARepository from '~/domain/repository/ARepository';
import { Page, PageRequest } from '~/domain/repository/PagingAndSorting';
import { WebcomponentFilter } from '~/domain/filter/WebcomponentFilter';
import { WebcomponentEntryModel } from '~/domain/model/WebcomponentEntryModel';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';

const basePath = 'webcomponent';

export default class WebcomponentRepository extends ARepository {
  constructor(ctx: any, defaultErrorCallback: any) {
    super(ctx, defaultErrorCallback, basePath);
  }

  listAllPaged(
    pageRequest: PageRequest,
    errorHandler?: any
  ): Promise<Page<WebcomponentEntryModel>> {
    return this.$get(
      `?${this.pageQuery(pageRequest)}&latest=true`,
      errorHandler
    );
  }

  findAllPaged(
    pageRequest: PageRequest,
    filter: WebcomponentFilter,
    errorHandler?: any
  ): Promise<Page<WebcomponentEntryModel>> {
    return this.$get(
      `?${this.pageQuery(pageRequest)}&${this.filterQuery(filter)}`,
      errorHandler
    );
  }

  getOneById(id: number, errorHandler?: any): Promise<WebcomponentModel> {
    return this.$get(`/${id}`, errorHandler);
  }

  getConfigById(
    id: number,
    version: string,
    errorHandler?: any
  ): Promise<WebcomponentConfigurationModel> {
    return this.$get(`/${id}/config/${version}`, errorHandler);
  }
}

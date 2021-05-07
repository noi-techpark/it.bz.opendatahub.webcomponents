import ARepository from '~/domain/repository/ARepository';

const basePath = 'searchtag';

export default class SearchtagRepository extends ARepository {
  constructor(ctx: any, defaultErrorCallback: any) {
    super(ctx, defaultErrorCallback, basePath);
  }

  listAll(errorHandler?: any): Promise<Array<string>> {
    return this.$get(``, errorHandler);
  }
}

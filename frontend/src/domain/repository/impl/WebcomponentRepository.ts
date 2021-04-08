import ARepository from '~/domain/repository/ARepository'

const basePath = 'webcomponent'

export default class WebcomponentRepository extends ARepository {
  constructor(ctx: any, defaultErrorCallback: any) {
    super(ctx, defaultErrorCallback, basePath)
  }

  listAllPaged(page, size, errorHandler?: any) {
    return this.$get(`?page=${page}&size=${size}&latest=true`, errorHandler)
  }

  findAllPaged(page, size, tags, term, errorHandler?: any) {
    return this.$get(
      `?page=${page}&size=${size}&tags=${tags}&searchTerm=${term}`,
      errorHandler
    )
  }

  getOneById(id, errorHandler?: any) {
    return this.$get(`/${id}`, errorHandler)
  }

  getConfigById(id, version, errorHandler?: any) {
    return this.$get(`/${id}/config/${version}`, errorHandler)
  }
}

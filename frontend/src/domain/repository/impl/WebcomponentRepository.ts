// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import ARepository from '~/domain/repository/ARepository';
import { Page, PageRequest } from '~/domain/repository/PagingAndSorting';
import { WebcomponentFilter } from '~/domain/filter/WebcomponentFilter';
import { WebcomponentEntryModel } from '~/domain/model/WebcomponentEntryModel';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';
import { CodeSandboxRequest } from '~/domain/request/CodeSandboxRequest';

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

  getOneById(id: string, errorHandler?: any): Promise<WebcomponentModel> {
    return this.$get(`/${id}`, errorHandler);
  }

  getConfigById(
    id: string,
    version: string,
    errorHandler?: any
  ): Promise<WebcomponentConfigurationModel> {
    return this.$get(`/${id}/config/${version}`, errorHandler);
  }

  search(
    term: string,
    errorHandler?: any
  ): Promise<WebcomponentConfigurationModel> {
    return this.$get(
      `?searchTerm=${term}&pageNumber=0&pageSize=1&latest=false`,
      errorHandler
    );
  }

  createCodeSandbox(snippet: string, errorHandler?: any): Promise<string> {
    const request: CodeSandboxRequest = {
      codeSnippet: snippet,
    };
    return this.$post('/createCodeSandbox', request, errorHandler);
  }
}

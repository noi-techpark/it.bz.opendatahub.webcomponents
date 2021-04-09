import { Action, Module, Mutation, VuexModule } from 'vuex-module-decorators';
import { WebcomponentModel } from '../domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '../domain/model/WebcomponentConfigurationModel';
import { WebcomponentEntryModel } from '../domain/model/WebcomponentEntryModel';
import { Page, PageRequest } from '../domain/repository/PagingAndSorting';
import { $api } from '~/utils/api-accessor';

@Module({
  name: 'webcomponent-list',
  stateFactory: true,
  namespaced: true,
})
export default class WebcomponentListModule extends VuexModule {
  webcomponentsList: Page<WebcomponentEntryModel> = {
    empty: true,
    first: true,
    last: true,
    number: 0,
    numberOfElements: 0,
    size: 0,
    totalElements: 0,
    totalPages: 0,
    content: [],
  };

  get getLoadedPage(): Page<WebcomponentEntryModel> {
    return this.webcomponentsList;
  }

  @Mutation
  setLoadedPage(webcomponentsList: Page<WebcomponentEntryModel>) {
    this.webcomponentsList = webcomponentsList;
  }

  @Action
  async loadPage({ pageRequest, filter }) {
    const result = await $api.webcomponent.findAllPaged(pageRequest, filter);

    this.setLoadedPage(result);
  }
}

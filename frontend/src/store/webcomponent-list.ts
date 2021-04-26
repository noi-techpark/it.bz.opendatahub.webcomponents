import { GetterTree, ActionTree, MutationTree } from 'vuex';
import { WebcomponentEntryModel } from '../domain/model/WebcomponentEntryModel';
import { Page, PageRequest } from '../domain/repository/PagingAndSorting';
import { $api } from '~/utils/api-accessor';

export const state = () => ({
  webcomponentsList: {
    empty: true,
    first: true,
    last: true,
    number: 0,
    numberOfElements: 0,
    size: 0,
    totalElements: 0,
    totalPages: 0,
    content: [],
  } as Page<WebcomponentEntryModel>,
});

export type RootState = ReturnType<typeof state>;

export const getters: GetterTree<RootState, RootState> = {
  getLoadedPage: (state) => state.webcomponentsList,
};

export const mutations: MutationTree<RootState> = {
  SET_LOADED_PAGE: (state, webcomponentsList: Page<WebcomponentEntryModel>) => {
    state.webcomponentsList = webcomponentsList;
  },
};

export const actions: ActionTree<RootState, RootState> = {
  async loadPage({ commit }, { pageRequest, filter }) {
    const result = await $api.webcomponent.findAllPaged(pageRequest, filter);

    commit('SET_LOADED_PAGE', result);
  },
};

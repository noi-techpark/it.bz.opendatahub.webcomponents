import { ActionTree, GetterTree, MutationTree } from 'vuex';
import { $api } from '~/utils/api-accessor';

export const state = () => ({
  searchtags: [] as string[],
});

export type RootState = ReturnType<typeof state>;

export const getters: GetterTree<RootState, RootState> = {
  getSearchtags(state) {
    return state.searchtags;
  },
};

export const mutations: MutationTree<RootState> = {
  SET_SEARCHTAGS: (state, searchtags: string[]) => {
    state.searchtags = searchtags;
  },
};

export const actions: ActionTree<RootState, RootState> = {
  async loadSearchtags({ commit }) {
    const result = await $api.searchtag.listAll();

    commit('SET_SEARCHTAGS', result);
  },
};

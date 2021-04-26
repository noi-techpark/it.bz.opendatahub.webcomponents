import { GetterTree, ActionTree, MutationTree } from 'vuex';
import { WebcomponentModel } from '../domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '../domain/model/WebcomponentConfigurationModel';
import { $api } from '~/utils/api-accessor';

export const state = () => ({
  currentWebcomponent: null as WebcomponentModel,
  currentConfig: null as WebcomponentConfigurationModel,
  currentVersion: null as string,
  currentSnipp: null as string,
});

export type RootState = ReturnType<typeof state>;

export const getters: GetterTree<RootState, RootState> = {
  currentWebcomponent: (state) => state.currentWebcomponent,
  currentConfig: (state) => state.currentConfig,
  currentVersion: (state) => state.currentVersion,
  currentSnipp: (state) => state.currentSnipp,
};

export const mutations: MutationTree<RootState> = {
  SET_WEBCOMPONENT: (state, webcomponent: WebcomponentModel) => {
    state.currentWebcomponent = webcomponent;
  },
  SET_CONFIG: (state, config: WebcomponentConfigurationModel) => {
    state.currentConfig = config;
  },
  SET_VERSION: (state, version: string) => {
    state.currentVersion = version;
  },
  SET_SNIPP: (state, snipp: string) => {
    state.currentSnipp = snipp;
  },
};

export const actions: ActionTree<RootState, RootState> = {
  async loadWebcomponent({ commit }, { uuid, version }) {
    commit('SET_WEBCOMPONENT', null);
    commit('SET_CONFIG', null);

    commit('SET_VERSION', version);

    const webcomponent = await $api.webcomponent.getOneById(uuid);
    commit('SET_WEBCOMPONENT', webcomponent);

    let selectedVersion = null;
    webcomponent.versions.forEach((entry) => {
      if (entry.versionTag === version) {
        selectedVersion = version;
      }
    });

    if (!selectedVersion) {
      selectedVersion = webcomponent.versions[0].versionTag;
    }

    commit('SET_VERSION', selectedVersion);

    const config = await $api.webcomponent.getConfigById(uuid, selectedVersion);
    commit('SET_CONFIG', config);
  },

  updateSnipp({ commit }, { snipp }) {
    commit('SET_SNIPP', snipp);
  },
};

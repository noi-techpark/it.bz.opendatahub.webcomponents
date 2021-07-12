import { ActionTree, GetterTree, MutationTree } from 'vuex';
import { WebcomponentModel } from '../domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '../domain/model/WebcomponentConfigurationModel';
import { $api } from '~/utils/api-accessor';
import { WebcomponentVersionModel } from '~/domain/model/WebcomponentVersionModel';
import { parseSnippetAttributes } from '~/utils/SnippetUtils';

export const state = () => ({
  webcomponent: null as WebcomponentModel,
  configuration: null as WebcomponentConfigurationModel,
  versionTag: null as string,
  snippetFromTool: '',
  snippetFromEditor: '',
});

export type RootState = ReturnType<typeof state>;

export const getters: GetterTree<RootState, RootState> = {
  currentVersionData(state): WebcomponentVersionModel {
    if (state.webcomponent) {
      return state.webcomponent.versions.find((v) => {
        return v.versionTag === state.versionTag;
      });
    }
    return null;
  },
  externalPreviewUrl(state): string {
    if (!state.webcomponent) {
      return '';
    }
    return (
      '/preview/' +
      state.webcomponent.uuid +
      '/' +
      state.versionTag +
      '?attribs=' +
      btoa(parseSnippetAttributes(state.snippetFromTool, state.configuration))
    );
  },
  attribs(state): string {
    console.log(state.snippetFromTool, state.configuration);
    if (!state.configuration) {
      return '';
    }
    return parseSnippetAttributes(
      String(state.snippetFromTool),
      state.configuration
    );
  },
};

export const mutations: MutationTree<RootState> = {
  SET_WEBCOMPONENT: (state, webcomponent: WebcomponentModel) => {
    state.webcomponent = webcomponent;
  },
  SET_CONFIGURATION: (state, config: WebcomponentConfigurationModel) => {
    state.configuration = config;
  },
  SET_VERSION_TAG: (state, version: string) => {
    state.versionTag = version;
  },
  SET_SNIPPET_FROM_TOOL: (state, snipp: string) => {
    state.snippetFromTool = snipp;
  },
  SET_SNIPPET_FROM_EDITOR: (state, snipp: string) => {
    state.snippetFromEditor = snipp;
  },
};

export const actions: ActionTree<RootState, RootState> = {
  async loadWebcomponent({ commit }, { uuid, version }) {
    commit('SET_WEBCOMPONENT', null);
    commit('SET_CONFIGURATION', null);

    commit('SET_VERSION_TAG', version);

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

    commit('SET_VERSION_TAG', selectedVersion);

    const config = await $api.webcomponent.getConfigById(uuid, selectedVersion);
    commit('SET_CONFIGURATION', config);
  },
};

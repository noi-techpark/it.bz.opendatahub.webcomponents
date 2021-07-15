import { ActionTree, GetterTree, MutationTree } from 'vuex';
import { WebcomponentModel } from '../domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '../domain/model/WebcomponentConfigurationModel';
import { $api } from '~/utils/api-accessor';
import { WebcomponentVersionModel } from '~/domain/model/WebcomponentVersionModel';

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
  transportString(state): string {
    if (!state.webcomponent || !state.configuration) {
      return '';
    }

    return toTransport(
      state.snippetFromEditor,
      state.configuration.configuration.tagName
    );
  },
  externalPreviewUrl(state): string {
    if (!state.webcomponent || !state.configuration) {
      return '';
    }
    return (
      '/preview/' +
      state.webcomponent.uuid +
      '/' +
      state.versionTag +
      '?attribs=' +
      toTransport(
        state.snippetFromEditor,
        state.configuration.configuration.tagName
      )
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
    state.snippetFromTool =
      snipp + '\n' + getDistIncludes(state.configuration).join('\n');
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
  fromTransport({ commit, state }, attribs: string) {
    commit(
      'SET_SNIPPET_FROM_EDITOR',
      fromTransport(attribs) +
        '\n' +
        getDistIncludes(state.configuration).join('\n')
    );
  },
};

function toTransport(snippet: string, tag: string): string {
  const regex = new RegExp('<' + tag + ' .*<\\/' + tag + '>');

  const result = snippet.match(regex);

  if (result) {
    return encodeURIComponent(btoa(result[0]));
  }
  return '';
}

function fromTransport(transport: string) {
  return atob(decodeURIComponent(transport));
}

function getDistIncludes(
  config: WebcomponentConfigurationModel
): Array<string> {
  const scripts = [];

  // Wait until the async loadData method has finished
  // eslint-disable-next-line no-prototype-builtins
  if (config.hasOwnProperty('dist')) {
    if (
      // eslint-disable-next-line no-prototype-builtins
      config.dist.hasOwnProperty('scripts') &&
      config.dist.scripts.length > 0
    ) {
      config.dist.scripts.forEach((item) => {
        scripts.push(
          '<script ' +
            item.parameter +
            ' src="' +
            config.deliveryBaseUrl +
            '/' +
            config.dist.basePath +
            '/' +
            item.file +
            '"></scr' +
            'ipt>'
        );
      });
    } else {
      config.dist.files.forEach((item) => {
        scripts.push(
          '<script src="' +
            config.deliveryBaseUrl +
            '/' +
            config.dist.basePath +
            '/' +
            item +
            '"></scr' +
            'ipt>'
        );
      });
    }
  }

  return scripts;
}

import { Action, Module, Mutation, VuexModule } from 'vuex-module-decorators';
import { WebcomponentModel } from '../domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '../domain/model/WebcomponentConfigurationModel';
import { WebcomponentEntryModel } from '../domain/model/WebcomponentEntryModel';
import { Page } from '../domain/repository/PagingAndSorting';
import { $api } from '~/utils/api-accessor';

@Module({
  name: 'webcomponent',
  stateFactory: true,
  namespaced: true,
})
export default class WebcomponentModule extends VuexModule {
  currentWebcomponent: WebcomponentModel = null;
  currentConfig: WebcomponentConfigurationModel = null;
  currentVersion: string = null;
  currentSnipp: string = null;

  @Mutation
  setWebcomponent(webcomponent: WebcomponentModel) {
    this.currentWebcomponent = webcomponent;
  }

  @Mutation
  setConfig(config: WebcomponentConfigurationModel) {
    this.currentConfig = config;
  }

  @Mutation
  setVersion(version: string) {
    this.currentVersion = version;
  }

  @Mutation
  setSnipp(snipp: string) {
    this.currentSnipp = snipp;
  }

  @Action
  async loadWebcomponent({ uuid, version }) {
    this.setVersion(version);

    const webcomponent = await $api.webcomponent.getOneById(uuid);
    this.setWebcomponent(webcomponent);

    let selectedVersion = null;
    webcomponent.versions.forEach((entry) => {
      if (entry.versionTag === version) {
        selectedVersion = version;
      }
    });

    if (!selectedVersion) {
      selectedVersion = webcomponent.versions[0].versionTag;
    }

    this.setVersion(selectedVersion);

    const config = await $api.webcomponent.getConfigById(uuid, selectedVersion);
    this.setConfig(config);
  }

  @Action
  updateSnipp({ snipp }) {
    this.setSnipp(snipp);
  }
}

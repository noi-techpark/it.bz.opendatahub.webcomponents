import { Store } from 'vuex';
import { getModule } from 'vuex-module-decorators';
import searchtags from '~/store/searchtags';
import webcomponent from '~/store/webcomponent';
import webcomponentList from '~/store/webcomponent-list';

// eslint-disable-next-line import/no-mutable-exports
let searchtagsStore: searchtags;
// eslint-disable-next-line import/no-mutable-exports
let webcomponentStore: webcomponent;
// eslint-disable-next-line import/no-mutable-exports
let webcomponentListStore: webcomponentList;

function initialiseStores(store: Store<any>): void {
  searchtagsStore = getModule(searchtags, store);
  webcomponentStore = getModule(webcomponent, store);
  webcomponentListStore = getModule(webcomponentList, store);
}

export {
  initialiseStores,
  searchtagsStore,
  webcomponentStore,
  webcomponentListStore,
};

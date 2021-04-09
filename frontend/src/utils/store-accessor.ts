import { Store } from 'vuex';
import { getModule } from 'vuex-module-decorators';
import searchtags from '~/store/searchtags';

// eslint-disable-next-line import/no-mutable-exports
let searchtagsStore: searchtags;

function initialiseStores(store: Store<any>): void {
  console.log('aiih', store);
  searchtagsStore = getModule(searchtags, store);
}

export { initialiseStores, searchtagsStore };

import { mount, shallowMount, createLocalVue } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import Vuex from 'vuex';
import { VuexModule } from 'vuex-module-decorators';
import VueI18n from 'vue-i18n';
import { WebcomponentModel } from '../../src/domain/model/WebcomponentModel';
import { WebcomponentEntryModel } from '../../src/domain/model/WebcomponentEntryModel';
import WcLatest from '~/components/wc-latest.vue';
import WcDetailBlock from '~/components/webcomponent/WcDetailBlock.vue';
import { Page, PageRequest } from '~/domain/repository/PagingAndSorting';

const localVue = createLocalVue();

localVue.use(BootstrapVue);
localVue.use(Vuex);
localVue.use(VueI18n);

const i18n = new VueI18n({
  numberFormats: { de: { currency: { style: 'currency', currency: 'EUR' } } },
  locale: 'de',
  fallbackLocale: 'de',
  messages: {},
});

describe('WCLatest', () => {
  let getters;
  let store;
  beforeEach(() => {
    getters = {
      loadPage: (pageRequest, filter) => {},
      getLoadedPage: () => {
        return <Page<WebcomponentEntryModel>>{
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
      },
    };

    store = new Vuex.Store({});

    store.registerModule(
      'webcomponentListStore',
      new VuexModule({ namespaced: true, getters }),
      {}
    );
  });
  test('', () => {
    const wrapper = shallowMount(WcLatest, {
      store,
      localVue,
      i18n,
      stubs: {
        NuxtLink: true,
      },
    });
    expect(true).toBe(true);
    /* wrapper.setData({ moreEnabled: false });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('text-secondary')).toContain(
      'load more components'
    ); */
  });
});

import { shallowMount, createLocalVue } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import Vuex from 'vuex';
import VueI18n from 'vue-i18n';
import { WebcomponentModel } from '../../src/domain/model/WebcomponentModel';
import WcDetailBlock from '~/components/webcomponent/WcDetailBlock.vue';

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

describe('WCDetailBloc', () => {
  let getters;
  let store;

  beforeEach(() => {
    getters = {
      currentWebcomponent: (): WebcomponentModel => {
        return <WebcomponentModel>{
          authors: [],
          copyrightHolders: [],
          datePublished: '2020-01-01',
          dateUpdated: '2020-01-01',
          description: '',
          descriptionAbstract: '',
          image: '',
          license: {},
          licenseString: '',
          repositoryUrl: '',
          searchTags: [],
          title: '',
          uuid: '',
          versions: [],
        };
      },
    };

    store = new Vuex.Store({});

    store.registerModule(
      'webcomponentStore',
      { namespaced: true, getters },
      {}
    );
  });

  test('check if header is closed', async () => {
    const wrapper = shallowMount(WcDetailBlock, {
      store,
      localVue,
      i18n,
      stubs: {
        NuxtLink: true,
      },
    });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('.bottom').isVisible()).toBe(true);
  });

  test('check if header is expanded', async () => {
    const wrapper = shallowMount(WcDetailBlock, {
      store,
      localVue,
      i18n,
      stubs: {
        NuxtLink: true,
      },
    });
    await wrapper.vm.$nextTick();
    await wrapper.find('.chevron').trigger('click');
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('.top').isVisible()).toBe(true);
  });
});

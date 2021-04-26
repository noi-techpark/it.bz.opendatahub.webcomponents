import { shallowMount, createLocalVue } from '@vue/test-utils';
import WcDetailBlock from '@/components/webcomponent/WcDetailBlock';
import BootstrapVue from 'bootstrap-vue';
import Vuex from 'vuex';

const localVue = createLocalVue();

localVue.use(BootstrapVue);
localVue.use(Vuex);

describe('WCDetailBloc', () => {
  test('', async () => {
    const wrapper = shallowMount(WcDetailBlock, localVue);
    // expect(wrapper.isVisible()).toBe(true);
    wrapper.setData({ moreEnabled: false });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('text-secondary')).toContain(
      'load more components'
    );
  });
});

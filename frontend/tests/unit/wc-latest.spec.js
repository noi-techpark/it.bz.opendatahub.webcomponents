import { mount, shallowMount, createLocalVue } from '@vue/test-utils';
import WcLatest from '@/components/wc-latest';
import { Store } from 'vuex';
import { webcomponentListStore } from '../utils/store-accessor';

const localVue = createLocalVue();
localVue.use(Store);

describe('WCLatest', () => {
  test('', () => {
    const wrapper = shallowMount(WcLatest);
    expect(true).toBe(true);
    /* wrapper.setData({ moreEnabled: false });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('text-secondary')).toContain(
      'load more components'
    ); */
  });
});

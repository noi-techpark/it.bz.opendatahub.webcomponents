import { shallowMount, createLocalVue } from '@vue/test-utils';
import DetailBottomBar from '@/components/detail-bottom-bar';
import BootstrapVue from 'bootstrap-vue';
const localVue = createLocalVue();

localVue.use(BootstrapVue);

describe('DetailBottomBar', () => {
  test('check if popover is not showing', async () => {
    const wrapper = shallowMount(DetailBottomBar, { localVue });
    await wrapper.vm.$nextTick();
    await expect(wrapper.vm.$data.showPopover).toBe(false);
  });
  test('check if popover is showing', async () => {
    const wrapper = shallowMount(DetailBottomBar, { localVue });
    await wrapper.find('#copy-code').trigger('click');
    await wrapper.vm.$nextTick();
    await expect(wrapper.vm.$data.showPopover).toBe(true);
  });
});

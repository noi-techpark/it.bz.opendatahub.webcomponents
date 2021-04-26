import { shallowMount, createLocalVue } from '@vue/test-utils';
import Header from '@/layouts/partials/header';
import BootstrapVue from 'bootstrap-vue';
import VueRouter from 'vue-router';
const localVue = createLocalVue();

localVue.use(BootstrapVue);
localVue.use(VueRouter);
const router = new VueRouter();

function localePath() {}

describe('Header', () => {
  test('check home item is visible', async () => {
    const wrapper = shallowMount(Header, {
      localVue,
      router,
      mocks: {
        localePath,
      },
      stubs: {
        NuxtLink: true,
      },
    });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('#home-item').isVisible()).toBe(true);
  });
  test('check contribute dropdown item is visible', async () => {
    const wrapper = shallowMount(Header, {
      localVue,
      router,
      mocks: {
        localePath,
      },
      stubs: {
        NuxtLink: true,
      },
    });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('#contribute-dropdown').isVisible()).toBe(true);
  });
  test('check contribute make use item is visible', async () => {
    const wrapper = shallowMount(Header, {
      localVue,
      router,
      mocks: {
        localePath,
      },
      stubs: {
        NuxtLink: true,
      },
    });
    await wrapper.vm.$nextTick();
    await expect(wrapper.find('#contribute-dropdown').isVisible()).toBe(true);
  });
});

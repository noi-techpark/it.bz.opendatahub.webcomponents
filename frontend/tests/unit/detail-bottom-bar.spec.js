// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { createLocalVue, shallowMount } from '@vue/test-utils';
import DetailBottomBar from '@/components/detail-bottom-bar';
import BootstrapVue from 'bootstrap-vue';
import VueRouter from 'vue-router';
import Vuex from 'vuex';
import VueI18n from 'vue-i18n';

const localVue = createLocalVue();
localVue.use(VueRouter);
localVue.use(Vuex);
localVue.use(VueI18n);
localVue.use(BootstrapVue);

const router = new VueRouter();

const i18n = new VueI18n({
  numberFormats: { de: { currency: { style: 'currency', currency: 'EUR' } } },
  locale: 'de',
  fallbackLocale: 'de',
  messages: {},
});

const store = new Vuex.Store({
  modules: {
    webcomponent: {
      namespaced: true,
    },
  },
});

const mocks = {
  // Always returns the input
  $t: (i) => i,
  localePath: (i) => i,
};

describe('DetailBottomBar', () => {
  test('check if popover is not showing', async () => {
    const wrapper = shallowMount(DetailBottomBar, {
      localVue,
      router,
      store,
      i18n,
      stubs: {
        NuxtLink: true,
      },
      mocks,
    });
    await wrapper.vm.$nextTick();
    await expect(wrapper.vm.$data.showPopover).toBe(false);
  });
  test('check if popover is showing', async () => {
    const wrapper = shallowMount(DetailBottomBar, {
      localVue,
      router,
      store,
      i18n,
      stubs: {
        NuxtLink: true,
      },
      mocks,
    });
    await wrapper.find('#copy-code').trigger('click');
    await wrapper.vm.$nextTick();
    await expect(wrapper.vm.$data.showPopover).toBe(true);
  });
});

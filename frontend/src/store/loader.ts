interface State {
  loaders: number;
}

export const state = (): State => ({
  loaders: 0,
});

export const mutations = {
  INCREMENT_LOADER(state: State) {
    state.loaders = state.loaders + 1;
  },
  DECREMENT_LOADER(state: State) {
    state.loaders = state.loaders - 1;
  },
  CLEAR_LOADERS(state: State) {
    state.loaders = 0;
  },
};

export const getters = {
  isLoading(state: State): boolean {
    return state.loaders !== 0;
  },
};

export const actions = {
  startLoading(context) {
    context.commit('INCREMENT_LOADER');
  },
  finishLoading(context) {
    context.commit('DECREMENT_LOADER');
  },
  clearLoading(context) {
    context.commit('CLEAR_LOADERS');
  },
};

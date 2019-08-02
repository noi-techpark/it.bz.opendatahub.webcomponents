const resource = 'webcomponent';

export default ($axios) => ({
  listAll() {
    return $axios.$get(`${resource}`);
  }
});

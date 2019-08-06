const resource = 'webcomponent';

export default ($axios) => ({
  listAllPaged(page, size) {
    return $axios.$get(`${resource}?page=${page}&size=${size}`);
  }
});

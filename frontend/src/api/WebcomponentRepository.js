const resource = 'webcomponent';

export default ($axios) => ({
  listAllPaged(page, size) {
    return $axios.$get(`${resource}?page=${page}&size=${size}`);
  },
  findAllPaged(page, size, term) {
    return $axios.$get(`${resource}?page=${page}&size=${size}&term=${term}`);
  },
  getOneById(id) {
    return $axios.$get(`${resource}/detail/${id}`);
  }
});

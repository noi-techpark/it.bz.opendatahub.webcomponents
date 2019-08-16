const resource = 'webcomponent';

export default ($axios) => ({
  listAllPaged(page, size) {
    return $axios.$get(`${resource}?page=${page}&size=${size}`);
  },
  findAllPaged(page, size, tags, term) {
    return $axios.$get(
      `${resource}?page=${page}&size=${size}&tags=${tags}&term=${term}`
    );
  },
  getOneById(id) {
    return $axios.$get(`${resource}/${id}`);
  }
});

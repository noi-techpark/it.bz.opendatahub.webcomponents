const resource = 'webcomponent';

export default ($axios) => ({
  listAllPaged(page, size) {
    return $axios.$get(`${resource}?page=${page}&size=${size}&latest=true`);
  },
  findAllPaged(page, size, tags, term) {
    return $axios.$get(
      `${resource}?page=${page}&size=${size}&tags=${tags}&searchTerm=${term}`
    );
  },
  getOneById(id) {
    return $axios.$get(`${resource}/${id}`);
  },
  getConfigById(id) {
    return $axios.$get(`${resource}/${id}/config`);
  }
});

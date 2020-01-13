const resource = 'searchtag'

export default ($axios) => ({
  listAll() {
    return $axios.$get(`${resource}`)
  }
})

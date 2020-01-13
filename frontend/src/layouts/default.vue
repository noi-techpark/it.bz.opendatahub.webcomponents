<template>
  <div>
    <Header />
    <main>
      <nuxt />
    </main>
    <Footer />
  </div>
</template>

<script>
import Header from './partials/header'
import Footer from './partials/footer'

export default {
  components: {
    Header,
    Footer
  }
}
</script>

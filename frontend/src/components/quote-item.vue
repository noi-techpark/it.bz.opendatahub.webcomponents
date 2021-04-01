<template>
  <div class="d-flex flex-column align-items-center">
    <div class="circle-icon"></div>
    <div class="text-uppercase font-weight-bold quote-title">
      {{ title }}
    </div>
    <div class="quote-text">
      {{ quote }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuoteItem',
  props: {
    title: { default: '', type: String },
    quote: { default: '', type: String }
  }
}
</script>

<style scoped lang="scss">
.quote-title {
  margin-top: 10px;
  margin-bottom: 10px;
}

.quote-text {
  max-width: 240px;
  margin-bottom: 10px;
}
</style>

<template>
  <div class="display: flex; flex-direction: column">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="6000"
      controls
      indicators
      img-width="1024"
      img-height="300"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <div class="carousel-background"></div>
      <banner-slide
        img-url="/banner/52-1024x480.jpg"
        title="Image"
        subtitle="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum."
        link-text="learn more"
        path="/contact"
      ></banner-slide>

      <banner-slide
        img-url="/banner/54-1024x480.jpg"
        title="Image"
        subtitle="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum."
        link-text="learn more"
        path="/contact"
      ></banner-slide>

      <banner-slide
        img-url="/banner/55-1024x480.jpg"
        title="Image"
        subtitle=""
        path="/contact"
      ></banner-slide>

      <banner-slide
        link-text="learn more"
        img-url="/banner/58-1024x480.jpg"
        title="Image"
        subtitle="Lorem ipsum dolor sit amet, consectetur adipiscing elit.Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum."
        path="/contact"
      ></banner-slide>
    </b-carousel>
  </div>
</template>

<script>
import BannerSlide from '~/components/banner-slide';

export default {
  name: 'Banner',
  components: { BannerSlide },
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
  },
};
</script>

<style lang="scss">
.carousel-indicators {
  top: 10px !important;
  z-index: 21;
  height: 20px;
}

#carousel-1 {
  max-height: 300px;
  overflow: hidden;
}

.carousel-caption {
  top: 40px;
}

@media (max-width: 576px) {
  .carousel-caption {
    top: 10px;
    right: 5%;
    left: 5%;

    p {
      margin-bottom: 0.2rem;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 3; /* number of lines to show */
      -webkit-box-orient: vertical;
    }

    h3 {
      font-size: 26px !important;
    }
  }
}

.carousel-control-prev,
.carousel-control-next {
  display: none;
}

.carousel-background {
  height: 300px;
  width: 100%;
  background-color: #2e3131;
  position: absolute;
  opacity: 0.6;
  z-index: 1;
}
</style>

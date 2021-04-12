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
      <!-- Text slides with image -->
      <b-carousel-slide
        caption="First slide"
        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide
        img-src="https://picsum.photos/1024/480/?image=54"
        caption="Image"
        img-alt="image"
      >
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum.
        </p>
        <nuxt-link :to="localePath('/contact')" class="text-secondary"
          >learn more</nuxt-link
        >
      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide
        img-src="https://picsum.photos/1024/480/?image=58"
      ></b-carousel-slide>

      <b-carousel-slide caption="Image" img-blank img-alt="image">
        <template #img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            src="https://picsum.photos/1024/480/?image=55"
            alt="image slot"
          />
        </template>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum.
        </p>
        <nuxt-link :to="localePath('/contact')" class="text-secondary"
          >learn more</nuxt-link
        >
      </b-carousel-slide>

      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum.
        </p>
        <nuxt-link :to="localePath('/contact')" class="text-secondary"
          >learn more</nuxt-link
        >
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
export default {
  name: 'Banner',
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

<style>
.carousel-indicators {
  top: 10px !important;
  z-index: 21;
  height: 50px;
}

#carousel-1 {
  max-height: 300px;
  overflow: hidden;
}

.carousel-caption {
  top: 50px;
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

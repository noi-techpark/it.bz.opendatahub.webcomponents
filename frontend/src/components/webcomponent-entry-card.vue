<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
      <div class="flip-card" :class="{'flipped': isFlipped}">
          <div class="flip-card-inner">
              <div class="flip-card-front" @click="toggleFlipped()">
                <div class="overlay" v-if="!isFlipped"></div>
                <div class="aspect-box">
                    <div
                        :style="
                        'background-image: url(' +
                        getLogo +
                        '); background-position: center;'
                        "
                        class="aspect-container"
                    ></div>
                </div>
                <div><nuxt-link :to="returnPath" style="color: inherit; text-decoration: inherit">Details</nuxt-link></div>
                <div class="front-content-container" v-if="!isFlipped">
                    <div class="title">{{ entry.title }}</div>
                    <div class="categories">
                        <div v-for="tag in entry.searchTags"
                            :key="tag"
                            class="category-badge">
                            {{ tag }}
                        </div>
                    </div>
                    <div class="description">
                        {{ entry.descriptionAbstract }}
                    </div>
                </div>
              </div>
              <div class="flip-card-back">
                <div class="wc-iframe-container" :id="'twrap'+entry.uuid">
                    <iframe
                        :id="'tframe'+entry.uuid"
                        class="wc-iframe"
                        style="min-height: 15rem"
                        title="iframe-preview"
                    ></iframe>
                </div>
                <div class="wc-command-bar container-fluid">
                    <div class="row">
                        <div class="col" @click="toggleFlipped()">close</div>
                        <div class="col">details</div>
                        <div class="col">share</div>
                    </div>
                </div>

                  <!-- <h1>John Doe</h1>
                  <p>Architect & Engineer</p>
                  <p>We love that guy</p> -->
              </div>
          </div>
      </div>
      <!-- <b-card
        no-body
        class="full-height shadow-sm overflow-hidden"
        style="border-radius: 6px"
      >
        <div class="aspect-box">
          <div
            :style="
              'background-image: url(' +
              getLogo +
              '); background-position: center;'
            "
            class="aspect-container"
          ></div>
        </div>
  
        <b-card-body>
          <b-card-title title-tag="div" class="h4">
            <span class="text-secondary">#</span>{{ entry.title }}
          </b-card-title>
  
          <b-card-text class="text-muted">
            {{ entry.descriptionAbstract }}
          </b-card-text>
        </b-card-body>
  
        <div slot="footer" class="row font-smaller card-short-info">
          <div class="col-6">
            <div v-if="entry.authors">
              Author:
              <span class="font-weight-bold">
                <span v-if="entry.authors.length > 0">{{
                  entry.authors[0].name
                }}</span>
                <span v-else>unknown</span>
              </span>
              <span v-if="entry.authors.length > 1"> et al.</span>
            </div>
            <div>
              Category:
              <span class="font-weight-bold"
                ><span
                  v-for="tag in entry.searchTags"
                  :key="tag"
                  class="text-capitalize implode"
                  >{{ tag }}</span
                ></span
              >
            </div>
          </div>
          <div class="col-6">
            <div>
              Version:
              <span v-if="entry.currentVersion" class="font-weight-bold">{{
                entry.currentVersion.versionTag
              }}</span>
              <span v-else class="font-weight-bold">n/a</span>
            </div>
            <div>
              License:
              <span v-if="entry.license" class="font-weight-bold">{{
                entry.license.licenseId
              }}</span>
              <span v-else class="font-weight-bold">{{
                entry.licenseString
              }}</span>
            </div>
          </div>
        </div>
      </b-card> -->
  </template>
  
  <script>
  export default {
    props: {
      entry: {
        default: null,
        type: Object,
      },
      returnTo: {
        default: null,
        type: String,
      },
    },
    data(){
        return {
            previewBaseURL: this.$api.baseUrl,
            isFlipped:false
        }
    },
    methods:{
        toggleFlipped(){
            this.isFlipped = !this.isFlipped
            if(this.isFlipped){
                // load web comp
                this.loadPreview()
            }else{
                // unload web comp
            }
        },
        async loadPreview() {
            await this.$store.dispatch('webcomponent/loadWebcomponent', {
                uuid: this.entry.uuid,
                version: 'latest'
            });

            this.updatePreview(true)
        },
        updatePreview(force = false) {
            const src = this.externalPreviewUrl;
            if (!src) {
                return; // probably not yet loaded
            }
            
            if (!force && !this.autoUpdate) {
                return;
            }
            
            const oldElement = document.getElementById('tframe'+this.entry.uuid);
            
            if (!oldElement) {
                return;
            }
            
            oldElement.parentNode.removeChild(oldElement);
            
            const newElement = document.createElement('iframe');
            newElement.setAttribute('id', 'tframe'+this.entry.uuid);
            newElement.setAttribute('class', 'full-height full-width');
            newElement.setAttribute('style', 'min-height: 15rem;');
            newElement.setAttribute('frameborder', '0');

            document.getElementById('twrap'+this.entry.uuid).appendChild(newElement);
            
            newElement.src = src;
            newElement.contentDocument.close();
        },
    },
    computed: {

        config() {
            return this.$store.state.webcomponent.configuration;
        },  
        externalPreviewUrl() {
            if (!this.entry || !this.config) {
                return '';
            }
            return (
                this.previewBaseURL +
                '/preview/' +
                this.entry.uuid +
                '/latest?attribs=' +
                this.$store.getters['webcomponent/transportString']
            );
        },
        getLogo() {
            if (this.entry.image) {
            return (
                this.$api.baseUrl + '/webcomponent/' + this.entry.uuid + '/logo'
                // this.$api.baseUrl + '/webcomponent/' + this.entry.uuid + '/logo/thumb'
            );
            }
    
            return '/component_placeholder.png';
        },
        returnPath() {
            if (!this.entry) {
            return this.localePath('/');
            }
    
            if (this.returnTo === null || this.returnTo === '/') {
            return this.localePath({
                name: 'webcomponent-id',
                params: {
                id: this.entry.shortName ? this.entry.shortName : this.entry.uuid,
                },
            });
            }
    
            return this.localePath({
            name: 'webcomponent-id',
            params: {
                id: this.entry.shortName ? this.entry.shortName : this.entry.uuid,
            },
            query: { from: this.returnTo },
            });
        },
    },
  };
  </script>
  
  <style lang="scss">
  /* The flip card container - set the width and height to whatever you want. We have added the border property to demonstrate that the flip itself goes out of the box on hover (remove perspective if you don't want the 3D effect */
  .flip-card {
    background-color: transparent;
    // width: 300px;
    height: 25rem;
    perspective: 1000px; /* Remove this if you don't want the 3D effect */
  }
  
  /* This container is needed to position the front and back side */
  .flip-card-inner {
    position: relative;
    width: 100%;
    height: 100%;
    text-align: center;
    transition: transform 0.8s;
    transform-style: preserve-3d;
  }

  /* Do an horizontal flip when you move the mouse over the flip box container */
    .flip-card.flipped{
        .flip-card-inner {
            transform: rotateY(180deg);
        }
    }

/* Do an horizontal flip when you move the mouse over the flip box container */
//   .flip-card:hover .flip-card-inner {
//     transform: rotateY(180deg);
//   }
  
  /* Position the front and back side */
  .flip-card-front, .flip-card-back {
    position: absolute;
    width: 100%;
    height: 100%;
    -webkit-backface-visibility: hidden; /* Safari */
    backface-visibility: hidden;
  }
  
  /* Style the front side (fallback if image is missing) */
  .flip-card-front {
    .overlay{
        position: absolute;
        z-index: 2;
        width: 100%;
        height: 100%;
        background: linear-gradient(180deg, rgba(0,0,0,0) 30%, rgba(46,49,49,1) 95%);
        overflow: hidden;
        border-radius: 0.4rem;
    }
    .aspect-box {
        height: 100%;
        top:0;
        position: relative;
        overflow: hidden;
        border-radius: 0.4rem;
        .aspect-container {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-size: cover;
        }
    }
    .front-content-container{
        color: white;
        width: 100%;
        z-index: 3;
        height: 8rem;
        position: absolute;
        bottom: 0;
        text-align: left;
        padding: 1rem;
        
        .title{
            width: 100%;
            height: auto;
            display: block;
            font-weight: bold;
            font-size: 1.2rem;
        }
        .categories{
            width: 100%;
            display: block;
            margin-top: -0.3rem;
            margin-bottom: 0.3rem;

            .category-badge{
                display: inline-block;
                background-color: white;
                border-radius: 0.2rem;
                font-size: 0.6rem;
                width: fit-content;
                color: rgb(46, 49, 49);
                font-weight: 500;
                padding: 0 0.3rem;
                margin: 0 0.1rem;
            }
        }
        .description{
            width: 100%;
            height: auto;
            display: block;
            font-weight: normal;
            font-size: 0.8rem;
            margin-top:0.5rem;
        }
    }
  }
  
  /* Style the back side */
  .flip-card-back {
    background-color: white;
    color: rgb(46, 49, 49);
    transform: rotateY(180deg);
    overflow: hidden;
    border-radius: 0.4rem;

    .wc-command-bar{
        background-color: rgb(46, 49, 49,1);
        color: white;
        width: 100%;
        height: 10%;
    }
    .wc-iframe-container{
        width: 100%;
        height: 90%;

        .wc-iframe{
            width: 100%;
            height: 100%;
        }
    }
    

  }
  
  
  
  </style>
<template>
  <div class="mb-5">
    <div class="bg-light">
      <div class="container p-5">
        <div class="row">
          <div class="col-1">
            <b-button variant="primary" to="/">BK</b-button>
          </div>
          <div class="col-8">
            <h1>#TolleKomponente</h1>

            <div class="d-flex">
              <div class="full-width mr-2">
                <div>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec
                  et egestas magna. Sed et nibh eget felis venenatis rutrum sed
                  sit amet enim. Nam imperdiet feugiat ex, id auctor sem rutrum
                  eget.
                </div>
                <div class="text-muted mt-4">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec
                  et egestas magna. Sed et nibh eget felis venenatis rutrum sed
                  sit amet enim. Nam imperdiet feugiat ex, id auctor sem rutrum
                  eget. Proin mollis suscipit quam, ac finibus metus rhoncus
                  non. Proin vestibulum nibh turpis, vel commodo enim feugiat
                  eu. Quisque fringilla fermentum elementum. Nam ac quam
                  interdum, euismod magna sit amet, tempus libero. Mauris in
                  consequat justo, fermentum venenatis sem. Nam scelerisque
                  congue elit mollis sodales. Donec gravida, est ut rutrum
                  ultrices, ex erat lobortis mauris, sed faucibus tortor metus
                  nec nisi. Integer interdum finibus orci, a tincidunt ligula.
                  Praesent tempor nunc at dui condimentum, vitae auctor lorem
                  feugiat. Cras velit ex, convallis eget massa non, placerat
                  luctus libero. In pellentesque, metus in malesuada tristique,
                  diam dui tristique lorem, ac pretium nisi orci a velit.
                </div>
              </div>
            </div>
          </div>
          <div class="col-3 border-left border-dark">
            <div class="row">
              <div class="col-6">Author:</div>
              <div class="col-6">Sepp Unterwurzacher</div>
            </div>
            <div class="row">
              <div class="col-6">Category:</div>
              <div class="col-6"></div>
            </div>
            <div class="row">
              <div class="col-6">Version:</div>
              <div class="col-6"></div>
            </div>
            <div class="row">
              <div class="col-6">License:</div>
              <div class="col-6"></div>
            </div>
            <div class="row">
              <div class="col-6">Published:</div>
              <div class="col-6"></div>
            </div>
            <div class="row">
              <div class="col-6">Last Update:</div>
              <div class="col-6"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container container-extended p-4">
      <div class="row">
        <div class="col-8">
          <div class="text-uppercase font-weight-bold mb-2">preview</div>
          <b-card id="widget-preview" class="full-height">
            <b-card-text class="text-center">
              <img
                src="https://static.anychart.com/images/gallery/v8/line-charts-line-chart.png"
              />
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              [] auto update [] update preview
            </div>
          </b-card>
        </div>

        <div class="col-4">
          <div class="text-uppercase font-weight-bold mb-2">configuration</div>
          <b-card id="widget-config" class="full-height">
            <b-card-text>
              Config...
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              [] apply
            </div>
          </b-card>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
          <b-card id="widget-codesnippet">
            <b-card-text>
              Code
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              [] COPY
            </div>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          autor: 'x',
          category: 'x',
          version: 'x',
          license: 'x',
          published: 'x',
          last_update: 'x'
        }
      ]
    };
  }
};
</script>

<style lang="scss">
#widget-codesnippet {
  .card-body {
    background-color: #f1f1f1;
  }

  .card-footer {
    background-color: inherit;
  }
}

#widget-config {
  .card-footer {
    background-color: inherit;
  }
}

#widget-preview {
  .card-footer {
    background-color: inherit;
  }
}
</style>

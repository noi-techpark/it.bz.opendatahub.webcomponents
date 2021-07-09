<template functional>
  <svg viewBox="0 0 36 36" :class="'circular-chart ' + props.color">
    <path
      class="circle-bg"
      d="M18 2.0845
          a 15.9155 15.9155 0 0 1 0 31.831
          a 15.9155 15.9155 0 0 1 0 -31.831"
    />
    <path
      class="circle"
      :stroke-dasharray="$options.strokeDasharray(props)"
      d="M18 2.0845
          a 15.9155 15.9155 0 0 1 0 31.831
          a 15.9155 15.9155 0 0 1 0 -31.831"
    />
    <foreignObject x="0" y="0" width="36" height="36" class="percentage">
      <slot></slot>
    </foreignObject>
  </svg>
</template>

<script lang="ts">
export default {
  name: 'CircularChart',
  functional: true,
  props: {
    circleValue: {
      type: Number,
      default: 0,
    },
    color: {
      type: String,
      default: 'green',
    },
  },
  strokeDasharray(props) {
    return props.circleValue + ', 100';
  },
};
</script>

<style scoped>
.circular-chart {
  display: block;
  margin-bottom: 5px;
  max-width: 50px;
  max-height: 250px;
}

.circle-bg {
  fill: none;
  stroke: white;
  stroke-width: 3.2;
}

.circle {
  fill: none;
  stroke-width: 2.8;
  animation: progress 1s ease-out forwards;
  stroke: #50742f;
}

.red .circle {
  stroke: crimson;
}

@keyframes progress {
  0% {
    stroke-dasharray: 0 100;
  }
}

.percentage {
  fill: #666;
  font-weight: bold;
  font-size: 11px;
  text-anchor: middle;
}
</style>

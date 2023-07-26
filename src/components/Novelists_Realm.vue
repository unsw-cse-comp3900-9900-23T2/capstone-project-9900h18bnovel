<script setup>
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
import { Linter } from 'eslint';
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `
</script>
<script>
import Global_Footer from './Global_Footer.vue';
// import axios from "axios";
export default {
  data(){
    return{

    }
  },
  mounted() {

  },
  components: {
    Global_Footer,
  },
  methods: {},
}
</script>
<template>
  <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
       element-loading-background="rgba(255, 255, 255, 255)"
       style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>


  <Global_Footer />
</template>

<style scoped>

</style>
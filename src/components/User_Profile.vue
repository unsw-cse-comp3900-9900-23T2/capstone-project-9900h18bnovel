<script setup>

</script>
<script>
  import Header from './Global_Header.vue';
  import Nav from './Global_Nav.vue';
  import Footer from './Global_Footer.vue';
  import Login from './Auth_Page.vue';
  export default {
    data(){},
    components: {
      Header,
      Nav,
      Footer
    },

  }
</script>
<template>
  <div>
    <Header />
    <Nav />
  </div>
  <Footer />
</template>

<style scoped>

</style>
<script>
import Header from './Global_Header.vue'
import Nav from './Global_Nav.vue'
export default {
  data() {
    return {
      header_Nav_Container: 'Header_Nav_Container'
    }
  },
  components: {
    Header,
    Nav
  }
}
</script>
<template>
  <div :class="header_Nav_Container">
    <Header />
    <Nav />
  </div>
</template>

<style >
body{
  margin: 0;
}
/* .header_Nav_Container{

} */
</style>

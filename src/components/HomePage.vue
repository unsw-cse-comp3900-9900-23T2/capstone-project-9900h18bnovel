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
  <div class="homeBody">
    <div class="collected_Novel">
      Collected novels section
    </div>
    <div class="weekly_book">
      Weekly book section
    </div>
  </div>
</template>

<style >
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
}

.homeBody{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 15px;
}

.collected_Novel {
  background-color: gainsboro;
  width: 80%;
  height: 150px; /* Need change */
  margin-bottom: 15px;
}

.weekly_book{
  background-color: antiquewhite;
  width: 80%;
  height: 200px;
}
/* .header_Nav_Container{

} */
</style>

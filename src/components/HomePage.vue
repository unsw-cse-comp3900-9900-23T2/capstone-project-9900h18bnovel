<script>
import Header from './Global_Header.vue';
import Nav from './Global_Nav.vue';
import Footer from './Global_Footer.vue';

export default {
  data() {
    return {
      header_Nav_Container: 'Header_Nav_Container',
      collect_flag: true
    }
  },
  components: {
    Header,
    Nav,
    Footer
  },
  methods: {
    testFlag(){
      this.collect_flag = !this.collect_flag;
    }
  }
}
</script>
<template>
  <div :class="header_Nav_Container">
    <Header />
    <Nav />
  </div>
  <div class="homeBody">
    <!-- <button @click="testFlag">test collect</button> -->
    <div v-if="collect_flag" class="collected_Novel">
      Collected novels section
    </div>
    <div v-else>
    </div>
    <div class="weekly_books">
      Weekly book section
    </div>
    <h2>Recommondation</h2>
    <div class="recomm_books_container">
      <div class="hottest_books">
        Hottest Book Section
      </div>
      <div class="best_books">
        Best Books Section
      </div>
    </div>
    <h2>Ranking of Books</h2>
    <div class="rank_books_container">
      <div class="click_rank">
        click rank
      </div>
      <div class="newest_rank">
        newest rank
      </div>
      <div class="update_rank">
        update rank
      </div>
    </div>
  </div>
  <Footer />
</template>

<style >
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
}

.homeBody{
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.rank_books_container{
  margin-top: -10px;
  width: 80%;
  height: 40%;
  display: flex;
}

.click_rank{
  background-color: aliceblue;
  width: 33.33%;
}
.newest_rank{
  background-color: antiquewhite;
  width: 33.33%;
}
.update_rank{
  background-color:lavender;
  width: 33.33%;
}

.recomm_books_container {
  margin-top: -10px;
  margin-bottom: -5px;
  width: 80%;
  display: flex;
  height: 40%;
}
.hottest_books {
  width: 50%;
  background-color: beige;
}
.best_books{
  width: 50%;
  background-color:blanchedalmond;
}
.collected_Novel {
  background-color: gainsboro;
  width: 80%;
  height: 20%; /* Need change */
  margin-bottom: 15px;
  margin-top: 15px;
}

.weekly_books{
  background-color: antiquewhite;
  width: 80%;
  height: 25%;
  margin-bottom: -5px;
}
/* .header_Nav_Container{

} */
</style>

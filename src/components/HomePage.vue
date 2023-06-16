<script setup>
import {
  CaretTop
} from '@element-plus/icons-vue'
</script >
<script>
import Header from './Global_Header.vue';
import Nav from './Global_Nav.vue';
import Footer from './Global_Footer.vue';
export default {
  data() {
    return {
      header_Nav_Container: 'Header_Nav_Container',
      collect_flag: false,
      weekly_books: ['This', 'is', 'test', 'for', 'Weekly', 'books'],
      hottest_books: ['This', 'is', 'a', 'test', 'for', 'Hottest', 'books'],
      newest_books: ['This', 'is', 'a', 'test', 'for', 'Best', 'books'],
    }
  },
  components: {
    Header,
    Nav,
    Footer
  },
  methods: {
    testFlag() {
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
    <div>
    </div>
    <div class="weekly_collect_books_container">
      <div class="weekly_books">
        <h3>Weekly Books</h3>
        <el-carousel :interval="4000" height="300px">
          <el-carousel-item v-for="item in weekly_books" :key="item">
            <h3 text="2xl" justify="center">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="collected_novel_container">
        <h3>Collected Books</h3>
        <div v-if="collect_flag" class="collected_Novel">
          U have collect books
          <button @click="testFlag">test collect</button>
        </div>
        <div v-else class="collected_Novel">
          U don't have collect books
          <button @click="testFlag">test collect</button>
        </div>
      </div>
    </div>
    <h2>Recommondation</h2>
    <div class="recomm_books_container">
      <div class="hottest_books">
        <h3>Hottest Books</h3>
        <el-carousel height="400px" direction="vertical" type="card" :autoplay="true">
          <el-carousel-item v-for="item in hottest_books" :key="item">
            <h3 text="2xl" justify="center">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="best_books">
        <h3>Best Books</h3>
        <el-carousel height="400px" direction="vertical" type="card" :autoplay="true" indicator-position="left">
          <el-carousel-item v-for="item in newest_books" :key="item">
            <h3 text="2xl" justify="center">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
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
  <!-- Go to top floating buttom -->
  <el-backtop :bottom="100">
    <div class="goTopButton">
      <el-icon>
        <CaretTop />
      </el-icon>
    </div>
  </el-backtop>
</template>

<style >
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
}

.homeBody {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.weekly_collect_books_container {
  margin-top: 15px;
  width: 60%;
  display: flex;
  justify-content: space-between;
}

.rank_books_container {
  margin-top: -10px;
  margin-bottom: 15px;
  width: 60%;
  display: flex;
  height: 400px;
}

.click_rank {
  background-color: aliceblue;
  width: 33.33%;
}

.newest_rank {
  background-color: antiquewhite;
  width: 33.33%;
}

.update_rank {
  background-color: lavender;
  width: 33.33%;
}

.recomm_books_container {
  margin-top: -10px;
  margin-bottom: -5px;
  width: 60%;
  display: flex;
}

.hottest_books {
  text-align: center;
  width: 50%;
  margin-top: -20px;
}

.best_books {
  width: 50%;
  text-align: center;
  margin-top: -20px;
}

.collected_novel_container{
  width: 50%;
  margin-left: 5px;
  display: flex;
  flex-direction: column;
}

.collected_Novel{
  border: 1px solid;
  height: 100%;
}

.weekly_books {
  width: 50%;
  margin-right: 5px;
}

.goTopButton {
  height: 100%;
  width: 100%;
  background-color: var(--el-bg-color-overlay);
  box-shadow: var(--el-box-shadow-lighter);
  align-items: center;
  display: flex;
  justify-content: center;
  line-height: 40px;
  color: #1989fa;
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}
</style>

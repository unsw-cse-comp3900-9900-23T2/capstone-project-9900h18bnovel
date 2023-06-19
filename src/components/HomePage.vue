<script setup>
import {
  CaretTop,
  UserFilled
} from '@element-plus/icons-vue';
import { ref } from 'vue'
const value = ref(4.8)
</script >
<script>
import Header from './Global_Header.vue';
import Nav from './Global_Nav.vue';
import Footer from './Global_Footer.vue';
export default {
  data() {
    const weekly_books_info = [
      { title: 'Memory', image: 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/contemporary-fiction-night-time-book-cover-design-template-1be47835c3058eb42211574e0c4ed8bf_screen.jpg?ts=1637012564', author: 'Ajax Mother', des: "I wandered lonely as a cloud that floats on high o'er vales and hills when all at once I saw a crowd, a host, of golden daffodils." },
      { title: 'Our Last Summer', image: 'https://marketplace.canva.com/EAFFEs6P168/1/0/1003w/canva-orange-green-watercolor-soft-cute-cartoon-love-romance-book-cover-5Zk2VM0-EAc.jpg', author: "Bob Fucker", des: 'Through this wide and troubled world I roam, alone and without a home, seeking solace in the depths of my soul' },
      { title: 'The Past is Rising', image: 'https://i.pinimg.com/474x/a7/91/62/a7916230aedcdce47a4dfbff5247f0ce.jpg', author: "Charles Joker", des: 'Amidst the chaos and confusion, she remained calm and composed, a beacon of hope in the midst of darkness' }
    ]
    const hottest_books_info = [
      { title: 'The Black Universe', image: 'https://marketplace.canva.com/EAFEbtlNK2Q/1/0/1003w/canva-double-exposure-artistic-background-novel-book-cover-sTAyOpO_rTI.jpg', author: 'Jesus Criss', des: "I wandered lonely as a cloud that floats on high o'er vales and hills when all at once I saw a crowd, a host, of golden daffodils." },
      { title: 'Shadow of Evil', image: 'https://i.etsystatic.com/19280387/r/il/63ad6f/2350960458/il_fullxfull.2350960458_sj9e.jpg', author: "Tommas Tu", des: 'Through this wide and troubled world I roam, alone and without a home, seeking solace in the depths of my soul' },
      { title: 'The Hypocrite World', image: 'https://marketplace.canva.com/EAD7WuSVrt0/1/0/1003w/canva-colorful-illustration-young-adult-book-cover-LVthABb24ik.jpg', author: "Siant Diygo", des: 'Amidst the chaos and confusion, she remained calm and composed, a beacon of hope in the midst of darkness' }
    ]
    const comments_black_universe=[
      { comment: 'This novel is a captivating masterpiece that kept me hooked from beginning to end. The characters are so well-developed, and the plot twists had me on the edge of my seat!', commenter:'Reviewer1'},
      { comment: "I couldn't put this book down! The author's writing style is incredibly engaging, and the story had me completely immersed in its world. Highly recommended for any avid reader!", commenter: 'Reviewer2'},
      { comment: "An absolute page-turner! The author has a gift for creating vivid imagery and evoking strong emotions. I laughed, I cried, and I couldn't get enough of this compelling story", commenter: 'Reviewer3'},
      { comment: "This novel is a true gem. The narrative is beautifully crafted, and the themes explored are thought-provoking. It's a literary work that will stay with me long after I've turned the last page", commenter: 'Reviewer4'},
      { comment: "I'm completely blown away by the depth and complexity of the characters in this novel. The author has created a rich and believable world that I didn't want to leave. A must-read for anyone seeking an immersive literary experience", commenter: 'Reviewer5'},
      { comment: "If you're looking for a novel that will take you on an unforgettable journey, look no further. The writing is exquisite, and the plot twists are cleverly executed. I was captivated by every word and eagerly await the author's next masterpiece", commenter: 'Reviewer6'},

    ]
    const newest_books_images = ['https://miblart.com/wp-content/uploads/2020/01/crime-and-mystery-cover-scaled-1.jpeg', 'https://s26162.pcdn.co/wp-content/uploads/2020/01/Sin-Eater-by-Megan-Campisi.jpg', 'https://s26162.pcdn.co/wp-content/uploads/2019/12/46301955-668x1024.jpg']
    return {
      collect_flag: false,
      hottest_books_info: hottest_books_info,
      comments_black_universe:comments_black_universe,
      weekly_books_info: weekly_books_info,
      newest_books_images: newest_books_images,
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
    },
    getBackgroundStyle(imageUrl) {
      return {
        'background-image': `url(${imageUrl})`
      };
    }
  },
  computed: {
    filteredComments() {
      return this.comments_black_universe.slice(0, 4);
    },
  },
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
          <el-carousel-item v-for="item in weekly_books_info" :key="item">
            <div class="carousel_weekly_background" :style="getBackgroundStyle(item.image)"></div>
            <el-row>
              <el-column class="carousel_weekly_image_container">
                <img :src="item.image" class="carousel_weekly_image">
              </el-column>
              <el-column class="carousel_weekly_text_container">
                <div class="carousel_weekly_text">
                  <span class="carousel_weekly_text_title">{{ item.title }}</span>
                  <span class="carousel_weekly_text_author">{{ item.author }}</span>
                  <span class="carousel_weekly_text_descr">{{ item.des }}</span>
                </div>
              </el-column>
            </el-row>
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
        <el-carousel height="600px" style="width: 600px;" direction="vertical" type="card" :autoplay="true">
          <el-carousel-item style="border-radius: 15px;" v-for="item in hottest_books_info" :key="item">
            <el-row class="carousel_hot_container">
              <div class="carousel_hot_left_container">
                <span style="font-size: 14pt;">{{ item.title }}</span>
                <span style="font-size: 12pt;">{{ item.author }}</span>
                <span style="font-size: 10pt; margin-top: 10px; margin-right: 10px;">{{ item.des }}</span>
                <span style="bottom: 10px;position: absolute;">
                  20 <el-icon>
                    <UserFilled />
                  </el-icon> Liked
                </span>
              </div>
              <div class="carousel_hot_image_container">
                <img :src="item.image" class="carousel_hot_image">
              </div>
              <div class="carousel_hot_right_container">
                <div class="carousel_hot_right_comments_container">
                  <el-text class="carousel_hot_right_comments" v-for="com in filteredComments" :key="com">{{ com.comment }}</el-text>
                </div>
                <div class="carousel_hot_right_rates_container">
                  <el-rate v-model="value" disabled show-score text-color="#ff9900" size="small"
                  score-template="{value} points" />
                </div>
              </div>     
            </el-row>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="best_books">
        <h3>Best Books</h3>
        <el-carousel height="600px" style="width: 600px;" direction="vertical" type="card" :autoplay="true">
          <el-carousel-item style="border-radius: 15px;" v-for="item in newest_books_images" :key="item">
            <el-row class="carousel_best_container">
              <div class="carousel_best_left_container">
                <span style="font-size: 20pt;">Title</span>
                <span style="font-size: 18pt;">Author</span>
                <span style="font-size: 14pt;">Des</span>
                <span style="font-size: 110pt;">&nbsp;</span>
                <span>20 <el-icon>
                    <UserFilled />
                  </el-icon> Liked</span>
              </div>
              <div class="carousel_best_image_container">
                <img :src="item" class="carousel_best_image">
              </div>
              <div class="carousel_best_right_container">
                <span class="carousel_best_right_comments">comments 1</span>
                <span class="carousel_best_right_comments">comments 2</span>
                <span class="carousel_best_right_comments">comments 3</span>
                <span class="carousel_best_right_comments">comments 4</span>
                <el-rate v-model="value" disabled show-score text-color="#ff9900" size="small"
                  score-template="{value} points" />
              </div>
            </el-row>
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
  width: 1920px;
  margin: 0 auto;
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
  justify-content: space-between;
}

.hottest_books {
  width: 45%;
  margin-top: -20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.best_books {
  width: 45%;
  margin-top: -20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.collected_novel_container {
  width: 50%;
  margin-left: 5px;
  display: flex;
  flex-direction: column;
}

.collected_Novel {
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
  opacity: 1;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(n) {
  background-color: rgb(253, 244, 244);
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

.carousel_weekly_background {
  background-size: cover;
  filter: blur(18px);
  width: 100%;
  height: 300px;
  position: absolute;
}

.carousel_weekly_image_container {
  width: 40%;
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel_weekly_image {
  height: 250px;
  object-fit: contain;
  box-shadow: 6px 4px 6px white;
  border-radius: 8px;
}

.carousel_weekly_text_container {
  width: 60%;
  height: 300px;
}

.carousel_weekly_text {
  display: flex;
  flex-direction: column;
  margin-top: 40px;
  color: white;
}

.carousel_weekly_text_title {
  font-size: 24pt;
  margin-bottom: 5px;
}

.carousel_weekly_text_author {
  font-size: 18pt;
  margin-bottom: 20px;
}

.carousel_weekly_text_descr {
  font-size: 12pt;
  padding-right: 50px;
}

.carousel_hot_container {
  height: 300px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 2px 4px rgba(0, 0, 0, 0.06);
  transform: translateY(0);
  transition: transform 0.3s ease;
}

.carousel_hot_container:hover {
  transform: translateY(-4px);
}

.carousel_hot_left_container {
  width: 30%;
  padding-left: 20px;
  padding-top: 20px;
  display: flex;
  flex-direction: column;
}

.carousel_hot_image_container {
  width: 30%;
}

.carousel_hot_image {
  height: 300px;
  object-fit: contain;
}

.carousel_hot_right_container {
  width: 30%;
  height: 100%;
  padding-left: 25px;
  padding-top: 10px;
}

.carousel_hot_right_comments_container{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 85%;
}
.carousel_hot_right_comments {
  display: block;
  height: 23%;
  width: 100%;
  border: 1px solid #1989fa;
  border-radius: 3px;
  overflow: auto;
}

.carousel_hot_right_rates_container{
  bottom: 7px;
  position: absolute;
}
.carousel_best_container {
  height: 300px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 2px 4px rgba(0, 0, 0, 0.06);
  transform: translateY(0);
  transition: transform 0.3s ease;
}

.carousel_best_container:hover {
  transform: translateY(-4px);
}

.carousel_best_left_container {
  width: 30%;
  padding-left: 20px;
  padding-top: 20px;
  display: flex;
  flex-direction: column;
}

.carousel_best_image_container {
  width: 30%;
}

.carousel_best_image {
  height: 300px;
  object-fit: contain;
}

.carousel_best_right_container {
  width: 30%;
  height: 100%;
  padding-left: 25px;
  padding-top: 10px;
  display: flex;
  flex-direction: column;
}

.carousel_best_right_comments {
  height: 20%;
  border: 1px solid #1989fa;
  border-radius: 3px;
  margin-bottom: 3px;
}
</style>

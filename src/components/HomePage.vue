<script setup>
import {
  CaretTop,
  UserFilled,
  User
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
const value = ref(4.8)
</script>
<script>
import Header from './Global_Header.vue';
import Nav from './Global_Nav.vue';
import Footer from './Global_Footer.vue';
import Login from './Auth_Page.vue';
export default {
  emits: ['showLogin', 'closeLoginBox', 'logout'],
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
    const comments_black_universe = [
      { comment: 'This novel is a captivating masterpiece that kept me hooked from beginning to end. The characters are so well-developed, and the plot twists had me on the edge of my seat!', commenter: 'Reviewer1' },
      { comment: "I couldn't put this book down! The author's writing style is incredibly engaging, and the story had me completely immersed in its world. Highly recommended for any avid reader!", commenter: 'Reviewer2' },
      { comment: "An absolute page-turner! The author has a gift for creating vivid imagery and evoking strong emotions. I laughed, I cried, and I couldn't get enough of this compelling story", commenter: 'Reviewer3' },
      { comment: "This novel is a true gem. The narrative is beautifully crafted, and the themes explored are thought-provoking. It's a literary work that will stay with me long after I've turned the last page", commenter: 'Reviewer4' },
      { comment: "I'm completely blown away by the depth and complexity of the characters in this novel. The author has created a rich and believable world that I didn't want to leave. A must-read for anyone seeking an immersive literary experience", commenter: 'Reviewer5' },
      { comment: "If you're looking for a novel that will take you on an unforgettable journey, look no further. The writing is exquisite, and the plot twists are cleverly executed. I was captivated by every word and eagerly await the author's next masterpiece", commenter: 'Reviewer6' },

    ]
    const newest_books_images = ['https://miblart.com/wp-content/uploads/2020/01/crime-and-mystery-cover-scaled-1.jpeg', 'https://s26162.pcdn.co/wp-content/uploads/2020/01/Sin-Eater-by-Megan-Campisi.jpg', 'https://s26162.pcdn.co/wp-content/uploads/2019/12/46301955-668x1024.jpg']

    const click_rank_info = [
      { value: 5, title: "Crack The Code", image: "https://www.adobe.com/express/create/cover/media_181e3d2c78f153ae7bf0e19a2faeb9a76e234da30.jpeg?width=400&format=jpeg&optimize=medium", author: "Patrick C.Harless", des: "In a small coastal town, a mysterious stranger arrives, bringing with him an air of intrigue and secrets. As the townspeople become entranced by his enigmatic presence, they soon discover that he holds the key to their deepest desires and darkest fears." },
      { value: 4.7, title: "Pattingson's Curse", image: "https://www.designbookcover.pt/uploads/media/photos/cache/pattinsons_curse_72_media_huge_thumbnail.jpg", author: "Graeme Arnold", des: "Set in a dystopian future, where technology rules every aspect of life, a group of rebels embarks on a perilous journey to dismantle the oppressive regime. Along the way, they uncover shocking truths about the world they thought they knew, testing their resolve and challenging their beliefs." },
      { value: 4.5, title: "Only Words", image: "https://www.designbookcover.pt/uploads/media/photos/cache/b1_only_words_72_media_huge_thumbnail.jpg", author: "Shane Ashby Trilogy", des: "In the heart of a bustling metropolis, two star-crossed lovers find themselves caught in a web of forbidden love. As they navigate the complexities of their social divide and societal expectations, they must confront the consequences of their actions and make choices that could change their lives forever." },
      { value: 4.0, title: "Invisible Man", image: "https://news.harvard.edu/wp-content/uploads/2022/07/20220727_books_invisibleman_2500-655x1024.jpg", author: "Ralph Ellison", des: "In a realm of magic and mythical creatures, a young hero emerges from humble beginnings. With an ancient prophecy guiding their path, they embark on a quest to save their kingdom from an impending darkness. Along the way, they face formidable challenges, forge unlikely alliances, and discover the true power within themselves" },
      { value: 3.6, title: "When Blood Meets Earth", image: "https://www.nairaland.com/attachments/16975928_octoberillustration6768x1152_jpeg7ab7fc0d22a2a942cf8632fae8e2295b", author: "E.A.NOBLE", des: "Against the backdrop of war-torn lands, a group of unlikely companions bands together to reclaim their homeland from the clutches of evil. As they navigate treacherous landscapes and face unimaginable dangers, they learn the true meaning of sacrifice, loyalty, and the enduring strength of friendship." }
    ]
    return {
      weekly_books_info: weekly_books_info,
      hottest_books_info: hottest_books_info,
      best_books_info: '',
      comments_black_universe: comments_black_universe,
      newest_books_images: newest_books_images,
      login_button: 'Sign in',
      click_rank_info: click_rank_info,
      newest_rank_info: '',
      update_rank_info: '',
      isLoginVisible: false,
      VerImage: '',
      sessionId: '',
    }
  },
  components: {
    Header,
    Nav,
    Footer
  },
  mounted() {
    this.getHomeBooks();
    if (localStorage.getItem("token")) {
      this.$store.dispatch('login', localStorage.getItem("token"));
    }
  },
  methods: {
    testFlag() {
      this.collect_flag = !this.collect_flag;
    },
    getBackgroundStyle(imageUrl) {
      return {
        'background-image': `url(${imageUrl})`
      };
    },
    async showLogin() {
      this.isLoginVisible = true;
      try {
        const response = await fetch("http://localhost:8888/api/front/user/img_verify_code", {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.verImage = "data:image/png;base64," + data.data.img;
          this.sessionId = data.data.sessionId;
        } else {
          console.log("Test");
        }
      } catch (error) {
        console.error(error);
      }
    },
    async getHomeBooks() {
      try {
        const response = await fetch("http://localhost:8888/api/front/home/books", {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.weekly_books_info = data.data.filter(item => item.type === '0');
          this.hottest_books_info = data.data.filter(item => item.type === '1');
          this.best_books_info = data.data.filter(item => item.type === '2');
          this.click_rank_info = data.data.filter(item => item.type === '3');
          this.newest_rank_info = data.data.filter(item => item.type === '4');
          this.update_rank_info = data.data.filter(item => item.type === '5');
        } else {
          console.log("Test");
        }
      } catch (error) {
        console.error(error);
      }
    },
    closeLoginBox() {
      this.isLoginVisible = false;
    },
    logout() {
      ElMessage({
        message: "Log out successful",
        type: 'success',
      });
      localStorage.removeItem('userName');
      localStorage.removeItem('token');
      localStorage.removeItem('uid');
      this.$store.dispatch('logout');
      this.$store.dispatch('clearusername');
      this.$store.dispatch('clearuid');
    }
  },
  computed: {
    filteredComments() {
      return this.comments_black_universe.slice(0, 3);
    },
  },
}
</script>
<template>
  <div :class="{ 'blur': isLoginVisible }">
    <div>
      <Header @showLogin="showLogin" @closeLoginBox="closeLoginBox" @logout="logout" />
      <Nav />
    </div>
    <div class="homeBody">
      <div>
      </div>
      <div class="weekly_collect_books_container">
        <div class="weekly_books">
          <h2>Weekly Books</h2>
          <el-carousel :interval="4000" height="300px">
            <el-carousel-item v-for="item in weekly_books_info.slice(0, 3)" :key="item.title">
              <div class="carousel_weekly_background" :style="getBackgroundStyle(item.picUrl)"></div>
              <el-row>
                <el-column class="carousel_weekly_image_container">
                  <img :src="'data:image/png;base64,' + item.picUrl" class="carousel_weekly_image">
                </el-column>
                <el-column class="carousel_weekly_text_container">
                  <div class="carousel_weekly_text">
                    <span class="carousel_weekly_text_title">{{ item.bookName }}</span>
                    <span class="carousel_weekly_text_author">{{ item.authorName }}</span>
                    <span class="carousel_weekly_text_descr">{{ item.bookDesc }}</span>
                  </div>
                </el-column>
              </el-row>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="collected_novel_container">
          <h2>Collected Books</h2>
          <div v-if="!this.$store.state.token" class="collected_novel_na_user">
            <el-empty :image-size="120" description="Please sign in to see more informations">
              <el-button class="login_button" type="primary" @click="showLogin" @showLogin="showLogin"><el-icon>
                  <User />
                </el-icon>{{ login_button }}</el-button>
            </el-empty>
          </div>
          <div v-else class="collected_novel_user">
            <el-empty :image-size="120" description="You don't have collect books"></el-empty>
          </div>
        </div>
      </div>
      <h2></h2>
      <div class="recomm_books_container">
        <div class="hottest_books">
          <h2 style=" border-bottom: 1px solid; width: 100%; border-color: rgb(206, 204, 204); padding-bottom: 10px;">
            Hottest Books</h2>
          <el-carousel height="600px" style="width: 600px; margin-left: -30px;;" direction="vertical" type="card"
            :autoplay="true">
            <el-carousel-item style="border-radius: 15px;" v-for="item in hottest_books_info.slice(0, 6)"
              :key="item.title">
              <el-row class="carousel_container">
                <div class="carousel_left_container">
                  <span style="font-size: 14pt;">{{ item.bookName }}</span>
                  <span style="font-size: 12pt;">{{ item.authorName }}</span>
                  <span style="font-size: 10pt; margin-top: 10px; margin-right: 10px;">{{ item.bookDesc }}</span>
                  <span style="bottom: 10px;position: absolute;">
                    20 <el-icon>
                      <UserFilled />
                    </el-icon> Liked
                  </span>
                </div>
                <div class="carousel_image_container">
                  <img :src="'data:image/png;base64,' + item.picUrl" class="carousel_image">
                </div>
                <div class="carousel_right_container">
                  <div class="carousel_right_comments_container">
                    <div class="carousel_right_comments" v-for="(com, index) in filteredComments" :key="index">
                      <div class="comment-text">{{ com.comment }}</div>
                    </div>
                  </div>
                  <div class="carousel_right_rates_container">
                    <el-rate v-model="value" disabled show-score text-color="#ff9900" size="small"
                      score-template="{value} points" />
                  </div>
                </div>
              </el-row>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="best_books">
          <h2 style="border-bottom: 1px solid; width: 100%; border-color: rgb(206, 204, 204);  padding-bottom: 10px;">Best
            Books
          </h2>
          <el-carousel height="600px" style="width: 600px;" direction="vertical" type="card" :autoplay="true">
            <el-carousel-item style="border-radius: 15px;" v-for="item in best_books_info.slice(0, 6)" :key="item.title">
              <el-row class="carousel_container">
                <div class="carousel_left_container">
                  <span style="font-size: 14pt;">{{ item.bookName }}</span>
                  <span style="font-size: 12pt;">{{ item.authorName }}</span>
                  <span style="font-size: 10pt; margin-top: 10px; margin-right: 10px;">{{ item.bookDesc }}</span>
                  <span style="bottom: 10px;position: absolute;">
                    20 <el-icon>
                      <UserFilled />
                    </el-icon> Liked
                  </span>
                </div>
                <div class="carousel_image_container">
                  <img :src="'data:image/png;base64,' + item.picUrl" class="carousel_image">
                </div>
                <div class="carousel_right_container">
                  <div class="carousel_right_comments_container">
                    <div class="carousel_right_comments" v-for="(com, index) in filteredComments" :key="index">
                      <div class="comment-text">{{ com.comment }}</div>
                    </div>
                  </div>
                  <div class="carousel_right_rates_container">
                    <el-rate v-model="value" disabled show-score text-color="#ff9900" size="small"
                      score-template="{value} points" />
                  </div>
                </div>
              </el-row>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <h2
        style=" border-bottom: 1px solid; border-color: rgb(223, 223, 223); padding-bottom: 10px; margin-bottom: 22px; margin-top: 30px; width: 60%; text-align: center;">
        Ranking of Books</h2>
      <div class="rank_books_container">
        <div class="rank_container">
          <div class="rank_name">Click Rank</div>
          <div class="rank_items" v-for="(item, index) in click_rank_info.slice(0, 5)" :key="item.title">
            <div class="rank_image">
              <img style="height: 80px;" :src="'data:image/png;base64,' + item.picUrl" />
            </div>
            <div class="rank_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
              0{{ index + 1 }}
            </div>
            <div class="rank_info">
              <el-text truncated style="font-size: 14pt; color: black;">{{ item.bookName }}</el-text>
              <br />
              <el-text truncated style="font-size: 10pt;">{{ item.authorName }}</el-text>
              <br />
              <el-text truncated style="font-size: 10pt; width: 250px;"><el-rate v-model="item.value" disabled show-score
                  text-color="#ff9900" size="small" score-template="{value} points" /></el-text>
            </div>
          </div>
        </div>
        <div class="rank_container">
          <div class="rank_name">Newest Rank</div>
          <div class="rank_items" v-for="(item, index) in newest_rank_info.slice(0, 5)" :key="item">
            <div class="rank_image">
              <img style="height: 80px;" :src="'data:image/png;base64,' + item.picUrl" />
            </div>
            <div class="rank_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
              0{{ index + 1 }}
            </div>
            <div class="rank_info">
              <el-text truncated style="font-size: 14pt; color: black;">{{ item.bookName }}</el-text>
              <br />
              <el-text truncated style="font-size: 10pt;">{{ item.authorName }}</el-text>
              <br />
              <el-text truncated style="font-size: 10pt; width: 250px;"><el-rate v-model="item.value" disabled show-score
                  text-color="#ff9900" size="small" score-template="{value} points" /></el-text>
            </div>
          </div>
        </div>
        <div class="rank_container">
          <div class="rank_name">Update Rank</div>
          <div class="rank_items" v-for="(item, index) in update_rank_info.slice(0, 5)" :key="item">
            <div class="rank_image">
              <img style="height: 80px;" :src="'data:image/png;base64,' + item.picUrl" />
            </div>
            <div class="rank_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
              0{{ index + 1 }}
            </div>
            <div class="rank_info">
              <el-text truncated style="font-size: 14pt; color: black;">{{ item.bookName }}</el-text>
              <br />
              <el-text truncated style="font-size: 10pt;">{{ item.authorName }}</el-text>
              <br />
              <el-text truncated style="font-size: 10pt; width: 250px;"><el-rate v-model="item.value" disabled show-score
                  text-color="#ff9900" size="small" score-template="{value} points" /></el-text>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Go to top floating buttom -->
    <el-backtop :bottom="100">
      <div class="goTopButton">
        <el-icon>
          <CaretTop />
        </el-icon>
      </div>
    </el-backtop>
    <Footer />
  </div>
  <transition name="fade">
    <div v-if="isLoginVisible" class="loginSection">
      <Login class="login" :verImage="this.verImage" :sessionId="this.sessionId" @showLogin="showLogin"
        @cancel="closeLoginBox" />
    </div>
  </transition>
</template>

<style>
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;

}

.blur {
  filter: blur(5px);
  pointer-events: none;
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

.weekly_books {
  width: 50%;
  margin-right: 5px;
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
  transition: transform 0.3s ease;
}

.carousel_weekly_image:hover {
  transform: translateY(-4px);
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
  font-size: 10pt;
  padding-right: 50px;
  border: 1px solid;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 8;
  overflow: hidden;
}

.collected_novel_container {
  width: 50%;
  margin-left: 5px;
  display: flex;
  flex-direction: column;
}

.collected_novel_na_user {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: var(--el-box-shadow);
  background-color: #FAFCFF;
}

.collected_novel_user {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: var(--el-box-shadow);
  background-color: #FAFCFF;
}

.recomm_books_container {
  margin-top: -10px;
  margin-bottom: -5px;
  width: 60%;
  display: flex;
  justify-content: space-between;
}

.hottest_books {
  margin-top: -20px;
  display: flex;
  flex-direction: column;
}

.best_books {
  margin-top: -20px;
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 1;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(n) {
  background-color: rgb(231, 231, 231);
}

.carousel_container {
  height: 300px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 2px 4px rgba(0, 0, 0, 0.06);
  transform: translateY(0);
  transition: transform 0.3s ease;
}

.carousel_container:hover {
  transform: translateY(-4px);
}

.carousel_left_container {
  width: 30%;
  padding-left: 20px;
  padding-top: 20px;
  display: flex;
  flex-direction: column;
}

.carousel_image_container {
  width: 30%;
}

.carousel_image {
  height: 300px;
  object-fit: contain;
}

.carousel_right_container {
  width: 30%;
  height: 100%;
  padding-left: 25px;
  padding-top: 10px;
}

.carousel_right_comments_container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 85%;
}

.carousel_right_comments {
  display: block;
  height: 31%;
  width: 98%;
  border: 1px solid #1989fa;
  border-radius: 3px;
  overflow: hidden;
}

.carousel_right_comments .comment-text {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
  overflow: hidden;
}

.carousel_right_rates_container {
  bottom: 7px;
  position: absolute;
}

.rank_books_container {
  margin-top: -10px;
  margin-bottom: 15px;
  width: 60%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-left: 100px;
  height: 500px;
}

.rank_container {
  /* background-color: aliceblue; */
  width: 30%;
  padding-left: 10px;
  margin-right: 10px;
}

.rank_name {
  font-size: 18pt;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #1989fa;
  width: 200px;
  color: white;
  box-shadow: 10px 8px 0 orange;
  margin-bottom: 22px;
  border-radius: 2px;
  padding: 2px;
}

.rank_items {
  display: flex;
  margin-top: 8px;
}

.rank_image img {
  border-radius: 4px;
  margin-right: 10px;
  transform: translateY(0);
  transition: transform 0.3s ease;
}

.rank_image img:hover {
  transform: translateY(-4px);
}

.rank_rank {
  margin-right: 10px;
  font-size: 14pt;
  color: grey;
  padding-top: 3px;
}

.red {
  color: rgb(255, 0, 0);
}

.orange {
  color: rgb(255, 166, 0);
}

.green {
  color: rgb(3, 179, 3);
}

.rank_info {
  padding-top: 3px;
  font-size: 14pt;
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
</style>

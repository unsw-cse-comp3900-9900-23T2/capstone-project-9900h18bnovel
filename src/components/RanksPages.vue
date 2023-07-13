<script setup>
import {
  CaretTop,
  UserFilled,
  CaretBottom,
  Warning,
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
import { Linter } from 'eslint';
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `
</script >

<script>
import Global_Header from './Global_Header.vue';
import Global_Footer from './Global_Footer.vue';
import Global_Nav from './Global_Nav.vue';
import Login from './Auth_Page.vue';
export default {
  emits: ['showLogin', 'closeLoginBox', 'logout'],
  data() {
    const update_book_info = [
      { value: 5, title: "Crack The Code", image: "https://www.adobe.com/express/create/cover/media_181e3d2c78f153ae7bf0e19a2faeb9a76e234da30.jpeg?width=400&format=jpeg&optimize=medium", author: "Patrick C.Harless", des: "In a small coastal town, a mysterious stranger arrives, bringing with him an air of intrigue and secrets. As the townspeople become entranced by his enigmatic presence, they soon discover that he holds the key to their deepest desires and darkest fears." },
      { value: 4.7, title: "Pattingson's Curse", image: "https://www.designbookcover.pt/uploads/media/photos/cache/pattinsons_curse_72_media_huge_thumbnail.jpg", author: "Graeme Arnold", des: "Set in a dystopian future, where technology rules every aspect of life, a group of rebels embarks on a perilous journey to dismantle the oppressive regime. Along the way, they uncover shocking truths about the world they thought they knew, testing their resolve and challenging their beliefs." },
      { value: 4.5, title: "Only Words", image: "https://www.designbookcover.pt/uploads/media/photos/cache/b1_only_words_72_media_huge_thumbnail.jpg", author: "Shane Ashby Trilogy", des: "In the heart of a bustling metropolis, two star-crossed lovers find themselves caught in a web of forbidden love. As they navigate the complexities of their social divide and societal expectations, they must confront the consequences of their actions and make choices that could change their lives forever." },
      { value: 4.0, title: "Invisible Man", image: "https://news.harvard.edu/wp-content/uploads/2022/07/20220727_books_invisibleman_2500-655x1024.jpg", author: "Ralph Ellison", des: "In a realm of magic and mythical creatures, a young hero emerges from humble beginnings. With an ancient prophecy guiding their path, they embark on a quest to save their kingdom from an impending darkness. Along the way, they face formidable challenges, forge unlikely alliances, and discover the true power within themselves" },
      { value: 3.6, title: "When Blood Meets Earth", image: "https://www.nairaland.com/attachments/16975928_octoberillustration6768x1152_jpeg7ab7fc0d22a2a942cf8632fae8e2295b", author: "E.A.NOBLE", des: "Against the backdrop of war-torn lands, a group of unlikely companions bands together to reclaim their homeland from the clutches of evil. As they navigate treacherous landscapes and face unimaginable dangers, they learn the true meaning of sacrifice, loyalty, and the enduring strength of friendship." },
      { title: 'Memory', image: 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/contemporary-fiction-night-time-book-cover-design-template-1be47835c3058eb42211574e0c4ed8bf_screen.jpg?ts=1637012564', author: 'Ajax Mother', des: "I wandered lonely as a cloud that floats on high o'er vales and hills when all at once I saw a crowd, a host, of golden daffodils." },
      { title: 'Our Last Summer', image: 'https://marketplace.canva.com/EAFFEs6P168/1/0/1003w/canva-orange-green-watercolor-soft-cute-cartoon-love-romance-book-cover-5Zk2VM0-EAc.jpg', author: "Bob Fucker", des: 'Through this wide and troubled world I roam, alone and without a home, seeking solace in the depths of my soul' },
      { title: 'The Past is Rising', image: 'https://i.pinimg.com/474x/a7/91/62/a7916230aedcdce47a4dfbff5247f0ce.jpg', author: "Charles Joker", des: 'Amidst the chaos and confusion, she remained calm and composed, a beacon of hope in the midst of darkness' },
      { title: 'The Black Universe', image: 'https://marketplace.canva.com/EAFEbtlNK2Q/1/0/1003w/canva-double-exposure-artistic-background-novel-book-cover-sTAyOpO_rTI.jpg', author: 'Jesus Criss', des: "I wandered lonely as a cloud that floats on high o'er vales and hills when all at once I saw a crowd, a host, of golden daffodils." },
      { title: 'Shadow of Evil', image: 'https://i.etsystatic.com/19280387/r/il/63ad6f/2350960458/il_fullxfull.2350960458_sj9e.jpg', author: "Tommas Tu", des: 'Through this wide and troubled world I roam, alone and without a home, seeking solace in the depths of my soul' },
      { title: 'The Hypocrite World', image: 'https://marketplace.canva.com/EAD7WuSVrt0/1/0/1003w/canva-colorful-illustration-young-adult-book-cover-LVthABb24ik.jpg', author: "Siant Diygo", des: 'Amidst the chaos and confusion, she remained calm and composed, a beacon of hope in the midst of darkness' },
    ]
    return {
      update_book_info: update_book_info,
      isLoginVisible: false,
      verImage: '',
      sessionId: '',
      newestUpdateBooks: null,
      loading: true,
      showNewestUpdatePage: false,
      count: 10,
      loadMore: false,
      isClickRank: false,
      isNewestRank: false,
      isUpdateRank: false,
    }
  },
  mounted() {
    const path = this.$route.path;
    path === "/newestrank" ? this.isNewestRank = true : this.isNewestRank = false;
    path === "/updaterank" ? this.isUpdateRank = true : this.isUpdateRank = false;
    path === "/clickrank" ? this.isClickRank = true : this.isClickRank = false;
    this.getNewestUpdateBooks();
    setTimeout(() => {
      this.loading = false;
      this.showNewestUpdatePage = true;
    }, 500);
  },
  methods: {
    handleSearch() {
      this.$router.push('/allnovels');
    },
    async getNewestUpdateBooks() {
      const whichrank = this.isNewestRank ? "newest_rank" : this.isClickRank ? "visit_rank" : this.isUpdateRank ? "update_rank" : null;
      try {
        const response = await fetch("http://localhost:8888/api/front/book/" + whichrank, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.newestUpdateBooks = data.data;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },
    load() {
      this.loadMore = true;
      setTimeout(() => {
        this.loadMore = false;
        this.count += 5;
        if (this.count >= this.newestUpdateBooks.length + 5) {
          ElMessage.error("There is no more books");
        }
      }, 500);
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
          console.log(response.status);
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
    },
    getItemColor(categoryName) {
      switch (categoryName) {
        case 'action':
          return '#FF6F61';
        case 'romance':
          return '#FFC0CB';
        case 'fantasy':
          return '#91D18B';
        case 'mystery':
          return '#6B705C';
        case 'horror':
          return '#585481';
        case 'thriller':
          return '#333A56';
        case 'drama':
          return '#FFC09F';
        default:
          return null;
      }
    },

  },
  computed: {
    filteredBooks() {
      return this.newestUpdateBooks.slice(0, this.count);
    },
  },
  watch: {
    '$route'(to) {
      if (to.path === "/newestrank") {
        this.isNewestRank = true;
        this.isUpdateRank = false;
        this.isClickRank = false;
        this.getNewestUpdateBooks();
      } else if (to.path === "/updaterank") {
        this.isNewestRank = false;
        this.isUpdateRank = true;
        this.isClickRank = false;
        this.getNewestUpdateBooks();
      } else if (to.path === "/clickrank") {
        this.isNewestRank = false;
        this.isUpdateRank = false;
        this.isClickRank = true;
        this.getNewestUpdateBooks();
      }
      this.loading = true;
      this.showNewestUpdatePage = false;
      setTimeout(() => {
        this.loading = false;
        this.showNewestUpdatePage = true;
      }, 500);
    }
  },
  components: {
    Global_Footer,
    Global_Header,
    Global_Nav,
  }
}
</script>

<template>
  <div :class="{ 'blur': isLoginVisible }">
    <div v-infinite-scroll="load">
      <el-backtop :bottom="100">
        <div class="goTopButton">
          <el-icon>
            <CaretTop />
          </el-icon>
        </div>
      </el-backtop>
      <Global_Header @handleSearch="handleSearch" @logout="logout" @showLogin="showLogin"
        @closeLoginBox="closeLoginBox" />
      <Global_Nav />
      <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
        element-loading-background="rgba(255, 255, 255, 255)"
        style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
      <div v-if="showNewestUpdatePage">
        <div style="display: flex; flex-direction: column;  align-items: center;">
          <ul class="infinite-list">
            <h1
              style="border-bottom: 1px solid; border-color: rgb(223, 223, 223); padding-bottom: 10px; margin-bottom: 22px; width: 100%; text-align: center;">
              {{ isUpdateRank ? "Update Rank" : isClickRank ? "Click Rank" : isNewestRank ? "Newest Rank" : null }}
              <el-popover placement="right" :width="240" trigger="hover"
                :content="isUpdateRank ? 'The Update Rank is a dynamic list that showcases novels with recent updates. It presents novels that have been recently added chapters or undergone significant updates' : isNewestRank ? 'The Newest Rank is a list that features the latest releases of novels. It highlights the freshest in the NovelHub.' : isClickRank ? 'The Click Rank is a list based on the total number of clicks a novel receives. It showcases the most popular and highly-clicked novels at the moment.' : null">
                <template #reference>
                  <el-icon style="font-size: 10pt;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </h1>
            <li v-for="(item, index) in filteredBooks" :key="item.id" class="infinite-list-item">
              <div style="font-size: 14pt; width:100px; text-align: center;">
                {{ index < 9 ? '0' + (index + 1) : index + 1 }} </div>
                  <img :src="item.picUrl"
                    style=" height: 155px; border-radius: 5px; box-shadow: 6px 4px 6px rgb(151, 151, 151);" />
                  <div class="update_book_item_container">
                    <div style="font-size: 18pt; overflow: hidden; text-overflow: ellipsis;">{{ item.bookName }}</div>
                    <div style="font-size: 12pt;">{{ item.authorName }}</div>
                    <div
                      style="font-size: 10pt; margin-top: 5px; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 5; overflow: hidden;">
                      {{ item.bookDesc }}</div>
                    <el-tag style="bottom: 12px; position: absolute; font-size: 10pt;"
                      :color="getItemColor(item.categoryName)" effect="dark">{{
                        item.categoryName
                      }}</el-tag>
                  </div>
                  <div class="update_book_reviews_container">
                    <div>{{ item.lastChapterUpdateTime }}</div>
                    <div>{{ item.collectCount }} <el-icon>
                        <UserFilled />
                      </el-icon> Collected</div>
                    <div>{{ item.visitCount }} <el-icon>
                        <UserFilled />
                      </el-icon> Viewed</div>
                    <el-rate v-model="item.score" disabled show-score text-color="#ff9900" size="small"
                      score-template="{value} points" />
                  </div>
            </li>
            <div style="height: 100px; margin-top: 20px; width: 100%; display:flex; justify-content: center;"
              v-loading="loadMore" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
              element-loading-background="rgba(255, 255, 255, 255)">
              <div v-if="count >= newestUpdateBooks.length + 5" @click="scrollToTop"
                style="text-align: center; width: 100%;">
                <h3>
                  <el-icon>
                    <CaretTop />
                  </el-icon>No more book behind<el-icon>
                    <CaretTop />
                  </el-icon>
                </h3>
                <el-divider />
              </div>
              <h3 v-else>
                <el-icon>
                  <CaretBottom />
                </el-icon>
                Scroll down to see more
                <el-icon>
                  <CaretBottom />
                </el-icon>
              </h3>
            </div>
          </ul>
        </div>
        <Global_Footer />
      </div>
    </div>
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

.blur {
  filter: blur(5px);
  pointer-events: none;
}

.infinite-list {
  width: 1152px;
  min-width: 1152px;
}

.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  padding-top: 10px;
  padding-bottom: 10px;
  height: 157px;
  border: 1px solid;
  border-color: rgb(223, 223, 223);
  margin-bottom: 10px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 2px 4px rgba(0, 0, 0, 0.06);
  transform: translateY(0);
  transition: transform 0.3s ease;
}

.infinite-list .infinite-list-item:hover {
  transform: translateY(-5px);
}

.infinite-list .infinite-list-item+.list-item {
  margin-top: 10px;
}

.update_book_item_container {
  height: 100%;
  width: 60%;
  padding-left: 50px;
  padding-right: 10px;
}

.update_book_reviews_container {
  height: 100%;
  width: 20%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  border-left: 1px solid;
  border-color: rgb(223, 223, 223);
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

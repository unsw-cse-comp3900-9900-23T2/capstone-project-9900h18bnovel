<script setup>
import {
  UserFilled,
  Warning,
  ArrowRight,
  StarFilled,
} from '@element-plus/icons-vue';
import axios from 'axios';
import { getItemColor } from  '@/utils.js';
</script>
<script>
export default {
  data() {
    return {
      weekly_books_info: '',
      hottest_books_info: '',
      best_books_info: '',
      comments_black_universe: '',
      newest_books_images: '',
      login_button: 'Sign in',
      click_rank_info: '',
      newest_rank_info: '',
      update_rank_info: '',
      isLoginVisible: false,
      VerImage: '',
      sessionId: '',
      showHomePage: false,
      loading: true,
      collectedBooks: [],
    }
  },
  watch: {
    '$store.getters.GetUID': {
      handler() {
        this.getUserCollect();
      },
      deep: true
    },
  },
  mounted() {
    this.getHomeBooks();
    setTimeout(() => {
      this.getUserCollect();
      this.loading = false;
      this.showHomePage = true;
    }, 500);
  },
  methods: {
    getBackgroundStyle(imageUrl) {
      return {
        'background-image': `url(${imageUrl})`
      };
    },

    async getUserCollect() {
      await axios.get(`/api/front/user/user_collect?userId=${this.$store.getters.GetUID}`)
        .then(response => {
          const data = response.data;
          this.collectedBooks = data.data ? data.data : [];
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getHomeBooks() {
      await axios.get("/api/front/home/books")
        .then(response => {
          const data = response.data;
          this.weekly_books_info = data.data.filter(item => item.type === '0');
          this.hottest_books_info = data.data.filter(item => item.type === '1').map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
          this.best_books_info = data.data.filter(item => item.type === '2').map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
          this.click_rank_info = data.data.filter(item => item.type === '3').map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
          this.newest_rank_info = data.data.filter(item => item.type === '4').map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
          this.update_rank_info = data.data.filter(item => item.type === '5').map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
        })
        .catch(error => {
          console.error(error);
        });
    },

    goBookInfo(bookId) {
      this.$router.push(`/bookInfo/${bookId}`);
    },

    GoToMyCollection() {
      this.$router.push('/userprofile');
    },

    goClickRank() {
      this.$router.push("/clickrank");
    },

    goNewestRank() {
      this.$router.push("/newestrank")
    },

    goUpdateRank() {
      this.$router.push("/updaterank")
    },
  },
  computed: {
    filteredComments() {
      return this.comments_black_universe.slice(0, 3);
    },
  },
}
</script>

<template>
  <div v-loading.fullscreen="loading" element-loading-spinner=" ">
  </div>
  <div v-if="showHomePage">
    <div class="flex-center">
      <div class="homeBody">
        <div class="weekly_collect_books_container">
          <div class="weekly_books">
            <h2>
              Weekly Books
              <el-popover placement="right" :width="230" trigger="hover"
                content="The Weekly Books features the highest-rated and most-viewed books of the week.">
                <template #reference>
                  <el-icon style="font-size: 10pt;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </h2>
            <el-carousel :interval="4000" height="300px"
              style="border-radius: 5px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);">
              <el-carousel-item v-for="item in weekly_books_info.slice(0, 3)" :key="item.title">
                <div class="carousel_weekly_background" :style="getBackgroundStyle(item.picUrl)"></div>
                <el-row>
                  <div class="carousel_weekly_image_container">
                    <img :src="item.picUrl" class="carousel_weekly_image" @click="goBookInfo(item.bookId)">
                  </div>
                  <div class="carousel_weekly_text_container">
                    <div class="carousel_weekly_text">
                      <span class="carousel_weekly_text_title" @click="goBookInfo(item.bookId)">{{ item.bookName
                      }}</span>
                      <span class="carousel_weekly_text_author">{{ item.authorName }}</span>
                      <span class="carousel_weekly_text_descr">{{ item.bookDesc }}</span>
                      <el-tag class="tag" effect="plain" :style="getItemColor(item.categoryName)">{{
                        item.categoryName
                      }}</el-tag>
                    </div>
                  </div>
                </el-row>
              </el-carousel-item>
            </el-carousel>
          </div>
          <div class="collected_novel_container">
            <h2>
              Collected Books
              <el-popover placement="right" :width="250" trigger="hover"
                content="The Collected Books determined by readers' personal collections, must signed in to view the section">
                <template #reference>
                  <el-icon style="font-size: 10pt;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </h2>
            <div v-if="!$store.state.token" class="collected_novel_na_user">
              <el-empty :image-size="120" description="Please sign in to see more informations">
              </el-empty>
            </div>
            <div v-else class="collected_novel_user">
              <div v-if="collectedBooks.length > 0" class="collected_novel_user_ya">
                <div v-for="item in collectedBooks.slice(0, 3)" :key="item.bookId"
                  style="margin-left: 15px; display: flex; flex-direction:column; align-items:center;">
                  <img :src="item.picUrl" class="collected_img" @click="goBookInfo(item.bookId)" />
                  <div class="collected_word" style="margin-top: 10px;" @click="goBookInfo(item.bookId)">
                    <b>{{ item.bookName }}</b>
                  </div>
                  <div class="collected_word" style="text-decoration: none; cursor: default;">
                    {{ item.preChapterName }}
                  </div>
                </div>
                <el-button style="height:100%; background-color:rgb(0,0,0,0); right:0; position:absolute;
                  border-color:rgb(0,0,0,0); border-left: 1px solid #b7b7b7; border-radius: 0;"
                  @click="GoToMyCollection">
                  A<br>L<br>L<br>
                  <el-icon>
                    <ArrowRight />
                  </el-icon>
                </el-button>
              </div>
              <div v-else>
                <div class="collected_novel_user_na">
                  <el-empty :image-size="120" description="You don't have collect books"></el-empty>
                </div>
              </div>
            </div>
          </div>
        </div>
        <el-divider />
        <div class="recomm_books_container">
          <div class="hottest_books">
            <h2 style="text-align: center;">Hottest Books
              <el-popover placement="right" :width="275" trigger="hover"
                content="The Hottest Books list features the most popular and sought-after literary gems based on the number of times they have been collected by readers">
                <template #reference>
                  <el-icon style="font-size: 10pt;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </h2>
            <el-carousel height="600px" class="hot_best_carousel" direction="vertical" type="card" :autoplay="true">
              <el-carousel-item style="border-radius: 15px;" v-for="item in hottest_books_info.slice(0, 6)"
                :key="item.bookName">
                <el-row class="carousel_container">
                  <div class="carousel_left_container">
                    <span class="carousel_left_title" @click="goBookInfo(item.bookId)">
                      <b>{{ item.bookName }}</b>
                    </span>
                    <span class="carousel_left_author">{{ item.authorName }}</span>
                    <span class="carousel_left_desc">{{
                      item.bookDesc }}</span>
                    <el-tag class="tag" effect="plain" :style="getItemColor(item.categoryName)">{{
                      item.categoryName
                    }}</el-tag>
                    <span style="bottom: 0px;position: absolute;">
                      {{ item.collectCount }} <el-icon>
                        <UserFilled />
                      </el-icon> Collected
                    </span>
                  </div>
                  <div class="carousel_image_container">
                    <img :src="item.picUrl" class="carousel_image" @click="goBookInfo(item.bookId)">
                  </div>
                  <div class="carousel_right_container">
                    <div class="carousel_right_comments_container">
                      <div v-for="(comments, index) in item.bookComments.slice(0, 3)" :key="index"
                        class="carousel_right_comments">
                        <div style="position: relative; height: 75px; padding: 5px;">
                          <div class="comment-text">
                            {{ comments.commentContent }}
                          </div>
                          <div
                            style="bottom: 15px; position: absolute; width: 100%; display: flex; justify-content: space-between;">
                            <div style="display: flex; align-items: center;">
                              <el-icon>
                                <StarFilled color="#f7ba2a" />
                              </el-icon>
                              {{ comments.score }}
                            </div>
                            <div
                              style="width: 60%; text-align: right; margin-right: 15px; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1; overflow: hidden;">
                              {{ comments.commentUserName }}
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="carousel_right_rates_container">
                      <el-rate v-model="item.score" disabled show-score text-color="#ff9900" size="small"
                        style="margin-left: -5px;" score-template="{value} points" />
                    </div>
                  </div>
                </el-row>
              </el-carousel-item>
            </el-carousel>
          </div>
          <div class="best_books">
            <h2 style="text-align: center;">Best Books
              <el-popover placement="right" :width="265" trigger="hover"
                content="The Best Books list showcases the most highly-rated and acclaimed literary works based on their scores">
                <template #reference>
                  <el-icon style="font-size: 10pt;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </h2>
            <el-carousel height="600px" class="hot_best_carousel" direction="vertical" type="card" :autoplay="true">
              <el-carousel-item style="border-radius: 15px;" v-for="item in best_books_info.slice(0, 6)"
                :key="item.bookName">
                <el-row class="carousel_container">
                  <div class="carousel_left_container">
                    <span class="carousel_left_title" @click="goBookInfo(item.bookId)">
                      <b>{{ item.bookName }}</b>
                    </span>
                    <span class="carousel_left_author">{{ item.authorName }}</span>
                    <span class="carousel_left_desc">{{
                      item.bookDesc }}</span>
                    <el-tag class="tag" effect="plain" :style="getItemColor(item.categoryName)">{{
                      item.categoryName
                    }}</el-tag>
                    <span style="bottom: 0px;position: absolute;">
                      {{ item.collectCount }} <el-icon>
                        <UserFilled />
                      </el-icon> Collected
                    </span>
                  </div>
                  <div class="carousel_image_container">
                    <img :src="item.picUrl" class="carousel_image" @click="goBookInfo(item.bookId)">
                  </div>
                  <div class="carousel_right_container">
                    <div class="carousel_right_comments_container">
                      <div v-for="(comments, index) in item.bookComments.slice(0, 3)" :key="index"
                        class="carousel_right_comments">
                        <div style="position: relative; height: 75px; padding: 5px;">
                          <div class="comment-text">
                            {{ comments.commentContent }}
                          </div>
                          <div
                            style="bottom: 15px; position: absolute; width: 100%; display: flex; justify-content: space-between;">
                            <div style="display: flex; align-items: center;">
                              <el-icon>
                                <StarFilled color="#f7ba2a" />
                              </el-icon>
                              {{ comments.score }}
                            </div>
                            <div
                              style="width: 60%; text-align: right; margin-right: 15px; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1; overflow: hidden;">
                              {{ comments.commentUserName }}
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="carousel_right_rates_container">
                      <el-rate v-model="item.score" disabled show-score text-color="#ff9900" size="small"
                        style="margin-left: -5px;" score-template="{value} points" />
                    </div>
                  </div>
                </el-row>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
        <el-divider />
        <div class="rank_books_container">
          <div class="rank_container">
            <div style="display: flex;">
              <div class="rank_name" @click="goClickRank">Click Rank</div>
              <el-popover placement="right" :width="210" trigger="hover"
                content="The Click Rank is a list based on the total number of clicks a novel receives. It showcases the most popular and highly-clicked novels at the moment.">
                <template #reference>
                  <el-icon style="margin-left: 10px;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </div>
            <div class="rank_items" v-for="(item, index) in click_rank_info.slice(0, 5)" :key="item.title">
              <div class="rank_image">
                <img class="rank_img" :src="item.picUrl" @click="goBookInfo(item.bookId)" />
              </div>
              <div class="rank_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
                0{{ index + 1 }}
              </div>
              <div class="rank_info">
                <el-text truncated class="rank_title" @click="goBookInfo(item.bookId)">{{ item.bookName }}</el-text>
                <br />
                <el-text truncated class="rank_author">{{ item.authorName }}</el-text>
                <br />
                <el-text truncated class="rank_score"><el-rate v-model="item.score" disabled show-score
                    text-color="#ff9900" size="small" score-template="{value}" /></el-text>
              </div>
            </div>
          </div>
          <div class="rank_container">
            <div style="display: flex;">
              <div class="rank_name" @click="goNewestRank">Newest Rank</div>
              <el-popover placement="right" :width="215" trigger="hover"
                content="The Newest Rank is a list that features the latest releases of novels. It highlights the freshest in the NovelHub.">
                <template #reference>
                  <el-icon style="margin-left: 10px;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </div>
            <div class="rank_items" v-for="(item, index) in newest_rank_info.slice(0, 5)" :key="item">
              <div class="rank_image">
                <img class="rank_img" :src="item.picUrl" @click="goBookInfo(item.bookId)" />
              </div>
              <div class="rank_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
                0{{ index + 1 }}
              </div>
              <div class="rank_info">
                <el-text truncated class="rank_title" @click="goBookInfo(item.bookId)">{{ item.bookName }}</el-text>
                <br />
                <el-text truncated class="rank_author">{{ item.authorName }}</el-text>
                <br />
                <el-text truncated class="rank_score"><el-rate v-model="item.score" disabled show-score
                    text-color="#ff9900" size="small" score-template="{value}" /></el-text>
              </div>
            </div>
          </div>
          <div class="rank_container">
            <div style="display: flex;">
              <div class="rank_name" @click="goUpdateRank">Update Rank</div>
              <el-popover placement="right" :width="240" trigger="hover"
                content="The Update Rank is a dynamic list that showcases novels with recent updates. It presents novels that have been recently added chapters or undergone significant updates">
                <template #reference>
                  <el-icon style="margin-left: 10px;">
                    <Warning />
                  </el-icon>
                </template>
              </el-popover>
            </div>
            <div class="rank_items" v-for="(item, index) in update_rank_info.slice(0, 5)" :key="item">
              <div class="rank_image">
                <img class="rank_img" :src="item.picUrl" @click="goBookInfo(item.bookId)" />
              </div>
              <div class="rank_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
                0{{ index + 1 }}
              </div>
              <div class="rank_info">
                <el-text truncated class="rank_title" @click="goBookInfo(item.bookId)">{{ item.bookName }}</el-text>
                <br />
                <el-text truncated class="rank_author">{{ item.authorName }}</el-text>
                <br />
                <el-text truncated class="rank_score"><el-rate v-model="item.score" disabled show-score
                    text-color="#ff9900" size="small" score-template="{value}" /></el-text>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.flex-center {
  display: flex;
  justify-content: center;
}

.tag {
  bottom: 30px;
  position: absolute;
  margin-top: 10px;
}

.homeBody {
  width: 1152px;
  min-width: 1152px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.weekly_collect_books_container {
  margin-top: 20px;
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.weekly_books {
  width: 50%;
  margin-right: 5px;
}

.weekly_books .el-carousel__button {
  background-color: white;
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
  width: 156.25px;
  height: 250px;
  object-fit: contain;
  box-shadow: 6px 4px 6px white;
  border-radius: 8px;
  transition: transform 0.3s ease;
}

.carousel_weekly_image:hover {
  transform: translateY(-4px);
  cursor: pointer;
}

.carousel_weekly_text_container {
  width: 60%;
  height: 300px;
}

.carousel_weekly_text {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  color: white;
}

.carousel_weekly_text_title {
  font-size: 20pt;
  margin-bottom: 5px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
}

.carousel_weekly_text_title:hover {
  cursor: pointer;
  text-decoration: underline;
}

.carousel_weekly_text_author {
  font-size: 18pt;
  margin-bottom: 20px;
}

.carousel_weekly_text_descr {
  font-size: 10pt;
  line-height: 1.5;
  padding-right: 50px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
  overflow: hidden;
}

.collected_novel_na_user .el-empty__description p {
  color: #000000;
}

.collected_novel_container {
  width: 50%;
  min-height: 300px;
  margin-left: 5px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.collected_novel_na_user {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f6f6f6;
  color: white;
  border-radius: 5px;
}

.collected_novel_user {
  height: 100%;
  background-color: #f3f3f3;
  border-radius: 5px;
}

.collected_novel_user_ya {
  display: flex;
  flex-wrap: wrap;
  margin-top: 9px;
  position: relative;
  border-radius: 5px;
}

.collected_word {
  text-align: center;
  width: 150px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
}

.collected_word:hover {
  cursor: pointer;
  text-decoration: underline;
}

.collected_img {
  height: 240px;
  width: 150px;
  box-shadow: 6px 4px 6px rgb(65, 65, 65);
  border-radius: 8px;
  transition: transform 0.3s ease;
}

.collected_img:hover {
  transform: translateY(-4px);
  cursor: pointer;
}

.collected_novel_user_na {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.recomm_books_container {
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
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
  background-color: #f3f3f3;
}

.recomm_books_container .el-carousel__button {
  background-color: #676767;
}

.hot_best_carousel {
  width: 570px;
}

.carousel_container {
  padding: 10px;
  height: 300px;
}

.carousel_container:hover {
  cursor: default;
}


.carousel_left_container {
  width: 30%;
  display: flex;
  flex-direction: column;
  margin-left: 10px;
  text-overflow: ellipsis;
  overflow: hidden;
  position: relative;
}

.carousel_left_title {
  font-size: 12pt;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

.carousel_left_title:hover {
  cursor: pointer;
  text-decoration: underline;
}

.carousel_left_author {
  font-size: 12pt;
}

.carousel_left_desc {
  font-size: 10pt;
  line-height: 1.5;
  margin-top: 10px;
  margin-right: 10px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 7;
  overflow: hidden;
}

.carousel_image_container {
  height: 100%;
  transform: scale(1);
  transition: transform 0.3s ease;
}

.carousel_image_container:hover {
  cursor: pointer;
  transform: scale(1.03);
}

.carousel_image {
  box-shadow: 6px 4px 6px rgb(92, 92, 92);
  border-radius: 5px;
  height: 100%;
  width: 175px;
  object-fit: contain;
}

.carousel_right_container {
  padding-left: 20px;
  width: 30%;
}

.carousel_right_comments_container {
  display: flex;
  flex-direction: column;
  height: 85%;
  margin-left: -10px;
  margin-top: 10px;
  font-size: 10pt;
}

.carousel_right_comments {
  display: block;
  height: 30%;
  width: 100%;
  border: 1px solid #1989fa;
  border-radius: 3px;
  overflow: hidden;
  margin-bottom: 5px;
}

.carousel_right_comments .comment-text {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}

.carousel_right_rates_container {
  bottom: 10px;
  position: absolute;
}

.rank_books_container {
  margin-bottom: 15px;
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 500px;
}

.rank_container {
  /* background-color: aliceblue; */
  width: 33%;
}

.rank_name {
  font-size: 18pt;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to right, #1989fa, #7c87ff);
  width: 200px;
  color: white;
  box-shadow: 10px 8px 0 orange;
  margin-bottom: 22px;
  border-radius: 2px;
  padding: 2px;
}

.rank_name:hover {
  cursor: pointer;
}

.rank_items {
  display: flex;
  margin-top: 5px;
}

.rank_image img {
  height: 80px;
  width: 50px;
  border-radius: 4px;
  margin-right: 10px;
  transform: translateY(0);
  transition: transform 0.3s ease;
}

.rank_image img:hover {
  transform: translateY(-4px);
  cursor: pointer;
}

.rank_rank {
  margin-right: 10px;
  font-size: 14pt;
  color: grey;
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
  font-size: 14pt;
}

.rank_info .rank_title {
  font-size: 14pt;
  color: black;
  width: 250px;
}

.rank_title:hover {
  cursor: pointer;
  text-decoration: underline;
}

.rank_author {
  font-size: 10pt;
}

.rank_score {
  font-size: 10pt;
  width: 250px;
}
</style>

<style>
@media screen and (max-width:415px) {
  .homeBody {
    width: 100vw;
    min-width: 100vw;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .weekly_collect_books_container {
    width: 90%;
    display: flex;
    flex-direction: column;
  }

  .weekly_books {
    width: 100%;
    margin: auto;
  }

  .collected_novel_container {
    margin-top: 20px;
    width: 100%;
    min-height: 300px;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    margin: auto;
  }

  .recomm_books_container {
    margin-top: 20px;
    width: 90%;
    display: flex;
    flex-direction: column;
  }

  .hottest_books {
    width: 100%;
    display: flex;
    flex-direction: column;
  }

  .best_books {
    width: 100%;
    margin-top: 20px;
    display: flex;
    flex-direction: column;
  }

  .hot_best_carousel {
    width: 100%;
  }

  .carousel_weekly_image {
    width: 125px;
    height: 200px;
    object-fit: contain;
    box-shadow: 6px 4px 6px white;
    border-radius: 8px;
    transition: transform 0.3s ease;
  }

  .carousel_weekly_text_container {
    width: 60%;
    border: 1px soild;
    height: 300px;
  }

  .carousel_weekly_text {
    margin-top: 50px;
  }

  .carousel_weekly_text_title {
    font-size: 14pt;
  }

  .carousel_weekly_text_author {
    font-size: 10pt;
  }

  .carousel_weekly_text_descr {
    font-size: 8pt;
    line-height: 1.5;
    padding-right: 10px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 5;
    overflow: hidden;
  }

  .carousel_left_container {
    width: 35%;
    margin-left: 0px;
  }

  .carousel_left_title {
    font-size: 12pt;
    -webkit-line-clamp: 1;
  }

  .carousel_left_author {
    font-size: 10pt;
  }

  .carousel_left_desc {
    font-size: 8pt;
    line-height: 1.5;
    margin-right: 0px;
    -webkit-line-clamp: 9;
  }

  .carousel_image_container {
    margin-left: 25px;
  }

  .carousel_right_container {
    display: none;
  }


  .rank_books_container {
    display: flex;
    width: 95%;
  }

  .rank_name {
    font-size: 10pt;
    width: 90px;
    color: white;
  }

  .rank_image img {
    height: 65px;
    width: 40.625px;
    margin-right: 5px;
  }

  .rank_rank {
    margin-right: 5px;
    font-size: 5pt;
  }

  .rank_info {
    font-size: 5pt;
    width: 60px;
  }

  .rank_info .rank_title {
    font-size: 6pt;
    width: 60px;
  }

  .rank_info .rank_author {
    font-size: 3pt;
    width: 60px;
  }

  .rank_info .rank_score {
    font-size: 5pt;
  }
}
</style>

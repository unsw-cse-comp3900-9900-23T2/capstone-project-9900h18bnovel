<script setup>
import {
  CaretTop,
  UserFilled,
  CaretBottom,
  Warning,
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { getItemColor } from '@/utils'
</script >

<script>
export default {
  data() {
    return {
      update_book_info: {},
      isLoginVisible: false,
      verImage: '',
      sessionId: '',
      rankBooks: null,
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
    window.scrollTo(0, 0);
    const path = this.$route.path;
    path === "/newestrank" ? this.isNewestRank = true : this.isNewestRank = false;
    path === "/updaterank" ? this.isUpdateRank = true : this.isUpdateRank = false;
    path === "/clickrank" ? this.isClickRank = true : this.isClickRank = false;
    this.getRanks();
    setTimeout(() => {
      this.loading = false;
      this.showNewestUpdatePage = true;
    }, 500);
  },
  methods: {
    async getRanks() {
      const whichrank = this.isNewestRank ? "newest_rank" : this.isClickRank ? "visit_rank" : this.isUpdateRank ? "update_rank" : null;
      await axios.get("/api/front/book/" + whichrank)
        .then(response => {
          const data = response.data;
          this.rankBooks = data.data.map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
        })
        .catch(error => {
          console.error(error);
        });
    },

    load() {
      this.loadMore = true;
      setTimeout(() => {
        this.loadMore = false;
        this.count += 5;
        if (this.count >= this.rankBooks.length + 5) {
          ElMessage.error("There is no more books");
        }
      }, 500);
    },

    goBookInfo(bookId) {
      this.$router.push(`/bookInfo/${bookId}`);
    },

  },
  computed: {
    filteredBooks() {
      return this.rankBooks.slice(0, this.count);
    },
  },
  watch: {
    '$route'(to) {
      if (to.path === "/newestrank") {
        this.isNewestRank = true;
        this.isUpdateRank = false;
        this.isClickRank = false;
        this.getRanks();
      } else if (to.path === "/updaterank") {
        this.isNewestRank = false;
        this.isUpdateRank = true;
        this.isClickRank = false;
        this.getRanks();
      } else if (to.path === "/clickrank") {
        this.isNewestRank = false;
        this.isUpdateRank = false;
        this.isClickRank = true;
        this.getRanks();
      }
      this.loading = true;
      this.showNewestUpdatePage = false;
      setTimeout(() => {
        this.loading = false;
        this.showNewestUpdatePage = true;
      }, 500);
    }
  },
}
</script>

<template>
  <div v-infinite-scroll="load">
    <div v-loading.fullscreen="loading" element-loading-spinner=" ">
    </div>
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
          <li v-for="(item, index) in filteredBooks" :key="item.id" class="infinite-list-item"
            @click="goBookInfo(item.id)">
            <div class="update_book_rank" :class="{ 'red': index === 0, 'orange': index === 1, 'green': index === 2 }">
              {{ index < 9 ? '0' + (index + 1) : index + 1 }} </div>
                <img :src="item.picUrl"
                  style=" height: 145px; width:90.63px; border-radius: 5px; box-shadow: 6px 4px 6px rgb(110, 110, 110);" />
                <div class="update_book_item_container">
                  <div
                    style="font-size: 18pt; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1; overflow: hidden;">
                    {{ item.bookName }}</div>
                  <div style="font-size: 11pt;">{{ item.authorName }}</div>
                  <div
                    style="font-size: 9pt; margin-top: 3px; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3; overflow: hidden;">
                    {{ item.bookDesc }}</div>
                  <el-tag style="bottom: 12px; position: absolute;" :style="getItemColor(item.categoryName)"
                    :color="getItemColor(item.categoryName)" effect="plain">{{
                      item.categoryName
                    }}</el-tag>
                </div>
                <div class="update_book_reviews_container">
                  <div>Last update:{{ item.lastChapterUpdateTime }}</div>
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
            v-loading="loadMore" element-loading-spinner=" ">
            <div v-if="count >= rankBooks.length + 5" @click="scrollToTop" style="text-align: center; width: 100%;">
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
              <div @click="load" class="clickScroll">
                <el-icon>
                  <CaretBottom />
                </el-icon>
                MORE
                <el-icon>
                  <CaretBottom />
                </el-icon>
              </div>
            </h3>
          </div>
        </ul>
      </div>
    </div>
  </div>
</template>


<style>
.update_book_rank {
  font-size: 14pt;
  font-weight: bold;
  width: 90px;
  text-align: center;
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
  cursor: pointer;
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
</style>
<style>
@media screen and (max-width:431px) {
  .infinite-list {
    width: 100vw;
    min-width: 100vw;
    padding: 0;
  }

  .update_book_item_container {
    height: 100%;
    width: 60%;
    padding-left: 10px;
    padding-right: 0px;
  }

  .update_book_reviews_container {
    display: none;
  }

  .update_book_rank {
    width: auto;
    margin-right: 5px;
  }
}
</style>
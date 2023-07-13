<!-- 目前存在的问题：
1. 每次调用load(),pageNum会加一
2. 分辨率
3. back to top -->
<script setup>
import Global_Header from './Global_Header.vue';
import Global_Footer from './Global_Footer.vue';
import Global_Nav from './Global_Nav.vue';
import Login from './Auth_Page.vue';
import { ElMessage } from 'element-plus';
import {
  StarFilled,
  CaretTop,
  CaretBottom,
  View,
  Document,
  CollectionTag,
  Warning,
} from '@element-plus/icons-vue';
const marks = ({
  0: '0',
  1: '10k',
  2: '20k',
  3: '30k',
  4: '40k',
  5: '50k',
  6: '60k',
  7: '70k',
  8: '80k',
  9: '90k',
  10: '100k+'
})
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
import { Linter } from 'eslint';
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `

</script>

<script>
export default {
  data() {
    return {
      genreChecked: [false, false, false, false, false, false, false],
      bookstatusChecked: [false, false],
      sortChecked: [true, false, false, false],
      dirChecked: [false, false],
      statusChecked: [false, false],
      updateChecked: [false, false],
      isLoginVisible: false,
      verImage: '',
      sessionId: '',
      novels: null,
      loading: true,
      showAllNovelPage: false,
      loadMore: false,
      clickedLoad: false,

      keyword: null,
      workDirection: null,
      categoryId: null,
      bookStatus: null,
      wordCount: [0, 10],
      wordCountMin: null,
      wordCountMax: null,
      updateTimeMin: null,
      sort: "visit_count",
      pageNum: 1,
      pageSize: 12,
    }
  },
  components: {
    Global_Footer,
    Global_Header,
    Global_Nav
  },
  watch: {
    '$store.getters.getCurrentURL'(newURL) {
      this.updateAllNovelsRoute(newURL);
    },
  },
  mounted() {
    this.keyword = this.$store.getters.getSearchInput ? this.$store.getters.getSearchInput : null;
    this.getResultBooks();
    setTimeout(() => {
      this.loading = false;
      this.showAllNovelPage = true;
    }, 500);
  },
  methods: {
    clearSearch() {
      this.keyword = null;
      this.$store.dispatch('clearSearchInput');
      this.pageNum = 1;
      this.pageSize = 12;
      this.clickedLoading();
    },

    clearFilters() {
      this.genreChecked = [false, false, false, false, false, false, false];
      this.bookstatusChecked = [false, false];
      this.dirChecked = [false, false];
      this.statusChecked = [false, false];
      this.updateChecked = [false, false];
      this.workDirection = null;
      this.categoryId = null;
      this.bookStatus = null;
      this.wordCount = [0, 10];
      this.wordCountMin = null;
      this.wordCountMax = null;
      this.updateTimeMin = null;
      this.pageNum = 1;
      this.pageSize = 12;
      this.clickedLoading();
    },

    clearAll() {
      this.keyword = null;
      this.genreChecked = [false, false, false, false, false, false, false];
      this.bookstatusChecked = [false, false];
      this.dirChecked = [false, false];
      this.statusChecked = [false, false];
      this.updateChecked = [false, false];
      this.workDirection = null;
      this.categoryId = null;
      this.bookStatus = null;
      this.wordCount = [0, 10];
      this.wordCountMin = null;
      this.wordCountMax = null;
      this.updateTimeMin = null;
      this.pageNum = 1;
      this.pageSize = 12;
      this.$store.dispatch('clearSearchInput');
      this.clickedLoading();
    },

    handleSearch(searchInput) {
      this.clickedLoading();
      this.keyword = searchInput;
    },

    updateAllNovelsRoute(newURL) {
      this.$router.replace(`/allnovels/${newURL}`);
    },

    genreonChange(index) {
      this.genreChecked[index]
        ? (() => {
          this.genreChecked[index] = false;
          this.categoryId = null;
          this.clickedLoading();
        })()
        : (() => {
          this.genreChecked = this.genreChecked.map(() => false);
          this.genreChecked[index] = true;
          index === 0 ? this.categoryId = index + 1 : null;
          index === 1 ? this.categoryId = index + 1 : null;
          index === 2 ? this.categoryId = index + 1 : null;
          index === 3 ? this.categoryId = index + 1 : null;
          index === 4 ? this.categoryId = index + 1 : null;
          index === 5 ? this.categoryId = index + 1 : null;
          index === 6 ? this.categoryId = index + 1 : null;
          this.clickedLoading();
        })()
    },

    // 如果是Popular, 就传visit_count, 如果是Collection就传collect_count, 如果是Score就传score, 如果是更新时间就传last_chapter_update_time
    sortonChange(index) {
      !this.sortChecked[index]
        ? (() => {
          this.sortChecked = this.sortChecked.map(() => false);
          this.sortChecked[index] = true;
          index === 0 ? this.sort = "visit_count" : null;
          index === 1 ? this.sort = "collect_count" : null;
          index === 2 ? this.sort = "score" : null;
          index === 3 ? this.sort = "last_chapter_update_time" : null;
          this.clickedLoading();
        })()
        : null;
    },

    dironChange(index) {
      this.dirChecked[index]
        ? (() => {
          this.dirChecked[index] = false;
          this.workDirection = null;
          this.clickedLoading();
        })()
        : (() => {
          this.dirChecked = this.dirChecked.map(() => false);
          this.dirChecked[index] = true;
          this.workDirection = index + 1;
          this.clickedLoading();
        })();
    },

    statusonChange(index) {
      this.statusChecked[index]
        ? (() => {
          this.statusChecked[index] = false;
          this.bookStatus = null;
          this.clickedLoading();
        })()
        : (() => {
          this.statusChecked = this.statusChecked.map(() => false);
          this.statusChecked[index] = true;
          this.bookStatus = index === 0 ? 0 : 1;
          this.clickedLoading();
        })();
    },

    updateonChange(index) {
      this.updateChecked[index]
        ? (() => {
          this.updateChecked[index] = false;
          this.updateTimeMin = null;
          this.clickedLoading();
        })()
        : (() => {
          this.updateChecked = this.updateChecked.map(() => false);
          this.updateChecked[index] = true;
          const currentDate = new Date();
          index === 0
            ?
            (() => {
              const oneWeekAgo = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() - 7);
              this.updateTimeMin = this.formatDate(oneWeekAgo);
            })()
            :
            index === 1
              ?
              (() => {
                const oneMonthAgo = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, currentDate.getDate());
                this.updateTimeMin = this.formatDate(oneMonthAgo);
              })()
              :
              index === 2
                ?
                (() => {
                  const oneYearAgo = new Date(currentDate.getFullYear() - 1, currentDate.getMonth(), currentDate.getDate());
                  this.updateTimeMin = this.formatDate(oneYearAgo);
                })()
                :
                null;
          this.clickedLoading();
        })();
    },

    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },

    clickedLoading() {
      this.clickedLoad = true;
      setTimeout(() => {
        this.getResultBooks();
        this.clickedLoad = false;
      }, 500);
    },

    wordcountonchange() {
      this.wordCountMin = this.wordCount[0] !== 0 ? this.wordCount[0] * 10000 : null;
      this.wordCountMax = this.wordCount[1] !== 10 ? this.wordCount[1] * 10000 : null;
      this.clickedLoading();
    },

    async getResultBooks() {
      let url = "http://localhost:8888/api/front/book/books?";
      url += this.keyword !== null ? "keyword=" + this.keyword + "&" : "";
      url += this.workDirection !== null ? "workDirection=" + this.workDirection + "&" : "";
      url += this.categoryId !== null ? "categoryId=" + this.categoryId + "&" : "";
      url += this.bookStatus !== null ? "bookStatus=" + this.bookStatus + "&" : "";
      url += this.wordCountMin !== null ? "wordCountMin=" + this.wordCountMin + "&" : "";
      url += this.wordCountMax !== null ? "wordCountMax=" + this.wordCountMax + "&" : "";
      url += this.updateTimeMin !== null ? "updateTimeMin=" + this.updateTimeMin + "&" : "";
      url += this.sort !== null ? "sort=" + this.sort + "&" : "";
      url += this.pageNum !== null ? "pageNum=" + this.pageNum + "&" : "";
      url += this.pageSize !== null ? "pageSize=" + this.pageSize + "&" : "";

      url = url.slice(0, -1);
      try {
        const response = await fetch(url, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          // console.log(data.data.list)
          this.novels = data.data.list;
          console.log(data.data.list)
          this.$store.dispatch('updateCurrentURL', url.substring(url.indexOf('books')));
        } else {
          console.log("Test");
        }
      } catch (error) {
        console.error(error);
      }
    },

    load() {
      this.loadMore = true;
      setTimeout(() => {
        if (this.pageNum >= this.novels.length) {
          ElMessage.error("There is no more books");
        } else {
          this.pageNum += 12;
          this.getResultBooks();
          this.loadMore = false;
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
    },

  },
  computed: {
    simplifiedWordCount() {
      return function (word) {
        const wordCount = parseInt(word);
        if (wordCount >= 10000) {
          const simplified = Math.floor(wordCount / 1000) + 'k+';
          return simplified;
        } else {
          return wordCount.toString();
        }
      };
    },
  }


}
</script>

<template>
  <div :class="{ 'blur': isLoginVisible }">
    <div v-infinite-scroll="load">
      <!-- <div class="infinite-body"> -->
      <Global_Header @handleSearch="handleSearch" @logout="logout" @showLogin="showLogin"
        @closeLoginBox="closeLoginBox" />
      <Global_Nav @clearFilters="clearFilters" />
      <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
        element-loading-background="rgba(255, 255, 255, 255)"
        style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
      <div v-if="showAllNovelPage">
        <div style="display: flex; justify-content: center;">
          <div class="allnovel_body">
            <div class="picker_content">
              <div class="filter_container">
                <div class="picker_left">
                  <div class="genresofnovels">
                    <h2>
                      Genres of Novels
                      <el-popover placement="right" :width="220" trigger="hover"
                        content="Genres of Novels refers to the different types or categories of novels">
                        <template #reference>
                          <el-icon style="font-size: 10pt;">
                            <Warning />
                          </el-icon>
                        </template>
                      </el-popover>
                    </h2>
                    <el-divider />
                    <div style="width: 100%;">
                      <el-check-tag class="tags" :checked="genreChecked[0]"
                        @click="genreonChange(0)">Action</el-check-tag>
                      <el-check-tag class="tags" :checked="genreChecked[1]"
                        @click="genreonChange(1)">Romance</el-check-tag>
                      <el-check-tag class="tags" :checked="genreChecked[2]"
                        @click="genreonChange(2)">Fantasy</el-check-tag>
                      <el-check-tag class="tags" :checked="genreChecked[3]"
                        @click="genreonChange(3)">Mystery</el-check-tag>
                      <el-check-tag class="tags" :checked="genreChecked[4]"
                        @click="genreonChange(4)">Horror</el-check-tag>
                      <el-check-tag class="tags" :checked="genreChecked[5]"
                        @click="genreonChange(5)">Thriller</el-check-tag>
                      <el-check-tag class="tags" :checked="genreChecked[6]" @click="genreonChange(6)">Drama</el-check-tag>
                    </div>
                  </div>
                  <div class="bookdirection">
                    <h2>Book direction
                      <el-popover placement="right" :width="220" trigger="hover"
                        content="Book Direction refers to the orientation of a book's content, particularly in relation to the reading direction">
                        <template #reference>
                          <el-icon style="font-size: 10pt;">
                            <Warning />
                          </el-icon>
                        </template>
                      </el-popover>
                    </h2>
                    <el-divider />
                    <div style="width: 100%;">
                      <el-check-tag class="tags" :checked="dirChecked[0]" @click="dironChange(0)">Male Lead</el-check-tag>
                      <el-check-tag class="tags" :checked="dirChecked[1]" @click="dironChange(1)">Female
                        Lead</el-check-tag>
                    </div>
                  </div>
                </div>
                <div class="picker_right">
                  <div class="filterby">
                    <h2>Filter By
                      <el-popover placement="right" :width="220" trigger="hover"
                        content="Filter By refers to the selection of book status, update time and word count based on specific criteria">
                        <template #reference>
                          <el-icon style="font-size: 10pt;">
                            <Warning />
                          </el-icon>
                        </template>
                      </el-popover>
                    </h2>
                    <el-divider />
                    <div
                      style="width: 100%; display: flex; flex-wrap: wrap; justify-content: space-between;  margin-top: -20px;">
                      <div style="width: 49%;">
                        <h4 style="color: rgb(117, 117, 117);">Book Status</h4>
                        <!-- 0-Continued 1-Completed -->

                        <el-check-tag class="tags" :checked="statusChecked[0]"
                          @click="statusonChange(0)">Ongoing</el-check-tag>
                        <el-check-tag class="tags" :checked="statusChecked[1]"
                          @click="statusonChange(1)">Completed</el-check-tag>
                      </div>
                      <div style="width: 49%;">
                        <h4 style="color: rgb(117, 117, 117);">Update Time (&le;)</h4>
                        <el-check-tag class="tags" :checked="updateChecked[0]" @click="updateonChange(0)">A
                          week</el-check-tag>
                        <el-check-tag class="tags" :checked="updateChecked[1]" @click="updateonChange(1)">A
                          month</el-check-tag>
                        <el-check-tag class="tags" :checked="updateChecked[2]" @click="updateonChange(2)">A
                          year</el-check-tag>
                      </div>
                    </div>
                    <div style="width: 100%; margin: auto;">
                      <h4 style="color: rgb(117, 117, 117);">Word Count</h4>
                      <el-slider v-model="wordCount" range :marks="marks" show-stops :show-tooltip="false" :max="10"
                        style="width: 98%; margin: auto;" @change="wordcountonchange()" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- <h2 style="margin-top: -30px;">Results</h2> -->
            <el-divider />
            <div style="display: flex; justify-content: space-between; position: relative;">
              <div class="sortby">
                <!-- 如果是Popular, 就传visit_count, 如果是Collection就传collect_count,如果是Score就传score,如果是更新时间就传last_chapter_update_time -->
                <span style="padding-bottom: 4px; display: flex; align-items: center; font-size: 14pt;"><b>Sort
                    By</b></span>&nbsp;&nbsp;&nbsp;
                <el-check-tag class="tags" :checked="sortChecked[0]" @click="sortonChange(0)">Popular</el-check-tag>
                <el-check-tag class="tags" :checked="sortChecked[1]" @click="sortonChange(1)">Most
                  Collections</el-check-tag>
                <el-check-tag class="tags" :checked="sortChecked[2]" @click="sortonChange(2)">Score</el-check-tag>
                <el-check-tag class="tags" :checked="sortChecked[3]" @click="sortonChange(3)">Time
                  updated</el-check-tag>
              </div>
              <div style="display: flex;">
                <div style="display: flex; margin-top: -8px; height:fit-content;">
                  <el-button :disabled="!keyword" text type="primary" @click="clearSearch">
                    Clear search
                  </el-button>
                  <el-button
                    :disabled="!genreChecked.some(checked => checked) && !bookstatusChecked.some(checked => checked) && !dirChecked.some(checked => checked) && !statusChecked.some(checked => checked) && !updateChecked.some(checked => checked) && !(wordCount[0] !== 0 || wordCount[1] !== 10)"
                    text type="primary" @click="clearFilters">
                    Clear filter
                  </el-button>
                  <el-button
                    :disabled="!keyword && !genreChecked.some(checked => checked) && !bookstatusChecked.some(checked => checked) && !dirChecked.some(checked => checked) && !statusChecked.some(checked => checked) && !updateChecked.some(checked => checked) && !(wordCount[0] !== 0 || wordCount[1] !== 10)"
                    text type="primary" @click="clearAll">
                    Clear all
                  </el-button>
                </div>
                <div style="margin-top: -2px; font-size: 14pt; width: 110px; text-align: right;">
                  <b>{{
                    novels.length }} {{ novels.length === 0 ? "Novel" : (novels.length === 1 ? "Novel" : "Novels") }}
                  </b>
                </div>
              </div>
            </div>
            <div class="novels_container" v-loading.fullscreen.lock="clickedLoad" :element-loading-spinner="svg"
              element-loading-svg-view-box="0, 5, 30, 40">
              <h3 v-if="this.keyword" style="width: 100%;">Search result for "{{ this.keyword }}"</h3>
              <h1 v-if="novels.length === 0" style="width: 100%; text-align: center;">No Results</h1>
              <div v-for="(item) in novels" :key="item.bookName" class="each_novel_container">
                <el-card shadow="hover" :body-style="{ padding: '10px' }">
                  <div class="each_novel_card">
                    <div>
                      <img :src="item.picUrl" class="novel_img" />
                    </div>
                    <div style="display: flex; flex-direction: column; margin-left: 20px;">
                      <span
                        style="font-size: 12pt; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden; margin-bottom: 5px;">
                        <b>{{ item.bookName }}</b>
                      </span>
                      <span style="font-size: 11pt;">{{ item.authorName }}</span>
                      <span
                        style="font-size: 10pt; margin-top: 10px; margin-right: 10px; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">{{
                          item.bookDesc }}</span>
                      <div style="bottom: 40px; position: absolute;">
                        <el-tag style="margin-right: 5px;">{{ item.categoryName }}</el-tag>
                      </div>
                      <div style="margin-left: 2px; bottom: 20px; position: absolute; display: flex;">
                        Last update: {{ item.lastChapterUpdateTime }}
                      </div>
                      <div style="bottom: 0; position: absolute; display: flex;">
                        <div style="margin-top: 1px;">
                          <el-icon>
                            <StarFilled />
                          </el-icon>
                        </div>
                        {{ item.score }}
                        &nbsp;&nbsp;&nbsp;
                        <div style="margin-top: 1px;">
                          <el-icon>
                            <Document />
                          </el-icon>
                        </div>
                        {{ simplifiedWordCount(item.wordCount) }}
                        &nbsp;&nbsp;&nbsp;
                        <div style="margin-top: 1px;">
                          <el-icon>
                            <View />
                          </el-icon>
                        </div>
                        {{ simplifiedWordCount(item.visitCount) }}
                        &nbsp;&nbsp;&nbsp;
                        <div style="margin-top: 1px;">
                          <el-icon>
                            <CollectionTag />
                          </el-icon>
                        </div>
                        {{ simplifiedWordCount(item.collectCount) }}
                      </div>
                    </div>
                  </div>
                </el-card>
              </div>
            </div>
          </div>
        </div>
        <div style="height: 157px; width: 60%; display:flex; justify-content: center; margin: auto;" v-loading="loadMore"
          :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
          element-loading-background="rgba(255, 255, 255, 255)">
          <div v-if="pageNum >= novels.length + 12" @click="scrollToTop" style="width: 100%; text-align: center;">
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
.blur {
  filter: blur(5px);
  pointer-events: none;
}

.allnovel_body {
  min-width: 1152px;
  width: 1152px;
}

.picker_content {
  width: 100%;
}

.filter_container {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.picker_left {
  width: 45%;
}

.picker_right {
  width: 54%;
}

.filterby {
  width: 100%;
}

.sortby {
  width: 100%;
  display: flex;
  margin-top: -6px;
  margin-bottom: 8px;
}

.tags {
  margin-right: 8px;
  margin-bottom: 6px;
}

.novels_container {
  display: flex;
  flex-wrap: wrap;
}

.each_novel_container {
  width: 33%;
  height: 200px;
  margin-right: 3.82px;
  margin-bottom: 3.82px;
}

.each_novel_card {
  width: 100%;
  height: 96%;
  object-fit: contain;
  display: flex;
  position: relative;
}

.novel_img {
  height: 175px;
  border-radius: 5px;
  transform: scale(1);
  transition: transform 0.3s ease;
}

.novel_img:hover {
  transform: scale(1.03);
}
</style>

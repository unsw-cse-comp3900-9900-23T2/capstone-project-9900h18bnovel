<script setup>
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { getItemColor } from '@/utils.js';
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
</script>

<script>
export default {
  data() {
    return {
      genreChecked: [false, false, false, false, false, false, false, false, false, false, false],
      bookstatusChecked: [false, false],
      sortChecked: [true, false, false, false],
      dirChecked: [false, false],
      statusChecked: [false, false],
      updateChecked: [false, false],
      isLoginVisible: false,
      verImage: '',
      sessionId: '',
      novels: null,
      loading: false,
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
      pageNum: 12,
      totalNum: null,
      isEnd: false,
    }
  },
  watch: {
    '$store.getters.getCurrentURL': {
      handler(newURL) {
        this.updateAllNovelsRoute(newURL);
      },
      immediate: true,
    },
    '$store.getters.getSearchInput'(searchInput) {
      this.keyword = searchInput;
      this.clickedLoading();
    },
  },
  mounted() {
    this.keyword = this.$store.getters.getSearchInput ? this.$store.getters.getSearchInput : null;
    this.pageNum = 12;
    this.getResultBooks();
    this.loading = true;
    setTimeout(() => {
      this.showAllNovelPage = true;
      this.loading = false;
    }, 500);
  },
  methods: {
    clearSearch() {
      this.keyword = null;
      this.$store.dispatch('clearSearchInput');
      this.clickedLoading();
    },

    clearFilters() {
      this.genreChecked = [false, false, false, false, false, false, false, false, false, false, false];
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
      this.clickedLoading();
    },

    clearAll() {
      this.keyword = null;
      this.genreChecked = [false, false, false, false, false, false, false, false, false, false, false];
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
      this.$store.dispatch('clearSearchInput');
      this.clickedLoading();
    },

    handleSearch(searchInput) {
      this.clickedLoading();
      this.keyword = searchInput;
    },

    updateAllNovelsRoute(newURL) {
      this.$router.replace(`/browse/${newURL}`);
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
          index === 10 ? this.categoryId = 8 : index >= 7 ? this.categoryId = index + 2 : this.categoryId = index + 1;
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
      this.pageNum = 12;
      this.isEnd = false;
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
      let url = "/api/front/book/books?";
      url += this.keyword !== null ? "keyword=" + this.keyword + "&" : "";
      url += this.workDirection !== null ? "workDirection=" + this.workDirection + "&" : "";
      url += this.categoryId !== null ? "categoryId=" + this.categoryId + "&" : "";
      url += this.bookStatus !== null ? "bookStatus=" + this.bookStatus + "&" : "";
      url += this.wordCountMin !== null ? "wordCountMin=" + this.wordCountMin + "&" : "";
      url += this.wordCountMax !== null ? "wordCountMax=" + this.wordCountMax + "&" : "";
      url += this.updateTimeMin !== null ? "updateTimeMin=" + this.updateTimeMin + "&" : "";
      url += this.sort !== null ? "sort=" + this.sort + "&" : "";
      url += this.pageNum !== null ? "pageNum=" + this.pageNum + "&" : "";

      url = url.slice(0, -1);
      await axios.get(url)
        .then(response => {
          const data = response.data;
          this.novels = data.data;
          this.totalNum = data.data[0] ? data.data[0].totalNum : 0;
          this.$store.dispatch('setCurrentURL', url.substring(url.indexOf('books')));
        })
        .catch(error => {
          console.error(error);
        });
    },

    load() {
      if (!this.loadMore) {
        this.loadMore = true;
        this.pageNum += 12;
        setTimeout(() => {
          if (this.pageNum >= this.totalNum) {
            ElMessage.error("There is no more books behind");
            this.isEnd = true;
            this.pageNum = this.totalNum;
          }
          this.getResultBooks();
          this.loadMore = false;
        }, 500);
      }
    },

    goBookInfo(bookId) {
      this.$router.push(`/bookInfo/${bookId}`);
    },
  },
  computed: {
    simplifiedWordCount() {
      return function (word) {
        const wordCount = parseInt(word);
        if (wordCount >= 10000) {
          const simplified = Math.floor(wordCount / 1000) + 'k';
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
  <div v-infinite-scroll="load" :infinite-scroll-disabled="isEnd">
    <div v-loading.fullscreen="loading" element-loading-spinner=" ">
    </div>
    <div v-if="showAllNovelPage">
      <div style="display: flex; justify-content: center;">
        <div class="allnovel_body">
          <div class="picker_content">
            <div class="filter_container">
              <div class="picker_left">
                <div class="genresofnovels">
                  <h2>
                    Genres of Novels
                    <el-popover placement="top-start" :width="220" trigger="hover"
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
                    <el-check-tag class="tags Action" :checked="genreChecked[0]"
                      @click="genreonChange(0)">Action</el-check-tag>
                    <el-check-tag class="tags Romance" :checked="genreChecked[1]"
                      @click="genreonChange(1)">Romance</el-check-tag>
                    <el-check-tag class="tags Fantasy" :checked="genreChecked[2]"
                      @click="genreonChange(2)">Fantasy</el-check-tag>
                    <el-check-tag class="tags Mystery" :checked="genreChecked[3]"
                      @click="genreonChange(3)">Mystery</el-check-tag>
                    <el-check-tag class="tags Horror" :checked="genreChecked[4]"
                      @click="genreonChange(4)">Horror</el-check-tag>
                    <el-check-tag class="tags Thriller" :checked="genreChecked[5]"
                      @click="genreonChange(5)">Thriller</el-check-tag>
                    <el-check-tag class="tags Drama" :checked="genreChecked[6]"
                      @click="genreonChange(6)">Drama</el-check-tag>
                    <el-check-tag class="tags Adventure" :checked="genreChecked[7]"
                      @click="genreonChange(7)">Adventure</el-check-tag>
                    <el-check-tag class="tags Humor" :checked="genreChecked[8]"
                      @click="genreonChange(8)">Humor</el-check-tag>
                    <el-check-tag class="tags Poetry" :checked="genreChecked[9]"
                      @click="genreonChange(9)">Poetry</el-check-tag>
                    <el-check-tag class="tags Other" :checked="genreChecked[10]"
                      @click="genreonChange(10)">Other</el-check-tag>
                  </div>
                </div>
                <div class="bookdirection">
                  <h2>Book direction
                    <el-popover placement="top-start" :width="220" trigger="hover"
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
                    <el-popover placement="top-start" :width="220" trigger="hover"
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
                      <h4 style="color: rgb(117, 117, 117);">Update Time</h4>
                      <el-check-tag class="tags" :checked="updateChecked[0]" @click="updateonChange(0)">A
                        week</el-check-tag>
                      <el-check-tag class="tags" :checked="updateChecked[1]" @click="updateonChange(1)">A
                        month</el-check-tag>
                      <el-check-tag class="tags" :checked="updateChecked[2]" @click="updateonChange(2)">A
                        year</el-check-tag>
                      <el-date-picker v-model="updateTimeMin" type="date" placeholder="Pick a Date"
                        value-format="YYYY-MM-DD" @change="clickedLoading" style="width: 180px;" />
                    </div>
                  </div>
                  <div style="width: 100%; margin: auto; " class="slider">
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
          <div class="sort_row">
            <div class="sortby">
              <!-- 如果是Popular, 就传visit_count, 如果是Collection就传collect_count,如果是Score就传score,如果是更新时间就传last_chapter_update_time -->
              <div class="sortby_word"><b>Sort By</b></div>
              <div>
                <el-check-tag class="tags" :checked="sortChecked[0]" @click="sortonChange(0)">Popular</el-check-tag>
                <el-check-tag class="tags" :checked="sortChecked[1]" @click="sortonChange(1)">Most
                  Collections</el-check-tag>
                <el-check-tag class="tags" :checked="sortChecked[2]" @click="sortonChange(2)">Score</el-check-tag>
                <el-check-tag class="tags" :checked="sortChecked[3]" @click="sortonChange(3)">Time
                  updated</el-check-tag>
              </div>
            </div>
            <div class="clear-totalNum">
              <div style="display: flex; margin-top: -8px; height:fit-content; justify-content: space-between;">
                <el-button :disabled="!keyword" text type="danger" @click="clearSearch">
                  Clear search
                </el-button>
                <el-button
                  :disabled="!genreChecked.some(checked => checked) && !bookstatusChecked.some(checked => checked) && !dirChecked.some(checked => checked) && !statusChecked.some(checked => checked) && !updateChecked.some(checked => checked) && !(wordCount[0] !== 0 || wordCount[1] !== 10) && !updateTimeMin"
                  text type="danger" @click="clearFilters">
                  Clear filter
                </el-button>
                <el-button
                  :disabled="!keyword && !genreChecked.some(checked => checked) && !bookstatusChecked.some(checked => checked) && !dirChecked.some(checked => checked) && !statusChecked.some(checked => checked) && !updateChecked.some(checked => checked) && !(wordCount[0] !== 0 || wordCount[1] !== 10)"
                  text type="danger" @click="clearAll">
                  Clear all
                </el-button>
              </div>
              <div style="margin-top: -2px; font-size: 14pt; width: 110px; text-align: right;">
                <b>{{ totalNum }} {{ novels.length <= 1 ? "book" : "books" }} </b>
              </div>
            </div>
          </div>
          <div class="novels_container" v-loading="clickedLoad" element-loading-spinner=" ">
            <h3 v-if="keyword" style="width: 100%;">Search result for "{{ keyword }}"</h3>
            <h1 v-if="novels.length === 0" style="width: 100%; text-align: center;">No Results</h1>
            <div v-for="(item) in novels" :key="item.bookName" class="each_novel_container">
              <el-card shadow="hover" :body-style="{ padding: '10px' }">
                <div class="each_novel_card">
                  <div>
                    <img :src="item.picUrl" class="novel_img" @click="goBookInfo(item.id)" />
                  </div>
                  <div class="each-card-right">
                    <span class="novel_title" @click="goBookInfo(item.id)">
                      <b>{{ item.bookName }}</b>
                    </span>
                    <span style="font-size: 10pt;">{{ item.authorName }}</span>
                    <span
                      style="font-size: 9pt; line-height: 1.5; margin-top: 5px; margin-right: 10px; display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">{{
                        item.bookDesc }}</span>
                    <div style="bottom: 40px; position: absolute;">
                      <el-tag effect="plain" :style="getItemColor(item.categoryName)">{{
                        item.categoryName
                      }}</el-tag>
                    </div>
                    <div
                      style="margin-left: 2px; bottom: 20px; position: absolute; display: flex; overflow: hidden; height: 17px;">
                      Last update: {{ item.lastChapterUpdateTime }}
                    </div>
                    <div class="book-comm">
                      <div style="display: flex; align-items: center;">
                        <el-icon>
                          <StarFilled />
                        </el-icon>
                        {{ item.score }}
                      </div>
                      <div style="display: flex; align-items: center;">
                        <el-icon>
                          <Document />
                        </el-icon>
                        {{ simplifiedWordCount(item.wordCount) }}
                      </div>
                      <div style="display: flex; align-items: center;">
                        <el-icon>
                          <View />
                        </el-icon>
                        {{ simplifiedWordCount(item.visitCount) }}
                      </div>
                      <div style="display: flex; align-items: center;">
                        <el-icon>
                          <CollectionTag />
                        </el-icon>
                        {{ simplifiedWordCount(item.collectCount) }}
                      </div>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </div>
      </div>
      <div style="height: 157px; width: 60%; display:flex; justify-content: center; margin: auto;" v-loading="loadMore"
        element-loading-spinner=" ">
        <div v-if="pageNum >= totalNum" style="width: 100%; text-align: center;">
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
    </div>
  </div>
</template>


<style>
.book-comm {
  bottom: 0;
  position: absolute;
  width: 228px;
  display: flex;
  justify-content: space-between;
}

.clear-totalNum {
  display: flex;
}

.clickScroll:hover {
  cursor: pointer;
}

.slider .el-slider {
  --el-slider-button-wrapper-offset: -9px
}

.Action.el-check-tag.is-checked {
  background-color: #b3281a;
  color: white;
}

.Romance.el-check-tag.is-checked {
  background-color: #e33939;
  color: white;
}

.Fantasy.el-check-tag.is-checked {
  background-color: #24bf37;
  color: white;
}

.Mystery.el-check-tag.is-checked {
  background-color: #4e6b1f;
  color: white;
}

.Horror.el-check-tag.is-checked {
  background-color: #6200a3;
  color: white;
}

.Thriller.el-check-tag.is-checked {
  background-color: #003da3;
  color: white;
}

.Drama.el-check-tag.is-checked {
  background-color: #ed7651;
  color: white;
}

.Adventure.el-check-tag.is-checked {
  background-color: #ff8f00;
  color: white;
}

.Humor.el-check-tag.is-checked {
  background-color: #ffc107;
  color: white;
}

.Poetry.el-check-tag.is-checked {
  background-color: #8d6e63;
  color: white;
}

.Other.el-check-tag.is-checked {
  background-color: #607d8b;
  color: white;
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

body .el-tag {
  margin-right: 5px;
  font-size: 10pt;
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
  width: 109.38px;
  height: 175px;
  border-radius: 5px;
  transform: scale(1);
  transition: transform 0.3s ease;
}

.novel_img:hover {
  transform: scale(1.03);
  cursor: pointer;
}

.novel_title {
  font-size: 13pt;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
  margin-bottom: 5px;
}

.novel_title:hover {
  cursor: pointer;
  text-decoration: underline;
}

.sort_row {
  display: flex;
  justify-content: space-between;
  position: relative;
}

.sortby_word {
  padding-bottom: 4px;
  display: flex;
  align-items: center;
  font-size: 14pt;
  margin-right: 10px;
}

.each-card-right {
  display: flex;
  flex-direction: column;
  margin-left: 20px;
}
</style>
<style>
@media screen and (max-width:431px) {
  .allnovel_body {
    width: 100vw;
    min-width: 100vw;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
  }

  .picker_content {
    width: 90%;
  }

  .filter_container {
    width: 100%;
    display: flex;
    flex-direction: column;
  }

  .picker_left {
    width: 100%;
  }

  .picker_right {
    width: 100%;
  }

  .tags {
    margin-right: 2px;
    margin-bottom: 2px;

  }

  .sort_row {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    position: relative;
    width: 90%;
  }

  .sortby_word {
    padding-bottom: 4px;
    display: block;
    align-items: center;
    font-size: 14pt;
  }

  .sortby {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: -6px;
    margin-bottom: 8px;
  }

  .clear-totalNum {
    display: flex;
    flex-direction: column;
  }

  .novels_container {
    display: flex;
    flex-direction: column;
    width: 100%;
  }

  .each_novel_container {
    width: 95vw;
    height: 200px;
    margin: auto;
    margin-right: none;
    margin-bottom: 3.82px;
  }

  .book-comm {
    width: 195px;
  }
}
</style>
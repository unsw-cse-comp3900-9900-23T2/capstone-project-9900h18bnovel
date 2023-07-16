<script setup>
import {
  View,
  Document,
  CollectionTag,
  Clock,
  Plus,
  Reading,
} from '@element-plus/icons-vue';
import { getItemColor } from '../utils'
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `

</script>
<script >
import Global_Footer from './Global_Footer.vue';
export default {
  components: {
    Global_Footer,
  },
  emits: ['showLogin', 'closeLoginBox'],
  data() {
    return {
      isLoginVisible: false,
      VerImage: '',
      sessionId: '',
      loading: true,
      showBookInfo: false,
      isShowComments: true,
      isShowChapters: false,
      clickedLoad: false,
      book: {},
      chapters: [],

    }
  },
  mounted() {
    setTimeout(() => {
      this.getBookInfo();
      this.loading = false;
      this.showBookInfo = true;
    }, 500);
  },
  methods: {
    async getBookInfo() {
      try {
        const response = await fetch(`http://localhost:8888/api/front/book/${this.$route.params.bookId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.book = data.data;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

    chooseComments() {
      this.clickedLoad = true;
      setTimeout(() => {
        this.isShowChapters = false;
        this.isShowComments = true;
        this.clickedLoad = false;
      }, 500);
    },

    chooseChapters() {
      this.clickedLoad = true;
      setTimeout(async () => {
        this.isShowComments = false;
        this.isShowChapters = true;
        try {
          const response = await fetch(`http://localhost:8888/api/front/book/chapter/list?bookId=${this.$route.params.bookId}`, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
            },
          });
          if (response.status == 200) {
            const data = await response.json();
            this.chapters = data.data;
          } else {
            console.log(response.status);
          }
        } catch (error) {
          console.error(error);
        }
        this.clickedLoad = false;
      }, 500);
    },
  }
}
</script>

<template>
  <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
    element-loading-background="rgba(255, 255, 255, 255)"
    style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
  <div v-if="showBookInfo">
    <div style=" width: 100%; height: 440px; background-color: #f5f6fc; position: absolute; z-index: -10;"></div>
    <div class="bookInfoBody">
      <div class="bookDetail">
        <img :src="book.picUrl" style="height: 400px;" />
        <div class="bookWordDetail">
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <h1 style="display: flex; align-items: center;"> {{ book.bookName }}
              <el-tag effect="plain" style="margin-left: 20px;" :style="getItemColor(book.categoryName)">{{
                book.categoryName
              }}</el-tag>
            </h1>
            <el-tag style="font-size: 14pt;" size="large" effect="plain">{{ book.bookStatus === "1" ? "Completed" :
              "Ongoing" }}</el-tag>
          </div>
          <div style="font-size: 14pt; margin-bottom: 15px;"> {{ book.authorName }}</div>
          <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 9; overflow: hidden;"> {{
            book.bookDesc }}</div>
          <div style="bottom: 10px; position: absolute;">
            <div class="bookInfoScore">
              <el-rate v-model="book.score" disabled show-score text-color="#ff9900" size="large"
                score-template="{value}" />&nbsp;({{ book.commentCount }})
            </div>
            <div style="display: flex; font-size: 16pt; align-items: center; margin-top: 10px;">
              <el-icon>
                <Document />
              </el-icon>
              {{ book.wordCount }}
              &nbsp;&nbsp;&nbsp;
              <el-icon>
                <View />
              </el-icon>
              {{ book.visitCount }}
              &nbsp;&nbsp;&nbsp;
              <el-icon>
                <CollectionTag />
              </el-icon>
              {{ book.collectCount }}
              &nbsp;&nbsp;&nbsp;
              <el-icon>
                <Clock />
              </el-icon>
              {{ book.lastChapterUpdateTime }}
              &nbsp;&nbsp;&nbsp;
            </div>
            <div style="margin-top: 10px;">
              <el-button style="font-size: 14pt;" size="large" type="primary" round><el-icon>
                  <Reading />
                </el-icon>
                &nbsp;READ</el-button>
              <el-button style="font-size: 14pt;" size="large" type="primary" round><el-icon>
                  <Plus />
                </el-icon>
                &nbsp;ADD TO COLLECTIONS</el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="chapterDetail">
        <div class="choose">
          <h1 :class="isShowComments ? 'chooseOne' : 'noChoose'" @click="chooseComments">Comments</h1>
          <h1>|</h1>
          <h1 :class="isShowChapters ? 'chooseOne' : 'noChoose'" @click="chooseChapters">Chapters</h1>
        </div>
        <el-divider />
        <div v-loading.fullscreen.lock="clickedLoad" :element-loading-spinner="svg"
          element-loading-svg-view-box="0, 5, 30, 40"></div>
        <div v-if="isShowComments">
          Comments
        </div>
        <div v-if="isShowChapters">
          <div v-if="chapters.length > 0" style="display: flex; flex-wrap: wrap; justify-content: space-between;">
            <div v-for="(item, index) in chapters" :key="index" class="chapters">
              <div style="overflow: hidden; text-overflow: ellipsis; width: 70%; ">
                {{ item.chapterName }}
              </div>
              <div
                style="right: 20px; position: absolute; display: flex; justify-content: right; align-items: center; width: 25%;">
                <el-icon>
                  <Document />
                </el-icon>
                {{ item.chapterWordCount }}
              </div>

            </div>
          </div>
          <div v-else>
            There is no chapter in this book
          </div>
        </div>
      </div>
    </div>
    <Global_Footer />
  </div>
</template>


<style>
.noChoose {
  text-decoration: none;
}

.noChoose:hover {
  cursor: pointer;
  text-decoration: underline;
}

.chooseOne {
  color: black;
}

.chooseOne:hover {
  cursor: pointer;
  text-decoration: underline;
}

.bookInfoBody {
  width: 1152px;
  min-width: 1152px;
  display: flex;
  flex-direction: column;
  margin: auto;
}

.bookDetail {
  width: 100%;
  display: flex;
  background-color: #f5f6fc;
  padding-top: 20px;
  padding-bottom: 20px;
}

.bookWordDetail {
  width: 100%;
  margin-left: 40px;
  position: relative;
  margin-right: 20px;
}

.bookInfoScore {
  display: flex;
  align-items: center;
}

.bookInfoScore .el-rate {
  --el-rate-icon-size: 22pt;
  --el-rate-font-size: 20pt;
}

.choose {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 30%;
  color: #b7b7b7;
  margin-bottom: -20px;
}

.chapters {
  background-color: #f5f6fc;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  font-size: 14pt;
  width: 45%;
  padding: 20px;
  position: relative;
  transition: transform 0.3s ease;
}

.chapters:hover {
  transform: translateY(-4px);
  cursor: pointer;
}
</style>

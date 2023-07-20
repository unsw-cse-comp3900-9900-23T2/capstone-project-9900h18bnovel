<script setup>
import {
  View,
  Document,
  CollectionTag,
  Clock,
  Plus,
  Reading,
  Delete,
  Check,
  MagicStick,
  Setting,
  Sunny,
  Moon,
  ArrowLeft,
  ArrowRight,
  CaretBottom
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { getItemColor } from '../utils'
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `
const marksOnFontSize = ({
  0: 'XS',
  1: 'S',
  2: 'M',
  3: 'L',
  4: 'XL',
})
</script>
<script >
import Global_Footer from './Global_Footer.vue';

export default {
  components: {
    Global_Footer,
  },
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
      isCollected: false,
      collectedBooksId: [],
      book: {},
      chapters: null,
      totalChapters: 0,
      chapterPageNum: 1,
      chapterNum: 0,
      prevChapterId: null,
      requestBody: {
        pageNum: 1,
        pageSize: 5,
        fetchAll: true,
        userId: this.$store.getters.GetUID ? this.$store.getters.GetUID : 0,
        bookId: this.$route.params.bookId,
        commentContent: "a",
        score: 5,
      },
      comments: [],
      userComment: null,
      totalComments: null,
      userCommented: {},
      isEditComment: false,
      drawer: false,
      chapterName: null,
      chapterContent: null,
      settingFontSize: 2,
      settingTheme: false,
      themeColor: "#ffffff",
    }
  },
  watch: {
    '$store.getters.isAuthenticated': {
      handler() {
        this.isCollected = false;
      },
      deep: true
    },
    'chapterPageNum': {
      handler() {
        this.chooseChapters();
      },
      deep: true
    },
    'requestBody.pageNum': {
      handler() {
        this.clickedLoad = true;
        setTimeout(() => {
          this.getAllComments();
          this.clickedLoad = false;
        }, 500);
      },
      deep: true
    },
    '$store.getters.GetUID': {
      handler() {
        this.requestBody.userId = this.$store.getters.GetUID;
        this.clickedLoading();
      },
      deep: true
    },
  },

  mounted() {
    setTimeout(() => {
      this.getBookInfo();
      this.getChapters();
      this.getAllComments();
      this.getUserComment();
      this.getUserCollect();
      this.loading = false;
      this.showBookInfo = true;
    }, 500);
  },

  methods: {
    async getUserCollect() {
      try {
        const response = await fetch(`http://localhost:8888/api/front/user/user_collect?userId=${this.$store.getters.GetUID}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.collectedBooksId = data.data.map(item => item.bookId);
          this.isCollected = this.collectedBooksId.includes(this.$route.params.bookId) ? true : false;
          const thisBook = data.data.find(item => item.bookId === this.$route.params.bookId);
          console.log(thisBook)
          this.prevChapterId = thisBook ? thisBook.preChapterId : null;
          console.log(this.prevChapterId)
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

    async prevChapter() {
      if (this.chapterNum === "1") {
        ElMessage.error("There is no previous chapter");
      } else {
        try {
          const response = await fetch(`http://localhost:8888/api/front/book/pre_chapter_id/${this.chapterId}`, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
            },
          });
          if (response.status == 200) {
            const data = await response.json();
            this.chapterId = data.data;
            this.getContent();
          } else {
            console.log(response.status);
          }
        } catch (error) {
          console.error(error);
        }
      }
    },

    async nextChapter() {
      if (this.chapterNum === this.totalChapters) {
        ElMessage.error("There is no next chapter");
      } else {
        try {
          const response = await fetch(`http://localhost:8888/api/front/book/next_chapter_id/${this.chapterId}`, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
            },
          });
          if (response.status == 200) {
            const data = await response.json();
            this.chapterId = data.data;
            this.getContent();
          } else {
            console.log(response.status);
          }
        } catch (error) {
          console.error(error);
        }
      }
    },

    async addVisit() {
      try {
        await fetch(`http://localhost:8888/api/front/book/add_visit?bookId=${this.$route.params.bookId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        this.getBookInfo();
      } catch (error) {
        console.error(error);
      }
    },

    async getContent() {
      try {
        const response = await fetch(`http://localhost:8888/api/front/book/get_content?chapterId=${this.chapterId}&userId=${this.$store.getters.GetUID}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.chapterContent = data.data.bookContent;
          this.chapterName = data.data.chapterInfo.chapterName;
          this.chapterId = data.data.chapterInfo.id;
          this.prevChapterId = data.data.chapterInfo.id;
          this.chapterNum = data.data.chapterInfo.chapterNum;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

    async cancel_collect() {
      const reqbody = {
        userId: this.$store.getters.GetUID,
        bookId: this.$route.params.bookId,
      };
      try {
        const response = await fetch('http://localhost:8888/api/front/book/cancel_collect', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(reqbody),
        });
        if (response.status == 200) {
          this.clickedLoading();
          ElMessage.success("Collection Removed");
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

    async collect() {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please log in to collect");
      } else if (this.totalChapters === "0") {
        ElMessage.error("There is no chapter in this book");
      } else {
        const reqbody = {
          userId: this.$store.getters.GetUID,
          bookId: this.$route.params.bookId,
        };
        try {
          const response = await fetch('http://localhost:8888/api/front/book/collect', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(reqbody),
          });
          if (response.status == 200) {
            const data = await response.json();
            if (data.code === "00000") {
              ElMessage.success("Book Collected");
              this.clickedLoading();
            }
          } else {
            console.log(response.status);
          }
        } catch (error) {
          console.error(error);
        }
      }
    },

    async updateUserComment() {
      if (!this.userComment) {
        ElMessage.error("Comment cannot be empty");
      } else if (this.userComment.trim() === '') {
        ElMessage.error("Comment cannot be empty");
      } else {
        this.requestBody.commentContent = this.userComment;
        try {
          await fetch('http://localhost:8888/api/front/book/update_comment', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.requestBody),
          });
        } catch (error) {
          console.error(error);
        }
        this.clickedLoading();
        this.isEditComment = false;
        this.userComment = null;
        ElMessage.success("Comment Updated");
      }
    },

    async deleteUserComment() {
      try {
        await fetch(`http://localhost:8888/api/front/book/comment/${this.userCommented.id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          },
        });
      } catch (error) {
        console.error(error);
      }
      this.isEditComment = false;
      this.userComment = null;
      this.clickedLoading();

      ElMessage.success("Comment Deleted");
    },

    async getUserComment() {
      try {
        const response = await fetch('http://localhost:8888/api/front/book/get_comment', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.requestBody),
        });
        if (response.status == 200) {
          const data = await response.json();
          this.userCommented = data.data;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

    async postUserComment() {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please log in to leave a comment");
      } else if (!this.userComment || this.userComment.trim() === '') {
        ElMessage.error("Comment cannot be empty");
      } else {
        this.requestBody.commentContent = this.userComment;
        try {
          await fetch('http://localhost:8888/api/front/book/new_comment', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.requestBody),
          });
        } catch (error) {
          console.error(error);
        }
        this.clickedLoading();
        this.isEditComment = false;
        this.userComment = null;
        ElMessage.success("Submit successful");
      }
    },

    async getAllComments() {
      try {
        const response = await fetch('http://localhost:8888/api/front/book/all_comments', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.requestBody),
        });
        if (response.status == 200) {
          const data = await response.json();
          this.comments = data.data.list;
          this.totalComments = data.data.total;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

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

    async getChapters() {
      try {
        const response = await fetch(`http://localhost:8888/api/front/book/chapter/list?bookId=${this.$route.params.bookId}&pageNum=${this.chapterPageNum}&pageSize=20`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.chapters = data.data.list;
          this.totalChapters = data.data.total;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },

    goToContent(chapterId, chapterName) {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please log in to read the book");
      } else {
        if (chapterId) {
          this.prevChapterId = chapterId;
          this.chapterId = chapterId;
          this.chapterName = chapterName;
          this.drawer = true;
          this.addVisit();
          this.getContent();
        } else {
          ElMessage.error("There is no chapter in this book");
        }
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
        this.getChapters();
        this.isShowComments = false;
        this.isShowChapters = true;
        this.clickedLoad = false;
      }, 500);
    },

    clickedLoading() {
      this.clickedLoad = true;
      setTimeout(() => {
        this.getUserComment();
        this.getAllComments();
        this.getBookInfo();
        this.getUserCollect();
        this.clickedLoad = false;
      }, 500);
    },

    clearUserComment() {
      this.userComment = null;
    },

    changeDrawerColor(color) {
      this.themeColor = color;
      const drawer = document.getElementsByClassName('el-drawer')[0];
      drawer.style.backgroundColor = color;
    },

    switchTheme() {
      if (this.settingTheme) {
        this.themeColor = '#1c1c1c';
      } else {
        this.themeColor = '#ffffff';
      }
    },
  },

  computed: {
    fontSizeStyle() {
      const fontSizeList = ["12px", "16px", "20px", "24px", "28px"];
      return fontSizeList[this.settingFontSize];
    },
  },
}
</script>

<template>
  <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
    element-loading-background="rgba(255, 255, 255, 255)"
    style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
  <div v-if="showBookInfo">
    <div style="width: 100%; height: 440px; background-color: #f5f6fc; position: absolute; z-index: -10;"></div>
    <div class="bookInfoBody">
      <div class="bookDetail">
        <div class="bookmark-container">
          <img :src="book.picUrl"
            style="height: 400px; box-shadow: 6px 4px 6px rgb(155, 155, 155); border-radius: 8px;" />
          <div v-if="isCollected" class="bookmark-icon">
            <el-icon size="60">
              <CaretBottom color="#f7ba2a" />
            </el-icon>
          </div>
          <div v-if="isCollected" class="bookmark-icon2">
            <el-icon size="60">
              <CollectionTag color="#f7ba2a" />
            </el-icon>
          </div>
        </div>
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
          <div style="bottom: 10px; position: absolute; width: 100%;">
            <div class="bookInfoScore">
              <div style="display: flex; align-items: center;">
                <el-rate v-model="book.score" disabled show-score text-color="#ff9900" size="large"
                  score-template="{value}" />&nbsp;({{ book.commentCount }})
              </div>
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
              <el-button v-if="this.prevChapterId === null" style="font-size: 14pt;" size="large" type="primary" round
                :icon="Reading"
                @click="goToContent(chapters[0] ? chapters[0].id : null, chapters[0] ? chapters[0].chapterName : null)">
                READ
              </el-button>
              <el-button v-else style="font-size: 14pt;" size="large" type="primary" round :icon="Reading"
                @click="goToContent(this.prevChapterId, this.chapterName)">
                CONTINUE READING
              </el-button>
              <el-button v-if="!isCollected" style="font-size: 14pt;" size="large" type="primary" round :icon="Plus"
                @click="collect">
                ADD TO COLLECTIONS
              </el-button>
              <el-button v-else style="font-size: 14pt;" size="large" type="primary" round :icon="Delete"
                @click="cancel_collect">
                REMOVE FROM COLLECTIONS
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="chapterDetail">
        <div class="container">
          <div class="list">
            <h1 :class="isShowComments ? 'chooseOne' : 'noChoose'" @click="chooseComments"><span>Comments</span></h1>
            <h1>|</h1>
            <h1 :class="isShowChapters ? 'chooseOne' : 'noChoose'" @click="chooseChapters"><span>Chapters</span></h1>
          </div>
        </div>
        <el-divider />
        <div v-loading.fullscreen="clickedLoad" :element-loading-spinner="svg"
          element-loading-svg-view-box="0, 5, 30, 40"></div>
        <div v-if="isShowComments">
          <div v-if="userCommented">
            <el-card>
              <div style="font-size: 16pt; display: flex; justify-content: space-between; align-items: center;">
                <b>My Comment</b>
                <div class="editSwitch">
                  <el-switch size="large" inline-prompt v-model="this.isEditComment" active-text="Editing"
                    inactive-text="Edit" />
                </div>
              </div>
              <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-rate disabled v-model="userCommented.score" allow-half show-score />
                <div>
                  {{ userCommented.updateTime ? userCommented.updateTime.replace('T', " ") : null }}
                </div>
              </div>
              <div>
                {{ userCommented.commentContent }}
              </div>
              <div v-if="isEditComment" style="margin-top: 20px;">
                <el-input v-model="userComment" :rows="4" type="textarea" :placeholder=userCommented.commentContent>
                </el-input>
                <div style="display: flex; justify-content: space-between;  margin-top: 10px;">
                  <div class="userScoreInput">
                    <span style="font-size: 12pt;">How would you rate this book?</span>&nbsp;<el-rate
                      v-model="requestBody.score" allow-half show-score />
                  </div>
                  <div>
                    <el-button type="success" :icon="Check" round @click="updateUserComment"
                      :disabled="!userComment">Update</el-button>
                    <el-button @click="clearUserComment" :disabled="!userComment" :icon="MagicStick"
                      round>Clear</el-button>
                    <el-popconfirm title="Are you sure to delete your comment?" width="190" @confirm="deleteUserComment">
                      <template #reference>
                        <el-button type="danger" :icon="Delete" round>Delete</el-button>
                      </template>
                    </el-popconfirm>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
          <div v-else>
            <el-input v-model="userComment" :rows="4" type="textarea"
              placeholder="Have something to say about this book? Write your comment!">
            </el-input>
            <div style="display: flex; justify-content: space-between;  margin-top: 10px;">
              <div class="userScoreInput">
                <span style="font-size: 12pt;">How would you rate this book?</span>&nbsp;<el-rate
                  v-model="requestBody.score" allow-half show-score />
              </div>
              <div>
                <el-button type="primary" @click="postUserComment" :disabled="!userComment">Submit</el-button>
                <el-button @click="clearUserComment" :disabled="!userComment">Clear</el-button>
              </div>
            </div>
          </div>
          <h1>{{ totalComments }} {{ totalComments <= 1 ? 'Review' : 'Reviews' }}</h1>
              <div v-for="comment in comments" :key="comment.id">
                <div style="display: flex;">
                  <div style="width: 50px; display: flex; justify-content: center; ">
                    <img
                      :src="comment.commentUserImage ? comment.commentUserImage : 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300'"
                      style="border: 1px solid; border-radius: 30px; height: 30px; width: 30px; object-fit: cover;" />
                  </div>
                  <div style="display: flex; flex-direction: column; width: 100%; position: relative;">
                    <div style="display: flex; align-items: center; justify-content: space-between; height: 30px;">
                      <div><b>{{ comment.commentUserName }}</b></div>
                      <div>{{ comment.commentTime }}</div>
                    </div>
                    <div style=" min-height: 50px; margin-top: 5px;">
                      {{ comment.commentContent }}
                    </div>
                    <el-rate v-model="comment.score" disabled show-score text-color="#ff9900" score-template="{value}"
                      style="bottom: -20px; position: absolute;" />
                  </div>
                </div>
                <el-divider />
              </div>
              <el-pagination :hide-on-single-page="totalComments <= 5" v-model:current-page="requestBody.pageNum"
                :page-size="50" :disabled="disabled" :background="background" layout="prev, pager, next"
                :total="totalComments * 10" style="width: 100%; display: flex; justify-content: center;" />
        </div>

        <div v-if="isShowChapters">
          <div v-if="chapters.length > 0">
            <h1>{{ totalChapters }} Total</h1>
            <div style="display: flex; flex-wrap: wrap; justify-content: space-between;">
              <div v-for="(item, index) in chapters" :key="index" class="chapters"
                :style="{ 'color': item.id === prevChapterId ? '#ff9800' : '#000000' }"
                @click="goToContent(item.id, item.chapterName, index)">
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
              <el-pagination :hide-on-single-page="totalChapters <= 20" v-model:current-page="chapterPageNum"
                :page-size="200" :disabled="disabled" :background="background" layout="prev, pager, next"
                :total="totalChapters * 10" style="width: 100%; display: flex; justify-content: center;" />
            </div>
          </div>
          <div v-else>
            There is no chapter in this book
          </div>
        </div>
        <div class="drawerContainer">
          <el-drawer size="100%" :with-header="false" v-model="drawer" direction="ttb" :style="{
            background: themeColor,
            color: !settingTheme ? '#333333' : '#ffffff',
          }">
            <div :style="{ fontSize: fontSizeStyle }">
              <h1 style="display: flex; align-items: center; justify-content: space-between;">
                {{ chapterName }}
                <div style="display: flex; justify-content:space-between; width: 35%">
                  <el-progress style="width: 90%;" :stroke-width="14" striped striped-flow
                    :duration="chapterNum / totalChapters * 60"
                    :percentage="Math.floor(chapterNum / totalChapters * 100)" />
                  <el-dropdown class="settingDropdown" trigger="click" :hide-on-click="false">
                    <div>
                      <el-icon size="16pt">
                        <Setting />
                      </el-icon>
                    </div>
                    <template #dropdown>
                      <el-collapse style="width: 300px;" v-model="activeName" accordion>
                        <el-collapse-item style="padding: 10px;" title="Font Size" name="1">
                          <el-slider style="width: 90%; margin: auto;" v-model="settingFontSize" :min="0" :max="4"
                            show-stops :show-tooltip="false" :marks="marksOnFontSize" />
                        </el-collapse-item>
                        <el-collapse-item style="padding: 10px;" title="Theme" name="2">
                          <div style="width: 100%; display: flex; justify-content: center;">
                            <el-switch v-model="settingTheme" @change="switchTheme" :active-icon="Moon"
                              :inactive-icon="Sunny"
                              style="border-right: 1px solid; border-color: #bebebe; padding-right: 15px;" />
                            &nbsp;&nbsp;&nbsp;
                            <el-button circle style="background-color: #ffffff"
                              @click="changeDrawerColor('#ffffff')"><el-icon></el-icon></el-button>
                            <el-button circle style="background-color: #F5F5DC;"
                              @click="changeDrawerColor('#F5F5DC')"><el-icon></el-icon></el-button>
                            <el-button circle style="background-color: #FFFACD"
                              @click="changeDrawerColor('#FFFACD')"><el-icon></el-icon></el-button>
                            <el-button circle style="background-color: #86c3f5;"
                              @click="changeDrawerColor('#86c3f5')"><el-icon></el-icon></el-button>
                          </div>
                        </el-collapse-item>
                      </el-collapse>
                    </template>
                  </el-dropdown>
                </div>
              </h1>
              <div style="display: flex;">
                <div style="margin-left: -50px;"><el-button @click="prevChapter"
                    style="height: 100%; border-color: rgba(0, 0, 0, 0); background-color: rgba(0, 0, 0, 0); border-right: 1px solid #b7b7b7; border-radius: 0;">
                    <el-icon>
                      <ArrowLeft />
                    </el-icon>
                    P
                    <br>
                    R
                    <br>
                    E
                    <br>
                    V
                  </el-button></div>
                <div style="line-height: 2; margin-left: 20px; margin-right: 20px;" v-html="chapterContent"></div>
                <div style="margin-right: -50px;"><el-button @click="nextChapter"
                    style="height: 100%; border-color: rgba(0, 0, 0, 0); background-color: rgba(0, 0, 0, 0); border-left: 1px solid #b7b7b7; border-radius: 0;">
                    N
                    <br>
                    E
                    <br>
                    X
                    <br>
                    T
                    <el-icon>
                      <ArrowRight />
                    </el-icon>
                  </el-button></div>
              </div>
            </div>
          </el-drawer>
        </div>
      </div>
    </div>
    <Global_Footer />
  </div>
</template>


<style>
.highlighted {
  background-color: #949494;
}

.bookmark-container {
  position: relative;
}

.bookmark-icon {
  position: absolute;
  top: -28px;
  right: 30px;
  width: 40px;
  height: 40px;
}

.bookmark-icon2 {
  position: absolute;
  top: -10px;
  right: 30px;
  width: 40px;
  height: 40px;
}

.container {
  font-family: 'Roboto Condensed', sans-serif;
  margin-bottom: -20px;
  color: #b7b7b7;
}

.container .list {
  list-style: none;
  margin: 0;
  padding: 0;
}

.container h1 {
  display: inline-block;
  padding: 0 10px;
}

.container span {
  position: relative;
  display: block;
  cursor: pointer;
}

.container span {

  &:before,
  &:after {
    content: '';
    position: absolute;
    width: 0%;
    height: 2px;
    bottom: -2px;
    margin-top: -0.5px;
    background: #fff;
  }

  &:before {
    left: -2.5px;
  }

  &:after {
    right: 2.5px;
    background: #fff;
    transition: width 0.5s cubic-bezier(0.22, 0.61, 0.36, 1);
  }

  &:hover {
    &:before {
      background: #949494;
      width: 100%;
      transition: width 0.5s cubic-bezier(0.22, 0.61, 0.36, 1);
    }

    &:after {
      background: transparent;
      width: 100%;
      transition: 0s;
    }
  }
}

.settingDropdown:hover {
  cursor: pointer;
}

.drawerContainer .el-drawer.ttb {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
}

.drawerContainer .el-drawer__title {
  font-size: 20pt;
}

.drawerContainer .el-drawer {
  --el-drawer-padding-primary: 50px;
}

.drawerContainer .el-drawer__body {
  position: relative;
}

.editSwitch .el-switch__core .el-switch__inner .is-text {
  font-size: 16pt;
}

.editSwitch .el-switch--large .el-switch__core .el-switch__inner {
  height: 30px;
}

.noChoose {
  color: #b7b7b7;
}

.chooseOne {
  color: black;
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
  justify-content: space-between;
  width: 100%;
}

.bookInfoScore .el-rate {
  --el-rate-icon-size: 22pt;
  --el-rate-font-size: 20pt;
}

.userScoreInput {
  display: flex;
  align-items: center;
}

.userScoreInput .el-rate {
  --el-rate-icon-size: 18pt;
  --el-rate-font-size: 18pt;
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

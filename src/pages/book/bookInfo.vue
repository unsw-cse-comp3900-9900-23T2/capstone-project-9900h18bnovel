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
  CaretBottom,
  ArrowLeft,
  ArrowRight,
  Male,
  Female,
  Edit,
  Upload,
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { getItemColor } from '@/utils'
const marksOnFontSize = ({
  0: 'XS',
  1: 'S',
  2: 'M',
  3: 'L',
  4: 'XL',
})
</script>
<script >
export default {
  data() {
    return {
      isLoginVisible: false,
      VerImage: '',
      sessionId: '',
      loading: true,
      showBookInfo: false,
      isShowComments: true,
      isShowChapters: false,
      isShowFiction: false,
      clickedLoad: false,
      isCollected: false,
      collectedBooksId: [],
      book: {},
      chapters: null,
      totalChapters: 0,
      chapterPageNum: 1,
      chapterNum: 0,
      prevChapterId: null,
      preChapterName: null,
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
      bookAuthorId: null,

      otherUserName: null,
      otherUserImg: null,
      otherUserGender: null,
      otherUserEmail: null,

      isCreateFiction: false,
      createFictionContent: null,
      fictionPageNum: 1,

      fanficList: [],
      totalFiction: null,
      fictionContent: {},
      fictionContentDialogVisible: false,

      isUserFiction: false,
      userFictionPageNum: 1,
      userFictionTotal: null,
      userFictionList: [],

      isEditUserFiction: false,

      penName: null,
      signature: null,

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
    'fictionPageNum': {
      handler() {
        this.chooseFiction();
      },
      deep: true
    },
    'userFictionPageNum': {
      handler() {
        this.getUserFiction();
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
    async getAuthorInfo() {
      await axios.get("/api/author/get_author_info?userId=" + this.bookAuthorId)
        .then(response => {
          const data = response.data;
          if (data.code === "00000") {
            if (data.data === null) {
              this.penName = "Thanks for reading my book ,hope you enjoy it!";
            } else {
              this.penName = data.data.penName;
              this.signature = data.data.signature;
            }
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async updateUserFiction(fictionContent, fictionId) {
      const reqBody = {
        "fanficId": fictionId,
        "fanficContent": fictionContent
      }
      await axios.post("/api/front/book/update_fanfic", reqBody)
        .then(response => {
          if (response.data.code === "00000") {
            ElMessage.success("Fiction updated");
            this.getAllFiction();
            this.getUserFiction();
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async deleteUserFiction(fictionId) {
      await axios.delete(`/api/front/book/delete_fanfic/${fictionId}`)
        .then(response => {
          if (response.data.code === "00000") {
            ElMessage.success("Fiction Deleted");
            this.getAllFiction();
            this.getUserFiction();
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getUserFiction() {
      await axios.get(`/api/front/book/user_fanfic/list?userId=${this.$store.getters.GetUID}&bookId=${this.$route.params.bookId}&pageNum=${this.userFictionPageNum}&pageSize=5`)
        .then(response => {
          const data = response.data;
          this.userFictionList = data.data.list;
          this.userFictionTotal = data.data.total;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getFictionContent(fictionId) {
      await axios.get("/api/front/book/fanfic_info/" + fictionId)
        .then(response => {
          const data = response.data;
          this.fictionContent = data.data;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getAllFiction() {
      await axios.get(`/api/front/book/all_fanfic/list?userId=${this.$store.getters.GetUID}&bookId=${this.$route.params.bookId}&pageNum=${this.fictionPageNum}&pageSize=5`)
        .then(response => {
          const data = response.data;
          this.fanficList = data.data.list;
          this.totalFiction = data.data.total;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async createFiction() {
      const reqbody = {
        "userId": this.$store.getters.GetUID,
        "bookId": this.$route.params.bookId,
        "fanficContent": this.createFictionContent
      }
      await axios.post("/api/front/book/new_fanfic", reqbody)
        .then(response => {
          response.data.code === "00000" ? (() => {
            ElMessage.success("Fiction created");
            this.isCreateFiction = false;
            this.createFictionContent = null;
            this.getAllFiction();
          })()
            : ElMessage.error("Oops, something wrong")
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getOtherUser(userId) {
      await axios.get("/api/front/user/get_other_userInfo?userId=" + userId)
        .then(response => {
          const data = response.data;
          this.otherUserEmail = data.data.email;
          this.otherUserImg = data.data.userPhoto;
          this.otherUserGender = data.data.userSex;
          this.otherUserName = data.data.username;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getUserCollect() {
      await axios.get(`/api/front/user/user_collect?userId=${this.$store.getters.GetUID}`)
        .then(response => {
          const data = response.data;
          this.collectedBooksId = data.data.map(item => item.bookId);
          this.isCollected = this.collectedBooksId.includes(this.$route.params.bookId) ? true : false;
          const thisBook = data.data.find(item => item.bookId === this.$route.params.bookId);
          this.prevChapterId = thisBook ? thisBook.preChapterId : null;
          this.preChapterName = thisBook ? thisBook.preChapterName : null;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async prevChapter() {
      if (this.chapterNum === "1") {
        ElMessage.error("There is no previous chapter");
      } else {
        await axios.get(`/api/front/book/pre_chapter_id/${this.chapterId}`)
          .then(response => {
            const data = response.data;
            this.chapterId = data.data;
            this.getContent();
            this.scrollToTop();
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    async nextChapter() {
      if (this.chapterNum === this.totalChapters) {
        ElMessage.error("There is no next chapter");
      } else {
        await axios.get(`/api/front/book/next_chapter_id/${this.chapterId}`)
          .then(response => {
            const data = response.data;
            this.chapterId = data.data;
            this.getContent();
            this.scrollToTop();
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    async addVisit() {
      await axios.get(`/api/front/book/add_visit?bookId=${this.$route.params.bookId}`)
        .catch(error => {
          console.error(error);
        });
      this.getBookInfo();
    },

    async getContent() {
      await axios.get(`/api/front/book/get_content?chapterId=${this.chapterId}&userId=${this.$store.getters.GetUID}`)
        .then(response => {
          const data = response.data;
          this.chapterContent = data.data.bookContent;
          this.chapterName = data.data.chapterInfo.chapterName;
          this.chapterId = data.data.chapterInfo.id;
          this.prevChapterId = data.data.chapterInfo.id;
          this.chapterNum = data.data.chapterInfo.chapterNum;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async cancel_collect() {
      const reqbody = {
        userId: this.$store.getters.GetUID,
        bookId: this.$route.params.bookId,
      };
      await axios.post('/api/front/book/cancel_collect', reqbody)
        .catch(error => {
          console.error(error);
        });
      this.clickedLoading();
      ElMessage.success("Collection Removed");
    },

    async collect() {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please sign in to collect");
      } else if (this.totalChapters === "0") {
        ElMessage.error("There is no chapter in this book");
      } else {
        const reqbody = {
          userId: this.$store.getters.GetUID,
          bookId: this.$route.params.bookId,
        };
        await axios.post('/api/front/book/collect', reqbody)
          .then(response => {
            const data = response.data;
            if (data.code === "00000") {
              this.clickedLoading();
              ElMessage.success("Book Collected");
            }
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    async updateUserComment() {
      if (!this.userComment) {
        ElMessage.error("Comment cannot be empty");
      } else if (this.userComment.trim() === '') {
        ElMessage.error("Comment cannot be empty");
      } else {
        this.requestBody.commentContent = this.userComment;
        await axios.post('/api/front/book/update_comment', this.requestBody)
          .catch(error => {
            console.error(error);
          });
        this.clickedLoading();
        this.isEditComment = false;
        this.userComment = null;
        ElMessage.success("Comment Updated");
      }
    },

    async deleteUserComment() {
      await axios.delete(`/api/front/book/comment/${this.userCommented.id}`)
        .catch(error => {
          console.error(error);
        });
      this.isEditComment = false;
      this.userComment = null;
      this.clickedLoading();
      ElMessage.success("Comment Deleted");
    },

    async getUserComment() {
      await axios.post('/api/front/book/get_comment', this.requestBody)
        .then(response => {
          const data = response.data;
          this.userCommented = data.data;
          if (this.userCommented && this.userCommented.score) {
            this.userCommented.score = parseFloat(this.userCommented.score);
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async postUserComment() {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please sign in to leave a comment");
      } else if (!this.userComment || this.userComment.trim() === '') {
        ElMessage.error("Comment cannot be empty");
      } else {
        this.requestBody.commentContent = this.userComment;

        await axios.post('/api/front/book/new_comment', this.requestBody)
          .catch(error => {
            console.error(error);
          });
        this.clickedLoading();
        this.isEditComment = false;
        this.userComment = null;
        ElMessage.success("Submit successful");
      }
    },

    async getAllComments() {
      await axios.post('/api/front/book/all_comments', this.requestBody)
        .then(response => {
          const data = response.data;
          this.comments = data.data.list.map(item => ({
            ...item,
            score: parseFloat(item.score)
          }));
          this.totalComments = data.data.total;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getBookInfo() {
      await axios.get(`/api/front/book/${this.$route.params.bookId}`)
        .then(response => {
          const data = response.data;
          this.book = data.data;
          if (this.book && this.book.score) {
            this.book.score = parseFloat(this.book.score);
          }
          this.bookAuthorId = data.data.authorId;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getChapters() {
      await axios.get(`/api/front/book/chapter/list?bookId=${this.$route.params.bookId}&pageNum=${this.chapterPageNum}&pageSize=20`)
        .then(response => {
          const data = response.data;
          this.chapters = data.data.list;
          this.totalChapters = data.data.total;
        })
        .catch(error => {
          console.error(error);
        });
    },

    goToContent(chapterId, chapterName) {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please sign in to read the book");
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

    scrollToTop() {
      window.scrollTo({
        top: 0,
        behavior: 'smooth', // 使用 smooth 动画滚动到顶部
      });
    },

    chooseComments() {
      this.clickedLoad = true;
      setTimeout(() => {
        this.isShowChapters = false;
        this.isShowComments = true;
        this.isShowFiction = false;
        this.clickedLoad = false;
      }, 500);
    },

    chooseChapters() {
      this.clickedLoad = true;
      setTimeout(() => {
        this.getChapters();
        this.isShowComments = false;
        this.isShowChapters = true;
        this.isShowFiction = false;
        this.clickedLoad = false;
      }, 500);
    },

    chooseFiction() {
      this.$store.getters.isAuthenticated ?
        (() => {
          this.clickedLoad = true;
          setTimeout(() => {
            this.getAllFiction();
            this.isShowComments = false;
            this.isShowChapters = false;
            this.isShowFiction = true;
            this.clickedLoad = false;
          }, 500);
        })() : ElMessage.error("Please sign in to see fun fiction")
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
      this.settingTheme = false;
    },

    switchTheme() {
      if (this.settingTheme) {
        this.themeColor = '#1c1c1c';
      } else {
        this.themeColor = '#ffffff';
      }
    },

    cleanOtherUserInfo() {
      this.otherUserEmail = null;
      this.otherUserGender = null;
      this.otherUserImg = null;
      this.otherUserName = null;
    },

    createFicCheck() {
      this.$store.getters.isAuthenticated ? this.isCreateFiction = true : ElMessage.error("Please sign in to create fiction")
    },

    goFictionContent(fictionId) {
      this.fictionContentDialogVisible = true;
      this.drawer = true;
      this.getFictionContent(fictionId);
    },

    viewUsersFiction() {
      this.isUserFiction = true;
      this.getUserFiction();
    },


  },

  computed: {
    fontSizeStyle() {
      const fontSizeList = ["12px", "16px", "20px", "24px", "28px"];
      return fontSizeList[this.settingFontSize];
    },
    getMaskedEmail() {
      const [username, domain] = this.otherUserEmail.split("@");
      const maskedUsername = username.substring(0, 4) + "XXXXX";
      return `${maskedUsername}@${domain}`;
    },
  },
}
</script>

<template>
  <div v-loading.fullscreen="loading" element-loading-spinner=" ">
  </div>
  <div v-if="showBookInfo">
    <div class="bookInfoBody">
      <div class="bookDetail">
        <div class="bookmark-container">
          <img :src="book.picUrl" class="book-image" />
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
          <div>
            <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
              <h1 style="display: flex; align-items: center;"> {{ book.bookName }}
                <el-tag effect="plain" style="margin-left: 20px;" :style="getItemColor(book.categoryName)">{{
                  book.categoryName
                }}</el-tag>
              </h1>
              <el-tag style="font-size: 14pt;" size="large" effect="plain" :type="book.bookStatus === '1' ? 'success'
                    : ''">{{ book.bookStatus === "1" ? "Completed" : "Ongoing" }}</el-tag>
            </div>

            <el-popover placement="top" :width="250" trigger="click">
              {{ penName }}
              {{ signature }}
              <template #reference>
                <div style="font-size: 14pt; margin-bottom: 15px;" @click="getAuthorInfo"> {{ book.authorName }}</div>
              </template>
            </el-popover>
          </div>

          <div style="line-height: 1.5;"> {{
            book.bookDesc }}</div>
          <div>
            <div class="bookInfoScore">
              <div style="display: flex; align-items: center;">
                <el-rate v-model="book.score" disabled show-score text-color="#ff9900" size="large"
                  score-template="{value}" />&nbsp;({{ book.commentCount }})
              </div>
            </div>
            <div class="book-reviews">
              <div>
                <el-icon>
                  <Document />
                </el-icon>
                {{ book.wordCount }}
                &nbsp;&nbsp;&nbsp;
              </div>
              <div><el-icon>
                  <View />
                </el-icon>
                {{ book.visitCount }}
                &nbsp;&nbsp;&nbsp;
              </div>
              <div>
                <el-icon>
                  <CollectionTag />
                </el-icon>
                {{ book.collectCount }}
                &nbsp;&nbsp;&nbsp;
              </div>
              <div>
                <el-icon>
                  <Clock />
                </el-icon>
                {{ book.lastChapterUpdateTime }}
                &nbsp;&nbsp;&nbsp;
              </div>

            </div>
            <div class="book-buttons">
              <el-button v-if="prevChapterId === null" class="book-buttons-button" size="large" type="primary" round
                :icon="Reading"
                @click="goToContent(chapters[0] ? chapters[0].id : null, chapters[0] ? chapters[0].chapterName : null)">
                READ
              </el-button>
              <el-button v-else class="book-buttons-button" size="large" type="primary" round :icon="Reading"
                @click="goToContent(prevChapterId, chapterName)">
                {{ chapterName ? chapterName : preChapterName ? preChapterName : "READ" }}
              </el-button>
              <el-button v-if="!isCollected" class="book-buttons-button" size="large" type="primary" round :icon="Plus"
                @click="collect">
                COLLECT
              </el-button>
              <el-button v-else class="book-buttons-button" size="large" type="primary" round :icon="Delete"
                @click="cancel_collect">
                UNCOLLECT
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="chapterDetail">
        <div class="list">
          <h1 :class="isShowComments ? 'chooseOne' : 'noChoose'" @click="chooseComments"><span>Comments</span></h1>
          &nbsp;&nbsp;<h1 class="choose-divider">|</h1>&nbsp;&nbsp;
          <h1 :class="isShowChapters ? 'chooseOne' : 'noChoose'" @click="chooseChapters"><span>Chapters</span></h1>
          &nbsp;&nbsp;<h1 class="choose-divider">|</h1>&nbsp;&nbsp;
          <h1 :class="isShowFiction ? 'chooseOne' : 'noChoose'" @click="chooseFiction"><span>Fan Fiction</span></h1>
        </div>
        <el-divider />
        <div v-loading.fullscreen="clickedLoad" element-loading-spinner=" "></div>
        <div v-if="isShowComments">
          <div v-if="userCommented">
            <el-card>
              <div style="font-size: 16pt; display: flex; justify-content: space-between; align-items: center;">
                <b>My Comment</b>
                <div class="editSwitch">
                  <el-switch size="large" inline-prompt v-model="isEditComment" active-text="Editing"
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
                <div style="display: flex; justify-content: space-between;  margin-top: 10px; flex-wrap: wrap;">
                  <div class="userScoreInput">
                    <span style="font-size: 12pt;" class="score-prompt">How would you rate this book?</span>&nbsp;<el-rate
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
            <div style="display: flex; justify-content: space-between;  margin-top: 10px; flex-wrap: wrap;">
              <div class="userScoreInput">
                <span style="font-size: 12pt;" class="score-prompt">How would you rate this book?</span>&nbsp;<el-rate
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
                    <el-popover placement="top-start" :width="350" trigger="click"
                      @show="getOtherUser(comment.commentUserId)" @before-leave="cleanOtherUserInfo()">
                      <template #reference>
                        <img
                          :src="comment.commentUserImage ? comment.commentUserImage : 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300'"
                          class="userAvatar" />
                      </template>
                      <div style="display: flex; align-items: center;">
                        <img
                          :src="otherUserImg ? otherUserImg : 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300'"
                          class="otherUserAvatar" />
                        <div>
                          <div>{{ otherUserEmail ? getMaskedEmail : "" }}</div>
                          <div>{{ otherUserName }}</div>
                          <div>
                            <el-icon v-if="otherUserGender === '0'">
                              <Male />
                            </el-icon>
                            <el-icon v-else>
                              <Female />
                            </el-icon>
                          </div>
                        </div>
                      </div>
                    </el-popover>

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
                :page-size="50" layout="prev, pager, next" :total="totalComments * 10"
                style="width: 100%; display: flex; justify-content: center;" />
        </div>

        <div v-else-if="isShowChapters">
          <div v-if="chapters.length > 0" class="chaptersBody">
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
                :page-size="200" layout="prev, pager, next" :total="totalChapters * 10"
                style="width: 100%; display: flex; justify-content: center;" />
            </div>
          </div>
          <div v-else class="chaptersBody">
            There is no chapter in this book
          </div>
        </div>

        <div v-else-if="isShowFiction" class="fictionBody">
          <div style="width: 100%; display: flex; justify-content: space-between;margin-bottom: 10px;">
            <el-button :icon="Edit" type="primary" @click="viewUsersFiction">Edit</el-button>
            <el-button :icon="Plus" type="primary" @click="createFicCheck">Create</el-button>
          </div>

          <el-dialog v-model="isCreateFiction" title="Create Fiction">
            <el-input v-model="createFictionContent" :autosize="{ minRows: 20, maxRows: 20 }" type="textarea"
              placeholder="Please input" />
            <el-button type="primary" style="width: 100%; margin-top: 20px;" @click="createFiction">Create</el-button>
          </el-dialog>

          <el-dialog v-model="isUserFiction" title="My Fiction">
            <h2 v-if="userFictionList.length < 1">You have no fiction</h2>
            <div v-else v-for="item in userFictionList" :key="item.id"
              style="display: flex; min-height: 100px; text-align: center; border-bottom: 1px solid #e7e7e7; padding-top: 10px; position: relative;">
              <el-input v-model="item.fanficContent" :autosize="{ minRows: 3, maxRows: 3 }" type="textarea" style="" />
              <div style="display: flex; flex-direction: column;">
                <span style="margin: 0 10px 10px 10px; height: 20px; display: inline-block;">
                  {{ item.fanficTime }}
                </span>
                <div style="display: flex; flex-direction: row; margin: 10px; justify-content: space-around;">
                  <el-button type="primary" :icon="Upload" circle @click="updateUserFiction(item.fanficContent, item.id)"
                    style="margin: 5px;" />
                  <el-button type="danger" :icon="Delete" circle @click="deleteUserFiction(item.id)"
                    style="margin: 5px;" />
                </div>
              </div>

            </div>
            <el-pagination :hide-on-single-page="userFictionTotal <= 5" v-model:current-page="userFictionPageNum"
              :page-size="50" layout="prev, pager, next" :total="userFictionTotal * 10"
              style="width: 100%; display: flex; justify-content: center;" />
            <template #footer>
              <span>
                <el-button type="primary" @click="isUserFiction = false">Close</el-button>
              </span>
            </template>
          </el-dialog>

          <h1 v-if="fanficList.length < 1">
            There is no fiction
          </h1>
          <div v-else v-for="item in fanficList" :key="item.id">
            <div style="display: flex;">
              <div style="width: 50px; display: flex; justify-content: center; ">
                <el-popover placement="top-start" :width="350" trigger="click" @show="getOtherUser(item.fanficUserId)"
                  @before-leave="cleanOtherUserInfo()">
                  <template #reference>
                    <img
                      :src="item.fanficUserImage ? item.fanficUserImage : 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300'"
                      class="userAvatar" />
                  </template>
                  <div style="display: flex; align-items: center;">
                    <img
                      :src="otherUserImg ? otherUserImg : 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300'"
                      class="otherUserAvatar" />
                    <div>
                      <div>{{ otherUserEmail ? getMaskedEmail : "" }}</div>
                      <div>{{ otherUserName }}</div>
                      <div>
                        <el-icon v-if="otherUserGender === '0'">
                          <Male />
                        </el-icon>
                        <el-icon v-else>
                          <Female />
                        </el-icon>
                      </div>
                    </div>
                  </div>
                </el-popover>

              </div>
              <div style="display: flex; flex-direction: column; width: 100%; position: relative;">
                <div style="display: flex; align-items: center; justify-content: space-between; height: 30px;">
                  <div><b>{{ item.fanficUserName }}</b></div>
                  <div>{{ item.fanficTime }}</div>
                </div>
                <div class="eachFiction" @click="goFictionContent(item.id)">
                  {{ item.fanficContent }}
                </div>
              </div>
            </div>
            <el-divider />
          </div>
          <el-pagination :hide-on-single-page="totalFiction <= 5" v-model:current-page="fictionPageNum" :page-size="50"
            layout="prev, pager, next" :total="totalFiction * 10"
            style="width: 100%; display: flex; justify-content: center;" />
        </div>

        <div class="drawerContainer">
          <el-drawer size="100%" :with-header="true" v-model="drawer" direction="ttb" :style="{
            background: themeColor,
            color: !settingTheme ? '#333333' : '#ffffff',
          }" @closed="fictionContentDialogVisible = false">
            <template #header>
              <h1 style="display: flex; align-items: center; justify-content: space-between;" class="read-header">
                <div v-if="fictionContentDialogVisible">
                  {{ fictionContent.fanficUserName }}
                </div>
                <div v-else>
                  {{ chapterName }}
                </div>
                <div style="display: flex; justify-content: right; width: 35%;">
                  <el-progress v-if="!fictionContentDialogVisible" style="width: 90%;" :stroke-width="14" striped
                    striped-flow :duration="chapterNum / totalChapters * 60"
                    :percentage="Math.floor(chapterNum / totalChapters * 100)" />
                  <el-dropdown class="settingDropdown" trigger="click" :hide-on-click="false">
                    <div>
                      <el-icon size="16pt">
                        <Setting />
                      </el-icon>
                    </div>
                    <template #dropdown>
                      <el-collapse style="width: 300px;" v-model="activeName" accordion>
                        <el-collapse-item style="padding: 10px;" title="Font Size" name="1" class="silder1">
                          <el-slider style="--el-slider-button-wrapper-offset: -9px; width: 90%; margin: auto;"
                            v-model="settingFontSize" :min="0" :max="4" show-stops :show-tooltip="false"
                            :marks="marksOnFontSize" />
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
            </template>
            <div :style="{ fontSize: fontSizeStyle }">

              <div style="display: flex; position: relative;">
                <div v-if="!fictionContentDialogVisible" style="margin-left: -50px;">
                  <div @click="prevChapter"
                    style="height: 100%; width: 50%; top: 0; left: 0; position: absolute; margin-left: -50px; border-color: rgba(0, 0, 0, 0); background-color: rgba(0, 0, 0, 0);"
                    class="chagenReadPage">
                  </div>
                </div>
                <div v-if="!fictionContentDialogVisible" class="read-body" v-html="chapterContent"></div>
                <div v-else class="read-body">
                  {{ fictionContent.fanficContent }}
                </div>
                <div v-if="!fictionContentDialogVisible" style="margin-right: -50px;">
                  <div @click="nextChapter"
                    style="height: 100%; width: 50%; top: 0; right: 0; margin-right: -50px;position: absolute; border-color: rgba(0, 0, 0, 0); background-color: rgba(0, 0, 0, 0);"
                    class="chagenReadPage">
                  </div>
                </div>
              </div>
              <div v-if="!fictionContentDialogVisible"
                style="width: 100%; display: flex; justify-content: space-between; margin-top: 20px;">
                <el-button @click="prevChapter" size="large" link style="font-size: 16pt;"><el-icon>
                    <ArrowLeft />
                  </el-icon><el-icon>
                    <ArrowLeft />
                  </el-icon><el-icon>
                    <ArrowLeft />
                  </el-icon>Prev</el-button>
                <el-button @click="nextChapter" size="large" link style="font-size: 16pt;">Next<el-icon>
                    <ArrowRight />
                  </el-icon><el-icon>
                    <ArrowRight />
                  </el-icon><el-icon>
                    <ArrowRight />
                  </el-icon></el-button>
              </div>
            </div>
          </el-drawer>
        </div>

      </div>
    </div>
  </div>
</template>


<style>
.book-buttons .el-button {
  font-size: 14pt;
}

.chagenReadPage:hover {
  cursor: pointer;
}

.read-body {
  line-height: 2;
  margin-left: 40px;
  margin-right: 40px;
}

.book-buttons {
  margin-top: 10px;
  display: flex;
  width: 100%;
}

.book-reviews {
  display: flex;
  font-size: 16pt;
  align-items: center;
  margin-top: 10px;
}

.book-image {
  height: 400px;
  width: 250px;
  box-shadow: 6px 4px 6px rgb(155, 155, 155);
  border-radius: 8px;
  margin-left: 20px;
}

.list {
  display: flex;
  color: #949494;
}

.eachFiction {
  min-height: 50px;
  margin-top: 5px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 6;
  overflow: hidden;
  transform: scale(1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.eachFiction:hover {
  cursor: pointer;
  transform: scale(1.02);
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.1), 0 4px 6px rgba(0, 0, 0, 0.1), 0 -4px 6px rgba(0, 0, 0, 0.1), 0 4px 6px rgba(0, 0, 0, 0.1);
}

.userAvatar {
  border: 1px solid;
  border-radius: 30px;
  height: 30px;
  width: 30px;
  object-fit: cover;
}

.userAvatar:hover {
  cursor: pointer;
}

.otherUserAvatar {
  border: 1px solid;
  border-radius: 50px;
  height: 80px;
  width: 80px;
  object-fit: cover;
  margin-right: 20px;
}

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

.noChoose:hover {
  text-decoration: underline;
  cursor: pointer;
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
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-left: 40px;
  position: relative;
  margin-right: 20px;
}

.bookInfoScore {
  display: flex;
  align-items: center;
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
  width: 49%;
  padding: 20px;
  position: relative;
  transition: transform 0.3s ease;
}

.chapters:hover {
  transform: translateY(-4px);
  cursor: pointer;
}
</style>
<style>
@media screen and (max-width:431px) {
  .book-buttons .el-button {
    font-size: 10pt;
  }

  .fictionBody .el-dialog {
    --el-dialog-width: 90%;
  }

  .fictionBody,
  .chaptersBody {
    margin-top: -10px;
  }

  .score-prompt {
    display: none;
  }

  .choose-divider {
    display: none;
  }

  .chapterDetail {
    width: 95%;
    margin: auto;
  }

  .bookInfoBody {
    width: 100vw;
    min-width: 100vw;
  }

  .bookDetail {
    display: flex;
    flex-direction: column;
    padding-top: 40px;
  }

  .bookmark-container {
    position: relative;
    display: flex;
    justify-content: center;
    width: 100%;
  }

  .book-image {
    height: 300px;
    width: 187.5px;
    margin-left: 0px;
  }

  .bookmark-icon {
    position: absolute;
    top: -28px;
    right: 36vw;
    width: 20px;
    height: 20px;
  }

  .bookmark-icon2 {
    position: absolute;
    top: -10px;
    right: 36vw;
    width: 20px;
    height: 20px;
  }

  .bookWordDetail {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 90%;
    margin-left: 0px;
    margin: auto;
    position: relative;
  }

  .book-reviews {
    display: flex;
    font-size: 10pt;
    align-items: center;
    margin-top: 10px;
    flex-wrap: wrap;
  }

  .book-buttons {
    margin-top: 10px;
    display: flex;
    width: 100%;
    flex-wrap: wrap;
  }

  .noChoose {
    color: #b7b7b7;
    font-size: 14pt;
  }

  .chooseOne {
    color: black;
    font-size: 16pt;
  }

  .list {
    display: flex;
    align-items: center;
    color: #949494;
    justify-content: center;
    margin-bottom: -20px;
  }

  .userScoreInput {
    display: flex;
    align-items: baseline;
    width: 100%;
    margin-top: -10px;
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
    font-size: 10pt;
    width: 49%;
    padding: 10px;
    position: relative;
    transition: transform 0.3s ease;
  }

  .drawerContainer .el-drawer.ttb {
    width: 100vw;
    min-width: 100vw;
    margin: auto;
  }

  .drawerContainer .el-drawer__title {
    font-size: 10pt;
  }

  .drawerContainer .el-drawer {
    --el-drawer-padding-primary: 50px;
  }

  .read-header {
    font-size: 15pt;
  }

  .read-body {
    margin-left: 10px;
    margin-right: 10px;
  }
}
</style>
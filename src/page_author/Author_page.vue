<script setup>
import axios from 'axios';
import {
  Plus,
  Finished,
  Upload,
  Edit,
  Delete,
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
</script>
<script>
export default {
  data() {
    return {
      penName: null,
      updatePenName: null,
      signature: null,
      isAuthor: false,
      loading: true,
      showAuthorPage: false,
      avatar: null,
      isEmpty: true,
      isAuthorUpdate: false,
      authorId: null,
      isCreateBook: false,
      imageUrl: null,
      bookName: null,
      bookDesc: null,
      dirChecked: [true, false],
      genreChecked: [false, false, false, false, false, false, false, false, false, false, false],
      workDirection: 1,
      categoryId: null,
      categoryName: null,
      authorBooks: [],
      isEditBook: false,
      selectedBook: {},
      editing: false,
      noLogin: false,
      loadingRegist: false,

      bookNameEdit: false,
      isCreateChapter: false,
      newChapterName: null,
      newChapterContent: null,
      chapters: [],
      totalChapters: null,
      chapterContent: null,
      isEditChapter: false,
      editChapterName: null,
      editChapterContent: null,
      oriBook: {},
    }
  },

  watch: {
    selectedBook: {
      handler() {
        this.getChapters();
      },
      deep: true,
    },
  },

  mounted() {
    document.body.classList.add('page-body');
    if (!this.$store.getters.isAuthenticated) {
      this.noLogin = true;
    }
    this.checkisAuthor();
    setTimeout(() => {
      this.getUserInfo();
      this.getAuthorBooks();
      this.loading = false;
      this.showAuthorPage = true;
    }, 1000);
  },

  beforeUnmount() {
    document.body.classList.remove('page-body');
  },

  methods: {
    async deleteChapter() {
      await axios.delete('/api/author/delete_chapter/' + this.editChapterId)
        .catch(error => {
          console.error(error);
        });
      ElMessage.success("Deleted");
      this.getChapters();
    },

    async editChapter() {
      const reqbody = {
        "chapterId": this.editChapterId,
        "bookId": this.selectedBook.id,
        "chapterName": this.editChapterName,
        "chapterContent": this.editChapterContent
      }
      await axios.post('/api/author/update_chapter', reqbody)
        .then(response => {
          if (response.data.code === "00000") {
            ElMessage.success("Updated")
            this.getChapters();
            this.getContent(this.editChapterId);
            this.isEditChapter = false;
          } else if (response.data.code === "B0001") {
            ElMessage.error("Oops, something wrong");
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getContent(id) {
      await axios.get(`/api/front/book/get_content?chapterId=${id}`)
        .then(response => {
          const data = response.data;
          this.chapterContent = data.data.bookContent;
          this.editChapterName = data.data.chapterInfo.chapterName;
          this.editChapterContent = data.data.bookContent;
          this.editChapterId = data.data.chapterInfo.id;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async createChapter() {
      this.newChapterName === null || this.newChapterName === "" ? ElMessage.error("Chapter name is required") :
        this.newChapterContent === null || this.newChapterContent === "" ? ElMessage.error("Chapter content is required") :
          (async () => {
            const reqbody = {
              "bookId": this.selectedBook.id,
              "chapterName": this.newChapterName,
              "chapterContent": this.newChapterContent,
            }
            await axios.post("/api/author/publish_chapter", reqbody)
              .then(response => {
                if (response.data.code === "00000") {
                  ElMessage.success("Chapter Created")
                  this.getAuthorBooks();
                  this.getChapters();
                  this.isCreateChapter = false;
                  this.newChapterName = null;
                  this.newChapterContent = null;
                } else if (response.data.code === "B0001") {
                  ElMessage.error("Oops, something wrong");
                }
              })
              .catch(error => {
                console.error(error);
              });
          })()
    },

    async update_book() {
      this.selectedBook.bookName === null || this.selectedBook.bookName === "" ? ElMessage.error("Book Name is required") :
        this.selectedBook.bookDesc === null || this.selectedBook.bookDesc === "" ? ElMessage.error("Book Description is required") :
          (async () => {
            const reqbody = {
              "bookId": this.selectedBook.id,
              "workDirection": this.selectedBook.workDirection,
              "authorId": this.selectedBook.authorId,
              "categoryId": this.selectedBook.categoryId,
              "categoryName": this.selectedBook.categoryName,
              "picUrl": this.selectedBook.picUrl,
              "bookName": this.selectedBook.bookName,
              "bookDesc": this.selectedBook.bookDesc,
              "bookStatus": this.selectedBook.bookStatus
            }
            await axios.post("/api/author/update_book", reqbody)
              .then(response => {
                if (response.data.code === "00000") {
                  ElMessage.success("Updated")
                  this.getAuthorBooks();
                  this.editing = false;
                  this.goEmpty();
                } else if (response.data.code === "B0001") {
                  ElMessage.error("Oops, something wrong");
                }
              })
              .catch(error => {
                console.error(error);
              });
          })()

    },

    async getChapters() {
      await axios.get(`/api/front/book/chapter/list?bookId=${this.selectedBook.id}`)
        .then(response => {
          const data = response.data;
          this.chapters = data.data.list;
          this.totalChapters = data.data.total;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async createBook() {
      this.imageUrl === null || this.imageUrl === "" ? ElMessage.error("Book cover is required") :
        this.bookName === null || this.bookName === "" ? ElMessage.error("Book name is required") :
          this.bookDesc === null || this.bookDesc === "" ? ElMessage.error("Book description is requried") :
            this.categoryId === null || this.categoryId === "" ? ElMessage.error("Genre not selected") :
              (async () => {
                const reqbody = {
                  "workDirection": this.workDirection,
                  "authorId": this.authorId,
                  "categoryId": this.categoryId,
                  "categoryName": this.categoryName,
                  "picUrl": this.imageUrl,
                  "bookName": this.bookName,
                  "bookDesc": this.bookDesc,
                }
                await axios.post("/api/author/publish_book", reqbody)
                  .then(response => {
                    response.data.code === "A3001" ? ElMessage.error("You already have this book") : response.data.code === "A0400" ? ElMessage.error("Oops, something wrong") :
                      (() => {
                        ElMessage.success("Book Created");
                        this.goEmpty();
                        this.getAuthorBooks();
                        this.workDirection = 1;
                        this.categoryId = null;
                        this.categoryName = null;
                        this.imageUrl = null;
                        this.bookName = null;
                        this.bookDesc = null;
                        this.dirChecked = [true, false];
                        this.genreChecked = [false, false, false, false, false, false, false, false, false, false, false];
                      })()
                  })
                  .catch(error => {
                    console.error(error);
                  });
              })()
    },

    async getAuthorBooks() {
      await axios.get("/api/author/get_books?authorId=" + this.authorId)
        .then(response => {
          this.authorBooks = response.data.data.list;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async update_author() {
      this.updatePenName === null || this.updatePenName === "" ? ElMessage.error("Pen name is required") :
        this.signature === null || this.signature === "" ? ElMessage.error("Signature is required") :
          (async () => {
            const reqbody = {
              "authorId": this.authorId,
              "userId": this.$store.getters.GetUID,
              "penName": this.updatePenName,
              "signature": this.signature,
            }
            await axios.post("/api/author/update", reqbody)
              .catch(error => {
                console.error(error);
              });
            ElMessage.success("Update Successful");
            this.checkisAuthor();
            this.goEmpty();
          })()
    },

    async getUserInfo() {
      const reqbody = {
        "userId": this.$store.getters.GetUID
      }
      await axios.post("/api/front/user/get_userInfo", reqbody)
        .then(response => {
          const data = response.data;
          this.avatar = data.data.userPhoto;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async checkisAuthor() {
      await axios.get("/api/author/get_author_info?userId=" + this.$store.getters.GetUID)
        .then(response => {
          const data = response.data;
          if (data === null) {
            this.isAuthor = false;
          } else {
            this.penName = data.data.penName;
            this.updatePenName = data.data.penName;
            this.signature = data.data.signature;
            this.authorId = data.data.id;
            this.isAuthor = true;
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async register_author() {
      this.penName === null || this.penName === "" ? ElMessage.error("Pen name is required") :
        this.signature === null || this.signature === "" ? ElMessage.error("Signature is required") :
          (async () => {
            const reqbody = {
              "authorId": 0,
              "userId": this.$store.getters.GetUID,
              "penName": this.penName,
              "signature": this.signature,
            }
            await axios.post("/api/author/register", reqbody)
              .catch(error => {
                console.error(error);
              });
            this.checkisAuthor();
            this.loadingRegist = true;
            setTimeout(() => {
              this.loadingRegist = false;
            }, 2000);
          })()
    },

    goCreateBook() {
      this.isEmpty = false;
      this.isAuthorUpdate = false;
      this.isCreateBook = true;
      this.isEditBook = false;
    },

    goUpdateAuthorInfo() {
      this.isEmpty = false;
      this.isAuthorUpdate = true;
      this.isCreateBook = false;
      this.isEditBook = false;
    },

    goEmpty() {
      this.isEmpty = true;
      this.isAuthorUpdate = false;
      this.isCreateBook = false;
      this.isEditBook = false;
    },

    goEditBook(book) {
      this.isEmpty = false;
      this.isAuthorUpdate = false;
      this.isCreateBook = false;
      this.isEditBook = true;
      this.oriBook = book;
      this.selectedBook = JSON.parse(JSON.stringify(book));
    },

    handleUploadClick() {
      this.$nextTick(() => {
        this.$refs.fileInput.click();
      });
    },

    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.previewImage(file);
      }
    },

    previewImage(file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        this.imageUrl = event.target.result;
        this.selectedBook.picUrl = event.target.result;
      };
      reader.readAsDataURL(file);
    },

    dironChange(index) {
      this.dirChecked = this.dirChecked.map(() => false);
      this.dirChecked[index] = true;
      this.workDirection = index + 1;
    },

    genreonChange(index) {
      this.genreChecked = this.genreChecked.map(() => false);
      this.genreChecked[index] = true;
      index === 10 ? this.categoryId = 8 : index >= 7 ? this.categoryId = index + 2 : this.categoryId = index + 1;
      const categoryMap = {
        1: 'action',
        2: 'romance',
        3: 'fantasy',
        4: 'mystery',
        5: 'horror',
        6: 'thriller',
        7: 'drama',
        8: 'other',
        9: 'adventure',
        10: 'humor',
        11: 'poetry',
      };

      this.categoryName = categoryMap[this.categoryId] || null;
    },

    cateIdChangeName() {
      const categoryMap = {
        1: 'action',
        2: 'romance',
        3: 'fantasy',
        4: 'mystery',
        5: 'horror',
        6: 'thriller',
        7: 'drama',
        8: 'other',
        9: 'adventure',
        10: 'humor',
        11: 'poetry',
      };

      this.selectedBook.categoryName = categoryMap[this.selectedBook.categoryId] || null;
    },
  },
}
</script>
<template>
  <div v-loading.fullscreen.lock="loading" element-loading-background="rgba(255, 255, 255, 255)"
    element-loading-spinner=" "></div>
  <div v-loading.fullscreen.lock="loadingRegist" element-loading-background="rgba(255, 255, 255, 255)"
    element-loading-spinner=" "></div>
  <div v-if="noLogin">
    <div class="background">
      <h1
        style="top: 50%; left:50%; transform: translate(-50%,-50%); position: absolute; z-index: 10; text-align: center;">
        YOU ARE NOT SUPPOSE TO BE HERE<br>PLEASE RETURN TO THE HOME PAGE<br>SIGN IN TO CONTINUE</h1>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </div>
  </div>
  <div v-else>
    <div v-show="showAuthorPage" class="authorPageBody">
      <!-- ↓↓↓↓↓Do not touch↓↓↓↓↓ -->
      <div class="content">
        <div class="container">
          <svg id="bongo-cat" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 787.3 433.8">
            <defs>
              <symbol id="eye" data-name="eye" viewBox="0 0 19.2 18.7">
                <circle cx="9.4" cy="9.1" r="8"></circle>
                <path
                  d="M16.3,5.1a1.3,1.3,0,0,1-1.4-.3,7.2,7.2,0,0,0-4.5-2.6A7.2,7.2,0,0,0,5.5,3.5,6.8,6.8,0,0,0,2.8,7.8a6.8,6.8,0,0,0,1,4.8,6.2,6.2,0,0,0,4,2.7,6.1,6.1,0,0,0,4.6-.7,6.7,6.7,0,0,0,2.9-3.7,6.4,6.4,0,0,0-.5-4.5c-.1-.2.8-1,1.5-1.3s2.2,0,2.3.5a9.4,9.4,0,0,1-.2,7.2,9.4,9.4,0,0,1-5.1,5.1,9,9,0,0,1-7,.2A9.6,9.6,0,0,1,1,13.5,9.2,9.2,0,0,1,.4,6.6,8.9,8.9,0,0,1,4.6,1.3,9,9,0,0,1,11.2.2,9.3,9.3,0,0,1,16.7,4C16.9,4.3,17,4.8,16.3,5.1Z">
                </path>
              </symbol>
              <symbol id="paw-pads" data-name="paw-pads" viewBox="0 0 31.4 33.9">
                <path
                  d="M6.8,16a3.7,3.7,0,0,1,1.1,2.8,3.2,3.2,0,0,1-1.6,2.6L5,21.8H4.4a2.8,2.8,0,0,1-1.8.3A4.2,4.2,0,0,1,.2,19.1,7.7,7.7,0,0,1,0,17.6a2.8,2.8,0,0,1,.6-2,3.2,3.2,0,0,1,2.1-.8H4A5,5,0,0,1,6.8,16Zm7.3-4.8a1.8,1.8,0,0,0,.7-.5l.7-.4a3.5,3.5,0,0,0,1.1-1,3.2,3.2,0,0,0,.3-1.4,1.4,1.4,0,0,0-.2-.6,3.4,3.4,0,0,0-.3-2.4,3.2,3.2,0,0,0-2.1-1.5H13.1a4.7,4.7,0,0,0-1.6.4,2,2,0,0,0-.9.9l-.4.6v.4a6.1,6.1,0,0,0-.5,1.2,4.3,4.3,0,0,0,0,1.6,3.5,3.5,0,0,0,.5,2l.7.6a3.3,3.3,0,0,0,1.7.7A3,3,0,0,0,14.1,11.2ZM22.7,7l.6.2h.3A2.3,2.3,0,0,0,25,6.8l.4-.3.6-.3a7.5,7.5,0,0,0,1.5-.9,4.2,4.2,0,0,0,.8-1.2,1.9,1.9,0,0,0,.1-1.5A2.6,2.6,0,0,0,27.5,1,3.5,3.5,0,0,0,23.6.3a3.8,3.8,0,0,0-2,1.5,4.8,4.8,0,0,0-.7,2,3.6,3.6,0,0,0,.9,2.6ZM31,24.1a13.5,13.5,0,0,0-2.2-4.7,36.6,36.6,0,0,0-3.2-3.9,5.3,5.3,0,0,0-5-1.9,10.5,10.5,0,0,0-4.5,2.2A5.6,5.6,0,0,0,13.5,20a15.1,15.1,0,0,0,1.2,6.3c.8,2,1.7,4,2.6,5.9a1.6,1.6,0,0,0,1.5.8,1.7,1.7,0,0,0,1.9.9,17.1,17.1,0,0,0,8.7-4.8,8.2,8.2,0,0,0,1.7-2C31.6,26.3,31.3,25,31,24.1Z"
                  fill="#ef97b0"></path>
              </symbol>
            </defs>
            <g id="head">
              <g id="head__outline">
                <path
                  d="M303.2,186.3c4-7,14.8-20.2,20-26,17-19,34.6-34.9,43-41l12-8s16.6-32,21-33c9-2,33,22,33,22s20-9,79,7c41,11.1,47,14,57,22,7.5,6,18,16,18,16s33.7-19.5,41-15-2,66-2,66,5.9,12.9,11,22c9.1,16.2,13.6,20.2,19,31,3.6,7.2,8.4,28.5,10.5,43.5l-385-62Z"
                  fill="#fff"></path>
                <path
                  d="M302.9,186.9c-1.2,3-5.9,12.6-9,18.8l-12.5,25.5-.6-1.2c32.2,4.8,64.4,9.2,96.6,13.6s64.4,8.9,96.5,13.7,64.3,9.7,96.4,14.9,64.1,10.5,96.2,15.8l-5.6,5.5c-1.2-8.5-2.8-17.1-4.8-25.6-1-4.1-2.1-8.4-3.4-12.3l-.5-1.4-.5-1.4-.6-1.3-.7-1.3a59.5,59.5,0,0,0-3.1-5.5c-2.2-3.6-4.7-7.2-7.1-11s-4.8-7.6-7-11.5c-4.5-7.9-8.3-15.9-12.1-24a4,4,0,0,1-.3-2.6h0c1.4-9.1,2.7-18.2,3.7-27.4.5-4.5.9-9.1,1.2-13.7s.4-9.1.2-13.4a26.4,26.4,0,0,0-.8-6,8.1,8.1,0,0,0-.3-1.1c-.1-.3-.2-.4-.1-.3h.3c0,.1.1.1,0,.1h-.6a11.9,11.9,0,0,0-2.5.2,16.3,16.3,0,0,0-3,.7,56.7,56.7,0,0,0-6.2,2.1,212.6,212.6,0,0,0-24.5,11.9h-.1a3.9,3.9,0,0,1-4.7-.6c-4.9-4.7-10-9.4-15.1-13.8a86.6,86.6,0,0,0-7.9-6,46.1,46.1,0,0,0-8.5-4.6c-6-2.6-12.6-4.6-19.2-6.7l-19.8-5.7a324.9,324.9,0,0,0-40-8.9,196.8,196.8,0,0,0-20.2-1.8c-1.7,0-3.4-.1-5.1,0h-2.5l-2.5.2-2.5.2-2.4.4-2.4.5-1.1.3h-.5l-.4.2H433a2.5,2.5,0,0,1-2.6-.7c-4.6-4.6-9.5-9.1-14.5-13.2a82.7,82.7,0,0,0-7.9-5.7L403.9,81a10.8,10.8,0,0,0-4-.9c-.1,0-.3,0-.3.1h0l-.7.5-1.5,1.7c-1,1.2-2,2.6-2.9,3.9s-3.6,5.5-5.3,8.3c-3.5,5.7-6.8,11.4-9.9,17.3h0l-.4.4-10.2,6.6a53.6,53.6,0,0,0-4.9,3.4l-4.6,3.8c-6.2,5.1-12.1,10.6-17.9,16.2s-11.3,11.4-16.7,17.4c-2.7,3-5.3,6.1-7.8,9.2s-5,6.3-7.4,9.5c-4.2,5.6-7,10-5.7,7.1a34.1,34.1,0,0,1,2.1-3.8l3.8-5.6c2.9-4,6.3-8.3,8.5-10.9s4.4-5.2,6.7-7.7l6.9-7.4c4.7-4.9,9.4-9.7,14.3-14.3s9.8-9.3,15-13.7l4-3.2,4.2-2.9,8.3-5.7-.4.4c3-5.9,6.1-11.8,9.4-17.7,1.6-2.9,3.3-5.8,5.1-8.6l2.9-4.3,1.8-2a7.5,7.5,0,0,1,1.3-1.1c.1-.2.6-.4,1-.5l.9-.2h1.7l1.4.2,2.7.8c1.7.7,3.3,1.5,4.8,2.3a84,84,0,0,1,8.5,5.7A175.7,175.7,0,0,1,434,98.5l-2.9-.6.8-.3.7-.2L434,97l2.7-.7,2.7-.5a23,23,0,0,1,2.6-.3l2.7-.3,2.7-.2h5.3a182.1,182.1,0,0,1,21,1.3,332.5,332.5,0,0,1,41.1,8.4l20,5.5c6.7,2,13.4,4,20.1,6.7a65.3,65.3,0,0,1,9.8,5.1c3.1,2.1,5.9,4.3,8.6,6.5,5.4,4.5,10.6,9.2,15.7,14l-4.8-.6c4.1-2.4,8.2-4.6,12.4-6.7s8.6-4.2,13-6c2.3-.9,4.6-1.7,7-2.4a23.4,23.4,0,0,1,3.8-.9,20,20,0,0,1,4.4-.4h1.3l1.5.4a5.1,5.1,0,0,1,1.7.7l.9.7.8.7a8.3,8.3,0,0,1,1.6,2.6,12.7,12.7,0,0,1,.8,2.3,44.6,44.6,0,0,1,1.1,7.7c.2,5,.1,9.7-.1,14.4s-.7,9.5-1.2,14.1c-.9,9.4-2.1,18.6-3.6,27.9l-.3-2.6c3.7,7.9,7.5,15.8,11.8,23.3,2.1,3.7,4.4,7.4,6.8,11s4.9,7.2,7.3,11.1c1.3,2,2.4,4,3.5,6.1a10.9,10.9,0,0,0,.8,1.5l.8,1.8.7,1.7.6,1.7c1.5,4.4,2.6,8.7,3.7,13.1a262,262,0,0,1,5.2,26.4,4.9,4.9,0,0,1-4.1,5.6h-1.5c-32.1-5-64.2-9.9-96.3-15.1s-64.1-10.6-96.1-16.1-64-11.4-96-17.4-63.9-11.9-95.9-17.4h-.1a.8.8,0,0,1-.6-.9v-.2l16.6-32.1C299.8,192.2,304.1,183.9,302.9,186.9Z">
                </path>
              </g>
              <g id="head__face">
                <g id="eyes">
                  <use width="19.2" height="18.7" transform="translate(474.8 195.2)" xlink:href="#eye"></use>
                  <use width="19.2" height="18.7" transform="matrix(-0.51, -0.85, 0.82, -0.5, 370.39, 192.59)"
                    xlink:href="#eye"></use>
                </g>
                <g id="mouth">
                  <path d="M399.2,186.3c.9,3.6,2.6,7.8,6,9,6.4,2.3,19-6,19-6s4.1,12.4,10,15,10.7-1.7,16-6" fill="#fff">
                  </path>
                  <path
                    d="M450.2,198.3c.6,1.2.2,1.9-.2,2.2a36.7,36.7,0,0,1-7.6,4.9,14.9,14.9,0,0,1-4.8,1.4h-1.4l-1.3-.2-1.4-.4-1.3-.6a21.6,21.6,0,0,1-6.4-7.2,52.8,52.8,0,0,1-4-8.3l3.8,1.3a62.3,62.3,0,0,1-7.1,4.1,32.1,32.1,0,0,1-7.9,2.8,13.2,13.2,0,0,1-4.9.2l-1.4-.3a7.5,7.5,0,0,1-1.3-.6,7.9,7.9,0,0,1-2.3-1.6,16.8,16.8,0,0,1-2.9-4,24.1,24.1,0,0,1-1.6-4.2c-.1-.5,1.6-1.3,3-1.4s3.5.2,3.6.6a10.3,10.3,0,0,0,2.6,4.9l.7.5h2.4l1.5-.2a28.4,28.4,0,0,0,6.5-2c2.1-1,4.3-2.1,6.3-3.3h.1a2.5,2.5,0,0,1,3.4.9l.3.5a43.1,43.1,0,0,0,3.2,7.7,19.8,19.8,0,0,0,2.2,3.4,8.1,8.1,0,0,0,2.6,2.6,5,5,0,0,0,3,.7,10.8,10.8,0,0,0,3.7-1,33.4,33.4,0,0,0,7.2-4.3C448.8,197.4,449.5,197.2,450.2,198.3Z">
                  </path>
                </g>
              </g>
            </g>
            <g id="table">
              <path d="M65.7,181.8l714,124c0,74-2,54-2,128l-673-161Z" fill="#fff"></path>
              <path
                d="M786.7,304.2c-2.7,1.2-10.8,0-16.1-.9L31.1,176.4c-5.2-.9-8.9-3.8-6.2-5s14.3-1.4,19.5-.5L777.1,300.6C782.3,301.6,789.4,303.1,786.7,304.2Z">
              </path>
            </g>
            <g id="laptop">
              <g id="laptop__base">
                <polygon points="641.9 304.1 454.7 348.2 103.8 271.3 254.6 230.3 641.9 304.1" fill="#f2f2f2"></polygon>
                <path
                  d="M641.9,304.1c1.5-.1-2.3,1.5-10.3,3.6-28.9,7.5-58.1,15.2-87.7,22.6s-59.1,14.5-88.4,21.3l-.8.2-.8-.2-349.5-78-1.1-.2-8.7-1.9,8.6-2.3,150.6-41.5.6-.2h.7c62.5,11.7,125.5,23.6,188.4,35.9s125.6,25.1,188,37.6c8,1.6,11.9,3,10.4,3a185.6,185.6,0,0,1-18.4-2.6c-61.9-11.2-123.6-22.2-185-33.5s-122.7-23.1-184.4-35h1.2L104.4,273.4h-.1v-4.3l351.2,75.7h-1.5c28.3-6.7,56.3-13.3,84.3-19.5s56.5-12,85.2-18.1C631.3,305.6,640.4,304.1,641.9,304.1Z"
                  fill="#231f20"></path>
              </g>
              <g id="laptop__keyboard">
                <polygon points="371.1 274.8 256.8 253.5 257 252.7 266.2 251.1 382.4 271.5 382.3 272.3 371.1 274.8"
                  fill="#3e3e54"></polygon>
                <polygon points="237.4 265.6 221.3 262.4 221.4 261.7 230.2 260.2 246.8 262.6 246.6 263.4 237.4 265.6"
                  fill="#3e3e54"></polygon>
                <polygon points="474.6 312.9 249.9 268.1 250.1 267.3 259.2 265.8 487.7 309.6 487.5 310.5 474.6 312.9"
                  fill="#3e3e54"></polygon>
                <polygon points="411.8 309.4 204.2 266.7 204.4 266 212.9 264.5 423.9 306.3 423.7 307.2 411.8 309.4"
                  fill="#3e3e54"></polygon>
                <polygon points="450 317.3 428.5 312.9 428.8 312 440.7 310.6 462.7 314.1 462.5 315 450 317.3"
                  fill="#3e3e54">
                </polygon>
                <polygon points="201.6 273.9 187.5 270.9 187.7 270.2 196 268.7 210.4 271 210.3 271.7 201.6 273.9"
                  fill="#3e3e54"></polygon>
                <polygon points="222.6 278.3 208.1 275.3 208.3 274.5 216.9 273.1 231.8 275.4 231.6 276.2 222.6 278.3"
                  fill="#3e3e54"></polygon>
                <polygon points="362.9 308.1 231.5 280.2 231.7 279.5 240.7 278.1 374.2 305.1 374 305.9 362.9 308.1"
                  fill="#3e3e54"></polygon>
                <polygon points="444.3 288.4 385.2 277.4 385.4 276.5 396.6 274.9 456.9 285.1 456.7 285.9 444.3 288.4"
                  fill="#3e3e54"></polygon>
                <polygon points="526.1 303.6 460.1 291.3 460.3 290.4 472.8 288.9 540.1 300.2 539.9 301.1 526.1 303.6"
                  fill="#3e3e54"></polygon>
                <polygon points="426.2 321.6 376.1 310.9 376.3 310.1 387.4 308.7 438.5 318.5 438.3 319.4 426.2 321.6"
                  fill="#3e3e54"></polygon>
                <g>
                  <polygon points="410.6 286.5 399.1 288 398.9 288.8 499.9 308.3 513.3 305.9 513.5 305 410.6 286.5"
                    fill="#3e3e54"></polygon>
                  <polygon points="395.7 283.7 395.9 282.8 248.2 255.7 239.2 257.3 239 258 384.3 286 395.7 283.7"
                    fill="#3e3e54"></polygon>
                </g>
                <polygon points="371.3 273.9 256.9 252.7 266.4 250.3 382.4 271.5 371.3 273.9" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="237.6 264.9 221.4 261.7 230.4 259.4 246.8 262.6 237.6 264.9" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="474.8 312 250 267.3 259.4 265.1 487.7 309.6 474.8 312" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="412 308.5 204.4 266 213.1 263.8 423.9 306.3 412 308.5" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="450.2 316.4 428.8 312 440.9 309.7 462.8 314.1 450.2 316.4" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="201.7 273.1 187.7 270.2 196.2 268 210.4 271 201.7 273.1" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="222.8 277.6 208.3 274.5 217.1 272.4 231.8 275.4 222.8 277.6" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="363.1 307.3 231.7 279.5 240.9 277.3 374.2 305.1 363.1 307.3" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="444.6 287.5 385.4 276.5 396.8 274.1 456.9 285 444.6 287.5" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="526.3 302.7 460.3 290.4 473 288 540.1 300.2 526.3 302.7" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <polygon points="426.4 320.7 376.3 310.1 387.6 307.9 438.5 318.5 426.4 320.7" stroke="#000"
                  stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                <g>
                  <polygon points="410.7 285.6 399.1 288 500.1 307.4 513.5 305 410.7 285.6" stroke="#000"
                    stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                  <polygon points="395.9 282.8 248.4 255 239.2 257.3 384.5 285.2 395.9 282.8" stroke="#000"
                    stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"></polygon>
                </g>
              </g>
              <g id="paw-right">
                <g id="paw-right--down">
                  <path
                    d="M293.2,191.3l10-7s-18.4,11.1-24,20-13,20.4-9,31c4.7,12.4,20.5,15.7,22,16,20,3.8,47.8-24.3,47.8-24.3s1.9-3.3,2.2-3.7"
                    fill="#fff"></path>
                  <path
                    d="M342.1,223.4c.9,1.2.2,2.8-.3,3.7l-.4.7-.3.3a118.1,118.1,0,0,1-14.2,12.3,83.2,83.2,0,0,1-16.2,9.8,43.9,43.9,0,0,1-9.3,3,26.3,26.3,0,0,1-10.1.2,44.5,44.5,0,0,1-9.3-3.2,34.2,34.2,0,0,1-8.3-5.5,23,23,0,0,1-5.8-8.5,21.3,21.3,0,0,1-1.3-10.3,34.9,34.9,0,0,1,2.7-9.7,76.1,76.1,0,0,1,4.5-8.5l2.4-4,.6-1,.8-1.1a15.6,15.6,0,0,1,1.6-2,49.9,49.9,0,0,1,7-6.8,136.1,136.1,0,0,1,15.3-11.2,3.1,3.1,0,0,1,4.4,1,3,3,0,0,1-.8,4.2H305l-8.6,6.2c-.9.6-2.7-.5-3.1-1.9s.5-4.4,1.5-5l6.6-4.5,3.5,5.3A131.9,131.9,0,0,0,290,197.4a52.7,52.7,0,0,0-6.4,6,6.5,6.5,0,0,0-1.3,1.6l-.6.8-.7,1-2.4,3.8c-1.6,2.6-3.1,5.2-4.4,7.8a27.7,27.7,0,0,0-2.4,8.1,15.6,15.6,0,0,0,.8,8,17.4,17.4,0,0,0,4.4,6.7,27.2,27.2,0,0,0,7.1,4.9,39.5,39.5,0,0,0,8.1,3,21.6,21.6,0,0,0,8.4,0,37.8,37.8,0,0,0,8.5-2.6,84.9,84.9,0,0,0,15.7-9,142.4,142.4,0,0,0,14.1-11.6l-.3.3,1.1-1.8C340.3,223.4,341.3,222.2,342.1,223.4Z">
                  </path>
                </g>
                <g id="paw-right--up">
                  <g>
                    <path
                      d="M282.2,215.2c-1.6-1.6-12.8-17.9-14-34.3-.1-2.5,1.7-16,12.9-22.4s22.3-1.9,26.2.4c12.2,7.3,21.2,19.1,22.8,22.4"
                      fill="#fff"></path>
                    <path
                      d="M330,181.2a2.4,2.4,0,0,1-2.6-1.3,71.4,71.4,0,0,0-9.8-10.8,64,64,0,0,0-11.7-8.6,26.3,26.3,0,0,0-6.5-2.3,26.9,26.9,0,0,0-6.9-.6,24.9,24.9,0,0,0-6.7,1.3,20.8,20.8,0,0,0-5.8,3.3,23.1,23.1,0,0,0-7.6,11,32.5,32.5,0,0,0-1.4,6.6,6.6,6.6,0,0,0,.1,1.4l.2,1.8c.1,1.2.4,2.3.6,3.5a65,65,0,0,0,4.8,13.4c1,2.2,2.2,4.3,3.4,6.4a43.1,43.1,0,0,0,3.9,5.9.6.6,0,0,1,0,.6c0,.2-.2.4-.4.7a5.7,5.7,0,0,1-1.5,1.6c-1.3.6-4.1.1-4.6-.6a89.5,89.5,0,0,1-7.2-13.7,63.7,63.7,0,0,1-4.3-14.9,25.7,25.7,0,0,1-.5-4c0-.3-.1-.6-.1-1v-1.2a12.5,12.5,0,0,1,.2-2.1,35.2,35.2,0,0,1,2.4-7.8,28.6,28.6,0,0,1,4.1-6.9,24.6,24.6,0,0,1,6.1-5.5,26.2,26.2,0,0,1,15.5-4.2,28.9,28.9,0,0,1,7.8,1.2l3.8,1.3,1.8.9,1.8,1a78.2,78.2,0,0,1,11.9,9.6,80.2,80.2,0,0,1,9.7,11.8C331.1,179.7,331.4,181,330,181.2Z">
                    </path>
                  </g>
                  <use width="31.4" height="33.93" transform="translate(273.2 166.1) rotate(-5.6)" xlink:href="#paw-pads">
                  </use>
                </g>
              </g>
              <g id="laptop__terminal">
                <path
                  d="M316.9,238.7,153.5,205.2a5.1,5.1,0,0,1-4-3.5L109.8,75.4c-1-3.3,1.9-6.6,5.6-6.3L277.9,84.5a5.2,5.2,0,0,1,4.6,3.7l40.7,144.4C324.2,236.2,320.8,239.5,316.9,238.7Z">
                </path>
                <path
                  d="M317.3,238.7a7.9,7.9,0,0,0,2.2-.7,5,5,0,0,0,2.2-1.9,3.7,3.7,0,0,0,.6-2.9l-.3-.8-.2-.9a15.4,15.4,0,0,1-.5-1.7L300,154.6l-10.7-37.5L284,98.3l-2.6-9.4a7.9,7.9,0,0,0-.4-.9,4.3,4.3,0,0,0-.4-.7,3.3,3.3,0,0,0-1.5-1.1l-.9-.3h-1.1l-2.4-.2L119.2,71.2l-2.4-.3h-2.2a3.3,3.3,0,0,0-2.8,1.6,2.4,2.4,0,0,0-.5,1.4v.8c.1.1.1.2.1.4l.2.6,1.5,4.6L119,98.8l11.8,37.3,11.7,37.2,5.9,18.6,2.9,9.3a3.4,3.4,0,0,0,2.2,2h1l1.2.3,2.4.4,153,31.1c4.3.9,7.4,2.9,5.2,3.3s-11.7-.1-16-1l-75.8-15.7L186.6,214l-19-3.9-9.5-2-4.7-1h-.7l-.8-.3a6.1,6.1,0,0,1-1.4-.7,7.6,7.6,0,0,1-2.3-2.4l-.4-.8a1.9,1.9,0,0,1-.2-.7l-.4-1.2-.7-2.3-1.4-4.6-2.9-9.2-5.8-18.5-11.5-36.9-11.5-37-2.9-9.2L109,78.5l-.7-2.3v-.6c0-.3-.1-.6-.1-.8a4.8,4.8,0,0,1,0-1.7,6.8,6.8,0,0,1,3.8-5,10.1,10.1,0,0,1,3-.7h2.6l9.6,1L204.1,76l38.5,3.7,19.3,1.9,9.6.9,4.8.5h2.6a6.6,6.6,0,0,1,2.7,1.2,7.2,7.2,0,0,1,1.9,2.4,12.1,12.1,0,0,1,.5,1.4l.3,1.1,1.3,4.7,2.6,9.3,5.2,18.6,10.4,37.3,10.4,37.3,5.3,18.6,2.6,9.4,1.3,4.6.6,2.4a7,7,0,0,1,.4,2.7,5.7,5.7,0,0,1-1.8,3.7,5.9,5.9,0,0,1-3.4,1.6,3.5,3.5,0,0,1-2.1-.4C316.7,239,316.8,238.9,317.3,238.7Z">
                </path>
              </g>
              <g id="laptop__terminal_code_scene">
                <g id="laptop__code">
                  <g stroke="#3DE0E8" stroke-width="6" transform="matrix(-1 0 0 1 278 103)">
                    <g id="f3" transform="translate(0 76)">
                      <path class="typing-animation" id="f3-l9" d="M8,25L8,25" stroke-dasharray="60,10"></path>
                      <path class="typing-animation" id="f3-l8" d="M8,13L8,13" stroke-dasharray="50,10"></path>
                      <path class="typing-animation" id="f3-l7" d="M0,1L0,1" stroke-dasharray="25,10"></path>
                    </g>
                    <g id="f2" transform="translate(0 38)">
                      <path class="typing-animation" id="f2-l6" d="M8,25L8,25" stroke-dasharray="40,10"></path>
                      <path class="typing-animation" id="f2-l5" d="M8,13L8,13" stroke-dasharray="60,10"></path>
                      <path class="typing-animation" id="f2-l4" d="M0,1L0,1" stroke-dasharray="30,10"></path>
                    </g>
                    <g id="f1">
                      <path class="typing-animation" id="f1-l3" d="M8,25L8,25" stroke-dasharray="60,10"></path>
                      <path class="typing-animation" id="f1-l2" d="M8,13L8,13" stroke-dasharray="60,10"></path>
                      <path class="typing-animation" id="f1-l1" d="M0,1L0,1" stroke-dasharray="60,10"></path>
                    </g>
                  </g>
                </g>
              </g>
              <g id="laptop__cover" style="mix-blend-mode: hard-light;">
                <polygon points="440.7 347.2 90.3 275.6 4.7 3.8 353 36.7 440.7 347.2" fill="#f2f2f2"></polygon>
                <path
                  d="M440.4,346.4c-2.5-5.3-6.5-18.8-9-27.4L390.7,178c-13.6-46.8-26.9-93.7-40.3-140.6l2.3,2L4.4,7.1,7.9,2.8,94,274.5l-2.9-2.6q83.7,16.8,166.8,34.2t166.8,35.4c8.8,1.9,17.5,5.1,14.7,5.5s-6.3-.2-12-.9-12.3-1.5-16.8-2.3Q330.5,328.1,250,312.1c-53.5-10.8-107.1-21.7-160.4-32.7l-2.3-.5-.6-2.1L1.5,4.8,0,0,5,.5,353.3,34l1.8.2.5,1.8q20.7,73.8,41.2,147.8l40.6,147.5C439.8,340.1,442.9,351.7,440.4,346.4Z">
                </path>
              </g>
            </g>
            <g id="paw-left">
              <g id="paw-left--up">
                <g>
                  <path
                    d="M545.4,261.9c-7.1-13-12.9-31.1-13.3-37.6-.6-9,0-15.6,5.2-22.2s15-9.8,22.7-8.8a26.7,26.7,0,0,1,17.3,9.4c5.3,5.8,9.4,12.9,11.6,16.6"
                    fill="#fff"></path>
                  <path
                    d="M588.9,219.2c-1.4.4-2.3-.7-2.8-1.4a93.9,93.9,0,0,0-8.9-12.5c-3.3-3.9-7.1-7-11.7-8.6a24.2,24.2,0,0,0-7.1-1.4,24.5,24.5,0,0,0-7.1.7,27,27,0,0,0-6.6,2.7,21,21,0,0,0-5.2,4.6,20.6,20.6,0,0,0-3.5,6.1,22.2,22.2,0,0,0-1.3,6.9,47.3,47.3,0,0,0,.1,7.5,52.2,52.2,0,0,0,1.4,7.1c1.4,4.8,3.1,9.7,5,14.4a147.7,147.7,0,0,0,6.5,13.9c.4.7-1,2.3-2.4,2.6s-4-.6-4.4-1.4c-2.3-4.8-4.3-9.7-6.1-14.6a128.8,128.8,0,0,1-4.6-15.3c-.3-1.3-.5-2.6-.7-4a16.4,16.4,0,0,1-.2-2.2v-2a57,57,0,0,1,.4-8.2,27.2,27.2,0,0,1,2.3-8.2c.7-1.3,1.4-2.5,2.2-3.7l1.3-1.7,1.4-1.6a28.8,28.8,0,0,1,7-5,27.6,27.6,0,0,1,8-2.5,25.6,25.6,0,0,1,8.3-.2,27.4,27.4,0,0,1,15.1,6.7,50.6,50.6,0,0,1,5.5,5.9,111.3,111.3,0,0,1,8.7,13.2C589.8,217.7,590.3,218.9,588.9,219.2Z">
                  </path>
                </g>
                <use width="31.4" height="33.93" transform="matrix(0.99, -0.03, 0.04, 1, 539.85, 203.52)"
                  xlink:href="#paw-pads"></use>
              </g>
              <g id="paw-left--down">
                <path
                  d="M538.2,239.3c-3.2,1.6-33,10.8-37,28-.4,1.8-2.1,18.9,7,26,5.5,4.3,12.7,2.8,25,0,10.3-2.3,19-5.8,40-16,9.1-4.4,16.6-8.2,22-11"
                  fill="#fff"></path>
                <path
                  d="M595.1,266.4c.1,1.4-1.4,2.4-2.4,2.9l-18.3,9.4c-6.2,3.1-12.3,6.1-18.6,9a120.8,120.8,0,0,1-19.6,7.2l-5.1,1.2-5.1,1.1a43.4,43.4,0,0,1-5.2.9,33.8,33.8,0,0,1-5.6.3,17.8,17.8,0,0,1-5.8-1.5,6.1,6.1,0,0,1-1.4-.7l-1.3-.9-2.2-2a23.6,23.6,0,0,1-5.2-10.2,44.5,44.5,0,0,1-1.3-10.9c0-.9.1-1.8.1-2.7a6.6,6.6,0,0,0,.1-1.4v-.7c.1-.3.1-.7.2-.9a21.6,21.6,0,0,1,2.1-5.5,33.4,33.4,0,0,1,7.1-8.7,67.1,67.1,0,0,1,8.7-6.4,121.7,121.7,0,0,1,19-9,1.5,1.5,0,0,1,1.7.6,3.4,3.4,0,0,1,.9,1.9c.1,1.5-1.6,4.2-2.6,4.6a91.1,91.1,0,0,0-17.8,8.5,40.1,40.1,0,0,0-7.6,5.8,22.8,22.8,0,0,0-5.2,7.3l-.4,1-.3,1a1.7,1.7,0,0,0-.2.5v.4c-.1.4-.1.8-.2,1.2s-.1,3.1-.1,4.7a35.4,35.4,0,0,0,1.4,9.3,15.6,15.6,0,0,0,4.5,7.3c2,1.9,4.7,2.6,7.8,2.5a55.9,55.9,0,0,0,9.7-1.2l4.9-1.1,4.9-1.1a121,121,0,0,0,18.8-6.8c12.4-5.3,24.6-11.5,36.8-17.4C593.4,265.4,595,264.9,595.1,266.4Z">
                </path>
              </g>
            </g>
          </svg>
        </div>
      </div>
      <!-- ↑↑↑↑↑Do not touch↑↑↑↑↑ -->
      <div v-if="!isAuthor" class="registerForm">
        <div class="logo_container">
          <img src="..\assets\logo1.png" class="logo">
          <h1 class="novelhub_name">
            NovelHub</h1>
        </div>
        <h1 style=" display: flex; justify-content: space-between; align-items: center;">
          Author Register
          <img :src="avatar"
            style="border: 1px solid; border-radius: 50px; height: 60px; width: 60px; object-fit: cover;" />
        </h1>
        <el-divider />
        <div style="display: flex; flex-direction: column;">
          <div style="display: flex;">
            <div style="width: 150px; font-size: 14pt; font-weight: bold;">Pen Name:</div>
            <el-input v-model="penName" style="font-size: 14pt;"></el-input>
          </div>
          <div style="display: flex; margin-top: 20px;">
            <div style="width: 150px; font-size: 14pt; font-weight: bold;">Signature:</div>
            <el-input v-model="signature" :rows="4" type="textarea"></el-input>
          </div>
          <div style="display: flex; justify-content: center; margin-top: 20px;">
            <el-button type="primary" @click='register_author' style="width: 100px;">
              CONTINUE
            </el-button>
          </div>
        </div>
      </div>
      <div v-else style="height: 100%;">
        <el-row style="height: 100%;">
          <el-col :span="6" class="navbarAuthor">
            <el-menu>
              <div class="dashboard" @click="goEmpty">Dashboard
                <!-- ↓↓↓↓↓Do not touch↓↓↓↓↓ -->
                <svg class="book-shelf" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid"
                  viewBox="0 0 60 60" height="50" width="40">
                  <path fill="none"
                    d="M37.612 92.805L4.487 73.71c-2.75-1.587-4.45-4.52-4.45-7.687L.008 27.877c-.003-3.154 1.676-6.063 4.405-7.634L37.558 1.167c2.73-1.57 6.096-1.566 8.835.013l33.124 19.096c2.75 1.586 4.45 4.518 4.45 7.686l.028 38.146c.002 3.154-1.677 6.063-4.406 7.634L46.445 92.818c-2.73 1.57-6.096 1.566-8.834-.013z" />
                  <g class="book-shelf__book book-shelf__book--one" fill-rule="evenodd">
                    <path fill="#5199fc"
                      d="M31 29h4c1.105 0 2 .895 2 2v29c0 1.105-.895 2-2 2h-4c-1.105 0-2-.895-2-2V31c0-1.105.895-2 2-2z" />
                    <path fill="#afd7fb"
                      d="M34 36h-2c-.552 0-1-.448-1-1s.448-1 1-1h2c.552 0 1 .448 1 1s-.448 1-1 1zm-2 1h2c.552 0 1 .448 1 1s-.448 1-1 1h-2c-.552 0-1-.448-1-1s.448-1 1-1z" />
                  </g>
                  <g class="book-shelf__book book-shelf__book--two" fill-rule="evenodd">
                    <path fill="#ff9868"
                      d="M39 34h6c1.105 0 2 .895 2 2v24c0 1.105-.895 2-2 2h-6c-1.105 0-2-.895-2-2V36c0-1.105.895-2 2-2z" />
                    <path fill="#d06061" d="M42 38c1.105 0 2 .895 2 2s-.895 2-2 2-2-.895-2-2 .895-2 2-2z" />
                  </g>
                  <g class="book-shelf__book book-shelf__book--three" fill-rule="evenodd">
                    <path fill="#ff5068"
                      d="M49 32h2c1.105 0 2 .86 2 1.92v25.906c0 1.06-.895 1.92-2 1.92h-2c-1.105 0-2-.86-2-1.92V33.92c0-1.06.895-1.92 2-1.92z" />
                    <path fill="#d93368"
                      d="M50 35c.552 0 1 .448 1 1v2c0 .552-.448 1-1 1s-1-.448-1-1v-2c0-.552.448-1 1-1z" />
                  </g>
                  <g fill-rule="evenodd">
                    <path class="book-shelf__shelf" fill="#ae8280"
                      d="M21 60h40c1.105 0 2 .895 2 2s-.895 2-2 2H21c-1.105 0-2-.895-2-2s.895-2 2-2z" />
                    <path fill="#855f6d"
                      d="M51.5 67c-.828 0-1.5-.672-1.5-1.5V64h3v1.5c0 .828-.672 1.5-1.5 1.5zm-21 0c-.828 0-1.5-.672-1.5-1.5V64h3v1.5c0 .828-.672 1.5-1.5 1.5z" />
                  </g>
                </svg>
                <!-- ↑↑↑↑↑Do not touch↑↑↑↑↑ -->
              </div>
              <div class="authorBox" @click="goUpdateAuthorInfo">
                <img :src="avatar"
                  style="border: 1px solid; border-radius: 50px; height: 80px; width: 80px; object-fit: cover;" />
                <div style="margin-left: 20px;">
                  <h3>{{ penName }}</h3>
                </div>
              </div>
              <div style="padding: 20px;">
                <el-button @click="goCreateBook" type="primary" :icon="Plus" style="width: 100%; font-size: large;">Create
                  Book</el-button>
                <el-switch width="65px" v-model="editing"
                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #e6a23c; margin-top:20px;" size="large"
                  active-text="Edit Mode" inactive-text="View Mode" />
              </div>
              <el-sub-menu index="index0" teleported>
                <template #title>
                  <span>My Books</span>
                </template>
                <el-menu-item v-for="(item, index) in authorBooks" :key="index" :index="item.id"
                  @click="goEditBook(item)">
                  {{ item.bookName }}
                </el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-col>
          <el-col v-show="isEmpty" :span="18" class="emptyAuthor">
            <h1 style="margin-right: 20px; text-align: center;">
              Choose<br>A<br>Book<br>Maybe?
              <br><br>OR<br><br>
              Create<br>One?
            </h1>
            <!-- ↓↓↓↓↓Do not touch↓↓↓↓↓ -->
            <div class="container">
              <div class="window">
                <div class="window-details"></div>
                <div class="mountains-left"></div>
                <div class="mountains-right"></div>
                <div class="moon"></div>
                <div class="star star-glow star-1"></div>
                <div class="star star-glow star-2"></div>
                <div class="star star-3"></div>
                <div class="star star-4"></div>
                <div class="star star-5"></div>
                <div class="clouds">
                  <span></span>
                  <span></span>
                </div>
                <div class="cat">
                  <div class="cat-body">
                    <div class="cat-ears"></div>
                  </div>
                  <div class="cat-belly"></div>
                </div>
              </div>
            </div>
            <!-- ↑↑↑↑↑Do not touch↑↑↑↑↑ -->
          </el-col>
          <el-col v-show="isAuthorUpdate" :span="18" class="authorUpdateSection">
            <h1>Author Information Edit</h1>
            <div style="display: flex; flex-direction: column;">
              <div style="display: flex;">
                <div style="width: 150px; font-size: 14pt; font-weight: bold;">Pen Name:</div>
                <el-input v-model="updatePenName" style="font-size: 14pt;"></el-input>
              </div>
              <div style="display: flex; margin-top: 20px;">
                <div style="width: 150px; font-size: 14pt; font-weight: bold;">Signature:</div>
                <el-input v-model="signature" :rows="4" type="textarea"></el-input>
              </div>
              <div style="display: flex; justify-content: center; margin-top: 20px;">
                <el-button type="primary" @click='update_author' style="width: 100px;">
                  Update
                </el-button>
              </div>
            </div>
          </el-col>
          <el-col v-show="isCreateBook" :span="18" class="createBookSection">
            <h1>Book Creation</h1>
            <div>
              <h2>Cover</h2>
              <div>
                <div>
                  <img
                    :src="imageUrl ? imageUrl : 'https://www.colorbook.io/imagecreator.php?hex=ffffff&width=1920&height=1080&text=%201920x1080'"
                    style="height: 300px; width: 187.5px; border-radius: 10px; border: 1px solid #dcdfe6;" />
                </div>

                <input type="file" ref="fileInput" @change="handleFileChange" accept="image/*" style="display: none" />
                <el-button type="primary" :icon="Upload" @click="handleUploadClick">
                  UPLOAD
                </el-button>
              </div>
            </div>
            <div>
              <h2>Name</h2>
              <el-input v-model="bookName"></el-input>
            </div>
            <div>
              <h2>Description</h2>
              <el-input v-model="bookDesc" :rows="4" type="textarea"></el-input>
            </div>
            <div>
              <h2>Work Direction</h2>
              <el-check-tag class="tags" :checked="dirChecked[0]" @click="dironChange(0)">Male Lead</el-check-tag>
              <el-check-tag class="tags" :checked="dirChecked[1]" @click="dironChange(1)">Female
                Lead</el-check-tag>
            </div>
            <div>
              <h2>Genre</h2>
              <el-check-tag class="tags Action" :checked="genreChecked[0]" @click="genreonChange(0)">Action</el-check-tag>
              <el-check-tag class="tags Romance" :checked="genreChecked[1]"
                @click="genreonChange(1)">Romance</el-check-tag>
              <el-check-tag class="tags Fantasy" :checked="genreChecked[2]"
                @click="genreonChange(2)">Fantasy</el-check-tag>
              <el-check-tag class="tags Mystery" :checked="genreChecked[3]"
                @click="genreonChange(3)">Mystery</el-check-tag>
              <el-check-tag class="tags Horror" :checked="genreChecked[4]" @click="genreonChange(4)">Horror</el-check-tag>
              <el-check-tag class="tags Thriller" :checked="genreChecked[5]"
                @click="genreonChange(5)">Thriller</el-check-tag>
              <el-check-tag class="tags Drama" :checked="genreChecked[6]" @click="genreonChange(6)">Drama</el-check-tag>
              <el-check-tag class="tags Adventure" :checked="genreChecked[7]"
                @click="genreonChange(7)">Adventure</el-check-tag>
              <el-check-tag class="tags Humor" :checked="genreChecked[8]" @click="genreonChange(8)">Humor</el-check-tag>
              <el-check-tag class="tags Poetry" :checked="genreChecked[9]" @click="genreonChange(9)">Poetry</el-check-tag>
              <el-check-tag class="tags Other" :checked="genreChecked[10]" @click="genreonChange(10)">Other</el-check-tag>
            </div>
            <div style="margin-top: 20px;">
              <el-button style="width: 100%; font-size: 16pt;" :icon="Finished" type="primary" size="large"
                @click="createBook">Create</el-button>
            </div>
          </el-col>
          <el-col v-show="isEditBook" :span="18" class="editBookSection">
            <div class="elTabs">
              <el-tabs>
                <el-tab-pane label="Book">
                  <div style="width: 100%; display:flex; justify-content:center;">
                    <img v-show="editing" :src="selectedBook.picUrl"
                      style="height:300px; width: 187.5px; border-radius: 10px;">
                    <img v-show="!editing" :src="oriBook.picUrl"
                      style="height:300px; width: 187.5px; border-radius: 10px;">
                  </div>
                  <div v-show="editing">
                    <input type="file" ref="fileInput" @change="handleFileChange" accept="image/*"
                      style="display: none" />
                    <el-button type="primary" style="width: 100%; margin-top: 10px;" :icon="Upload"
                      @click="handleUploadClick">
                      REPLACE COVER
                    </el-button>
                  </div>
                  <el-descriptions direction="vertical" :column="2" border>

                    <el-descriptions-item label="Name">
                      <div v-show="!editing">
                        {{ oriBook.bookName }}
                      </div>
                      <el-input v-model="selectedBook.bookName" v-show="editing"></el-input>
                    </el-descriptions-item>

                    <el-descriptions-item label="Work Direction">
                      <div v-show="!editing">
                        {{ oriBook.workDirection === '1' ? "Male Lead" : "Female Lead" }}
                      </div>
                      <el-radio-group v-show="editing" v-model="selectedBook.workDirection" size="small">
                        <el-radio label="1" size="large" border>Male Lead</el-radio>
                        <el-radio label="2" size="large" border>Female Lead</el-radio>
                      </el-radio-group>
                    </el-descriptions-item>

                    <el-descriptions-item label="Description" :span="2">
                      <div v-show="!editing">
                        {{ oriBook.bookDesc }}
                      </div>
                      <el-input v-model="selectedBook.bookDesc" v-show="editing" :autosize="{ minRows: 2, maxRows: 6 }"
                        type="textarea"></el-input>
                    </el-descriptions-item>

                    <el-descriptions-item label="Genre" :span="2">
                      <div v-show="!editing">
                        {{ oriBook.categoryName }}
                      </div>
                      <el-radio-group v-show="editing" v-model="selectedBook.categoryId" @change="cateIdChangeName"
                        size="small" class="genreGroup">
                        <el-radio-button label="1">Action</el-radio-button>
                        <el-radio-button label="2">Romance</el-radio-button>
                        <el-radio-button label="3">Fantasy</el-radio-button>
                        <el-radio-button label="4">Mystery</el-radio-button>
                        <el-radio-button label="5">Horror</el-radio-button>
                        <el-radio-button label="6">Thriller</el-radio-button>
                        <el-radio-button label="7">Drama</el-radio-button>
                        <el-radio-button label="9">Adventure</el-radio-button>
                        <el-radio-button label="10">Humor</el-radio-button>
                        <el-radio-button label="11">Poetry</el-radio-button>
                        <el-radio-button label="8">Other</el-radio-button>
                      </el-radio-group>
                    </el-descriptions-item>

                    <el-descriptions-item label="Book status">
                      <div v-show="!editing">
                        {{ oriBook.bookStatus === "0" ? "Ongoing" : "Completed" }}
                      </div>
                      <el-radio-group v-show="editing" v-model="selectedBook.bookStatus" size="small">
                        <el-radio label="0" size="large" border>Ongoing</el-radio>
                        <el-radio label="1" size="large" border>Completed</el-radio>
                      </el-radio-group>
                    </el-descriptions-item>

                    <el-descriptions-item label="Word">
                      {{ oriBook.wordCount }}
                    </el-descriptions-item>

                    <el-descriptions-item label="Collected">
                      {{ oriBook.collectCount }}
                    </el-descriptions-item>

                    <el-descriptions-item label="Commented">
                      {{ oriBook.commentCount }}
                    </el-descriptions-item>

                    <el-descriptions-item label="Visited">
                      {{ oriBook.visitCount }}
                    </el-descriptions-item>

                    <el-descriptions-item label="Score">
                      {{ oriBook.score }}
                    </el-descriptions-item>

                  </el-descriptions>
                  <div style="margin-top: 10px;">
                    <el-button v-show="editing" type="success" style="width: 100%;"
                      @click="update_book">Submit</el-button>
                  </div>
                </el-tab-pane>

                <el-tab-pane label="Chapter">
                  <el-button :icon="Plus" type="primary" @click="isCreateChapter = true" style="width: 100%;">Create
                    Chapter</el-button>

                  <div v-if="chapters.length > 0">
                    <h2>{{ totalChapters }} Total</h2>
                    <el-collapse accordion>
                      <el-collapse-item v-for="(item, index) in chapters" :key="index" style="position: relative;">
                        <template #title>
                          <h3 style="width:100%;" @click="getContent(item.id)">{{ item.chapterName }}</h3>
                        </template>
                        <div style="right:40px; top:6px; position: absolute;">
                          <el-button v-show="editing" @click="isEditChapter = true" type="primary" :icon="Edit" circle />
                          <el-popconfirm title="Are you sure to delete this?" @confirm="deleteChapter">
                            <template #reference>
                              <el-button v-show="editing" type="danger" :icon="Delete" circle />
                            </template>
                          </el-popconfirm>

                        </div>

                        <div>{{ chapterContent }}</div>
                        <el-dialog v-model="isEditChapter" width="60%" @close-auto-focus="isEditChapter = false">
                          <template #header>
                            <div style="font-size: 20pt; font-weight: bold; margin-bottom: -30px;">Chapter Editing</div>
                          </template>
                          <div>
                            <h2>Name:</h2>
                            <el-input v-model="editChapterName"></el-input>
                          </div>
                          <div>
                            <h2>Content:</h2>
                            <el-input v-model="editChapterContent" :autosize="{ minRows: 20, maxRows: 20 }"
                              type="textarea"></el-input>
                          </div>
                          <template #footer>
                            <span class="dialog-footer">
                              <el-button @click="isEditChapter = false">Cancel</el-button>
                              <el-button type="primary" @click="editChapter">
                                Submit
                              </el-button>
                            </span>
                          </template>
                        </el-dialog>
                      </el-collapse-item>
                    </el-collapse>
                  </div>
                  <div v-else>
                    <h2>No chapters</h2>
                  </div>
                  <el-dialog v-model="isCreateChapter" width="60%">
                    <template #header>
                      <div style="font-size: 20pt; font-weight: bold; margin-bottom: -30px;">Chapter Creation</div>
                    </template>
                    <div>
                      <h2>Name:</h2>
                      <el-input v-model="newChapterName"></el-input>
                    </div>
                    <div>
                      <h2>Content:</h2>
                      <el-input v-model="newChapterContent" :autosize="{ minRows: 20 }" type="textarea"></el-input>
                    </div>
                    <template #footer>
                      <span class="dialog-footer">
                        <el-button @click="isCreateChapter = false">Cancel</el-button>
                        <el-button type="primary" @click="createChapter">
                          Submit
                        </el-button>
                      </span>
                    </template>
                  </el-dialog>

                </el-tab-pane>
              </el-tabs>
            </div>
            <!-- <el-button type="danger" @click="deleteBook(selectedBook.id)">Delete</el-button> -->
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<style>
.page-body {
  height: 96vh;
}

.genreGroup .el-radio-button--small .el-radio-button__inner {
  font-size: 12.7px;
}

.elTabs .el-tabs--top .el-tabs__item.is-top:nth-child(2),
.el-tabs--top .el-tabs__item.is-top:last-child {
  font-size: 16pt;
  margin-top: -10px;
}

.chaptersDisplay {
  background-color: #f6f6f6;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  font-size: 10pt;
  width: 100%;
  padding: 20px;
  position: relative;
}

.custom-file-label:focus {
  outline: none;
}

.dashboard {
  font-size: 24pt;
  font-weight: bold;
}

.dashboard:hover {
  cursor: pointer;
}

.authorBox {
  display: flex;
  align-items: center;
  padding: 10px;
  margin: 10px;
  border-radius: 50px;
  background-color: transparent;
  transition: background-color 0.5s;
}

.authorBox:hover {
  cursor: pointer;
  background-color: #f6f6f6;
}

.authorUpdateSection,
.createBookSection,
.emptyAuthor,
.editBookSection {
  padding: 20px;
  overflow: auto;
  height: 760px;
}

.emptyAuthor {
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: -20px;
  margin-left: -20px;
}

.authorPageBody {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
  height: 860px;
  border: 2px solid #b8b8b8;
  border-radius: 15px;
  padding: 20px;
  margin-top: 34px;
}

.registerForm {
  margin: auto;
  padding: 50px;
}

.navbarAuthor .el-menu {
  height: 820px;
}

.navbarAuthor .el-menu .el-menu--inline {
  max-height: 455px;
  overflow: auto;
}

/* ↓↓↓↓↓Do not touch↓↓↓↓↓ */
.background span {
  width: 20vmin;
  height: 20vmin;
  border-radius: 20vmin;
  backface-visibility: hidden;
  position: absolute;
  animation-name: move;
  animation-duration: 6s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}

.background span:nth-child(1) {
  color: #FFACAC;
  top: 20%;
  left: 85%;
  animation-duration: 15.2s;
  animation-delay: -14.3s;
  transform-origin: -14vw -9vh;
  box-shadow: -40vmin 0 8.1590475535vmin currentColor;
}

.background span:nth-child(2) {
  color: #E45A84;
  top: 95%;
  left: 30%;
  animation-duration: 14.8s;
  animation-delay: -2.6s;
  transform-origin: -3vw 25vh;
  box-shadow: -40vmin 0 5.9669644743vmin currentColor;
}

.background span:nth-child(3) {
  color: #583C87;
  top: 86%;
  left: 35%;
  animation-duration: 15.1s;
  animation-delay: -13.8s;
  transform-origin: 1vw 6vh;
  box-shadow: -40vmin 0 10.117229939vmin currentColor;
}

.background span:nth-child(4) {
  color: #E45A84;
  top: 34%;
  left: 77%;
  animation-duration: 13.2s;
  animation-delay: -0.5s;
  transform-origin: -21vw 12vh;
  box-shadow: 40vmin 0 5.8295497142vmin currentColor;
}

.background span:nth-child(5) {
  color: #FFACAC;
  top: 6%;
  left: 39%;
  animation-duration: 15s;
  animation-delay: -0.5s;
  transform-origin: -14vw 5vh;
  box-shadow: -40vmin 0 10.4321028682vmin currentColor;
}

.background span:nth-child(6) {
  color: #FFACAC;
  top: 75%;
  left: 30%;
  animation-duration: 12.5s;
  animation-delay: -12s;
  transform-origin: 24vw 20vh;
  box-shadow: 40vmin 0 11.188375372vmin currentColor;
}

.background span:nth-child(7) {
  color: #FFACAC;
  top: 78%;
  left: 69%;
  animation-duration: 13.2s;
  animation-delay: -11.5s;
  transform-origin: -9vw -21vh;
  box-shadow: -40vmin 0 11.8140744482vmin currentColor;
}

.background span:nth-child(8) {
  color: #E45A84;
  top: 37%;
  left: 21%;
  animation-duration: 11.1s;
  animation-delay: -12.7s;
  transform-origin: -16vw -22vh;
  box-shadow: -40vmin 0 6.9343138779vmin currentColor;
}

.background span:nth-child(9) {
  color: #FFACAC;
  top: 19%;
  left: 90%;
  animation-duration: 13.7s;
  animation-delay: -0.4s;
  transform-origin: 18vw -11vh;
  box-shadow: -40vmin 0 11.3076308358vmin currentColor;
}

.background span:nth-child(10) {
  color: #583C87;
  top: 67%;
  left: 38%;
  animation-duration: 13.3s;
  animation-delay: -13.1s;
  transform-origin: -6vw 21vh;
  box-shadow: 40vmin 0 11.9622445387vmin currentColor;
}

.background span:nth-child(11) {
  color: #FFACAC;
  top: 14%;
  left: 63%;
  animation-duration: 11s;
  animation-delay: -12.2s;
  transform-origin: -21vw 15vh;
  box-shadow: -40vmin 0 13.2233894276vmin currentColor;
}

.background span:nth-child(12) {
  color: #583C87;
  top: 28%;
  left: 9%;
  animation-duration: 11.9s;
  animation-delay: -7.7s;
  transform-origin: 15vw 25vh;
  box-shadow: 40vmin 0 9.1113190293vmin currentColor;
}

.background span:nth-child(13) {
  color: #583C87;
  top: 51%;
  left: 62%;
  animation-duration: 12s;
  animation-delay: -1.8s;
  transform-origin: -5vw -17vh;
  box-shadow: -40vmin 0 10.9538350778vmin currentColor;
}

.background span:nth-child(14) {
  color: #FFACAC;
  top: 53%;
  left: 42%;
  animation-duration: 11.9s;
  animation-delay: -9.8s;
  transform-origin: 23vw -8vh;
  box-shadow: 40vmin 0 5.2951927654vmin currentColor;
}

.background span:nth-child(15) {
  color: #E45A84;
  top: 65%;
  left: 3%;
  animation-duration: 11.4s;
  animation-delay: -11.6s;
  transform-origin: 3vw 5vh;
  box-shadow: 40vmin 0 14.0154516358vmin currentColor;
}

.background span:nth-child(16) {
  color: #583C87;
  top: 48%;
  left: 80%;
  animation-duration: 10.9s;
  animation-delay: -14s;
  transform-origin: -23vw 0vh;
  box-shadow: -40vmin 0 8.4969629829vmin currentColor;
}

.background span:nth-child(17) {
  color: #FFACAC;
  top: 60%;
  left: 92%;
  animation-duration: 12.2s;
  animation-delay: -6.1s;
  transform-origin: 11vw 3vh;
  box-shadow: 40vmin 0 7.0554495411vmin currentColor;
}

.background span:nth-child(18) {
  color: #583C87;
  top: 28%;
  left: 33%;
  animation-duration: 10.8s;
  animation-delay: -11.2s;
  transform-origin: 4vw 18vh;
  box-shadow: -40vmin 0 7.3897533605vmin currentColor;
}

.background span:nth-child(19) {
  color: #E45A84;
  top: 56%;
  left: 12%;
  animation-duration: 15.6s;
  animation-delay: -14.9s;
  transform-origin: -5vw 9vh;
  box-shadow: 40vmin 0 11.4947634051vmin currentColor;
}

.background span:nth-child(20) {
  color: #583C87;
  top: 31%;
  left: 5%;
  animation-duration: 13.4s;
  animation-delay: -8.3s;
  transform-origin: -19vw 7vh;
  box-shadow: 40vmin 0 7.7825104892vmin currentColor;
}

@keyframes move {
  100% {
    transform: translate3d(0, 0, 1px) rotate(360deg);
  }
}

@-webkit-keyframes book-bounce {
  0% {
    transform: translateY(0);
  }

  40% {
    transform: translateY(-10px);
  }

  80% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(0);
  }
}

@keyframes book-bounce {
  0% {
    transform: translateY(0);
  }

  40% {
    transform: translateY(-10px);
  }

  80% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(0);
  }
}

@-webkit-keyframes shelf-lift {
  0% {
    transform: translateY(0) rotate(0);
  }

  20% {
    transform: translateY(-4px) rotate(10deg);
  }

  40% {
    transform: translateY(-4px) rotate(0);
  }

  40% {
    transform: translateY(-4px) rotate(-10deg);
  }

  80% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(0);
  }
}

@keyframes shelf-lift {
  0% {
    transform: translateY(0) rotate(0);
  }

  20% {
    transform: translateY(-4px) rotate(10deg);
  }

  40% {
    transform: translateY(-4px) rotate(0);
  }

  40% {
    transform: translateY(-4px) rotate(-10deg);
  }

  80% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(0);
  }
}

.book-shelf:hover {
  cursor: pointer;
}

.book-shelf:hover .book-shelf__book {
  -webkit-animation: book-bounce 0.4s ease;
  animation: book-bounce 0.4s ease;
  -webkit-animation-iteration-count: 1;
  animation-iteration-count: 1;
}

.book-shelf:hover .book-shelf__book--two {
  -webkit-animation-delay: 0.04s;
  animation-delay: 0.04s;
}

.book-shelf:hover .book-shelf__book--three {
  -webkit-animation-delay: 0.08s;
  animation-delay: 0.08s;
}

.book-shelf:hover .book-shelf__shelf {
  -webkit-animation: shelf-lift 0.4s ease;
  animation: shelf-lift 0.4s ease;
  -webkit-animation-iteration-count: 1;
  animation-iteration-count: 1;
  transform-origin: 50% 50%;
}

.content {
  position: relative;
}

.content .container {
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}

#bongo-cat {
  position: absolute;
  height: 180px;
  width: 180px;
  top: 817px;
  bottom: 0;
  left: 938px;
  right: 0;
  margin: auto;
}

.typing-animation {
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
  -webkit-animation-duration: 1200ms;
  animation-duration: 1200ms;
}

path#f1-l1 {
  -webkit-animation-name: typing-f1-l1;
  animation-name: typing-f1-l1;
}

path#f1-l2 {
  -webkit-animation-name: typing-f1-l2;
  animation-name: typing-f1-l2;
}

path#f1-l3 {
  -webkit-animation-name: typing-f1-l3;
  animation-name: typing-f1-l3;
}

path#f2-l4 {
  -webkit-animation-name: typing-f2-l4;
  animation-name: typing-f2-l4;
}

path#f2-l5 {
  -webkit-animation-name: typing-f2-l5;
  animation-name: typing-f2-l5;
}

path#f2-l6 {
  -webkit-animation-name: typing-f2-l6;
  animation-name: typing-f2-l6;
}

path#f3-l7 {
  -webkit-animation-name: typing-f3-l7;
  animation-name: typing-f3-l7;
}

path#f3-l8 {
  -webkit-animation-name: typing-f3-l8;
  animation-name: typing-f3-l8;
}

path#f3-l9 {
  -webkit-animation-name: typing-f3-l9;
  animation-name: typing-f3-l9;
}

@-webkit-keyframes typing-f3-l9 {
  0% {
    d: path("M8,25L8,25");
  }

  82% {
    d: path("M8,25L8,25");
  }

  92% {
    d: path("M8,25L96,25");
  }

  100% {
    d: path("M8,25L96,25");
  }
}

@keyframes typing-f3-l9 {
  0% {
    d: path("M8,25L8,25");
  }

  82% {
    d: path("M8,25L8,25");
  }

  92% {
    d: path("M8,25L96,25");
  }

  100% {
    d: path("M8,25L96,25");
  }
}

@-webkit-keyframes typing-f3-l8 {
  0% {
    d: path("M8,13L8,13");
  }

  68% {
    d: path("M8,13L8,13");
  }

  82% {
    d: path("M8,13L146,13");
  }

  100% {
    d: path("M8,13L146,13");
  }
}

@keyframes typing-f3-l8 {
  0% {
    d: path("M8,13L8,13");
  }

  68% {
    d: path("M8,13L8,13");
  }

  82% {
    d: path("M8,13L146,13");
  }

  100% {
    d: path("M8,13L146,13");
  }
}

@-webkit-keyframes typing-f3-l7 {
  0% {
    d: path("M0,1L0,1");
  }

  60% {
    d: path("M0,1L0,1");
  }

  68% {
    d: path("M0,1L96,1");
  }

  100% {
    d: path("M0,1L96,1");
  }
}

@keyframes typing-f3-l7 {
  0% {
    d: path("M0,1L0,1");
  }

  60% {
    d: path("M0,1L0,1");
  }

  68% {
    d: path("M0,1L96,1");
  }

  100% {
    d: path("M0,1L96,1");
  }
}

@-webkit-keyframes typing-f2-l6 {
  0% {
    d: path("M8,25L8,25");
  }

  54% {
    d: path("M8,25L8,25");
  }

  60% {
    d: path("M8,25L69,25");
  }

  100% {
    d: path("M8,25L69,25");
  }
}

@keyframes typing-f2-l6 {
  0% {
    d: path("M8,25L8,25");
  }

  54% {
    d: path("M8,25L8,25");
  }

  60% {
    d: path("M8,25L69,25");
  }

  100% {
    d: path("M8,25L69,25");
  }
}

@-webkit-keyframes typing-f2-l5 {
  0% {
    d: path("M8,13L8,13");
  }

  44% {
    d: path("M8,13L8,13");
  }

  54% {
    d: path("M8,13L114,13");
  }

  100% {
    d: path("M8,13L114,13");
  }
}

@keyframes typing-f2-l5 {
  0% {
    d: path("M8,13L8,13");
  }

  44% {
    d: path("M8,13L8,13");
  }

  54% {
    d: path("M8,13L114,13");
  }

  100% {
    d: path("M8,13L114,13");
  }
}

@-webkit-keyframes typing-f2-l4 {
  0% {
    d: path("M0,1L0,1");
  }

  30% {
    d: path("M0,1L0,1");
  }

  44% {
    d: path("M0,1L136,1");
  }

  100% {
    d: path("M0,1L136,1");
  }
}

@keyframes typing-f2-l4 {
  0% {
    d: path("M0,1L0,1");
  }

  30% {
    d: path("M0,1L0,1");
  }

  44% {
    d: path("M0,1L136,1");
  }

  100% {
    d: path("M0,1L136,1");
  }
}

@-webkit-keyframes typing-f1-l3 {
  0% {
    d: path("M8,25L8,25");
  }

  24% {
    d: path("M8,25L8,25");
  }

  30% {
    d: path("M8,25L61,25");
  }

  100% {
    d: path("M8,25L61,25");
  }
}

@keyframes typing-f1-l3 {
  0% {
    d: path("M8,25L8,25");
  }

  24% {
    d: path("M8,25L8,25");
  }

  30% {
    d: path("M8,25L61,25");
  }

  100% {
    d: path("M8,25L61,25");
  }
}

@-webkit-keyframes typing-f1-l2 {
  0% {
    d: path("M8,13L8,13");
  }

  14% {
    d: path("M8,13L8,13");
  }

  24% {
    d: path("M8,13L124,13");
  }

  100% {
    d: path("M8,13L124,13");
  }
}

@keyframes typing-f1-l2 {
  0% {
    d: path("M8,13L8,13");
  }

  14% {
    d: path("M8,13L8,13");
  }

  24% {
    d: path("M8,13L124,13");
  }

  100% {
    d: path("M8,13L124,13");
  }
}

@-webkit-keyframes typing-f1-l1 {
  0% {
    d: path("M0,1L0,1");
  }

  14% {
    d: path("M0,1L160,1");
  }

  100% {
    d: path("M0,1L160,1");
  }
}

@keyframes typing-f1-l1 {
  0% {
    d: path("M0,1L0,1");
  }

  14% {
    d: path("M0,1L160,1");
  }

  100% {
    d: path("M0,1L160,1");
  }
}

#paw-right--up,
#paw-right--down,
#paw-left--up,
#paw-left--down {
  -webkit-animation: blink 300ms infinite;
  animation: blink 300ms infinite;
}

#paw-right--up,
#paw-left--down {
  -webkit-animation-delay: 150ms;
  animation-delay: 150ms;
}

@-webkit-keyframes blink {
  0% {
    opacity: 0;
  }

  49% {
    opacity: 0;
  }

  50% {
    opacity: 1;
  }
}

@keyframes blink {
  0% {
    opacity: 0;
  }

  49% {
    opacity: 0;
  }

  50% {
    opacity: 1;
  }
}

#laptop__code {
  transform: rotateX(-37deg) rotateY(-46deg) rotateZ(-23deg) translateX(8px) translateY(20px) translateZ(-50px);
}

@import url("https://fonts.googleapis.com/css?family=Sanchez");

:root {
  --dark-brown: #2d2623;
  --dark-blue: #22366c;
  --light-blue: #439abf;
  --med-blue: #294679;
  --stars: #e9ae4b;
}

* {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

*:before,
*:after {
  content: "";
  position: absolute;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

.window {
  width: 275px;
  height: 440px;
  background-color: var(--med-blue);
  border: 18px solid var(--dark-brown);
  position: relative;
  border-bottom: none;
}

.window:before {
  background-color: var(--dark-brown);
  width: calc(100% + 68px);
  height: 18px;
  left: -34px;
  bottom: 0;
  border-top: 6px solid var(--dark-blue);
}

.window:after {
  width: calc(100% + 18px);
  height: 6px;
  background-color: var(--light-blue);
  bottom: 12px;
  left: -9px;
}

.window-details {
  position: relative;
  height: 12px;
  width: 100%;
  top: 190px;
  background-color: var(--dark-brown);
}

.window-details:before,
.window-details:after {
  border: 6px solid var(--light-blue);
  width: 100%;
  border-top: none;
}

.window-details:before {
  height: 190px;
  top: -190px;
}

.window-details:after {
  height: 208px;
  bottom: -208px;
  border-bottom: none;
}

.mountains-left,
.mountains-right {
  position: absolute;
  bottom: 18px;
  width: 0;
  height: 0;
  border-bottom: 60px solid var(--dark-blue);
}

.mountains-left {
  left: 6px;
  border-right: 22px solid transparent;
}

.mountains-right {
  right: 6px;
  border-left: 22px solid transparent;
}

.mountains-left:before,
.mountains-left:after,
.mountains-right:before {
  width: 0;
  height: 0;
  border-left: 22px solid transparent;
  border-right: 22px solid transparent;
  border-bottom: 60px solid var(--dark-blue);
}

.mountains-left:before {
  left: 22px;
}

.mountains-right:before {
  right: 0px;
}

.moon {
  background-color: var(--stars);
  width: 50px;
  height: 50px;
  border-radius: 50%;
  position: absolute;
  left: 35px;
  top: 35px;
}

.moon:before {
  background-color: var(--med-blue);
  width: 42px;
  height: 42px;
  border-radius: 50%;
  position: absolute;
  left: 12px;
  top: 4px;
}

.star,
.star:before,
.star:after {
  background-color: var(--stars);
  position: absolute;
  border-radius: 50%;
}

.star-glow {
  animation: glow ease-in-out infinite alternate;
}

.star-1 {
  width: 8px;
  height: 8px;
  top: 230px;
  left: 50%;
  animation-duration: 5s;
}

.star-1:before {
  width: 4px;
  height: 4px;
  left: -90px;
  top: 60px;
  animation: glow ease-in-out infinite alternate;
  animation-duration: 6s;
}

.star-1:after {
  width: 3px;
  height: 3px;
  left: 80px;
  top: 10px;
  animation: glow ease-in-out infinite alternate;
  animation-duration: 2s;
}

.star-2 {
  width: 4px;
  height: 4px;
  top: 30px;
  left: 50%;
  animation-duration: 8s;
}

.star-2:before {
  width: 4px;
  height: 4px;
  left: 70px;
  top: 80px;
  animation: glow ease-in-out infinite alternate;
  animation-duration: 4s;
}

.star-2:after {
  width: 4px;
  height: 4px;
  left: -80px;
  top: 100px;
  animation: glow ease-in-out infinite alternate;
  animation-duration: 3s;
}

.star-3 {
  width: 6px;
  height: 6px;
  top: 215px;
  left: 45px;
}

.star-3:before {
  width: 3px;
  height: 3px;
  top: 54px;
  left: 75px;
}

.star-3:after {
  width: 3px;
  height: 3px;
  top: 10px;
  left: 140px;
}

.star-4 {
  width: 3px;
  height: 3px;
  top: 20px;
  left: 100px;
}

.star-4:before {
  width: 6px;
  height: 6px;
  top: 32px;
  left: -18px;
}

.star-4:after {
  width: 3px;
  height: 3px;
  top: 135px;
  left: -35px;
}

.star-5 {
  width: 3px;
  height: 3px;
  top: 40px;
  left: 210px;
}

.star-5:before {
  width: 3px;
  height: 3px;
  top: 110px;
  left: 10px;
}

@keyframes glow {

  0%,
  50% {
    box-shadow: 0px 0px 0px 0px var(--stars);
  }

  80%,
  100% {
    box-shadow: 0px 0px 8px 2px var(--stars);
  }
}

.clouds {
  background-color: var(--dark-blue);
  width: 180px;
  height: 12px;
  position: absolute;
  left: 25px;
  top: 74px;
  border-radius: 14px;
}

.clouds:before,
.clouds:after,
.clouds span {
  height: inherit;
  border-radius: inherit;
  background-color: inherit;
  position: absolute;
}

.clouds:before {
  width: 95px;
  top: 24px;
  left: 35px;
}

.clouds:after {
  width: 55px;
  top: -24px;
  left: 100px;
}

.clouds span:first-child {
  width: 55px;
  top: -12px;
  left: 100px;
}

.clouds span:nth-child(2) {
  width: 70px;
  top: 12px;
  left: 35px;
}

.clouds span:before,
.clouds span:after {
  height: inherit;
  border-radius: 14px;
  background-color: var(--med-blue);
  width: 40px;
}

.clouds span:before {
  left: -20px;
}

.clouds span:after {
  right: -25px;
}

.cat {
  background-color: var(--dark-brown);
  z-index: 100;
  position: absolute;
  bottom: 0;
  left: 90px;
  animation: breathe-belly 5s ease infinite;
}

.cat-body {
  position: absolute;
  width: 45px;
  height: 125px;
  border-top-left-radius: 45px;
  border-top-right-radius: 45px;
  background-color: inherit;
  bottom: 0;
  transform-origin: left bottom;
  animation: breathe-body 5s ease infinite;
}

@keyframes breathe-body {

  0%,
  5%,
  100% {
    height: 125px;
  }

  20%,
  55% {
    height: 125px;
  }

  60%,
  90% {
    height: 88px;
  }
}

.cat-belly {
  background-color: inherit;
  position: absolute;
  width: 85px;
  height: 85px;
  bottom: 0;
  border-radius: 50%;
  transform-origin: left bottom;
}

.cat-belly:before {
  background-color: inherit;
  width: 12px;
  height: 100px;
  top: 73px;
  right: 14px;
  border-radius: 12px;
}

@keyframes breathe-belly {

  0%,
  20%,
  50%,
  100% {
    transform: scale(1);
  }

  40% {
    transform: scale(1.02);
  }
}

.cat-ears:before {
  width: 0;
  height: 0;
  border-bottom: 30px solid var(--dark-brown);
  border-right: 35px solid transparent;
  top: -10px;
}

.cat-ears:after {
  width: 0;
  height: 0;
  border-bottom: 30px solid var(--dark-brown);
  border-left: 35px solid transparent;
  top: -10px;
  right: 0;
}
</style>
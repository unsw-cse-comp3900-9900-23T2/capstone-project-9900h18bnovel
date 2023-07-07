<script setup>
import Global_Header from './Global_Header.vue';
import Global_Footer from './Global_Footer.vue';
import Global_Nav from './Global_Nav.vue';
import Login from './Auth_Page.vue';
import { ElMessage } from 'element-plus';
import {
  StarFilled
} from '@element-plus/icons-vue';
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
      genreChecked: [false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      filterChecked: [false, false, false, false],
      sortChecked: [true, false, false, false],
      isLoginVisible: false,
      verImage: '',
      sessionId: '',
      novels: null,
      loading: true,
      showAllNovelPage: false,
    }
  },
  components: {
    Global_Footer,
    Global_Header,
    Global_Nav
  },
  mounted() {
    this.getNewestUpdateBooks();
    setTimeout(() => {
      this.loading = false;
      this.showAllNovelPage = true;
    }, 500);
  },
  methods: {
    genreonChange(index) {
      if (this.genreChecked[index] === true) {
        this.genreChecked = this.genreChecked.map(() => false);
      } else {
        this.genreChecked = this.genreChecked.map(() => false);
        this.genreChecked[index] = true;
      }
    },
    filteronChange(index) {
      if (this.filterChecked[index] === true) {
        this.filterChecked = this.filterChecked.map(() => false);
      } else {
        this.filterChecked = this.filterChecked.map(() => false);
        this.filterChecked[index] = true;
      }
    },
    sortonChange(index) {
      this.sortChecked = this.sortChecked.map(() => false);
      this.sortChecked[index] = true;
    },
    async getNewestUpdateBooks() {
      try {
        const response = await fetch("http://localhost:8888/api/front/book/update_rank ", {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.novels = data.data;
          console.log(this.novels);
        } else {
          console.log("Test");
        }
      } catch (error) {
        console.error(error);
      }
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
  }
}
</script>

<template>
  <div :class="{ 'blur': isLoginVisible }">
    <Global_Header @logout="logout" @showLogin="showLogin" @closeLoginBox="closeLoginBox" />
    <Global_Nav />
    <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
      element-loading-background="rgba(255, 255, 255, 255)"
      style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
    <div v-if="showAllNovelPage">
      <div class="allnovel_body">
        <div class="picker_content">
          <div class="filter_container">
            <div class="picker_left">
              <h2 style="color: rgb(73, 73, 73);">Genres of Novels</h2>
              <el-divider />
              <div style="width: 100%;">
                <el-check-tag class="tags" :checked="genreChecked[0]" @click="genreonChange(0)">Sci-Fi</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[1]" @click="genreonChange(1)">Fantasy</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[2]" @click="genreonChange(2)">Adventure</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[3]" @click="genreonChange(3)">Mystery</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[4]" @click="genreonChange(4)">Action</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[5]" @click="genreonChange(5)">Horror</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[6]" @click="genreonChange(6)">Humor</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[7]" @click="genreonChange(7)">Erotica</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[8]" @click="genreonChange(8)">Poetry</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[9]" @click="genreonChange(9)">Thriller</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[10]" @click="genreonChange(10)">Romance</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[11]" @click="genreonChange(11)">Children</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[12]" @click="genreonChange(12)">Drama</el-check-tag>
                <el-check-tag class="tags" :checked="genreChecked[13]" @click="genreonChange(13)">Other</el-check-tag>
              </div>
            </div>
            <div class="picker_right">
              <div class="filterby">
                <h2 style="color: rgb(73, 73, 73);">Filter By</h2>
                <el-divider />
                <div style="width: 100%;">
                  <el-check-tag class="tags" :checked="filterChecked[0]" @click="filteronChange(0)">Filter
                    1</el-check-tag>
                  <el-check-tag class="tags" :checked="filterChecked[1]" @click="filteronChange(1)">Filter
                    2</el-check-tag>
                  <el-check-tag class="tags" :checked="filterChecked[2]" @click="filteronChange(2)">Filter
                    3</el-check-tag>
                  <el-check-tag class="tags" :checked="filterChecked[3]" @click="filteronChange(3)">Filter
                    4</el-check-tag>
                </div>
              </div>
              <div class="sortby">
                <h2 style="color: rgb(73, 73, 73);">Sort By</h2>
                <el-divider />
                <div style="width: 100%;">
                  <el-check-tag class="tags" :checked="sortChecked[0]" @click="sortonChange(0)">Popular</el-check-tag>
                  <el-check-tag class="tags" :checked="sortChecked[1]" @click="sortonChange(1)">Recommended</el-check-tag>
                  <el-check-tag class="tags" :checked="sortChecked[2]" @click="sortonChange(2)">Most
                    Collections</el-check-tag>
                  <el-check-tag class="tags" :checked="sortChecked[3]" @click="sortonChange(3)">Score</el-check-tag>
                  <el-check-tag class="tags" :checked="sortChecked[4]" @click="sortonChange(4)">Time
                    updated</el-check-tag>
                </div>
              </div>
            </div>
          </div>
        </div>
        <el-divider />
        <div class="novels_container">
          <div v-for="(item) in novels" :key="item.bookName" class="each_novel_container">
            <el-card shadow="hover" :body-style="{ padding: '10px' }">
              <div class="each_novel_card">
                <div>
                  <img :src="item.picUrl" style="height: 175px; border-radius: 5px;" />
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
                  <el-tag style="margin-top: 15px;">{{ item.categoryName }} Tag</el-tag>
                  <div style="bottom: 0; position: absolute;">
                    <el-icon>
                      <StarFilled />
                    </el-icon>
                    {{ item.score }}
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    {{ item.wordCount }} Words
                  </div>
                </div>
              </div>
            </el-card>
          </div>

        </div>
      </div>
      <Global_Footer />
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
  width: 60%;
  margin: auto;
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
  width: 59%;
}

.picker_right {
  width: 40%;
}

.filterby {
  width: 100%;
}

.sortby {
  width: 100%;
}

.tags {
  margin-right: 8px;
  margin-bottom: 6px;
}

.novels_container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.each_novel_container {
  width: 33%;
  height: 200px;
}

.each_novel_card {
  width: 100%;
  height: 96%;
  object-fit: contain;
  display: flex;
  justify-content: space-between;
  position: relative;
}
</style>

<script setup>
import axios from 'axios';
import {
  Male,
  Female,
  Upload,
  Calendar,
} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';

</script>
<script >
export default {
  data() {
    return {
      load: true,
      userImg: null,
      userGender: null,
      userName: null,
      showEditProfile: false,
      showProfilePage: false,

      authorId: null,
      authorPenName: null,
      authorsignature: null,
      authorCreateTime: null,
      authorBooks: [],

      userCollectedBooks: [],

    }
  },

  mounted() {
    if (this.$store.getters.isAuthenticated) {
      setTimeout(() => {
        this.getUserInfo();
        this.getAuthorInfo();
        this.getUserCollected();
        this.load = false;
        this.showProfilePage = true;
      }, 500);
    } else {
      ElMessage.error("Without sign in cannot go to user profile");
      this.$router.push('/home');
    }


  },
  methods: {
    async getUserCollected() {
      await axios.get('api/front/user/user_collect?userId=' + this.$store.getters.GetUID)
        .then(response => {
          this.userCollectedBooks = response.data.data;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getAuthorBooks() {
      await axios.get('api/author/get_books?authorId=' + this.authorId + '&pageSize=6')
        .then(response => {
          const data = response.data.data;
          this.authorBooks = data.list;
          this.authorBooksTotal = data.total;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getAuthorInfo() {
      await axios.get('/api/author/get_author_info?userId=' + this.$store.getters.GetUID)
        .then(response => {
          const data = response.data.data;
          this.authorId = data.id;
          this.authorPenName = data.penName;
          this.authorsignature = data.signature;
          this.authorCreateTime = data.createTime;
          this.getAuthorBooks();
        })
        .catch(error => {
          console.error(error);
        });
    },

    async getUserInfo() {
      const reqbody = {
        "userId": this.$store.getters.GetUID
      }
      await axios.post('/api/front/user/get_userInfo', reqbody)
        .then(response => {
          const data = response.data.data;
          this.userImg = data.userPhoto;
          this.userGender = data.userSex;
          this.userName = data.username;
          localStorage.setItem('userPhoto', data.userPhoto);
          this.$store.dispatch('photo', data.userPhoto);
        })
        .catch(error => {
          console.error(error);
        });
    },

    async updateUserInfo() {
      this.load = true;
      setTimeout(async () => {
        this.load = false;
        const reqbody = {
          "userId": this.$store.getters.GetUID,
          "username": this.userName,
          "userPhoto": this.userImg,
          "userSex": this.userGender
        }
        await axios.put('/api/front/user/modify_userInfo', reqbody)
          .then(response => {
            if (response.data.code === "00000") {
              this.showEditProfile = false;
              ElMessage.success("Update user profile success");
              this.getUserInfo();
            }
            else
              ElMessage.error("Oops, something wrong");
          })
          .catch(error => {
            console.error(error);
          });
      }, 500);
    },

    returnEditProfile() {
      this.load = true;
      setTimeout(() => {
        this.load = false;
        this.showEditProfile = false;
        this.getUserInfo();
      }, 500);

    },

    handleUploadClick() {
      this.$nextTick(() => {
        this.$refs.avatarInput.click();
      });
    },

    goBook(bookId) {
      this.$router.push(`/bookInfo/${bookId}`);
    },

    goHome() {
      this.$router.push('/home');
    },

    goBrowse() {
      this.$router.push('/browse');
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
        this.userImg = event.target.result;
      };
      reader.readAsDataURL(file);
    },

    goEditProfile() {
      this.load = true;
      setTimeout(() => {
        this.load = false;
        this.showEditProfile = true;
      }, 500);
    },

    goAuthorPage() {
      window.open('/author');
    },
  }
}
</script>
<template>
  <div v-loading.fullscreen="load" element-loading-spinner=" ">
  </div>
  <div v-show="showProfilePage" class="profile-body">
    <div class="profile-background"></div>
    <div class="profile-basicinfo">
      <div class="profile-basicinfo">
        <el-avatar v-if="userImg !== null" class="profile-avatar" :size="200" :src="userImg" />
        <el-avatar v-else class="profile-avatar" :size="200"> {{ $store.getters.GetUsername }} </el-avatar>
        <el-button @click="goEditProfile" class="profile-button-edit" round color="#e5e7f5">EDIT PROFILE</el-button>
      </div>

      <div v-if="!showEditProfile">
        <div class="profile-username">
          {{ userName }}
          <el-icon v-if="userGender === null" style="margin-left: 30px;">
            <Male color="#3b66f5" size="60" style="margin-left: -21px; margin-top: -16px" />
            <Female color="#eb1551" size="60" style="margin-left: -36.8px" />
          </el-icon>
          <el-icon v-else style="margin-left: 20px;">
            <Male color="#3b66f5" v-if="userGender === '0'" />
            <Female color="#eb1551" v-else />
          </el-icon>
        </div>
        <div class="profile-userid">
          ID: {{ $store.getters.GetUID }}
        </div>
      </div>

      <el-form v-else label-width="120px" label-position="left" class="profile-edit">
        <el-form-item label="Avatar">
          <input type="file" ref="avatarInput" @change="handleFileChange" accept="image/*" style="display: none" />
          <el-button type="primary" :icon="Upload" @click="handleUploadClick">
            UPLOAD
          </el-button>
        </el-form-item>
        <el-form-item label="Username">
          <el-input v-model="userName" />
        </el-form-item>
        <el-form-item label="Gender">
          <el-radio-group v-model="userGender">
            <el-radio label="0">Male</el-radio>
            <el-radio label="1">Female</el-radio>
            <el-radio :label="null">Secrecy</el-radio>
          </el-radio-group>
        </el-form-item>
        <div style="display: flex; justify-content: right;">
          <el-button @click="returnEditProfile">CANCEL</el-button>
          <el-button type="success" @click="updateUserInfo">UPDATE</el-button>
        </div>
      </el-form>
    </div>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span style="font-size: 18pt;">Author</span>
          <el-link type="primary" @click="goAuthorPage">MORE</el-link>
        </div>
      </template>
      <div v-if="authorId" class="author-info-books">
        <div class="author-info">
          <div class="author-penname">
            {{ authorPenName }}
          </div>
          <div class="author-id">
            ID: {{ authorId }}
          </div>
          <div class="author-signature">
            {{ authorsignature }}
          </div>
          <div class="authorCreateTime">
            <el-icon>
              <Calendar />
            </el-icon>&nbsp;
            {{ authorCreateTime ? authorCreateTime.split('T')[0] : null }} Joined
          </div>
        </div>
        <div class="author-books">
          <h2>
            My Books <br><br><br>
            {{ authorBooksTotal }} Total
          </h2>
          <div v-for="(item, index) in authorBooks.slice(0, 6)" :key="index" class="author-book">
            <img :src="item.picUrl" class="author-books-img" @click="goBook(item.id)" />
          </div>
        </div>
      </div>
      <div v-else style="display: flex; align-items: center;">
        You are not an author yet, wanna become an author?&nbsp;<el-link type="primary" @click="goAuthorPage">Register
          now!</el-link>
      </div>
    </el-card>

    <div class="user-collection">
      <div style="font-size: 18pt; font-weight: bold;">
        My Collections
      </div>
      <el-divider />
      <div v-if="userCollectedBooks.length > 0" class="collected_novel_user_ya">
        <div v-for="item in userCollectedBooks" :key="item.bookId"
          style="display: flex; flex-direction:column; margin-bottom: 10px;">
          <img :src="item.picUrl" class="collected_img" @click="goBook(item.bookId)" />
          <div class="collected_word" style="margin-top: 10px;" @click="goBook(item.bookId)">
            <b>{{ item.bookName }}</b>
          </div>
          <div class="collected_word" style="text-decoration: none; cursor: default;">
            {{ item.preChapterName }}
          </div>
        </div>
      </div>
      <div v-else style="display: flex; align-items: center;">
        You have no collected book. Maybe try use&nbsp;<el-link type="primary"
          @click="goBrowse">Browse</el-link>&nbsp;or&nbsp;<el-link type="primary" @click="goHome">Homepage</el-link>
        &nbsp;find out what books you like
      </div>
    </div>
  </div>
</template>

<style>
.user-collection {
  background-color: #f6f7fc;
  padding: 20px;
}

.user-collection .collected_novel_user_ya {
  flex-wrap: wrap;
}

.user-collection .collected_novel_user_ya .collected_img {
  margin-right: 30px;
}

.author-books-img {
  height: 150px;
  width: 93.75px;
  box-shadow: 6px 4px 6px rgb(92, 92, 92);
  border-radius: 5px;
  transform: scale(1);
  transition: transform 0.3s ease;
}

.authorCreateTime {
  display: flex;
  align-items: center;
  bottom: 0;
  position: absolute;
}

.author-books-img:hover {
  cursor: pointer;
  transform: scale(1.04);
}

.author-books {
  width: 73%;
  display: flex;
  border: 1px solid #dbdbdb;
  border-radius: 15px;
  padding: 10px;
  margin-top: -10px;
}

.author-book {
  margin-left: 18px;
}

.author-penname {
  font-size: 22pt;
}

.author-info {
  width: 25%;
  text-overflow: ellipsis;
  overflow: hidden;
  position: relative;
}

.author-id {
  color: #8a8989;
  font-size: 14pt;
}

.author-signature {
  font-size: 12pt;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}

.author-info-books {
  display: flex;
  justify-content: space-between;
}

.card-header {
  font-size: 15pt;
  font-weight: bold;
  display: flex;
  justify-content: space-between;
}

.box-card {
  margin: 20px 0 20px 0;
}

.profile-edit {
  width: 500px;
  margin: auto;
}

.profile-background {
  background-image: url(@/assets/AuthBG.jpg);
  height: 400px;
  background-size: contain;
}

.profile-basicinfo {
  position: relative;
}

.profile-button-edit {
  right: 50px;
  position: absolute;
}

.profile-userid {
  color: #8a8989;
  font-size: 14pt;
  margin-left: 50px;
}

.profile-username {
  display: flex;
  align-items: center;
  font-size: 25pt;
  margin-left: 50px;
  margin-top: 20px;
}

.profile-basicinfo {
  background-color: #f6f7fc;
  padding-bottom: 20px;
}

.profile-body {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
}

.profile-avatar {
  margin-top: -100px;
  margin-left: 20px;
  border: 5px solid white;
}
</style>
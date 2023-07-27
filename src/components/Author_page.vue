<script setup>
import axios from 'axios';
import {
  Plus,

} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `
</script>
<script>
export default {
  data() {
    return {
      penName: null,
      signature: null,
      isAuthor: false,
      loading: true,
      showAuthorPage: false,
      avatar: null,
      isEmpty: true,
      isAuthorUpdate: false,
      authorId: null,
    }
  },

  mounted() {
    this.checkisAuthor();
    setTimeout(() => {
      this.loading = false;
      this.showAuthorPage = true;
    }, 1000);
  },

  methods: {
    async update_author() {
      const reqbody = {
        "authorId": this.authorId,
        "userId": this.$store.getters.GetUID,
        "penName": this.penName,
        "signature": this.signature,
      }
      await axios.post("http://localhost:8888/api/author/update", reqbody)
        .catch(error => {
          console.error(error);
        });
      ElMessage.success("Update Successful")
    },

    async getUserInfo() {
      const reqbody = {
        "userId": this.$store.getters.GetUID
      }
      await axios.post("http://localhost:8888/api/front/user/get_userInfo", reqbody)
        .then(response => {
          const data = response.data;
          this.avatar = data.data.userPhoto;
        })
        .catch(error => {
          console.error(error);
        });
    },

    async checkisAuthor() {
      await axios.get("http://localhost:8888/api/author/get_author_info?userId=" + this.$store.getters.GetUID)
        .then(response => {
          const data = response.data;
          if (data === null) {
            this.isAuthor = false;
          } else {
            this.penName = data.data.penName;
            this.signature = data.data.signature;
            this.authorId = data.data.id;
            this.isAuthor = true;
            this.getUserInfo();
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    async register_author() {
      const reqbody = {
        "authorId": 0,
        "userId": this.$store.getters.GetUID,
        "penName": this.penName,
        "signature": this.signature,
      }
      await axios.post("http://localhost:8888/api/author/register", reqbody)
        .catch(error => {
          console.error(error);
        });
      this.checkisAuthor();
    },

    goUpdateAuthorInfo() {
      this.isEmpty = false;
      this.isAuthorUpdate = true;
    },
    goEmpty() {
      this.isEmpty = true;
      this.isAuthorUpdate = false;
    }
  }
}
</script>
<template>
  <div v-loading.fullscreen.lock="loading" element-loading-text="Is great that we have you" :element-loading-spinner="svg"
    element-loading-svg-view-box="0, 5, 30, 40" element-loading-background="rgba(255, 255, 255, 255)"></div>
  <div v-if="showAuthorPage" class="authorPageBody">
    <div v-if="!isAuthor" class="registerForm">
      <div class="logo_container">
        <img src="..\logo1.png" class="logo">
        <h1
          style="font-family:'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif; color: rgb(68, 68, 68); text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.4); margin-right: 50px;">
          NovelHub</h1>
      </div>
      <h1>Author Register</h1>
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
    <div v-else>
      <el-row>
        <el-col :span="6" class="navbarAuthor">
          <el-menu>
            <h1 class="dashboard" @click="goEmpty">Dashboard</h1>
            <div class="authorBox" @click="goUpdateAuthorInfo">
              <img :src="avatar"
                style="border: 1px solid; border-radius: 50px; height: 80px; width: 80px; object-fit: cover;" />
              <div style="margin-left: 20px;">
                <h3>{{ penName }}</h3>
                <!-- <div>{{ signature }}</div> -->
              </div>
            </div>
            <div style="padding: 20px;">
              <el-button type="primary" :icon="Plus" style="width: 100%; font-size: large;">Create Book</el-button>
            </div>
            <el-sub-menu>
              <template #title>
                <span>My Books</span>
              </template>
            </el-sub-menu>
          </el-menu>
        </el-col>
        <el-col v-if="isEmpty" :span="18" class="emptyAuthor">
          <h1 style="margin-right: 20px; text-align: center;">
            Choose<br>A<br>Book<br>Maybe?
            <br><br>OR<br><br>
            Create<br>One?
          </h1>
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
        </el-col>
        <el-col v-if="isAuthorUpdate" :span="18" class="authorUpdateSection">
          <h1>Author Information Edit</h1>
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
              <el-button type="primary" @click='update_author' style="width: 100px;">
                Update
              </el-button>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<style>
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

.authorUpdateSection {
  padding: 20px;
}

.emptyAuthor {
  margin: auto;
  display: flex;
  justify-content: center;
  margin-left: -20px;
}

.authorPageBody {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
  height: 800px;
  border: 2px solid #b8b8b8;
  border-radius: 15px;
  padding: 20px;
  margin-top: 50px;
}

.registerForm {
  margin: auto;
  padding: 50px;
}

.navbarAuthor .el-menu {
  height: 720px;
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
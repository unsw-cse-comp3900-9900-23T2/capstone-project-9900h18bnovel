<script setup>
import {
  Male,
  Female,
} from '@element-plus/icons-vue';
import axios from 'axios';
</script >
<script>
export default {
  data() {
    return {
      adminEmail: null,
      adminName: null,
      adminAva: null,
      adminGender: null,
    }
  },
  methods: {
    async getAdminUser(userId) {
      await axios.get("/api/front/user/get_other_userInfo?userId=" + userId)
        .then(response => {
          const data = response.data;
          this.adminEmail = data.data.email;
          this.adminAva = data.data.userPhoto;
          this.adminGender = data.data.userSex;
          this.adminName = data.data.username;
        })
        .catch(error => {
          console.error(error);
        });
    },

    cleanOtherUserInfo() {
      this.adminEmail = null;
      this.adminGender = null;
      this.adminAva = null;
      this.adminName = null;
    },
  },
}
</script>
<template>
  <footer class="footer">
    <div class="footer-top">
      <div class="logo_container">
        <img src="..\assets\logo1.png" class="logo">
        <h1 class="novelhub_name">
          NovelHub</h1>
      </div>
      <div class="footer-right">
        <div class="footer-top-blocks">
          <h2>TEAMS</h2>
          <el-popover placement="top-start" :width="350" trigger="click" @show="getAdminUser(1)"
            @before-leave="cleanOtherUserInfo()">
            <template #reference>
              <div class="footer_Admin">Mingjie Huang</div>
            </template>
            <div style="display: flex; align-items: center;">
              <img :src="adminAva" class="otherUserAvatar" />
              <div>
                <div>{{ adminEmail }}</div>
                <div>{{ adminName }}</div>
                <div>
                  <el-icon v-if="adminGender === '0'">
                    <Male />
                  </el-icon>
                  <el-icon v-else>
                    <Female />
                  </el-icon>
                </div>
              </div>
            </div>
          </el-popover>

          <el-popover placement="top-start" :width="350" trigger="click" @show="getAdminUser(2)"
            @before-leave="cleanOtherUserInfo()">
            <template #reference>
              <div class="footer_Admin">Yuyan Xiong</div>
            </template>
            <div style="display: flex; align-items: center;">
              <img :src="adminAva" class="otherUserAvatar" />
              <div>
                <div>{{ adminEmail }}</div>
                <div>{{ adminName }}</div>
                <div>
                  <el-icon v-if="adminGender === '0'">
                    <Male />
                  </el-icon>
                  <el-icon v-else>
                    <Female />
                  </el-icon>
                </div>
              </div>
            </div>
          </el-popover>

          <el-popover placement="top-start" :width="350" trigger="click" @show="getAdminUser(3)"
            @before-leave="cleanOtherUserInfo()">
            <template #reference>
              <div class="footer_Admin">Dazhi Gao</div>
            </template>
            <div style="display: flex; align-items: center;">
              <img :src="adminAva" class="otherUserAvatar" />
              <div>
                <div>{{ adminEmail }}</div>
                <div>{{ adminName }}</div>
                <div>
                  <el-icon v-if="adminGender === '0'">
                    <Male />
                  </el-icon>
                  <el-icon v-else>
                    <Female />
                  </el-icon>
                </div>
              </div>
            </div>
          </el-popover>

          <el-popover placement="top-start" :width="350" trigger="click" @show="getAdminUser(4)"
            @before-leave="cleanOtherUserInfo()">
            <template #reference>
              <div class="footer_Admin">Liuyi Chen</div>
            </template>
            <div style="display: flex; align-items: center;">
              <img :src="adminAva" class="otherUserAvatar" />
              <div>
                <div>{{ adminEmail }}</div>
                <div>{{ adminName }}</div>
                <div>
                  <el-icon v-if="adminGender === '0'">
                    <Male />
                  </el-icon>
                  <el-icon v-else>
                    <Female />
                  </el-icon>
                </div>
              </div>
            </div>
          </el-popover>

          <el-popover placement="top-start" :width="350" trigger="click" @show="getAdminUser(5)"
            @before-leave="cleanOtherUserInfo()">
            <template #reference>
              <div class="footer_Admin">Guoling Dong</div>
            </template>
            <div style="display: flex; align-items: center;">
              <img :src="adminAva" class="otherUserAvatar" />
              <div>
                <div>{{ adminEmail }}</div>
                <div>{{ adminName }}</div>
                <div>
                  <el-icon v-if="adminGender === '0'">
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
        <div class="footer-top-blocks">
          <h2>ABOUT</h2>
          <el-popover placement="top-start" :width="230" trigger="click"
            content="Front-end: Vue.js Ecosystem + Element Plus + Axios. Back-end: Spring Boot + Redis + Mysql.">
            <template #reference>
              <span class="footer-elements">How</span>
            </template>
          </el-popover>
          <el-popover placement="top-start" :width="230" trigger="click"
            content="COMP9900, Postgraduate Capstone Project, UNSW">
            <template #reference>
              <span class="footer-elements">Why</span>
            </template>
          </el-popover>
          <el-popover placement="top-start" :width="230" trigger="click"
            content="Sprint 1, June 15th 2023 - June 29th 2023. Sprint 2, June 29th 2023 - July 20th 2023. Sprint 3, July 20th 2023 - August 3rd 2023">
            <template #reference>
              <span class="footer-elements">When</span>
            </template>
          </el-popover>
        </div>
        <div class="footer-top-blocks">
          <h2>REFERRALS</h2>
          <a class="footer-elements" href="https://www.inkitt.com/genres/scifi">Inkitt</a>
          <a class="footer-elements" href="https://www.webnovel.com/">WebNovel</a>
          <a class="footer-elements" href="https://www.novelnow.com/">NovelNow</a>
        </div>
      </div>
    </div>
    <div class="footer-down">
      © 2023 NovelHub. All rights reserved.
    </div>
  </footer>
</template>

<style>
.footer-down {
  margin-top: 20px;
}

.footer_Admin {
  color: rgb(117, 117, 117);
  margin-bottom: 5px;
}

.footer_Admin:hover {
  cursor: pointer;
  text-decoration: underline;
}

.footer {
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding-top: 20px;
  padding-bottom: 20px;
}

.footer-top {
  display: flex;
}

.footer-top-blocks {
  display: flex;
  flex-direction: column;
  margin-right: 60px;
}

.footer-elements {
  color: rgb(117, 117, 117);
  text-decoration: none;
  margin-bottom: 5px;
}

.footer-elements:hover {
  cursor: pointer;
  text-decoration: underline;
}

.footer-right {
  margin-left: 20px;
  display: flex;
}
</style>
<style>
@media screen and (max-width:431px) {

  .footer-top .logo_container {
    margin-left: -40px;
  }

  .footer-top {
    flex-direction: column;
    padding-left: 20px;
    font-size: 10pt;
  }

  .footer-top-blocks {
    display: flex;
    margin-right: 20px;
  }

  .footer-right {
    margin-left: 0px;
    display: flex;
  }

  .footer-down {
    font-size: 10pt;
  }
}
</style>

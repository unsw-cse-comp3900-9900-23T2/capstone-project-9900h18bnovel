<script setup>
import axios from 'axios';
import {
  Male,
  Female,
  Upload,
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
    }
  },

  mounted() {
    if (this.$store.getters.isAuthenticated) {
      setTimeout(() => {
        this.getUserInfo();
        this.load = false;
        this.showProfilePage = true;
      }, 500);
    } else {
      ElMessage.error("Without sign in cannot go to user profile");
      this.$router.push('/home');
    }


  },
  methods: {
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
  }
}
</script>
<template>
  <div v-loading.fullscreen="load" element-loading-spinner=" ">
  </div>
  <div v-show="showProfilePage" class="profile-body">
    <div class="profile-background"></div>
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
</template>

<style>
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

.profile-body {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
  background-color: #f6f7fc;
  padding-bottom: 20px;
}

.profile-avatar {
  margin-top: -100px;
  margin-left: 20px;
  border: 5px solid white;
}
</style>
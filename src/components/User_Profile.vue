<script setup>
import {
  Edit,
  ShoppingCart,
  GoldMedal,
  Coin,
  Upload,
  Check,
} from '@element-plus/icons-vue';
import { ElIcon } from 'element-plus'
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

<script >
import Global_Footer from './Global_Footer.vue';
import { ElMessage } from "element-plus";
// import Login from './Auth_Page.vue';
export default {
  data() {
    return {
      isLoginVisible: false,
      userName: localStorage.getItem('username') || '',
      uid: localStorage.getItem('uid') || '',
      email: localStorage.getItem('email') || '',
      userPhoto: localStorage.getItem('userPhoto') || '',
      userSex: localStorage.getItem('userSex') || '',
      VIPLevel: 0,
      gender: '',
      isEditing: false,
      newGender: '',
      SubmitSex: '',
      CurrentPhoto: '',
      DefaultPhoto: 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300',
      //https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png
    }
  },
  mounted() {
    if (localStorage.getItem('userPhoto') === 'undefined' || !localStorage.getItem('userPhoto')) {
      this.CurrentPhoto = this.DefaultPhoto;
    }
  },
  components: {
    Global_Footer,
  },
  methods: {
    async UpdateGender() {
      if (this.newGender.toLowerCase() === 'male') {
        this.userSex = 0;
      } else if (this.newGender.toLowerCase() === 'female') {
        this.userSex = 1;
      } else {
        ElMessage.error('Please choose a provided gender!');
        this.userSex = '';
        this.isEditing = false;
        return;
      }
      if (!this.userPhoto || this.userPhoto === 'undefined') {
        this.userPhoto = '';
      }
      const requestData = {
        userId: parseInt(this.uid),
        username: this.userName,
        userPhoto: this.userPhoto,
        userSex: parseInt(this.userSex)
      }
      try {
        const response = await fetch("http://localhost:8888/api/front/user/modify_userInfo", {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(requestData)
        });
        if (response.status === 200) {
          const data = await response.json();
          console.log(data);
          if (data.code === '00000') {
            this.isEditing = false;
            this.userSex = this.newGender;
            localStorage.setItem('userSex', this.userSex);
          }
        }
      } catch (error) {
        console.error(error);
      }
    },
    openPhotoInput() {
      this.$refs.fileInput.click();
    },
    async CollectNewPhoto(event) {
      const file = event.target.files[0];
      const reader = new FileReader();
      if (this.userSex.toLowerCase() === "male") {
        this.SubmitSex = 0;
      } else {
        this.SubmitSex = 1;
      }
      reader.onload = () => {
        const requestData = {
          userId: parseInt(this.uid),
          username: this.userName,
          userPhoto: reader.result,
          userSex: parseInt(this.SubmitSex)
        }
        this.SubmitPhoto(requestData);
      };
      if (file) {
        reader.readAsDataURL(file);
      }

    },
    async SubmitPhoto(requestData) {
      try {
        console.log(requestData);
        const response = await fetch("http://localhost:8888/api/front/user/modify_userInfo", {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(requestData)
        });
        if (response.status === 200) {
          const data = await response.json();
          console.log(data);
          if (data.code === '00000') {
            this.CurrentPhoto = requestData.userPhoto;

          } else {
            console.log("User_Profile 169行有问题");
          }
        }
      } catch (error) {
        console.error(error);
      }
    },
    StartEditGender() {
      this.isEditing = true;
      this.newGender = this.userSex;
    }
  }
}
</script>
<template>
  <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
    element-loading-background="rgba(255, 255, 255, 255)"
    style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
  <div class="ProfileBody">
    <div class="ProfileContainer">
      <h1
        style="border-bottom: 1px solid; border-color: rgb(223, 223, 223); padding-bottom: 10px; margin-bottom: 22px; margin-top: 30px; text-align: center;">
        User Profile</h1>
      <div class="BasicInfoContainer">
        <div class="AvatarContainer">
          <span class="el-avatar el-avatar--circle"
            style="height: 200px; width: 200px; line-height: 200px; margin: 2em;"><img :src="CurrentPhoto"
              style="object-fit: cover;"></span>
          <input type="file" ref="fileInput" @change="CollectNewPhoto" style="display: none" />
          <el-button class="UploadPhotoButton" circle @click="openPhotoInput">
            <el-icon color="gray">
              <Upload />
            </el-icon>
          </el-button>
        </div>

        <div class="BasicInfo">
          <h3>UserID: {{ uid }} </h3>
          <div class="UserNameContainer">
            <h3>Username: {{ userName }} </h3>
            <el-button class="UpdateNameButton" @click="UpdateName" circle>
              <el-icon color="gray">
                <edit />
              </el-icon>
            </el-button>
          </div>
          <div class="EmailContainer">
            <h3>Email: {{ email }}</h3>
            <el-button class="UpdateGenderButton" @click="UpdateGender" circle>
              <el-icon color="gray">
                <edit />
              </el-icon>
            </el-button>
          </div>
          <div class="GenderContainer">
            <div class="gender-row" v-if="!isEditing">
              <h3>Gender:</h3>
              <div class="gender-info">
                <h3>{{ userSex }}</h3>
                <el-button class="UpdateGenderButton" @click="StartEditGender" circle>
                  <el-icon color="gray">
                    <edit />
                  </el-icon>
                </el-button>
              </div>
            </div>
            <div class="gender-row" v-else>
              <h3>Gender:</h3>
              <div class="input-container">
                <input type="text" v-model="newGender" placeholder="Male / Female" />
                <el-button class="SubmitGender" @click="UpdateGender" circle>
                  <el-icon color="lightgreen">
                    <Check />
                  </el-icon>
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-divider />
    </div>
    <div class="OverviewContainer">
      <h1 style="text-align: center;">
        Account Overview</h1>
      <el-divider />
      <div class="AccountInfo">
        <div class="IsVIPCont">
          <h3>VIP Status:</h3>
          <el-icon :size="20" color="gray" style="margin: auto; ">
            <shopping-cart />
          </el-icon>
        </div>
        <div class="VIPLevelCont">
          <h3>VIP Level:</h3>
          <el-icon :size="20" color="gray" style="margin: auto; ">
            <GoldMedal />
          </el-icon>
        </div>
        <div class="BalanceCont">
          <h3>Balance:</h3>
          <el-icon :size="20" color="gray" style="margin: auto; ">
            <coin />
          </el-icon>
        </div>
        <div class="CreateTimeCont">
          <h3>Time of Account Creating:</h3>
        </div>
      </div>
    </div>
  </div>
  <Global_Footer />
</template>

<style>
.ProfileBody {
  width: 1152px;
  min-width: 1152px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: auto;
}

.IsVIPCont,
.VIPLevelCont,
.BalanceCont {
  display: flex;
  flex-direction: row;
}

.AvatarContainer {
  position: relative;
}

.UploadPhotoButton {
  position: absolute;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  bottom: 3em;
  right: 3em;
}

.UpdateGenderButton {
  margin: auto 1em;
}

.GenderInputCont {
  display: flex;
  flex-direction: row;
  padding: 8px;
}

.ProfileContainer,
.OverviewContainer {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.AccountInfo {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  gap: 10px;
}

.BasicInfoContainer {
  display: flex;
  padding-bottom: 10px;
  margin-bottom: 22px;
  margin-top: 30px;
  text-align: center;
  flex-direction: row;
}

.BasicInfo {
  display: flex;
  flex-direction: column;
  margin-left: 5em;
  text-align: left;
}

.UserNameContainer,
.EmailContainer {
  display: flex;
  flex-direction: row;
  margin: auto 0px;
  text-align: left;
  justify-content: space-between;
}

.GenderContainer {
  display: flex;
  flex-direction: column;
}

.gender-row {
  display: flex;
  align-items: center;
}

.gender-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-grow: 1;
}

.input-container {
  display: flex;
  align-items: center;
}

.gender-row h3:first-child {
  margin-right: 10px;
}
</style>
<script setup>
import {
  Edit,
  Upload,
  Check,
  User,
  /*Postcard,*/
  MessageBox,
  Filter,
  CaretBottom, Reading, Delete, /*Document, CollectionTag, View, Clock,*/
} from '@element-plus/icons-vue';
import { computed } from 'vue'
const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '8px',
  }
  return {
    marginRight: marginMap['large'.value] || marginMap.default,
  }
})
import { ElIcon } from 'element-plus'
import {getItemColor} from "@/utils";
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
      userSex: localStorage.getItem('userSex')? localStorage.getItem('userSex'):"",
      VIPLevel: 0,
      gender: '',
      isGenderEditing: false,
      isNameEditing: false,
      isLoadBooks: false,
      newGender: '',
      SubmitSex: '',
      newUserName: '',
      CurrentPhoto: '',
      DefaultPhoto: 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300',
      AllCollections: '',
      EachBook: '',
    }
  },
  computed: {
    truncatedDesc() {
      return function (desc) {
        if (desc.length <= 200) {
          return desc;
        } else {
          return desc.slice(0, 200) + "... ...";
        }
      };
    }
  },
  mounted() {
    if (localStorage.getItem('userPhoto') === 'undefined' || !localStorage.getItem('userPhoto')) {
      this.CurrentPhoto = this.DefaultPhoto;
    } else{
      this.CurrentPhoto = this.userPhoto;
    }
    console.log("@#$@#$"+this.userSex);
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
        if(this.userSex === 'undefined' || !localStorage.getItem('userSex')){
          this.userSex = '';
        }
        this.isGenderEditing = false;
        return;
      }
      if (!this.userPhoto || this.userPhoto === 'undefined'|| !localStorage.getItem('userPhoto')) {
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
            this.isGenderEditing = false;
            console.log("-*/-*/"+this.userSex);
            // this.userSex = this.newGender;
            localStorage.setItem('userSex', this.userSex);
            this.$store.dispatch('sex',this.userSex);
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
      /*if (this.userSex.toLowerCase() === "male") {
        this.SubmitSex = 0;
      } else {
        this.SubmitSex = 1;
      }*/
      reader.onload = () => {
        const requestData = {
          userId: parseInt(this.uid),
          username: this.userName,
          userPhoto: reader.result,
          userSex: parseInt(this.userSex)
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
            localStorage.setItem('userPhoto',requestData.userPhoto);
            this.$store.dispatch('photo',requestData.userPhoto);
          } else {
            console.log("User_Profile 169行有问题");
          }
        }
      } catch (error) {
        console.error(error);
      }
    },
    async UpdateNewName(){
      if (this.newUserName === ''){
        ElMessage.error('Please input a valid username!');
        this.isNameEditing = false;
        return;
      }
      const requestData = {
        userId: parseInt(this.uid),
        username: this.newUserName,
        userPhoto: this.userPhoto,
        userSex: parseInt(this.userSex)
      }
      console.log(requestData);
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
            this.isNameEditing = false;
            this.userName = this.newUserName;
            localStorage.setItem('username', this.newUserName);
            this.$store.dispatch('username', this.newUserName);
          }
        }
      } catch (error) {
        console.error(error);
      }

    },
    StartEditGender() {
      this.isGenderEditing = true;
      // this.newGender = this.userSex;
    },
    StartEditName(){
      this.isNameEditing = true;
    },
    async LoadingCollections(){
      this.isLoadBooks = true;
      try {
        const response = await fetch(`http://localhost:8888/api/front/user/user_collect?userId=${this.$store.getters.GetUID}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status === 200) {
          const data = await response.json();
          console.log(data);
          if (data.code === '00000') {
            this.AllCollections = data.data;
          }
        }
      } catch (error) {
        console.error(error);
      }
    },
    GoToBook(bookId){
      this.$router.push('/bookInfo/'+bookId);
    },
    async cancel_collect(bookId) {
      if (!this.$store.getters.isAuthenticated) {
        ElMessage.error("Please log in to collect");
      } else {
        const reqbody = {
          userId: this.$store.getters.GetUID,
          bookId: bookId,
        };
        console.log(reqbody);
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
      }
      this.isLoadBooks = false;
    },
  }
}
</script>
<template>
  <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
    element-loading-background="rgba(255, 255, 255, 255)"
    style="top:50%; left: 50%; transform: translate(-50%,-50%); position: absolute;"></div>
  <div class="ProfileBody">
    <div class="ProfileContainer">
      <h1 style="text-align: center; align-content: center; margin-top: 1.5em;">
        Basic Information</h1>
      <el-divider />
      <el-container>
        <el-aside ><!--width="40%"-->
          <div class="AvatarContainer">
          <span class="el-avatar el-avatar--circle"
                style="height: 200px; width: 200px; line-height: 200px; margin: 2em;"><img :src="this.$store.state.photo? this.$store.state.photo : CurrentPhoto" style="object-fit: contain;"></span>
            <input type="file" ref="fileInput" @change="CollectNewPhoto" style="display: none" />
            <el-button class="UploadPhotoButton" circle @click="openPhotoInput">
              <el-icon color="gray">
                <Upload />
              </el-icon>
            </el-button>
          </div>
        </el-aside>
        <el-main>
          <el-descriptions
              class="BasicInfoContainer"
              :column="1"
              :size="large"
              border>
<!--            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <Postcard />
                  </el-icon>
                  UserID
                </div>
              </template>
              {{ uid }}
            </el-descriptions-item>-->
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <MessageBox />
                  </el-icon>
                  Email
                </div>
              </template>
              {{ email }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <user />
                  </el-icon>
                  Username
                </div>
              </template>
              <div class="name-row" v-if="!isNameEditing">
                <div class="name-info">
                  {{ userName }}
                  <el-button class="UpdateNameButton" @click="StartEditName" circle>
                    <el-icon color="gray">
                      <edit />
                    </el-icon>
                  </el-button>
                </div>
              </div>
              <div class="name-row" v-else>
                <div class="input-name-container">
                  <input type="text" v-model="newUserName" :placeholder="userName" />
                  <el-button class="SubmitNewName" @click="UpdateNewName" circle>
                    <el-icon color="lightgreen">
                      <Check />
                    </el-icon>
                  </el-button>
                </div>
              </div>
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <Filter />
                  </el-icon>
                  Gender
                </div>
              </template>
              <div class="gender-row" v-if="!isGenderEditing">
                <div class="gender-info">
                  {{this.$store.state.sex== 0 ? 'Male' : 'Female' }}<!---->
                  <el-button class="UpdateGenderButton" @click="StartEditGender" circle>
                    <el-icon color="gray">
                      <edit />
                    </el-icon>
                  </el-button>
                </div>
              </div>
                <div class="gender-row" v-else>
                  <div class="input-gender-container">
                    <input type="text" v-model="newGender" placeholder="Male / Female" />
                    <el-button class="SubmitGender" @click="UpdateGender" circle>
                      <el-icon color="lightgreen">
                        <Check />
                      </el-icon>
                    </el-button>
                  </div>
                </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-main>
      </el-container>
    </div>

    <div class="MyCollectionContainer">
      <el-divider />
      <h1 style="text-align: center; align-content: center;">
        My Collection</h1>
      <el-divider />
      <div class="CollectionsContainer">
          <el-button class = "loadingCollButton " v-if = "!isLoadBooks" type="primary" @click = "LoadingCollections">
            Show All My Collections
            <el-icon class="el-icon--right">
              <CaretBottom />
            </el-icon>
          </el-button>
        <div v-else>
          <div class="bookInfo" v-for="book in AllCollections" :key = "book.bookId" >
            <img :src="book.picUrl" style="height: 250px; margin: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);" />
            <div class="bookContentDetail">
              <div style="display: flex; justify-content: space-between; align-items: center;">
                <h1 style="display: flex; align-items: center;"> {{ book.bookName }}
                </h1>
                <el-tag style="font-size: 14pt; margin: 0px 10px;" size="large" effect="plain">{{ book.bookStatus === "1" ? "Completed" :
                    "Ongoing" }}</el-tag>
              </div>
              <el-tag effect="plain"  :style="getItemColor(book.categoryName)">{{
                  book.categoryName
                }}</el-tag>
              <div style="font-size: 14pt; margin: 10px auto 10px 0;"> Author: {{ book.authorName }}</div>
              <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 9; overflow: hidden;"> {{truncatedDesc(book.bookDesc)  }}</div>
              <div style="bottom: 0; position: absolute;">

<!--                <div style="display: flex; font-size: 16pt; align-items: center; margin-top: 10px;">
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
                </div>-->
                <div style="margin-top: 10px;">
                  <el-button style="font-size: 14pt;" size="large" type="primary" round :icon="Reading" @click = "GoToBook(book.bookId)"><!--@click="goToContent(chapters[0] ? chapters[0].id : null, chapters[0] ? chapters[0].chapterName : null, 0)"-->
                    READ
                  </el-button>
                  <el-button style="font-size: 14pt;" size="large" type="primary" round :icon="Delete"
                             @click="cancel_collect(book.bookId)">
                    REMOVE FROM COLLECTIONS
                  </el-button>
                </div>
              </div>
            </div>
          </div>
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
.ProfileContainer, .MyCollectionContainer {
  display: flex;
  flex-direction: column;
  width: 70%;
  margin: auto;
}
.BasicInfoContainer{
  margin: auto;
}
.AvatarContainer {
  position: relative;
  margin: auto;
}
.loadingCollButton{
  margin-bottom: 2em;
}
.UploadPhotoButton {
  position: absolute;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  bottom: 3em;
  right: 6em;
}
.bookInfo {
  width: 95%;
  display: flex;
  background-color: whitesmoke;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 5px;
  margin: 0 auto 20px auto;
  padding: 10px;
}

.bookContentDetail {
  width: 75%;
  position: relative;
  margin: 0 30px 20px 20px;
}
.UpdateGenderButton, .UpdateNameButton {
  margin: auto 1em;
}
.el-descriptions {
  margin-top: 2em;
}
.cell-item {
  display: flex;
  align-items: center;
  label-align: left;
}

.gender-row, .name-row {
  display: flex;
  align-items: center;
}

.gender-info, .name-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-grow: 1;
}

.input-gender-container, .input-name-container {
  display: flex;
  align-items: center;
}

.gender-row h3:first-child, .name-row h3:first-child {
  margin-right: 10px;
}

.CollectionsContainer{
  display: flex;
  flex-direction: column;
  width: 100%;
}
</style>
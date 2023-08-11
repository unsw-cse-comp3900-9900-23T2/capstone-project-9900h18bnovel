<script setup>
import {
  Edit,
  Upload,
  Check,
  User,
  MessageBox,
  Filter,
  Reading,
  Delete,
  Close,
} from '@element-plus/icons-vue';
import { getItemColor, svg } from '../utils';
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
</script>
<script >
import Global_Footer from './Global_Footer.vue';
import { ElMessage } from "element-plus";
import axios from "axios";
import { ref } from 'vue';
const NewGender = ref([]);
const options = [
  {
    value: 'male',
    label: 'Male',
  },
  {
    value: 'female',
    label: 'Female',
  }]
// import Login from './Auth_Page.vue';
export default {
  data() {
    return {
      isLoginVisible: false,
      userName: localStorage.getItem('username') || '',
      uid: localStorage.getItem('uid') || '',
      email: localStorage.getItem('email') || '',
      userPhoto: localStorage.getItem('userPhoto') || '',
      userSex: localStorage.getItem('userSex') ? localStorage.getItem('userSex') : "",
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
      DefaultFemalePhoto: 'https://bpic.51yuansu.com/pic3/cover/02/84/17/5a5c92db641d9_610.jpg',
      DefaultMalePhoto: 'https://bpic.51yuansu.com/pic3/cover/03/47/83/5badd6731ddff_610.jpg',
      AllCollections: '',
      EachBook: '',
      loading: true,
      showPage: false,
    }
  },
  computed: {
    truncatedDesc() {
      return function (desc) {
        if (desc.length <= 100) {
          return desc;
        } else {
          return desc.slice(0, 100) + "... ...";
        }
      };
    },
    CutTitle() {
      return function (title) {
        if (title.length <= 20) {
          return title;
        } else {
          return title.slice(0, 20) + " ...";
        }
      }
    }
  },
  mounted() {
    if (localStorage.getItem('userPhoto') === 'undefined' || !localStorage.getItem('userPhoto')) {
      this.CurrentPhoto = this.DefaultMalePhoto;
    } else {
      this.CurrentPhoto = this.userPhoto;
    }
    axios.post("http://localhost:8888/api/front/user/get_userInfo", {
      userId: localStorage.getItem('uid') ? localStorage.getItem('uid') : ''
    })
      .then(response => {
        console.log(response);
        if (response.data.code === "00000") {
          if (response.data.data.userPhoto) {
            localStorage.setItem('userPhoto', response.data.data.userPhoto);
            this.$store.dispatch('photo', response.data.data.userPhoto);
          } else {
            this.userPhoto = this.DefaultMalePhoto;
          }
          if (response.data.data.userSex) {
            localStorage.setItem('userSex', response.data.data.userSex);
            this.userSex = response.data.data.userSex;
            this.$store.dispatch('sex', response.data.data.userSex);
            if (response.data.data.userSex === "1" && response.data.data.userPhoto === this.DefaultMalePhoto) {
              /*Female with make avatar should be modified*/
              localStorage.setItem('userPhoto', this.DefaultFemalePhoto);
              this.$store.dispatch('photo', this.DefaultFemalePhoto);
            }
          } else {
            this.userSex = '';
          }
        }
      }).catch(error => {
        console.error("Failed in getting user information：", error);
      });
    axios.get(`http://localhost:8888/api/front/user/user_collect?userId=${this.$store.getters.GetUID}`)
      .then(response => {
        if (response.status === 200) {
          if (response.data.code === '00000') {
            this.AllCollections = response.data.data;
          }
        }
      }).catch(error => {
        // 请求失败时的处理
        console.error("Failed in loading mycollections：", error);
      });
    setTimeout(() => {
      this.loading = false;
      this.showPage = true;
    }, 500);
  },
  components: {
    Global_Footer,
  },
  methods: {
    async UpdateGender() {
      this.userPhoto = localStorage.getItem("userPhoto");
      console.log("-----" + this.userPhoto);
      if (NewGender.value[0] === 'male') {/*.toLowerCase()*/
        this.userSex = 0;
        if (this.userPhoto === this.DefaultMalePhoto || this.userPhoto === this.DefaultFemalePhoto) {
          console.log("走男的这儿了？");
          this.userPhoto = this.DefaultMalePhoto;
          localStorage.setItem('userPhoto', this.DefaultMalePhoto);
          this.$store.dispatch('photo', this.DefaultMalePhoto);
        }
      } else if (NewGender.value[0] === 'female') {/*.toLowerCase()*/
        this.userSex = 1;
        if (this.userPhoto === this.DefaultMalePhoto || this.userPhoto === this.DefaultFemalePhoto) {
          console.log("走女的这儿了？");
          this.userPhoto = this.DefaultFemalePhoto;
          localStorage.setItem('userPhoto', this.DefaultFemalePhoto);
          this.$store.dispatch('photo', this.DefaultFemalePhoto);
        }
      } else {
        ElMessage.error('Please choose a provided gender!');
        if (this.userSex === 'undefined' || !localStorage.getItem('userSex')) {
          this.userSex = '';
        }
        this.isGenderEditing = false;
        return;
      }
      const requestData = {
        userId: parseInt(this.uid),
        username: this.userName,
        userPhoto: this.userPhoto,
        userSex: parseInt(this.userSex)
      };
      await axios.put("http://localhost:8888/api/front/user/modify_userInfo", requestData)
        .then(response => {
          if (response.status === 200) {
            const data = response.data;
            if (data.code === '00000') {
              this.isGenderEditing = false;
              localStorage.setItem('userSex', this.userSex);
              this.$store.dispatch('sex', this.userSex);
              ElMessage.success("Gender changed!");
              console.log("Change gender successful!")
            }
          }
        })
        .catch(error => {
          console.error(error);
        });
    },
    openPhotoInput() {
      this.$refs.fileInput.click();
    },
    async CollectNewPhoto(event) {
      const file = event.target.files[0];
      const reader = new FileReader();
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
      await axios.put("http://localhost:8888/api/front/user/modify_userInfo", requestData)
        .then(response => {
          if (response.status === 200) {
            console.log(response);
            if (response.data.code === '00000') {
              this.CurrentPhoto = requestData.userPhoto;
              ElMessage.success("Avatar changed!");
              localStorage.setItem('userPhoto', requestData.userPhoto);
              this.userPhoto = requestData.userPhoto;
              this.$store.dispatch('photo', requestData.userPhoto);
              console.log("上传完后头像为：" + this.userPhoto);
            } else {
              console.log("User_Profile 203行有问题");
            }
          }
        })
        .catch(error => {
          console.error(error);
        });
    },
    async UpdateNewName() {
      if (this.newUserName === '') {
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
      await axios.put("http://localhost:8888/api/front/user/modify_userInfo", requestData)
        .then(response => {
          if (response.status === 200) {
            console.log(response);
            if (response.data.code === '00000') {
              ElMessage.success("Username changed!");
              this.isNameEditing = false;
              this.userName = this.newUserName;
              localStorage.setItem('username', this.newUserName);
              this.$store.dispatch('username', this.newUserName);
            } else {
              console.log("User_Profile 228行有问题");
            }
          }
        })
        .catch(error => {
          console.error(error);
        });
    },
    StartEditGender() {
      this.isGenderEditing = true;
      this.isNameEditing = false;
    },
    StartEditName() {
      this.isNameEditing = true;
      this.isGenderEditing = false;
    },
    CancelNameInput() {
      this.isNameEditing = false;
    },
    CancelGenderSelect() {
      this.isGenderEditing = false;
    },
    GoToBook(bookId) {
      this.$router.push('/bookInfo/' + bookId);
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
        await axios.post('http://localhost:8888/api/front/book/cancel_collect', reqbody)
          .then(response => {
            if (response.status === 200) {
              ElMessage.success("Collection Removed");
              this.AllCollections = this.AllCollections.filter(book => book.bookId !== bookId)
              console.log(response);
            }
            else {
              console.log("User_Profile 262行有问题");
            }
          })
          .catch(error => {
            console.error(error);
          });
      }
      this.isLoadBooks = false;
    },

  }
}
</script>
<template>
  <div v-loading.lock="loading" :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
    element-loading-background="rgba(255, 255, 255, 255)" class="page-center"></div>
  <div v-if="showPage">
    <div class="ProfileBody">
      <div class="ProfileContainer">
        <h1 style="text-align: center; align-content: center; margin-top: 1.5em;">
          Basic Information</h1>
        <el-divider />
        <div style="display: flex; align-items: center; margin: auto;">
          <div class="AvatarContainer">
            <span class="el-avatar el-avatar--circle"
              style="height: 200px; width: 200px; margin: 3em;"><img
                :src="this.$store.state.photo ? this.$store.state.photo : CurrentPhoto"
                style="object-fit: contain;"></span>
            <input type="file" ref="fileInput" @change="CollectNewPhoto" style="display: none" />
            <el-button class="UploadPhotoButton" circle @click="openPhotoInput">
              <el-icon color="gray">
                <Upload />
              </el-icon>
            </el-button>
          </div>
          <table>
            <tbody class="InfoTableBody">
              <div class="Binfo">
                <tr class="InfoLine">
                  <td class="InfoTitle">
                    <el-icon :style="iconStyle" style="color: dimgray; margin: 19px 5px 19px 10px;">
                      <MessageBox />
                    </el-icon>
                    <span style="font-weight: 700; color: #606266; display: inline-block">Email</span>
                  </td>
                  <td class="InfoCont" style="margin-left: 10px; font-size:16px; color:  #606266; display: inline-block;">
                    {{ email }}
                  </td>
                </tr>
                <tr class="InfoLine">
                  <td class="InfoTitle">
                    <div class="cell-item">
                      <el-icon :style="iconStyle" style="color: dimgray; margin: 19px 5px 19px 10px;">
                        <user />
                      </el-icon>
                      <span style="font-weight: 700; color: #606266; display: inline-block">
                        Username
                      </span>
                    </div>
                  </td>
                  <td class="InfoCont">
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
                        <input type="text" v-model="newUserName" :placeholder="userName"
                          style="width: 190px; margin-left: 10px;" />
                        <el-button class="CancelSubmitName" style="margin: auto 0px auto 10px;" @click="CancelNameInput"
                          circle>
                          <el-icon color="grey">
                            <Close />
                          </el-icon>
                        </el-button>
                        <el-button class="SubmitNewName" style="margin: auto 0px auto 10px;" @click="UpdateNewName"
                          circle>
                          <el-icon color="lightgreen">
                            <Check />
                          </el-icon>
                        </el-button>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr class="InfoLine">
                  <td class="InfoTitle">
                    <div class="cell-item">
                      <el-icon :style="iconStyle" style="color: dimgray; margin: 19px 5px 19px 10px;">
                        <Filter />
                      </el-icon>
                      <span style="font-weight: 700; color: #606266; display: inline-block">
                        Gender
                      </span>
                    </div>
                  </td>
                  <td class="InfoCont">
                    <div class="gender-row" v-if="!isGenderEditing">
                      <div class="gender-info">
                        {{ this.$store.state.sex == null || '' ? "Record Now!" : (this.$store.state.sex == 0 ? 'Male' :
                          'Female')
                        }}
                        <el-button class="UpdateGenderButton" @click="StartEditGender" circle>
                          <el-icon color="gray">
                            <edit />
                          </el-icon>
                        </el-button>
                      </div>
                    </div>
                    <div class="gender-row" v-else>
                      <div class="input-gender-container">
                        <el-cascader v-model="NewGender" :options="options" @change="UpdateGender()"
                          style="width: 190px; margin-left:10px;" />
                        <el-button class="CancelGenderSelect" style="margin: auto 0px auto 10px;"
                          @click="CancelGenderSelect" circle>
                          <el-icon color="grey">
                            <Close />
                          </el-icon>
                        </el-button>
                      </div>
                    </div>
                  </td>
                </tr>
              </div>
            </tbody>
          </table>
        </div>
      </div>

      <div class="MyCollectionContainer">
        <el-divider />
        <h1 style="text-align: center; align-content: center;">
          My Collection</h1>
        <el-divider />
        <div class="CollectionsContainer">
          <div v-if="AllCollections.length == 0">
            <h2 style="color: darkgray; margin: 2em auto; text-align: center;">You don't have any collection yet!</h2>
          </div>
          <div class="bookInfo" v-for="book in AllCollections" :key="book.bookId">
            <img :src="book.picUrl"
              style="height: 250px; width: 156px; margin: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);" />
            <div class="bookContentDetail">
              <div style="display: flex; justify-content: space-between; align-items: center;">
                <h1 style="display: flex; align-items: center;"> {{ CutTitle(book.bookName) }}
                </h1>
                <el-tag style="font-size: 14pt; margin: 0px 10px;" size="large" effect="plain">{{ book.bookStatus === "1"
                  ?
                  "Completed" :
                  "Ongoing" }}</el-tag>
              </div>
              <el-tag effect="plain" :style="getItemColor(book.categoryName)">{{
                book.categoryName
              }}</el-tag>
              <div style="font-size: 14pt; margin: 10px auto 10px 0;"> Author: {{ book.authorName }}</div>
              <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 9; overflow: hidden;">
                {{ truncatedDesc(book.bookDesc) }}</div>
              <div style="bottom: 0; position: absolute;">
                <div style="margin-top: 10px;">
                  <el-button style="font-size: 14pt;" size="large" type="primary" round :icon="Reading"
                    @click="GoToBook(book.bookId)"><!--@click="goToContent(chapters[0] ? chapters[0].id : null, chapters[0] ? chapters[0].chapterName : null, 0)"-->
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
    <Global_Footer />
  </div>
</template>

<style>
.ProfileBody {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
}

.ProfileContainer,
.MyCollectionContainer {
  display: flex;
  flex-direction: column;
  margin: auto;
}

.BasicInfoContainer {
  margin: auto;
}

.UploadPhotoButton {
  position: absolute;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  bottom: 3em;
  right: 6em;
}

.AvatarContainer {
  position: relative;
}

.bookInfo {
  display: flex;
  background-color: whitesmoke;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 5px;
  margin: 0 auto 20px auto;
  padding: 10px;
}

.bookContentDetail {
  position: relative;
  margin: 0 30px 20px 20px;
}

.UpdateGenderButton,
.UpdateNameButton {
  margin: auto 1em;
}

.InfoLine {
  width: 500px;
  height: 50px;
  padding: 8px;
  border: #949494 solid;
}

table {
  border-collapse: collapse;
}

.InfoTableBody {
  border: 0.5px solid #f5f7fa;
}

.InfoTitle {
  width: 184px;
  height: 34px;
  background-color: rgba(61, 61, 62, 0.1);
  border: 0.5px solid #f5f7fa;
}

.InfoCont {
  width: 284px;
  height: 34px;
}

.el-descriptions {
  margin-top: 2em;
}

.cell-item {
  display: flex;
  align-items: center;
}

.gender-row,
.name-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  right: 0;
  justify-content: space-between;
}

.gender-info,
.name-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  flex-grow: 1;
  font-size: 16px;
  margin-left: 10px;
  color: #606266;
}

.input-gender-container,
.input-name-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

}

.gender-row h3:first-child,
.name-row h3:first-child {
  margin-right: 10px;
}

.CollectionsContainer {
  display: flex;
  flex-direction: column;
  width: 100%;
}
</style>
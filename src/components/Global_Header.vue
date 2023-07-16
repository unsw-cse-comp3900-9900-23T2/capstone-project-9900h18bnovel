<script setup>
import {
  Search,
  User,
  CircleCloseFilled,
} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
</script >
<script>
import { logout } from '../utils';

export default {
  props: {
    keyword: {
      type: String,
      default: ''
    },
    isLoginVisible: {
      type: Boolean,
      default: false
    },
    verImage: {
      type: String,
      default: ''
    },
    sessionId: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      header_left: 'header_left',
      header_right: 'header_right',
      login_button: 'Sign in',
      isSearchActive: false,
      searchInput: '',
      uid: localStorage.getItem('uid') || '',
      email: localStorage.getItem('email') || '',
      userName: localStorage.getItem('username') || '',
      userPhoto: '',
      userSex: '',
      gender: '',
      DefaultPhoto: 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300',
    }
  },

  mounted() {
    document.addEventListener('click', this.searchGlobalClick);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.searchGlobalClick);
  },
  methods: {
    toggleSearch() {
      this.isSearchActive = true;
    },
    handleSearch() {
      this.$emit("handleSearch", this.searchInput);
    },
    searchGlobalClick() {
      if (this.isSearchActive == true) {
        this.isSearchActive = false;
      }
    },
    goHome() {
      this.$router.push('/home');
    },
    showLogin() {
      this.$emit('showLogin');
    },
    closeLoginBox() {
      this.$emit('closeLoginBox');
    },
    async ShowUserProfile() {
      this.$router.push('/userprofile');
      const requestData = {
        userId: this.uid
      }
      console.log(requestData);
      try {
        const response = await fetch("http://localhost:8888/api/front/user/get_userInfo", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(requestData)
        });
        if (response.status == 200) {
          const data = await response.json();
          console.log(data);
          if (data.code === "00000") {
            if (data.data.userPhoto) {
              const userPh = localStorage.setItem('userPhoto', data.userPhoto);
              this.userPhoto = userPh;
              this.$store.dispatch('photo', data.data.userPhoto);
            } else {
              this.userPhoto = this.DefaultPhoto;
            }
            if (data.data.userSex) {
              localStorage.setItem('userSex', data.userSex);
              const userS = data.userSex;
              this.userSex = userS;
              this.$store.dispatch('sex', data.data.userSex);
              console.log(userS);
              if (userS === '0') {//这里目前还不能确定是'0'还是0，先这样吧
                this.gender = 'Male';
              } else {
                this.gender = 'Female';
              }
            } else {
              this.userSex = '';
            }

          }
        } else {
          console.log("Global_Header.vue 的78行附近有问题");
        }
      } catch (error) {
        ElMessage.error(error);
      }
    },
    clearSearch() {
      this.searchInput = null;
      this.$emit("clearSearch");
    }
  },

}

</script>
<template>
  <div class="header_container">
    <!-- Click here will return to Home page in any circumstances -->
    <div class="logo_container">
      <img src="..\logo.png" class="logo" @click="goHome">
    </div>
    <div class="search_container">
      <div v-if="!isSearchActive">
        <el-button class="searchButton" ref="searchContainer" @click.stop="toggleSearch" :icon="Search"
          round>Search</el-button>
      </div>
      <div v-else @click.stop>
        <el-input class="searchText" v-model="searchInput" :placeholder="keyword ? keyword : 'Please Enter Keyword'"
          @keyup.enter="handleSearch">
          <template #prepend>
            <el-button @click.stop="handleSearch" :icon="Search" />
          </template>
          <template #append>
            <el-button :icon="CircleCloseFilled" @click="clearSearch" />
          </template>
        </el-input>
      </div>
    </div>
    <div class="mobile_search_container">
      <div v-if="!isSearchActive">
        <el-button class="mobile_searchButton" ref="searchContainer" @click.stop="toggleSearch" :icon="Search"
          round></el-button>
      </div>
      <div v-else @click.stop>
        <el-input class="mobile_searchText" v-model="searchInput" placeholder="Keyword">
          <template #prepend>
            <el-button @click.stop="handleSearch" :icon="Search" />
          </template>
        </el-input>
      </div>
    </div>
    <!-- This right side of class will represent user thumbnail, name and log out button once token exists -->
    <!-- Click here will link to login page -->
    <div v-if="!this.$store.state.token"
      style="display: flex; align-items: center; justify-content: flex-end; width: 200px;">
      <el-button class="login_button" type="primary" @click="showLogin"><el-icon>
          <User />
        </el-icon>{{ login_button }}</el-button>
    </div>
    <div v-else style="color: white; display: flex; align-items: center; justify-content: space-between; width: 200px;">
      <div style="display: flex; flex-direction: column; align-items: center;" @click="ShowUserProfile()">
        <el-avatar :size="70" :src="img ? img : this.DefaultPhoto" />
        <div>{{ this.$store.state.userName ? this.$store.state.userName : userName }}</div>
      </div>
      <el-button class="logout_button" type="primary" @click="logout"><el-icon>
          <User />
        </el-icon> Sign out </el-button>
    </div>
  </div>
</template>

<style >
.logo_container {
  height: 100px;
  margin-left: 20px;
  object-fit: contain;
}

.logo {
  height: 100%;
}

.logo:hover {
  cursor: pointer;
}

.search_container {
  display: block;
  text-align: center;
  width: 500px;
}

.searchButton {
  width: 30vh;

}

.login_button {
  margin-right: 20px;
  width: 100px;
}

.logout_button {
  width: 100px;
}

.mobile_search_container {
  display: none;
}

.header_container {
  margin: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: black;
  height: 100px;
  min-width: 1152px;
}

@media (max-width: 500px) {
  .mobile_search_container {
    display: block;
  }

  .search_container {
    display: none;
  }
}
</style>
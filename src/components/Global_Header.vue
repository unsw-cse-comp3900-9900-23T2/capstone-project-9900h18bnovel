<script setup>
import {
  User,
  HomeFilled,
  Platform,
  TrendCharts,
  EditPen,
} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
</script >
<script>
import { logout } from '../utils';
export default {
  emits: ['clearSearch', 'handleSearch', 'showLogin'],
  props: {
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
      userPhoto: this.$store.getters.GetPhoto,
      userSex: '',
      activeIndex: "/home",
    }
  },

  mounted() {
    document.addEventListener('click', this.searchGlobalClick);
  },

  watch: {
    '$store.getters.GetPhoto'(photo) {
      this.userPhoto = photo;
    },
    '$store.getters.getSearchInput'(searchInput) {
      this.searchInput = searchInput;
    },
    '$route': {
      handler() {
        this.activeIndex = "/" + this.$route.path.split("/")[1];
      },
      immediate: true,
    },
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
    ShowUserProfile() {
      this.$router.push('/userprofile');
    },

    clearSearch() {
      this.searchInput = null;
      this.$emit("clearSearch");
    },
    goAuthorPage() {
      if (this.$store.getters.isAuthenticated) {
        window.open('/author');
      } else {
        ElMessage.error("Please Sign In First");
        this.showLogin();
      }

    }
  },

}

</script>
<template>
  <!-- <div
    style="width: 100%; height: 81px; background-color: #ffffff; border-bottom: 1px solid #e7e7e7; position: absolute; z-index: -10;">
  </div> -->
  <div class="header_container">
    <!-- Click here will return to Home page in any circumstances -->
    <div class="logo_container" @click="goHome">
      <img src="..\assets\logo1.png" class="logo">
      <h1 class="novelhub_name">
        NovelHub</h1>
    </div>
    <el-menu :default-active="activeIndex" mode="horizontal" router>
      <el-menu-item route index="/home"><el-icon>
          <HomeFilled />
        </el-icon>Home</el-menu-item>
      <el-menu-item route index="/browse"><el-icon>
          <Platform />
        </el-icon>Browse</el-menu-item>
      <el-sub-menu index="2">
        <template #title><el-icon>
            <TrendCharts />
          </el-icon>Ranking</template>
        <el-menu-item route index="/clickrank">Click Rank</el-menu-item>
        <el-menu-item route index="/newestrank">Newest Rank</el-menu-item>
        <el-menu-item route index="/updaterank">Update Rank</el-menu-item>
      </el-sub-menu>
      <el-menu-item @click="goAuthorPage"><el-icon>
          <EditPen />
        </el-icon>My Creation</el-menu-item>
    </el-menu>

    <div class="flexbox">
      <div class="search">
        <div>
          <input v-model="searchInput" @keyup.enter="handleSearch" type="text" placeholder="Search . . ." required>
        </div>
      </div>
    </div>

    <div v-if="!$store.state.token" style="display: flex; align-items: center; justify-content: flex-end; width: 200px;">
      <el-button class="login_button" type="primary" @click="showLogin"><el-icon>
          <User />
        </el-icon>{{ login_button }}</el-button>
    </div>
    <div v-else class="logout_section">
      <!-- <h3 style="color: black; margin-right: 20px;">{{ this.$store.state.userName ? this.$store.state.userName :
        userName }}</h3> -->
      <el-dropdown trigger="click">
        <div>
          <el-avatar v-if="userPhoto !== null" :size="70" :src="userPhoto" class="user_Avatar" />
          <el-avatar v-else :size="70" class="user_Avatar"> {{ $store.getters.GetUsername }} </el-avatar>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="ShowUserProfile()">My Profile</el-dropdown-item>
            <el-dropdown-item @click="logout($router.currentRoute.value.path.includes('userprofile'))" divided>
              Sign out
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style>
.novelhub_name {
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  color: rgb(68, 68, 68);
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.4);
}

.logo_container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 20px;
  margin-left: 20px;
}

.logo_container:hover {
  cursor: pointer;
}

.logo {
  height: 70px;
  width: 70px;
  object-fit: cover;
  margin-right: 10px;
}

.flexbox {
  width: 200px;
  height: 100%;
  display: flex;
  justify-content: right;
  align-items: center;
  right: 120px;
  position: absolute;
}

.search {
  margin: 20px;
}

.search>div {
  display: inline-block;
  position: relative;
}

.search>div:after {
  content: "";
  background: rgb(139, 139, 139);
  width: 2px;
  height: 15px;
  position: absolute;
  top: 22px;
  right: 0px;
  transform: rotate(135deg);
}

.search>div>input {
  color: rgb(0, 0, 0);
  font-size: 16px;
  background: transparent;
  width: 10px;
  height: 10px;
  padding: 12px;
  border: solid 2px rgb(139, 139, 139);
  outline: none;
  border-radius: 20px;
  transition: width 0.5s;
}

.search>div>input:hover {
  cursor: pointer;
}

.search>div>input::placeholder {
  color: rgb(139, 139, 139);
  opacity: 0;
  transition: opacity 0.5s ease-out;
}

.search>div>input:focus::placeholder {
  opacity: 1;
}

.search>div>input:focus,
.search>div>input:not(:placeholder-shown) {
  width: 200px;
}

.login_button {
  right: 20px;
  position: absolute;
  width: 100px;
}

.logout_section {
  display: flex;
  align-items: center;
  right: 20px;
  position: absolute;
}

.user_Avatar {
  border: 2px solid white;
  border-radius: 60px;
  width: 70px;
  height: 70px;
  margin-right: 20px;
}

.user_Avatar:hover {
  cursor: pointer;
}

.header_container {
  margin: 0;
  display: flex;
  align-items: center;
  background-color: #ffffff;
  border-bottom: 1px solid #e7e7e7;
  height: 81px;
  margin: auto;
  position: relative;
}


.header_container .el-menu--horizontal {
  border-bottom: none;
  background-color: #ffffff;
  width: 650px;
}

.header_container .el-menu--horizontal>.el-menu-item.is-active,
.header_container .el-menu--horizontal>.el-sub-menu.is-active .el-sub-menu__title {
  border-bottom: none;
}

.header_container .el-menu-item,
.header_container .el-menu--horizontal>.el-sub-menu.is-active .el-sub-menu__title {
  transition: background-color var(--el-transition-duration), color var(--el-transition-duration)
}

.header_container .el-menu--horizontal .el-menu-item:not(.is-disabled):hover,
.header_container .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.header_container .el-menu--horizontal>.el-sub-menu .el-sub-menu__title:hover {
  background-color: rgb(0, 0, 0, 0);
}


.header_container .el-menu--horizontal>.el-menu-item,
.header_container .el-menu--horizontal>.el-sub-menu .el-sub-menu__title {
  color: #717174;
  font-weight: bold;
}
</style>

<style>
@media screen and (max-width:431px) {
  .logo_container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 0px;
    margin-left: 0px;
  }

  .novelhub_name {
    font-size: 15pt;
  }

  .header_container {
    margin: 0;
    display: flex;
    align-items: center;
    background-color: #ffffff;
    border-bottom: 1px solid #e7e7e7;
    height: 81px;
    width: 100vw;
    margin: auto;
    position: relative;
  }

  .header_container .el-menu--horizontal {
    border-bottom: none;
    background-color: #ffffff;
    width: 10vw;
  }

  .logo {
    height: 60px;
    width: 60px;
    object-fit: cover;
  }

  .flexbox {
    width: 20vw;
    height: 100%;
    display: flex;
    justify-content: right;
    align-items: center;
    right: 10vw;
    top: 60px;
    position: absolute;
  }

  .search {
    margin: -20px;
  }

  .search>div {
    display: inline-block;
    position: relative;
  }

  .search>div:after {
    content: "";
    background: rgb(139, 139, 139);
    width: 2px;
    height: 10px;
    position: absolute;
    top: 16px;
    right: 0px;
    transform: rotate(135deg);
  }

  .search>div>input {
    color: rgb(0, 0, 0);
    font-size: 10pt;
    background: transparent;
    width: 10px;
    height: 10px;
    padding: 8px;
    border: solid 2px rgb(139, 139, 139);
    outline: none;
    border-radius: 20px;
    transition: width 0.5s;
  }

  .search>div>input:hover {
    cursor: pointer;
  }

  .search>div>input::placeholder {
    color: rgb(139, 139, 139);
    opacity: 0;
    transition: opacity 0.5s ease-out;
  }

  .search>div>input:focus::placeholder {
    opacity: 1;
  }

  .search>div>input:focus,
  .search>div>input:not(:placeholder-shown) {
    width: 90vw;
  }

  .login_button {
    right: 10px;
    position: absolute;
    width: 70px;
  }

  .logout_section {
    display: flex;
    align-items: center;
    right: 0px;
    position: absolute;
  }
}
</style>
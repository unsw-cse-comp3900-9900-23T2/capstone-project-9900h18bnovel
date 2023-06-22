<script setup>
import {
  Search,
  User
} from '@element-plus/icons-vue'
import login from './LoginPage';
</script >
<script>
export default {
  data() {
    return {
      header_container: 'header_container',
      header_left: 'header_left',
      header_right: 'header_right',
      login_button: 'Sign in',
      isSearchActive: false,
      isLoginVisible: false,
      searchInput: ''
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
      // Waiting for API
      console.log(this.searchInput);
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
      this.isLoginVisible = true;
    },
    closeLoginBox(){
      this.isLoginVisible = false;
    }
  }
}
</script>
<template>
  <div :class="header_container">
    <!-- Click here will return to Home page in any circumstances -->
    <img src="..\JustForFunLogo.png" class="logo" @click="goHome">

    <div class="search_container">
      <div v-if="!isSearchActive">
        <el-button class="searchButton" ref="searchContainer" @click.stop="toggleSearch" :icon="Search"
          round>Search</el-button>
      </div>
      <div v-else @click.stop>
        <el-input class="searchText" v-model="searchInput" placeholder="Please Enter Keyword">
          <template #prepend>
            <el-button @click.stop="handleSearch" :icon="Search" />
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
    <el-button class="login_button" type="primary" @click="showLogin"><el-icon>
        <User />
      </el-icon>{{ login_button }}</el-button>
  </div>
  <div v-if="isLoginVisible" class="loginSection">
    <login @cancel="closeLoginBox"/>
  </div>
</template>

<style >
.loginSection {
  position: absolute;
  z-index: 100;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
}

.logo {
  height: 60px;
  width: 20vh
}

.search_container {
  display: block;
}

.searchButton {
  width: 20vh;
}

.login_button {
  width: 10vh;
}

.mobile_search_container {
  display: none;
}

.header_container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: black;
  padding: 20px;
}

.header_left {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 70%;
}

.header_right {
  display: flex;
  justify-content: right;
  align-items: center;
  width: 30%;
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
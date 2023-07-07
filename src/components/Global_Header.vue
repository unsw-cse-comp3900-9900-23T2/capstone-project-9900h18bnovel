<script setup>
import {
  Search,
  User
} from '@element-plus/icons-vue'

</script >
<script>
import Login from './Auth_Page.vue';
export default {
  emits: ['showLogin', 'closeLoginBox', 'logout'],
  data() {
    return {
      header_left: 'header_left',
      header_right: 'header_right',
      login_button: 'Sign in',
      isSearchActive: false,
      searchInput: '',
      username: '',
      isLoginVisible: false,
    }
  },
  components: {
    Login,
  },
  mounted() {
    document.addEventListener('click', this.searchGlobalClick);
    if (localStorage.getItem("token")) {
      this.$store.dispatch('login', localStorage.getItem("token"));
      this.$store.dispatch('username', localStorage.getItem("username"));
    }
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
    closeLoginBox() {
      this.$emit('closeLoginBox');
    },
    showLogin() {
      this.$emit('showLogin');
    },
    logout() {
      this.$emit('logout');
    }
  }
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
    <div v-if="!this.$store.state.token"
      style="display: flex; align-items: center; justify-content: flex-end; width: 200px;">
      <el-button class="login_button" type="primary" @click="showLogin"><el-icon>
          <User />
        </el-icon>{{ login_button }}</el-button>
    </div>
    <div v-else style="color: white; display: flex; align-items: center; justify-content: space-between; width: 200px;">
      <div style="display: flex; flex-direction: column; align-items: center;">
        <el-avatar :size="70"
          :src="img ? img : 'https://img-qn.51miz.com/Element/00/88/60/42/ea5b40df_E886042_1992a532.png!/quality/90/unsharp/true/compress/true/format/png/fw/300'" />
        <div>{{ this.$store.state.userName ? this.$store.state.userName : username }}</div>
      </div>
      <el-button class="logout_button" type="primary" @click="logout"><el-icon>
          <User />
        </el-icon> Sign out </el-button>
    </div>
  </div>
  <div v-if="isLoginVisible" class="loginSection">
    <Login @showLogin="showLogin" @cancel="closeLoginBox" />
  </div>
</template>

<style >
.loginSection {
  position: absolute;
  z-index: 100;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.logo_container {
  height: 200px;
  width: 200px;
  margin-top: -20px;
  margin-left: -20px;
}

.logo {
  height: 100%;
  width: 100%;
  object-fit: contain;
}

.logo:hover {
  cursor: pointer;
}

.search_container {
  display: block;
}

.searchButton {
  width: 20vh;
}

.login_button {
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
  padding: 20px;
  padding-right: 30px;
  height: 80px;
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
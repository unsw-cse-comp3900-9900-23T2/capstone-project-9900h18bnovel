<script setup>
import {
  Search
} from '@element-plus/icons-vue'
</script >

<script>
export default {
  data() {
    return {
      header_container: 'header_container',
      header_left: 'header_left',
      header_right: 'header_right',
      login_button: 'Sign In',
      isSearchActive: false,
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
    searchGlobalClick(){
      if(this.isSearchActive == true){
        this.isSearchActive = false;
      }
    },
    goHome(){
      this.$router.push('/home');
    },
    goLogin(){
      this.$router.push('/login');
    }
  }
}
</script>

<template>
  <div :class="header_container">
    <div :class="header_left">
      <!-- Click here will return to Home page in any circumstances -->
      <img src="..\logo.png" class="logo" @click="goHome">
      <div class="search_container">
        <div v-if="!isSearchActive">
          <el-button class="searchButton" ref="searchContainer" @click.stop="toggleSearch" :icon="Search" round>Search</el-button>
        </div>
        <div v-else @click.stop>
          <el-input v-model="searchInput" placeholder="Please Enter Keyword">
            <template #prepend>
              <el-button @click.stop="handleSearch" :icon="Search" />
            </template>
          </el-input>
        </div>
      </div>
    </div>
    <!-- This right side of class will represent user thumbnail, name and log out button once token exists -->
    <div :class="header_right">
      <!-- Click here will link to login page -->
      <el-button type="primary" @click="goLogin">{{ login_button }}</el-button>
    </div>
  </div>
</template>

<style >
.logo{
  height: 60px;
  width: 120px
}
.searchButton {
  width: 200px;
}
.header_container {
  display: flex;
  background-color: antiquewhite;
  /* border: 1px orangered solid; */
  padding: 20px;
  /* position: ?; */
}

.header_left {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* border: 1px red solid; */
  width: 80%;
}

.header_right {
  display: flex;
  justify-content: right;
  align-items: center;
  /* border: 1px orange solid; */
  width: 20%;
}

</style>

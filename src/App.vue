<script setup>
import {
  CaretTop,
} from '@element-plus/icons-vue';
const svg = `
<path class="path" d="
          M 10 40
          L 10 15
          L 30 40
          L 30 15
        " style="stroke-width: 5px; fill: rgba(0, 0, 0, 0); animation: none;"/>
      `
</script>

<script>
import Global_Header from './components/Global_Header.vue';
import Global_Nav from './components/Global_Nav.vue';
import Login from './components/Auth_Page.vue';

export default {
  data() {
    return {
      searchInput: null,
      verImage: null,
      sessionId: null,
      loading: true,
      isLoadFinished: false,
    }
  },
  components: {
    Global_Header,
    Global_Nav,
    Login,
  },
  mounted() {
    if (localStorage.getItem("token")) {
      this.$store.dispatch('login', localStorage.getItem("token"));
      this.$store.dispatch('username', localStorage.getItem("username"));
      this.$store.dispatch('uid', localStorage.getItem("uid"));
    }
    this.searchInput = this.$store.getters.getSearchInput ? this.$store.getters.getSearchInput : null;
    setTimeout(() => {
      this.loading = false;
    }, 1000);
    this.isLoadFinished = true;
  },
  methods: {
    clearSearch() {
      this.searchInput = null;
      this.$store.dispatch('clearSearchInput');
    },
    handleSearch(searchInput) {
      this.$store.dispatch('setSearchInput', searchInput);
      if (!this.$router.currentRoute.value.path.includes('/allnovels')) {
        this.$router.push('/allnovels');
      }
    },

    async showLogin() {
      this.$store.dispatch("isLoginVisible", true);
      try {
        const response = await fetch("http://localhost:8888/api/front/user/img_verify_code", {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          },
        });
        if (response.status == 200) {
          const data = await response.json();
          this.verImage = "data:image/png;base64," + data.data.img;
          this.sessionId = data.data.sessionId;
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.error(error);
      }
    },
    closeLoginBox() {
      this.$store.dispatch("isLoginVisible", false);
    },
  },
}
</script>

<template>
  <div v-loading.fullscreen.lock="loading" element-loading-text="Welcome to NovelHub, novels will ready for you ASAP"
    :element-loading-spinner="svg" element-loading-svg-view-box="0, 5, 30, 40"
    element-loading-background="rgba(255, 255, 255, 255)"></div>
  <div v-if="isLoadFinished" :class="{ 'blur': this.$store.state.isLoginVisible }">
    <Global_Header @clearSearch="clearSearch" @handleSearch="handleSearch" @showLogin="showLogin"
      @closeLoginBox="closeLoginBox" :searchInput="this.searchInput" :verImage="this.verImage"
      :sessionId="this.sessionId" />
    <Global_Nav />
    <router-view>
    </router-view>
    <!-- Go to top floating buttom -->
    <el-backtop :bottom="100">
      <div class="goTopButton">
        <el-icon>
          <CaretTop />
        </el-icon>
      </div>
    </el-backtop>
  </div>
  <div v-if="this.$store.state.isLoginVisible" class="loginSection">
    <Login class="login" :verImage="this.verImage" :sessionId="this.sessionId" @closeLoginBox="closeLoginBox" />
  </div>
</template>

<style>
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.blur {
  filter: blur(5px);
  pointer-events: none;
}

.loginSection {
  position: absolute;
  z-index: 100;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.goTopButton {
  height: 100%;
  width: 100%;
  background-color: var(--el-bg-color-overlay);
  box-shadow: var(--el-box-shadow-lighter);
  align-items: center;
  display: flex;
  justify-content: center;
  line-height: 40px;
  color: #1989fa;
}
</style>
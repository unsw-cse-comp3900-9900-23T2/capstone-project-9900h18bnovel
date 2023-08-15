<script setup>
import {
  CaretTop,
} from '@element-plus/icons-vue';
</script>

<script>
import Global_Header from '@/components/Global_Header.vue';
import Global_Footer from '@/components/Global_Footer.vue';
import Login from '@/components/Auth_Page.vue';
import Author_page from '@/page_author/Author_page.vue';
export default {
  data() {
    return {
      searchInput: null,
      loading: true,
      isLoadFinished: false,
    }
  },
  components: {
    Global_Header,
    Global_Footer,
    Login,
    Author_page,
  },
  mounted() {
    if (localStorage.getItem("token")) {
      this.$store.dispatch('login', localStorage.getItem("token"));
      this.$store.dispatch('username', localStorage.getItem("username"));
      this.$store.dispatch('uid', localStorage.getItem("uid"));
      this.$store.dispatch('photo', localStorage.getItem("userPhoto"));
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
      if (!this.$router.currentRoute.value.path.includes('/browse')) {
        this.$router.push('/browse');
      }
    },

    showLogin() {
      this.$store.dispatch("isLoginVisible", true);
    },

    closeLoginBox() {
      this.$store.dispatch("isLoginVisible", false);
    },
  },

}
</script>

<template>
  <div v-if="$route.path !== '/author'">
    <div v-loading.fullscreen.lock="loading" element-loading-background="rgba(255, 255, 255, 255)"
      element-loading-spinner=" "></div>
    <div v-if="isLoadFinished" :class="{ 'blur': $store.state.isLoginVisible }">
      <Global_Header @clearSearch="clearSearch" @handleSearch="handleSearch" @showLogin="showLogin" />
      <router-view>
      </router-view>
      <Global_Footer />
      <!-- Go to top floating buttom -->
      <el-backtop :bottom="100">
        <div class="goTopButton">
          <el-icon>
            <CaretTop />
          </el-icon>
        </div>
      </el-backtop>
    </div>
    <transition name="fade">
      <div v-if="$store.state.isLoginVisible" class="loginSection">
        <Login class="login" @closeLoginBox="closeLoginBox" />
      </div>
    </transition>
  </div>
  <div v-else>
    <Author_page />
  </div>
</template>

<style>
.el-loading-spinner {
  background-image: url(./assets/spinner.gif);
  background-repeat: no-repeat;
  background-size: 70px 70px;
  height: 70px;
  width: 100%;
  background-position: center;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 500ms;
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
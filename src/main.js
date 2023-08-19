import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from '@/App.vue';
import HomePage from '@/pages/homePage/HomePage.vue';
import browse from '@/pages/browse/All_Novels.vue';
import Profile from '@/pages/user/User_Profile.vue';
import ranksPages from '@/pages/ranks/RanksPages.vue';
import bookInfo from '@/pages/book/bookInfo.vue';
import notFoundPage from '@/pages/404/NotFoundPage.vue';
import author_page from '@/page_author/Author_page.vue';
import store from '@/store';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';

// import axios from 'axios';
// axios.defaults.baseURL = "http://192.168.1.18:8888"

const app = createApp(App);
app.use(ElementPlus);

const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: HomePage },
  {
    path: '/browse',
    component: browse,
  },
  {
    path: '/browse/:currentURL',
    component: browse,
    beforeEnter: (to, from, next) => {
      to.params.currentURL = store.getters.getCurrentURL;
      next();
    },
  },
  { path: '/userprofile', component: Profile, },
  { path: '/newestrank', component: ranksPages },
  { path: '/clickrank', component: ranksPages },
  { path: '/updaterank', component: ranksPages },
  { path: '/bookInfo/:bookId', component: bookInfo },
  { path: '/:pathMatch(.*)', component: notFoundPage },
  { path: '/author', component: author_page },
];

export const router = createRouter({
  history: createWebHistory(),
  routes
});

app.use(store);
app.use(router);
app.mount('#app');

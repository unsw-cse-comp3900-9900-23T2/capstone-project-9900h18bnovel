import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import HomePage from './components/HomePage.vue';
import All_Novels from './components/All_Novels.vue';
import Profile from './components/User_Profile.vue';
import ranksPages from './components/RanksPages.vue';
import bookInfo from './components/bookInfo.vue';
import notFoundPage from './components/NotFoundPage.vue';
import author_page from './components/Author_page.vue';
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';

// import AuthorApp from "./AuthorApp.vue"
const app = createApp(App);
app.use(ElementPlus);

function isUserLoggedIn() {
  // 判断用户是否已登录的逻辑
  // 返回 true 表示已登录，返回 false 表示未登录
  // 这里仅为示例，你需要根据实际情况进行判断
  // console.log(localStorage.getItem('token'));
  return localStorage.getItem('token') !== null;
}

// Rounter address here
const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: HomePage },
  {
    path: '/allnovels',
    component: All_Novels,
  },
  {
    path: '/allnovels/:currentURL',
    component: All_Novels,
    beforeEnter: (to, from, next) => {
      to.params.currentURL = store.getters.getCurrentURL;
      next();
    },
  },
  {
    path: '/userprofile',
    component: Profile,
    name: 'UserProfile',
    meta: {
      requiresAuth: true,
    },
  },
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
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !isUserLoggedIn()) {
    // console.log(to.meta.requiresAuth);
    // console.log(isUserLoggedIn());
    // console.log('用户未登录，重定向到登录页面或其他处理逻辑');
    // console.log('from.path:', from.path);
    next(from.path ? from.path : '/home');
  } else {
    // console.log(to.meta.requiresAuth);
    // console.log(isUserLoggedIn());
    // console.log('用户已登录或无需验证，允许访问');
    // console.log('to.path:', to.path);
    // console.log('from.path:', from.path);
    next();
  }
});

app.use(store);
app.use(router);
app.mount('#app');

import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import HomePage from './components/HomePage.vue';
import All_Novels from './components/All_Novels.vue';
import ranksPages from './components/RanksPages.vue';
import bookInfo from './components/bookInfo.vue';
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';

const app = createApp(App);
app.use(ElementPlus);

// Rounter address here
const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: HomePage },
  {
    path: '/allnovels/',
    component: All_Novels,
  },
  {
    path: '/allnovels/:currentURL',
    component: All_Novels,
    beforeEnter: (to, from, next) => {
      // Modify the route path to include currentURL
      to.params.currentURL = store.getters.getCurrentURL;
      next();
    },
  },
  { path: '/newestrank', component: ranksPages },
  { path: '/clickrank', component: ranksPages },
  { path: '/updaterank', component: ranksPages },
  { path: '/bookInfo/:bookId', component: bookInfo}
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

app.use(store);
app.use(router);
app.mount('#app');

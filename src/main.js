import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import HomePage from './components/HomePage.vue';
import All_Novels from './components/All_Novels.vue';
import new_update from './components/new_update.vue';
import Profile from './components/User_Profile.vue'
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';

const app = createApp(App);
app.use(ElementPlus);

// Rounter address here
const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: HomePage },
  { path: '/allnovels', component: All_Novels },
  { path: '/newupdate', component: new_update },
  { path: '/Profile', component: Profile},
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

app.use(store);
app.use(router);
app.mount('#app');

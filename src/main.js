import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import HomePage from './components/HomePage.vue';
import Login from './components/LoginPage.vue';
import All_Novels from './components/All_Novels.vue';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';

// Rounter address here
const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: HomePage },
  { path: '/login', component: Login },
  { path: '/allnovels', component: All_Novels },
]

const router = createRouter({
  history: createWebHistory(),
  routes
});

const app = createApp(App);
app.use(ElementPlus);
app.use(router);
app.mount('#app');
// router.push('/home');
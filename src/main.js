import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import HomePage from './components/HomePage.vue';
import Login from './components/LoginPage.vue';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';

// Rounter address here
const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: HomePage },
  { path: '/login', component: Login }
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

import { ElMessage } from 'element-plus';
import store from '@/store';
import { router } from '@/main';

export function getItemColor(categoryName) {
    switch (categoryName) {
        case 'action':
            return 'border-color: #b3281a; color: #b3281a; background-color: #ffffff00';
        case 'romance':
            return 'border-color: #e33939; color: #e33939; background-color: #ffffff00';
        case 'fantasy':
            return 'border-color: #24bf37; color: #24bf37; background-color: #ffffff00';
        case 'mystery':
            return 'border-color: #4e6b1f; color: #4e6b1f; background-color: #ffffff00';
        case 'horror':
            return 'border-color: #6200a3; color: #6200a3; background-color: #ffffff00';
        case 'thriller':
            return 'border-color: #003da3; color: #003da3; background-color: #ffffff00';
        case 'drama':
            return 'border-color: #ed7651; color: #ed7651; background-color: #ffffff00';
        case 'adventure':
            return 'border-color: #ff8f00; color: #ff8f00; background-color: #ffffff00';
        case 'humor':
            return 'border-color: #ffc107; color: #ffc107; background-color: #ffffff00';
        case 'poetry':
            return 'border-color: #8d6e63; color: #8d6e63; background-color: #ffffff00';
        case 'other':
            return 'border-color: #607d8b; color: #607d8b; background-color: #ffffff00';
        default:
            return null;
    }
}

export function darkenColor(color) {
    switch (color) {
        case '#FFB5AE':
            return '#e33939';
        case '#FFD9E0':
            return '#fb4c6c';
        case '#BEE6C3':
            return '#24bf37';
        case '#A2A79A':
            return '#4e6b1f';
        case '#9998B5':
            return '#4843c3';
        case '#7C8496':
            return '#22479b';
        case '#FFD8CC':
            return '#ed7651';
        default:
            return null
    }
}

export function logout(if_in_userprofile) {
    ElMessage({
        message: "Log out successful",
        type: 'success',
    });
    localStorage.removeItem('userSex');
    localStorage.removeItem('uid');
    localStorage.removeItem('email');
    localStorage.removeItem('username');
    localStorage.removeItem('userPhoto');
    localStorage.removeItem('token');
    store.dispatch('clearSex');
    store.dispatch('clearuid');
    store.dispatch('clearEmail');
    store.dispatch('clearUserName');
    store.dispatch('clearPhoto');
    store.dispatch('logout');
    if (if_in_userprofile) {
        //router.push('/home');
        router.go(-1);
    }
}

// export const svg = `
// <path class="path" d="
//           M 10 40
//           L 10 15
//           L 30 40
//           L 30 15
//         " style="stroke-width: 5px; fill: rgb(0,0,0,0); animation: none;"/>
//       `

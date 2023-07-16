import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      token: null,
      userName: null,
      uid: null,
      currentURL: null,
      email: null,
      photo: null,
      sex: null,
      searchInput: null,
    };
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
    },
    clearToken(state) {
      state.token = null;
    },
    Sex(state, sex) {
      state.sex = sex;
    },
    clearSex(state) {
      state.sex = null;
    },
    setPhoto(state, photo) {
      state.photo = photo;
    },
    clearPhoto(state) {
      state.photo = null;
    },
    setEmail(state, email){
      state.email = email;
    },
    clearEmail(state){
      state.email = null;
    },
    setUserName(state, userName) {
      state.userName = userName;
    },
    clearUserName(state) {
      state.userName = null;
    },
    setUid(state, uid) {
      state.uid = uid;
    },
    clearUid(state) {
      state.uid = null;
    },
    setCurrentURL(state, url) {
      state.currentURL = url;
    },
    clearCurrentURL(state) {
      state.currentURL = null;
    },
    setSearchInput(state, input) {
      state.searchInput = input;
    },
    clearSearchInput(state) {
      state.searchInput = null;
    },
  },
  actions: {
    login({ commit }, token) {
      commit('setToken', token);
    },
    logout({ commit }) {
      commit('clearToken');
    },
    sex({ commit }, sex) {
      commit('setSex', sex);
    },
    clearSex({ commit }) {
      commit('clearSex');
    },
    email({ commit }, email) {
      commit('setEmail', email);
    },
    clearEmail({ commit }) {
      commit('clearEmail');
    },
    photo({ commit }, photo) {
      commit('setEmail', photo);
    },
    clearPhoto({ commit }) {
      commit('clearPhoto');
    },
    username({ commit }, userName) {
      commit('setUserName', userName);
    },
    clearusername({ commit }) {
      commit('clearUserName');
    },
    uid({ commit }, uid) {
      commit('setUid', uid);
    },
    clearuid({ commit }) {
      commit('clearUid');
    },
    updateCurrentURL({ commit }, url) {
      commit('setCurrentURL', url);
    },
    clearCurrentURL({ commit }) {
      commit('clearCurrentURL');
    },
    setSearchInput({ commit }, input) {
      commit('setSearchInput', input);
    },
    clearSearchInput({ commit }) {
      commit('clearSearchInput');
    }
  },
  getters: {
    isAuthenticated: (state) => {
      return !!state.token;
    },
    getCurrentURL: (state) => {
      return state.currentURL;
    },
    GetSex: (state) =>{
      return state.sex;
    },
    GetEmail: (state) =>{
      return state.email;
    },
    GetPhoto: (state) =>{
      return state.photo;
    },
    GetUsername: (state) =>{
      return state.userName;
    },
    GetUID: (state) =>{
      return state.uid;
    },
    getSearchInput: (state) => {
      return state.searchInput;
    },
  },
});

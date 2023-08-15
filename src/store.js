import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      isLoginVisible: false,
      token: null,
      userName: null,
      uid: null,
      currentURL: null,
      email: null,
      photo: null,
      sex: null,
      searchInput: null,
      myCollection: null,
    };
  },
  mutations: {
    isLoginVisible(state, bool) {
      state.isLoginVisible = bool;
    },
    setToken(state, token) {
      state.token = token;
    },
    clearToken(state) {
      state.token = null;
    },
    myCollection(state,myCollection){
      state.myCollection = myCollection;
    },
    clearMyCollection(state){
      state.myCollection = null;
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
    setEmail(state, email) {
      state.email = email;
    },
    clearEmail(state) {
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
    isLoginVisible({ commit }, bool) {
      commit('isLoginVisible', bool);
    },
    myCollection({commit},myCollection){
      commit('myCollection', myCollection);
    },
    clearMyCollection({commit}){
      commit('clearMyCollection');
    },
    login({ commit }, token) {
      commit('setToken', token);
    },
    logout({ commit }) {
      commit('clearToken');
    },
    sex({ commit }, sex) {
      commit('Sex', sex);
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
      commit('setPhoto', photo);
    },
    clearPhoto({ commit }) {
      commit('clearPhoto');
    },
    username({ commit }, userName) {
      commit('setUserName', userName);
    },
    clearUserName({ commit }) {
      commit('clearUserName');
    },
    uid({ commit }, uid) {
      commit('setUid', uid);
    },
    clearuid({ commit }) {
      commit('clearUid');
    },
    setCurrentURL({ commit }, url) {
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
    GetMyCollection:(state) =>{
      return state.myCollection;
},
    GetSex: (state) => {
      return state.sex;
    },
    GetEmail: (state) => {
      return state.email;
    },
    GetPhoto: (state) => {
      return state.photo;
    },
    GetUsername: (state) => {
      return state.userName;
    },
    GetUID: (state) => {
      return state.uid;
    },
    getSearchInput: (state) => {
      return state.searchInput;
    },
  },
});

import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      token: null,
      userName: null,
      uid: null,
      currentURL: null,
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
    getSearchInput: (state) => {
      return state.searchInput;
    },
  },
});

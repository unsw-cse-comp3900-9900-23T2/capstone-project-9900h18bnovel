import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      token: null,
      userName: null,
      uid: null,
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
  },
  getters: {
    isAuthenticated: (state) => {
      return !!state.token;
    },
  },
});

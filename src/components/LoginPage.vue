<script setup>
import {
  User
} from '@element-plus/icons-vue'

</script >
<script>
export default {

  data() {
    return {
      login_form: "login_form",
      show_login_form: true,
      username: '',
      password: '',
    }
  },
  mounted() {
    console.log(this.username)
  },
  methods: {
    loginin() {

    },
    closeLoginBox() {
      this.$emit('cancel');
    }
  }
}
</script>

<template>
  <div class="login_form">
    <el-icon size="20pt" style="border: 1px solid; border-radius: 50px; padding: 10px;">
      <User />
    </el-icon>
    <el-divider />
    <div class="each_input_container">
      <div style="width: 120px; display: flex; justify-content: center; align-items: center;">Username: </div>
      <el-input style="width: 30%;" v-model="username" />
    </div>

    <div class="each_input_container">
      <div style="width: 120px; display: flex; justify-content: center; align-items: center;">Password: </div>
      <el-input style="width: 30%;" v-model="password" show-password type="password" />
    </div>

    <div class="other_options">
      <el-link>Not a user? Register here!</el-link>
      <div style="margin-top: 20px;">
        <el-button type="primary" @click="loginin">Log in</el-button>
        <el-button @click="closeLoginBox">Cancel</el-button>
      </div>
    </div>
  </div>
</template>


<style >


.login_form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: antiquewhite;
  width: 600px;
  height: 300px;
  border-radius: 50px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.4);
  padding: 20px;
}

.each_input_container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
  font-size: 16pt;
}

.other_options {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>


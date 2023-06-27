<script setup>
import {
  User,

} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
</script >

<script>
export default {
  props: {
    verImage: {
      type: String,
      default: ''
    },
    sessionId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      login_form: "login_form",
      show_login_form: true,
      isLoginVisible: true,
      isRegisterVisible: false,
      isForgetVisible: false,
      email: '',
      username: '',
      password: '',
      confirmPass: '',
      verCode: '',
      uid: '',
      token: '',
      countdown1: 0,
      countdown2: 0,
      verCodeIsNumbers: false,
    }
  },
  methods: {
    validateIsNumbers() {
      const regex = /^[0-9]+$/;
      if (!regex.test(this.verCode)) {
        ElMessage({
          message: 'Verification code must be numbers',
          type: 'error',
        })
        this.verCodeIsNumbers = false;
      } else {
        this.verCodeIsNumbers = true;
      }
    },
    closeLoginBox() {
      this.$emit('cancel');
    },

    toRegister() {
      this.isRegisterVisible = true;
      this.isForgetVisible = false;
      this.isLoginVisible = false;
      this.password = '';
      this.confirmPass = '';
      this.verCode = '';
      this.username = '';
    },

    toLogin() {
      this.isRegisterVisible = false;
      this.isForgetVisible = false;
      this.isLoginVisible = true;
      this.password = '';
      this.confirmPass = '';
      this.verCode = '';
    },

    toForget() {
      this.isForgetVisible = true;
      this.isLoginVisible = false;
      this.isRegisterVisible = false;
      this.password = '';
      this.confirmPass = '';
      this.verCode = '';
    },

    isValidEmail(email) {
      const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return emailPattern.test(email);
    },

    startCountdown(buttonIndex) {
      if (this.email === '') {
        this.alertBox("emailEmpty")
      } else if (!this.isValidEmail(this.email)) {
        this.alertBox("emailInvalid");
      } else {
        ElMessage({
          message: 'Verification code sent',
          type: 'success',
        })
        let countdown;
        if (buttonIndex === 1) {
          countdown = this.countdown1;
        } else if (buttonIndex === 2) {
          countdown = this.countdown2;
        }
        if (countdown > 0) {
          return;
        }
        countdown = 60;
        const timer = setInterval(() => {
          countdown--;
          if (countdown === 0) {
            clearInterval(timer);
          }
          if (buttonIndex === 1) {
            this.countdown1 = countdown;
          } else if (buttonIndex === 2) {
            this.countdown2 = countdown;
          }
        }, 1000);
        this.getVerCode();
      }
    },

    async getVerCode() {
      const requestData = {
        email: this.email
      };
      try {
        const response = await fetch("http://localhost:8888/api/front/user/email_verify_code", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(requestData)
        });
        if (response.status == 200) {
          await response.json();
        } else {
          ElMessage.error("Get verification code error");
        }
      } catch (error) {
        ElMessage.error(error);
      }
    },

    alertBox(situation) {
      if (situation === "emailEmpty") {
        ElMessageBox.alert("Email can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "emailInvalid") {
        ElMessageBox.alert("Invalid email format", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "usernameEmpty") {
        ElMessageBox.alert("Username can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "usernameContainSpace") {
        ElMessageBox.alert("Username can' contain any space", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "passwordEmpty") {
        ElMessageBox.alert("Password can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "passwordContainSpace") {
        ElMessageBox.alert("Password can' contain any space", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "conPassEmpty") {
        ElMessageBox.alert("Confirm Password can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      }else if (situation === "conPassContainSpace") {
        ElMessageBox.alert("Confirm Password can' contain any space", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "verCodeEmpty") {
        ElMessageBox.alert("Verification code can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "passDiff") {
        ElMessageBox.alert('Two Password is not same', 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "verCodeIsNotNumbers") {
        ElMessageBox.alert('Verification code must be numbers', 'Error', {
          confirmButtonText: 'OK',
        })
      }
    },

    async signin() {
      if (this.email === '') {
        this.alertBox("emailEmpty")
      } else if (!this.isValidEmail(this.email)) {
        this.alertBox("emailInvalid");
      } else if (this.password === '') {
        this.alertBox("passwordEmpty")
      } else if(/\s/.test(this.password)){
        /*因为发现如果输入六个空格也会向后端发请求，所以我这里就加了个这个 */
        this.alertBox("passwordContainSpace")
      } else if (this.verCode === '') {
        this.alertBox("verCodeEmpty")
      } else if (this.verCodeIsNumbers === false) {
        this.alertBox("verCodeIsNotNumbers")
      } else {
        const requestData = {
          email: this.email,
          password: this.password,
          velCode: this.verCode,
          sessionId: this.sessionId
        };
        try {
          const response = await fetch("http://localhost:8888/api/front/user/login", {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
          });
          if (response.status == 200) {
            const data = await response.json();
            console.log(data);
          } else {
            console.log("Test");
          }
        } catch (error) {
          ElMessage.error(error);
        }
      }
    },
    async signUp() {
      if (this.email === '') {
        this.alertBox("emailEmpty")
      } else if (!this.isValidEmail(this.email)) {
        this.alertBox("emailInvalid");
      } else if (this.username === '') {
        this.alertBox("usernameEmpty")
      } else if (/\s/.test(this.username)) {
        this.alertBox("usernameContainSpace")
      } else if (this.password === '') {
        this.alertBox("passwordEmpty")
      } else if(/\s/.test(this.password)){
        /*因为发现如果输入六个空格也会向后端发请求，所以我这里就加了个这个 */
        this.alertBox("passwordContainSpace")
      } else if (this.confirmPass === '') {
        this.alertBox("conPassEmpty")
      } else if (/\s/.test(this.confirmPass)) {
        this.alertBox("conPassContainSpace")
      } else if (this.verCode === '') {
        this.alertBox("verCodeEmpty")
      } else if (this.confirmPass !== this.password) {
        this.alertBox("passDiff")
      } else if (this.verCodeIsNumbers === false) {
        this.alertBox("verCodeIsNotNumbers")
      } else {
        const requestData = {
          email: this.email,
          username: this.username,
          password: this.password,
          velCode: this.verCode
        };
        try {
          const response = await fetch("http://localhost:8888/api/front/user/register", {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
          });
          if (response.status == 200) {
            const data = await response.json();
            console.log(data);
          } else {
            console.log("Test");
          }
        } catch (error) {
          ElMessage.error(error);
        }
      }
    },
    async forgetPass() {
      if (this.email === '') {
        this.alertBox("emailEmpty")
      } else if (!this.isValidEmail(this.email)) {
        this.alertBox("emailInvalid");
      } else if (this.password === '') {
        this.alertBox("passwordEmpty")
      } else if(/\s/.test(this.password)){
        /*因为发现如果输入六个空格也会向后端发请求，所以我这里就加了个这个 */
        this.alertBox("passwordContainSpace")
      } else if (this.confirmPass === '') {
        this.alertBox("conPassEmpty")
      } else if (/\s/.test(this.confirmPass)) {
        this.alertBox("conPassContainSpace")
      } else if (this.verCode === '') {
        this.alertBox("verCodeEmpty")
      } else if (this.confirmPass !== this.password) {
        this.alertBox("passDiff")
      } else if (this.verCodeIsNumbers === false) {
        this.alertBox("verCodeIsNotNumbers")
      } else {
        const requestData = {
          email: this.email,
          password: this.password,
          velCode: this.verCode
        };
        try {
          const response = await fetch("http://localhost:8888/api/front/user/reset_password", {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
          });
          if (response.status == 200) {
            const data = await response.json();
            console.log(data);
          } else {
            console.log("Test");
          }
        } catch (error) {
          ElMessage.error(error);
        }
      }
    }
  },
  watch: {
    verCode: {
      handler() {
        if (this.verCode !== '') {
          this.validateIsNumbers();
        }
      },
      immediate: true
    }
  }
};
</script>

<template>
  <div v-if="isLoginVisible" class="login_form">
    <el-icon size="20pt" style="border: 1px solid; border-radius: 50px; padding: 10px;">
      <User />
    </el-icon>
    <div style="font-size: 14pt; padding-top: 10px;">Sign in</div>
    <el-divider />
    <div class="each_input_container">
      <div class="text">Email: </div>
      <el-input placeholder="example@example.com" style="width: 30%;" v-model="email" />
    </div>

    <div class="each_input_container">
      <div class="text">Password: </div>
      <el-input style="width: 30%;" v-model="password" show-password type="password" />
    </div>

    <div class="each_input_container">
      <div class="text">Verification Code: </div>
      <el-input style="width: 30%;" v-model="verCode" />
      <img style="height: 100%; width: 80px;" :src="verImage" />
    </div>

    <div class="other_options">
      <div style="width: 350px; display: flex; justify-content: space-between; margin-top: -15px;">
        <el-link @click="toRegister">Not a user yet? Sign Up here!</el-link>
        <el-link @click="toForget">Forget Password</el-link>
      </div>
      <div style="margin-top: 20px;">
        <el-button type="primary" @click="signin">Log in</el-button>
        <el-button @click="closeLoginBox">Cancel</el-button>
      </div>
    </div>
  </div>

  <div v-else-if="isRegisterVisible" class="register_form">
    <el-icon size="20pt" style="border: 1px solid; border-radius: 50px; padding: 10px;">
      <User />
    </el-icon>
    <div style="font-size: 14pt; padding-top: 10px;">Sign Up</div>
    <el-divider />

    <div class="each_input_container">
      <div class="text">Email: </div>
      <el-input placeholder="example@example.com" style="width: 30%;" v-model="email" />
    </div>

    <div class="each_input_container">
      <div class="text">Username: </div>
      <el-input style="width: 30%;" v-model="username" />
    </div>

    <div class="each_input_container">
      <div class="text">Password: </div>
      <el-input style="width: 30%;" v-model="password" show-password type="password" />
    </div>

    <div class="each_input_container">
      <div class="text">Confirm Password: </div>
      <el-input style="width: 30%;" v-model="confirmPass" show-password type="password" />
    </div>

    <div class="each_input_container">
      <div class="text">Verification Code: </div>
      <el-input style="width: 30%;" v-model="verCode" />
      <el-button :disabled="countdown1 > 0" @click="startCountdown(1)">
        {{ countdown1 > 0 ? countdown1 + 's' : 'Get Code' }}
      </el-button>
    </div>

    <div class="other_options">
      <div style="width: 350px; display: flex; justify-content: space-between; margin-top: -15px;">
        <el-link @click="toLogin">Already a user? Sign in here!</el-link>
        <el-link @click="toForget">Forget Password</el-link>
      </div>
      <div style="margin-top: 20px;">
        <el-button type="primary" @click="signUp">Sign up</el-button>
        <el-button @click="closeLoginBox">Cancel</el-button>
      </div>
    </div>
  </div>

  <div v-else-if="isForgetVisible" class="forget_form">
    <el-icon size="20pt" style="border: 1px solid; border-radius: 50px; padding: 10px;">
      <User />
    </el-icon>
    <div style="font-size: 14pt; padding-top: 10px;">Forget Password</div>
    <el-divider />

    <div class="each_input_container">
      <div class="text">Email: </div>
      <el-input placeholder="example@example.com" style="width: 30%;" v-model="email" />
    </div>

    <div class="each_input_container">
      <div class="text">New Password: </div>
      <el-input style="width: 30%;" v-model="password" show-password type="password" />
    </div>

    <div class="each_input_container">
      <div class="text">Confirm New Password: </div>
      <el-input style="width: 30%;" v-model="confirmPass" show-password type="password" />
    </div>

    <div class="each_input_container">
      <div class="text">Verification Code: </div>
      <el-input style="width: 30%;" v-model="verCode" />
      <el-button :disabled="countdown2 > 0" @click="startCountdown(2)">
        {{ countdown2 > 0 ? countdown2 + 's' : 'Get Code' }}
      </el-button>
    </div>

    <div class="other_options">
      <div style="width: 350px; display: flex; justify-content: space-between; margin-top: -15px;">
        <el-link @click="toLogin">Go back to Sign in</el-link>
        <el-link @click="toRegister">Not a user yet? Sign Up here!</el-link>
      </div>
      <div style="margin-top: 20px;">
        <el-button type="primary" @click="forgetPass">Submit</el-button>
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
  background-color: rgb(245, 243, 243);
  width: 600px;
  border-radius: 50px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.4);
  padding: 20px;
}

.register_form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(245, 243, 243);
  width: 600px;
  border-radius: 50px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.4);
  padding: 20px;
}

.forget_form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(245, 243, 243);
  width: 600px;
  border-radius: 50px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.4);
  padding: 20px;
}

.each_input_container {
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
  font-size: 14pt;
}

.text {
  width: 210px;
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;

}

.other_options {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>


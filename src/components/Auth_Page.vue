<script setup>
import {
  Close,
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import axios from 'axios';
import 'animate.css';
</script >

<script>
export default {
  emits: ['closeLoginBox'],
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
      countdown1: 0,
      countdown2: 0,
      verCodeIsNumbers: false,
      password_length: 0,
      contains_eight_characters: false,
      contains_number: false,
      contains_uppercase: false,
      contains_special_character: false,
      valid_password: false,
      localVerImage: null,
      localSessionId: null,
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
    },

    isLoginVisible(newValue) {
      if (newValue === true) {
        this.getNewImgVer();
      }
    },
  },

  mounted() {
    this.getNewImgVer();
  },

  methods: {

    checkPassword() {
      this.password_length = this.password.length;
      const format = /[ !@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/;

      if (this.password_length > 8) {
        this.contains_eight_characters = true;
      } else {
        this.contains_eight_characters = false;
      }

      this.contains_number = /\d/.test(this.password);
      this.contains_uppercase = /[A-Z]/.test(this.password);
      this.contains_special_character = format.test(this.password);

      if (this.contains_eight_characters === true &&
        this.contains_special_character === true &&
        this.contains_uppercase === true &&
        this.contains_number === true) {
        this.valid_password = true;
      } else {
        this.valid_password = false;
      }
    },

    resetPassReq() {
      this.password = '';
      this.confirmPass = '';
      this.verCode = '';
      this.contains_eight_characters = false;
      this.contains_special_character = false;
      this.contains_uppercase = false;
      this.contains_number = false;
      this.valid_password = false;
    },

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
      this.$emit('closeLoginBox');
    },

    toRegister() {
      this.isRegisterVisible = true;
      this.isForgetVisible = false;
      this.isLoginVisible = false;
      this.username = '';
      this.resetPassReq();
    },

    toLogin() {
      this.isRegisterVisible = false;
      this.isForgetVisible = false;
      this.isLoginVisible = true;
      this.resetPassReq();
    },

    toForget() {
      this.isForgetVisible = true;
      this.isLoginVisible = false;
      this.isRegisterVisible = false;
      this.resetPassReq();
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
        ElMessage({
          message: 'Verification code sent',
          type: 'success',
        })
      }
    },

    async getVerCode() {
      const requestData = {
        email: this.email
      };
      await axios.post("/api/front/user/email_verify_code", requestData)
        .catch(error => {
          console.error(error);
        });
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
        /* 用户名不能有空格 */
        ElMessageBox.alert("Username can' contain any space", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "passwordEmpty") {
        ElMessageBox.alert("Password can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "passwordContainSpace") {
        /* 密码不能有空格 */
        ElMessageBox.alert("Password can' contain any space", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "conPassEmpty") {
        ElMessageBox.alert("Confirm Password can' be empty", 'Error', {
          confirmButtonText: 'OK',
        })
      } else if (situation === "conPassContainSpace") {
        /* 确认密码不能有空格 */
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
      } else if (situation === "passReqNa") {
        ElMessageBox.alert('Password requirements not met', 'Error', {
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
        this.alertBox("passwordEmpty");
      } else if (/\s/.test(this.password)) {
        this.alertBox("passwordContainSpace");
      } else if (this.verCode === '') {
        this.alertBox("verCodeEmpty");
      } else if (this.verCodeIsNumbers === false) {
        this.alertBox("verCodeIsNotNumbers");
      } else {

        const requestData = {
          email: this.email,
          password: this.password,
          velCode: this.verCode,
          sessionId: this.localSessionId
        };

        await axios.post("/api/front/user/login", requestData)
          .then(response => {
            const data = response.data;
            if (data.code === "00000") {
              ElMessage({
                message: 'Welcome ' + data.data.userName,
                type: 'success',
              });
              this.closeLoginBox();
              // console.log("登录之后返回的信息：\n");
              // console.log(data.data);
              localStorage.setItem('email', this.email);
              localStorage.setItem('token', data.data.token);
              localStorage.setItem('uid', data.data.uid);
              localStorage.setItem('username', data.data.userName);
              localStorage.setItem('userPhoto', data.data.userPhoto);
              this.$store.dispatch('email', data.data.email);
              this.$store.dispatch('login', data.data.token);
              this.$store.dispatch('uid', data.data.uid);
              this.$store.dispatch('username', data.data.userName);
              this.$store.dispatch('photo', data.data.userPhoto);
            } else if (data.code === "A0201") {
              ElMessage({
                message: "Email: " + this.email + " is not exists, please check again",
                type: 'error',
              });
              this.password = '';
              this.verCode = '';
              this.getNewImgVer();
            } else if (data.code === "A0210") {
              ElMessage({
                message: "Incorrect password",
                type: 'error',
              });
              this.password = '';
              this.verCode = '';
              this.getNewImgVer();
            } else if (data.code === "A0240" || data.code === "A0400") {
              ElMessage({
                message: "Incorrect verification code",
                type: 'error',
              });
              this.getNewImgVer();
            } else if (data.code === "B0001") {
              ElMessage.error('System execution error');
              this.password = '';
              this.verCode = '';
              this.getNewImgVer();
            }
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    async signUp() {
      if (this.email === '') {
        this.alertBox("emailEmpty")
      } else if (!this.isValidEmail(this.email)) {
        this.alertBox("emailInvalid");
      } else if (this.username === '') {
        /* 全空格用户名或带空格用户名不接受 */
        this.alertBox("usernameEmpty")
      } else if (/\s/.test(this.username)) {
        this.alertBox("usernameContainSpace")
      } else if (this.password === '') {
        this.alertBox("passwordEmpty")
      } else if (/\s/.test(this.password)) {
        /*因为发现如果输入六个空格也会向后端发请求，所以我这里就加了个这个 */
        this.alertBox("passwordContainSpace")
      } else if (this.confirmPass === '') {
        this.alertBox("conPassEmpty")
      } else if (/\s/.test(this.confirmPass)) {
        /* 理同上一个 */
        this.alertBox("conPassContainSpace")
      } else if (this.confirmPass !== this.password) {
        this.alertBox("passDiff")
      } else if (!this.valid_password) {
        this.alertBox("passReqNa");
      } else if (this.verCode === '') {
        this.alertBox("verCodeEmpty")
      } else if (this.verCodeIsNumbers === false) {
        this.alertBox("verCodeIsNotNumbers")
      } else {
        const requestData = {
          email: this.email,
          username: this.username,
          password: this.password,
          velCode: this.verCode
        };

        await axios.post("/api/front/user/register", requestData)
          .then(response => {
            const data = response.data;
            if (data.code === "00000") {
              ElMessage({
                message: 'Sign up successful',
                type: 'success',
              });
              this.toLogin();
            } else if (data.code === 'A0111') {
              ElMessage({
                message: "Email: " + this.email + " already exists",
                type: 'error',
              });
              this.resetPassReq();
            } else if (data.code === "A0240" || data.code === "A0400") {
              ElMessage.error('Incorrect Verification code');
            } else if (data.code === "B0001") {
              ElMessage.error('System execution error');
              this.resetPassReq();
            }
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    async forgetPass() {
      if (this.email === '') {
        this.alertBox("emailEmpty")
      } else if (!this.isValidEmail(this.email)) {
        this.alertBox("emailInvalid");
      } else if (this.password === '') {
        this.alertBox("passwordEmpty")
      } else if (/\s/.test(this.password)) {
        /*因为发现如果输入六个空格也会向后端发请求，所以我这里就加了个这个 */
        this.alertBox("passwordContainSpace")
      } else if (this.confirmPass === '') {
        this.alertBox("conPassEmpty")
      } else if (/\s/.test(this.confirmPass)) {
        this.alertBox("conPassContainSpace")
      } else if (this.confirmPass !== this.password) {
        this.alertBox("passDiff")
      } else if (!this.valid_password) {
        this.alertBox("passReqNa");
      } else if (this.verCode === '') {
        this.alertBox("verCodeEmpty")
      } else if (this.verCodeIsNumbers === false) {
        this.alertBox("verCodeIsNotNumbers")
      } else {
        const requestData = {
          email: this.email,
          velCode: this.verCode,
          newPassword: this.password,
        };
        await axios.post("/api/front/user/reset_password", requestData)
          .then(response => {
            const data = response.data;
            console.log(data);
            if (data.code === "00000") {
              ElMessage({
                message: 'Password has been reset',
                type: 'success',
              });
              this.resetPassReq();
              this.toLogin();
            } else if (data.code === "A0240" || data.code === "A0400") {
              ElMessage.error('Incorrect Verification code');
            } else if (data.code === "A0201") {
              ElMessage.error("Email: " + this.email + " does not exists");
              this.resetPassReq();
            } else if (data.code === "B0001") {
              ElMessage.error('System execution error');
              this.resetPassReq();
            }
          })
          .catch(error => {
            console.error(error);
          });
      }
    },

    async getNewImgVer() {
      await axios.get("/api/front/user/img_verify_code")
        .then(response => {
          const data = response.data;
          this.localVerImage = "data:image/png;base64," + data.data.img;
          this.localSessionId = data.data.sessionId;
        })
        .catch(error => {
          console.error(error);
        });
    },
  },
};
</script>

<template>
  <div v-show="isLoginVisible" class="animate__bounceIn animate__faster">
    <div class="auth_form">
      <el-button style="top:10px; right: 10px; position: absolute;" @click="closeLoginBox" :icon="Close" circle link
        size="large"></el-button>
      <img src="..\assets\logo1.png" class="logo">
      <div style="font-size: 22pt; font-weight: bold; padding-top: 10px;">Sign in</div>
      <br>
      <div class="each_input_container">
        <el-input placeholder="Email" v-model="email" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Password" v-model="password" show-password type="password" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Verification Code" v-model="verCode" size="large" style="width: 60%;" />
        <div style="color: red;">&nbsp;*&nbsp;</div>
        <img style="height: 100%; width: 80px;" :src="localVerImage" @click="getNewImgVer" class="signInVerImg" />
      </div>

      <div class="other_options">
        <el-button type="primary" @click="signin" style="width: 100%;" round>SIGN IN</el-button>
        <el-link style="margin-top: 10px; margin-bottom: 10px;" @click="toForget">Forgot your password?</el-link>
        <el-link @click="toRegister">Not a member?&nbsp; <b>Sign up now</b></el-link>
      </div>
    </div>
  </div>

  <div v-show="isRegisterVisible" class="animate__bounceIn animate__faster">
    <div class="auth_form">
      <el-button style="top:10px; right: 10px; position: absolute;" @click="closeLoginBox" :icon="Close" circle link
        size="large"></el-button>
      <img src="..\assets\logo1.png" class="logo">
      <div style="font-size: 22pt; font-weight: bold; padding-top: 10px;">Sign Up</div>
      <br>
      <div class="each_input_container">
        <el-input placeholder="Email" v-model="email" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Username" v-model="username" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="passPopover">
        <div class="input_container">
          <ul>
            <li v-bind:class="{ is_valid: contains_eight_characters }">8 Characters</li>
            <li v-bind:class="{ is_valid: contains_number }">Contains Number</li>
            <li v-bind:class="{ is_valid: contains_uppercase }">Contains Uppercase</li>
            <li v-bind:class="{ is_valid: contains_special_character }">Contains Special Character</li>
          </ul>
          <div class="checkmark_container" v-bind:class="{ show_checkmark: valid_password }">
            <svg width="50%" height="50%" viewBox="0 0 140 100">
              <path class="checkmark" v-bind:class="{ checked: valid_password }" d="M10,50 l25,40 l95,-70" />
            </svg>
          </div>
        </div>
      </div>

      <div class="each_input_container">
        <el-input type="password" @input="checkPassword" v-model="password" autocomplete="off" placeholder="Password"
          show-password size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Confirm Password" v-model="confirmPass" show-password type="password" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Verification Code" v-model="verCode" size="large" style="width: 60%;" />
        <div style="color: red;">&nbsp;*&nbsp;</div>
        <el-button :disabled="countdown1 > 0" @click="startCountdown(1)">
          {{ countdown1 > 0 ? countdown1 + 's' : 'Get Code' }}
        </el-button>
      </div>

      <div class="other_options">
        <el-button type="primary" @click="signUp" style="width: 100%;" round>SIGN UP</el-button>
        <el-link style="margin-top: 10px; margin-bottom: 10px;" @click="toLogin">Already is member?&nbsp; <b>Sign in
            now</b></el-link>
        <el-link @click="toForget">Forgot your password?</el-link>
      </div>
    </div>
  </div>

  <div v-show="isForgetVisible" class="animate__bounceIn animate__faster">
    <div class="auth_form">
      <el-button style="top:10px; right: 10px; position: absolute;" @click="closeLoginBox" :icon="Close" circle link
        size="large"></el-button>
      <img src="..\assets\logo1.png" class="logo">
      <div style="font-size: 22pt; font-weight: bold; padding-top: 10px;">Renew Password</div>
      <br>

      <div class="each_input_container">
        <el-input placeholder="Email" v-model="email" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="passPopover">
        <div class="input_container">
          <ul>
            <li v-bind:class="{ is_valid: contains_eight_characters }">8 Characters</li>
            <li v-bind:class="{ is_valid: contains_number }">Contains Number</li>
            <li v-bind:class="{ is_valid: contains_uppercase }">Contains Uppercase</li>
            <li v-bind:class="{ is_valid: contains_special_character }">Contains Special Character</li>
          </ul>
          <div class="checkmark_container" v-bind:class="{ show_checkmark: valid_password }">
            <svg width="50%" height="50%" viewBox="0 0 140 100">
              <path class="checkmark" v-bind:class="{ checked: valid_password }" d="M10,50 l25,40 l95,-70" />
            </svg>
          </div>
        </div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="New Password" v-model="password" show-password type="password" size="large"
          @input="checkPassword" autocomplete="off" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Confirm Password" v-model="confirmPass" show-password type="password" size="large" />
        <div style="color: red;">&nbsp;*</div>
      </div>

      <div class="each_input_container">
        <el-input placeholder="Verification Code" v-model="verCode" size="large" style="width: 60%;" />
        <div style="color: red;">&nbsp;*&nbsp;</div>
        <el-button :disabled="countdown2 > 0" @click="startCountdown(2)">
          {{ countdown2 > 0 ? countdown2 + 's' : 'Get Code' }}
        </el-button>
      </div>

      <div class="other_options">
        <el-button type="primary" @click="forgetPass" style="width: 100%;" round>SUBMIT</el-button>
        <el-link style="margin-top: 10px; margin-bottom: 10px;" @click="toLogin">Already is member?&nbsp; <b>Sign in
            now</b></el-link>
        <el-link @click="toRegister">Not a member?&nbsp; <b>Sign up now</b></el-link>
      </div>
    </div>

  </div>
</template>


<style >
.signInVerImg:hover {
  cursor: pointer;
}

.auth_form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 350px;
  /* box-shadow: 0 10px 10px rgba(0, 0, 0, 0.4); */
  box-shadow: 0 0 40px 0 #6bb4fc;
  padding: 30px 50px 30px 50px;
  background-color: white;
  background: url(../assets/AuthBG.jpg);
  background-size: cover;
  background-position: 40% 60%;
  margin: 0 auto;
  border-radius: 0.625rem;
  position: relative;
}


.each_input_container {
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
  font-size: 12pt;
}

.other_options {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.passPopover {
  margin-top: -20px;
  margin-bottom: -15px;
  margin-left: -70px;
}

.passPopover ul {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.passPopover li {
  margin-bottom: 8px;
  color: #525f7f;
  position: relative;
}

.passPopover li:before {
  content: "";
  width: 0%;
  height: 2px;
  background: #2ecc71;
  position: absolute;
  left: 0;
  top: 50%;
  display: block;
  transition: all .6s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}


/* Password Input --------- */

.passPopover .input_container {
  position: relative;
}

/* Checkmark & Strikethrough --------- */

.passPopover .is_valid {
  color: rgba(136, 152, 170, 0.8);
}

.passPopover .is_valid:before {
  width: 100%;
}

.passPopover .checkmark_container {
  border-radius: 50%;
  position: absolute;
  top: 0;
  right: 0;
  background: #2ecc71;
  width: 50px;
  height: 50px;
  visibility: hidden;
  opacity: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity .4s ease;
}

.passPopover .show_checkmark {
  visibility: visible;
  opacity: 1;
}

.passPopover .checkmark {
  width: 100%;
  height: 100%;
  fill: none;
  stroke: white;
  stroke-width: 15;
  stroke-linecap: round;
  stroke-dasharray: 180;
  stroke-dashoffset: 180;
}

.passPopover .checked {
  animation: draw 0.5s ease forwards;
}

@keyframes draw {
  to {
    stroke-dashoffset: 0;
  }
}
</style>


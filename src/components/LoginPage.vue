<template>
	<Header />
	<RegisterPage />
	<el-form :inline="true" :model="login_form" class = "login_form" v-show="show_login_form">
		<div class="logo_container"></div>
		<el-divider />
		<div class="username_container">
			<el-form-item label="Username: "></el-form-item>
			<el-input v-model="username" placeholder="Your username" />
		</div>

		<div class="password_container">
			<el-form-item label="Input your password: "></el-form-item>
			<el-input v-model="set_pwd_input" placeholder="Input your password" />
		</div>

		<div class="other_options">
			<el-button @click="turn_to_register" type="primary">Not a user? Register here!</el-button>
			<el-button type="primary">Login !</el-button>
			<el-button @click="hide_login_form">Cancel</el-button>
		</div>
	</el-form>
</template>

<script>
import RegisterPage from './RegisterPage.vue';
import Header from './Global_Header.vue';

export default {
	
  data(){
	return {
		login_form: "login_form",
		show_login_form: true,
	}
  },
  methods:{
	hide_login_form(){
		this.show_login_form = false;
	},
	turn_to_register(){
		this.show_login_form = false;
		RegisterPage.register_container.show_Register_Form = true;
	}
  },
  components: {
	Header
  }
}
</script>

<style >
.login_form {
	display: block;
    background-color: antiquewhite;
    width: 36em;
    height: auto;
    border-radius: 10px;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.4);;
	padding: 2em;
	margin: auto;
}

.logo_container{
	display: block;
	width: 100px;
	height: 100px;
	border-radius: 50%;
	background-color: #fff;
	margin: 1.5em auto;
}

.username_container, .password_container, .other_options{
	display: flex;
    justify-content: space-evenly;
    align-items: baseline;
    height: 2em;
    margin: 0.5em 0 0.5em 0;
}
</style>


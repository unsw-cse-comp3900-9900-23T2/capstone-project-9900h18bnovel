
<template>
	<el-form :inline="true" :model="register_container" class="register_container" v-show="show_Register_Form">
	<!-- <div :class="register_container"> -->
		<div class="logo_container"></div>
		<el-divider />
		<div class="email_contianer">
			<el-form-item label="Email: "></el-form-item>
			<el-input v-model="email" placeholder="Email address" style="margin-right: 10px;"/>
			<el-button type="primary">Verify</el-button>
		</div>

		<div class="captcha_container">
			<el-form-item label="Captcha: "></el-form-item>
			<el-input v-model="captcha" placeholder="Captcha you get" style="margin-right: 10px;"/>
			<el-image style="width: 4em; height: 80%" :src="url" :fit="fit" />
			<!-- <div class="show_captcha"></div> -->
		</div>

		<div class="username_container">
			<el-form-item label="Username: "></el-form-item>
			<el-input v-model="username" placeholder="Your username" />
		</div>

		<div class="set_password">
			<el-form-item label="Set your password: "></el-form-item>
			<el-input v-model="set_pwd_input" placeholder="Set your password" />
		</div>

		<div class="confirm_password">
			<el-form-item label="Confirm your password:"></el-form-item>
			<el-input v-model="confirm_pwd_input" placeholder="Confirm your password" />
		</div>

		<div class="other_options">
			<el-button type="primary">Already a user? Login here!</el-button>
			<el-button type="primary">Submit !</el-button>
			<el-button @click="hide_register_form">Cancel</el-button>
		</div>
	<!-- </div> -->
	</el-form>

</template>

<script >

export default {
	data(){
		return{
			register_container: 'register_container',
			logo_container: 'logo_container',
			email_contianer: 'email_contianer',
			email_hint: 'email',
			email_input_box: 'email_input',
			get_captcha: 'get_captcha',
			captcha_container: 'captcha_container',
			captcha_hint: 'captcha',
			captcha_input_box: 'captcha_input',
			captcha_display: 'show_captcha',
			username_container: 'username_container',
			username_input_box: 'username_input',
			set_password: 'set_password',
			set_pwd_input_box: 'set_pwd_input',
			confirm_password: 'confirm_password',
			confirm_pwd_hint :'confirm_pwd',
			confirm_pwd_input_box: 'confirm_pwd_input',
			other_options: 'other_options',
			show_Register_Form: true
			// 调试完了改成true
		}
	},
	methods:{
		hide_register_form(){
			this.show_Register_Form = false;
		}
	}
}
</script>

<style scoped>
.register_container{
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
.email_contianer, .captcha_container, .username_container, .other_options{
	display: flex;
    justify-content: space-evenly;
    align-items: baseline;
    height: 2em;
    margin: 0.5em 0 0.5em 0;
}

.show_captcha{
	width: 5em;
	height: 80%;
	background-color: lightskyblue;
}
.set_password, .confirm_password{
	display: flex;
    justify-content: space-evenly;
    align-items: baseline;
    height: 2em;
    margin: 0.5em 0 0.5em 0;
}



</style>

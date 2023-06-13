<template>
	<div :class="register_container">
		<div class="logo_container"></div>

		<div :class="email_contianer">
			<p :class="email">Email: </p>
			<input type="text" :class="email_input">
			<button :class="get_captcha">Verify email</button>
		</div>

		<div :class="captcha_container">
			<p :class="captcha">Captcha: </p>
			<input type="text" :class="captcha_input">
			<div :class="show_captcha"></div>
		</div>

		<div :class="username_container">
			<p :class="username">Username: </p>
			<input type="text" :class="username_input">
		</div>

		<div :class="set_password">
			<p :class="set_pwd">Set your password: </p>
			<input type="text" :class="set_pwd_input">
		</div>

		<div :class="confirm_password">
			<p :class ='confirm_pwd'>Confirm your password: </p>
			<input type="text" :class ='confirm_pwd_input'>
		</div>

		<div :class="other_options">
			<button :class="turn_to_login">Already a user? Login!</button>
			<button :class="unknown_option"></button>
		</div>
	</div>
</template>

<script>
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
			username_hint: 'username',
			username_input_box: 'username_input',
			set_password: 'set_password',
			set_pwd_hint: 'set_pwd',
			set_pwd_input_box: 'set_pwd_input',
			confirm_password: 'confirm_password',
			confirm_pwd_hint :'confirm_pwd',
			confirm_pwd_input_box: 'confirm_pwd_input',
			other_options: 'other_options',
			turn_to_login: 'turn_to_login',
			unknown_option: 'unknown_option'
		}
	}
}
</script>

<style scoped>
.register_container{
    display: block;
    background-color: antiquewhite;
    width: 16em;
    height: 26em;
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
	margin: auto;
}
.email_contianer, .captcha_container, .username_container, .other_options{
	display: flex;
	justify-content: space-between;
    align-items: center;
	height: 2em;
    margin: 0.5em 0 0.5em 0;
}

.set_password, .confirm_password{
	display: block;
	margin: 0;/*0.5em 0 0.5em 0*/
}

.set_password p, .confirm_password p{
	margin: 5px auto;
}

.get_captcha, .turn_to_login, .unknown_option{
	display: inline-block;
	height: 1.5em;
}


</style>

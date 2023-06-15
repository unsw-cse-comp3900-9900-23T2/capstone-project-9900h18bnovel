<script setup>
import {
  Search
} from '@element-plus/icons-vue'
</script >

<script>
export default {
    data() {
        return {
            logo: 'NovelHub Logo',
            header_container: 'header_container',
            header_left: 'header_left',
            header_right: 'header_right',
            login_button: 'Sign In',
        }
    }
}
</script>

<template>
    <div :class="header_container">
        <div :class="header_left">
            <!-- Click here will return to Home page in any circumstances -->
            <h3>{{ logo }}</h3> 
            <div>
                <input v-model="userIn_Search">
                <button>Search</button>
            </div>
        </div>
        <!-- This right side of class will represent user thumbnail, name and log out button once token exists -->
        <div :class="header_right">
            <!-- Click here will link to login page -->
            <h3>{{ login_button }}</h3>
        </div>
    </div>
</template>

<style >
.logo{
  height: 60px;
  width: 120px
}
.searchButton {
  width: 200px;
}
.header_container {
  display: flex;
  background-color: antiquewhite;
  /* border: 1px orangered solid; */
  padding: 20px;
  /* position: ?; */
}

.header_left {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* border: 1px red solid; */
  width: 80%;
}

.header_right {
    display: flex;
    justify-content: center;
    align-items: center;
    /* border: 1px orange solid; */
    width: 20%;
}

</style>

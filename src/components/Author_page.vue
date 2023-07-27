<script setup>
import axios from 'axios';
</script>
<script>
export default {
  data() {
    return {
      penName: null,
      signature: null,
      isAuthor: false,
    }
  },
  methods: {
    async register_author() {
      const reqbody = {
        "authorId": 0,
        "userId": this.$store.getters.GetUID,
        "penName": this.penName,
        "signature": this.signature,
      }
      await axios.post("http://localhost:8888/api/author/register", reqbody)
        .catch(error => {
          console.error(error);
        });
      this.isAuthor = true;
    }
  }
}
</script>
<template>
  <div class="authorPageBody">
    <div v-if="!isAuthor" class="registerForm">
      <div>
        Pen Name:<el-input v-model="penName"></el-input>
        Signature:<el-input v-model="signature"></el-input>
        <el-button @click='register_author'>
          CONTINUE
        </el-button>
      </div>
    </div>
    <div v-else>
      <el-row class="navbarAuthor">
        <el-col :span="6">
          <el-menu>
            <h1>Dashboard</h1>
            <el-sub-menu>
              <template #title>
                <span>My Books</span>
              </template>
            </el-sub-menu>
          </el-menu>
        </el-col>
        <el-col :span="12">
          This is aaaa
        </el-col>
      </el-row>
    </div>

  </div>
</template>
<style>
.authorPageBody {
  width: 1152px;
  min-width: 1152px;
  margin: auto;
}

.registerForm {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: auto;
}
</style>
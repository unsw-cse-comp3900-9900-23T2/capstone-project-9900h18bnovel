<script>
export default {
  data() {
    return {
    }
  },
  methods:{
    goAllNov(){
      this.$router.push('/allnovels');
    }
  }
}
</script>
<template>
  <div class="nav_container">
    <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" background-color="#545c64"
      text-color="#fff" active-text-color="#ffd04b" @select="handleSelect">
      <el-menu-item index="1" @click="goAllNov">All Novels</el-menu-item>
      <el-sub-menu index="2">
        <template #title>Browse</template>
        <el-sub-menu index="2-1">
          <template #title>Filter 1</template>
          <el-menu-item index="2-1-1">1</el-menu-item>
          <el-menu-item index="2-1-2">2</el-menu-item>
          <el-menu-item index="2-1-3">3</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2-2">
          <template #title>Filter 2</template>
          <el-menu-item index="2-2-1">1</el-menu-item>
          <el-menu-item index="2-2-2">2</el-menu-item>
          <el-menu-item index="2-2-3">3</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2-3">
          <template #title>Filter 3</template>
          <el-menu-item index="2-3-1">1</el-menu-item>
          <el-menu-item index="2-3-2">2</el-menu-item>
          <el-menu-item index="2-3-3">3</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2-4">
          <template #title>Filter 4</template>
          <el-menu-item index="2-4-1">1</el-menu-item>
          <el-menu-item index="2-4-2">2</el-menu-item>
          <el-menu-item index="2-4-3">3</el-menu-item>
        </el-sub-menu>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>Ranking</template>
          <el-menu-item index="3-1">Rank 1</el-menu-item>
          <el-menu-item index="3-2">Rank 2</el-menu-item>
          <el-menu-item index="3-3">Rank 3</el-menu-item>
      </el-sub-menu>
    <el-menu-item index="4">My Creation</el-menu-item>
    <el-menu-item index="5">Newest Update</el-menu-item>
  </el-menu>
</div>
</template>
<style>
</style>
<script>
export default {
  data() {
    return {
    }
  },
  methods: {
    goAllNov() {
      this.$emit('clearFilters');
      const currentURL = this.$store.getters.getCurrentURL;
      this.$router.push(`/allnovels/${currentURL}`);
    },
    goNewestRank(){
      this.$router.push('/newestrank');
    },
    goClickRank(){
      this.$router.push('/clickrank');
    },
    goUpdateRank(){
      this.$router.push('/updaterank');
    },
  }
}
</script>
<template>
  <div class="nav_container" style="min-width: 1152px;">
    <el-menu class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff"
      active-text-color="#ffd04b" >
      <el-menu-item index="1" @click="goAllNov">Browse Novels</el-menu-item>
      <!-- <el-sub-menu index="2">
        <template #title>Browse</template>
        <el-sub-menu index="2-1">
          <template #title>Genres</template>
          <el-menu-item index="2-1-1">Action</el-menu-item>
          <el-menu-item index="2-1-2">Romance</el-menu-item>
          <el-menu-item index="2-1-3">Fantasy</el-menu-item>
          <el-menu-item index="2-1-4">Mystery</el-menu-item>
          <el-menu-item index="2-1-5">Horror</el-menu-item>
          <el-menu-item index="2-1-6">Thriller</el-menu-item>
          <el-menu-item index="2-1-7">Drama</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2-1">
          <template #title>Book Status</template>
          <el-menu-item index="2-1-1">Ongoing</el-menu-item>
          <el-menu-item index="2-1-2">Completed</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2-3">
          <template #title>Update Time (&le;)</template>
          <el-menu-item index="2-3-1">A week</el-menu-item>
          <el-menu-item index="2-3-2">A month</el-menu-item>
          <el-menu-item index="2-3-3">A year</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2-4">
          <template #title>Book direction</template>
          <el-menu-item index="2-4-1">Male Lead</el-menu-item>
          <el-menu-item index="2-4-2">Female Lead</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
    -->
      <el-sub-menu index="3">
        <template #title>Ranking</template>
        <el-menu-item index="3-1" @click="goClickRank">Click Rank</el-menu-item>
        <el-menu-item index="3-2" @click="goNewestRank">Newest Rank</el-menu-item>
        <el-menu-item index="3-3" @click="goUpdateRank">Update Rank</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="4">My Creation</el-menu-item>
    </el-menu>
  </div>
</template>
<style></style>
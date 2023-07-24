<script setup>
import {
  HomeFilled,
  Platform,
  TrendCharts,
  Collection,
  EditPen,
} from '@element-plus/icons-vue'
</script>
<script>
export default {
  data() {
    return {
      activeIndex: "/home",
    }
  },
  watch: {
    '$route': {
      handler() {
        this.activeIndex = "/" + this.$route.path.split("/")[1];
      },
      immediate: true,
    },
  },
}
</script>
<template>
  <div class="menu" style="min-width: 1152px;">
    <el-menu :default-active="activeIndex" mode="horizontal" background-color="#f5f5f5" router>
      <el-menu-item route index="/home"><el-icon>
          <HomeFilled />
        </el-icon>Home</el-menu-item>
      <el-menu-item route index="/allnovels"><el-icon>
          <Platform />
        </el-icon>Browse</el-menu-item>
      <el-sub-menu>
        <template #title><el-icon>
            <TrendCharts />
          </el-icon>Ranking</template>
        <el-menu-item route index="/clickrank">Click Rank</el-menu-item>
        <el-menu-item route index="/newestrank">Newest Rank</el-menu-item>
        <el-menu-item route index="/updaterank">Update Rank</el-menu-item>
      </el-sub-menu>
      <el-menu-item route index="4"><el-icon>
          <EditPen />
        </el-icon>My Creation</el-menu-item>
      <el-menu-item route index=""><el-icon>
          <Collection />
        </el-icon>My Collection</el-menu-item>
    </el-menu>
  </div>
</template>
<style>
.menu .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  background-color: rgb(0, 0, 0, 0);
}
</style>
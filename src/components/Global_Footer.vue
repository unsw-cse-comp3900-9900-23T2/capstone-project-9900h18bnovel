<template>
  <footer class="footer">
    <div class="footer-top">
      <div class="footer-top-blocks">
        <img src="../logo.png" style="height: 100px;" />
      </div>
      <div class="footer-top-blocks">
        <h2>TEAMS</h2>
        <div style="color: rgb(117, 117, 117); margin-bottom: 5px;">Mingjie Huang</div>
        <div style="color: rgb(117, 117, 117); margin-bottom: 5px;">Yuyan Xiong</div>
        <div style="color: rgb(117, 117, 117); margin-bottom: 5px;">Dazhi Gao</div>
        <div style="color: rgb(117, 117, 117); margin-bottom: 5px;">Liuyi Chen</div>
        <div style="color: rgb(117, 117, 117); margin-bottom: 5px;">Guoling Dong</div>
      </div>
      <div class="footer-top-blocks">
        <h2>ABOUT</h2>
        <el-popover placement="right" :width="215" trigger="click"
          content="Front-end: Vue2 + Vue3 + VueX + Element-UI. Back-end: sprint boot + redis + mysql.">
          <template #reference>
            <span class="footer-elements">How</span>
          </template>
        </el-popover>
        <el-popover placement="right" :width="215" trigger="click"
          content="COMP9900, Postgraduate Capstone Project, UNSW">
          <template #reference>
            <span class="footer-elements">Why</span>
          </template>
        </el-popover>
        <el-popover placement="right" :width="215" trigger="click"
          content="Sprint 1, June 15th 2023 - June 29th 2023. Sprint 2, June 29th 2023 - July 20th 2023. Sprint 3, July 20th 2023 - August 3rd 2023">
          <template #reference>
            <span class="footer-elements">When</span>
          </template>
        </el-popover>
      </div>
      <div class="footer-top-blocks">
        <h2>REFERRALS</h2>
        <a class="footer-elements" href="https://www.inkitt.com/genres/scifi">Inkitt</a>
        <a class="footer-elements" href="https://www.webnovel.com/">WebNovel</a>
        <a class="footer-elements" href="https://www.novelnow.com/">NovelNow</a>
      </div>
    </div>
    <div style="margin-top: 20px;">
      © 2023 NovelHub. All rights reserved.
    </div>
  </footer>
</template>

<style>
.footer {
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding-top: 20px;
  padding-bottom: 20px;
}

.footer-top {
  display: flex;
}

.footer-top-blocks {
  display: flex;
  flex-direction: column;
  margin-right: 60px;
  
}

.footer-elements {
  color: rgb(117, 117, 117);
  text-decoration: none;
  margin-bottom: 5px;
}

.footer-elements:hover {
  cursor: pointer;
  text-decoration: underline;
}
</style>

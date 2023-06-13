<script>
export default {
    data() {
        return {
            nav_container: 'nav_container',
            browse: 'Browse',
            ranking: 'Ranking',
            all_Nov: 'All Novel',
            my_creat: 'My Creation',
            latest_upda: 'Lastest Update'
        }
    }
}
</script>
<template>
    <div :class="nav_container">
        <el-dropdown :hide-on-click="false">
            <el-link>{{ browse }}</el-link>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>
                        <b>Filter 1</b>
                        <el-dropdown-item divided>Action 1</el-dropdown-item>
                        <el-dropdown-item divided>Action 1</el-dropdown-item>
                        <el-dropdown-item divided>Action 1</el-dropdown-item>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <b>Filter 2</b>
                        <el-dropdown-item divided>Action 2</el-dropdown-item>
                        <el-dropdown-item divided>Action 2</el-dropdown-item>
                        <el-dropdown-item divided>Action 2</el-dropdown-item>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <b>Filter 3</b>
                        <el-dropdown-item divided>Action 3</el-dropdown-item>
                        <el-dropdown-item divided>Action 3</el-dropdown-item>
                        <el-dropdown-item divided>Action 3</el-dropdown-item>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <b>Filter 4</b>
                        <el-dropdown-item divided>Action 4</el-dropdown-item>
                        <el-dropdown-item divided>Action 4</el-dropdown-item>
                        <el-dropdown-item divided>Action 4</el-dropdown-item>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <b>Filter 5</b>
                        <el-dropdown-item divided>Action 5</el-dropdown-item>
                        <el-dropdown-item divided>Action 5</el-dropdown-item>
                        <el-dropdown-item divided>Action 5</el-dropdown-item>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        
        <el-dropdown :hide-on-click="false">
            <el-link>{{ ranking }}</el-link>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>Rank 1</el-dropdown-item>
                    <el-dropdown-item divided>Rank 2</el-dropdown-item>
                    <el-dropdown-item divided>Rank 3</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown><!-- with dropbox -->
        <el-link>{{ all_Nov }}</el-link>
        <el-link>{{ my_creat }}</el-link>
        <el-link>{{ latest_upda }}</el-link>
    </div>
</template>
<style>
b{
    font-size: medium;
}
.nav_container {
    display: flex;
    justify-content: space-between;
    background-color: rgb(203, 252, 252);
    padding: 20px;
}
</style>

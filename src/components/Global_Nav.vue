<script>
export default {
    data(){
        return{
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
        <button>{{ browse }}</button><!-- with dropbox -->
        <button>{{ ranking }}</button><!-- with dropbox -->
        <button>{{ all_Nov }}</button>
        <button>{{ my_creat }}</button>
        <button>{{ latest_upda }}</button>
    </div>
</template>
<style >
.nav_container{
    display: flex;
    justify-content: space-between;
    background-color:rgb(203, 252, 252);
    padding: 20px;
}
</style>

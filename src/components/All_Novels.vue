<template>
    <Global_Header />
    <Global_Nav />
    <div class="allnovel_body">
        <div class="allnovel_content">
            <h1>All Novels</h1>
        </div>
        <Global_Footer />
    </div>
</template>

<script>
import Global_Header from './Global_Header.vue';
import Global_Footer from './Global_Footer.vue';
import Global_Nav from './Global_Nav.vue';

export default {
    components: {
        Global_Footer,
        Global_Header,
        Global_Nav
    }
}
</script>

<style >
</style>

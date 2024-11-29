<script setup lang="ts">
import { useGetMe, useGetUsers } from '@entities/user/api';

console.log(1);
const { isLoading } = useGetMe();
console.log(2);
useGetUsers();
console.log(3);
</script>

<template>
<div class="container">
  <div v-if="isLoading">ЗАГРУЗКА КЛИЕНТСКОЙ СТРАНЦЫ</div>
  ClientMainPage
</div>
</template>

<style scoped>

</style>
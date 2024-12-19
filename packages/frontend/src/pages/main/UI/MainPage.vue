<script setup lang="ts">
import { httpClient } from '@shared/api';

const cl = async () => {
  await httpClient.get('users');
};
</script>

<template>
  <div class="main">
    Главная страница
    <button @click="cl">Запросить юзеров</button>
  </div>
</template>
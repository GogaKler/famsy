<script setup lang="ts">
import { HttpClient } from '@shared/api/http-client';

const httpClient = HttpClient.getInstance(import.meta.env.VITE_BACKEND_URL || '');
const cl = async () => {
  await httpClient.get('users');
};
</script>

<template>
  <div class="main bg-action-default">
    Главная страница
    <button @click="cl">
      Запросить юзеров
    </button>
  </div>
</template>
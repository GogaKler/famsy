<script setup lang="ts">
import { RouterView } from 'vue-router';
import { useAuthStore } from '@entities/auth';

const authStore = useAuthStore();
</script>

<template>
  {{ authStore.isAuth }}
  <RouterView />
</template>
<script setup lang="ts">
import { useTheme } from '@features/theme-switcher';
import { RouterView } from 'vue-router';

useTheme();
</script>

<template>
  <RouterView />
</template>
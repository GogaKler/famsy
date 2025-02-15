<script lang="ts" setup>
import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
const authService: AuthService = container.resolve(AuthService);
const currentUser = authService.authStore.currentUser;

const initials = computed(() => {
  return `${currentUser?.username[0]}${currentUser?.username[1]}`.toUpperCase();
});
</script>

<template>
  <FamsyAvatar :label="initials" shape="circle" class="cursor-pointer" />
</template>
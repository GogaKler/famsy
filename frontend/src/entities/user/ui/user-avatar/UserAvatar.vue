<script lang="ts" setup>
import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
const authService: AuthService = container.resolve(AuthService);
const currentUser = authService.authStore.currentUser?.usernameInitials;

const initials = computed(() => {
  return `${currentUser}`.toUpperCase();
});
</script>

<template>
  <FamsyAvatar :label="initials" shape="circle" class="cursor-pointer" />
</template>
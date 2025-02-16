<script lang="ts" setup>
import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
const authService: AuthService = container.resolve(AuthService);

const usernameInitials = computed(() => {
  return authService.authStore.currentUser?.usernameInitials;
});

</script>

<template>
  <FamsyAvatar :label="usernameInitials" shape="circle" class="cursor-pointer" />
</template>
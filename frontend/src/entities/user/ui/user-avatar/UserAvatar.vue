<script lang="ts" setup>
import { AuthService } from '@entities/auth';
import { storeToRefs } from 'pinia';
import { container } from 'tsyringe';

const authService: AuthService = container.resolve(AuthService);
const { currentUser } = storeToRefs(authService.authStore);

</script>

<template>
  <FamsyAvatar :label="currentUser?.usernameInitials" shape="circle" class="cursor-pointer" />
</template>
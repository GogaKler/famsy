<script setup lang="ts">
import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import { FamsyButton } from '@shared/ui';
import { useRouter } from 'vue-router';
import { computed } from 'vue';

const router = useRouter();
const authService: AuthService = container.resolve(AuthService);
const userId = computed(() => authService.authStore.user?.id || 0);
const isLogoutLoading = computed(() => authService.isLogoutLoading(userId.value));


async function logout() {
  await authService.logout(userId.value);
  await router.push({ name: 'auth-login' });
}
</script>

<template>
  <div class="main">
    Главная страница
    <FamsyButton
      variant="primary"
      :disabled="isLogoutLoading"
      @click="logout">
      Выход
    </FamsyButton>
  </div>
</template>
<script setup lang="ts">
import RegisterForm from './RegisterForm.vue';
import { useBreakpoint } from '@shared/lib';

const { isMobile } = useBreakpoint();
</script>

<template>
  <div class="
    w-full 
    sm:bg-surface-secondary/30
    sm:backdrop-blur-md
    sm:p-8
    rounded-2xl
  ">
    <div class="text-center mb-8">
      <h2 class="text-2xl font-bold text-text-primary cursor-default">
        Добро пожаловать! 🎉
      </h2>
    </div>

    <RegisterForm />

    <div class="mt-4">
      <p v-if="!isMobile" class="text-sm text-text-secondary text-center cursor-default">
        Уже есть аккаунт?
        <RouterLink 
          :to="{ name: 'auth-login' }" 
          class="text-action-default hover:text-action-hover font-medium ml-1 transition-colors duration-200"
        >
          Войти
        </RouterLink>
      </p>
      <FamsyButton
        v-else
        class="w-full"
        variant="outlined"
        @click="$router.push({ name: 'auth-login' })"
      >
        Войти в существующий аккаунт
      </FamsyButton>
    </div>
  </div>
</template>


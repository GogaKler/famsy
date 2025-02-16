<script setup lang="ts">
import LoginForm from './LoginForm.vue';
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
    <div class="absolute inset-x-0 top-0 h-[1px] bg-gradient-to-r from-transparent via-action-default to-transparent opacity-20"></div>

    <div class="text-center mb-6 space-y-2">
      <h2 class="text-2xl font-bold text-text-primary cursor-default">
        С возвращением! 👋
      </h2>
      <p class="text-md text-text-secondary cursor-default">
        Войдите в свой аккаунт для продолжения
      </p>
    </div>

    <LoginForm />

    <div class="mt-6">
      <p v-if="!isMobile" class="text-sm text-text-secondary text-center cursor-default">
        Еще нет аккаунта?
        <RouterLink 
          :to="{ name: 'auth-register' }" 
          class="text-action-default hover:text-action-hover font-medium ml-1 transition-colors duration-200"
        >
          Зарегистрироваться
        </RouterLink>
      </p>
      <FamsyButton
        v-else
        class="w-full"
        variant="outlined"
        @click="$router.push({ name: 'auth-register' })"
      >
        Зарегистрироваться
      </FamsyButton>
    </div>
  </div>
</template>


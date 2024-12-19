<script setup lang="ts">
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { AuthAPI, type TLoginDto } from '@entities/auth';
const router = useRouter();

const loginForm = reactive<TLoginDto>({
  login: '',
  password: '',
});

async function auth() {
  await AuthAPI.login(loginForm);
  await router.push({ name: 'main-root' });
}
</script>

<template>
  <div class="flex flex-col gap-2 bg-primary p-4">
    <input v-model="loginForm.login" type="text" class="mb-3">
    <input v-model="loginForm.password" type="password" class="mb-3">
    <button class="mb-3" @click="auth">
      Логин
    </button>
  </div>
</template>
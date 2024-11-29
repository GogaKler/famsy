<script setup lang="ts">
import { reactive } from 'vue';
import { httpClient } from '@shared/api';
import { useRouter } from 'vue-router';
const router = useRouter();

interface IAuthLogin {
  login: string
  password: string
}

const loginForm = reactive<IAuthLogin>({
  login: '',
  password: '',
});

async function auth() {
  await httpClient.post('/api/auth/login', { ...loginForm });
  await router.push({ name: 'client-main' });
}
</script>

<template>
<div>
  <div class="bg-secondary flex flex-col gap-2">
  </div>
    <div class="flex flex-col gap-2 bg-primary p-4">
      <input v-model="loginForm.login" type="text" class="mb-3">
      <input v-model="loginForm.password" type="password" class="mb-3">
      <button class="mb-3" @click="auth">
        Логин
      </button>
    </div>
</div>
</template>
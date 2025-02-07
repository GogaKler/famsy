<script setup lang="ts">
import { AuthService, AuthStateActions, type LoginDTO } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { FormKitMessages } from '@formkit/vue';
import { useFormKitErrors } from '@shared/lib';

const router = useRouter();
const authService: AuthService = container.resolve(AuthService);
const isLoading = computed(() => authService.isLoginLoading);
useFormKitErrors(authService, authService.unAuthUserIdentifier, AuthStateActions.LOGIN);

const auth = async (fields: LoginDTO): Promise<void> => {
  try {
    await authService.login(fields);
    await router.push({ name: 'dashboard' });
  } catch {}
};
</script>

<template>
  <FormKit
    :id="AuthStateActions.LOGIN"
    type="form"
    form-class="w-full space-y-5"
    :actions="false"
    @submit="auth">
    <template #default="{ state: { valid } }">
      <FormKitMessages />
      <FormKit
        type="text"
        name="login"
        label="Логин"
        autocomplete="username"
        validation="required"
        placeholder="Email или имя пользователя"
      />

      <FormKit
        type="password"
        name="password"
        label="Пароль"
        autocomplete="current-password"
        validation="required"
        placeholder="Введите пароль"
      />

      <div class="flex items-center justify-between">
        <FormKit id="remember" type="checkbox" name="remember" label="Запомнить меня" />
        <a
          href="#"
          class="text-sm text-action-default hover:text-action-hover"
        >
          Забыли пароль?
        </a>
      </div>

      <FamsyButton
        type="submit"
        class="w-full"
        label="Войти"
        :loading="isLoading"
        :disabled="isLoading || !valid"
      />
    </template>
  </FormKit>
</template>


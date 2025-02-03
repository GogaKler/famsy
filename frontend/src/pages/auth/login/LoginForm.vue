<script setup lang="ts">
import { AuthService, AuthStateActions, type LoginDTO } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { FamsyButton } from '@shared/ui';
import { FormKitMessages } from '@formkit/vue';
import { useFormKitErrors } from '@shared/lib';

const router = useRouter();
const authService: AuthService = container.resolve(AuthService);
const isLoading = computed(() => authService.isLoginLoading);
useFormKitErrors(authService, authService.unAuthUserIdentifier, AuthStateActions.LOGIN);

const auth = async (fields: LoginDTO): Promise<void> => {
  try {
    await authService.login(fields);
    await router.push({ name: 'main-root' });
  } catch {}
};
</script>

<template>
  <FormKit
    :id="AuthStateActions.LOGIN"
    type="form"
    form-class="w-full"
    :actions="false"
    @submit="auth">
    <div class="space-y-4">
      <FormKitMessages />
      <FormKit
        type="text"
        name="login"
        label="Логин"
        autocomplete="username"
        placeholder="Email или имя пользователя"
        data-testid="loginInput"
      />

      <FormKit
        type="password"
        name="password"
        label="Пароль"
        autocomplete="current-password"
        placeholder="Введите пароль"
        data-testid="passwordInput"
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
        :loading="isLoading"
        :disabled="isLoading"
        data-testid="loginButton"
      >
        Войти
      </FamsyButton>
    </div>
  </FormKit>
</template>


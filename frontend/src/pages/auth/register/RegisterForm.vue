<script setup lang="ts">
import { AuthService, AuthStateActions, type RegisterDTO } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { FamsyButton } from '@shared/ui';
import { FormKitMessages } from '@formkit/vue';
import { useFormKitErrors } from '@shared/lib';

const router = useRouter();
const authService: AuthService = container.resolve(AuthService);
const isLoading = computed(() => authService.isRegisterLoading);
useFormKitErrors(authService, authService.unAuthUserIdentifier, AuthStateActions.REGISTER);

const register = async (fields: RegisterDTO): Promise<void> => {
  try {
    await authService.register(fields);
    await router.push({ name: 'main-root' });
  } catch {}
};
</script>

<template>
  <FormKit
    :id="AuthStateActions.REGISTER"
    type="form"
    form-class="w-full space-y-5"
    :actions="false"
    @submit="register">
    <template #default="{ state: { valid } }">
      <FormKitMessages />
      <FormKit
        type="text"
        name="username"
        label="Имя пользователя"
        autocomplete="username"
        validation="required"
        placeholder="Введите имя пользователя"
      />

      <FormKit
        type="email"
        name="email"
        label="Email"
        autocomplete="email"
        validation="required|email"
        placeholder="Введите email"
      />

      <FormKit
        type="password"
        name="password"
        label="Пароль"
        autocomplete="current-password"
        placeholder="Введите пароль"
        validation="required"
      />

      <FormKit
        type="password"
        name="password_confirm"
        label="Подтверждение пароля"
        placeholder="Введите пароль еще раз"
        validation="required|confirm"
      />

      <FamsyButton
        type="submit"
        class="w-full"
        :loading="isLoading"
        :disabled="isLoading || !valid"
      >
        Зарегистрироваться
      </FamsyButton>
    </template>
  </FormKit>
</template>


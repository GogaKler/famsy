<script lang="ts" setup>
import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
import { useRouter } from 'vue-router';

interface Props {
  visible: boolean;
}

interface Emits {
  (e: 'update:visible', value: boolean): void;
}
const router = useRouter();
const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const sidebarFlag = (value: boolean) => {
  emit('update:visible', value);
};

const authService: AuthService = container.resolve(AuthService);
const isLogoutLoading = computed(() => authService.isLogoutLoading);
const userName = authService.authStore;

async function handleLogout() {
  await authService.logout();
  router.push({ name: 'auth-root' });
}

</script>

<template>
  <FamsyDrawer
    :visible="visible"
    class="!w-full md:!w-80 lg:!w-[23rem]"
    :header="userName.user?.username"
    position="right" 
    @update:visible="sidebarFlag"
  >
    <template #header>
      <div class="flex items-center gap-2">
        <FamsyAvatar :label="userName.user?.id" shape="circle" />
        <span class="font-bold">{{ userName.user?.username }}</span>
      </div>
    </template>
    <nav class="p-4 space-y-6  border-t">
      <section class="space-y-2">
        <RouterLink to="/dashboard" class="flex items-center gap-3 px-3 py-2 rounded-lg text-gray-700 hover:bg-surface-primary">
          <FontAwesomeIcon icon="user" />
          <span>Дашборд</span>
        </RouterLink>
        <div class="flex justify-end">
          <FamsyButton
            :disabled="isLogoutLoading"
            :loading="isLogoutLoading"
            @click="handleLogout"
          >
            Выйти
          </FamsyButton>
        </div>
      </section>
    </nav>
  </FamsyDrawer>
</template>
<script lang="ts" setup>
import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { UserAvatar } from '@entities/user';

interface Props {
  visible: boolean;
}

interface Emits {
  (e: 'update:visible', value: boolean): void;
}
const router = useRouter();
const props = defineProps<Props>();
const emit = defineEmits<Emits>();

function toggleSidebar(value: boolean) {
  emit('update:visible', value);
}

const authService: AuthService = container.resolve(AuthService);
const isLogoutLoading = computed(() => authService.isLogoutLoading);
const currentUser = authService.authStore.currentUser?.usernameInitials ;

async function handleLogout() {
  await authService.logout();
  router.push({ name: 'auth-root' });
}

</script>

<template>
  <FamsyDrawer
    :visible="visible"
    class="!w-full md:!w-80 lg:!w-[23rem]"
    position="right" 
    @update:visible="toggleSidebar"
  >
    <template #header>
      <div class="flex items-center gap-2">
        <UserAvatar />
        <span class="font-bold">{{ currentUser }}</span>
      </div>
    </template>
    <nav class="p-4 space-y-6 border-t">
      <section class="space-y-2">
        <RouterLink :to="{ name: 'dashboard' }" class="flex items-center gap-3 px-3 py-2 rounded-lg text-gray-700 hover:bg-surface-tertiary/50">
          <FontAwesomeIcon icon="table-columns" />
          <span>Дашборд</span>
        </RouterLink>
        <FamsyButton
          :disabled="isLogoutLoading"
          :loading="isLogoutLoading"
          @click="handleLogout"
        >
          Выйти
        </FamsyButton>
      </section>
    </nav>
  </FamsyDrawer>
</template>
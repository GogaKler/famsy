<script lang="ts" setup>
import HeaderLayout from './HeaderLayout.vue';
import { HeaderTabs } from '@widgets/header-tabs';
import { ref } from 'vue';
import { NavigationSidebar } from '@widgets/sidebar';
import { UserSidebar, UserAvatar } from '@entities/user';


const isSidebarOpen = ref(false);
const isUserSidebarOpen = ref(false);

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

const toggleUserSidebar = () => {
  isUserSidebarOpen.value = !isUserSidebarOpen.value;
};
</script>

<template>
  <HeaderLayout>
    <template #left>
      <FamsyButton variant="text" severity="secondary" @click="toggleSidebar">
        <template #icon>
          <FontAwesomeIcon icon="bars" />
        </template>
      </FamsyButton>
      <div class="flex items-center gap-2">
        <FontAwesomeIcon icon="wallet" />
        <div class="font-medium text-lg text-action-default">
          FAMSY
        </div>
      </div>

      <div class="text-text-tertiary">
        |
      </div>

      <div class="flex items-center gap-4 font-medium">
        <RouterLink :to="{name: 'profile'}" class="text-text-secondary hover:text-action-default">
          Личный кабинет
        </RouterLink>
      </div>
    </template> 

    <template #right>
      <UserAvatar @click="toggleUserSidebar" />
    </template> 

    <template #bottom>
      <HeaderTabs class="px-4 min-h-12" />
    </template>
  </HeaderLayout>

  <NavigationSidebar v-model:visible="isSidebarOpen" />
  <UserSidebar :visible="isUserSidebarOpen" @update:visible="toggleUserSidebar" />
</template>
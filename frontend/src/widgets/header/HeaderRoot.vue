<script lang="ts" setup>
import HeaderLayout from './HeaderLayout.vue';
import { HeaderTabs } from '@widgets/header-tabs';
import { FamsyButton } from '@shared/ui';
import { ref } from 'vue';
import { SidebarRoot } from '@widgets/sidebar';

const isSidebarOpen = ref(false);

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};
</script>

<template>
  <HeaderLayout>
    <template #left>
      <button 
        class="w-10 h-10 flex items-center justify-center rounded-lg text-gray-500 hover:bg-surface-primary transition-colors duration-200"
        @click="toggleSidebar"
      >
        <FontAwesomeIcon icon="bars" class="text-xl" />
      </button>

      <div class="flex items-center gap-2">
        <FontAwesomeIcon icon="wallet" />
        <div class="font-medium text-lg text-action-default">
          FAMSY
        </div>
      </div>

      <div class="flex items-center gap-4 text-sm">
        <a href="#" class="text-text-secondary hover:text-action-default transition-colors duration-200">Личный кабинет</a>
        <span class="text-text-tertiary">></span>
        <a href="#" class="text-text-secondary hover:text-action-default transition-colors duration-200">Семейный бюджет</a>
      </div>
    </template> 

    <template #right>
      <div class="flex items-center gap-4">
        <FontAwesomeIcon icon="bell" />
        <FamsyButton size="sm">
          <FontAwesomeIcon icon="plus" />
        </FamsyButton>
        <FontAwesomeIcon icon="user" />
      </div>
    </template> 

    <template #bottom>
      <HeaderTabs class="px-4 min-h-10" />
    </template>
  </HeaderLayout>

  <SidebarRoot v-model:is-open="isSidebarOpen">
    <section class="space-y-2">
      <h3 class="text-xs font-semibold text-gray-500 uppercase tracking-wider px-3">
        ФИНАНСЫ
      </h3>
      <a href="#" class="flex items-center gap-3 px-3 py-2 rounded-lg text-primary bg-action-default">
        <FontAwesomeIcon icon="wallet" />
        <span>Мой бюджет</span>
      </a>
      <a href="#" class="flex items-center gap-3 px-3 py-2 rounded-lg text-gray-700 hover:bg-surface-primary">
        <FontAwesomeIcon icon="people-roof" />
        <span>Семейный бюджет</span>
      </a>
    </section>
    <section class="space-y-2">
      <h3 class="text-xs font-semibold text-gray-500 uppercase tracking-wider px-3">
        ШАБЛОНЫ
      </h3>
      <a href="#" class="flex items-center gap-3 px-3 py-2 rounded-lg text-primary hover:bg-surface-primary">
        <FontAwesomeIcon icon="folder" />
        <span>Мои шаблоны</span>
      </a>
      <a href="#" class="flex items-center gap-3 px-3 py-2 rounded-lg text-gray-700 hover:bg-surface-primary">
        <FontAwesomeIcon icon="store" />
        <span>Маркетплейс</span>
      </a>
    </section>
    <section class="space-y-2">
      <h3 class="text-xs font-semibold text-gray-500 uppercase tracking-wider px-3">
        СЦЕНАРИИ
      </h3>
      <a href="#" class="flex items-center gap-3 px-3 py-2 rounded-lg text-primary hover:bg-surface-primary">
        <FontAwesomeIcon icon="flag" />
        <span>Активные цели</span>
      </a>
      <a href="#" class="flex items-center gap-3 px-3 py-2 rounded-lg text-gray-700 hover:bg-surface-primary">
        <FontAwesomeIcon icon="clock-rotate-left" />
        <span>История</span>
      </a>
    </section>
  </SidebarRoot>
</template>
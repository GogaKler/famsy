<script lang="ts" setup>
import HeaderLayout from './HeaderLayout.vue';
import { HeaderTabs } from '@widgets/header-tabs';
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
        <a href="#" class="text-text-secondary hover:text-action-default transition-colors duration-200">Личный кабинет</a>
        <FontAwesomeIcon icon="angle-right" size="sm" />
        <a href="#" class="text-text-secondary hover:text-action-default transition-colors duration-200">Семейный бюджет</a>
      </div>
    </template> 

    <template #right>
      <div class="flex items-center gap-4">
        <FamsyButton severity="secondary" text>
          <template #icon>
            <FontAwesomeIcon icon="bell" />
          </template>
        </FamsyButton>
        <FamsyButton severity="secondary" text>
          <template #icon>
            <FontAwesomeIcon icon="plus" />
          </template>
        </FamsyButton>
        <FamsyButton severity="secondary" text>
          <template #icon>
            <FontAwesomeIcon icon="user" />
          </template>
        </FamsyButton>
      </div>
    </template> 

    <template #bottom>
      <HeaderTabs class="px-4 min-h-12" />
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
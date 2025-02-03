<script lang="ts" setup>
import HeaderLayout from './HeaderLayout.vue';
import { HeaderTabs } from '@widgets/header-tabs';
import { FamsyButton } from '@shared/ui';
</script>

<template>
  <HeaderLayout>
    <template #left>
      <FontAwesomeIcon icon="bars" />
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
</template>
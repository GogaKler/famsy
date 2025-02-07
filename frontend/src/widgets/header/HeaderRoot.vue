<script lang="ts" setup>
import HeaderLayout from './HeaderLayout.vue';
import { HeaderTabs } from '@widgets/header-tabs';
</script>

<template>
  <HeaderLayout>
    <template #left>
      <FamsyButton variant="text" severity="secondary">
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
</template>
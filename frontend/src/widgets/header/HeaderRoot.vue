<script lang="ts" setup>
import HeaderLayout from './HeaderLayout.vue';
import { HeaderTabs } from '@widgets/header-tabs';
</script>

<template>
  <header class="bg-surface-secondary">
    <HeaderLayout class="p-4">
      <template #left>
        <div>
          Логотип
        </div>
      </template>
      <template #right>
        <div>
          Профиль
        </div>
        <div>
          Выйти
        </div>
      </template>
    </HeaderLayout>
    <HeaderTabs class="px-4 min-h-10" />
  </header>
</template>
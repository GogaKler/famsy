<script lang="ts" setup>
import type { HeaderTabList } from '@entities/header-tabs';
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const route = useRoute();
const router = useRouter();
const headerTabs = computed<HeaderTabList | null>(() => route.meta.headerTabs as HeaderTabList);

const navigate = (routeName: string) => {
  router.push({ name: routeName });
};

const isActive = (tabRouteName: string) => {
  return route.name === tabRouteName;
};
</script>

<template>
  <div v-if="headerTabs" class="border-b border-border-primary">
    <nav class="flex items-center gap-2">
      <div
        v-for="headerTab in headerTabs.getTabs()"
        :key="headerTab.routeName"
        class="relative flex items-center cursor-pointer text-text-secondary"
        @click="navigate(headerTab.routeName)"
      >
        <button 
          type="button"
          class="
            relative flex items-center gap-2.5 py-1.5 px-2 rounded-md
            text-sm font-medium
            hover:bg-surface-secondary
            after:absolute after:bottom-[-9px] after:left-0 after:right-0 after:h-[2px] after:transition-colors
          "
          :class="{ 'after:bg-action-default': isActive(headerTab.routeName) }"
        >
          <FontAwesomeIcon 
            v-if="headerTab.icon" 
            :icon="headerTab.icon" 
            class="shrink-0"
            aria-hidden="true"
          />
          <span>{{ headerTab.name }}</span>
        </button>
      </div>
    </nav>
  </div>
</template>

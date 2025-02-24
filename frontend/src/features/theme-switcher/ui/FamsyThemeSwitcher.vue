<script setup lang="ts">
import { useTheme } from '../model/useTheme';
import { FamsyTheme } from '@shared/ui';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const { theme, toggleTheme } = useTheme();
</script>

<template>
  <FamsyToggleSwitch :modelValue="theme === FamsyTheme.DARK" @update:modelValue="toggleTheme">
    <template #handle="{ checked }">
      <FontAwesomeIcon v-if="checked" icon="fa-regular fa-moon" size="xs" />
      <FontAwesomeIcon v-else icon="fa-regular fa-lightbulb" size="xs" />
    </template>
  </FamsyToggleSwitch>
</template>
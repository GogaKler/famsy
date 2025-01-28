<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
</script>

<template>
  <FontAwesomeIcon
    :icon="['fas', 'spinner']"
    :class="[
      'animate-spin',
      'text-current'
    ]"
  />
</template>

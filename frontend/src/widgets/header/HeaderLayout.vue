<script setup lang="ts">
</script>

<template>
  <header class="flex justify-between items-center p-2 bg-surface-secondary text-text-primary">
    <div class="flex items-center">
      <slot name="left" />
    </div>
    <div class="flex-1 text-center">
      <slot name="center" />
    </div>
    <div class="flex justify-end items-center">
      <slot name="right" />
    </div>
  </header>
</template>
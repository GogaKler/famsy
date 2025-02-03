<script setup lang="ts">
</script>

<template>
  <header class="flex justify-between items-center p-3 bg-surface-secondary text-text-primary">
    <div class="flex items-center gap-4">
      <slot name="left" />
    </div>
   
    <div class="flex items-center gap-4">
      <slot name="right" />
    </div>
  </header>
</template>
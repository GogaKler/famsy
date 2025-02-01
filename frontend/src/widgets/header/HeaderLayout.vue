<script lang="ts" setup>

</script>

<template>
  <div class="flex items-center justify-between">
    <div class="flex items-center gap-4">
      <slot name="left" />
    </div>
    <div class="flex items-center gap-4">
      <slot name="right" />
    </div>
  </div>
</template>

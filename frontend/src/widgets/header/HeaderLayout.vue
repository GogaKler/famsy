<template>
  <header class="bg-surface-secondary">
    <div class="flex items-center justify-between px-4 py-2">
      <div class="flex items-center gap-4">
        <slot name="left" />
      </div>
      <div class="flex items-center gap-4">
        <slot name="right" />
      </div>
    </div>
    <slot name="bottom" />
  </header>
</template>

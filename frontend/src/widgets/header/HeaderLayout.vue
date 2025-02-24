<template>
  <header class="bg-surface-primary">
    <div class="flex items-center justify-between px-4 pt-4 pb-2">
      <div class="flex items-center gap-5">
        <slot name="left" />
      </div>
      <div class="flex items-center gap-5">
        <slot name="right" />
      </div>
    </div>
    <slot name="bottom" />
  </header>
</template>

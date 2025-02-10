<script lang="ts" setup>
interface Props {
  isOpen: boolean;
}

interface Emits {
  (e: 'update:isOpen', value: boolean): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const handleClose = () => {
  emit('update:isOpen', false);
};
</script>

<template>
  <div>
    <Transition name="fade">
      <div v-if="isOpen" 
           class="fixed inset-0 bg-black/30 backdrop-blur-[2px] z-40"
           @click="handleClose">
      </div>
    </Transition>

    <aside 
      class="fixed top-0 right-0 w-[280px] h-full bg-surface-secondary shadow-xl z-50 transition-transform duration-300 ease-in-out transform"
      :class="{ 'translate-x-0': isOpen, 'translate-x-full': !isOpen }"
    >
      <div class="p-4 flex items-center justify-between border-b border-gray-200">
        <div class="flex items-center gap-2 text-lg font-semibold text-primary">
          <FontAwesomeIcon icon="user" />
          <div class="font-medium text-lg text-action-default">
            Иван Иванов
          </div>
        </div>
        <button 
          class=" text-gray-500 hover:bg-gray-100 rounded-lg transition-colors duration-200"
          @click="handleClose"
        >
          <FontAwesomeIcon icon="times" />
        </button>
      </div>
      <nav class="p-4 space-y-6">
        <slot />
      </nav>
    </aside>
  </div>
</template>
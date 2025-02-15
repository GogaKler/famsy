<script lang="ts" setup>
import { userRoutes } from './constants';
interface Props {
  visible: boolean;
}

interface Emits {
  (e: 'update:visible', value: boolean): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const sidebarFlag = (value: boolean) => {
  emit('update:visible', value);
};

</script>

<template>
  <FamsyDrawer 
    :visible="visible"
    class="!w-full md:!w-80 lg:!w-[23rem]"
    header="ФИНАНСЫ"
    position="left" 
    @update:visible="sidebarFlag"
  >
    <div class="space-y-9">
      <section class="space-y-2">
        <RouterLink v-for="userRoute in userRoutes" :key="userRoute.routeName" :to="{ name: userRoute.routeName }" class="flex items-center gap-3 px-3 py-2 rounded-lg text-gray-700 hover:bg-surface-primary">
          <FontAwesomeIcon :icon="userRoute.icon" />
          <span>{{ userRoute.title }}</span>
        </RouterLink>
      </section>
    </div>
  </FamsyDrawer>
</template>
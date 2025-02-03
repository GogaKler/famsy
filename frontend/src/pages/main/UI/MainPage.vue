<script setup lang="ts">
import HeaderLayoyt from '@/widgets/header/HeaderLayout.vue';
import { FamsyButton } from '@shared/ui';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
</script>

<template>
  <div class="main">
    <HeaderLayoyt>
      <template #left>
        <FontAwesomeIcon icon="bars" class="p-3 rounded-lg cursor-pointer hover:bg-surface-primary/50" />
        <div class="flex items-center p-2">
          <FontAwesomeIcon icon="wallet" class="p-2 rounded-lg cursor-pointer bg-action-default hover:bg-action-hover" />
          <div class="flex items-center font-medium text-lg text-action-default px-2.5">
            FAMSY
          </div>
        </div>

        <div class="flex items-center gap-2 pl-2.5 text-sm">
          <a href="#" class="text-text-secondary hover:text-action-default transition-colors duration-200">Личный кабинет</a>
          <span class="text-text-tertiary"> ></span>
          <a href="#" class="text-text-secondary hover:text-action-default transition-colors duration-200">Семейный бюджет</a>
        </div>
      </template> 
      <template #center>
      </template> 
      <template #right>
        <div class="flex items-center gap-4 pl-2.5">
          <div class="flex items-center justify-center p-3 rounded-lg cursor-pointer hover:bg-surface-primary/50">
            <FontAwesomeIcon icon="bell" />
          </div>
          <FamsyButton>
            <FontAwesomeIcon icon="plus" />
          </FamsyButton>
          <div class="flex items-center justify-center p-3 rounded-lg cursor-pointer transition-all duration-200 hover:bg-surface-primary/50">
            <div class="flex items-center justify-center">
              <FontAwesomeIcon icon="user" />
            </div>
          </div>
        </div>
      </template> 
    </HeaderLayoyt>
  </div>
</template>
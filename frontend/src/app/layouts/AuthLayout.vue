<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { useBreakpoint } from '@shared/lib';

const { isMobile } = useBreakpoint();
</script>

<template>
  <div class="min-h-screen bg-surface-primary relative overflow-hidden">
    <div class="absolute inset-0">
      <div class="absolute inset-0 bg-[size:24px_24px] bg-[radial-gradient(#00000025_1px,transparent_1px)] dark:bg-[radial-gradient(#ffffff15_1px,transparent_1px)]"></div>
    </div>

    <div class="min-h-screen w-full flex flex-col relative z-10">
      <div v-if="!isMobile" class="flex flex-row items-center justify-center min-h-screen px-6 lg:px-16">
        <div class="w-full lg:w-[55%] lg:pr-16 xl:pr-20 max-w-2xl">
          <div class="w-32 sm:w-40 mb-8 sm:mb-12 cursor-default">
            Лого
          </div>
          
          <h1 class="text-3xl sm:text-4xl lg:text-5xl font-bold text-text-primary mb-4 sm:mb-6 leading-tight cursor-default">
            Управляйте финансами<br>всей семьи
          </h1>

          <p class="text-lg text-text-secondary leading-relaxed mb-12 cursor-default">
            FAMSY поможет вам отслеживать доходы, расходы и планировать бюджет.
            Анализируйте свои финансы с помощью удобных графиков и статистики.
          </p>

          <div class="space-y-4">
            <div class="p-6 flex items-center gap-4 rounded-xl hover:bg-surface-secondary transition-colors">
              <div class="p-3 bg-action-default/10 rounded-lg">
                <FontAwesomeIcon icon="chart-pie" size="lg" class="text-action-default" />
              </div>
              <div>
                <h3 class="text-text-primary font-medium text-base sm:text-lg mb-1 cursor-default">
                  Аналитика
                </h3>
                <p class="text-text-secondary text-sm sm:text-base cursor-default">
                  Детальные отчеты и графики
                </p>
              </div>
            </div>

            <div class="p-6 flex items-center gap-4 rounded-xl hover:bg-surface-secondary transition-colors">
              <div class="p-3 bg-action-default/10 rounded-lg">
                <FontAwesomeIcon icon="wallet" size="lg" class="text-action-default" />
              </div>
              <div>
                <h3 class="text-text-primary font-medium text-lg mb-1 cursor-default">
                  Бюджетирование
                </h3>
                <p class="text-text-secondary text-sm sm:text-base cursor-default">
                  Планирование расходов
                </p>
              </div>
            </div>
          </div>
        </div>

        <div class="w-full lg:w-[45%] max-w-md">
          <RouterView v-slot="{ Component }">
            <Transition name="fade" mode="out-in">
              <component :is="Component" />
            </Transition>
          </RouterView>

          <div class="mt-6 text-center text-sm text-text-tertiary cursor-default">
            <p>© 2025 FAMSY. Все права защищены.</p>
          </div>
        </div>
      </div>

      <div v-else class="flex flex-col justify-between min-h-screen p-8">
        <div class="
          w-full
          relative
          overflow-hidden
        ">
          <RouterView v-slot="{ Component }">
            <Transition name="fade" mode="out-in">
              <component :is="Component" />
            </Transition>
          </RouterView>
        </div>

        <div class="mt-6 text-center text-xs sm:text-sm text-text-tertiary cursor-default">
          <p>© 2025 FAMSY. Все права защищены.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease-in;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>


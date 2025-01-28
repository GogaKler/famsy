<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import LoginForm from './LoginForm.vue';
import { FamsyButton } from '@shared/ui';
import { useBreakpoint } from '@shared/lib';

const { isMobile } = useBreakpoint();
</script>

<template>
  <div class="min-h-screen bg-surface-primary relative overflow-hidden">
    <div class="absolute inset-0">
      <div class="absolute inset-0 bg-gradient-to-br from-surface-primary via-surface-primary to-surface-secondary"></div>

      <div class="absolute inset-0 bg-[size:24px_24px] bg-[radial-gradient(#00000025_1px,transparent_1px)] dark:bg-[radial-gradient(#ffffff15_1px,transparent_1px)]"></div>
    </div>

    <div class="min-h-screen w-full flex flex-col relative z-10">
      <div v-if="!isMobile" class="flex flex-row items-center justify-center min-h-screen px-6 lg:px-16">
        <div class="w-full lg:w-[55%] lg:pr-16 xl:pr-20 max-w-2xl">
          <div class="w-32 sm:w-40 mb-8 sm:mb-12">
            Лого
          </div>
          
          <h1 class="text-3xl sm:text-4xl lg:text-5xl font-bold text-text-primary mb-4 sm:mb-6 leading-tight">
            Управляйте финансами<br>всей семьи
          </h1>
          
          <p class="text-base sm:text-lg text-text-secondary leading-relaxed mb-8 sm:mb-12 max-w-xl">
            FAMSY поможет вам отслеживать доходы, расходы и планировать бюджет.
            Анализируйте свои финансы с помощью удобных графиков и статистики.
          </p>

          <div class="space-y-4 max-w-xl">
            <div class="p-4 sm:p-6 flex items-center gap-4 rounded-xl bg-surface-overlay/60 hover:bg-surface-overlay/80 transition-colors">
              <div class="p-3 rounded-lg">
                <FontAwesomeIcon icon="chart-pie" class="w-5 h-5 sm:w-6 sm:h-6 text-action-default" />
              </div>
              <div>
                <h3 class="text-text-primary font-medium text-base sm:text-lg mb-1">
                  Аналитика
                </h3>
                <p class="text-text-secondary text-sm sm:text-base">
                  Детальные отчеты и графики
                </p>
              </div>
            </div>

            <div class="p-4 sm:p-6 flex items-center gap-4 rounded-xl hover:bg-surface-secondary transition-colors">
              <div class="p-3 bg-action-default/10 rounded-lg">
                <FontAwesomeIcon icon="wallet" class="w-5 h-5 sm:w-6 sm:h-6 text-action-default" />
              </div>
              <div>
                <h3 class="text-text-primary font-medium text-base sm:text-lg mb-1">
                  Бюджетирование
                </h3>
                <p class="text-text-secondary text-sm sm:text-base">
                  Планирование расходов
                </p>
              </div>
            </div>
          </div>
        </div>

        <div class="w-full lg:w-[45%] max-w-md">
          <div class="
            w-full 
            bg-surface-primary/80
            backdrop-blur-md
            p-6 sm:p-8
            rounded-2xl
            border border-border-disabled
            relative
            overflow-hidden
          ">
            <div class="absolute inset-x-0 top-0 h-[1px] bg-gradient-to-r from-transparent via-action-default to-transparent opacity-20"></div>

            <div class="text-center mb-6">
              <h2 class="text-xl sm:text-2xl font-bold text-text-primary mb-2">
                С возвращением! 👋
              </h2>
              <p class="text-text-secondary text-sm sm:text-base">
                Войдите в свой аккаунт для продолжения
              </p>
            </div>

            <LoginForm />

            <div class="mt-6 text-center">
              <p class="text-text-secondary text-sm">
                Еще нет аккаунта?
                <router-link 
                  to="/auth/register" 
                  class="text-action-default hover:text-action-hover font-medium ml-1 transition-colors duration-200"
                >
                  Зарегистрироваться
                </router-link>
              </p>
            </div>
          </div>

          <div class="mt-6 text-center text-sm text-text-tertiary">
            <p>© 2025 FAMSY. Все права защищены.</p>
          </div>
        </div>
      </div>

      <div v-else class="flex flex-col justify-between min-h-screen p-8">
        <div class="flex justify-center">
          <div class="w-24">
            Лого
          </div>
        </div>

        <div class="
          w-full
          relative
          overflow-hidden
        ">
          <div class="text-center mb-8">
            <h2 class="text-2xl font-bold text-text-primary mb-2">
              С возвращением! 👋
            </h2>
            <p class="text-md text-text-secondary">
              Войдите в свой аккаунт для продолжения
            </p>
          </div>

          <LoginForm />

          <FamsyButton variant="outline" class="mt-6 w-full">
            Создать аккаунт
          </FamsyButton>
        </div>

        <div class="mt-6 text-center text-xs sm:text-sm text-text-tertiary">
          <p>© 2025 FAMSY. Все права защищены.</p>
        </div>
      </div>
    </div>
  </div>
</template>


import { onMounted, ref, type Ref } from 'vue';
import { FamsyTheme } from '@shared/ui';

export const useTheme = () => {
  const theme: Ref<FamsyTheme, FamsyTheme> = ref<FamsyTheme>(FamsyTheme.LIGHT);

  const setTheme = (newTheme: FamsyTheme) => {
    document.documentElement.classList.remove(theme.value);
    document.documentElement.classList.add(newTheme);
    theme.value = newTheme;
    localStorage.setItem('theme', newTheme);
  };

  const toggleTheme = () => {
    const newTheme = theme.value === FamsyTheme.LIGHT ? FamsyTheme.DARK : FamsyTheme.LIGHT;
    setTheme(newTheme);
  };

  onMounted(() => {
    const savedTheme: FamsyTheme = localStorage.getItem('theme') as FamsyTheme;
    if (savedTheme) {
      setTheme(savedTheme);
      return;
    }

    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    setTheme(prefersDark ? FamsyTheme.DARK : FamsyTheme.LIGHT);
  });

  return {
    theme,
    setTheme,
    toggleTheme,
  };
};
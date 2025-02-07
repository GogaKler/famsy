import { onMounted, ref, type Ref } from 'vue';

type Theme = 'light' | 'dark' | 'contrast';

export const useTheme = () => {
  const theme: Ref<Theme, Theme> = ref<Theme>('light');

  const setTheme = (newTheme: Theme) => {
    document.documentElement.classList.remove(theme.value);
    document.documentElement.classList.add(newTheme);
    theme.value = newTheme;
    localStorage.setItem('theme', newTheme);
  };

  const toggleTheme = () => {
    const newTheme = theme.value === 'light' ? 'dark' : 'light';
    setTheme(newTheme);
  };

  onMounted(() => {
    const savedTheme: Theme = localStorage.getItem('theme') as Theme;
    if (savedTheme) {
      setTheme(savedTheme);
      return;
    }

    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    setTheme(prefersDark ? 'dark' : 'light');
  });

  return {
    theme,
    setTheme,
    toggleTheme,
  };
}; 
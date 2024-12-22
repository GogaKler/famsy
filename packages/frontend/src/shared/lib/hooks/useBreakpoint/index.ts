import { onMounted, onUnmounted, reactive } from 'vue';

type BreakpointKey = 'xs' | 'sm' | 'md' | 'lg' | 'xl' | 'xxl' | 'all';

interface Screens {
  xs: number;
  sm: number;
  md: number;
  lg: number;
  xl: number;
  xxl: number;
}

interface Breakpoints {
  w: number;
  h: number;
  is: BreakpointKey;
}

const screens: Screens = {
  xs: 360,
  sm: 640,
  md: 768,
  lg: 1024,
  xl: 1280,
  xxl: 1536,
};

const breakpoints = reactive<Breakpoints>({ w: 0, h: 0, is: 'xs' });

const xs = (val: number): boolean => val <= screens.xs;
const sm = (val: number): boolean => val <= screens.sm;
const md = (val: number): boolean => val <= screens.md;
const lg = (val: number): boolean => val <= screens.lg;
const xl = (val: number): boolean => val <= screens.xl;
const xxl = (val: number): boolean => val <= screens.xxl;

const getBreakpoint = (w: number): BreakpointKey => {
  if (xs(w)) return 'xs';
  else if (sm(w)) return 'sm';
  else if (md(w)) return 'md';
  else if (lg(w)) return 'lg';
  else if (xl(w)) return 'xl';
  else if (xxl(w)) return 'xxl';
  else return 'all';
};

const setBreakpoint = (): void => {
  breakpoints.w = document.documentElement.clientWidth;
  breakpoints.h = document.documentElement.clientHeight;
  breakpoints.is = getBreakpoint(document.documentElement.clientWidth);
};

export function useBreakpoint(): Breakpoints {
  const handleResize = (): void => {
    setBreakpoint();
  };

  onMounted(() => {
    setBreakpoint();
    window.addEventListener('resize', handleResize);
  });

  onUnmounted(() => {
    window.removeEventListener('resize', handleResize);
  });

  return breakpoints;
} 
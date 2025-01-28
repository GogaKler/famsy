import { breakpointsTailwind, useBreakpoints } from '@vueuse/core';

const breakpoints = useBreakpoints(breakpointsTailwind);

export function useBreakpoint() {
  return {
    isMobile: breakpoints.smallerOrEqual('md'),
    isTablet: breakpoints.greater('lg'),
    isDesktop: breakpoints.greaterOrEqual('lg'),
  };
}
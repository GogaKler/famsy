import { onMounted, onUnmounted, reactive } from 'vue';

const screens = {
    xs: 360,
    sm: 640,
    md: 768,
    lg: 1024,
    xl: 1280,
    xxl: 1536
};

const breakpoints = reactive({ w: 0, h: 0, is: 'xs' });

const xs = (val) => val <= screens.xs;
const sm = (val) => val <= screens.sm;
const md = (val) => val <= screens.md;
const lg = (val) => val <= screens.lg;
const xl = (val) => val <= screens.xl;
const xxl = (val) => val <= screens.xxl;

const getBreakpoint = (w) => {
    if (xs(w)) return 'xs';
    else if (sm(w)) return 'sm';
    else if (md(w)) return 'md';
    else if (lg(w)) return 'lg';
    else if (xl(w)) return 'xl';
    else if (xxl(w)) return 'xxl';
    else return 'all';
};

const setBreakpoint = () => {
    breakpoints.w = document.documentElement.clientWidth;
    breakpoints.h = document.documentElement.clientHeight;
    breakpoints.is = getBreakpoint(document.documentElement.clientWidth);
};

export const useBreakpoint = () => {
    onMounted(() => {
        setBreakpoint();
        window.addEventListener('resize', () => {
            setBreakpoint();
        });
    });

    onUnmounted(() => {
        window.removeEventListener('resize', () => {
            setBreakpoint();
        });
    });

    return breakpoints;
};

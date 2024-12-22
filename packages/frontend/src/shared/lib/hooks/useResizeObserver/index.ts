import { useSupported } from '../useSupported';
import { type Ref, unref, watch } from 'vue';

interface ResizeObserverSize {
  width: number;
  height: number;
  entry: ResizeObserverEntry;
}

type ResizeCallback = (size: ResizeObserverSize) => void;
type MaybeElement = HTMLElement | VueInstance | null | undefined;
type MaybeElementRef = Ref<MaybeElement> | (() => MaybeElement) | MaybeElement;

const defaultWindow: Window = window;

interface VueInstance {
  $el: HTMLElement;
}

/**
 * Преобразует ref или функцию в элемент
 * @param elRef - Элемент, ref или функция, возвращающая элемент
 */
function unrefElement(elRef: MaybeElementRef): MaybeElement {
  const plain = typeof elRef === 'function' ? elRef() : unref(elRef);
  return plain && '$el' in plain ? plain.$el : plain;
}

/**
 * Хук для отслеживания изменений размеров элемента
 * @param element - Элемент для отслеживания
 * @param callback - Функция обратного ��ызова при изменении размеров
 * @param options - Опции ResizeObserver
 */
export function useResizeObserver(
  element: MaybeElementRef,
  callback: ResizeCallback,
  options: ResizeObserverOptions = {}
) {
  const { ...observerOptions } = options;
  let observer: ResizeObserver | undefined;
  const isSupported = useSupported(() => defaultWindow && 'ResizeObserver' in defaultWindow);

  const cleanup = () => {
    if (observer) {
      observer.disconnect();
      observer = undefined;
    }
  };

  const stopWatch = watch(
    () => unrefElement(element),
    (el) => {
      cleanup();

      if (isSupported.value && window && el instanceof HTMLElement) {
        observer = new ResizeObserver((entries) => {
          const entry = entries[0];
          const target = entry.target as HTMLElement;
          const width = target.offsetWidth;
          const height = target.offsetHeight;

          callback({ width, height, entry });
        });
        observer.observe(el, observerOptions);
      }
    },
    { immediate: true, flush: 'post' }
  );

  const stop = () => {
    cleanup();
    stopWatch();
  };

  return {
    isSupported,
    stop,
    observer,
  };
}

type Handler = (event: MouseEvent, el: HTMLElement) => void;
type EventHandler = (event: MouseEvent) => void;
type ActionType = 'add' | 'remove';

const instances: Map<HTMLElement, EventHandler> = new Map();

const toggleEventListeners = (action: ActionType, eventHandler: EventHandler): void => {
  document.addEventListener('click', eventHandler, true);
};

const onClickOutside = ({ event, el, handler }: { event: MouseEvent, el: HTMLElement, handler: Handler; }): void => {
  const isClickOutside = event.target !== el && !el.contains(event.target as Node);

  if (isClickOutside) {
    handler(event, el);
  }
};

export const clickOutside = {
  beforeMount(el: HTMLElement, { value: handler }: { value: Handler }): void {
    const eventHandler = (event: MouseEvent) => onClickOutside({ event, el, handler });

    toggleEventListeners('add', eventHandler);

    instances.set(el, eventHandler);
  },
  
  unmounted(el: HTMLElement): void {
    const eventHandler = instances.get(el);

    if (eventHandler) {
      toggleEventListeners('remove', eventHandler);
      instances.delete(el);
    }
  },
};

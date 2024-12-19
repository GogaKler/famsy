const instances = new Map();

const toggleEventListeners = (action, eventHandler) => {
    document[`${action}EventListener`]('click', eventHandler, true);
};

const onClickOutside = ({ event, el, handler }) => {
    const isClickOutside = event.target !== el && !el.contains(event.target);

    return isClickOutside ? handler(event, el) : null;
};

export const clickOutside = {
    beforeMount(el, { value: handler }) {
        const eventHandler = (event) => onClickOutside({ event, el, handler });

        toggleEventListeners('add', eventHandler);

        instances.set(el, eventHandler);
    },
    unmounted(el) {
        const eventHandler = instances.get(el);

        toggleEventListeners('remove', eventHandler);

        instances.delete(el);
    }
};

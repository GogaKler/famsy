import { io } from 'socket.io-client';

export const useSocket = () => {
    const socket = io(import.meta.env.VITE_BACKEND_WS_URL);

    return { socket };
};

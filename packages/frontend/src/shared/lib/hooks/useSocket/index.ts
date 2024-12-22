import { io, Socket } from 'socket.io-client';

export const useSocket = (): { socket: Socket } => {
    const socket = io(import.meta.env.VITE_BACKEND_WS_URL);

    return { socket };
};

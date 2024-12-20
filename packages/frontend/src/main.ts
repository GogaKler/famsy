import '@app/styles/main.scss';
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { FsPluginFontawesome } from '@shared/plugins';
import { App } from '@app/ui';
import { useAuthGuard } from '@app/guard';
import router from '@app/router';

const app = createApp(App);
app.use(FsPluginFontawesome);
app.use(createPinia());
app.use(router);
useAuthGuard(router);
app.mount('#app');
import express from 'express';
import { type Request, type Response } from 'express';
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { createServer } from 'vite';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

async function startServer() {
  const app = express();
  const isProduction = process.env.NODE_ENV === 'production';
  let vite: any;

  if (isProduction) {
    app.use(express.static(path.resolve(__dirname, '../dist')));
  } else {
    vite = await createServer({
      server: { middlewareMode: true },
      root: path.resolve(__dirname, '../'),
    });
    app.use(vite.middlewares);
  }

  app.use('*', async (req: Request, res: Response) => {
    try {
      const url = req.originalUrl;
      let template: string, renderPage: (url: string) => Promise<string>;

      if (isProduction) {
        template = fs.readFileSync(
          path.resolve(__dirname, '../index.html'),
          'utf-8',
        );
        renderPage = (await import('./entry-server.js')).renderPage;
      } else {
        template = fs.readFileSync(
          path.resolve(__dirname, '../index.html'),
          'utf-8',
        );
        template = await vite.transformIndexHtml(url, template);
        const serverEntry = await vite.ssrLoadModule('/frontend/server/entry-server.ts');
        renderPage = serverEntry.renderPage;
      }

      const appHtml = await renderPage(url);
      const html = template.replace('<!--ssr-outlet-->', appHtml);
      res.status(200).set({ 'Content-Type': 'text/html' }).end(html);
    } catch (error: any) {
      if (isProduction) {
        console.error(error);
        res.status(500).end(error.message);
      } else {
        vite.ssrFixStacktrace(error);
      }
    }
  });

  const port = process.env.VITE_PORT || 8069;
  app.listen(port, () => {
    console.log(`Server started at http://localhost:${port}`);
  });
}

startServer();

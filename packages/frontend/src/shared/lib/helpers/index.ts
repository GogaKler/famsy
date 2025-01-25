

export async function urlToFile(url: string, filename: string, mimeType: string): Promise<File> {
  mimeType = mimeType || (url.match(/^data:([^;]+);/) || '')[1];
  const res = await fetch(url);
  const buf: ArrayBuffer = await res.arrayBuffer();

  return new File([buf], filename, { type: mimeType });
}

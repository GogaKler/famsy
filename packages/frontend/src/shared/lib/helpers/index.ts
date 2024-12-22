

export async function urlToFile(url: string, filename: string, mimeType: string): Promise<File> {
    mimeType = mimeType || (url.match(/^data:([^;]+);/) || '')[1];
    let res = await fetch(url);
    let buf: ArrayBuffer = await res.arrayBuffer();

    return new File([buf], filename, { type: mimeType });
}

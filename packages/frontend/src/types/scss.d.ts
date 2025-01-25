declare module '*.scss' {
  const content: {
    colorsJson: string;
    [key: string]: any;
  };
  export default content;
}
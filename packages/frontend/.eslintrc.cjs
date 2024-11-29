/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution');

module.exports = {
  root: true,
  extends: [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    '@vue/eslint-config-typescript',
  ],
  plugins: ['@stylistic'],
  rules: {
    '@stylistic/array-bracket-newline': ['error', { multiline: true }],
    '@stylistic/array-element-newline': ['error', 'consistent'],
    '@stylistic/indent': ['error', 2],
    '@stylistic/semi': 'error',
    '@stylistic/quotes': ['error', 'single', { avoidEscape: true }],
    '@stylistic/comma-dangle': ['error', 'always-multiline'],
    '@stylistic/no-extra-semi': 'error',
    '@stylistic/object-curly-spacing': ['error', 'always'],
  },
  parserOptions: {
    ecmaVersion: 'latest',
  },
};

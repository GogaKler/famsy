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

    'vue/singleline-html-element-content-newline': ['error'], // Перенос содержимого на новую строку
    'vue/multiline-html-element-content-newline': ['error'], // Перенос содержимого на новую строку
    'vue/no-multi-spaces': ['error'],
    'vue/html-indent': ['error', 2], // 2 пробела для отступов
    'vue/max-attributes-per-line': [
      'error', {
        'singleline': 4, // Максимум 4 атрибута на строку
        'multiline': 1,
      },
    ],
    'vue/html-closing-bracket-spacing': [
      'error', {
        'startTag': 'never',
        'endTag': 'never',
        'selfClosingTag': 'always',
      },
    ],
    'vue/attribute-hyphenation': ['error', 'always'], // Дефисы в атрибутах
    'vue/attributes-order': [
      'error', { // Порядок атрибутов
        'order': [
          'DEFINITION',
          'LIST_RENDERING',
          'CONDITIONALS',
          'RENDER_MODIFIERS',
          'GLOBAL',
          'UNIQUE',
          'TWO_WAY_BINDING',
          'OTHER_DIRECTIVES',
          'OTHER_ATTR',
          'EVENTS',
          'CONTENT',
        ],
      },
    ],
  },
  parserOptions: {
    ecmaVersion: 'latest',
  },
};

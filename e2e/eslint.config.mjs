import globals from "globals";
import pluginJs from "@eslint/js";
import tseslint from "typescript-eslint";
import playwright from "eslint-plugin-playwright";

/** @type {import('eslint').Linter.Config[]} */
export default [
  {
    ignores: [
      "playwright-report/**",
      "test-results/**",
      "node_modules/**",
      ".github/**",
    ],
  },
  {
    files: ["**/*.{js,mjs,cjs,ts}"],
    rules: {
      "space-before-blocks": ["error", "always"],
      indent: ["error", 2, { SwitchCase: 1 }],
      "no-tabs": "error",
      "no-multi-spaces": "error",
      "no-trailing-spaces": ["error", { skipBlankLines: false }],
      "object-curly-spacing": ["error", "always"],
      "array-bracket-spacing": ["error", "never"],
      "space-in-parens": ["error", "never"],
      "key-spacing": ["error", { beforeColon: false, afterColon: true }],
      "semi-spacing": ["error", { before: false, after: true }],
      "comma-spacing": ["error", { before: false, after: true }],
      "space-before-function-paren": [
        "error",
        { anonymous: "always", named: "never", asyncArrow: "always" },
      ],
      "keyword-spacing": ["error", { before: true, after: true }],
      "block-spacing": ["error", "always"],
      "comma-dangle": ["error", "always-multiline"],
      "space-unary-ops": ["error", { words: true, nonwords: false }],
    },
  },

  { languageOptions: { globals: globals.browser } },
  pluginJs.configs.recommended,
  ...tseslint.configs.recommended,
  {
    ...playwright.configs['flat/recommended'],
    files: ['tests/**'],
    rules: {
      ...playwright.configs['flat/recommended'].rules,
      // Customize Playwright rules
      // ...
    },
  },


];

// @ts-check
// `@type` JSDoc annotations allow editor autocompletion and type checking
// (when paired with `@ts-check`).
// There are various equivalent ways to declare your Docusaurus config.
// See: https://docusaurus.io/docs/api/docusaurus-config

import { themes as prismThemes } from 'prism-react-renderer';

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'AssertJ-JSON',
  tagline: 'Fluent assertions for JSON',
  favicon: 'img/favicon.ico',

  url: 'https://assertj-json.archyoshi.com/',
  baseUrl: '/',

  organizationName: 'archyoshi',
  projectName: 'assertj-json',

  onBrokenLinks: 'throw',

  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.js',
          editUrl: 'https://github.com/archyoshi/assertj-json/tree/main/docs/',
        },
        blog: false,
        theme: {
          customCss: './src/css/custom.css',
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      colorMode: {
        respectPrefersColorScheme: true,
      },
      // Algolia Search configuration
      // IMPORTANT: Populate these with your actual Algolia credentials
      algolia: {
        appId: 'YOUR_APP_ID',
        apiKey: 'YOUR_SEARCH_API_KEY',
        indexName: 'YOUR_INDEX_NAME',
        contextualSearch: true,
      },
      navbar: {
        title: 'AssertJ-JSON',
        items: [
          {
            type: 'doc',
            docId: 'index',
            position: 'left',
            label: 'Guide',
          },
          {
            type: 'doc',
            docId: 'quick-start',
            position: 'left',
            label: 'Quick Start',
          },
          {
            href: 'https://javadoc.jitpack.io/com/github/archyoshi/assertj-json/0.1.4/javadoc/index.html',
            label: 'API',
            position: 'left',
          },
          {
            href: 'https://github.com/archyoshi/assertj-json',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Docs',
            items: [
              {
                label: 'Guide',
                to: '/docs/',
              },
              {
                label: 'Quick Start',
                to: '/docs/quick-start',
              },
            ],
          },
          {
            title: 'More',
            items: [
              {
                label: 'GitHub',
                href: 'https://github.com/archyoshi/assertj-json',
              },
            ],
          },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} AssertJ-JSON. Built with Docusaurus.`,
      },
      prism: {
        theme: prismThemes.github,
        darkTheme: prismThemes.dracula,
        additionalLanguages: ['java', 'json'],
      },
    }),
};

export default config;

// @ts-check
import { defineConfig } from "astro/config";
import starlight from "@astrojs/starlight";
import tailwind from "@astrojs/tailwind";

// https://astro.build/config
export default defineConfig({
  site: "https://pixeval.github.io",
  integrations: [
    starlight({
      title: "Pixeval",
      defaultLocale: "root",
      locales: {
        root: {
          label: "简体中文",
          lang: "zh-CN",
        },
        en: {
          label: "English",
        },
        fr: {
          label: "Français",
        },
        ru: {
          label: "Русский",
        },
      },
      logo: {
        src: "./src/assets/logo.svg",
      },
      social: {
        github: "https://github.com/Pixeval/Pixeval",
      },
      sidebar: [
        {
          label: "Guides",
          items: [
            // Each item here is one entry in the navigation menu.
            { label: "Example Guide", slug: "guides/example" },
          ],
        },
        {
          label: "Reference",
          autogenerate: { directory: "reference" },
        },
      ],
      customCss: ["./src/tailwind.css"],
      components: {
        Footer: "./src/components/Footer.astro",
      },
    }),
    tailwind({ applyBaseStyles: false }),
  ],
});

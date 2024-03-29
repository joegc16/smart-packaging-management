// https://nuxt.com/docs/api/configuration/nuxt-config
// @ts-ignore
export default defineNuxtConfig({
  devtools: { enabled: true },

  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/academics/api'
    }
  },
  modules: [
      "@pinia/nuxt"
  ]
})
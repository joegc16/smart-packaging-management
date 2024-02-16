<template>
  <div>
    <Header />
    <slot />
  </div>
</template>

<script setup>
import {useAuthStore} from "~/store/auth-store.js";
const router = useRouter()
const authStore = useAuthStore()
function logout() {
authStore.logout()
router.push('/')
}
</script>
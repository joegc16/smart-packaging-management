<template>
  <div>
    <Header />
    <slot />
  </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js";
const authStore = useAuthStore();
const user = ref(null);

onMounted(() => {  
  if (authStore.restoreToken()){
    authStore.loadUser().then(() => {
      user.value = authStore.user;
      console.log("user.value", user.value);
    }).catch(error => {
      console.error("Error loading user data:", error);
    });
  }else{
  }
});
</script>

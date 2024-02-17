<template>
  <div>
    <h1>Welcome to the homepage</h1>
    <p v-if="loading">Loading...</p>
    <p v-else>Products</p>
  </div>
</template>

<script setup >
import {useAuthStore} from "../store/auth-store.js";

const authStore = useAuthStore()
const user = ref(null);
const loading = ref(true);

if (process.client) {
  if (authStore.restoreToken()){
    authStore.loadUser().then(() => {
      user.value = authStore.user; // Assuming authStore has a 'user' property
      console.log("user.value", user.value);
      loading.value = false; // Set loading to false once user data is loaded
    }).catch(error => {
      console.error("Error loading user data:", error);
      loading.value = false; // Set loading to false even in case of error
    });
  }else{
    loading.value = true; // Set loading to false if there's no token
  }
}

</script>
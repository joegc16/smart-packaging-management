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

if (user.value != undefined){
  console.log("nadad")
  console.log(user.value)
}else{
  if (process.client){
    console.log("entoasdasd")
    await authStore.loadUser()
    loading.value = true
  }
}

if (process.client) {
  // Load user data asynchronously
  authStore.loadUser().then(() => {
    user.value = authStore.user; // Assuming authStore has a 'user' property
    loading.value = false; // Set loading to false once user data is loaded
  }).catch(error => {
    console.error("Error loading user data:", error);
    loading.value = false; // Set loading to false even in case of error
  });
}
</script>
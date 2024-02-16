<template>
  <h1>Login Form</h1>
  <div>Username:
    <input v-model="loginFormData.username"></div>
  <div>Password:
    <input v-model="loginFormData.password"></div>
  <button @click="login">LOGIN</button>

</template>

<script setup>
import {useAuthStore} from "../../store/auth-store.js";

const authStore = useAuthStore()
const router = useRouter()
const loginFormData = ref({
  username: "",
  password: ""
})


async function login() {
  if (await authStore.login(loginFormData)) {
    router.push('/')
  }else{
    alert('you fucked up.')
  }
}
</script>

<style scoped></style>

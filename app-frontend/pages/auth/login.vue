<template>
  <div class="form-container">
    <form @submit.prevent="login">
      <h3>Login</h3>
      <div>
        <div>
          <label for="inputUsername">Username</label>
          <input
              type="text"
              id="inputUsername"
              placeholder="Your Username"
              required
              v-model="loginFormData.username">
        </div>
      </div>
      <div>
        <div>
          <label for="inputPassword">Password</label>
          <input
              type="password"
              id="inputPassword"
              placeholder="Your Password"
              required
              v-model="loginFormData.password">
        </div>
      </div>
      <div>
        <button type="button" @click="login">Login</button>
      </div>
    </form>
  </div>
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
  } else {
    alert('you fucked up.')
  }
}
</script>

<style>
@import url("assets/form.css");
</style>


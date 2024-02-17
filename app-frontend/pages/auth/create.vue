<template>
  <div class="form-container">
    <form @submit.prevent="create">
      <h3>Create</h3>
      <div>
        <div>
          <label for="inputName">Name</label>
          <input
              type="text"
              pattern="[A-Za-zÀ-ÖØ-öø-ÿ\s]+"
              id="inputName"
              placeholder="Type your Name"
              title="Please enter only letters and spaces"
              required
              v-model="createFormData.name">
        </div>
      </div>
      <div>
        <div>
          <label for="inputPassword">Password</label>
          <input
              type="password"
              id="inputPassword"
              placeholder="Type Password"
              required
              v-model="createFormData.password">
        </div>
      </div>
      <div>
        <div>
          <label for="inputUsername">Username</label>
          <input
              type="text"
              id="inputUsername"
              placeholder="Type a username"
              required
              v-model="createFormData.username">
        </div>
      </div>
      <div>
        <div>
          <label for="inputEmail">Email</label>
          <input
              type="email"
              id="inputEmail"
              placeholder="Type a email"
              required
              v-model="createFormData.email">
        </div>
      </div>
      <div>
        <div>
          <label for="inputRole">Role</label>
          <select v-model="createFormData.role" required>
            <option value="" disabled selected>Select a role</option>
            <option value=1>Manufacturer</option>
            <option value=2>Customer</option>
            <option value=3>LogisticsOperator</option>
          </select>
        </div>
      </div>
      <div>
        <button type="submit">Create</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import {useAuthStore} from "../../store/auth-store.js";
import { useToast } from "vue-toastification"


const config = useRuntimeConfig()
const api = config.public.API_URL
const authStore = useAuthStore()

const createFormData = ref({
  name: "",
  password: "",
  username: "",
  email: "",
  role: 0
})

const endpoint = computed(() => {
  if (createFormData.value.role == 1) {
    return 'manufacturers'
  } else if (createFormData.value.role == 2) {
    return 'customers'
  } else if (createFormData.value.role == 3) {
    return 'logistic-operators'
  }
})


const create = async () => {
  if (await authStore.createUser(endpoint, createFormData)) {
    console.log("Success!");
    navigateTo('/')

  } else {
    console.error("Error creating user");
  }
}

</script>

<style>


</style>
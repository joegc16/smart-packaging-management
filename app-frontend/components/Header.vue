<template>
    <div>
        <header>
            <h5>OnShop</h5>
            <nav>
              <ul class="navbar">
                <li>
                  <nuxt-link :to="{ name: 'index'}">Home</nuxt-link>
                </li>
                <li v-show="!authStore.user">
                  <nuxt-link :to="{ name: 'auth-login' }">Login</nuxt-link>
                </li>
                <li v-show="!authStore.user">
                  <nuxt-link :to="{ name: 'auth-create'}">Register</nuxt-link>
                </li>
                <li v-show="authStore.user">
                  <nuxt-link @click.prevent="logout">Logout</nuxt-link>
                </li>
              </ul>
            </nav>
        </header>
    </div>
</template>
<script setup>

import {useAuthStore} from "../store/auth-store.js";

const authStore = useAuthStore()
const router = useRouter()


const logout = async () => {
  if (await authStore.logout()) {
    await navigateTo({ name: 'index' })
  } else {
    alert('There was a problem logging out of the application!')
  }
}


</script>
<style>

header {
  background-color: #333; /* Dark background color */
  color: #fff; /* Text color */
  padding: 10px; /* Padding for content inside the header */
  display: flex;
  position: relative;
  line-height: normal;
  justify-content: space-between; /* Space items evenly along the main axis */
  align-items: center; /* Center items vertically */
}

h1 {
  margin: 0; /* Remove default margin for h1 */
  justify-content: flex-start; /* Align to the left */
}

nav a:hover {
  background-color: #777; /* Lighter color on hover */
  color: #fff; /* Text color on hover */
  text-decoration: none; /* Remove underline on hover */
}

.navbar {
  list-style: none; /* Remove bullet points */
  padding: 0; /* Remove default padding */
  display: flex; /* Use flexbox to create a horizontal layout */
}

.navbar li {
  margin-right: 20px; /* Add spacing between navbar items */
}

.navbar li:last-child {
  margin-right: 0; /* Remove margin from the last navbar item */
}

.navbar a {
  color: #fff; /* Text color for navbar links */
  text-decoration: none; /* Remove underline from links */
}

.navbar a:hover {
  text-decoration: underline; /* Add underline on hover */
}

</style>
<template>
  <div>
    <header>
      <nav class="menu-bar">
        <div class="group">
          <a class="item title">Products Shop</a>
        </div>
        <div class="group">
          <div class="item">
            <nuxt-link :to="{ name: 'index' }">Home</nuxt-link>
          </div>
          <div class="item" v-if="authStore.user">
            <nuxt-link :to="{ name: 'users-idClient-cart', params: { id: authStore.userId } }"
              >Cart</nuxt-link>
          </div>
          <div class="item" v-if="authStore.user">
            <nuxt-link :to="{ name: 'users-id', params: { id: authStore.userId } }"
              >Profile</nuxt-link>
          </div>
          <div class="item" v-if="!authStore.user">
            <nuxt-link :to="{ name: 'auth-login' }">Login</nuxt-link>
          </div>
          <div class="item" v-if="!authStore.user">
            <nuxt-link :to="{ name: 'auth-create' }">Register</nuxt-link>
          </div>
          <div class="item" v-if="authStore.user">
            <nuxt-link  @click.prevent="logout">Logout</nuxt-link>
          </div>
        </div>
      </nav>
    </header>
  </div>
</template>
<script setup>
import { useAuthStore } from "../store/auth-store.js";

const authStore = useAuthStore();

const logout = async () => {
  if (authStore.logout()) {
    await navigateTo({ name: "index" });
    alert("You have been logged out of the application!");
  } else {
    alert("There was a problem logging out of the application!");
  }
};
</script>
<style>
.menu-bar {
  background-color: #13c540;
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
}

.item {
  color: white;
  background-color: transparent;
  font-size: 18px;
  display: inline-block;
  box-sizing: border-box;
  padding: 14px 20px;
}

.item.title {
  font-weight: 600;
}

.item:hover {
  background-color: rgba(0, 0, 0, 0.1);
}
</style>

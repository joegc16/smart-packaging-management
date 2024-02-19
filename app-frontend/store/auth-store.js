import { defineStore } from "pinia";
import { useCartStore } from "./cart";
export const useAuthStore = defineStore("authStore", () => {
  const user = ref(null);
  const userId = computed(() => user.value?.id ?? -1);
  const userUsername = computed(() => user.value?.username ?? "Anonymous");

  const cartStore = useCartStore();
  const config = useRuntimeConfig();
  const api = config.public.API_URL;

  async function login(credentials) {
    try {
      const data = await $fetch(`${api}/auth/login`, {
        method: "post",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: JSON.stringify(credentials.value),
      });

      sessionStorage.setItem("token", data);
      await loadUser();
      return true;
    } catch (error) {
      console.log("auth store erro linha 38");
      return false;
    }
  }

  function logout() {
    clearUser();
    return true;
  }

  async function loadUser() {
    try {
      let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/auth/user`, {
        method: "get",
        headers: {
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
      });
      user.value = data;
      cartStore.loadCart(user.value.id);
    } catch (error) {
      throw error;
    }
  }

  async function getUserById(endpoint, id) {
    try {
      let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/${endpoint.value}/${id}`, {
        method: "get",
        headers: {
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
      });
      return data;
    } catch (error) {
      throw error;
    }
  }

  async function createUser(endpoint, userForm) {
    try {
      await $fetch(`${api}/${endpoint.value}`, {
        method: "post",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userForm.value),
      });
      const credentials = ref({
        username: userForm.value.username,
        password: userForm.value.password,
      });
      await login(credentials);
      return true;
    } catch (error) {
      clearUser();
      return false;
    }
  }

  async function saveUser(endpoint, userToSave) {
    try {
      let storedToken = sessionStorage.getItem("token");
      await $fetch(`${api}/${endpoint.value}/${userToSave.id}`, {
        method: "put",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: "Bearer " + storedToken,
        },
        body: JSON.stringify(userToSave),
      });
    } catch (error) {
      throw error;
    }
  }

  async function deleteUser(endpoint, id) {
    try {
      let storedToken = sessionStorage.getItem("token");
      await $fetch(`${api}/${endpoint.value}/${id}`, {
        method: "delete",
        headers: {
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
      });
      clearUser();
    } catch (error) {
      throw error;
    }
  }

  function restoreToken() {
    let storedToken = sessionStorage.getItem("token");
    if (storedToken) {
      return true;
    }
    clearUser();
    return false;
  }

  function clearUser() {
    sessionStorage.removeItem("token");
    user.value = null;
  }

  return {
    user,
    userId,
    userUsername,
    logout,
    login,
    loadUser,
    clearUser,
    restoreToken,
    createUser,
    saveUser,
    deleteUser,
    getUserById,
  };
});

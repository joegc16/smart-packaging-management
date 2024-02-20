import { defineStore } from "pinia";
import { useOrderStore } from "./order";
export const useCartStore = defineStore("cartStore", () => {
  const config = useRuntimeConfig();
  const api = config.public.API_URL;

  const cart = ref(null);
  const cartItems = ref([]);
  const orderStore = useOrderStore();

  async function loadCart(customerId) {
    try {
        let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/customers/${customerId}/cart`, {
        method: "get",
        headers: {
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
      });
      cart.value = data;
      cartItems.value = cart.value.cartItems;
      console.log("cart.value",cart.value);
      console.log("cartItems.value",cartItems.value);
    } catch (error) {
      throw error;
    }
  }

  async function updateItemInCart(id, newItem) {
    try {
      let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/carts/${cart.value.id}/items/${id}`, {
        method: "patch",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
        body: JSON.stringify(newItem.value),
      });
    } catch (error) {
      throw error;
    }
  }

  async function addItemToCart(newItem) {
    try {
      let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/carts/${cart.value.id}/items`, {
        method: "post",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
        body: JSON.stringify(newItem.value),
      });
    } catch (error) {
      throw error;
    }
  }

  async function removeItemInCart(itemId) {
    try {
      let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/carts/${cart.value.id}/items/${itemId}`, {
        method: "delete",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
      });
    } catch (error) {
      throw error;
    }

  }

  async function cleanCart() {
    try {
      let storedToken = sessionStorage.getItem("token");
      const data = await $fetch(`${api}/carts/${cart.value.id}/clean`, {
        method: "put",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: "Bearer " + storedToken,
        },
      });
    } catch (error) {
      throw error;
    }

  }

  async function checkOut(newOrder) {
    try {
      await orderStore.createOrder(newOrder);
      await cleanCart();
    } catch (error) {
      throw error;
    }
  }


  return { cart, cartItems, loadCart, updateItemInCart, addItemToCart, 
    removeItemInCart, cleanCart, checkOut};
});

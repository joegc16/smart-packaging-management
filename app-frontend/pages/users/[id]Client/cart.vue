<template>
  <div v-if="cart && cartItems">
    <UserCart
      :cart="cart"
      :cartItems="cartItems"
      @removeItem="removeItem"
      @updateQuantity="updateQuantity"
      @cleanCart="cleanCart"
      @checkOut="checkOut"
    >
    </UserCart>
  </div>
</template>

<script setup>
import { useCartStore } from "~/store/cart";

const route = useRoute();
const cartStore = useCartStore();
const cart = ref(null);
const cartItems = ref([]);
const userId = route.params.id;
const removeItem = async (id) => {
  try {
    await cartStore.removeItemInCart(id);
    await cartStore.loadCart(userId);
    cart.value = cartStore.cart;
    cartItems.value = cartStore.cartItems;
  } catch (error) {
    console.error("Error Removing Cart Item", error);
  }
};

const updateQuantity = async (id, newItem) => {
  try {
    await cartStore.updateItemInCart(id, newItem);
    await cartStore.loadCart(userId);
    cart.value = cartStore.cart;
    cartItems.value = cartStore.cartItems;
  } catch (error) {
    console.error("Error Incrementir Cart Item", error);
  }
};

const cleanCart = async () => {
  try {
    await cartStore.cleanCart(cart.value.id);
    await cartStore.loadCart(userId);
    cart.value = cartStore.cart;
    cartItems.value = cartStore.cartItems;
  } catch (error) {
    console.error("Error Cleaning Cart", error);
  }
};

const checkOut = async (customerInfo) => {
  try {
    const orderItems = cartItems.value.map((item) => {
      return {
        productPackageId: item.productPackageId,
        quantity: item.quantity,
        subPrice: item.subPrice,
      };
    });
    console.log("orderItems", orderItems);
    const order = ref({
      customerId: userId,
      manufacturerId: 1,
      logisticOperatorId: 4,
      orderDate: new Date().toISOString(),
      deliveryDate: null,
      estimatedDeliveryTime: "1 week",
      packageLocation: "unknown",
      city: customerInfo.value.city,
      postalCode: customerInfo.value.postalCode,
      country: customerInfo.value.country,
      address: customerInfo.value.address,
      paymentMethod: customerInfo.value.paymentMethod,
      status: "Pending",
      count: cart.value.count + 5,
      orderItems: orderItems,
    });
    console.log("order", order);
    await cartStore.checkOut(order);
    await cartStore.loadCart(userId);
    cart.value = cartStore.cart;
    cartItems.value = cartStore.cartItems;
  } catch (error) {
    console.error("Error Checking Out", error);
  }
};

onMounted(() => {
  cartStore.loadCart(userId).then(() => {
    cart.value = cartStore.cart;
    cartItems.value = cartStore.cartItems;
  });
});
</script>


import type { directivesPlugin } from 'bootstrap-vue';
<template>
  <div v-if="cart">
    <link
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
      rel="stylesheet"
    />
    <div class="card">
      <div class="row">
        <div class="col-md-8 cart">
          <div class="title">
            <div class="row">
              <div class="col">
                <h4><b>Shopping Cart</b></h4>
              </div>
              <div class="col align-self-center text-right text-muted">
                {{ cartItems.length }} Items
              </div>
            </div>
          </div>
          <div
            v-if="cartItems.length > 0"
            class="row border-top border-bottom"
            v-for="(item, index) in cartItems"
            :key="index"
          >
            <div class="row main align-items-center">
              <div class="col-2">
                <img
                  class="img-fluid"
                  :src="getImagePath(item.imageProductPackage)"
                />
              </div>
              <div class="col">
                <div class="row text-muted">{{ item.nameProduct }}</div>
                <div class="row">{{ item.descriptionProduct }}</div>
              </div>
              <div class="col">
                <a href="#" @click="decrementQuantity(index, item.id)">-</a>
                <a href="#" class="border">{{ item.quantity }}</a>
                <a href="#" @click="incrementQuantity(index, item.id)">+</a>
              </div>
              <div class="col">
                &euro; {{ item.subPrice }}
                <span class="close" @click="removeItem(item.id)">&#10005;</span>
              </div>
            </div>
          </div>
          <div v-else>
            <h5 class="text-muted">Your cart is empty</h5>
            </div>

          <div class="back-to-shop">
            <a href="/">&leftarrow;</a
            ><span class="text-muted">Back to shop</span>
          </div>
          <div class="back-to-shop">
            <button @click="cleanCart">Clear Cart</button>
          </div>
        </div>
        <div class="col-md-4 summary">
          <div>
            <h5><b>Summary</b></h5>
          </div>
          <hr />
          <div class="row">
            <div class="col" style="padding-left: 0">
              ITEMS {{ cartItems.length }}
            </div>
            <div class="col text-right">&euro; {{ cart.count }}</div>
          </div>
          <p>SHIPPING</p>
          <select>
            <option class="text-muted">Standard-Delivery- &euro;5.00</option>
          </select>
          <p>Info</p>
          <input v-model="customerInfo.city" id="code" placeholder="City" />
          <input v-model="customerInfo.country" id="code" placeholder="Country" />
          <input v-model="customerInfo.postalCode" id="code" placeholder="Postal Code" />
          <input v-model="customerInfo.address" id="code" placeholder="Address house" />
          <select v-model="customerInfo.paymentMethod">
            <option value="" class="text-muted" disabled selected></option>
            <option value="Credit Cart"  class="text-muted">Credit Cart</option>
            <option value="Debit Cart" class="text-muted">Debit Cart</option>
          </select>
          <div
            class="row"
            style="border-top: 1px solid rgba(196, 59, 59, 0.1); padding: 2vh 0"
          >
            <div class="col">TOTAL PRICE</div>
            <div class="col text-right">&euro; {{ totalCount }}</div>
          </div>
          <button class="btn" @click.prevent="checkOut">CHECKOUT</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useProductPackageStore } from "~/store/product-package";
import { useProductsStore } from "~/store/products";

const props = defineProps({
  cart: {
    type: Object,
    required: true,
  },
  cartItems: {
    type: Array,
    required: true,
  },
});

const emit = defineEmits(["removeItem", "updateQuantity", "cleanCart", "checkOut"]);

const productsPackagesStore = useProductPackageStore();
const productsStore = useProductsStore();
const productPackage = ref(null); //Image
const product = ref(null); //Name description
const totalCount = computed(() => {
  return (props.cart.count + 5).toFixed(2);
});
const newItem = ref({
  quantity: -1,
  subPrice: -1,
});
const customerInfo = ref({
  city: "",
  postalCode: "",
  country: "",
  address: "",
  paymentMethod: "",
})

const getImagePath = (imageUrl) =>
  imageUrl == "Not%20available"
    ? `/imgs/${imageUrl}`
    : "/imgs/default-image.png";

const removeItem = (id) => {
  emit("removeItem", id);
};

const incrementQuantity = (index, id) => {
  newItem.value = {
    quantity: props.cartItems[index].quantity + 1,
    subPrice:
      (props.cartItems[index].quantity + 1) *
      props.cartItems[index].priceProductPackage,
  };
  emit("updateQuantity", id, newItem);
};

const decrementQuantity = (index, id) => {
  if (props.cartItems[index].quantity - 1 == 0) {
    removeItem(id);
    return;
  }
  newItem.value = {
    quantity: props.cartItems[index].quantity - 1,
    subPrice:
      (props.cartItems[index].quantity - 1) *
      props.cartItems[index].priceProductPackage,
  };
  emit("updateQuantity", id, newItem);
};


const cleanCart = () => {
  emit("cleanCart");
};

const checkOut = () => {
  emit("checkOut", customerInfo);
};
</script>

<style scoped>
body {
  background: #ddd;
  min-height: 100vh;
  vertical-align: middle;
  display: flex;
  font-family: sans-serif;
  font-size: 0.8rem;
  font-weight: bold;
}
.title {
  margin-bottom: 5vh;
}
.card {
  margin: auto;
  max-width: 950px;
  width: 90%;
  box-shadow: 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 1rem;
  border: transparent;
}
@media (max-width: 767px) {
  .card {
    margin: 3vh auto;
  }
}
.cart {
  background-color: #fff;
  padding: 4vh 5vh;
  border-bottom-left-radius: 1rem;
  border-top-left-radius: 1rem;
}
@media (max-width: 767px) {
  .cart {
    padding: 4vh;
    border-bottom-left-radius: unset;
    border-top-right-radius: 1rem;
  }
}
.summary {
  background-color: #ddd;
  border-top-right-radius: 1rem;
  border-bottom-right-radius: 1rem;
  padding: 4vh;
  color: rgb(65, 65, 65);
}
@media (max-width: 767px) {
  .summary {
    border-top-right-radius: unset;
    border-bottom-left-radius: 1rem;
  }
}
.summary .col-2 {
  padding: 0;
}
.summary .col-10 {
  padding: 0;
}
.row {
  margin: 0;
}
.title b {
  font-size: 1.5rem;
}
.main {
  margin: 0;
  padding: 2vh 0;
  width: 100%;
}
.col-2,
.col {
  padding: 0 1vh;
}
a {
  padding: 0 1vh;
}
.close {
  margin-left: auto;
  font-size: 0.7rem;
}
img {
  width: 3.5rem;
}
.back-to-shop {
  margin-top: 4.5rem;
}
h5 {
  margin-top: 4vh;
}
hr {
  margin-top: 1.25rem;
}
form {
  padding: 2vh 0;
}
select {
  border: 1px solid rgba(0, 0, 0, 0.137);
  padding: 1.5vh 1vh;
  margin-bottom: 4vh;
  outline: none;
  width: 100%;
  background-color: rgb(247, 247, 247);
}
input {
  border: 1px solid rgba(0, 0, 0, 0.137);
  padding: 1vh;
  margin-bottom: 4vh;
  outline: none;
  width: 100%;
  background-color: rgb(247, 247, 247);
}
input:focus::-webkit-input-placeholder {
  color: transparent;
}
.btn {
  background-color: #000;
  border-color: #000;
  color: white;
  width: 100%;
  font-size: 0.7rem;
  margin-top: 4vh;
  padding: 1vh;
  border-radius: 0;
}
.btn:focus {
  box-shadow: none;
  outline: none;
  box-shadow: none;
  color: white;
  -webkit-box-shadow: none;
  transition: none;
}
.btn:hover {
  color: white;
}
a {
  color: black;
}
a:hover {
  color: black;
  text-decoration: none;
}
#code {
  background-image: linear-gradient(
      to left,
      rgba(255, 255, 255, 0.253),
      rgba(255, 255, 255, 0.185)
    ),
    url("https://img.icons8.com/small/16/000000/long-arrow-right.png");
  background-repeat: no-repeat;
  background-position-x: 95%;
  background-position-y: center;
}
</style>

<script setup>

const props = defineProps({
  products: {
    type: Array,
    required: true,
  }
});
</script>

<template>
    <div class="product-table">
      <div class="card-container">
        <nuxt-link :to="`/products/${product.code}`" v-for="(product, index) in products" :key="index">
          <ProductsProduct :product="product" />
        </nuxt-link>
      </div>
    </div>
</template>


<style scoped>
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* Align items to the start (left) by default */
  align-items: center;
  gap: 10%;
}
</style>
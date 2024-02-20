<script setup>

const props = defineProps({
  productsPackages: {
    type: Array,
    required: true,
  }
});
</script>

<template>
    <div class="product-table">
      <div class="card-container">
        <nuxt-link :to="`products/${productPackage.id}`" v-for="(productPackage, index) in productsPackages" :key="index">
          <ProductsProduct :product="productPackage" />
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
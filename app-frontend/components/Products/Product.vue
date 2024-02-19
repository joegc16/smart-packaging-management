<template>
    <div class="product">
      <div class="card">
        <img :src="getImagePath(product.image)" alt="Product Image" class="card-image" />
        <div class="card-body">
          <p class="product-price">{{product.price }}</p>
        </div>
      </div>
    </div>
  </template>

  
<script setup>

const props = defineProps(['product']);

const getImagePath = (imageUrl) => imageUrl == "Not%20available" ? `/imgs/${imageUrl}`: '/imgs/default-image.png'

</script>
<style scoped>
.product {
  text-align: center;
  margin: 10px;
}

.card {
  width: 200px;
  height: 100%; /* Adjust height as needed */
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.card-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* Ensure the image covers the entire space while maintaining aspect ratio */
}

.card-body {
  padding: 10px;
}

.product-price {
  font-size: 18px;
  margin-top: 8px;
  color: #333;
}
</style>
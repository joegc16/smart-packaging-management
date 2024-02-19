<template>
  <div class="grid">
    <main class="page-main">
      <div class="content">
        <div v-if="productPackage">
          <img :src="getImagePath(productPackage.image)" alt="Product Image" />
        </div>
        <div v-else class="loading">
          <p>Loading...</p>
        </div>
      </div>
    </main>
    <aside class="page-rightbar">
      <div class="content-rightbar">
        <div v-if="product && productPackage" class="product-info">
          <p>Price: {{ productPackage.price }}</p>
          <p><strong>Brand:</strong> {{ product.brand }}</p>
          <p><strong>Description:</strong> {{ product.description }}</p>
          <p>
            <strong>Manufacturer:</strong> {{ product.manufacturerUsername }}
          </p>
          <p><strong>Name:</strong> {{ product.name }}</p>
        </div>
        <div v-else class="loading">
          <p>Loading...</p>
        </div>
      </div>
    </aside>
  </div>
</template>

<script setup>
const props = defineProps({
  productPackage: Object,
  product: Object,
});

const getImagePath = (imageUrl) => imageUrl == "Not%20available" ? `/imgs/${imageUrl}`: '/imgs/default-image.png'

</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: auto 250px;
  grid-template-rows: 57px minmax(160px, auto) auto;
  grid-template-areas:
    "header header"
    "main rightbar"
    "footer footer";
  column-gap: 17px;
  row-gap: 14px;
}

.page-rightbar {
  grid-area: rightbar;
}

.page-main {
  grid-area: main;
}

.content {
  color: #242424;
  background-color: #ffffff;
  font-weight: 600;
  text-align: center;
  box-sizing: border-box;
  height: 100%;
  padding: 10px;
}

.content-rightbar {
  color: #242424;
  background-color: #a8a8a8;
  font-weight: 600;
  text-align: center;
  box-sizing: border-box;
  height: 100%;
  padding: 10px;
}
</style>

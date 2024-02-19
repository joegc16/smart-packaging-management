<template>
  <div v-if="loading">
        <p>Loading...</p>
    </div>
    <div v-else>
        <ProductsPackagesTable :productsPackages="productsPackagesStore.productsPackages" />
    </div>
</template>

<script setup >
import { useProductPackageStore } from "~/store/product-package";

const productsPackagesStore= useProductPackageStore()
const loading = ref(true);

const loadProductsPackages = async () => {
  try{
    console.log("Loading products packages");
    await productsPackagesStore.loadProductsPackages();
    loading.value = false;
  }catch(error){
    console.log("Error loading products packages");
  }
}

onMounted(() => {
  loadProductsPackages()
});

</script>
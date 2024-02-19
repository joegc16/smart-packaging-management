<template>

<div v-if="productPackage">
    <ProductsPackagesDetail 
    :productPackage="productPackage"
    :product="product">
    </ProductsPackagesDetail>
</div>

</template>

<script setup>
import { useProductPackageStore } from "~/store/product-package";
import { useProductsStore } from "~/store/products";
const route = useRoute()
const productsPackagesStore= useProductPackageStore()
const productsStore = useProductsStore()
const productPackageId = route.params.id
const productPackage = ref(null)
const product = ref(null)

const getProductPackage = async () => {
    try{
        productPackage.value = await productsPackagesStore.getProductPackageById(productPackageId);
        await getProduct();
    }catch(error){
        console.error("Error loading product package", error);
    }
}

const getProduct = async () => {
    try{
        product.value = await productsStore.getProductById(productPackage.value.productCode);
        console.log("product.value", product.value);
    }catch(error){
        console.error("Error loading product", error);
    }
}

onMounted(() => {
    getProductPackage();
});

</script>
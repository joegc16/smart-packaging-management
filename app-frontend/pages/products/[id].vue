<template>
  <div v-if="productPackage">
    <ProductsPackagesDetail
      :productPackage="productPackage"
      :product="product"
      @addToCart="addToCart"
    >
    </ProductsPackagesDetail>
  </div>
</template>

<script setup>
import { useProductPackageStore } from "~/store/product-package";
import { useProductsStore } from "~/store/products";
import { useCartStore } from "~/store/cart";

const route = useRoute();
const productsPackagesStore = useProductPackageStore();
const productsStore = useProductsStore();
const cartStore = useCartStore();

const productPackageId = route.params.id;
const productPackage = ref(null);
const product = ref(null);

const getProductPackage = async () => {
  try {
    productPackage.value = await productsPackagesStore.getProductPackageById(
      productPackageId
    );
    await getProduct();
  } catch (error) {
    console.error("Error loading product package", error);
  }
};

const getProduct = async () => {
  try {
    product.value = await productsStore.getProductById(
      productPackage.value.productCode
    );
    console.log("product.value", product.value);
  } catch (error) {
    console.error("Error loading product", error);
  }
};

const addToCart = async () => {
  try {
    const newItem = ref({
      quantity: 1,
      productPackageId: productPackageId,
        subPrice: productPackage.value.price,
    });
    console.log("Adding to cart", newItem.value);
    await cartStore.addItemToCart(newItem);
  } catch (error) {
    console.error("Error adding to cart", error);
  }
};

onMounted(() => {
  getProductPackage();
});
</script>

import {defineStore} from "pinia";

export const useProductsStore = defineStore("productsStore", () => {
    const config = useRuntimeConfig()
    const api = config.public.API_URL;

    const products = ref([])

    const loadProducts = async () => {}

    const getProductById = async (code) => {
        try {
            const data = await $fetch(`${api}/products/${code}`, {
                method: 'get',
                headers: {
                    'Accept': 'application/json'
                }
            })
            return data
        } catch (error) {
            return error
        }
    }


    return {products, getProductById}
})
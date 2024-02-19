import {defineStore} from "pinia";
export const useProductPackageStore = defineStore("productsPackagesStore", () => {
    const config = useRuntimeConfig()
    const api = config.public.API_URL;

    const productsPackages = ref([])

    async function loadProductsPackages(){
        try {
            const data = await $fetch(`${api}/productPackages`, {
                method: 'get',
                headers: {
                    'Accept': 'application/json'
                }
            })
            console.log('data', data)
            productsPackages.value = data
            return productsPackages.value
        } catch (error) {
            return error
        }
    }

    async function getProductPackageById(id){
        try {
            const data = await $fetch(`${api}/productPackages/${id}`, {
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




    return {productsPackages, loadProductsPackages, getProductPackageById}
})

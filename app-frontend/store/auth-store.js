import {defineStore} from "pinia";
export const useAuthStore = defineStore("authStore", () => {
    const user = ref(null)

    const config = useRuntimeConfig()
    const api = config.public.API_URL;

    async function login(credentials) {
        try {

            const form = {
                username : credentials.value.username,
                password : credentials.value.password}

            const data = await $fetch(`${api}/auth/login`, {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: form
            })

            sessionStorage.setItem('token', data)
            await loadUser()
            return true
        }
        catch(error) {
            console.log('auth store erro linha 38')
            return false
        }
    }


    function logout() {
        clearUser()
        return true
    }

    async function loadUser(){
        try {
            let storedToken = sessionStorage.getItem('token')
            const data = await $fetch(`${api}/auth/user`, {
                method: 'get',
                headers: {
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + storedToken
                }
            })
            user.value = data
        } catch (error) {
            throw error
        }
    }

    async function createUser(endpoint, userForm){
        try {
            await $fetch(`${api}/${endpoint.value}`, {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userForm.value)
            })
            await login(userForm)
            return true
        }
        catch(error) {
            clearUser()
            return false
        }
    }

    function restoreToken () {
        let storedToken = sessionStorage.getItem('token')
        if (storedToken) {
            return true
        }
        clearUser()
        return false
    }

    function clearUser() {
        sessionStorage.removeItem('token')
        user.value = null
    }

    return { user, logout, login, loadUser, clearUser, restoreToken, createUser }
})
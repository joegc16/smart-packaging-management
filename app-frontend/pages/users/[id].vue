<template>
  <div v-if="user">
    <UserDetail :user="user" 
    @saveChanges="saveChanges" 
    @cancelEdit="cancelEdit"
    @deleteCustomer="deleteCustomer"> 
    </UserDetail>
  </div>
</template>

<script setup>
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const user = ref(null);
const credentials = ref({
  username: "",
  password: ""
})
const router = useRouter()
const endpoint = computed(() => {
  if (user.value.role == 1) {
    return 'manufacturers'
  } else if (user.value.role == 2) {
    return 'customers'
  } else if (user.value.role == 3) {
    return 'logistic-operators'
  }
})

const saveChanges = async (userToSave) => {
  try {
    console.log("user", user.value)
    console.log("userToSave", userToSave);
    await authStore.saveUser(endpoint, userToSave);
    credentials.value = {
      username: userToSave.username,
      password: userToSave.password
    }
    await authStore.login(credentials);
    user.value = authStore.user;
  } catch (error) {
    console.error("Error saving user:", error);
  }
};

const cancelEdit = (editedUser) => {
};


const deleteCustomer = async () => {
  try {
    await authStore.deleteUser(endpoint, user.value.id);
    router.push("/");
  } catch (error) {
    console.error("Error deleting user:", error);
  }
};

onMounted(() => {
  authStore.loadUser().then(() => {
    user.value = authStore.user;
    console.log("user.value details", user.value);
  }).catch(error => {
    console.error("Error loading user data:", error);
  });
});

</script>
